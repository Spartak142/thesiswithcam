
import com.google.gson.Gson;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author robindah
 */
public class JSON {

    @Override
    public String toString() {
        Gson g = new Gson();
        String jsonString = g.toJson(this);
        return jsonString;
    }
    int scannedImages;
    ArrayList<String> traningQueue;
    ArrayList<JSONObject> classes;

    public JSON(int scannedImages, ArrayList<String> traningQueue, ArrayList<JSONObject> classes) {
        this.scannedImages = scannedImages;
        this.traningQueue = traningQueue;
        this.classes = classes;
    }

    public JSON() {
    }

    
    public int getScannedImages() {
        return scannedImages;
    }

    public void setScannedImages(int scannedImages) {
        this.scannedImages = scannedImages;
    }

    public ArrayList<String> getTraningQueue() {
        return traningQueue;
    }

    public void setTraningQueue(ArrayList<String> traningQueue) {
        this.traningQueue = traningQueue;
    }

    public ArrayList<JSONObject> getClasses() {
        return classes;
    }

    public void setClasses(ArrayList<JSONObject> classes) {
        this.classes = classes;
    }
    
    
}

class JSONObject{
    String className;
    int numberOfScans;
    double accuracy;

    public JSONObject(String className, int numberOfScans, double accuracy) {
        this.className = className;
        this.numberOfScans = numberOfScans;
        this.accuracy = accuracy;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getNumberOfScans() {
        return numberOfScans;
    }

    public void setNumberOfScans(int numberOfScans) {
        this.numberOfScans = numberOfScans;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    
    
}
