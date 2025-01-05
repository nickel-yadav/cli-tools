import java.util.Scanner;

public class TaskTracker {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TaskList taskList = new TaskList();
        System.out.println("Welcome to task tracker \n");
        String input = scanner.nextLine();
        String[] words = input.split(" ");
        String command = words[0];
        String description = words[1];
        if (command.equals("add")) {
            taskList.addTask(description);
        }
        if (command.equals("list")) {
            taskList.listTasks();
        }
    }
}
