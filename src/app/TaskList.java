package app;

import data.Priority;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.SerializationUtils;
import utils.IToDoList;

/**
 * The TaskList class represents a collection of tasks and provides methods for
 * managing the task list. It implements the IToDoList interface.
 */
public class TaskList implements IToDoList {

    private List<Task> tasks = new ArrayList<Task>();
    private final Properties config = new Properties();

    /**
     * Constructs a new TaskList object. Initializes the tasks list and loads
     * configuration properties.
     */
    public TaskList() {
        tasks = new ArrayList<Task>();
    }

    /**
     * Saves today's tasks to a text file. The tasks are sorted based on their
     * priority before saving.
     */
    public void saveTodaysTasks() {
        File file = new File(getPath() + System.getProperty("file.separator") + "TodaysTasks.txt");
        LocalDate today = LocalDate.now();
        try {
            if (file.createNewFile()) {
                System.out.println("New file created: " + file.getAbsolutePath());
            } else {
                System.out.println("File already exists: " + file.getAbsolutePath());
            }
        } catch (IOException ex) {
            System.out.println("Failed to create file: " + ex.getMessage());
            return;
        }

        PriorityComparator priorityComparator = new PriorityComparator();
        Collections.sort(tasks, priorityComparator);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Task task : tasks) {
                if (task.getDate().equals(today)) {
                    String taskString = "Category: " + task.getCategory() + "\n"
                            + "Name: " + task.getName() + "\n"
                            + "Description: " + task.getDescription() + "\n"
                            + "Priority: " + task.getPriority() + "\n"
                            + "Date: " + task.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + "\n"
                            + "Status: " + (task.isStatus() ? "Completed" : "Pending") + "\n";
                    writer.write(taskString);
                    writer.newLine();
                }
            }
            System.out.println("Tasks saved: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Failed to write tasks to file: " + e.getMessage());
        }
    }

    /**
     * Retrieves the task list.
     *
     * @return the list of tasks
     */
    @Override
    public List getTaskList() {
        return tasks;
    }

    /**
     * Retrieves the task on specific index in the task list.
     *
     * @param index the index of the task
     * @return the task at the specified index
     */
    @Override
    public Task getTaskOnIndex(int index) {
        List<Task> temp = List.copyOf(tasks);
        return temp.get(index - 1);
    }

    /**
     * Checks if the specified input matches one of the saved categories.
     *
     * @param input the input to be checked
     * @return true if the input matches category saved in Categories.txt file,
     * false otherwise
     */
    @Override
    public boolean isViableCategory(String input) {
        File file = new File("src/utils/Categories.txt");
        if (!file.exists()) {
            System.out.println("File does not exist: " + file.getAbsolutePath());
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            ArrayList<String> words = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                words.add(line);
            }

            for (String category : words) {
                if (category.equalsIgnoreCase(input)) {
                    return true;
                }
            }

            return false;

        } catch (IOException ex) {
            System.out.println("failed tor read Categories.txt ");
            return false;
        }
    }

    /**
     * Checks if the specified input matches priority inside Priority enum
     * class.
     *
     * @param input the input to be checked
     * @return true if the input is a viable priority, false otherwise
     */
    @Override
    public boolean isViablePriority(String input) {
        try {
            Priority.valueOf(input.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }

    }

    /**
     * Loads tasks from a file based on the configured data format.
     */
    @Override
    public void loadFromFile() {

        if (getFormat().equals("binary")) {
            File file = new File(getPath() + System.getProperty("file.separator") + "Tasks.dat");

            if (!file.exists()) {
                System.out.println("File does not exist: " + file.getAbsolutePath());
                return;
            }

            tasks.removeAll(tasks);
            try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file))) {
                tasks = (ArrayList<Task>) SerializationUtils.deserialize(inputStream);
                System.out.println("Tasks loaded: " + file.getAbsolutePath());
            } catch (IOException e) {
                System.err.println("Failed to read from file: " + e.getMessage());
            }
        } else if (getFormat().equals("txt")) {

            String filePath = getPath() + System.getProperty("file.separator") + "Tasks.txt";
            File file = new File(filePath);

            if (!file.exists()) {
                System.out.println("File does not exist: " + file.getAbsolutePath());
                return;
            }

            tasks.removeAll(tasks);
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] taskData = line.split(" ; ");
                    String category = taskData[0];
                    String name = taskData[1];
                    String description = taskData[2];
                    String priority = taskData[3];
                    LocalDate date = LocalDate.parse(taskData[4]);
                    boolean status = Boolean.parseBoolean(taskData[5]);

                    Task task = new Task(category, name, description, priority, date, status);
                    tasks.add(task);
                }
                System.out.println("Tasks loaded from file: " + file.getAbsolutePath());
            } catch (IOException e) {
                System.err.println("Failed to read from file: " + e.getMessage());
            }
        } else {
            System.out.println("invalid format please switch to a different one");
        }

    }

    /**
     * Saves tasks to a file based on the configured data format.
     */
    @Override
    public void saveToFile() {

        if (getFormat().equals("binary")) {
            //save as binary
            File file = new File(getPath() + System.getProperty("file.separator") + "Tasks.dat");
            try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file))) {
                SerializationUtils.serialize((Serializable) tasks, outputStream);
                System.out.println("Tasks saved : " + file.getAbsolutePath());
            } catch (IOException e) {
                System.err.println("Failed to write tasks to file: " + e.getMessage());
            }

        } else if (getFormat().equals("txt")) {
            // save as txt
            File file = new File(getPath() + System.getProperty("file.separator") + "Tasks.txt");
            try {
                if (file.createNewFile()) {
                    System.out.println("New file created: " + file.getAbsolutePath());
                } else {
                    System.out.println("File already exists: " + file.getAbsolutePath());
                }
            } catch (IOException ex) {
                System.out.println("Failed to create file: " + ex.getMessage());

                return;
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (Task task : tasks) {
                    String taskString = task.getCategory() + " ; " + task.getName() + " ; " + task.getDescription() + " ; " + task.getPriority() + " ; " + task.getDate() + " ; " + task.isStatus();
                    writer.write(taskString);
                    writer.newLine();
                }
                System.out.println("Tasks saved: " + file.getAbsolutePath());
            } catch (IOException e) {
                System.err.println("Failed to write tasks to file: " + e.getMessage());
            }
        } else {
            System.out.println("invalid format please switch to a different one");
        }
    }

    /**
     * Retrieves the list of categories as String.
     *
     * @return the list of categories in String format
     */
    @Override
    public String getListCategories() {
        File file = new File("src/utils/Categories.txt");
        if (!file.exists()) {
            System.out.println("File does not exist: " + file.getAbsolutePath());
            return "";
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            ArrayList<String> words = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                words.add(line);
            }
            return String.join(" | ", words.toArray(new String[0]));
        } catch (IOException e) {
            System.err.println("Failed to read file: " + e.getMessage());
            return "";
        }
    }

    /**
     * Retrieves the list of priorities as String.
     *
     * @return the list of priorities in String format
     */
    @Override
    public String getListPriority() {

        Priority[] values = Priority.values();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            if (i > 0) {
                sb.append("     ");
            }
            sb.append("|" + values[i].toString() + "|");
        }
        return sb.toString();

    }

    /**
     * Adds a task to the task list.
     *
     * @param tList the task list
     * @param task the task to be added
     */
    @Override
    public void addTask(List<Task> tList, Task task) {
        tList.add(task);
    }

    /**
     * Removes a task from the task list.
     *
     * @param tList the task list
     * @param index the index of the task to be removed
     */
    @Override
    public void removeTask(List<Task> tList, int index) {
        tList.remove(index - 1);
    }

    /**
     * Rewrites the data path in the configuration file.
     *
     * @param path the new data path
     */
    @Override
    public void rewritePath(String path) {

        try {
            config.load(new FileReader("src/utils/config.txt"));
            File file = new File(path);
            if (file.exists()) {
                config.setProperty("data_directory", path);
                config.store(new FileOutputStream("src/utils/config.txt"), null);
                System.out.println("new path : " + path);
            } else {
                System.out.println("file does not exist and path remains unchanged");
            }
        } catch (IOException ex) {
            Logger.getLogger(TaskList.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Retrieves the data path from the configuration file.
     *
     * @return the data path
     */
    @Override
    public String getPath() {
        try {
            config.load(new FileReader("src/utils/config.txt"));
        } catch (IOException ex) {
            Logger.getLogger(TaskList.class.getName()).log(Level.SEVERE, null, ex);
        }
        return config.getProperty("data_directory");
    }

    /**
     * Switches the data format between binary and text.
     */
    @Override
    public void switchFormat() {
        try {
            config.load(new FileReader("src/utils/config.txt"));
            String format = config.getProperty("data_format");
            if (format.equals("txt")) {
                config.setProperty("data_format", "binary");
            } else {
                config.setProperty("data_format", "txt");
            }
            config.store(new FileOutputStream("src/utils/config.txt"), null);
        } catch (IOException ex) {
            Logger.getLogger(TaskList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Retrieves the current data format from the configuration file.
     *
     * @return the current data format
     */
    @Override
    public String getFormat() {
        try {
            config.load(new FileReader("src/utils/config.txt"));
        } catch (IOException ex) {
            System.err.println("Error: Failed to read the configuration file.");
            ex.printStackTrace();
        }
        return config.getProperty("data_format");
    }
}
