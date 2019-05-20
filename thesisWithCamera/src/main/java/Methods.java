
import com.ibm.watson.developer_cloud.service.security.IamOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassResult;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifiedImages;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.Classifiers;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ListClassifiersOptions;
import java.io.FileNotFoundException;
import java.util.ArrayList;


public class Methods {

    public static IamOptions options = new IamOptions.Builder()
            .apiKey(apikey())
            .build();

    public static VisualRecognition service = new VisualRecognition("2018-03-19", options);

    public static void main(String[] args) throws FileNotFoundException {

    }

    //For safety reasons
    public static String apikey() {
        return "O3x33LreolcRdA9vPJBk-0V0pzcAqCWC0a2tlGqs31im";
    }

    public static ArrayList<ClassifiedObject> JSONToArray(ClassifiedImages ci) {
        ArrayList<ClassifiedObject> resultArray = new ArrayList<ClassifiedObject>();
        for (ClassResult cr : ci.getImages().get(0).getClassifiers().get(0).getClasses()) {
            resultArray.add(new ClassifiedObject(cr.getClassName(), cr.getScore()));
            //System.out.println(cr.getClassName() + "  " + cr.getScore());
        }
        resultArray.sort(new Sortbyvalue());

        return resultArray;
    }

    public static ArrayList<String> getClassifiers() {

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
