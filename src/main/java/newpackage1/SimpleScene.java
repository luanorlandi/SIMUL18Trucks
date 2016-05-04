package newpackage1;
import br.usp.icmc.vicg.gl.matrix.Matrix4;
import br.usp.icmc.vicg.gl.model.SimpleModel;
import br.usp.icmc.vicg.gl.model.Sphere;
import br.usp.icmc.vicg.gl.model.WiredCube;
import br.usp.icmc.vicg.gl.util.Shader;
import br.usp.icmc.vicg.gl.util.ShaderFactory;
import java.util.ArrayList;
import javax.media.opengl.GL;
import javax.media.opengl.GL3;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;

public class SimpleScene implements GLEventListener {
    
    // le os arquivos e carrega na placa grafica
    private Shader shader;
    private SimpleModel cube;
    private SimpleModel sphere;
    private Matrix4 modelMatrix;
    private float delta;
    private float inc;
    
    private int n = 10;
    SimpleModel[] cubes = new SimpleModel[n];
    SimpleModel[] cubes2 = new SimpleModel[n];
    
    public SimpleScene () {
        shader = ShaderFactory.getInstance(ShaderFactory.ShaderType.MODEL_MATRIX_SHADER);
        cube = new WiredCube();
        sphere = new Sphere();
        modelMatrix = new Matrix4();
        inc = 0.01f;
        
        
        for(int i = 0; i < n; i++) {
            cubes[i] = new WiredCube();
            cubes2[i] = new WiredCube();
        }
    }

    @Override
    public void init(GLAutoDrawable glad) {
        GL3 gl = glad.getGL().getGL3();
        
        // pintar a tela de branco
        gl.glClearColor(1.0f,1.0f,1.0f,1.0f);
        
        shader.init(gl);
        shader.bind();
        
        cube.init(gl, shader);
        sphere.init(gl, shader);
        
        for(int i = 0; i < n; i++) {
            cubes[i].init(gl, shader);
            cubes2[i].init(gl, shader);
        }
        
        modelMatrix.init(gl,shader.getUniformLocation("u_modelMatrix"));
    }
    
    @Override
    public void display(GLAutoDrawable glad) {
        GL3 gl = glad.getGL().getGL3();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        
//        modelMatrix.loadIdentity();
//        modelMatrix.translate(0.5f, 0.5f, 0.0f);
//        //modelMatrix.rotate(0,0,0,0);
//        modelMatrix.scale(0.25f,0.25f,0.25f);
//        modelMatrix.rotate(15,1,1,0);
//        modelMatrix.translate(0.01f,0,0);
//        modelMatrix.bind();
//        
//        cubes[1].bind();
//        cubes[1].draw();
//        
//        modelMatrix.loadIdentity();
//        modelMatrix.translate(0.252f, 0.5f, 0.0f);
//        //modelMatrix.rotate(0,0,0,0);
//        modelMatrix.scale(0.25f,0.25f,0.25f);
//        modelMatrix.rotate(15,1,1,0);
//        modelMatrix.translate(0.01f,0,0);
//        modelMatrix.bind();
//        
//        cubes[2].bind();
//        cubes[2].draw();
        
        float x = -1;
        
        for(int i = 0; i < n; i++) {
            modelMatrix.loadIdentity();
            modelMatrix.translate(x, 0.5f, 0.0f);
            //modelMatrix.rotate(0,0,0,0);
            modelMatrix.scale(0.25f,0.25f,0.25f);
            modelMatrix.rotate(15,1,1,0);
            modelMatrix.translate(0.01f,0,0);
            modelMatrix.bind();

            cubes[i].bind();
            cubes[i].draw();
            
            x += 0.248;
        }
        
        
        x = -1;
        
        for(int i = 0; i < n; i++) {
            modelMatrix.loadIdentity();
            modelMatrix.translate(x, -0.5f, 0.0f);
            //modelMatrix.rotate(0,0,0,0);
            modelMatrix.scale(0.25f,0.25f,0.25f);
            modelMatrix.rotate(15,1,1,0);
            modelMatrix.translate(0.01f,0,0);
            modelMatrix.bind();

            cubes2[i].bind();
            cubes2[i].draw();
            
            x += 0.248;
        }
        
        //cube.bind();
        //cube.draw();
        
        // -----------------------------
        
        delta += inc;
        if (delta > 0.5f || delta < 0) {
            inc = -inc;
        }
        
        modelMatrix.loadIdentity();
        modelMatrix.translate(0.5f, delta, 0.0f);
        modelMatrix.rotate(33,1,1,0);
        modelMatrix.scale(0.125f,0.125f,0.125f);
        //modelMatrix.rotate(45,1,0,0);
        //modelMatrix.translate(0.01f,0,0);
        modelMatrix.bind();
        
        sphere.bind();
        sphere.draw();
    }

    @Override
    public void dispose(GLAutoDrawable glad) {
    }

    @Override
    public void reshape(GLAutoDrawable glad, int i, int i1, int i2, int i3) {
    }
    
}