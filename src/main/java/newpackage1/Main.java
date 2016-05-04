package newpackage1;

import com.jogamp.opengl.util.AnimatorBase;
import com.jogamp.opengl.util.FPSAnimator;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.JFrame;

public class Main {
    
    public static void main(String []args) {
        
        GLProfile profile = GLProfile.get(GLProfile.GL3);
        
        GLCapabilities cap =  new GLCapabilities(profile);
        cap.setDoubleBuffered(true);
        cap.setHardwareAccelerated(true);
        
        // cria canvas
        GLCanvas canvas = new GLCanvas(cap);
        
        // registra seu desenhador
        canvas.addGLEventListener(new SimpleScene());
        
        // cria janela
        JFrame frame = new JFrame();
        frame.getContentPane().add(canvas);
        frame.setSize(600,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // fecha a janela quando termina
        
        // 30 frames por segundo
        AnimatorBase animator = new FPSAnimator(canvas,30);
        
        frame.setVisible(true);
        animator.start();
    }

    private static GLCanvas newGLCanvas(GLCapabilities cap) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}