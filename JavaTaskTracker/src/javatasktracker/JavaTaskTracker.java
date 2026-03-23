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
        //need to add while loop to keep app running unti i type exit
        ArrayList<Task> toDoList = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        //after declaring the array list and scanner I will accept an input
        System.out.println("Enter a command 'add/update/delete/mark/list'");
        String input = sc.nextLine();
        while (!input.equalsIgnoreCase("exit")) {

            //if the input is add I'll prompt the user to ask 
            if (input.equalsIgnoreCase("add")) {
                System.out.println("Type the task you would like to add and hit enter...");
                String taskDescription = sc.nextLine();
                //because the arraylist first index is 0 and we are adding to end I will use the size which is 0 now and add 1 so the first id starts at 1
                int taskId = toDoList.size() + 1;
                String taskStatus = "to-do";
                //localdate time because it looks better than currentTimeMillis
                LocalDateTime taskCreatedAt = LocalDateTime.now();
                LocalDateTime taskUpdatedAt = LocalDateTime.now();
                //create new object and add it to my to do list with confirmation message
                Task newTask = new Task(taskId, taskDescription, taskStatus, taskCreatedAt, taskUpdatedAt);
                toDoList.add(newTask);
                System.out.println("Task has been added successfully!");
            } else if (input.equalsIgnoreCase("update")) {
                if (toDoList.isEmpty()) {
                    System.out.println("The To Do List is empty!!");
                } else {
                    System.out.println("Enter the id of the task you want to update..");
                    int taskId = sc.nextInt();
                    /*DEBUGGINGthis is to fix the debugging issue I commented below regarding updatedDesc, testing now 
                        ***TEST SUCCESS****
                    */
                    sc.nextLine();
                    boolean taskFound = false;
                    //get the task object from the array list if the id exists (we will traverse the array for this) and then set a new description 
                    for (int i = 0; i < toDoList.size(); i++) {
                        //make a task object for the item we get at i to make it easier
                        Task retrievedTask = toDoList.get(i);
                        // if the task get id matches the user inputed id then this logic runs
                        if (retrievedTask.getId() == taskId) {
                            System.out.println("TASK FOUND IN LIST, the next line you type will modify the task.");
                            /*
                            DEBUGGING: LINE 62 is being SKIPPED, turns out it is from sc.nextInt() line 49, I will need to add a new 
                            sc.nextLine() after to fix the input
                            ****FIXED*****
                            */
                            String updatedDesc = sc.nextLine();
                            //we update the description and the updated time
                            retrievedTask.setDescription(updatedDesc);
                            retrievedTask.setUpdatedAt(LocalDateTime.now());
                            //confirmation message
                            System.out.println("Task with ID: " + retrievedTask.getId() + " updated successfully!!\n New Task: " + retrievedTask.getDescription() + "\n Updated At: " + retrievedTask.getUpdatedAt());
                            //the boolean value is used incase we do not find the id in the arraylist
                            taskFound = true;
                            //break out of the loop
                            break;
                        }
                    }
                    //if the id is not found in the list we will display a message accordingly
                    if (!taskFound) {
                        System.out.println("The id you have entered does not exist in the list!");
                    }
                    /*MAYBE A WHILE LOOP HERE 
                    int i = 0;
                    boolean taskFound = false;
                    while(!taskFound && i < toDoList.size(){
                    
                *
                     */

                }
            }
            //delete feature
            else if(input.equalsIgnoreCase("delete")){
                if(toDoList.isEmpty()){
                    System.out.println("The To Do List is empty, you cannot remove from it!");
                }
                else{
                    System.out.println("Enter the id of the task you want to remove...");
                    int taskId = sc.nextInt();
                    //this line is to make sure that if we use nextLine again it doesn't break the app
                    sc.nextLine();
                    //loop to match the task id and then remove it ( WILL HAVE TO FIGURE OUT A WAY TO READJUST THE IDS FOR ITEMS AFTER
                    /*
                    MAYBE HAVE A FOR LOOP WITH THE STARTING INT AS THE TASK ID THAT WE REMOVED
                    RETRIEVE THE OBJECT, SET THE NEW ID AS THE TASK ID THAT WAS REMOVED 
                    INCREMENT THE TASK ID OR COPY OF IT AND RETRIEVE AND SET AGAIN UNTIL WE REACH END OF TO DO LIST
                    WILL CHECK TOMORROW IF THIS LOGIC WORKS OR FIND ANOTHER WAY
                    */
                    //boolean value to make sure task is found, if not ERROR MESSAGE
                    boolean taskFound = false;
                    for(int i = 0; i < toDoList.size(); i++){
                        if(toDoList.get(i).getId()==taskId){
                            toDoList.remove(i);
                            System.out.println("The task with the id: " + taskId + " was removed successfully!");
                            //break to exit the loop
                            taskFound = true;
                            break;
                        }
                    }
                    if(!taskFound){
                        System.out.println("The id you have entered doesn't exist!");
                    }
                    
                }
            }
            System.out.println("Enter 'add/update/delete/mark/list' or 'exit' to close");
            input = sc.nextLine();
        }
    }

}
