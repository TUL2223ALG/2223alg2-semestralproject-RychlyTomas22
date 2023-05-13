package app;

import java.util.Comparator;

/**
 *
 * @author tomir
 */
public class CompletionComparator implements Comparator<Task> {

    private Comparator<Task> secondaryComparator;
     private Comparable<Task> secondaryComparable;

    public CompletionComparator(Comparator<Task> secondaryComparator) {
        this.secondaryComparator = secondaryComparator;
    }
    
      public CompletionComparator(Comparable<Task> secondaryComparator) {
        this.secondaryComparable = secondaryComparator;
    }

    @Override
    public int compare(Task o1, Task o2) {
        if (o1.isStatus() && !o2.isStatus()) {
            return 1;
        } else if (!o1.isStatus() && o2.isStatus()) {
            return -1;
        } else {
            return secondaryComparator.compare(o1, o2);
        }
    }
}
