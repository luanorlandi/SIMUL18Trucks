package exemplo;

import br.usp.icmc.vicg.gl.core.Light;
import br.usp.icmc.vicg.gl.jwavefront.JWavefrontObject;
import br.usp.icmc.vicg.gl.matrix.Matrix4;
import br.usp.icmc.vicg.gl.util.Shader;
import br.usp.icmc.vicg.gl.util.ShaderFactory;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.opengl.GL;
import javax.media.opengl.GL3;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;

/**
 *
 * @author Orlandi
 */
public class Scene implements GLEventListener {

    private final Shader shader;
    private final JWavefrontObject model;
    
    private final Matrix4 modelMatrix;
    private final Matrix4 projectionMatrix;
    private final Matrix4 viewMatrix;
    
    private final Light light;
    
    private Input input;
    
    private float scale;
    
    private float rotX;
    private float rotY;
    private float rotZ;
    private float delta;
    
    public Scene() {
        shader = ShaderFactory.getInstance(ShaderFactory.ShaderType.COMPLETE_SHADER);
        model = new JWavefrontObject(new File("./model/bridge/bridge.obj"));
        
        modelMatrix = new Matrix4();
        projectionMatrix = new Matrix4();
        viewMatrix = new Matrix4();
        
        light = new Light();
        
        scale = 1;
        
        rotX = 0;
        rotY = 0;
        rotZ = 0;
        delta = 5;
        
        this.input = Input.getInstance();
    }
    
    private void readInput() {
        if(input.objectIncrease) {
            scale *= 1.1f;
        }
        
        if(input.objectDecrease) {
            scale /= 1.1f;
        }
        
        if(input.objectRotateLeft) {
            rotX += 2.0f;
        }
        
        if(input.objectRotateRight) {
            rotX -= 2.0f;
        }
        
        if(input.objectRotateUp) {
            rotZ += 2.0f;
        }
        
        if(input.objectRotateDown) {
            rotZ -= 2.0f;
        }
        
        if(input.objectRotateCW) {
            rotY += 2.0f;
        }
        
        if(input.objectRotateAntiCW) {
            rotY -= 2.0f;
        }
    }
    
    @Override
    public void init(GLAutoDrawable glad) {
        GL3 gl = glad.getGL().getGL3();
        System.out.println("OpenGL Version: " + gl.glGetString(GL.GL_VERSION) + "\n");
        
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        gl.glClearDepth(1.0f);

        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glEnable(GL.GL_CULL_FACE);
        
        shader.init(gl);
        shader.bind();
        
        try {
            model.init(gl, shader);
            model.unitize();
            model.dump();
        } catch (IOException ex) {
            Logger.getLogger(Scene.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        modelMatrix.init(gl, shader.getUniformLocation("u_modelMatrix"));
        projectionMatrix.init(gl, shader.getUniformLocation("u_projectionMatrix"));
        viewMatrix.init(gl, shader.getUniformLocation("u_viewMatrix"));
        
        light.setPosition(new float[]{10, 10, 50, 1.0f});
        light.setAmbientColor(new float[]{0.1f, 0.1f, 0.1f, 1.0f});
        light.setDiffuseColor(new float[]{0.75f, 0.75f, 0.75f, 1.0f});
        light.setSpecularColor(new float[]{0.7f, 0.7f, 0.7f, 1.0f});
        light.init(gl, shader);
    }

    @Override
    public void dispose(GLAutoDrawable glad) {

    }

    @Override
    public void display(GLAutoDrawable glad) {
        GL3 gl = glad.getGL().getGL3();
        
        gl.glClear(GL3.GL_COLOR_BUFFER_BIT | GL3.GL_DEPTH_BUFFER_BIT);
        
        readInput();
        
        projectionMatrix.loadIdentity();
        projectionMatrix.ortho(
                -delta, delta, 
                -delta, delta, 
                -100 * delta, 100 * delta);
        projectionMatrix.bind();

        modelMatrix.loadIdentity();
        modelMatrix.scale(scale, scale, scale);
        modelMatrix.rotate(rotX, 1.0f, 0, 0);
        modelMatrix.rotate(rotY, 0, 1.0f, 0);
        modelMatrix.rotate(rotZ, 0, 0, 1.0f);
        modelMatrix.bind();

        viewMatrix.loadIdentity();
        viewMatrix.lookAt(
                1, 1, 1, 
                0, 0, 0, 
                0, 1, 0);
        viewMatrix.bind();

        light.bind();

        model.draw();
        
        gl.glFlush();
    }

    @Override
    public void reshape(GLAutoDrawable glad, int i, int i1, int i2, int i3) {
    
    }
}
