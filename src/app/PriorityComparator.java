package app;

import java.util.Comparator;

/**
 *
 * @author tomir
 */
public class PriorityComparator implements Comparator<Task> {

    @Override
    public int compare(Task t1, Task t2) {
        String priority1 = t1.getPriority();
        String priority2 = t2.getPriority();
        
        return Integer.compare(getLevel(priority1), getLevel(priority2));
    }

    private int getLevel(String priority) {
        switch (priority) {
            case "NONE":
                return 0;
            case "LOW":
                return 1;
            case "MEDIUM":
                return 2;
            case "HIGH":
                return 3;
            default:
                throw new IllegalArgumentException("Invalid priority: " + priority);
        }
    }
}
