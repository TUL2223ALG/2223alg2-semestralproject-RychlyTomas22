package app;

import data.DataStore;
import data.Priority;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    public void loadFromFile(String fileName) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void saveToFile(String fileName) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String[] getListCategories() {
        return DataStore.loadCat();
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
