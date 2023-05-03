/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;

import data.DataStore;
import data.Priority;
import java.util.ArrayList;
import java.util.List;
import utils.IToDoList;

/**
 *
 * @author tomir
 */
public class TaskList implements IToDoList {

    private List<Task> tasks = new ArrayList<Task>();

    public TaskList() {
        tasks = new ArrayList<Task>();
    }

    @Override
    public List getTaskList() {
        return tasks;
    }

    @Override
    public Task getTaskOnIndex(int index) {
         List<Task> temp =  List.copyOf(tasks);
         return temp.get(index-1);
        }

    @Override
    public boolean isViableCategory() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean isViablePriority() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }


    @Override
    public void loadFromFile(String fileName) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void saveToFile(String fileName) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String[] getListCategories() {
        return DataStore.loadCat();
    }

    @Override
    public String getListPriority() {

        Priority[] values = Priority.values();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            if (i > 0) {
                sb.append("     ");
            }
            sb.append("|" + values[i].toString() + "|");
        }
        return sb.toString();

    }

    @Override
    public void addTask(List<Task> tList, Task task) {
        tList.add(task);
    }

    @Override
    public void removeTask(List<Task> tList, int index) {
        tList.remove(index-1);
        }

}
