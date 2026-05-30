public class Cipher {

    // Алфавит, с которым работает шифр
    private char[] alphabet;

    // Конструктор - принимает алфавит при создании объекта
    public Cipher(char[] alphabet) {
        this.alphabet = alphabet;
    }

    // Метод для шифрования текста
    public String encrypt(String text, int shift) {
        // Создаем StringBuilder для эффективного построения строки
        StringBuilder result = new StringBuilder();

        // Проходим по каждому символу в тексте
        for (int i = 0; i < text.length(); i++) {
            char currentChar = text.charAt(i);

            // Находим позицию символа в алфавите
            int index = findIndex(currentChar);

            // Если символ не найден в алфавите, оставляем его без изменений
            if (index == -1) {
                result.append(currentChar);
            } else {
                // Вычисляем новую позицию: (старая позиция + сдвиг) % размер алфавита
                // Оператор % дает остаток от деления, чтобы символ "не улетел в космос"
                int newIndex = (index + shift) % alphabet.length;
                // Добавляем символ с новой позиции
                result.append(alphabet[newIndex]);
            }
        }

        // Возвращаем зашифрованную строку
        return result.toString();
    }

    // Метод для расшифровки текста
    public String decrypt(String encryptedText, int shift) {
        // Создаем StringBuilder для результата
        StringBuilder result = new StringBuilder();

        // Проходим по каждому символу в зашифрованном тексте
        for (int i = 0; i < encryptedText.length(); i++) {
            char currentChar = encryptedText.charAt(i);

            // Находим позицию символа в алфавите
            int index = findIndex(currentChar);

            // Если символ не найден, оставляем его
            if (index == -1) {
                result.append(currentChar);
            } else {
                // При расшифровке сдвигаем в обратную сторону
                // Формула: (старая позиция - сдвиг) % размер алфавита
                int newIndex = (index - shift) % alphabet.length;
                // Если результат отрицательный, добавляем размер алфавита
                if (newIndex < 0) {
                    newIndex += alphabet.length;
                }
                result.append(alphabet[newIndex]);
            }
        }

        return result.toString();
    }

    // Вспомогательный метод для поиска индекса символа в алфавите
    private int findIndex(char c) {
        // Проходим по всему массиву алфавита
        for (int i = 0; i < alphabet.length; i++) {
            // Если нашли нужный символ, возвращаем его позицию
            if (alphabet[i] == c) {
                return i;
            }
        }
        // Если символ не найден, возвращаем -1
        return -1;
    }
}