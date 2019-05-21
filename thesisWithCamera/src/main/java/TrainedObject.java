/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author robindah
 */
public class TrainedObject {
    String className;
    String pathToZip;

    public TrainedObject(String className, String pathToZip) {
        this.className = className;
        this.pathToZip = pathToZip;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getPathToZip() {
        return pathToZip;
    }

    public void setPathToZip(String pathToZip) {
        this.pathToZip = pathToZip;
    }
    
    
    
}
