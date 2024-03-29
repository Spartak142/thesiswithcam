
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;

public class UI extends javax.swing.JFrame implements MouseListener, Runnable {

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

        try {
            runUI();
        } catch (InterruptedException | IOException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("problem with UI");
        }

    }

    public void runUI() throws InterruptedException, IOException {
        
        watsonGuess = null;
        resultFromCamera = null;
        phase1 = new ArrayList<>();
        phase2 = new ArrayList<>();
        phase3 = new ArrayList<>();
  
        
        Runnable Camera = new Camera();
        Thread Camera_Thread = new Thread(Camera);
        Camera_Thread.start();

        //This phase is when the program is waiting for a valid picture to be taken
        phase1();
        addComponents(phase1);

        synchronized (Camera_Thread) {
            System.out.println("Waiting for Camera to complete...");
            Camera_Thread.wait();
        }

        //Classifies the valid image from camera
        System.out.println("phase 2!");
        //Here it shows the alternatives

        phase2();
        removeComponents(phase1);
        addComponents(phase2);
        System.out.println("Im ready for phase 3");
        phase3();
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
        Object o = e.getSource();
        if (e.getComponent().getName().equals("yes")) {
            //Done with this session and restart
            try {

                //Moves image from session to classfolder
                moveImage(watsonGuess);
                //restart thread.
                
                
                thisUIThread = new Thread(new UI());
                thisUIThread.start();
                
                /*this.getContentPane().removeAll();
                this.repaint();
                //THIS SHIT WORKS BUT NOT WITH UI
                //Everything else works except for runUI()
                //Can even call phase1() and display all of that. 
                //The problem is with runUI
                phase1();
                addComponents(phase1);
                setVisible(true);
                
                removeComponents(phase2);

                */

            } catch (IOException ex) {
                Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Throwable ex) {
                Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (e.getComponent().getName().equals("no")) {
            removeComponents(phase2);
            addComponents(phase3);
        } else {

        }

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

    public void removeComponents(ArrayList<Component> phaseX) {
        for (Component c : phaseX) {
            this.remove(c);
        }
        phaseX.clear();
    }

    public void addComponents(ArrayList<Component> phaseX) {
        for (Component c : phaseX) {
            this.add(c);
        }
        setVisible(true);
        repaint();
    }

    public void resetConfiguration() {
        Squares.clear();
        phase1.clear();
        phase2.clear();
        phase3.clear();
        resultFromCamera.clear();
        watsonGuess = null;
    }

    //Get an image on some sort
    public void phase1() throws InterruptedException {
        GridBagLayout g = new GridBagLayout();
        setLayout(g);
        GridBagConstraints con = new GridBagConstraints();
        con = new GridBagConstraints();
        con.gridy = 0;
        con.gridx = 0;
        con.gridwidth = 1;
        con.fill = GridBagConstraints.HORIZONTAL;
        JLabel test = new JLabel("Currently taking pictures");
        Font f = new Font("serif", Font.PLAIN, 60);
        test.setFont(f);
        g.setConstraints(test, con);
        //Adds it to the main frame

        //add(test);
        phase1.add(test);

        Icon icon = new ImageIcon(fullPath + "loader.gif");
        JLabel gif = new JLabel(icon);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        con.gridy = 2;
        con.ipady = 200;
        g.setConstraints(gif, con);
        //add(gif);
        phase1.add(gif);

    }

    public void phase2() {

        GridBagLayout g = new GridBagLayout();
        setLayout(g);
        GridBagConstraints con = new GridBagConstraints();

        //Image
        JPanel imageContainer = new JPanel();

        Image image = null;
        try {
            image = ImageIO.read(new File(fullPath + resultFromCamera.get(0).getName() + ".jpg")).getScaledInstance((int) screenSize.getWidth() / 5, (int) screenSize.getWidth() / 5, Image.SCALE_SMOOTH);
        } catch (IOException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
        JLabel JLabel1 = new JLabel(new ImageIcon(image), SwingConstants.CENTER);
        JLabel1.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        imageContainer.add(JLabel1);

        con.gridy = 0;
        con.gridx = 0;
        con.gridwidth = 1;
        con.fill = GridBagConstraints.HORIZONTAL;

        g.setConstraints(imageContainer, con);
        imageContainer.setVisible(true);
        //add(imageContainer);
        phase2.add(imageContainer);

        //Buttons
        JPanel buttons = new JPanel();
        buttons.setLayout(new BorderLayout());
        Font f = new Font("serif", Font.PLAIN, 160);

        JPanel yes = new JPanel();
        JLabel yesLabel = new JLabel("✔");
        yesLabel.setFont(f);
        yesLabel.setForeground(Color.green); //Green
        yes.add(yesLabel);
        yes.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));
        yes.addMouseListener(this);
        yes.setName("yes");

        JPanel no = new JPanel();
        JLabel noLabel = new JLabel("✘");
        noLabel.setFont(f);
        noLabel.setForeground(Color.red); //Green
        no.add(noLabel);
        no.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));
        no.addMouseListener(this);
        no.setName("no");

        buttons.add(yes, BorderLayout.LINE_START);
        buttons.add(no, BorderLayout.LINE_END);
        //this.VisableComponents.add(yes);
        //this.VisableComponents.add(no);

        con.gridy = 1;
        con.gridx = 0;
        con.gridwidth = 1;
        con.fill = GridBagConstraints.HORIZONTAL;

        g.setConstraints(buttons, con);
        buttons.setVisible(true);
        //add(buttons);
        phase2.add(buttons);

        //setVisible(true);
        //repaint();
    }

    //The phase after taking the picture
    public void phase3() throws IOException {
        //Gets the number of custom classes
        Squares = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            //Configuring all the squares
            //Creates a new square

            Square sq = new Square(0, 0, resultFromCamera.get(i));
            final String className = resultFromCamera.get(i).getName();
            //Adds a mouselistener for later use like clicking on it.
            sq.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    try {
                        //Moves image from session to classfolder
                        moveImage(className);
                        //restart thread.
                        UI.this.dispose();
                        thisUIThread = new Thread(new UI());
                        thisUIThread.start();

                    } catch (IOException ex) {
                        Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (Exception ex) {
                        Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                    }

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
            });
            //This line does not work at school but works at home
            sq.setBackground(new Color(255, 255, 255));
            //This can be done so that each square or something has its own path to the image
            Image image = null;
            try {
                image = ImageIO.read(new File(fullPath + className + ".jpg")).getScaledInstance((int) screenSize.getWidth() / 9, (int) screenSize.getWidth() / 9, Image.SCALE_SMOOTH);
            } catch (IOException ex) {
                Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
            }
            sq.add(new JLabel(new ImageIcon(image), SwingConstants.CENTER));
            Squares.add(sq);
        }
        GridBagLayout g = new GridBagLayout();
        setLayout(g);
        GridBagConstraints con = new GridBagConstraints();

        int x_axis = 0;
        int y_axis = 0;

        for (int i = 0; i < 6; i++) {
            con = new GridBagConstraints();
            con.gridy = y_axis;
            con.gridx = x_axis;
            con.gridwidth = 1;
            con.fill = GridBagConstraints.HORIZONTAL;

            g.setConstraints(Squares.get(i), con);
            //add(Squares.get(i));
            phase3.add(Squares.get(i));
            Squares.get(i).setPreferredSize(new Dimension((int) screenSize.getWidth() / 8, (int) screenSize.getWidth() / 8));
            Squares.get(i).setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

            if (x_axis == 5) {
                x_axis = 0;
                y_axis++;
            } else {
                x_axis++;
            }
        }

    }

    // If Watson guessed correctly move the file to the appropriate folder for fuTURE
    private void moveImage(String possibleObject) throws IOException, Exception {
        String pathToNewPlace = "classes/" + possibleObject;
        int numberOfFIlesInTheFolder = new File(pathToNewPlace).listFiles().length;
        if (numberOfFIlesInTheFolder < 40) {
            System.out.println("Trying to move the file");
            Files.move(Paths.get("sessionImages/current.png"), Paths.get(pathToNewPlace + "/" + numberOfFIlesInTheFolder + ".png"));
            System.out.println("Move successfull");
        } else {
            //We still have to add the image
            Files.move(Paths.get("sessionImages/current.png"), Paths.get(pathToNewPlace + "/" + numberOfFIlesInTheFolder + ".png"));
            zipFolder(possibleObject);
        }
    }

    /**
     * To be added
     */
    private void getDataFromFile() {

    }

    private void zipFolder(String possibleObject) throws Exception {
//Here it should zip the files and send it to watson
        try {
            className = possibleObject;
            source = classesPath + possibleObject;
            destination = trainedPath + possibleObject + "//" + possibleObject + ".zip";
        }catch(Exception e){
            System.out.println("Data is missing for it to be able to zip");
        }
        Runnable FolderZipper = new FolderZipper();
        Thread FolderZipper_Thread = new Thread(FolderZipper);
        FolderZipper_Thread.start();

        //resetting the list


    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
