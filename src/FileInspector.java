import javax.swing.JFileChooser;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileInspector {
    public static void main(String[] args) {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));

        if (chooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
            System.out.println("No file selected. Exiting.");
            System.exit(0);
        }

        Path selectedPath = chooser.getSelectedFile().toPath();
        String fileName = selectedPath.getFileName().toString();

        try {
            // EAGER (bulk) read: whole file into a List
            List<String> lines = Files.readAllLines(selectedPath);

            int lineCount = lines.size();
            int wordCount = 0;
            int charCount = 0;

            System.out.println("----- Contents of " + fileName + " -----");
            for (String line : lines) {
                System.out.println(line);
                charCount += line.length();
                String trimmed = line.trim();
                if (!trimmed.isEmpty()) {
                    wordCount += trimmed.split("\\s+").length;
                }
            }

            System.out.println("\n----- File Report -----");
            System.out.println("File name:      " + fileName);
            System.out.println("Number of lines: " + lineCount);
            System.out.println("Number of words: " + wordCount);
            System.out.println("Number of chars: " + charCount);
        } catch (IOException e) {
            System.out.println("An error occurred reading the file.");
            e.printStackTrace();
        }
    }
}