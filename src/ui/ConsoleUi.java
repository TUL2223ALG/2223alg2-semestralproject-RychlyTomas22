package ui;

import app.CategoryComparator;
import app.CompletionComparator;
import app.PriorityComparator;
import app.Task;
import app.TaskList;
import java.text.ParseException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.IToDoList;

/**
 *
 * @author tomir
 */
public class ConsoleUi {

    public static Scanner sc = new Scanner(System.in);

    private IToDoList iface;

    public void run() throws ParseException {
        iface = new TaskList();
        setDataPath();
        setDataFormat();
        displayMenu();
        boolean isRunning = true;
        while (isRunning) {
            int choise = getInputAsInt(sc);
            switch (choise) {
                case 1:
                    add();
                    break;
                case 2:
                    removeTask();
                    break;
                case 3:
                    updateTask();
                    break;
                case 4:
                    TasksDone();
                    break;
                case 5:
                    viewTasks();
                    break;
                case 6:
                    saveTasksToFile();
                    break;
                case 7:
                    loadTasksFromFile();
                    break;
                case 8:
                    setDataPath();
                    break;
                case 9:
                    setDataFormat();
                    break;
                case 10:
                    isRunning = false;
                    break;
                default:
                    System.out.println("not an option");
                    break;
            }
            displayMenu();

        }
    }

    private static void displayMenu() {
        System.out.println(" ***** ToDoList App ***** ");
        System.out.println("1. Add an item");
        System.out.println("2. Remove an item");
        System.out.println("3. Update existing item");
        System.out.println("4. Mark an item as done");
        System.out.println("5. Display ToDo list");
        System.out.println("6. Save the ToDo list to a file");
        System.out.println("7. Load the ToDo list from a file");
        System.out.println("8. select path to Data folder");
        System.out.println("9. switch Data format (txt / binary)");
        System.out.println("10. Exit");
        System.out.print("Enter your choice: ");
    }

    private void add() {

        System.out.println("what category would you like to assign to this task ?");
        System.out.println("write the full name of the category");
        System.out.println(iface.getListCategories());

        String cat = sc.nextLine();

        System.out.println("add name of the task");
        String name = sc.nextLine();

        System.out.println("add description");
        String description = sc.nextLine();

        System.out.println("set priority");
        System.out.println(iface.getListPriority());
        String priority = sc.nextLine().toUpperCase();

        System.out.println("set date ( format dd.mm.yyyy )");
        String string = sc.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        try {
            LocalDate date = LocalDate.parse(string, formatter);

            boolean isDone = false;

            if (iface.isViableCategory(cat) && iface.isViablePriority(priority)) {
                Task temp = new Task(cat, name, description, priority, date, isDone);
                iface.addTask(iface.getTaskList(), temp);
            } else if (!iface.isViableCategory(cat)) {
                System.out.println("invalid selection of category");
            } else {
                System.out.println("invalid selection of priority");
            }

        } catch (DateTimeParseException ex) {
            System.out.println("Invalid date format. Enter the date in the format [dd.mm.yyyy].");
        }

    }

    private void removeTask() {

        System.out.println("Enter the task index to remove:");
        System.out.println(taskSelectonView());
        int index = getInputAsInt(sc);
        if (index > 0 && index <= iface.getTaskList().size()) {
        if ( iface.getTaskOnIndex(index) != null  ) {
            iface.removeTask(iface.getTaskList(), index);
            System.out.println("Task removed.");
        } else {
            System.out.println("Invalid index try again.");
        }
        }else {System.out.println("invalid index");}
    }

