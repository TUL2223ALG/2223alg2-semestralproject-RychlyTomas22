package app;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * The Task class represents a task with its variables.
 * It implements the Serializable interface to support object serialization
 * and the Comparable interface to enable sorting of tasks.
 */
public class Task implements Serializable, Comparable<Task>{
    
    private String category;
    private String name;
    private String description;
    private String priority;
    private LocalDate date;
    private boolean status;

    public Task(String category, String name, String description, String priority, LocalDate date, boolean status) {
        this.category = category;
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.date = date;
        this.status = status;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public int compareTo(Task o) {
    return this.date.compareTo(o.getDate());    
    }
}