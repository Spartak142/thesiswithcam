
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Queue;

import java.util.concurrent.ScheduledThreadPoolExecutor;

public class Main {

    public static final File dir = new File("classes");
    public static Path file = Paths.get("stats.txt");
    public static JSON json;
    //This is used for updating classes. 
    public static volatile Queue<TrainedObject> QueuingClasses;

    public static void main(String[] args) throws IOException, InterruptedException {
        QueuingClasses = null;
        createFile();
        initialiseFolders();
        Runnable UI = new UI();
        Thread UI_Thread = new Thread(UI);
        UI_Thread.start();
         
    }

    /**
     * initialise the folders
     */
    private static void initialiseFolders() {
        
            //Creates folders
            ArrayList<String> classes = Methods.getClassifiers();
            dir.mkdir();
            for (int i = 0; i < classes.size(); i++) {
                System.out.println(classes.get(i));
                File classFolder = new File("classes/" + classes.get(i));
                classFolder.mkdir();
            }
        
    }

    private static void createFile() {

        try {
            // Create the empty file with default permissions, etc.
            Files.createFile(file);
        } catch (FileAlreadyExistsException x) {
            System.out.println("file named %s"
                    + " already exists%n");
        } catch (IOException x) {
            // Some other sort of failure, such as permissions.
            System.out.println("createFile error: %s%n");
        }
    }

    private static JSON createJSON() {
        ArrayList<String> classes = Methods.getClassifiers();

        JSON temp = new JSON();
        temp.setScannedImages(0);
        ArrayList<JSONObject> test = new ArrayList<>();
        for (String s : classes) {

            test.add(new JSONObject(s, 0, 0));
        }
        temp.setClasses(test);

        System.out.println(temp.toString());

        return temp;
    }

}
