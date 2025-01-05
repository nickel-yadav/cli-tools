import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;
    private int idCounter;

    TaskList() {
        this.tasks = new ArrayList<>();
        this.idCounter = 1;
    }

    public void addTask(String description) {
        Task newTask = new Task(idCounter, description);
        tasks.add(newTask);
        System.out.println("Task added successfully (ID: " + idCounter + ")");
        idCounter++;
    }

    public ArrayList<Task> listTasks() {
        return this.tasks;
    }

    private ArrayList<Task> listTasksByStatus(Status status) {
        ArrayList<Task> filteredByStatus = new ArrayList<Task>();
        for (Task currentTask : tasks) {
            if (currentTask.getStatus() == status) {
                filteredByStatus.add(currentTask);
            }
        }
        return filteredByStatus;
    }

    private void updateTaskStatus(int id, Status updatedStatus) {
        for( Task task: tasks) {
            if (task.getId() == id) {
                task.setStatus(updatedStatus);
                System.out.println("Status updated successfully");
            }
        }
    }

    private void deleteTask(int id) {
        for (Task task: tasks) {
            if (task.getId() == id) {
                tasks.remove(task);
                System.out.println("Task deleted successfully");
            }
        }
    }
}
