package app;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * The Task class represents a task with its variables. It implements the
 * Serializable interface to support object serialization and the Comparable
 * interface to enable sorting of tasks.
 */
public class Task implements Serializable, Comparable<Task> {

    private String category;
    private String name;
    private String description;
    private String priority;
    private LocalDate date;
    private boolean status;

    /**
     * Constructs a new Task object with the specified attributes.
     *
     * @param category the category of the task
     * @param name the name of the task
     * @param description the description of the task
     * @param priority the priority of the task
     * @param date the date of the task
     * @param status the status of the task (finished / not Finished)
     */
    public Task(String category, String name, String description, String priority, LocalDate date, boolean status) {
        this.category = category;
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.date = date;
        this.status = status;
    }

    /**
     * Gets the category of the task.
     *
     * @return the category of the task
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the category of the task.
     *
     * @param category the category of the task
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Gets the name of the task.
     *
     * @return the name of the task
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the task.
     *
     * @param name the name of the task
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the description of the task.
     *
     * @return the description of the task
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the task.
     *
     * @param description the description of the task
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the priority of the task.
     *
     * @return the priority of the task
     */
    public String getPriority() {
        return priority;
    }

    /**
     * Sets the priority of the task.
     *
     * @param priority the priority of the task
     */
    public void setPriority(String priority) {
        this.priority = priority;
    }

    /**
     * Gets the date of the task.
     *
     * @return the date of the task
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Sets the date of the task.
     *
     * @param date the date of the task
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Checks if the task is marked as completed.
     *
     * @return true if the task is completed, false otherwise
     */
    public boolean isStatus() {
        return status;
    }

    /**
     * Sets the status of the task.
     *
     * @param status the status of the task
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * Compares this task with the specified task based on their dates. This
     * method is used for sorting tasks in ascending order of dates.
     *
     * @param o the task to be compared
     * @return a negative integer if this task is before the specified task in
     * terms of dates, zero if both tasks have the same date, or a positive
     * integer if this task is after the specified task in terms of dates
     */
    @Override
    public int compareTo(Task o) {
        return this.date.compareTo(o.getDate());
    }
}
