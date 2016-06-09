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
    
    private final float[] sunPos;
    
    public Illumination() {
        light = new Light();
        
        sunPos = new float[]{100.0f, 100.0f, -100.0f, 0.0f};
    }
    
    public void init(GL3 gl, Shader shader) {
        light.init(gl, shader);
    }
    
    public void bindSun() {
        light.setPosition(new float[]{sunPos[0], sunPos[1], sunPos[2], 0.0f});
        light.setAmbientColor(new float[]{0.1f, 0.1f, 0.1f, 1.0f});
        light.setDiffuseColor(new float[]{1.0f, 1.0f, 1.0f, 1.0f});
        light.setSpecularColor(new float[]{0.4f, 0.4f, 0.4f, 1.0f});
        light.bind();
    }
    
    public void bindSkybox(float camX, float camY, float camZ) {
        light.setPosition(new float[]{camX, camY, camZ, 1.0f});
        light.setAmbientColor(new float[]{0.2f, 0.2f, 0.2f, 1.0f});
        light.setDiffuseColor(new float[]{1.0f, 1.0f, 1.0f, 1.0f});
        light.setSpecularColor(new float[]{0.0f, 0.0f, 0.0f, 1.0f});
        light.bind();
    }
}
