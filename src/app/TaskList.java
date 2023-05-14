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
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.SerializationUtils;
import utils.IToDoList;

/**
 *
 * @author tomir
 */
public class TaskList implements IToDoList {

    private List<Task> tasks = new ArrayList<Task>();
    private Properties config = new Properties();

    public TaskList() {
        tasks = new ArrayList<Task>();
    }

    @Override
    public List getTaskList() {
        return tasks;
    }

    @Override
    public Task getTaskOnIndex(int index) {
        List<Task> temp = List.copyOf(tasks);
        return temp.get(index - 1);
    }

    @Override
    public boolean isViableCategory() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean isViablePriority() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

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
                System.err.println("Failed to create file: " + ex.getMessage());
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

    @Override
    public void addTask(List<Task> tList, Task task) {
        tList.add(task);
    }

    @Override
    public void removeTask(List<Task> tList, int index) {
        tList.remove(index - 1);
    }

    @Override
    public void rewritePath(String path) {

        try {
            config.load(new FileReader("src/utils/config.txt"));
            config.setProperty("data_directory", path);
            config.store(new FileOutputStream("src/utils/config.txt"), null);
        } catch (IOException ex) {
            Logger.getLogger(TaskList.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public String getPath() {
        try {
            config.load(new FileReader("src/utils/config.txt"));
        } catch (IOException ex) {
            Logger.getLogger(TaskList.class.getName()).log(Level.SEVERE, null, ex);
        }
        return config.getProperty("data_directory");
    }

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

    @Override
    public String getFormat() {
        try {
            config.load(new FileReader("src/utils/config.txt"));
        } catch (IOException ex) {
            Logger.getLogger(TaskList.class.getName()).log(Level.SEVERE, null, ex);
        }
        return config.getProperty("data_format");
    }
}
