
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamMotionDetector;
import com.github.sarxos.webcam.WebcamMotionEvent;
import com.github.sarxos.webcam.WebcamMotionListener;
import com.github.sarxos.webcam.WebcamResolution;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifiedImages;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyOptions;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Camera implements WebcamMotionListener, Runnable {

    public static Webcam webcam;
    public static int i;
    public static String classifierId = "DefaultCustomModel_1716876290";
    public final String testPic = "testpic";
    public File sessionImages;
    public FolderZipper zippah = new FolderZipper();
    DateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmss");
    public static volatile ArrayList<ClassifiedObject> arrayOfResults;
    public static volatile boolean temp;
    private static String imageName;
    public static String watsonGuess;
    public static ClassifiedObject current;
    public static ClassifiedObject previous;

    @Override
    public void motionDetected(WebcamMotionEvent wme) {
        previous = current;

        //taking a pic and putting it into the folder for pics.
        System.out.println(sessionImages.listFiles().length);
        BufferedImage image = webcam.getImage();
        imageName = "sessionImages/" + "sessionImage_" + dateFormat.format(new Date()) + ".png";
        System.out.println(imageName);
        //i++;
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

            //THIs has to be done for each class
            //if (sessionImages.listFiles().length > 10) {
            //zippah.zipFolder("sessionImages", "zipped");
            //}
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
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }

        //System.out.println(arrayOfResults.toString());
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

    public String Camera() {
        run();
        return "asd";
    }

    @Override
    public void run() {
        synchronized (this) {
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
            sessionImages = new File("sessionImages");
            sessionImages.mkdir();

            // Start and keep the program open
            WebcamMotionDetector detector = new WebcamMotionDetector(webcam);
            detector.setInterval(200); // one check per 1500 ms
            detector.addMotionListener(this);
            detector.start();
            temp = false;
            while (!temp) {

            }

            detector.stop();
            webcam.close();

            System.out.println("Camera done");
            Thread.currentThread().interrupt();
        }
    }

}
