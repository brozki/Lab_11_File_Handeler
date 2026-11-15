import javax.swing.JFileChooser;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class DataSaver {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ArrayList<String> records = new ArrayList<>();
        boolean done = false;

        do {
            String firstName = SafeInput.getNonZeroLenString(in, "Enter first name");
            String lastName  = SafeInput.getNonZeroLenString(in, "Enter last name");
            int idNum        = SafeInput.getRangedInt(in, "Enter ID number", 0, 999999);
            String email     = SafeInput.getNonZeroLenString(in, "Enter email");
            int yob          = SafeInput.getRangedInt(in, "Enter year of birth", 1900, 2025);

            String record = String.format("%s,%s,%06d,%s,%d",
                    firstName, lastName, idNum, email, yob);
            records.add(record);
            System.out.println("Added: " + record);

            done = !SafeInput.getYNConfirm(in, "Add another record");
        } while (!done);

        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        chooser.setDialogTitle("Save File");

        if (chooser.showSaveDialog(null) != JFileChooser.APPROVE_OPTION) {
            System.out.println("No save location specified. Exiting.");
            System.exit(0);
        }

        Path filePath = chooser.getSelectedFile().toPath();

        // LAZY (line by line) write with try-with-resources
        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
            for (String record : records) {
                writer.write(record);
                writer.newLine();
            }
            System.out.println("File saved successfully: " + filePath);
        } catch (IOException e) {
            System.out.println("An error occurred writing the file.");
            e.printStackTrace();
        }
    }
}
