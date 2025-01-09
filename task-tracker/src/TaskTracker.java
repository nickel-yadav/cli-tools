import java.util.Scanner;

public class TaskTracker {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TaskList taskList = new TaskList();
        System.out.println("Welcome to task tracker \n");
        System.out.println("Enter a command (type 'exit' to quit):");
        while (true) {
            // Take user input
            System.out.println("> ");
            String input = scanner.nextLine().trim();

            // Check for edge case - user wants to exit
            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Goodbye !");
                break;
            }

            // Split the input into command and instruction
            String[] parts = input.split(" ");
            String command = parts[0];
            String instruction = "";


            // Check for cases where command is valid
            switch (command) {
                case "add":
                    if (parts.length > 1) {
                        for (int i = 1; i < parts.length; i++) {
                            String formatted = parts[i] + " ";
                            instruction += formatted;
                        }
                        taskList.addTask(instruction);
                    } else {
                        System.out.println("Please provide a description");
                    }
                    break;
                case "list":
                    taskList.listTasks();
                    break;
                case "update":
                    if (parts.length > 1) {
                        if (isInteger(parts[1])) {
                            int taskId = Integer.parseInt(parts[1]);
                            for (int i=2; i<parts.length; i++) {
                                String formatted = parts[i] + " ";
                                instruction += formatted;
                            }
                            taskList.updateTask(taskId, instruction);
                        } else {
                            System.out.println("Please enter a valid id.");
                        }
                    } else {
                        System.out.println("Please provide task id and description");
                    }
                    break;
                case "delete":
                    if (parts.length > 1) {
                        if (isInteger(parts[1])) {
                            int taskId = Integer.parseInt(parts[1]);
                            taskList.deleteTask(taskId);
                        } else {
                            System.out.println("Please enter a valid id.");
                        }
                    } else {
                        System.out.println("Please provide task id");
                    }
                    break;
                default:
                    System.out.println("Unknown command");
                    break;
            }
        }
        scanner.close();
    }

    public static boolean isInteger(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }

    }
}
