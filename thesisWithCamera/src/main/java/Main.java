
import com.ibm.watson.developer_cloud.service.security.IamOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.Classifier;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.Classifiers;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.GetClassifierOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ListClassifiersOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.UpdateClassifierOptions;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

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
public class Main implements Runnable {

    //final static String apikey = "arK3RfX0XWFFUdA_ES6TZ8-Yb1bnC-sTeNxo4BEAm1Jy";
    public static void main(String[] args) throws FileNotFoundException, IOException, Exception {
        //UI ui = new UI();

        //ArrayList<ClassifiedObject> test = Methods.classifyImage(path);
        //ArrayList<ClassifiedObject> urltest = Methods.classifyURL("https://d2lnr5mha7bycj.cloudfront.net/product-image/file/large_91e6ebd6-fb26-4320-bc37-2003de8b54ce.jpg");
        //ArrayList<ClassifiedObject> cameratest = Methods.classifyCamera();
        //This is a pool of threads that are going to be used
        //A Thread for UI
        //A Thread for gathering Data for faster executions/ getting camera. 
        //A Thread for traning the AI. 
        ScheduledThreadPoolExecutor eventPool = new ScheduledThreadPoolExecutor(3);

        //Adds the UI as a runnable
        eventPool.schedule(new UI(), 0, TimeUnit.SECONDS);
    }

    @Override
    public void run() {
        
        //Dont konw waht to put here
        }

}
