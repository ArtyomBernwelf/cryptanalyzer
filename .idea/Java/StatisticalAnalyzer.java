public class StatisticalAnalyzer {

    // Метод для нахождения наиболее вероятного сдвига
    public int findMostLikelyShift(String encryptedText, char[] alphabet, String representativeText) {
        // 1. Вычисляем частоту символов в тексте-образце
        double[] sampleFreq = calculateFrequency(representativeText, alphabet);

        // 2. Перебираем все возможные сдвиги
        int bestShift = 0;
        double bestDeviation = Double.MAX_VALUE;

        for (int shift = 0; shift < alphabet.length; shift++) {
            // 3. Для каждого сдвига вычисляем частоту в зашифрованном тексте
            double[] encryptedFreq = calculateFrequency(encryptedText, alphabet);

            // 4. Сдвигаем частоты зашифрованного текста и сравниваем с образцом
            double deviation = 0;
            for (int i = 0; i < alphabet.length; i++) {
                // Индекс со сдвигом: (i + shift) % размер алфавита
                int shiftedIndex = (i + shift) % alphabet.length;
                // Вычисляем разницу частот
                double diff = encryptedFreq[shiftedIndex] - sampleFreq[i];
                // Суммируем квадраты отклонений (метод наименьших квадратов)
                deviation += diff * diff;
            }

            // 5. Запоминаем сдвиг с наименьшим отклонением
            if (deviation < bestDeviation) {
                bestDeviation = deviation;
                bestShift = shift;
            }
        }

        System.out.println("Статистический анализ: найден сдвиг " + bestShift +
                " с отклонением " + bestDeviation);

        return bestShift;
    }

    // Метод для вычисления частоты символов в тексте
    private double[] calculateFrequency(String text, char[] alphabet) {
        // Массив для подсчета количества каждого символа
        int[] counts = new int[alphabet.length];
        int total = 0;

        // Подсчитываем каждый символ в тексте
        for (int i = 0; i < text.length(); i++) {
            char currentChar = text.charAt(i);
            int index = findIndexInAlphabet(currentChar, alphabet);
            if (index != -1) {
                counts[index]++;
                total++;
            }
        }

        // Преобразуем количество в частоту (вероятность)
        double[] frequencies = new double[alphabet.length];
        if (total > 0) {
            for (int i = 0; i < alphabet.length; i++) {
                frequencies[i] = (double) counts[i] / total;
            }
        }

        return frequencies;
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