/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import br.usp.icmc.vicg.gl.util.Shader;
import java.util.ArrayList;
import javax.media.opengl.GL3;

/**
 *
 * @author Orlandi
 */
public class Vehicle extends Object {
    private float speed;
    private float acceleration;
    private float max_speed;
    private float max_reverse;
    
    private float translation;
    private float wheeltranslation;
    private float pSurfaceAngle;             /* save previous angle */
    
    private boolean interior;
    private Object dlc;
    
    private final ArrayList<Wheel> wheels;
    
    public Vehicle(String name, GL3 gl, Shader shader, String filePath) {
        super(name, gl, shader, filePath);
        
        speed = 0;
        acceleration = 0;
        //max_speed = 0.2f;
        //max_reverse = -0.05f;
        
        pSurfaceAngle = 0.0f;
        
        wheels = new ArrayList();
        interior = false;
    }
    
    public float move(Surface surface) {
        /* move in x */   
        speed += acceleration;
        
        translate(speed, 0.0f, 0.0f);
        
        float newY = surface.height(this.getPosX());
        float newR = surface.rotation(this.getPosX());
        
        float speedY = (newY + translation) - this.getPosY();
        
        this.setPosY(newY + translation);
        this.rotate(newR - pSurfaceAngle, 0, 0);
        
        /* move and rotate all wheels */
        for(Wheel w : wheels) {
            w.translate(speed, 0, 0);
            float newWheelY = surface.height(w.getPosX());
            float newWheelR = surface.rotation(w.getPosX());
        
            w.setPosY(newWheelY);
//            w.rotate(newWheelR - w.getpBridgeAngle(), 0, 0);
            w.rotateWheel(speed);
            w.translate(0, wheeltranslation, 0);
        }
        
        /* reposition interior */
        if(interior) {
            dlc.translate(speed, 0.0f, 0.0f);
            dlc.setAngleX(surface.rotation(dlc.getPosX()));
            
            float interiorY = surface.height(dlc.getPosX());
            dlc.setPosY(interiorY + 0.060f);
            
            float interiorRZ = 0;
            
            if(max_speed != 0) {
                interiorRZ = (float) Math.sin(2*Math.PI*speed/(max_speed/15));
            } else {
                System.err.println("Dive by zero avoided in roation interior");
            }
            
            dlc.rotate(0, 0, interiorRZ/3.5f);
            
            if(acceleration == 0.0f) {
                dlc.setAngleZ(dlc.getAngleZ()/1.05f);
            }
        }
        
        pSurfaceAngle = newR;       /* new previous angle */
        
        return speedY;
    }
    
    /* add Downloadable Content to vehicle interior */
    public void addDLC(String name, GL3 gl, Shader shader, String filePath) {
        dlc = new Object(name, gl, shader, filePath);
        interior = true;
    }
    
    public void setTranslation(float translation) {
        this.translation = translation;
    }
    
    public void setWheelTranslation(float wheeltranslation) {
        this.wheeltranslation = wheeltranslation;
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

    public float getMaxSpeed() {
        return max_speed;
    }
    
    public void setMaxSpeed(float max_speed) {
        this.max_speed = max_speed;
    }
    
    public float getMaxReverse() {
        return max_reverse;
    }
    
    public void setMaxReverse(float max_reverse) {
        this.max_reverse = max_reverse;
    }
    
    public void setAcceleration(float acceleration) {
        if (speed < 0) {
            this.acceleration = 5 * acceleration;
        }
        else if (speed > max_speed) this.acceleration = 0;
        else this.acceleration = acceleration;
    }
    
    public void setDesacceleration(float acceleration) {
        if (speed != 0) {
            if (speed > 0) {
                this.acceleration = - acceleration;
            }
            else this.acceleration = acceleration;
            
            if (speed < 0.0005f && speed > -0.0005f) {
                speed = 0;
                this.acceleration = 0;
            }
        }
    }
    
    public void setBreak(float acceleration) {
        if (speed > 0) {
            this.acceleration = acceleration;
        }
        else if (speed < max_reverse) this.acceleration = 0;
        else this.acceleration = acceleration / 10;
    }

    public ArrayList<Wheel> getWheels() {
        return wheels;
    }

    public boolean isInterior() {
        return interior;
    }

    public void setInterior(boolean interior) {
        this.interior = interior;
    }

    public Object getDlc() {
        return dlc;
    }
}
