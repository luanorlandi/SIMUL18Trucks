package game;

import java.awt.AWTException;
import java.awt.Cursor;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

/**
 *
 * @author Orlandi
 */
public class Input implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {
    private static Input input;
    
    private Robot robot = null;
    private boolean cursorLocked;
    
    private boolean editMode;
    
    private boolean truckMoveForward;
    private boolean truckMoveBackward;
    
    private boolean cameraRotateLeft;
    private boolean cameraRotateRight;
    private boolean cameraRotateUp;
    private boolean cameraRotateDown;
    
    public boolean objTansXNeg;
    public boolean objTansXPos;
    public boolean objTansYNeg;
    public boolean objTansYPos;
    public boolean objTansZPos;
    public boolean objTansZNeg;
    
    public boolean objRotXNeg;
    public boolean objRotXPos;
    public boolean objRotYNeg;
    public boolean objRotYPos;
    public boolean objRotZNeg;
    public boolean objRotZPos;
    
    public boolean objInc;
    public boolean objDec;
    
    public boolean camTransXNeg;
    public boolean camTransXPos;
    public boolean camTransYNeg;
    public boolean camTransYPos;
    public boolean camTransZPos;
    public boolean camTransZNeg;
    
    public boolean camRefXNeg;
    public boolean camRefXPos;
    public boolean camRefYNeg;
    public boolean camRefYPos;
    public boolean camRefZPos;
    public boolean camRefZNeg;
    
    public boolean camRotXPos;
    public boolean camRotXNeg;
    public boolean camRotYPos;
    public boolean camRotYNeg;
    public boolean camRotZPos;
    public boolean camRotZNeg;
    
    public boolean scenePositions;
    
    private Input() {
        try {
            robot = new Robot();
        } catch (AWTException ex) {
            System.err.println("Robot not created: " + ex.getMessage());
        }
        
        cursorLocked = false;
        
        editMode = false;
        
        truckMoveForward = false;
        truckMoveBackward = false;
        cameraRotateLeft = false;
        cameraRotateRight = false;
        cameraRotateUp = false;
        cameraRotateLeft = false;
        
        objTansXNeg = false;
        objTansXPos = false;
        objTansYNeg = false;
        objTansYPos = false;
        objTansZNeg = false;
        objTansZPos = false;

        objRotXNeg = false;
        objRotXPos = false;
        objRotYNeg = false;
        objRotYPos = false;
        objRotZNeg = false;
        objRotZPos = false;

        objInc = false;
        objDec = false;
    
        camRotXPos = false;
        camRotXNeg = false;
        camRotYPos = false;
        camRotYNeg = false;
        camRotZPos = false;
        camRotZNeg = false;
    }
    
    public static Input getInstance() {
        if(input == null) {
            input = new Input();
        }
        
        return input;
    }
    
