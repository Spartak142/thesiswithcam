
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
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author robindah
 */
public class Camera implements WebcamMotionListener, Runnable {

    private static Webcam webcam;
    private static int i;
    public static String classifierId = "DefaultCustomModel_1716876290";
    private final String testPic = "testpic";
    private FolderZipper zippah = new FolderZipper();

    @Override
    public void motionDetected(WebcamMotionEvent wme) {
        System.out.println("Lego");

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
            ClassifiedImages result = Methods.service.classify(classifyOptions).execute();
            System.out.println(result);
            if (i == 10) {
                zippah.zipFolder("testpics", "zipped");
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            System.out.println("Something is wrong with zipping");
        }

    }

    @Override
    public void run() {
        System.out.println("Camera!");
        WebcamMotionDetector detector = new WebcamMotionDetector(webcam);
        detector.setInterval(1500); // one check per 1500 ms
        detector.addMotionListener(this);
        detector.start();

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
        i = 0;
        File thePics = new File("testpics");
        thePics.mkdir();

        // Start and keep the program open
       

    }

}
