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

    public void listTasks() {
        if (tasks.isEmpty()) {
            System.out.println("There are no tasks in the list.");
            return;
        }
        for (Task task: tasks) {
            System.out.println(task.getId() + "\n" + task.getDescription() + "\n" + task.getStatus() + "\n" );
        }
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
            else {
                System.out.println("Task with id" + id + "does not exist");
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
