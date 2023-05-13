package app;

/**
 *
 * @author tomir
 */
import java.util.Comparator;

public class CategoryComparator implements Comparator<Task> {

    @Override
    public int compare(Task t1, Task t2) {
        return t1.getCategory().compareTo(t2.getCategory());
    }
}
