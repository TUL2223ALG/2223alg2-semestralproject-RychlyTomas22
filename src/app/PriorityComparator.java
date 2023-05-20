package app;

import java.util.Comparator;

/**
 * Compares two tasks based on their priority levels. The priority levels are
 * compared in the following order HIGH MEDIUM LOW NONE.
 * Tasks with a higher priority level are considered greater than tasks with a lower priority level.
 * 
 *
 * @author tomir
 */
public class PriorityComparator implements Comparator<Task> {

    /**
     * Compares the priorities of two tasks.
     *
     * @param t1 the first task to compare
     * @param t2 the second task to compare
     * @return a negative integer if t1 has a higher priority than t2, zero if
     * their priorities are equal, and a positive integer if t1 has a lower
     * priority than t2
     * @throws IllegalArgumentException if the priority of either task is
     * invalid
     */
    @Override
    public int compare(Task t1, Task t2) {
        String priority1 = t1.getPriority();
        String priority2 = t2.getPriority();

        return Integer.compare(getLevel(priority1), getLevel(priority2));
    }

    /**
     * Converts a priority string into a corresponding level.
     *
     * @param priority the priority string to convert
     * @return the corresponding priority level
     * @throws IllegalArgumentException if the priority string is invalid
     */
    private int getLevel(String priority) {
        switch (priority) {
            case "NONE":
                return 3;
            case "LOW":
                return 2;
            case "MEDIUM":
                return 1;
            case "HIGH":
                return 0;
            default:
                throw new IllegalArgumentException("Invalid priority: " + priority);
        }
    }
}
