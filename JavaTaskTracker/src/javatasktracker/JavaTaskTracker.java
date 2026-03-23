/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package javatasktracker;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author marti
 */
public class JavaTaskTracker {
     


   
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ArrayList<Task> toDoList = new ArrayList<>();       
        Scanner sc = new Scanner(System.in);
        //after declaring the array list and scanner I will accept an input
        System.out.println("Enter a command 'add/update/delete/mark/list");
        String input = sc.nextLine();
        //if the input is add I'll prompt the user to ask 
        if(input.equalsIgnoreCase("add")){
            System.out.println("Type the task you would like to add and hit enter...");
            String taskDescription = sc.nextLine();
            int taskId = toDoList.size() + 1;
            String taskStatus = "to-do";
            LocalDateTime taskCreatedAt = LocalDateTime.now();
            LocalDateTime taskUpdatedAt = LocalDateTime.now();
            Task newTask = new Task(taskId,taskDescription, taskStatus, taskCreatedAt, taskUpdatedAt);
            toDoList.add(newTask);
            System.out.println("Task has been added successfully!");          
        }
        
        System.out.println(toDoList.get(0).toString());
        
        
        
        
    }
    
    
}