    @Override
    public void keyPressed(KeyEvent ke) {
        if(!editMode) {
            switch (ke.getKeyCode()) {
                case KeyEvent.VK_W: truckMoveForward = true; break;
                case KeyEvent.VK_S: truckMoveBackward = true; break;
                case KeyEvent.VK_Q: cameraRotateLeft = true; break;
                case KeyEvent.VK_E: cameraRotateRight = true; break;
                case KeyEvent.VK_A: cameraRotateUp = true; break;
                case KeyEvent.VK_D: cameraRotateDown = true; break;
            }
        } else {
            switch (ke.getKeyCode()) {
                case KeyEvent.VK_Q: objTansXNeg = true; break;
                case KeyEvent.VK_W: objTansXPos = true; break;
                case KeyEvent.VK_A: objTansYNeg = true; break;
                case KeyEvent.VK_S: objTansYPos = true; break;
                case KeyEvent.VK_Z: objTansZNeg = true; break;
                case KeyEvent.VK_X: objTansZPos = true; break;

                case KeyEvent.VK_E: objRotXNeg = true; break;
                case KeyEvent.VK_R: objRotXPos = true; break;
                case KeyEvent.VK_D: objRotYNeg = true; break;
                case KeyEvent.VK_F: objRotYPos = true; break;
                case KeyEvent.VK_C: objRotZNeg = true; break;
                case KeyEvent.VK_V: objRotZPos = true; break;

                case KeyEvent.VK_EQUALS: objInc = true; break;
                case KeyEvent.VK_MINUS: objDec = true; break;

                case KeyEvent.VK_T: camTransXNeg = true; break;
                case KeyEvent.VK_Y: camTransXPos = true; break;
                case KeyEvent.VK_G: camTransYNeg = true; break;
                case KeyEvent.VK_H: camTransYPos = true; break;
                case KeyEvent.VK_B: camTransZNeg = true; break;
                case KeyEvent.VK_N: camTransZPos = true; break;

                case KeyEvent.VK_U: camRefXNeg = true; break;
                case KeyEvent.VK_I: camRefXPos = true; break;
                case KeyEvent.VK_J: camRefYNeg = true; break;
                case KeyEvent.VK_K: camRefYPos = true; break;
                case KeyEvent.VK_M: camRefZNeg = true; break;
                case KeyEvent.VK_COMMA: camRefZPos = true; break;

                case KeyEvent.VK_O: camRotXNeg = true; break;
                case KeyEvent.VK_P: camRotXPos = true; break;
                case KeyEvent.VK_L: camRotYNeg = true; break;
                case KeyEvent.VK_CLOSE_BRACKET: camRotYPos = true; break;
                case KeyEvent.VK_PERIOD: camRotZNeg = true; break;
                case KeyEvent.VK_SEMICOLON: camRotZPos = true; break;

                case KeyEvent.VK_0: scenePositions = true; break;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        if(!editMode) {
            switch (ke.getKeyCode()) {
                case KeyEvent.VK_8: editMode = !editMode; break;
                
                case KeyEvent.VK_W: truckMoveForward = false; break;
                case KeyEvent.VK_S: truckMoveBackward = false; break;
                case KeyEvent.VK_Q: cameraRotateLeft = false; break;
                case KeyEvent.VK_E: cameraRotateRight = false; break;
                case KeyEvent.VK_A: cameraRotateUp = false; break;
                case KeyEvent.VK_D: cameraRotateDown = false; break;
            }
        } else {
            switch (ke.getKeyCode()) {
                case KeyEvent.VK_8: editMode = !editMode; break;
                
                case KeyEvent.VK_Q: objTansXNeg = false; break;
                case KeyEvent.VK_W: objTansXPos = false; break;
                case KeyEvent.VK_A: objTansYNeg = false; break;
                case KeyEvent.VK_S: objTansYPos = false; break;
                case KeyEvent.VK_Z: objTansZNeg = false; break;
                case KeyEvent.VK_X: objTansZPos = false; break;

                case KeyEvent.VK_E: objRotXNeg = false; break;
                case KeyEvent.VK_R: objRotXPos = false; break;
                case KeyEvent.VK_D: objRotYNeg = false; break;
                case KeyEvent.VK_F: objRotYPos = false; break;
                case KeyEvent.VK_C: objRotZNeg = false; break;
                case KeyEvent.VK_V: objRotZPos = false; break;

                case KeyEvent.VK_EQUALS: objInc = false; break;
                case KeyEvent.VK_MINUS: objDec = false; break;

                case KeyEvent.VK_T: camTransXNeg = false; break;
                case KeyEvent.VK_Y: camTransXPos = false; break;
                case KeyEvent.VK_G: camTransYNeg = false; break;
                case KeyEvent.VK_H: camTransYPos = false; break;
                case KeyEvent.VK_B: camTransZNeg = false; break;
                case KeyEvent.VK_N: camTransZPos = false; break;

                case KeyEvent.VK_U: camRefXNeg = false; break;
                case KeyEvent.VK_I: camRefXPos = false; break;
                case KeyEvent.VK_J: camRefYNeg = false; break;
                case KeyEvent.VK_K: camRefYPos = false; break;
                case KeyEvent.VK_M: camRefZNeg = false; break;
                case KeyEvent.VK_COMMA: camRefZPos = false; break;

                case KeyEvent.VK_O: camRotXNeg = false; break;
                case KeyEvent.VK_P: camRotXPos = false; break;
                case KeyEvent.VK_L: camRotYNeg = false; break;
                case KeyEvent.VK_CLOSE_BRACKET: camRotYPos = false; break;
                case KeyEvent.VK_PERIOD: camRotZNeg = false; break;
                case KeyEvent.VK_SEMICOLON: camRotZPos = false; break;

                case KeyEvent.VK_0: scenePositions = false; break;
                case KeyEvent.VK_9: Scene.getInstance().changeObjectSelect(); break;
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        
    }

    @Override
    public void mousePressed(MouseEvent me) {
        
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
    public void mouseDragged(MouseEvent e) {
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(cursorLocked && robot != null) {
            repositionCursor(e);
        }
    }
    
    @Override
    public void mouseWheelMoved(MouseWheelEvent mwe) {
        float scroll = (float) (0.05 * mwe.getUnitsToScroll());
        
        Scene.getInstance().getCamera().spin(0.0f, 0.0f, scroll);
    }
    
    
    public void hideCursor(JFrame frame) {
        BufferedImage cursorImg = new BufferedImage(
            16, 16, BufferedImage.TYPE_INT_ARGB);

        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
            cursorImg, new Point(0, 0), "blank cursor");

        frame.getContentPane().setCursor(blankCursor);
    }
    
    public void repositionCursor(MouseEvent me) {
        Point framePos = GameFrame.getInstance().getLocationOnScreen();
        int height = GameFrame.getInstance().getSize().height;
        int width = GameFrame.getInstance().getSize().width;
        
        int centerX = (framePos.x + (width/2));
        int centerY = (framePos.y + (height/2));
        
        int mouseX = me.getXOnScreen();
        int mouseY = me.getYOnScreen();
        
        float spinY = (float) (mouseX - centerX)/4;
        float spinXZ = (float) (mouseY - centerY)/4;
        
        Scene.getInstance().getCamera().spin(spinY, spinXZ, 0.0f);
        
        robot.mouseMove(centerX, centerY);
    }
    
    public void moveCursorToCenter() {
        Point framePos = GameFrame.getInstance().getLocationOnScreen();
        int height = GameFrame.getInstance().getSize().height;
        int width = GameFrame.getInstance().getSize().width;
        
        int centerX = (framePos.x + (width/2));
        int centerY = (framePos.y + (height/2));
        
        robot.mouseMove(centerX, centerY);
    }
    
    public void setCursorLocked(boolean cursorLocked) {
//        if(windowWidth <= 0 || windowHeight <= 0) {
//            System.err.println("Cursor not locked: screen size 0");
//            return;
//        }
        this.cursorLocked = cursorLocked;
    }

    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    public boolean isTruckMoveForward() {
        return truckMoveForward;
    }

    public boolean isTruckMoveBackward() {
        return truckMoveBackward;
    }
    
    public boolean isCameraRotateLeft() {
        return cameraRotateLeft;
    }

    public boolean isCameraRotateRight() {
        return cameraRotateRight;
    }

    public boolean isCameraRotateUp() {
        return cameraRotateUp;
    }

    public boolean isCameraRotateDown() {
        return cameraRotateDown;
    }
}