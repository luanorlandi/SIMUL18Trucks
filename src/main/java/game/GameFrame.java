/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import com.jogamp.opengl.util.AnimatorBase;
import com.jogamp.opengl.util.FPSAnimator;
import java.awt.Toolkit;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.JFrame;

/**
 *
 * @author Orlandi
 */
public class GameFrame extends JFrame {
    private static GameFrame gameFrame;
    
    GLProfile profile;
    GLCapabilities cap;
    GLCanvas canvas;
    AnimatorBase animator;
    
    Scene scene;
    
    private GameFrame() {
        profile = GLProfile.get(GLProfile.GL3);
        
        cap = new GLCapabilities(profile);
        cap.setDoubleBuffered(true);
        cap.setHardwareAccelerated(true);
        
        scene = Scene.getInstance();
        
        canvas = new GLCanvas(cap);
        canvas.addGLEventListener(scene);
        
        animator = new FPSAnimator(canvas, 60);
        
        this.setTitle("SIMUL18Trucks");
        this.getContentPane().add(canvas);
        this.setSize(700, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("images/icon.png")));
        
        Input input = Input.getInstance();
        input.setCursorLocked(true);
        input.hideCursor(this);
        
        this.setFocusable(true);
        this.addKeyListener(input);
        this.addMouseListener(input);
        this.addMouseMotionListener(input);
        this.addMouseWheelListener(input);
        
        canvas.setFocusable(true);
        canvas.addKeyListener(input);
        canvas.addMouseListener(input);
        canvas.addMouseMotionListener(input);
        canvas.addMouseWheelListener(input);
        
        animator.start();
    }
    
    public static GameFrame getInstance() {
        if(gameFrame == null) {
            gameFrame = new GameFrame();
        }
        
        return gameFrame;
    }
}
