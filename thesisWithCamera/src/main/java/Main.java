
import java.io.IOException;
import java.io.File;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import java.util.concurrent.ScheduledThreadPoolExecutor;

public class Main {

    public static ScheduledThreadPoolExecutor eventPool;
    public static boolean waitFor2;
    public static final File dir = new File("classes");
    public static Path file = Paths.get("stats.txt");

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

    public static void main(String[] args) throws IOException, InterruptedException {

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
        if (dir.listFiles().length != 52) {
            //Creates folders
            ArrayList<String> classes = Methods.getClassifiers();
            dir.mkdir();
            for (int i = 0; i < classes.size(); i++) {
                System.out.println(classes.get(i));
                File classFolder = new File("classes/" + classes.get(i));
                classFolder.mkdir();
            }
        }
    }

}
