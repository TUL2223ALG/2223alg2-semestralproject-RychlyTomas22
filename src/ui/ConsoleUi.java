package ui;

import app.Task;
import app.TaskList;
import java.text.DateFormat;
import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.util.Date;
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
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    private void add() {

        System.out.println("what category would you like to assign to this task ?");
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
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        try {
            Date date = format.parse(string);

            boolean isDone = false;

            Task temp = new Task(cat, name, description, priority, date, isDone);
            iface.addTask(iface.getTaskList(), temp);

        } catch (ParseException ex) {
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
                    DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                    try {
                        Date date = format.parse(string);
                        iface.getTaskOnIndex(index).setDate(date);
                        break;
                    } catch (ParseException ex) {
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
        String a;
        
        List<Task> tasks = iface.getTaskList();
        if (tasks.isEmpty()) {
            System.out.println("No tasks yet.");
        } else {
            System.out.println("All Tasks:");
            for (int i = 0; i < tasks.size(); i++) {
                Task task = tasks.get(i);
                System.out.format("   %d. %-20s [%-10s] | Description: %-45s | Priority: %-10s | Due Date: %-12s | Status: %-10s |\n",
                        i + 1, task.getName(), task.getCategory(), task.getDescription(), task.getPriority(),
                        new SimpleDateFormat("dd/MM/yyyy").format(task.getDate()), task.isStatus() ? "Done" : "Not done");
            }
        }
        System.out.println("type anything to exit");
         sc.nextLine();
         sc.nextLine();
        
    }

    private void saveTasksToFile() {
        System.out.println("Enter the file name to save tasks to:");
        String fileName = sc.nextLine();
        iface.saveToFile(fileName);
        System.out.println("Tasks saved to file.");

    }

    private void loadTasksFromFile() {
        System.out.println("Enter the file name to load tasks from:");
        String fileName = sc.nextLine();
        iface.loadFromFile(fileName);
        System.out.println("Tasks loaded from file.");

    }

}
