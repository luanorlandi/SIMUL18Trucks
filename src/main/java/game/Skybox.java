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
public class Skybox extends Object {
    
    public Skybox(String name, GL3 gl, Shader shader, String filePath) {
        super(name, gl, shader, filePath);
    }
}
