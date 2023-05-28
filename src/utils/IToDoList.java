package utils;

import app.Task;
import java.util.List;

/**
 * The IToDoList interface defines a set of operations for managing a to-do
 * list.
 */
public interface IToDoList {
    
     /**
     * Saves task with the current date into a txt file in a readable format
     */
    public void saveTodaysTasks();
    
    /**
     * Adds a task to the task list.
     *
     * @param tList the task list
     * @param task the task to be added
     */
    public void addTask(List<Task> tList, Task task);

    /**
     * Retrieves the task list.
     *
     * @return the list of tasks
     */
    public List getTaskList();

    /**
     * Retrieves the list of categories as String.
     *
     * @return the list of categories in String format
     */
    public String getListCategories();

    /**
     * Retrieves the list of priorities as String.
     *
     * @return the list of priorities in String format
     */
    public String getListPriority();

    /**
     * Retrieves the task on specific index in the task list.
     *
     * @param index the index of the task
     * @return the task at the specified index
     */
    public Task getTaskOnIndex(int index);

    /**
     * Checks if the specified input is a viable category.
     *
     * @param input the input to be checked
     * @return true if the input is a viable category, false otherwise
     */
    public boolean isViableCategory(String input);

    /**
     * Checks if the specified input is a viable priority.
     *
     * @param input the input to be checked
     * @return true if the input is a viable priority, false otherwise
     */
    public boolean isViablePriority(String input);

    /**
     * Removes a task from the task list.
     *
     * @param tList the task list
     * @param index the index of the task to be removed
     */
    public void removeTask(List<Task> tList, int index);

    /**
     * Loads tasks from a file based on the configured data format.
     */
    public void loadFromFile();

    /**
     * Saves tasks to a file based on the configured data format.
     */
    public void saveToFile();

    /**
     * Rewrites the data path in the configuration file.
     *
     * @param path the new data path
     */
    public void rewritePath(String path);

    /**
     * Retrieves the data path from the configuration file.
     *
     * @return the data path
     */
    public String getPath();

    /**
     * Switches the data format between binary and text.
     */
    public void switchFormat();

    /**
     * Retrieves the current data format from the configuration file.
     *
     * @return the current data format
     */
    public String getFormat();

}
