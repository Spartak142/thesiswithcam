
import com.ibm.watson.developer_cloud.service.security.IamOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.Classifier;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.Classifiers;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.GetClassifierOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ListClassifiersOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.UpdateClassifierOptions;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

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
        System.out.println(classifiers);
        ArrayList<String> result = new ArrayList<>();
        for (com.ibm.watson.developer_cloud.visual_recognition.v3.model.Class c : classifiers.getClassifiers().get(0).getClasses()) {
            result.add(c.getClassName());
        }

        return result;
    }

    public static void getClassifierDetails(String classifier) {
        IamOptions options = new IamOptions.Builder()
                .apiKey(apikey)
                .build();

        VisualRecognition service = new VisualRecognition("2018-03-19", options);

        GetClassifierOptions getClassifierOptions = new GetClassifierOptions.Builder(classifier).build();
        Classifier classifierDetails = service.getClassifier(getClassifierOptions).execute();
        System.out.println(classifierDetails);
    }

    /*public static void updateCLassifier(String classifierID, String positiveExamplesPathZip, String name) throws FileNotFoundException {
        IamOptions options = new IamOptions.Builder()
                .apiKey(apikey)
                .build();

        VisualRecognition service = new VisualRecognition("2018-03-19", options);

        UpdateClassifierOptions updateClassifierOptions = new UpdateClassifierOptions.Builder()
                .classifierId("DefaultCustomModel_973098667")
                .addPositiveExamples("asd", new File(positiveExamplesPathZip))
                .build();

        Classifier updatedClassifier = service.updateClassifier(updateClassifierOptions).execute();
        System.out.println(updatedClassifier);

    }*/

}
