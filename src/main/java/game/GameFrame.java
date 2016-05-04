/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import com.jogamp.opengl.util.AnimatorBase;
import com.jogamp.opengl.util.FPSAnimator;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.JFrame;

/**
 *
 * @author Orlandi
 */
public class GameFrame extends JFrame {
    GLProfile profile;
    GLCapabilities cap;
    GLCanvas canvas;
    AnimatorBase animator;
    
    Scene scene;
    
    public GameFrame() {
        profile = GLProfile.get(GLProfile.GL3);
        
        cap = new GLCapabilities(profile);
        cap.setDoubleBuffered(true);
        cap.setHardwareAccelerated(true);
        
        scene = Scene.getInstance();
        
        canvas = new GLCanvas(cap);
        canvas.addGLEventListener(scene);
        
        animator = new FPSAnimator(canvas, 60);
        
        this.setTitle("SIMUL18trucks");
        this.getContentPane().add(canvas);
        this.setSize(600, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        
        Input input = Input.getInstance();
        input.setWindowWidth(this.getContentPane().getSize().width);
        input.setWindowHeight(this.getContentPane().getSize().height);
        
        this.setFocusable(true);
        this.addKeyListener(input);
        this.addMouseListener(input);
        this.addMouseWheelListener(input);
        
        canvas.setFocusable(true);
        canvas.addKeyListener(input);
        canvas.addMouseListener(input);
        canvas.addMouseWheelListener(input);
        
        animator.start();
    }
}
