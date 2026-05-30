import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;

public class Validator {

    // Метод для проверки существования файла
    public boolean isFileExists(String filePath) {
        // Заменяем неправильные слеши на правильные
        String correctedPath = filePath.replace("\\", "/");

        // Создаем объект Path
        Path path = Paths.get(correctedPath);

        // Получаем абсолютный путь для отладки
        String absolutePath = path.toAbsolutePath().toString();

        // Проверяем существует ли файл
        boolean exists = Files.exists(path);

        // Если файл не существует, выводим подробную информацию
        if (!exists) {
            System.out.println("Файл не найден: " + filePath);
            System.out.println("Программа ищет файл по пути: " + absolutePath);
            System.out.println();
            System.out.println("СОВЕТЫ:");
            System.out.println("1. Используйте прямые слеши: C:/папка/файл.txt");
            System.out.println("2. Или двойные обратные слеши: C:\\папка\\файл.txt");
            System.out.println("3. Или положите файл в папку с программой и введите просто имя файла");
        }

        return exists;
    }

    // Метод для проверки корректности ключа
    public boolean isValidKey(int key, char[] alphabet) {
        if (key >= 0 && key < alphabet.length) {
            return true;
        } else {
            System.out.println("Ошибка: ключ должен быть от 0 до " + (alphabet.length - 1));
            System.out.println("Вы ввели: " + key);
            return false;
        }
    }

    // Показать текущую рабочую папку
    public void showCurrentDirectory() {
        String currentDir = System.getProperty("user.dir");
        System.out.println("Текущая рабочая папка: " + currentDir);
    }
}