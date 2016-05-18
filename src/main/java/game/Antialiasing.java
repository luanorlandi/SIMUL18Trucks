/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import br.usp.icmc.vicg.gl.util.Shader;
import javax.media.opengl.GL;
import javax.media.opengl.GL3;

/**
 *
 * @author Orlandi
 */
public class Antialiasing {
    private final Shader shader;
    private final GL3 gl;
    
    public Antialiasing(GL3 gl, Shader shader) {
        this.gl = gl;
        this.shader = shader;
    }
    
    public void init() {
        gl.glEnable(GL.GL_LINE_SMOOTH);
        gl.glEnable(GL.GL_BLEND);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
    }
}
