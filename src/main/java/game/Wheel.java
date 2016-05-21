/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import br.usp.icmc.vicg.gl.util.Shader;
import javax.media.opengl.GL3;

/**
 *
 * @author Orlandi
 */
public class Wheel extends Object {
    private final float rotationSpeed;
    private float pSurfaceAngle;             /* save previous angle */
    
    public Wheel(String name, GL3 gl, Shader shader, String filePath) {
        super(name, gl, shader, filePath);
        
        rotationSpeed = 420;
        pSurfaceAngle = 0;
    }
    
    public void rotateWheel(float vehicleSpeed) {
        rotate(rotationSpeed * vehicleSpeed, 0, 0);
    }

    public float getpBridgeAngle() {
        return pSurfaceAngle;
    }

    public void setpSurfaceAngle(float pSurfaceAngle) {
        this.pSurfaceAngle = pSurfaceAngle;
    }
}
