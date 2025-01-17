import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

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
        if (tasks.length() == 0) {
            System.out.println("There are no tasks in the list.");
            return;
        }
        try (FileReader fileReader = new FileReader(tasks.getAbsolutePath())) {
            StringBuilder fileContent = new StringBuilder();
            int i;
            while((i = fileReader.read()) != -1) {
                fileContent.append((char) i);
            }
            String jsonString = fileContent.toString().trim();
            if (jsonString.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(jsonString);
                iterateOverArray(jsonArray);
            } else if  (jsonString.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(jsonString);
                iterateOverObject(jsonObject);
            } else {
                System.out.println("Invalid JSON format");
            }
        } catch (IOException e) {
            System.out.println("Error reading the JSON file" + e.getMessage());
        }
    }

    private static void iterateOverArray(JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            System.out.println("Object " + (i + 1) + ": " + obj);
        }
    }

    private static void iterateOverObject(JSONObject jsonObject) {
        for (String key : jsonObject.keySet()) {
            Object value = jsonObject.get(key);
            System.out.println("Key: " + key + ", Value: " + value);
        }
    }

    public void listTasksByStatus(String status) {
        ArrayList<Task> filteredByStatus = new ArrayList<Task>();
        Status currentStatus = Status.PENDING;
        currentStatus = switch (status) {
            case "done" -> Status.COMPLETE;
            case "in-progress" -> Status.ONGOING;
            default -> currentStatus;
        };
        for (Task currentTask : tasks) {
            if (currentTask.getStatus() == currentStatus) {
                filteredByStatus.add(currentTask);
            }
        }

        for (Task task : filteredByStatus) {
            System.out.println(task.getId() + "\n" + task.getDescription() + "\n" + task.getStatus() + "\n" );
        }

    }

    public void updateTask(int id, String description) {
        // Edge case: Empty task list
        if (tasks.isEmpty()) {
            System.out.println("No tasks in the list");
        }

        for ( Task task: tasks) {
            if ( task.getId() == id) {
                task.setDescription(description);
                System.out.println("Task updated successfully");
            }
        }
    }

    public void updateTaskStatus(int id, Status updatedStatus) {
        for( Task task: tasks) {
            if (task.getId() == id) {
                task.setStatus(updatedStatus);
                System.out.println("Status updated successfully");
            }
        }
    }

    public void deleteTask(int id) {
        // Edge case: If the list is empty
        if (tasks.isEmpty()) {
            System.out.println("No tasks in the list.");
        }

        // Edge case: If there is only task in the list
        if (tasks.size() == 1) {
            tasks.remove(0);
            System.out.println("Task deleted successfully");
        }

        for (Task task: tasks) {
            if (task.getId() == id) {
                tasks.remove(task);
                System.out.println("Task deleted successfully");
            }
        }
    }
}
