package exemplo;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 *
 * @author Orlandi
 */
public class Input implements KeyListener, MouseListener, MouseWheelListener {
    private static Input input;
    
    public boolean objectIncrease;
    public boolean objectDecrease;
    
    public boolean objectRotateUp;
    public boolean objectRotateDown;
    public boolean objectRotateLeft;
    public boolean objectRotateRight;
    public boolean objectRotateCW;
    public boolean objectRotateAntiCW;
    
    private Input() {
        objectIncrease = false;
        objectDecrease = false;
        
        objectRotateUp = false;
        objectRotateDown = false;
        objectRotateLeft = false;
        objectRotateRight = false;
        objectRotateCW = false;
        objectRotateAntiCW = false;
    }
    
    public static Input getInstance() {
        if(input == null) {
            input = new Input();
        }
        
        return input;
    }
    
    @Override
    public void keyPressed(KeyEvent ke) {
        System.out.println("press input");
        switch (ke.getKeyCode()) {
          case KeyEvent.VK_R:
            objectIncrease = true;
            break;
          case KeyEvent.VK_T:
            objectDecrease = true;
            break;
          case KeyEvent.VK_W:
            objectRotateUp = true;
            break;
          case KeyEvent.VK_S:
            objectRotateDown = true;
            break;
          case KeyEvent.VK_A:
            objectRotateLeft = true;
            break;
          case KeyEvent.VK_D:
            objectRotateRight = true;
            break;
          case KeyEvent.VK_Q:
            objectRotateCW = true;
            break;
          case KeyEvent.VK_E:
            objectRotateAntiCW = true;
            break;
        }
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        System.out.println("release input");
        switch (ke.getKeyCode()) {
          case KeyEvent.VK_R:
            objectIncrease = false;
            break;
          case KeyEvent.VK_T:
            objectDecrease = false;
            break;
          case KeyEvent.VK_W:
            objectRotateUp = false;
            break;
          case KeyEvent.VK_S:
            objectRotateDown = false;
            break;
          case KeyEvent.VK_A:
            objectRotateLeft = false;
            break;
          case KeyEvent.VK_D:
            objectRotateRight = false;
            break;
          case KeyEvent.VK_Q:
            objectRotateCW = false;
            break;
          case KeyEvent.VK_E:
            objectRotateAntiCW = false;
            break;
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        
    }

    @Override
    public void mousePressed(MouseEvent me) {
        System.out.println("mouse press" + me.getX() + " " + me.getY());
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        
    }

    @Override
    public void mouseExited(MouseEvent me) {
        
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent mwe) {
        System.out.println("mouse wheel");
    }
}