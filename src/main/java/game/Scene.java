package game;

import br.usp.icmc.vicg.gl.util.Shader;
import br.usp.icmc.vicg.gl.util.ShaderFactory;
import java.util.ArrayList;
import javax.media.opengl.GL;
import javax.media.opengl.GL3;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;

/**
 *
 * @author Orlandi
 */
public class Scene implements GLEventListener {
    private static Scene scene;
    
    private final Shader shader;
    private GL3 gl;
    
    private final Illumination illumination;    /* class config for light */
    
    private final Input input;
    
    public Camera camera;
    
    private final String bridgeFilePath;
    private final String truckFilePath;
    private final String frontWheelsPath;
    private final String backWheels1Path;
    private final String backWheels2Path;
    
    public Object bridge;
    public Surface surface;
    public Vehicle truck;
    
    public Object selected;             /* affected by edit mode */
    public int selectedId;              /* position in array objects */
    
    private ArrayList<Object> objects;  /* list with all objects */
    
    private Scene() {
        shader = ShaderFactory.getInstance(
            ShaderFactory.ShaderType.COMPLETE_SHADER);
        
        illumination = new Illumination();
        
        input = Input.getInstance();
        
        bridgeFilePath = "./model/bridge/bridge.obj";
        truckFilePath = "./model/Ogre_Semi/Ogre_Semi.obj";
        frontWheelsPath = "./model/Wheels/FrontWheels/FrontWheels.obj";
        backWheels1Path = "./model/Wheels/BackWheels1/BackWheels1.obj";
        backWheels2Path = "./model/Wheels/BackWheels2/BackWheels2.obj";
    }
    
    public static Scene getInstance() {
        if(scene == null) {
            scene = new Scene();
        }
        
        return scene;
    }
    
    @Override
    public void init(GLAutoDrawable glad) {
        gl = glad.getGL().getGL3();
        System.out.println("OpenGL Version: " +
            gl.glGetString(GL.GL_VERSION) + "\n");
        
        gl.glClearColor(0.85f, 0.85f, 1.0f, 1.0f);
        gl.glClearDepth(1.0f);

        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glEnable(GL.GL_CULL_FACE);
        
        shader.init(gl);
        shader.bind();
        
        illumination.init(gl, shader);
        
        camera = new Camera(gl, shader);
        camera.setPosX(-31.70f);
        camera.setPosY(-0.46f);
        camera.setPosZ(-0.79f);
        
        bridge = new Object("bridge", gl, shader, bridgeFilePath);
        bridge.scale(30.0f);
        bridge.rotate(0.0f, 180.0f, 0.0f);
        
        surface = new Surface();
        
        truck = new Vehicle("truck", gl, shader, truckFilePath);
        truck.scale(1.1f);
        truck.translate(-29.00f, -0.74f, -0.79f);
        truck.rotate(0.0f, 90.0f, 0.0f);
        
        Wheel frontWheels = new Wheel("frontWheels", gl, shader, frontWheelsPath);
        frontWheels.scale(1.702f);
        frontWheels.translate(-28.395f, -1.02f, -0.79f);
        frontWheels.rotate(0.0f, 90.0f, 0.0f);
        
        Wheel backWheels1 = new Wheel("backWheels1", gl, shader, backWheels1Path);
        backWheels1.scale(0.748f);
        backWheels1.translate(-29.395f, -1.025f, -0.78f);
        backWheels1.rotate(0.0f, 90.0f, 0.0f);

        Wheel backWheels2 = new Wheel("backWheels2", gl, shader, backWheels2Path);
        backWheels2.scale(0.512f);
        backWheels2.translate(-29.63f, -1.025f, -0.78f);
        backWheels2.rotate(0.0f, 90.0f, 0.0f);
        
        truck.getWheels().add(frontWheels);
        truck.getWheels().add(backWheels1);
        truck.getWheels().add(backWheels2);
        
        objects = new ArrayList<>();
        objects.add(truck);
        objects.add(bridge);
        objects.add(frontWheels);
        objects.add(backWheels1);
        objects.add(backWheels2);
        
        selected = objects.get(0);
        selectedId = 0;
    }

    @Override
    public void dispose(GLAutoDrawable glad) {

    }

    @Override
    public void display(GLAutoDrawable glad) {
        processInput();
        
        gl.glClear(GL3.GL_COLOR_BUFFER_BIT | GL3.GL_DEPTH_BUFFER_BIT);
        
        illumination.bind();
        camera.followObject(truck);
        camera.perpective();
        
        bridge.draw();
        truck.draw();
        for(Wheel w : truck.getWheels()) {
            w.draw();
        }
        
        gl.glFlush();
    }

    @Override
    public void reshape(GLAutoDrawable glad, int i, int i1, int i2, int i3) {
        
    }
    
