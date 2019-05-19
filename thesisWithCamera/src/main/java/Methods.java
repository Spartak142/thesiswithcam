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

    public static ArrayList<ClassifiedObject> classifyImage(String path) throws FileNotFoundException {
   
        
        InputStream imagesStream = new FileInputStream(path);
        ClassifyOptions classifyOptions = new ClassifyOptions.Builder()
                .imagesFile(imagesStream)
                .imagesFilename("download.jpg")
                .threshold((float) 0)
                .classifierIds(Arrays.asList("DefaultCustomModel_1716876290"))
                .build();

        ClassifiedImages result = service.classify(classifyOptions).execute();

       
        return JSONToArray(result);
    }
    //No parsing, raw json
    public static ClassifiedImages classifyURLNoParse(String adress) throws FileNotFoundException, IOException {
        
        
        URL url = new URL(adress);
        Image image = ImageIO.read(url);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write((RenderedImage) image,"jpg", os); 
        InputStream imagesStream = new ByteArrayInputStream(os.toByteArray());
        
        
        
        ClassifyOptions classifyOptions = new ClassifyOptions.Builder()
                .imagesFile(imagesStream)
                .imagesFilename("URL")
                .threshold((float) 0)
                .classifierIds(Arrays.asList("DefaultCustomModel_1716876290"))
                .build();

        ClassifiedImages result = service.classify(classifyOptions).execute();
        
        return (result);
    }
    
    
    public static ArrayList<ClassifiedObject> classifyURL(String adress) throws FileNotFoundException, IOException {
        
        
        URL url = new URL(adress);
        Image image = ImageIO.read(url);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write((RenderedImage) image,"jpg", os); 
        InputStream imagesStream = new ByteArrayInputStream(os.toByteArray());
        
        
        
        ClassifyOptions classifyOptions = new ClassifyOptions.Builder()
                .imagesFile(imagesStream)
                .imagesFilename("URL")
                .threshold((float) 0)
                .classifierIds(Arrays.asList("DefaultCustomModel_1716876290"))
                .build();

        ClassifiedImages result = service.classify(classifyOptions).execute();
        
        return JSONToArray(result);
    }
    
    public static ArrayList<ClassifiedObject> classifyCamera() throws FileNotFoundException, IOException {
        
        
        Webcam webcam = Webcam.getDefault();
        webcam.open();
	Image image = webcam.getImage();
	//ImageIO.write(image, "PNG", new File("test.png")); //THis has to be reaned later on when it is going to be trained
        
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write((RenderedImage) image,"jpg", os); 
        InputStream imagesStream = new ByteArrayInputStream(os.toByteArray());
        
        ClassifyOptions classifyOptions = new ClassifyOptions.Builder()
                .imagesFile(imagesStream)
                .imagesFilename("URL")
                .threshold((float) 0)
                .classifierIds(Arrays.asList("DefaultCustomModel_1716876290"))
                .build();

        ClassifiedImages result = service.classify(classifyOptions).execute();
        
        return JSONToArray(result);
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

    
    
    
    
    
    
    
    
    
    
    public static void getClassifierDetails(String classifier) {

        GetClassifierOptions getClassifierOptions = new GetClassifierOptions.Builder(classifier).build();
        Classifier classifierDetails = service.getClassifier(getClassifierOptions).execute();
        System.out.println(classifierDetails);
    }
    
        public static void updateClassifier() throws FileNotFoundException {
        
        UpdateClassifierOptions updateClassifierOptions = new UpdateClassifierOptions.Builder()
                .classifierId("DefaultCustomModel_1716876290")
                //.addPositiveExamples("Carrot", new File("./Carrots.zip"))
                //.negativeExamples(new File("D:/hand.jpg"))
                .build();

        Classifier updatedClassifier = service.updateClassifier(updateClassifierOptions).execute();
        System.out.println(updatedClassifier);

    }
        
    /*public void motionDetected(WebcamMotionEvent wme) {
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
    }*/
    

}
