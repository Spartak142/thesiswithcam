
import com.github.sarxos.webcam.Webcam;
import com.ibm.watson.developer_cloud.service.security.IamOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassResult;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifiedImages;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyOptions;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
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
public class Methods {
final static String apikey= "arK3RfX0XWFFUdA_ES6TZ8-Yb1bnC-sTeNxo4BEAm1Jy";
    static IamOptions options = new IamOptions.Builder()
            .apiKey(apikey)
            .build();

    static VisualRecognition service = new VisualRecognition("2018-03-19", options);

    public static void main(String[] args) throws FileNotFoundException {
        String path = "C:/Users/Robin/Desktop/Skolarbete/Thesis/Data/mango/61TbBBpszTL._SX425_.jpg";
        System.out.println(classifyImage(path));
    }

    public static ArrayList<ClassifiedObject> classifyImage(String path) throws FileNotFoundException {
        
        /*
        URL url = new URL("https://images-na.ssl-images-amazon.com/images/I/71gI-IUNUkL._SY355_.jpg");
        Image image = ImageIO.read(url);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write((RenderedImage) image,"jpg", os); 
        InputStream fis = new ByteArrayInputStream(os.toByteArray());
        */
        
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

}
