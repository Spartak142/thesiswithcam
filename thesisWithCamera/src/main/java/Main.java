
import com.github.sarxos.webcam.Webcam;

import com.ibm.watson.developer_cloud.service.security.IamOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifiedImages;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.Classifier;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.CreateClassifierOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.UpdateClassifierOptions;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;
import javax.imageio.ImageIO;

public class Main {

    public static VisualRecognition service;
    public static String classifierId = "DefaultCustomModel_1716876290";
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
    //Initilising API
        IamOptions options = new IamOptions.Builder()
                .apiKey("sP83C9a-mEePlb5-4banZN1EYLSbxavGnpYmOLYGl-v7")
                .build();
      service = new VisualRecognition("2018-03-19", options);

        // Taking in user commands
        Scanner command = new Scanner(System.in);

        //Picture name definition
        String testPic= "testpic";
        int i=0;
        File thePics = new File("testpics");
        thePics.mkdir();
   
        //Webcam initialisation
        Webcam webcam = Webcam.getDefault();
        webcam.setViewSize(new Dimension(640, 480));
        webcam.open();

        System.out.println("Type 'pic' to take a picture and analyse it. Type send to update the model for Garlic");
        while (true) {

            String userInput = command.next();
            if (userInput.equalsIgnoreCase("pic")) {
                //taking a pic and putting it into the folder for pics.
               BufferedImage image = webcam.getImage();
               String imgName="./testpics/"+testPic+i+".png";
               System.out.println(imgName);
               i++;
                ImageIO.write(image, "PNG", new File(imgName));

               // Sending the taken pic for processing
                InputStream imagesStream = new FileInputStream(imgName);
                ClassifyOptions classifyOptions = new ClassifyOptions.Builder()
                        .imagesFile(imagesStream)
                        .imagesFilename("download.jpg")
                        .threshold((float) 0.6)
                        .classifierIds(Arrays.asList(classifierId))
                        .build();
                // Geting the results
                ClassifiedImages result = service.classify(classifyOptions).execute();
                System.out.println(result);

            }
             if (userInput.equalsIgnoreCase("send")) {
                 sendForProcessing();
             }
        }
    }
    
    
    
    
    public static void sendForProcessing() throws FileNotFoundException{

        
        /**
         * Add zipper of testpics here otehrwise line 95 will give an error
         */
        
        
        
        
        

UpdateClassifierOptions updateClassifierOptions = new UpdateClassifierOptions.Builder()
  .classifierId(classifierId)
  .addClass("Garlic", new File("./testpics.zip"))
  .build();

Classifier updatedDogs = service.updateClassifier(updateClassifierOptions).execute();
System.out.println(updatedDogs);
    }
}
