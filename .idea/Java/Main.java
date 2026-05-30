import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CaesarCipher cipher = new CaesarCipher();

        System.out.println("========== ПРОГРАММА ШИФРА ЦЕЗАРЯ ==========");
        System.out.println("ТЕКУЩАЯ ПАПКА: " + System.getProperty("user.dir"));
        System.out.println();
        System.out.println("КАК УКАЗЫВАТЬ ПУТИ К ФАЙЛАМ:");
        System.out.println("  - Просто имя: input.txt (файл в текущей папке)");
        System.out.println("  - Прямые слеши: C:/папка/файл.txt");
        System.out.println("  - Двойные обратные слеши: C:\\папка\\файл.txt");
        System.out.println("===========================================");

        while (true) {
            System.out.println("\n1. Шифрование");
            System.out.println("2. Расшифровка с ключом");
            System.out.println("3. Brute force");
            System.out.println("4. Статистический анализ");
            System.out.println("0. Выход");
            System.out.print("Выберите режим: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 0) {
                System.out.println("До свидания!");
                break;
            }

            switch (choice) {
                case 1:
                    System.out.print("Входной файл: ");
                    String inputFile = scanner.nextLine();
                    System.out.print("Выходной файл: ");
                    String outputFile = scanner.nextLine();
                    System.out.print("Ключ: ");
                    int key = scanner.nextInt();
                    cipher.encrypt(inputFile, outputFile, key);
                    break;

                case 2:
                    System.out.print("Зашифрованный файл: ");
                    inputFile = scanner.nextLine();
                    System.out.print("Выходной файл: ");
                    outputFile = scanner.nextLine();
                    System.out.print("Ключ: ");
                    key = scanner.nextInt();
                    cipher.decrypt(inputFile, outputFile, key);
                    break;

                case 3:
                    System.out.print("Зашифрованный файл: ");
                    inputFile = scanner.nextLine();
                    System.out.print("Выходной файл: ");
                    outputFile = scanner.nextLine();
                    System.out.print("Файл с примером (Enter - нет): ");
                    String sampleFile = scanner.nextLine();
                    if (sampleFile.isEmpty()) sampleFile = null;
                    cipher.bruteForce(inputFile, outputFile, sampleFile);
                    break;

                case 4:
                    System.out.print("Зашифрованный файл: ");
                    inputFile = scanner.nextLine();
                    System.out.print("Выходной файл: ");
                    outputFile = scanner.nextLine();
                    System.out.print("Файл с примером текста: ");
                    sampleFile = scanner.nextLine();
                    cipher.statisticalAnalysis(inputFile, outputFile, sampleFile);
                    break;

                default:
                    System.out.println("Неверный выбор!");
            }
        }

        scanner.close();
    }
}