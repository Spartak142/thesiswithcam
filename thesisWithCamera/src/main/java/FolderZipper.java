
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

//All credit goes to https://www.java2s.com/Code/Java/File-Input-Output/UseJavacodetozipafolder.htm
public class FolderZipper implements Runnable {

    public static void zipFolder(String srcFolder, String destZipFile) throws Exception {
        ZipOutputStream zip = null;
        FileOutputStream fileWriter = null;
        fileWriter = new FileOutputStream(destZipFile);
        zip = new ZipOutputStream(fileWriter);
        addFolderToZip("", srcFolder, zip);
        zip.flush();
        zip.close();
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
        String destination = UI.destination;
        String source = UI.source;
        try {
            zipFolder(source, destination);
        } catch (Exception ex) {
            Logger.getLogger(FolderZipper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        Thread.currentThread().interrupt();
    }
}
