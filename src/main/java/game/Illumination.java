/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import br.usp.icmc.vicg.gl.core.Light;
import br.usp.icmc.vicg.gl.util.Shader;
import javax.media.opengl.GL3;

/**
 *
 * @author Orlandi
 */
public class Illumination {
    private final Light light;
    
    public Illumination() {
        light = new Light();
    }
    
    public void init(GL3 gl, Shader shader) {
        light.setPosition(new float[]{10.0f, 10.0f, 5.0f, 0.0f});
        light.setAmbientColor(new float[]{0.1f, 0.1f, 0.1f, 1.0f});
        light.setDiffuseColor(new float[]{1.0f, 1.0f, 1.0f, 1.0f});
        light.setSpecularColor(new float[]{0.1f, 0.1f, 0.1f, 1.0f});
        
//        light.setConstantAttenuation(1.0f);
//        light.setLinearAttenuation(1.0f);
//        light.setQuadraticAttenuation(1.0f);
        
        light.init(gl, shader);
        light.bind();
    }
    
    public void bind() {
        light.bind();
    }
}
