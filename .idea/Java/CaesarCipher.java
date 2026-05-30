public class CaesarCipher {

    // Алфавит - константа, доступная всем методам класса
    // Включает русские буквы, знаки пунктуации и пробел
    private static final char[] ALPHABET = {
            'а', 'б', 'в', 'г', 'д', 'е', 'ж', 'з', 'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п',
            'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я',
            '.', ',', '"', '\'', ':', '!', '?', ' '
    };

    // Создаем объекты других классов для использования их методов
    private FileManager fileManager = new FileManager();
    private Validator validator = new Validator();
    private BruteForce bruteForce = new BruteForce();
    private StatisticalAnalyzer statisticalAnalyzer = new StatisticalAnalyzer();

    // Метод для шифрования текста
    public void encrypt(String inputFile, String outputFile, int key) {
        // 1. Проверяем, что входной файл существует
        if (!validator.isFileExists(inputFile)) {
            System.out.println("Ошибка: файл " + inputFile + " не найден!");
            return;
        }

        // 2. Проверяем, что ключ допустимый
        if (!validator.isValidKey(key, ALPHABET)) {
            System.out.println("Ошибка: ключ должен быть от 0 до " + (ALPHABET.length - 1));
            return;
        }

        // 3. Читаем текст из файла
        String text = fileManager.readFile(inputFile);
        if (text == null) return;

        // 4. Приводим текст к нижнему регистру
        text = text.toLowerCase();

        // 5. Создаем объект Cipher для выполнения шифрования
        Cipher cipher = new Cipher(ALPHABET);

        // 6. Шифруем текст
        String encryptedText = cipher.encrypt(text, key);

        // 7. Записываем результат в файл
        fileManager.writeFile(encryptedText, outputFile);

        System.out.println("Шифрование завершено! Результат сохранен в: " + outputFile);
    }

    // Метод для расшифровки с известным ключом
    public void decrypt(String inputFile, String outputFile, int key) {
        // 1. Проверяем, что входной файл существует
        if (!validator.isFileExists(inputFile)) {
            System.out.println("Ошибка: файл " + inputFile + " не найден!");
            return;
        }

        // 2. Проверяем, что ключ допустимый
        if (!validator.isValidKey(key, ALPHABET)) {
            System.out.println("Ошибка: ключ должен быть от 0 до " + (ALPHABET.length - 1));
            return;
        }

        // 3. Читаем зашифрованный текст из файла
        String encryptedText = fileManager.readFile(inputFile);
        if (encryptedText == null) return;

        // 4. Создаем объект Cipher для выполнения расшифровки
        Cipher cipher = new Cipher(ALPHABET);

        // 5. Расшифровываем текст
        String decryptedText = cipher.decrypt(encryptedText, key);

        // 6. Записываем результат в файл
        fileManager.writeFile(decryptedText, outputFile);

        System.out.println("Расшифровка завершена! Результат сохранен в: " + outputFile);
    }

    // Метод для взлома перебором (brute force)
    public void bruteForce(String inputFile, String outputFile, String optionalSampleFile) {
        // 1. Проверяем, что входной файл существует
        if (!validator.isFileExists(inputFile)) {
            System.out.println("Ошибка: файл " + inputFile + " не найден!");
            return;
        }

        // 2. Читаем зашифрованный текст
        String encryptedText = fileManager.readFile(inputFile);
        if (encryptedText == null) return;

        // 3. Читаем файл с примером текста (если он указан)
        String sampleText = null;
        if (optionalSampleFile != null && validator.isFileExists(optionalSampleFile)) {
            sampleText = fileManager.readFile(optionalSampleFile);
            if (sampleText != null) {
                sampleText = sampleText.toLowerCase();
            }
        }

        // 4. Выполняем brute force
        String result = bruteForce.decryptByBruteForce(encryptedText, ALPHABET, sampleText);

        // 5. Сохраняем результат
        fileManager.writeFile(result, outputFile);

        System.out.println("Brute force завершен! Результат сохранен в: " + outputFile);
    }

    // Метод для статистического анализа
    public void statisticalAnalysis(String inputFile, String outputFile, String optionalSampleFile) {
        // 1. Проверяем, что входной файл существует
        if (!validator.isFileExists(inputFile)) {
            System.out.println("Ошибка: файл " + inputFile + " не найден!");
            return;
        }

        // 2. Проверяем, что файл с примером текста указан и существует
        if (optionalSampleFile == null || !validator.isFileExists(optionalSampleFile)) {
            System.out.println("Ошибка: для статистического анализа нужен файл с примером текста!");
            return;
        }

        // 3. Читаем зашифрованный текст
        String encryptedText = fileManager.readFile(inputFile);
        if (encryptedText == null) return;

        // 4. Читаем текст-образец
        String sampleText = fileManager.readFile(optionalSampleFile);
        if (sampleText == null) return;

        // 5. Приводим к нижнему регистру
        encryptedText = encryptedText.toLowerCase();
        sampleText = sampleText.toLowerCase();

        // 6. Выполняем статистический анализ
        int bestShift = statisticalAnalyzer.findMostLikelyShift(encryptedText, ALPHABET, sampleText);

        // 7. Расшифровываем найденным ключом
        Cipher cipher = new Cipher(ALPHABET);
        String decryptedText = cipher.decrypt(encryptedText, bestShift);

        // 8. Формируем результат с информацией о найденном ключе
        String result = "Найденный ключ (статистический анализ): " + bestShift + "\n\n" + decryptedText;

        // 9. Сохраняем результат
        fileManager.writeFile(result, outputFile);

        System.out.println("Статистический анализ завершен! Найден ключ: " + bestShift);
    }
}