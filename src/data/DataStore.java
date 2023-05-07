package data;

import java.util.Arrays;

/**
 *
 * @author tomir
 */
public class DataStore {
    private static String[] categories
            = {"work", "school", "presonal", "shopping"};
    
    public static String[] loadCat() {
        return Arrays.copyOf(categories, categories.length);
    }
}
