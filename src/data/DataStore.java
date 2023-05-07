/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
