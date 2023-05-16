package utils;

import app.Task;
import java.util.List;

/**
 *
 * @author tomir
 */
public interface IToDoList {

    public void addTask(List<Task> tList, Task task);
    
    public List getTaskList();
    
    public  String getListCategories();
    
    public String getListPriority();
    
    public Task getTaskOnIndex(int index);
    
    public boolean isViableCategory(String input);
    
    public boolean isViablePriority(String input);
    
    public void removeTask(List<Task> tList, int index);

    public void loadFromFile();

    public void saveToFile();
    
    public void rewritePath(String path);
    
    public String getPath();
    
    public void switchFormat();
    
    public String getFormat();
    
}
