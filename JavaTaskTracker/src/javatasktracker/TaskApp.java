/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javatasktracker;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author marti
 */
public class TaskApp {

    //need to add while loop to keep app running unti i type exit
    //declare array list, scanner and object mapper for json
    ArrayList<Task> toDoList = new ArrayList<>();
    Scanner sc = new Scanner(System.in);
    ObjectMapper mapper = new ObjectMapper();

    public void run() {
        mapper.registerModule(new JavaTimeModule());
        loadFile();
        //after declaring the array list and scanner I will accept an input
        System.out.println("Enter a command 'add/update/delete/mark/list'");
        String input = sc.nextLine();
        while (!input.equalsIgnoreCase("exit")) {

            //if the input is add I'll prompt the user to ask 
            if (input.equalsIgnoreCase("add")) {
                add();
            } else if (input.equalsIgnoreCase("update")) {
                update();
            } //delete feature
            else if (input.equalsIgnoreCase("delete")) {
                delete();
            } //list feature
            else if (input.equalsIgnoreCase("list")) {
                list();
                //mark feature
            } else if (input.equalsIgnoreCase("mark")) {
                mark();
            } else {
                System.out.println("INVALID COMMAND - make sure you type in 'add/update/delete/mark/list'!");
            }
            saveFile();

            System.out.println("Enter 'add/update/delete/mark/list' or 'exit' to close..");
            input = sc.nextLine();
        }
    }

    private void add() {
        System.out.println("Type the task you would like to add and hit enter...");
        String taskDescription = sc.nextLine();
        //because the arraylist first index is 0 and we are adding to end I will use the size which is 0 now and add 1 so the first id starts at 1
        int taskId = toDoList.size() + 1;
        String taskStatus = "toDo";
        //localdate time because it looks better than currentTimeMillis
        LocalDateTime taskCreatedAt = LocalDateTime.now();
        LocalDateTime taskUpdatedAt = LocalDateTime.now();
        //create new object and add it to my to do list with confirmation message
        Task newTask = new Task(taskId, taskDescription, taskStatus, taskCreatedAt, taskUpdatedAt);
        toDoList.add(newTask);
        System.out.println("Task has been added successfully!");

    }

    private void mark() {
        //check if there is anything in the list first
        if (toDoList.isEmpty()) {
            System.out.println("Cant mark any tasks if the list is empty!");
        } else {
            System.out.println("Enter the id of the task you want to change it's status..");
            int taskId = sc.nextInt();
            //to make sure we can still accept nextLine input
            sc.nextLine();
            boolean taskFound = false;
            //get the task object from the array list if the id exists (we will traverse the array for this) and then set new status 
            for (int i = 0; i < toDoList.size(); i++) {
                //make a task object for the item we get at i to make it easier
                Task retrievedTask = toDoList.get(i);
                // if the task get id matches the user inputed id then this logic runs
                if (retrievedTask.getId() == taskId) {
                    System.out.println("TASK FOUND IN LIST - mark the task as 'inProgress/done'");

                    String updatedStatus = sc.nextLine();
                    if (updatedStatus.equalsIgnoreCase("inProgress") || updatedStatus.equalsIgnoreCase("done")) {
                        //we update the status and the updated time
                        retrievedTask.setStatus(updatedStatus);
                        retrievedTask.setUpdatedAt(LocalDateTime.now());
                        //confirmation message
                        System.out.println("Task with ID: " + retrievedTask.getId() + " updated successfully!!\n New Status: " + retrievedTask.getStatus() + "\n Updated At: " + retrievedTask.getUpdatedAt());
                    } else {
                        System.out.println("INVALID INPUT, MAKE SURE IT MATCHES THE PROMPT ABOVE!!!");
                    }

                    //the boolean value is used incase we do not find the id in the arraylist
                    taskFound = true;
                    //break out of the loop
                    break;
                }
            }
            if (!taskFound) {
                System.out.println("The id you have entered doesn't exist!");
            }
        }

    }

