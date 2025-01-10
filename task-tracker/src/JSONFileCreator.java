import org.json.JSONObject;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class JSONFileCreator {
    public void createLocalFile() {
       try {
           JFileChooser fileChooser = new JFileChooser();
           fileChooser.setDialogTitle("Select a location to save the JSON file");
           fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

           int userSelection = fileChooser.showSaveDialog(null);

           if (userSelection == JFileChooser.APPROVE_OPTION) {
               File selectedDirectory = fileChooser.getSelectedFile();
               System.out.println("Selected directory: " + selectedDirectory.getAbsolutePath());

               String fileName = JOptionPane.showInputDialog("Enter the file name (without extension):");
               if (fileName == null || fileName.trim().isEmpty()) {
                   System.out.println("File creation canceled by the user.");
                   return;
               }

               File jsonFile = new File(selectedDirectory, fileName + ".json");
               if (jsonFile.exists()) {
                   System.out.println("The file already exists" + jsonFile.getAbsolutePath());
               } else {
                   createJSONFile(jsonFile);
                   System.out.println("File created successfully at: " + jsonFile.getAbsolutePath());
               }
           } else {
               System.out.println("File selection canceled");
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
    }

    private static void createJSONFile(File file) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "John Doe");
        jsonObject.put("email", "john.doe@example.com");
        jsonObject.put("age", 30);
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(jsonObject.toString(4));
            fileWriter.flush();
        } catch (IOException e) {
            System.out.println("Error writing to the file: " + e.getMessage());
        }
    }
}
