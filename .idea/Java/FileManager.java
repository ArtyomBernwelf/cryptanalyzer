import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;

public class FileManager {

    // Метод для чтения файла
    public String readFile(String filePath) {
        try {
            // Заменяем обратные слеши на прямые для совместимости
            String correctedPath = filePath.replace("\\", "/");

            // Создаем объект Path с путем к файлу
            Path path = Paths.get(correctedPath);

            // Проверяем существует ли файл перед чтением
            if (!Files.exists(path)) {
                System.out.println("Файл не существует: " + correctedPath);
                return null;
            }

            // Читаем все байты из файла
            byte[] bytes = Files.readAllBytes(path);

            // Преобразуем байты в строку с кодировкой UTF-8
            String content = new String(bytes, StandardCharsets.UTF_8);

            return content;

        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
            return null;
        }
    }

    // Метод для записи в файл
    public void writeFile(String content, String filePath) {
        try {
            // Заменяем обратные слеши на прямые
            String correctedPath = filePath.replace("\\", "/");

            // Создаем объект Path
            Path path = Paths.get(correctedPath);

            // Создаем папки если их нет
            Path parent = path.getParent();
            if (parent != null && !Files.exists(parent)) {
                Files.createDirectories(parent);
            }

            // Записываем в файл
            Files.write(path, content.getBytes(StandardCharsets.UTF_8));

            System.out.println("Файл успешно записан: " + correctedPath);

        } catch (IOException e) {
            System.out.println("Ошибка при записи файла: " + e.getMessage());
        }
    }
}