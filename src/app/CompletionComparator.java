package app;

import java.util.Comparator;

/**
 * A Comparator implementation for comparing tasks based on their completion
 * status. Tasks will be sorted based on their completion status, with completed
 * tasks appearing after incomplete tasks. Additionally, this comparator can
 * utilize a secondary comparator or comparable to further refine the sorting
 * order.
 */
public class CompletionComparator implements Comparator<Task> {

    private Comparator<Task> secondaryComparator;
    private Comparable<Task> secondaryComparable;

    /**
     * Constructs a CompletionComparator with a secondary comparator.
     *
     * @param secondaryComparator the secondary comparator used for further
     * refining the sorting order
     */
    public CompletionComparator(Comparator<Task> secondaryComparator) {
        this.secondaryComparator = secondaryComparator;
    }

    /**
     * Constructs a CompletionComparator with a secondary comparable.
     *
     * @param secondaryComparable the secondary comparable used for further
     * refining the sorting order
     */
    public CompletionComparator(Comparable<Task> secondaryComparable) {
        this.secondaryComparable = secondaryComparable;
    }

/**
 * Compares two tasks based on their completion status. Incomplete tasks are considered greater than completed tasks.
 * If a secondary comparable is provided, it is used for comparison.
 *
 * @param o1 the first task to compare
 * @param o2 the second task to compare
 * @return a negative integer if o1 is completed and o2 is incomplete,
 *         zero if their completion statuses are equal or the secondary comparable returns zero,
 *         and a positive integer if o1 is incomplete and o2 is completed,
 *         or the secondary comparable returns a non-zero value
 */
    @Override
    public int compare(Task o1, Task o2) {
        if (o1.isStatus() && !o2.isStatus()) {
            return 1;
        } else if (!o1.isStatus() && o2.isStatus()) {
            return -1;
        } else if (secondaryComparable != null) {
            return secondaryComparable.compareTo(o1);
        } else {
            return secondaryComparator.compare(o1, o2);
        }
    }
}
