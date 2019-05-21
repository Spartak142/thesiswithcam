
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.Classifier;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import static jdk.nashorn.internal.objects.NativeDebug.map;

//All credit goes to https://www.java2s.com/Code/Java/File-Input-Output/UseJavacodetozipafolder.htm
public class FolderZipper implements Runnable {

    public static void zipFolder(String srcFolder, String destZipFile) throws Exception {
        ZipOutputStream zip = null;
        FileOutputStream fileWriter = null;
        fileWriter = new FileOutputStream(destZipFile);
        zip = new ZipOutputStream(fileWriter);
        addFolderToZip("", srcFolder, zip);
        zip.flush();
        System.out.println("flushing");
        zip.close();
        System.out.println("closing");

    }

    private static void addFileToZip(String path, String srcFile, ZipOutputStream zip)
            throws Exception {

        File folder = new File(srcFile);
        if (folder.isDirectory()) {
            addFolderToZip(path, srcFile, zip);
        } else {
            byte[] buf = new byte[1024];
            int len;
            FileInputStream in = new FileInputStream(srcFile);
            zip.putNextEntry(new ZipEntry(path + "/" + folder.getName()));
            while ((len = in.read(buf)) > 0) {
                zip.write(buf, 0, len);
            }
        }
    }

    private static void addFolderToZip(String path, String srcFolder, ZipOutputStream zip)
            throws Exception {
        File folder = new File(srcFolder);
        for (String fileName : folder.list()) {
            if (path.equals("")) {

                addFileToZip(folder.getName(), srcFolder + "\\" + fileName, zip);
                File temp = new File(srcFolder + "\\" + fileName);
                System.out.println(temp.toString());
                temp.delete();
            } else {
                addFileToZip(path + "\\" + folder.getName(), srcFolder + "\\" + fileName, zip);
            }
        }
    }

    @Override
    public void run() {

        try {
            zipFolder(UI.source, UI.destination);
        } catch (Exception ex) {
            System.out.println("File zipper logging");
            Logger.getLogger(FolderZipper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //when it is done zipping send data to Watson
        //First if statement adds request to queue
        //Second if updates queueing requests
        //Last else updates current request

        Classifier details = Methods.getClassifierDetails();
        String ifWatsonIsTraning = details.getStatus();
        if (ifWatsonIsTraning.equals("training") || ifWatsonIsTraning.equals("retraining")) {
            //Adding the path to a queue
            //This is the queue object in MAIN for each class that has to be trained
             Main.QueuingClasses.add(new TrainedObject(UI.className, UI.destination));
        } 
        //Here we check if the queue is empty or not. 
        //If the queue is not empty it will train queued classes.
        else if (Main.QueuingClasses != null && !Main.QueuingClasses.isEmpty()) {
            try {
                //Sends the destination of the zip file to a methods which updates the class
                //Takes the first class in the array
                Methods.updateClassifier(Main.QueuingClasses.poll());
            } catch (FileNotFoundException ex) {
                Logger.getLogger(FolderZipper.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            /*try {
                //Sends the destination of the zip file to a methods which updates the class
                Methods.updateClassifier(new TrainedObject(UI.className, UI.destination));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(FolderZipper.class.getName()).log(Level.SEVERE, null, ex);
            }*/
            //ONLY FINAL FINAL FINAL VERSION
            //If it gets here it is supposed to update
            System.out.println("Fake updating!");
        }

        System.out.println(Thread.currentThread().getName());
    }
}
