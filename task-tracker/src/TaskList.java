import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TaskList {
    private File tasks;
    private JSONObject data;
    private int idCounter;
    private static int nameCounter = 1;

    public TaskList() {
     this.idCounter = 1;
     nameCounter++;
     createOrLoadFile();
    }

    private void createOrLoadFile() {
        try {
            tasks = new File("taskList"+ nameCounter);
            if (tasks.exists()) {
                System.out.println("File already exists:" + tasks.getAbsolutePath());
            }
            else {
                if (tasks.createNewFile()) {
                    data = new JSONObject();
                    System.out.println("File created successfully" + tasks.getAbsolutePath());
                }
                else {
                    System.out.println("Failed to create the file.");
                }
            }

        }
        catch (IOException e) {
           System.out.println("An error has occurred while creating the file" + e.getMessage());
           e.printStackTrace();
        }
    }

    public void addTask(String description) {
        Task newTask = new Task(idCounter, description);
        JSONObject taskData = new JSONObject();
        taskData.put("id", newTask.getId());
        taskData.put("description", newTask.getDescription());
        taskData.put("status", newTask.getStatus());
        save();
        System.out.println("Task added successfully (ID: " + idCounter + ")");
        idCounter++;
    }

    private void save() {
        try (FileWriter writer = new FileWriter(tasks)) {
            writer.write(data.toString(4));
        } catch (IOException e) {
            throw new RuntimeException("Error saving JSON to file", e);
        }
    }

    public void listTasks() {
        processTasks((task) -> true); // Process all tasks
    }

    public void listTasksByStatus(String status) {
        processTasks((task) -> task.has("status") && task.getString("status").equalsIgnoreCase(status));
    }

    private void processTasks(TaskProcessor processor) {
        if (tasks.length() == 0) {
            System.out.println("There are no tasks in the list.");
            return;
        }
        try (FileReader fileReader = new FileReader(tasks.getAbsolutePath())) {
            String jsonString = readFile(fileReader);
            if (jsonString.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(jsonString);
                processJSONArray(jsonArray, processor);
            } else if (jsonString.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(jsonString);
                processJSONObject(jsonObject, processor);
            } else {
                System.out.println("Invalid JSON format");
            }
        } catch (IOException e) {
            System.out.println("Error reading the JSON file: " + e.getMessage());
        }
    }

    private String readFile(FileReader fileReader) throws IOException {
        StringBuilder fileContent = new StringBuilder();
        int i;
        while ((i = fileReader.read()) != -1) {
            fileContent.append((char) i);
        }
        return fileContent.toString().trim();
    }

    private void processJSONArray(JSONArray jsonArray, TaskProcessor processor) {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            if (processor.shouldProcess(obj)) {
                System.out.println("Task " + (i + 1) + ": " + obj);
            }
        }
    }

    private void processJSONObject(JSONObject jsonObject, TaskProcessor processor) {
        for (String key : jsonObject.keySet()) {
            JSONObject obj = jsonObject.getJSONObject(key);
            if (processor.shouldProcess(obj)) {
                System.out.println("Key: " + key + ", Task: " + obj);
            }
        }
    }

    // Functional interface for processing tasks
    @FunctionalInterface
    interface TaskProcessor {
        boolean shouldProcess(JSONObject task);
    }



}
