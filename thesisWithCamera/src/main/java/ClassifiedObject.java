
import java.util.Comparator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Robin
 */
public class ClassifiedObject {
    String name;
    double value;

    public ClassifiedObject(String name, double value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return name + " " + value + "\n";
        
    }
    
    
}

class Sortbyvalue implements Comparator<ClassifiedObject> 
{ 
    // Used for sorting in ascending order of 
    // roll number 
    public int compare(ClassifiedObject a, ClassifiedObject b) 
    { 
        if (a.getValue() > b.getValue()) return -1;
        if (a.getValue() < b.getValue()) return 1;
        return 0; 
    } 
} 
