package main.java;

import java.util.List;
import java.util.ArrayList;

public class TaskManager {
    private List<Task> tasks;

    public TaskManager(){
        this.tasks = new ArrayList<Task>();
    }

    public void addTask(String taskName) {
        this.tasks.add(new Task(taskName));
    }

    @Override
    public String toString() {
        String output = "";
        for (int i = 1; i <= tasks.size(); i++) {
            output += i + ". " + tasks.get(i-1).toString();
            if (i != tasks.size()) {
                output += "\n";
            }
        }
        return output;
    }
}