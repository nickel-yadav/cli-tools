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
