
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamMotionDetector;
import com.github.sarxos.webcam.WebcamMotionEvent;
import com.github.sarxos.webcam.WebcamMotionListener;
import com.github.sarxos.webcam.WebcamResolution;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifiedImages;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyOptions;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

/*
This project used the Generic Webcam Java API created by sarxos

Copyright (C) 2012 - 2015 Bartosz Firyn (https://github.com/sarxos)

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 */
public class Camera extends javax.swing.JFrame implements WebcamMotionListener, Runnable {

    public static Webcam webcam;
    public static int i;
    public static String classifierId = "CompleteModel_35852773";
    public final String testPic = "testpic";
    public File sessionImages;
    public FolderZipper zippah = new FolderZipper();
    public static volatile ArrayList<ClassifiedObject> arrayOfResults;
    public static volatile boolean temp;
    private static String imageName;
    public static String watsonGuess;
    public static ClassifiedObject current;
    public static ClassifiedObject previous;

    @Override
    public void motionDetected(WebcamMotionEvent wme) {
        //previous and current taken picture.
        previous = current;

        //taking a pic and putting it into the folder for pics.
        BufferedImage image = webcam.getImage();
        imageName = "sessionImages/current.png";
        try {
            ImageIO.write(image, "PNG", new File(imageName));
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Sending the taken pic for processing
        InputStream imagesStream;
        try {
            imagesStream = new FileInputStream(imageName);
            ClassifyOptions classifyOptions = new ClassifyOptions.Builder()
                    .imagesFile(imagesStream)
                    .imagesFilename(imageName)
                    .threshold((float) 0)
                    .classifierIds(Arrays.asList(classifierId))
                    .build();
            // Geting the results
            ClassifiedImages result = Methods.service.classify(classifyOptions).execute();
            current = Methods.JSONToArray(result).get(0);

            if (previous != null) {
                acceptedClassification();

                if (temp) {
                    //Sends the results to UI
                    UI.resultFromCamera = Methods.JSONToArray(result);
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException ex) {
            System.out.println("Something is wrong with zipping");
        } catch (RuntimeException ex) {
            System.out.println(ex.toString());
        }
    }

    //This checks if the past 2 values are the same
    public static void acceptedClassification() {
        double a = current.getValue();
        double b = previous.getValue();
        double diff = Math.abs(a - b);
        System.out.println("abs: " + diff + "a: " + current.toString() + ",b: " + previous.toString());
        if (diff < 0.1 && (current.getName().equals(previous.getName()))) {
            System.out.println("temp is true now");
            //Sets the variable in UI to the correct name 
            UI.watsonGuess = current.getName();
            temp = true;
        }
    }

    @Override
    public void run() {
            //Gets screen size
        //Here it waits for the image to be taken
        //Closes on exit
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //Fullscreen
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        //Removes the bars so looks like fullscreen
        //setUndecorated(true);
        //To like warp up all components.
        //Make it visible
        // this.setVisible(true);
            System.out.println("asdasd");
            arrayOfResults = new ArrayList<ClassifiedObject>();

            Dimension[] nonStandardResolutions = new Dimension[]{
                WebcamResolution.HD720.getSize(),
                new Dimension(2000, 1000),
                new Dimension(1000, 500),};
            //Webcam initialisation
            webcam = Webcam.getWebcams().get(0);
            webcam.setCustomViewSizes(nonStandardResolutions);
            webcam.setViewSize(WebcamResolution.HD720.getSize());
            webcam.open();

            //For naming the pictures
            sessionImages = new File("Experiment 1");
            sessionImages.mkdir();

            BufferedImage image = webcam.getImage();

            JLabel a = new JLabel(new ImageIcon(image), SwingConstants.CENTER);
            a.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
            add(a);
            setVisible(true);
            while (true) {
                try {
                    System.out.println("Hey");
                    sleep(1000);
                    image = webcam.getImage();
                    remove(a);
                    a = new JLabel(new ImageIcon(image), SwingConstants.CENTER);
                    a.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
                    add(a);
                    setVisible(true);
                     repaint();

                } catch (InterruptedException ex) {
                    Logger.getLogger(Camera.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        
    }

}
