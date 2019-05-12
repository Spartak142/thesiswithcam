
import com.github.sarxos.webcam.Webcam;
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
import java.util.Scanner;
import javax.imageio.ImageIO;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author MVPIMP
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        IamOptions options = new IamOptions.Builder()
                .apiKey("sP83C9a-mEePlb5-4banZN1EYLSbxavGnpYmOLYGl-v7")
                .build();

        VisualRecognition service = new VisualRecognition("2018-03-19", options);

        Scanner command = new Scanner(System.in);

        Webcam webcam = Webcam.getDefault();
        webcam.setViewSize(new Dimension(1024, 768));

        webcam.open();

        System.out.println("Type 'takepic' to take a picture and analyse it.");
        while (true) {

            String userInput = command.next();
            if (userInput.equalsIgnoreCase("takepic")) {
                BufferedImage image = webcam.getImage();
                ImageIO.write(image, "PNG", new File("hello-world.png"));

                //InputStream imagesStream = new FileInputStream("C:\\Users\\MVPIMP\\Pictures\\Watson test\\Carrots\\images.jpg");
                InputStream imagesStream = new FileInputStream("hello-world.png");
                ClassifyOptions classifyOptions = new ClassifyOptions.Builder()
                        .imagesFile(imagesStream)
                        .imagesFilename("download.jpg")
                        .threshold((float) 0.6)
                        .classifierIds(Arrays.asList("DefaultCustomModel_1716876290"))
                        .build();

                ClassifiedImages result = service.classify(classifyOptions).execute();
                System.out.println(result);

            }
        }
    }
}
