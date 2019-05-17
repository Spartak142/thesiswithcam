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
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Main implements WebcamMotionListener {

    public static VisualRecognition service;
    private static int i;
    public static String classifierId = "DefaultCustomModel_1716876290";
    private static Webcam webcam;
    private final String testPic = "testpic";
    private FolderZipper zippah= new FolderZipper();

    // Constructor for this class that start motion detector
    public Main() {
        WebcamMotionDetector detector = new WebcamMotionDetector(webcam);
        detector.setInterval(1500); // one check per 1500 ms
        detector.addMotionListener(this);
        detector.start();
        
        //UI ui = new UI();

        //ArrayList<ClassifiedObject> test = Methods.classifyImage(path);
        //ArrayList<ClassifiedObject> urltest = Methods.classifyURL("https://d2lnr5mha7bycj.cloudfront.net/product-image/file/large_91e6ebd6-fb26-4320-bc37-2003de8b54ce.jpg");
        //ArrayList<ClassifiedObject> cameratest = Methods.classifyCamera();
        //This is a pool of threads that are going to be used
        //A Thread for UI
        //A Thread for gathering Data for faster executions/ getting camera. 
        //A Thread for traning the AI. 
        //ScheduledThreadPoolExecutor eventPool = new ScheduledThreadPoolExecutor(3);

        //Adds the UI as a runnable
        //eventPool.schedule(new UI(), 0, TimeUnit.SECONDS);
    }

    @Override
    public void motionDetected(WebcamMotionEvent wme) {
        //Initilising API
        IamOptions options = new IamOptions.Builder()
                .apiKey("sP83C9a-mEePlb5-4banZN1EYLSbxavGnpYmOLYGl-v7")
                .build();
        service = new VisualRecognition("2018-03-19", options);

        //taking a pic and putting it into the folder for pics.
        BufferedImage image = webcam.getImage();
        String imgName = "./testpics/" + testPic + i + ".png";
        System.out.println(imgName);
        i++;
        try {
            ImageIO.write(image, "PNG", new File(imgName));
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Sending the taken pic for processing
        InputStream imagesStream;
        try {
            imagesStream = new FileInputStream(imgName);
            ClassifyOptions classifyOptions = new ClassifyOptions.Builder()
                    .imagesFile(imagesStream)
                    .imagesFilename("download.jpg")
                    .threshold((float) 0.6)
                    .classifierIds(Arrays.asList(classifierId))
                    .build();
            // Geting the results
            ClassifiedImages result = service.classify(classifyOptions).execute();
            System.out.println(result);
            if (i==10){
               zippah.zipFolder("testpics","zipped");
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            System.out.println("Something is wrong with zipping");
        }

    }

    public static void main(String[] args) throws IOException {
        Dimension[] nonStandardResolutions = new Dimension[]{
            WebcamResolution.HD720.getSize(),
            new Dimension(2000, 1000),
            new Dimension(1000, 500),};
        //Webcam initialisation
        webcam = Webcam.getDefault();
        webcam.setCustomViewSizes(nonStandardResolutions);
        webcam.setViewSize(WebcamResolution.HD720.getSize());
        webcam.open();

        //For naming the pictures
        i = 0;
        File thePics = new File("testpics");
        thePics.mkdir();

        // Start and keep the program open
        new Main();
        System.in.read();
    }

    @Override
    public void run() {
        
        //Dont konw waht to put here
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