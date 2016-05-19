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
public class Vehicle extends Object {
    private float speed;
    private float acceleration;
    private float max_speed;
    private float max_reverse;
            
    private float velocityX;
    private float velocityY;
    private float xPos;
    private float yPos;
    
    private final float cY[];
    private final float cR[];
    
    public Vehicle(String name, GL3 gl, Shader shader, String filePath) {
        super(name, gl, shader, filePath);
        
        speed = 0;
        acceleration = 0;
        max_speed = 0.001f;
        max_reverse = -0.001f;
        
        cY = new float[5];
        cY[0] = (float) -2.09687270222880e+002;
        cY[1] = (float) -1.73677692452703e+000;
        cY[2] = (float) -2.17934186065177e+000;
        cY[3] = (float) -7.55762104308736e-002;
        cY[4] = (float) -8.83708721852949e-004;
        
        cR = new float[5];
        cR[0] = (float) 1.06358436789827e+002;
        cR[1] = (float) 5.35603485093407e+002;
        cR[2] = (float) 5.84694281292338e+001;
        cR[3] = (float) 2.62318796899470e+000;
        cR[4] = (float) 4.24757612400653e-002;
    }
    
    public void steering() {
        double degreesPerFrame = 180 / (2*30); //180 degrees over 30 fps in 2s
        velocityX = (float) (velocityX * -1 * Math.cos(degreesPerFrame));
        velocityY = (float) (velocityY * -1 * Math.sin(degreesPerFrame));
        double yChange = Math.sin(degreesPerFrame) * velocityY;
        double xChange = Math.cos(degreesPerFrame) * velocityX;
        xPos += xChange; //change x position
        yPos += yChange; //change y position
    
        translate(xPos, yPos, 0.0f);
    }
    
    public void move() {
        /* move in x */
        speed += acceleration;
        
        if(acceleration == 0 && speed != 0) {
            if(speed > 0) {
                speed -= 0.0002f;
            } else {
                speed += 0.0002f;
            }
        }
        
        if(speed > max_speed && speed < max_reverse) {
            speed = 0;
        }
        
        translate(speed, 0.0f, 0.0f);
        
        float x = this.getPosX();
        if(x > 0) {
            x *= -1;
        }
        
        /* move in y */
        float y = 0;
        
        for(int i = 0; i < cY.length; i++) {
            y += cY[i] * Math.pow(x, i);
        }
        
        y /= 1000;
        this.setPosY(y);
        
        /* rotate in z */
        float r = 0;
        
        for(int i = 0; i < cR.length; i++) {
            r += cR[i] * Math.pow(x, i);
        }
        
        r /= 1000;
        
        if(this.getPosX() > 0) {
            r *= -1;
        }
        this.setAngleX(r);
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
        if (speed < 0) {
            this.acceleration = 5 * acceleration;
        }
        else this.acceleration = acceleration;
    }
    
    public void setDesacceleration(float acceleration) {
        if (speed != 0) {
            if (speed > 0) {
                this.acceleration = - acceleration;
            }
            else this.acceleration = acceleration;
        }
    }
    
    public void setBreak(float acceleration) {
        if (speed > 0) {
            this.acceleration = acceleration;
        }
        else this.acceleration = acceleration / 10;
    }
}
