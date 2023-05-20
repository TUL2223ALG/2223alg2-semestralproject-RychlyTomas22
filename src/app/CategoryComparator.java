package app;

import java.util.Comparator;

/**
 * A Comparator implementation for comparing tasks based on their category.
 * Tasks will be sorted in ascending order based on the category name.
 */
public class CategoryComparator implements Comparator<Task> {

    /**
     * Compares two tasks based on their category names.
     *
     * @param t1 the first task to compare
     * @param t2 the second task to compare
     * @return a negative integer if t1's category is lexicographically less
     * than t2's category, zero if the categories are equal, and a positive
     * integer if t1's category is lexicographically greater than t2's category
     */
    @Override
    public int compare(Task t1, Task t2) {
        return t1.getCategory().compareTo(t2.getCategory());
    }
}
