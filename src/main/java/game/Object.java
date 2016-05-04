/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import br.usp.icmc.vicg.gl.jwavefront.JWavefrontObject;
import br.usp.icmc.vicg.gl.matrix.Matrix4;
import br.usp.icmc.vicg.gl.util.Shader;
import java.io.File;
import java.io.IOException;
import javax.media.opengl.GL3;

/**
 *
 * @author Orlandi
 */
public class Object {
    private final String name;
    
    private final JWavefrontObject model;
    private final Matrix4 matrix;
    
    private float posX;
    private float posY;
    private float posZ;
    
    private float angleX;
    private float angleY;
    private float angleZ;
    
    private float sizeX;
    private float sizeY;
    private float sizeZ;
    
    public Object(String name, GL3 gl, Shader shader, String filePath) {
        this.name = name;
        
        model = new JWavefrontObject(new File(filePath));
        
        try {
            model.init(gl, shader);
            model.unitize();
            model.dump();
        } catch (IOException ex) {
            System.err.println("Model from " + filePath + "not initialized: " +
                ex.getMessage());
        }
        
        matrix = new Matrix4();
        matrix.init(gl, shader.getUniformLocation("u_modelMatrix"));
        
        reset();
    }
    
    public final void reset() {
        posX = 0;
        posY = 0;
        posZ = 0;
        
        angleX = 0;
        angleY = 0;
        angleZ = 0;
        
        sizeX = 1;
        sizeY = 1;
        sizeZ = 1;
    }
    
    public void translate(float x, float y, float z) {
        posX = posX + x;
        posY = posY + y;
        posZ = posZ + z;
    }
    
    public void rotate(float x, float y, float z) {
        angleX = angleX + x;
        angleY = angleY + y;
        angleZ = angleZ + z;
    }
    
    public void scale(float percent) {
        sizeX = sizeX * percent;
        sizeY = sizeY * percent;
        sizeZ = sizeZ * percent;
    }
    
    public void draw() {
        matrix.loadIdentity();
        matrix.translate(posX, posY, posZ);
        matrix.scale(sizeX, sizeY, sizeZ);
        matrix.rotate(angleY, 0, 1.0f, 0);
        matrix.rotate(angleZ, 0, 0, 1.0f);
        matrix.rotate(angleX, 1.0f, 0, 0);
        matrix.bind();
        
        model.draw();
    }

    public String getName() {
        return name;
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

    public float getAngleX() {
        return angleX;
    }

    public void setAngleX(float angleX) {
        this.angleX = angleX;
    }

    public float getAngleY() {
        return angleY;
    }

    public void setAngleY(float angleY) {
        this.angleY = angleY;
    }

    public float getAngleZ() {
        return angleZ;
    }

    public void setAngleZ(float angleZ) {
        this.angleZ = angleZ;
    }

    public float getSizeX() {
        return sizeX;
    }

    public void setSizeX(float sizeX) {
        this.sizeX = sizeX;
    }

    public float getSizeY() {
        return sizeY;
    }

    public void setSizeY(float sizeY) {
        this.sizeY = sizeY;
    }

    public float getSizeZ() {
        return sizeZ;
    }

    public void setSizeZ(float sizeZ) {
        this.sizeZ = sizeZ;
    }
}
