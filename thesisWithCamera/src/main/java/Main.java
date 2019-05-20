/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamMotionDetector;
import com.github.sarxos.webcam.WebcamMotionEvent;
import com.github.sarxos.webcam.WebcamMotionListener;
import com.github.sarxos.webcam.WebcamResolution;
import com.ibm.watson.developer_cloud.service.security.IamOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifiedImages;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyOptions;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Main implements WebcamMotionListener {
    public static ScheduledThreadPoolExecutor eventPool;
    public static boolean waitFor2;

    // Constructor for this class that start motion detector
    public Main() throws IOException, InterruptedException {

        //ArrayList<ClassifiedObject> test = Methods.classifyImage(path);
        //ArrayList<ClassifiedObject> urltest = Methods.classifyURL("https://d2lnr5mha7bycj.cloudfront.net/product-image/file/large_91e6ebd6-fb26-4320-bc37-2003de8b54ce.jpg");
        //ArrayList<ClassifiedObject> cameratest = Methods.classifyCamera();
        //This is a pool of threads that are going to be used
        //A Thread for UI
        //A Thread for gathering Data for faster executions/ getting camera. 
        //A Thread for traning the AI. 
        
    }


    public static void main(String[] args) throws IOException, InterruptedException {
        //Creates folders
        /*ArrayList<String> classes = Methods.getClassifiers();
        for (int i = 0; i < classes.size(); i++) {
            System.out.println(classes.get(i));
            new File("classes/" + classes.get(i)).mkdir();
            
        }*/
        /*File dir = new File("classes");
        for(File f : dir.listFiles()){
            System.out.println("Foldername: " + f.getName() + ", Files: " + f.listFiles().length);
        }*/

        
        //eventPool = new ScheduledThreadPoolExecutor(3);

        //Adds the UI as a runnable
        //eventPool.schedule(new UI(), 0, TimeUnit.SECONDS);
        //Adds the camera as a runnaable
        //eventPool.schedule(new Camera(), 0, TimeUnit.SECONDS);

        Runnable UI = new UI();
        Thread UI_Thread = new Thread(UI);
        UI_Thread.start();
        
   
                

        
    }

    public void run() {/*empty*/}

    @Override
    public void motionDetected(WebcamMotionEvent wme) {/*empty*/}

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