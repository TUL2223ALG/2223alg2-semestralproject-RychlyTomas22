package utils;

import app.Task;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tomir
 */
public interface IToDoList {

    public void addTask(List<Task> tList, Task task);
    
    public List getTaskList();
    
    public  String getListCategories(); // ToDo get from file
    
    public String getListPriority();
    
    public Task getTaskOnIndex(int index);
    
    public boolean isViableCategory();
    
    public boolean isViablePriority();
    
    public void removeTask(List<Task> tList, int index);

    public void loadFromFile();

    public void saveToFile();
    
    public void rewritePath(String path);
    
    public String getPath();
    
    public void switchFormat();
    
    public String getFormat();
    
}
