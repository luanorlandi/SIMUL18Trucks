/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import br.usp.icmc.vicg.gl.matrix.Matrix4;
import br.usp.icmc.vicg.gl.util.Shader;
import static java.lang.Math.*;
import javax.media.opengl.GL3;

/**
 *
 * @author Orlandi
 */
public class Camera {
    private final Matrix4 projectionMatrix;
    private final Matrix4 viewMatrix;
    
    private final float ortho;
    
    private float posX;
    private float posY;
    private float posZ;
    
    private float viewX;
    private float viewY;
    private float viewZ;
    
    private float upX;
    private float upY;
    private float upZ;
    
    private final float firstPersonX;
    private final float firstPersonY;
    private final float firstPersonZ;
    
    public float theta;
    public float phi;
    
    private boolean firstPerson;
    
    public Camera(GL3 gl, Shader shader) {
        projectionMatrix = new Matrix4();
        viewMatrix = new Matrix4();
        
        projectionMatrix.init(gl, shader.getUniformLocation("u_projectionMatrix"));
        viewMatrix.init(gl, shader.getUniformLocation("u_viewMatrix"));
        
        ortho = 5;
        
        firstPerson = false;
        
        firstPersonX = 0.240f;
        firstPersonY = 0.035f;
        firstPersonZ = -0.068f;
        
        reset();
    }
    
    public final void reset() {
        posX = 10;
        posY = 0;
        posZ = 0;
        
        viewX = 0;
        viewY = 0;
        viewZ = 0;
        
        upX = 0;
        upY = 1;
        upZ = 0;
        
        theta = 0.0f;
        phi = -90.0f;
    }
    
    public void translate(float x, float y, float z) {
        posX = posX + x;
        posY = posY + y;
        posZ = posZ + z;
    }
    
    public void center(float x, float y, float z) {
        viewX = viewX + x;
        viewY = viewY + y;
        viewZ = viewZ + z;
    }
    
    public void spinX(float theta) {
        posY -= viewY;
        posZ -= viewZ;
        
        posY = (float) (posY*cos(toRadians(theta)) - posZ*sin(toRadians(theta)));
        posZ = (float) (posY*sin(toRadians(theta)) + posZ*cos(toRadians(theta)));
        
        posY += viewY;
        posZ += viewZ;
    }
    
    public void spinY(float theta) {
        posZ -= viewZ;
        posX -= viewX;
        
        posZ = (float) (posZ*cos(toRadians(theta)) - posX*sin(toRadians(theta)));
        posX = (float) (posZ*sin(toRadians(theta)) + posX*cos(toRadians(theta)));
        
        posZ += viewZ;
        posX += viewX;
    }
    
    public void spinZ(float theta) {
        posX -= viewX;
        posY -= viewY;
        
        posX = (float) (posX*cos(toRadians(theta)) - posY*sin(toRadians(theta)));
        posY = (float) (posX*sin(toRadians(theta)) + posY*cos(toRadians(theta)));
        
        posX += viewX;
        posY += viewY;
    }

    public void spin(float angleY, float angleXZ, float zoom) {
        if(firstPerson) {
            angleXZ *= -1;   /* reverse up down controls */
        }
        
        /* limit spin */
        if(phi + angleXZ >= 0.0f) {
            angleXZ = 0;
        } else if(phi + angleXZ <= -180.0f) {
            angleXZ = 0;
        }
        
        float rho = (float) sqrt(
            pow(posX-viewX, 2) +
            pow(posY-viewY, 2) +
            pow(posZ-viewZ, 2));
        
        rho += zoom;
        
        if(firstPerson) {
            rho = 1.0f;     /* radius fixed */
        }
        
        theta += angleY;
        phi += angleXZ;
        
        theta %= 360.0f;
        phi %= 360.0f;
        
        posX = (float) (viewX + rho *
            sin(toRadians(this.phi)) *
            cos(toRadians(this.theta)));
        
        posZ = (float) (viewZ + rho *
            sin(toRadians(this.phi)) *
            sin(toRadians(this.theta)));
        
        posY = (float) (viewY + rho *
            cos(toRadians(this.phi)));
    }
    
    /* if firstPerson is enable then swap view with pos */
    public void checkFirstPerson() {
        if(firstPerson) {
            float tmpX = this.getViewX();
            float tmpY = this.getViewY();
            float tmpZ = this.getViewZ();

            viewX = this.getPosX();
            viewY = this.getPosY();
            viewZ = this.getPosZ();

            posX = tmpX;
            posY = tmpY;
            posZ = tmpZ;
            
            posX += firstPersonX;
            posY += firstPersonY;
            posZ += firstPersonZ;
            
            viewX += firstPersonX;
            viewY += firstPersonY;
            viewZ += firstPersonZ;
        }
    }
    
    public void checkFirstPerson2() {
        if(firstPerson) {
            float tmpX = this.getViewX();
            float tmpY = this.getViewY();
            float tmpZ = this.getViewZ();

            viewX = this.getPosX();
            viewY = this.getPosY();
            viewZ = this.getPosZ();

            posX = tmpX;
            posY = tmpY;
            posZ = tmpZ;
            
            posX -= firstPersonX;
            posY -= firstPersonY;
            posZ -= firstPersonZ;
            
            viewX -= firstPersonX;
            viewY -= firstPersonY;
            viewZ -= firstPersonZ;
        }
    }

    public void followObject(Object obj) {
        viewX = obj.getPosX();
        viewY = obj.getPosY();
        viewZ = obj.getPosZ();
    }
    
    public void projection() {
        projectionMatrix.loadIdentity();
        projectionMatrix.ortho(
                -ortho, ortho, 
                -ortho, ortho, 
                -100 * ortho, 100 * ortho);
        projectionMatrix.bind();
    }
    
    public void perpective() {
        viewMatrix.loadIdentity();
        viewMatrix.lookAt(posX, posY, posZ, 
                viewX, viewY, viewZ,
                upX, upY, upZ);
        viewMatrix.bind();
        
        projectionMatrix.loadIdentity();
        projectionMatrix.perspective(60, 700/700, 0.01f, 100f);
        projectionMatrix.bind();
        
    }

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public float getPosZ() {
        return posZ;
    }

    public void setPosZ(float posZ) {
        this.posZ = posZ;
    }

    public float getViewX() {
        return viewX;
    }

    public void setViewX(float centerX) {
        this.viewX = centerX;
    }

    public float getViewY() {
        return viewY;
    }

    public void setViewY(float centerY) {
        this.viewY = centerY;
    }

    public float getViewZ() {
        return viewZ;
    }

    public void setViewZ(float centerZ) {
        this.viewZ = centerZ;
    }

    public float getUpX() {
        return upX;
    }

    public void setUpX(float upX) {
        this.upX = upX;
    }

    public float getUpY() {
        return upY;
    }

    public void setUpY(float upY) {
        this.upY = upY;
    }

    public float getUpZ() {
        return upZ;
    }

    public void setUpZ(float upZ) {
        this.upZ = upZ;
    }

    public boolean isFirstPerson() {
        return firstPerson;
    }

    public void setFirstPerson(boolean firstPerson) {
        this.firstPerson = firstPerson;
    }
}