package app;

import java.util.Comparator;

/**
 *
 * @author tomir
 */
public class PriorityComparator implements Comparator<Task> {

    @Override
    public int compare(Task o1, Task o2) {
        String Pri1 = o1.getPriority();
        String Pri2 = o2.getPriority();

        if (Pri1.equals(Pri2)) {
            return o1.getDate().compareTo(o2.getDate());
        } else {
            if (Pri1.equals("High")) {
                return -1;
            } else if (Pri2.equals("High")) {
                return 1;
            } else if (Pri1.equals("Medium")) {
                return -1;
            } else if (Pri2.equals("Medium")) {
                return 1;
            } else {
                return -1;
            }
        }
    }
}