    private void list() {
        System.out.println("Enter the option you would like to list 'all/toDo/inProgress/done'");
        //accepts input
        String listInput = sc.nextLine();
        //if list is empty displays message accordingly
        if (toDoList.isEmpty()) {
            System.out.println("Cannot list anything if the list is empty!!");
        } //if list is not empty it will accept different inputs all/toDo/inProgress/done and error handling if none of them are typed
        else {
            if (listInput.equalsIgnoreCase("all")) {
                for (int i = 0; i < toDoList.size(); i++) {
                    System.out.println(toDoList.get(i).toString());
                    System.out.println("****************");
                }
            } else if (listInput.equalsIgnoreCase("toDo")) {
                for (int i = 0; i < toDoList.size(); i++) {
                    if (toDoList.get(i).getStatus().equalsIgnoreCase("todo")) {
                        System.out.println(toDoList.get(i).toString());
                        System.out.println("****************");

                    }
                }
            } else if (listInput.equalsIgnoreCase("inProgress")) {
                for (int i = 0; i < toDoList.size(); i++) {
                    if (toDoList.get(i).getStatus().equalsIgnoreCase("inProgress")) {
                        System.out.println(toDoList.get(i).toString());
                        System.out.println("****************");

                    }
                }
            } else if (listInput.equalsIgnoreCase("done")) {
                for (int i = 0; i < toDoList.size(); i++) {
                    if (toDoList.get(i).getStatus().equalsIgnoreCase("done")) {
                        System.out.println(toDoList.get(i).toString());
                        System.out.println("****************");

                    }
                }
            } else {
                System.out.println("INVALID INPUT, TRY AGAIN AND MAKE SURE YOU TYPE IT THE SAME AS THE PROMPT!!!");
            }

        }

    }

    private void update() {
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

    private void delete() {
        if (toDoList.isEmpty()) {
            System.out.println("The To Do List is empty, you cannot remove from it!");
        } else {
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
            for (int i = 0; i < toDoList.size(); i++) {
                if (toDoList.get(i).getId() == taskId) {
                    toDoList.remove(i);
                    System.out.println("The task with the id: " + taskId + " was removed successfully!");
                    //to readjust task ids

                    /* for(int readjustId = taskId; readjustId <=toDoList.size();readjustId++){
                                /*TESTING
                                we will get the task object in front of the one we removed
                                    we will then set the id as the current readjustid and this will increment on each iteration
                                DIDNT WORK WILL TRY WHILE LOOP AND START FROM END OF LIST
                                toDoList.get(readjustId).setId(readjustId);
                            }
                     */
                    //will try a while loop and start from the end working my way back readjusting the ids
                    //will decrement endIndex var at the end til it reaches the end
                    /*
                           TEST SUCCESS AFTER SOME MINOR MODIFICATIONS, WOULD HAVE MADE MY LIFE EASIER IF I DID NOT WANT THE FIRST ID TO START WITH 1
                           CRAFTSMANSHIP AND ALL THAT
                     */
                    int endIndex = toDoList.size() - 1;
                    int decreaseIndex = toDoList.size();
                    while (endIndex != -1) {
                        toDoList.get(endIndex).setId(decreaseIndex);
                        endIndex--;
                        decreaseIndex--;
                    }

                    //break to exit the loop
                    taskFound = true;
                    break;
                }
            }
            if (!taskFound) {
                System.out.println("The id you have entered doesn't exist!");
            }

        }

    }

    private void saveFile() {
        //file saved

        //new Json serialiaztion
        try {
            mapper.writeValue(new File("tasks.json"), toDoList);
            System.out.println("File saved successfully as JSON..");
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }

        /* OLD SERIALIAZATION try {
            File outFile = new File("output.dat");
            FileOutputStream fStream = new FileOutputStream(outFile);
            ObjectOutputStream oStream = new ObjectOutputStream(fStream);

            oStream.writeObject(toDoList);
            System.out.println("File updated successfully...");
            oStream.close();
        } catch (IOException e) {
            System.out.println(e);
        } */
    }

    private void loadFile() {
        //load a saved file
        try {
            toDoList = mapper.readValue(new File("tasks.json"), mapper.getTypeFactory().constructCollectionType(ArrayList.class, Task.class));
            System.out.println("JSON File loaded successfully...");
        } catch (IOException e) {
            System.out.println(e);
        }

        /* OLD LOAD METHOD try {
            File inFile = new File("output.dat");
            FileInputStream fStream = new FileInputStream(inFile);
            ObjectInputStream oStream = new ObjectInputStream(fStream);

            ArrayList<Task> readToDoList = (ArrayList<Task>) oStream.readObject();
            for (Task t : readToDoList) {
                toDoList.add(t);
            }
            System.out.println("File loaded successfully...");
            oStream.close();
        } catch (IOException e) {
            System.out.println(e);
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        }
         */
    }

}
