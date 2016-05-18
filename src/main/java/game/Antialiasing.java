/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import javax.media.opengl.GL3;

/**
 *
 * @author Orlandi
 */
public class Antialiasing {
    private final GL3 gl;
    
    public Antialiasing(GL3 gl) {
        this.gl = gl;
    }
    
    public void init() {
        gl.glEnable(GL3.GL_POLYGON_SMOOTH);
        gl.glEnable(GL3.GL_BLEND);
        gl.glBlendFunc(GL3.GL_SRC_ALPHA, GL3.GL_ONE_MINUS_SRC_ALPHA);
    }
}