    private void updateTask() throws ParseException {
        List<Task> tasks = iface.getTaskList();
        if (!tasks.isEmpty()) {
            Collections.sort(tasks, new CompletionComparator(new PriorityComparator()));
            System.out.println(taskSelectonView());
            System.out.println("select task to edit or press enter 0 to exit");
            int index = getInputAsInt(sc);

            if (index != 0 && index <= iface.getTaskList().size() && index > -1) {
                boolean isSelecting = true;
                while (isSelecting) {
                    System.out.println("what would you like to change ?");
                    System.out.println("1. category");
                    System.out.println("2. name");
                    System.out.println("3. description");
                    System.out.println("4. priority");
                    System.out.println("5. date");
                    System.out.println("6. mark as finished/unfinished");
                    System.out.println("7. done");
                    System.out.println("select an option :");

                    int selection = getInputAsInt(sc);
                    switch (selection) {
                        case 1:
                            System.out.println("write (select) new category");
                            System.out.println(iface.getListCategories());
                            String newCat = sc.nextLine();
                            iface.getTaskOnIndex(index).setCategory(newCat);
                            break;
                        case 2:
                            System.out.println("create new name");
                            String newName = sc.nextLine();
                            iface.getTaskOnIndex(index).setName(newName);
                            break;
                        case 3:
                            System.out.println("create new description");
                            String newDescr = sc.nextLine();
                            iface.getTaskOnIndex(index).setDescription(newDescr);
                            break;
                        case 4:
                            System.out.println("write (select) new priority");
                            String newPri = sc.nextLine();
                            iface.getTaskOnIndex(index).setPriority(newPri.toUpperCase());
                            break;
                        case 5:
                            System.out.println("set date ( format dd.mm.yyyy )");
                            String string = sc.nextLine();
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                            try {
                                LocalDate date = LocalDate.parse(string, formatter);
                                iface.getTaskOnIndex(index).setDate(date);
                                break;
                            } catch (DateTimeParseException ex) {
                                Logger.getLogger(ConsoleUi.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            break;
                        case 6:
                            if (iface.getTaskOnIndex(index).isStatus()) {
                                iface.getTaskOnIndex(index).setStatus(false);
                            } else {
                                iface.getTaskOnIndex(index).setStatus(true);
                            }
                            System.out.println("task status updated");
                            break;
                        case 7:
                            isSelecting = false;
                            Collections.sort(tasks, new CompletionComparator(new CategoryComparator()));
                            break;
                        default:
                            System.out.println("not an option");
                            break;

                    }
                }
            }else if(index ==0 ){
                System.out.println("exiting");
            }else {System.out.println("not an option");}
        } else {
            System.out.println("there are no tasks to be edited yet");
        }

    }

    private void TasksDone() {
        System.out.println("select witch task woul you like to mark as done ?");
        System.out.println(taskSelectonView());
        int index = getInputAsInt(sc);
        if (index > 0 && index <= iface.getTaskList().size()) {
            iface.getTaskOnIndex(index).setStatus(true);
        }else {System.out.println("invalid index");}
    }

    private void viewTasks() {
        List<Task> tasks = iface.getTaskList();
        System.out.println(taskSelectonView());

        System.out.println("Sort tasks by:\n1. Priority\n2. Category\npress enter to go back");
        String choice = sc.nextLine();

        if (!choice.equals("")) {

            switch (choice) {
                case "1":
                    Collections.sort(tasks, new CompletionComparator(new PriorityComparator()));
                    break;
                case "2":
                    Collections.sort(tasks, new CompletionComparator(new CategoryComparator()));
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }

            System.out.println(taskSelectonView());

            System.out.println("press enter to go back");
            sc.nextLine();
        }
    }

    private void saveTasksToFile() {
        iface.saveToFile();
        System.out.println("Tasks saved to file.");

    }

    private void loadTasksFromFile() {
        iface.loadFromFile();
        System.out.println("Tasks loaded from file.");

    }

    private void setDataFormat() {
        System.out.println("would you like to change the data format ?");
        System.out.println("current : " + iface.getFormat());
        System.out.println("yes / no");
        String option = getInputYesNo(sc);
        if (option.equals("yes") || option.equals("y")) {
            iface.switchFormat();
            System.out.println("new format is now : " + iface.getFormat());
        }

    }

    private void setDataPath() {
        System.out.println("would you like to change your data folder ?");
        System.out.println("if not the folder will be saved at currently set path : " + iface.getPath());
        System.out.println("note that you can also change the Data folder path in the Config.txt file");
        System.out.println("yes / no");
        String option = getInputYesNo(sc);
        if (option.equals("yes") || option.equals("y")) {
            System.out.println("type in your custom path (default : ././Data)");
            String path = sc.nextLine();
            iface.rewritePath(path);

        }
    }

    private int getInputAsInt(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine();
            if (input.matches("\\d+")) {
                int num = Integer.parseInt(input);
                if (num >= 0) {
                    return num;
                } else {
                    System.out.println("Input must be greater or equal to zero. Please enter a valid value:");
                }
            } else {
                System.out.println("Input must be a number. Please enter a valid value:");
            }
        }
    }

    private String getInputYesNo(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine().toLowerCase();
            if (input.equals("yes") || input.equals("y") || input.equals("n") || input.equals("no")) {
                return input;
            } else {
                System.out.println("Invalid input, please enter 'yes' or 'no'");
            }
        }
    }

    private String taskSelectonView() {
        List<Task> tasks = iface.getTaskList();
        StringBuilder taskTable = new StringBuilder();
        taskTable.append(String.format("%-4s %-15s %-20s %-10s %-12s %-12s %s\n", "ID", "Category", "Name", "Priority", "Date", "Done", "Description"));
        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i);
            taskTable.append(String.format("%-4s %-15s %-20s %-10s %-12s %-12s %s\n", i + 1, t.getCategory(), t.getName(), t.getPriority(), t.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")), t.isStatus() ? "yes" : "no", t.getDescription()));
        }
        return taskTable.toString();
    }

}
