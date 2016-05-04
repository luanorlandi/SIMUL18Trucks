/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import br.usp.icmc.vicg.gl.util.Shader;
import javax.media.opengl.GL3;
import static java.lang.Math.*;

/**
 *
 * @author Orlandi
 */
public class Vehicle extends Object {
    private float speed;
    private float acceleration;
    
    public Vehicle(String name, GL3 gl, Shader shader, String filePath) {
        super(name, gl, shader, filePath);
        
        speed = 0;
        acceleration = 0;
    }
    
    public void move() {
        /* move in x */
        speed += acceleration;
        
        if(acceleration == 0 && speed != 0) {
            if(speed > 0) {
                speed -= 0.005f;
            } else {
                speed += 0.005f;
            }
            
            if(speed < 0.02f && speed > -0.02f) {
                speed = 0;
            }
        }
        
        translate(speed, 0.0f, 0.0f);
        
        /* move in y */
        float x = this.getPosX();
        float y;
        
        y = (float) cos(x/11);  /* frequency */
        y = (float) y+1;        /* translate in y */
        y = (float) y/3.4f;     /* amplitude */
        
        y -= 0.80f;
        this.setPosY(y);
        /* rotate in z */
        
        float rotZ = (float) ((-1) * sin(x/11)/(3.4f * 11) * 2000);
//        System.out.println("angle: " + rotZ);
//        this.setAngleZ(rotZ);
    }

    public float getSpeed() {
        return speed;
    }

    public float getAcceleration() {
        return acceleration;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }
}
