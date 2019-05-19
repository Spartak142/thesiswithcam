import com.github.sarxos.webcam.Webcam;
import com.ibm.watson.developer_cloud.service.security.IamOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassResult;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifiedImages;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.Classifier;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.Classifiers;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.GetClassifierOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ListClassifiersOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.UpdateClassifierOptions;
import java.awt.Image;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import javax.imageio.ImageIO;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Robin
 */


public class Methods{
    
    
    public static IamOptions options = new IamOptions.Builder()
            .apiKey(apikey())
            .build();

    public static VisualRecognition service = new VisualRecognition("2018-03-19", options);
  
    
    //For safety reasons
    public static String apikey (){
        return "sP83C9a-mEePlb5-4banZN1EYLSbxavGnpYmOLYGl-v7";
    }

    public static ArrayList<ClassifiedObject> JSONToArray (ClassifiedImages ci){
        ArrayList<ClassifiedObject> resultArray = new ArrayList<ClassifiedObject>();
        for(ClassResult cr : ci.getImages().get(0).getClassifiers().get(0).getClasses()){
            resultArray.add(new ClassifiedObject(cr.getClassName(), cr.getScore()));
            //System.out.println(cr.getClassName() + "  " + cr.getScore());
        }
        resultArray.sort(new Sortbyvalue());
        
        return resultArray;
    }
    
   /**
    * Used method
    * @return 
    */    
    
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
