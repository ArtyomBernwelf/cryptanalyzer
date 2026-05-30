public class BruteForce {

    // Метод для взлома перебором всех ключей
    public String decryptByBruteForce(String encryptedText, char[] alphabet, String sampleText) {
        // Создаем объект Cipher для расшифровки
        Cipher cipher = new Cipher(alphabet);

        // Переменные для хранения лучшего результата
        String bestResult = "";
        int bestKey = 0;
        int bestScore = -1;

        // Перебираем все возможные ключи (от 0 до размера алфавита-1)
        for (int key = 0; key < alphabet.length; key++) {
            // Расшифровываем текст текущим ключом
            String decryptedText = cipher.decrypt(encryptedText, key);

            // Оцениваем качество расшифровки
            int score = evaluateText(decryptedText, sampleText, alphabet);

            // Если текущий результат лучше предыдущего, запоминаем его
            if (score > bestScore) {
                bestScore = score;
                bestResult = decryptedText;
                bestKey = key;
            }
        }

        // Формируем результат с информацией о найденном ключе
        String output = "Результат brute force:\n";
        output += "Найденный ключ: " + bestKey + "\n";
        output += "Оценка качества: " + bestScore + "\n\n";
        output += bestResult;

        return output;
    }

    // Метод для оценки качества расшифрованного текста
    private int evaluateText(String text, String sampleText, char[] alphabet) {
        int score = 0;

        // Если есть образец текста, используем его для оценки
        if (sampleText != null && !sampleText.isEmpty()) {
            // Сравниваем частоту символов
            score = compareFrequency(text, sampleText, alphabet);
        } else {
            // Если образца нет, используем простые эвристики

            // 1. Проверяем наличие пробелов (в нормальном тексте они есть)
            int spaceCount = 0;
            for (int i = 0; i < text.length(); i++) {
                if (text.charAt(i) == ' ') {
                    spaceCount++;
                }
            }
            // Если пробелов достаточно много (хотя бы 3% от длины текста)
            if (spaceCount > text.length() / 33) {
                score += 50;
            }

            // 2. Проверяем наличие гласных букв (их должно быть много)
            String vowels = "аеиоуыэюя";
            int vowelCount = 0;
            for (int i = 0; i < text.length(); i++) {
                if (vowels.indexOf(text.charAt(i)) != -1) {
                    vowelCount++;
                }
            }
            // Добавляем очки за гласные (чем больше, тем лучше)
            score += vowelCount;

            // 3. Проверяем наличие коротких слов (предлогов)
            String[] words = text.split(" ");
            for (String word : words) {
                // Предлоги и союзы из одной буквы
                if (word.length() == 1) {
                    char c = word.charAt(0);
                    if (c == 'в' || c == 'и' || c == 'а' || c == 'о' || c == 'у' || c == 'к' || c == 'с') {
                        score += 30;
                    }
                }
                // Короткие слова из 2-3 букв тоже часто встречаются
                if (word.length() == 2 || word.length() == 3) {
                    score += 5;
                }
            }
        }

        return score;
    }

    // Метод для сравнения частоты символов
    private int compareFrequency(String text, String sampleText, char[] alphabet) {
        // Считаем частоту каждого символа в расшифрованном тексте
        int[] textCounts = new int[alphabet.length];
        for (int i = 0; i < text.length(); i++) {
            int index = findIndexInAlphabet(text.charAt(i), alphabet);
            if (index != -1) {
                textCounts[index]++;
            }
        }

        // Считаем частоту каждого символа в образце
        int[] sampleCounts = new int[alphabet.length];
        for (int i = 0; i < sampleText.length(); i++) {
            int index = findIndexInAlphabet(sampleText.charAt(i), alphabet);
            if (index != -1) {
                sampleCounts[index]++;
            }
        }

        // Сравниваем частоты (чем меньше разница, тем лучше)
        int score = 0;
        for (int i = 0; i < alphabet.length; i++) {
            // Вычитаем разницу, чтобы меньшая разница давала больший счет
            score -= Math.abs(textCounts[i] - sampleCounts[i]);
        }

        return score;
    }

    // Поиск индекса символа в алфавите
    private int findIndexInAlphabet(char c, char[] alphabet) {
        for (int i = 0; i < alphabet.length; i++) {
            if (alphabet[i] == c) {
                return i;
            }
        }
        return -1;
    }
}