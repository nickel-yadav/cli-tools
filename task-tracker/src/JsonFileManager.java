import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonFileManager {
    private File jsonFile;
    private JSONObject data;

    public JsonFileManager(String filePath) {
        try {
            createOrLoadFile(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Error intializing the JSON file manager.", e);
        }
    }

    private void createOrLoadFile(String filePath) throws IOException {
        jsonFile = new File(filePath);
        if (!jsonFile.exists()) {
            jsonFile.createNewFile();
            data = new JSONObject();
            save();
        } else {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            data = content.isEmpty() ? new JSONObject() : new JSONObject(content);
        }
    }

    public JSONObject read() {
        return data;
    }

    public void write(String key, Object value) {
        data.put(key, value);
        save();
    }

    public void delete(String key) {
        data.remove(key);
        save();
    }

    private void save() {
        try (FileWriter writer = new FileWriter(jsonFile)) {
            writer.write(data.toString(4));
        } catch (IOException e) {
            throw new RuntimeException("Error saving JSON to file", e);
        }
    }


}
