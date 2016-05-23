/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import br.usp.icmc.vicg.gl.core.Light;
import br.usp.icmc.vicg.gl.core.Material;
import br.usp.icmc.vicg.gl.matrix.Matrix4;
import br.usp.icmc.vicg.gl.model.SimpleModel;
import br.usp.icmc.vicg.gl.model.SolidSphere;
import br.usp.icmc.vicg.gl.util.Shader;
import javax.media.opengl.GL3;

/**
 *
 * @author Orlandi
 */
public class Illumination {
    private final Light light;
    private final Material material;
    
    private Matrix4 matrix;
    private final SimpleModel lamp;
    
    private float pos[];
    private float size[];
    
    public Illumination() {
        light = new Light();
        material = new Material();
        
        lamp = new SolidSphere();
        
        pos = new float[]{-26.0f, 0.5f, 0.0f, 1.0f};
        size = new float[]{0.4f, 0.4f, 0.4f};
    }
    
    public void init(GL3 gl, Shader shader) {
//        light.setPosition(pos);
//        light.setAmbientColor(new float[]{0.1f, 0.1f, 0.1f, 1.0f});
//        light.setDiffuseColor(new float[]{1.0f, 1.0f, 1.0f, 1.0f});
//        light.setSpecularColor(new float[]{1.0f, 1.0f, 1.0f, 1.0f});
//        
//        light.setConstantAttenuation(1.0f);
//        light.setLinearAttenuation(0.15f);
//        light.setQuadraticAttenuation(0.1f);
//        
//        light.init(gl, shader);
//        light.bind();
        
        material.init(gl, shader);
        material.setAmbientColor(new float[]{0.1f, 0.1f, 0.1f, 1.0f});
        material.setDiffuseColor(new float[]{1.0f, 1.0f, 0.0f, 1.0f});
        material.setSpecularColor(new float[]{0.1f, 0.1f, 0.1f, 1.0f});
        material.setSpecularExponent(64.0f);
        material.bind();
        
        matrix = new Matrix4();
        matrix.init(gl, shader.getUniformLocation("u_modelMatrix"));
        
        lamp.init(gl, shader);
    }
    
    public void bind() {
//        light.bind();
        material.bind();
        
        matrix.loadIdentity();
        matrix.translate(pos[0], pos[1], pos[2]);
        matrix.scale(size[0], size[1], size[2]);
        matrix.bind();
        
        lamp.bind();
        lamp.draw();
    }
}
