
import com.ibm.watson.developer_cloud.service.security.IamOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.Classifiers;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ListClassifiersOptions;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/*
        To do list:
        
        Take a picture +
        Classify picture +
        Show results on screen
        Wait for users to click
        Register users choice 
        Add users choice as category for the taken picture to a buffer --> TrainObject class
        "print out" the ticket that they will use to scan
        
       ...
        ...
        
        End of the day:
        
        Need some sort of error check on the buffered pictures in case they are wrong
        Send all of those pictures to IBM to improve api.     
        
 */
public class Main {

    final static String apikey = "arK3RfX0XWFFUdA_ES6TZ8-Yb1bnC-sTeNxo4BEAm1Jy";

    public static void main(String[] args) throws FileNotFoundException, IOException, Exception {
        //SimpleUI ui=new SimpleUI();
        //ui.setVisible(true);

        String path = "C:/Users/Robin/Desktop/Skolarbete/Thesis/Data/mango/images.jpg";
        //ArrayList<ClassifiedObject> test = Methods.classifyImage(path);
        //ArrayList<ClassifiedObject> urltest = Methods.classifyURL("https://d2lnr5mha7bycj.cloudfront.net/product-image/file/large_91e6ebd6-fb26-4320-bc37-2003de8b54ce.jpg");
        //ArrayList<ClassifiedObject> cameratest = Methods.classifyCamera();

        System.out.println(getClassifiers().toString());

        
        
        FolderZiper.zipFolder("C:\\Users\\Robin\\Desktop\\zip", "C:\\Users\\Robin\\Desktop\\zip.zip");
        
    }

    public static ArrayList<String> getClassifiers() {
        IamOptions options = new IamOptions.Builder()
                .apiKey(apikey)
                .build();

        VisualRecognition service = new VisualRecognition("2018-03-19", options);

        ListClassifiersOptions listClassifiersOptions = new ListClassifiersOptions.Builder()
                .verbose(true)
                .build();
        Classifiers classifiers = service.listClassifiers(listClassifiersOptions).execute();

        ArrayList<String> result = new ArrayList<>();
        for (com.ibm.watson.developer_cloud.visual_recognition.v3.model.Class c : classifiers.getClassifiers().get(0).getClasses()) {
            result.add(c.getClassName());
        }

        return result;
    }

}
