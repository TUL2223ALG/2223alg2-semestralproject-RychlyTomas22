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

    public void run() {
        iface = new TaskList();
        setDataPath();
        setDataFormat();
        displayMenu();
        boolean isRunning = true;
        while (isRunning) {
            int choise = sc.nextInt();
            switch (choise) {
                case 1:
                    add();
                    break;
                case 2:
                    removeTask();
                    break;
                case 3: {
                    try {
                        updateTask();
                    } catch (ParseException ex) {
                        Logger.getLogger(ConsoleUi.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
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
                    sc.nextLine();
                    setDataPath();
                    break;
                case 9:
                    sc.nextLine();
                    setDataFormat();
                    break;
                case 0:
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
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    private void add() {

        System.out.println("what category would you like to assign to this task ?");
        System.out.println("write the full name of the category");
        System.out.println(iface.getListCategories());
        sc.nextLine();

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

            Task temp = new Task(cat, name, description, priority, date, isDone);
            iface.addTask(iface.getTaskList(), temp);

        } catch (DateTimeParseException ex) {
            Logger.getLogger(ConsoleUi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void removeTask() {

        System.out.println("Enter the task index to remove:");
        System.out.println(iface.getTaskList());
        int index = sc.nextInt();
        if (iface.getTaskOnIndex(index) != null) {
            iface.removeTask(iface.getTaskList(), index);
            System.out.println("Task removed.");
        } else {
            System.out.println("Invalid index try again.");
        }
    }

    private void updateTask() throws ParseException {
        System.out.println("select task to edit");
        System.out.println(iface.getTaskList());
        int index = sc.nextInt();

        System.out.println("what would you like to change ?");

        System.out.println("1. category");
        System.out.println("2. name");
        System.out.println("3. description");
        System.out.println("4. priority");
        System.out.println("5. date");
        System.out.println("6. mark as finished/unfinished");
        System.out.println("0. done");

        System.out.println("select an option :");
        int selection = sc.nextInt();
        boolean isSelecting = true;
        while (isSelecting) {
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
                    iface.getTaskOnIndex(index).setPriority(newPri);
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
                case 0:
                    isSelecting = false;
                    break;
                default:
                    System.out.println("not an option");
                    break;

            }
        }
    }

    private void TasksDone() {
        System.out.println("select witch task woul you like to mark as done ?");
        System.out.println(iface.getTaskList());
        int index = sc.nextInt();
        iface.getTaskOnIndex(index).setStatus(true);

    }

    private void viewTasks() {
        List<Task> tasks = iface.getTaskList();
        StringBuilder taskTable = new StringBuilder();
        Collections.sort(tasks);
        taskTable.append(String.format("%-4s %-15s %-20s %-10s %-12s %-12s %s\n", "ID", "Category", "Name", "Priority", "Date", "Done", "Description"));
        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i);
            taskTable.append(String.format("%-4s %-15s %-20s %-10s %-12s %-12s %s\n", i, t.getCategory(), t.getName(), t.getPriority(), t.getDate(), t.isStatus() ? "yes" : "no", t.getDescription()));
        }
        System.out.println(taskTable.toString());
        
        System.out.println("Sort tasks by:\n1. Priority\n2. Category\npress enter to go back");
        sc.nextLine();
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

        taskTable = new StringBuilder();
        taskTable.append(String.format("%-4s %-15s %-20s %-10s %-12s %-12s %s\n", "ID", "Category", "Name", "Priority", "Date", "Done", "Description"));
        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i);
            taskTable.append(String.format("%-4s %-15s %-20s %-10s %-12s %-12s %s\n", i, t.getCategory(), t.getName(), t.getPriority(), t.getDate(), t.isStatus() ? "yes" : "no", t.getDescription()));
        }
        System.out.println(taskTable.toString());

        System.out.println("press enter to go back");
        sc.nextLine();
         }
    }

    private void saveTasksToFile() {
        iface.saveToFile();
        System.out.println("Tasks saved to file.");

    }

    private void loadTasksFromFile() {;
        iface.loadFromFile();
        System.out.println("Tasks loaded from file.");

    }

    private void setDataFormat() {
        System.out.println("would you like to change the data format ?");
        System.out.println("default: files will be saved in a [ filename.txt ] format");
        System.out.println("current : " + iface.getFormat());
        System.out.println("yes / no");
        String option = sc.nextLine();
        if (option.equals("yes")) {
            iface.switchFormat();
            System.out.println("new format is now : " + iface.getFormat());

        }

    }

    private void setDataPath() {
        System.out.println("would you like to change your data folder ?");
        System.out.println("if not the folder will be saved at currently set path : " + iface.getPath());
        System.out.println("note that you can also change the Data folder path in the Config.txt file");
        System.out.println("yes / no");
        String option = sc.nextLine();
        if (option.toLowerCase().equals("yes")) {
            System.out.println("type in your custom path (default : ././Data)");
            String path = sc.nextLine();
            iface.rewritePath(path);
            System.out.println("new path : " + path);

        }
    }

}