    public void showObjectsPositions() {
        System.out.format("%n" + selected.getName() + ": pos(%.3f, %.3f, %.3f)%n",
                selected.getPosX(), selected.getPosY(), selected.getPosZ()); 
        System.out.format("\tangle(%.3f, %.3f, %.3f)%n",
                selected.getAngleX(), selected.getAngleY(), selected.getAngleZ());
        System.out.format("\tsize(%.3f, %.3f, %.3f)%n",
                selected.getSizeX(), selected.getSizeY(), selected.getSizeZ()); 
        
        System.out.format("%nCamera: pos(%.3f, %.3f, %.3f)%n",
                camera.getPosX(), camera.getPosY(), camera.getPosZ()); 
        System.out.format("\tviewCenter(%.3f, %.3f, %.3f)%n",
                camera.getCenterX(), camera.getCenterY(), camera.getCenterZ());
        System.out.format("\tviewUp(%.3f, %.3f, %.3f)%n",
                camera.getUpX(), camera.getUpY(), camera.getUpZ()); 
    }
    
    public void changeObjectSelect() {
        if(selectedId >= objects.size() - 1) {
            selectedId = 0;
        } else {
            selectedId++;
        }
        
        selected = objects.get(selectedId);
    }
    
    private void processInput() {
        if(!input.isEditMode()) {
            if(input.isTruckMoveForward()) {
                truck.setAcceleration(0.0002f);
            }
            else if (!input.isTruckMoveForward() && !input.isTruckMoveBackward()) {
                truck.setDesacceleration(0.0002f);
            }

            if(input.isTruckMoveBackward()) {
                truck.setBreak(-0.001f);
            }
            else if (!input.isTruckMoveForward() && !input.isTruckMoveBackward()) {
                truck.setDesacceleration(0.0004f);
            }

            if (input.isTruckMoveRight()) {
                System.out.println(truck.getSpeed());
            }
            
            if(input.isCameraRotateLeft()) {
                camera.spinY(-1.0f);
            }

            if(input.isCameraRotateRight()) {
                camera.spinY(1.0f);
            }
            
            truck.move(surface);
            camera.translate(truck.getSpeed(), 0.0f, 0.0f);
        } else {
            float to = 1.005f;
            float ro = 1.05f;
            float tc = 1.05f;
            float rc = 1.5f;
            
            if(input.objTansXNeg)   selected.translate(-to, 0f, 0f);
            if(input.objTansXPos)   selected.translate(to, 0f, 0f);
            if(input.objTansYNeg)   selected.translate(0f, -to, 0f);
            if(input.objTansYPos)   selected.translate(0f, to, 0f);
            if(input.objTansZNeg)   selected.translate(0f, 0f, -to);
            if(input.objTansZPos)   selected.translate(0f, 0f, to);

            if(input.objRotXNeg)    selected.rotate(-ro, 0f, 0f);
            if(input.objRotXPos)    selected.rotate(ro, 0f, 0f);
            if(input.objRotYNeg)    selected.rotate(0f, -ro, 0f);
            if(input.objRotYPos)    selected.rotate(0f, ro, 0f);
            if(input.objRotZPos)    selected.rotate(0f, 0f, -ro);
            if(input.objRotZNeg)    selected.rotate(0f, 0f, ro);

            if(input.objDec)        selected.scale(0.9f);
            if(input.objInc)        selected.scale(1.1f);

            if(input.camTransXNeg)  camera.translate(-tc, 0f, 0f);
            if(input.camTransXPos)  camera.translate(tc, 0f, 0f);
            if(input.camTransYNeg)  camera.translate(0f, -tc, 0f);
            if(input.camTransYPos)  camera.translate(0f, tc, 0f);
            if(input.camTransZNeg)  camera.translate(0f, 0f, -tc);
            if(input.camTransZPos)  camera.translate(0f, 0f, tc);

            if(input.camRefXNeg)    camera.center(-tc, 0f, 0f);
            if(input.camRefXPos)    camera.center(tc, 0f, 0f);
            if(input.camRefYNeg)    camera.center(0f, -tc, 0f);
            if(input.camRefYPos)    camera.center(0f, tc, 0f);
            if(input.camRefZNeg)    camera.center(0f, 0f, -tc);
            if(input.camRefZPos)    camera.center(0f, 0f, tc);

            if(input.camRotXNeg)    camera.spinX(-rc);
            if(input.camRotXPos)    camera.spinX(rc);
            if(input.camRotYNeg)    camera.spinY(-rc);
            if(input.camRotYPos)    camera.spinY(rc);
            if(input.camRotZNeg)    camera.spinZ(-rc);
            if(input.camRotZPos)    camera.spinZ(rc);

            if(input.scenePositions) showObjectsPositions();
        }
    }
}
