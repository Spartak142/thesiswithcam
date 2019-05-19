/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;

import com.github.sarxos.webcam.WebcamMotionEvent;
import com.github.sarxos.webcam.WebcamMotionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Main implements WebcamMotionListener {
    public static ScheduledThreadPoolExecutor eventPool;
    public static boolean waitFor2;
    public static final File dir = new File("classes");    

    
    public static void main(String[] args) throws IOException, InterruptedException {
       
        initialiseFolders();
        eventPool = new ScheduledThreadPoolExecutor(3);
        
        //Add threads for camera and UI
        eventPool.schedule(new Camera(), 0, TimeUnit.SECONDS);
    
   
    }

    public void run() {/*empty*/}

    @Override
    public void motionDetected(WebcamMotionEvent wme) {/*empty*/}
    
    
    
    /**
     * initialise the folders
     */
    private static void initialiseFolders() {
         //Creates folders
        ArrayList<String> classes = Methods.getClassifiers();         
        dir.mkdir(); 
        for (int i = 0; i < classes.size(); i++) {
            System.out.println(classes.get(i));
          File classFolder=  new File("classes/" + classes.get(i));
          classFolder.mkdir();
        }
    }

}


/*   Shows how many objects in each folder
 for(File f : dir.listFiles()){
            System.out.println("Foldername: " + f.getName() + ", Files: " + f.listFiles().length);
        }
*/

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