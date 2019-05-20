/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.File;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import java.util.concurrent.ScheduledThreadPoolExecutor;

public class Main {

    public static ScheduledThreadPoolExecutor eventPool;
    public static boolean waitFor2;
    public static final File dir = new File("classes");
    public static Path file = Paths.get("stats.txt");

    public static int amountOfClasses = 52;

    private static void createFile() {

        try {
            // Create the empty file with default permissions, etc.
            Files.createFile(file);
        } catch (FileAlreadyExistsException x) {
            System.out.println("file named %s"
                    + " already exists%n");
        } catch (IOException x) {
            // Some other sort of failure, such as permissions.
            System.out.println("createFile error: %s%n");
        }
    }

    // Constructor for this class that start motion detector
    public Main() throws IOException, InterruptedException {

    }

    public static void main(String[] args) throws IOException, InterruptedException {
        createFile();
        initialiseFolders();
        Runnable UI = new UI();
        Thread UI_Thread = new Thread(UI);
        UI_Thread.start();

    }

    public void run() {/*empty*/
    }

    /**
     * initialise the folders
     */
    private static void initialiseFolders() {
        //Checks if all folders exist
        if (dir.listFiles().length != amountOfClasses) {
            //Creates folders
            ArrayList<String> classes = Methods.getClassifiers();
            dir.mkdir();
            for (int i = 0; i < classes.size(); i++) {
                File classFolder = new File("classes/" + classes.get(i));
                classFolder.mkdir();
            }
        }
    }

}

/*
        To do list:
        
        Take a picture +
        Classify picture +
        Show results on screen
        Wait for users to click
        Register users choice 
        Add users choice as category for the taken picture to a buffer --> TrainObject class
        "print out" the ticket that they will use to scan
        
       ...asd
        ...
        
        End of the day:
        
        Need some sort of error check on the buffered pictures in case they are wrong
        Send all of those pictures to IBM to improve api.  


    We need different phases of the UI. 
    Startup phase
    Scan phase
    Choose option phase
    
    
    
        
 */
