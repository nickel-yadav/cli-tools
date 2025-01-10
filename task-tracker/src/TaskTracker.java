public class TaskTracker {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage:<command> [arguments...]");
            return;
        }

        TaskList taskList = new TaskList();
        System.out.println("Welcome to task tracker \n");
        System.out.println("Enter a command (type 'exit' to quit):");

        // Split the input into command and arguments
        String command = args[0];
        String[] commandArgs = java.util.Arrays.copyOfRange(args, 1, args.length);
        // Check for edge case - user wants to exit
        if (command.equalsIgnoreCase("exit")) {
            System.out.println("Goodbye !");
            return;
        }

        // Check for cases where command is valid
        switch (command) {
            case "add":
                if (commandArgs.length > 0) {
                    taskList.addTask(String.join(" ", commandArgs));
                } else {
                    System.out.println("Please provide a description");
                }
                break;
            case "list":
                if (commandArgs.length > 0) {
                    taskList.listTasksByStatus(commandArgs[0]);
                }
                else {
                    taskList.listTasks();
                }
                break;
            case "update":
                if (commandArgs.length > 0) {
                    if (isInteger(commandArgs[0])) {
                        int taskId = Integer.parseInt(commandArgs[0]);
                        taskList.updateTask(taskId, String.join(" ", commandArgs[1]));
                    } else {
                        System.out.println("Please enter a valid id.");
                    }
                } else {
                    System.out.println("Please provide task id and description");
                }
                break;
            case "delete":
                if (commandArgs.length > 1) {
                    if (isInteger(commandArgs[0])) {
                        int taskId = Integer.parseInt(commandArgs[0]);
                        taskList.deleteTask(taskId);
                    } else {
                        System.out.println("Please enter a valid id.");
                    }
                } else {
                    System.out.println("Please provide task id");
                }
                break;
            case "mark-in-progress":
                if (commandArgs.length > 0) {
                    if (isInteger(commandArgs[0])) {
                        int taskId = Integer.parseInt(commandArgs[0]);
                        taskList.updateTaskStatus(taskId, Status.ONGOING);
                    }
                }
                break;
            case "mark-done":
                if (commandArgs.length > 0) {
                    if (isInteger(commandArgs[0])) {
                        int taskId = Integer.parseInt(commandArgs[0]);
                        taskList.updateTaskStatus(taskId, Status.COMPLETE);
                    }
                }
                break;
            default:
                System.out.println("Unknown command");
                break;
        }
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
