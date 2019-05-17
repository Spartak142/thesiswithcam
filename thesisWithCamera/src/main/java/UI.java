
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifiedImages;
import static com.sun.javafx.iio.ImageStorage.ImageType.RGB;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.filechooser.FileSystemView;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author robindah
 */
public class UI extends javax.swing.JFrame implements MouseListener, Runnable {

    /**
     * Init figures
     */
    //This will be the amount of result from the classifier, should be a fixed number ideally since the amount of classes wont increase, currently 24 classes 
    public ArrayList<Square> Squares;
    public Dimension screenSize;

    public UI() throws IOException, InterruptedException {
        //Added everything in run();
    }
    
    @Override
    public void run() {
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        //Here it waits for the image to be taken
        //Closes on exit
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //Fullscreen
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        //Removes the bars so looks like fullscreen
        setUndecorated(true);
        //To like warp up all components.
        pack();
        //Make it visible
        setVisible(true);
        
        
        phase1();
        this.repaint();
        //Here it shows the alternatives
        
        phase2();
        this.repaint(5000);
        //Here it does... something
        
        //phase3();
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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        //TEMPORARLY just to see that it works
        e.getComponent().setBackground(Color.green);
        //This is the magic
        Object o = e.getSource();
        //Squares.indexOf(o) gives us the object with all values.
        System.out.println(Squares.get(Squares.indexOf(o)).co.name);
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //System.out.println(e.getLocationOnScreen());
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }


    //Get an image on some sort
    public void phase1() {
        GridBagLayout g = new GridBagLayout();
        setLayout(g);
        GridBagConstraints con = new GridBagConstraints();
        con = new GridBagConstraints();
            con.gridy = 0;
            con.gridx = 0;
            con.gridwidth = 1;
            con.fill = GridBagConstraints.HORIZONTAL;
            JLabel test = new JLabel("HELLO");
            Font f = new Font("serif", Font.PLAIN, 60);
            test.setFont(f);
            g.setConstraints(test, con);
            //Adds it to the main frame
            add(test);


    }
    //The phase after taking the picture
    public void phase2() {
        ClassifiedImages test = null;
        try {
            test = Methods.classifyURLNoParse("https://i5.walmartimages.ca/images/Large/337/846/6000197337846.jpg");
        } catch (IOException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
        ArrayList<ClassifiedObject> urltest = Methods.JSONToArray(test);
        //Gets the number of custom classes
        Long arraysize = test.getCustomClasses();
        Squares = new ArrayList<>();
        for (int i = 0; i < arraysize; i++) {
            //Configuring all the squares
            //Creates a new square
            Square sq = new Square(0, 0, urltest.get(i));
            //Adds the name of the class to the square
            //sq.add(new JLabel(urltest.get(i).getName()));
            //Adds a mouselistener for later use like clicking on it.
            sq.addMouseListener(this);
            //This line does not work at school but works at home
            sq.setBackground(new Color(255, 255, 255));
            //This can be done so that each square or something has its own path to the image
            Image image = null;
            try {
                image = ImageIO.read(new File("H:/GitHub/thesiswithcam/thesisWithCamera/src/main/java/watson_images/" + urltest.get(i).getName() + ".jpg")).getScaledInstance((int) screenSize.getWidth()/9, (int) screenSize.getWidth()/9, Image.SCALE_SMOOTH);
            } catch (IOException ex) {
                Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
            }
            sq.add(new JLabel(new ImageIcon(image), SwingConstants.CENTER));
            Squares.add(sq);
        }
        
        GridBagLayout g = new GridBagLayout();
        setLayout(g);
        GridBagConstraints con = new GridBagConstraints();

        //
        int x_axis = 0;
        int y_axis = 0;

        for (int i = 0; i < Squares.size(); i++) {
            con = new GridBagConstraints();
            con.gridy = y_axis;
            con.gridx = x_axis;
            con.gridwidth = 1;
            con.fill = GridBagConstraints.HORIZONTAL;
            
            g.setConstraints(Squares.get(i), con);
            add(Squares.get(i));
            Squares.get(i).setPreferredSize(new Dimension((int) screenSize.getWidth()/8, (int) screenSize.getWidth()/8));
            Squares.get(i).setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

            if (x_axis == 5) {
                x_axis = 0;
                y_axis++;
            } else {
                x_axis++;
            }
        }
        
        

    }

    public void phase3() {
        System.out.println("phase 3");
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}