
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import com.ibm.watson.developer_cloud.service.security.IamOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifiedImages;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyOptions;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import static java.lang.Thread.sleep;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;

public class UI extends javax.swing.JFrame implements MouseListener, Runnable, ActionListener {

    //This will be the amount of result from the classifier, should be a fixed number ideally since the amount of classes wont increase, currently 24 classes 
    public ArrayList<Square> Squares;
    public Dimension screenSize;
    boolean classifyMode;
    ArrayList<Component> phase1;
    ArrayList<Component> phase2;
    ArrayList<Component> phase3;
    public static String watsonGuess;
    public static ArrayList<ClassifiedObject> resultFromCamera;
    String fileName = "stats.txt";
    private volatile Thread thisUIThread;
    String fullPath = "H:\\GitHub\\thesiswithcam\\thesisWithCamera\\src\\main\\java\\watson_images\\";
    String classesPath = "H:\\GitHub\\thesiswithcam\\thesisWithCamera\\classes\\";
    String trainedPath = "H:\\GitHub\\thesiswithcam\\thesisWithCamera\\trainedClasses\\";
    FolderZipper zippah = new FolderZipper();

    //Classname, source, destination ONLY
    public volatile static String className;
    public volatile static String destination;
    public volatile static String source;

    public static BufferedImage image;
    public static String imageName;
    public static File experimentFolder;
    public static Webcam webcam;
    public static String experimentName = "experiment_1";
    public static int filescounter = 0;
    public static ArrayList<ClassifiedObject> results = null;

    public static String classifierId = "CompleteModel_35852773";
    public static IamOptions options = new IamOptions.Builder()
            .apiKey(Methods.apikey())
            .build();

    public static VisualRecognition service = new VisualRecognition("2018-03-19", options);

    public UI() throws IOException, InterruptedException {
        //Added everything in run();
        thisUIThread = Thread.currentThread();

    }

    @Override
    public void run() {
        //Gets screen size
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        //Here it waits for the image to be taken
        //Closes on exit
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //Fullscreen
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        //Removes the bars so looks like fullscreen
        //setUndecorated(true);
        //To like warp up all components.
        //Make it visible
        // this.setVisible(true);
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
        experimentFolder = new File(experimentName);
        experimentFolder.mkdir();
        this.addMouseListener(this);
        JLabel a = new JLabel("yes");
        a.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        add(a);
        setVisible(true);
        /*
        while (true) {
            try {
                System.out.println("Hey");
                sleep(1000);
                image = webcam.getImage();
                remove(a);
                a = new JLabel(new ImageIcon(image), SwingConstants.CENTER);
                a.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
                add(a);
                setVisible(true);
                repaint();
                
            } catch (InterruptedException ex) {
                Logger.getLogger(Camera.class.getName()).log(Level.SEVERE, null, ex);
            }

        }*/

    }

 

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1313, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 840, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        //TEMPORARLY just to see that it works
        //e.getComponent().setBackground(Color.green);
        //This is the magic
        //Object o = e.getSource();

        try {
            image = webcam.getImage();
            imageName = experimentName + "/image.png";
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
            ClassifiedImages result = Methods.service.classify(classifyOptions).execute();
            ClassifiedObject current = Methods.JSONToArray(result).get(0);
            String currentName = Methods.JSONToArray(result).get(0).getName();
            imageName = experimentName + "/" + current;
            Files.move(Paths.get(experimentName + "/image.png"), Paths.get(experimentName + "/" + currentName + filescounter + ".png"));

            for(ClassifiedObject co : results){
                if(co.name.equals(current)){
                    co.counter++;
                }else{
                    current.counter++;
                    results.add(current);
                    
                }
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(!results.isEmpty()) System.out.println(results.toString());
        
        filescounter++;
        System.out.println("Image taken!");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }



    

    /**
     * To be added
     */
    private void getDataFromFile() {

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
