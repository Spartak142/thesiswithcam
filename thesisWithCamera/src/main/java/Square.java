
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;

public class Square extends JPanel implements MouseListener{
    
    int x_axis;
    int y_axis;
    ClassifiedObject co;
    

    public Square(int x_axis, int y_axis, ClassifiedObject co) {
        this.x_axis = x_axis;
        this.y_axis = y_axis;
        this.co = co;
    }

    public int getX_axis() {
        return x_axis;
    }

    public void setX_axis(int x_axis) {
        this.x_axis = x_axis;
    }

    public int getY_axis() {
        return y_axis;
    }

    public void setY_axis(int y_axis) {
        this.y_axis = y_axis;
    }

    public ClassifiedObject getCo() {
        return co;
    }

    public void setCo(ClassifiedObject co) {
        this.co = co;
    }
    
    
    

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
      
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
    
}
