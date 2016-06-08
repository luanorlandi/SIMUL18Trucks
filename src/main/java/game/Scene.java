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
    private final Shader shaderSkybox;
    private GL3 gl;
    
    private final Illumination illumination;    /* class config for light */
    
    private final Input input;
    
    private Camera camera;
    
    private final String skyboxFilePath;
    private final String bridgeFilePath;
    private String carFilePath;
    private final String truckFilePath;
    private final String frontWheelsPath;
    private final String backWheels1Path;
    private final String backWheels2Path;
    private final String murciWheelsPath;
    private final String dicePath;
    
    private Object skybox;
    
    private Bridge bridge;
    private ArrayList<Vehicle> cars;
    private Vehicle truck;
    
    private Object selected;             /* affected by edit mode */
    private int selectedId;              /* position in array objects */
    
    private ArrayList<Object> objects;  /* list with all objects */
    
    private Scene() {
        shader = ShaderFactory.getInstance(
            ShaderFactory.ShaderType.COMPLETE_SHADER);
        
        shaderSkybox = ShaderFactory.getInstance(
            ShaderFactory.ShaderType.SKYBOX_SHADER);
        
        illumination = new Illumination();
        
        input = Input.getInstance();
        
        bridgeFilePath = "./model/bridge/bridge.obj";
        skyboxFilePath = "./model/Skybox/skybox.obj";
        carFilePath = "./model/murci/murci.obj";
        truckFilePath = "./model/Ogre_Semi/Ogre_Semi.obj";
        frontWheelsPath = "./model/Wheels/FrontWheels/FrontWheels.obj";
        backWheels1Path = "./model/Wheels/BackWheels1/BackWheels1.obj";
        backWheels2Path = "./model/Wheels/BackWheels2/BackWheels2.obj";
        murciWheelsPath = "./model/murciWheels/murciwheels.obj";
        dicePath = "./model/dice/dice.obj";
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
        gl.glCullFace(GL.GL_BACK);
        
        shader.init(gl);
        shaderSkybox.init(gl);
        shader.bind();
        
        input.moveCursorToCenter();
        
        illumination.init(gl, shader);
        
        camera = new Camera(gl, shader);
        camera.setPosX(-31.70f);
        camera.setPosY(-0.74f);
        camera.setPosZ(0.748f);
        
        skybox = new Object("skybox", gl, shaderSkybox, skyboxFilePath);
        skybox.rotate(0.0f, 180.0f, 0.0f);
                
        bridge = new Bridge(gl, shader, bridgeFilePath);
        
        truck = new Vehicle("truck", gl, shader, truckFilePath);
        truck.scale(1.1f);
        truck.translate(-29.00f, -0.74f, 0.748f);
        truck.rotate(0.0f, 90.0f, 0.0f);
        truck.setTranslation(0);
        truck.setWheelTranslation(-0.277f);
        truck.setMaxReverse(-0.05f);
        truck.setMaxSpeed(0.2f);
        
        Wheel frontWheels = new Wheel("frontWheels", gl, shader, frontWheelsPath);
        frontWheels.scale(1.702f);
        frontWheels.translate(-28.395f, -1.02f, 0.748f);
        frontWheels.rotate(0.0f, 90.0f, 0.0f);
        
        Wheel backWheels1 = new Wheel("backWheels1", gl, shader, backWheels1Path);
        backWheels1.scale(0.748f);
        backWheels1.translate(-29.395f, -1.025f, 0.747f);
        backWheels1.rotate(0.0f, 90.0f, 0.0f);

        Wheel backWheels2 = new Wheel("backWheels2", gl, shader, backWheels2Path);
        backWheels2.scale(0.512f);
        backWheels2.translate(-29.63f, -1.025f, 0.747f);
        backWheels2.rotate(0.0f, 90.0f, 0.0f);
        
        truck.getWheels().add(frontWheels);
        truck.getWheels().add(backWheels1);
        truck.getWheels().add(backWheels2);
        
        truck.addDLC("dice", gl, shader, dicePath);
        truck.getDlc().scale(1.1f);
        truck.getDlc().translate(-28.609f, -0.689f, 0.748f);
        truck.getDlc().rotate(0.0f, 90.0f, -5.0f);
        
        carFilePath = "./model/murci0/murci0.obj";
        Vehicle car = new Vehicle("car", gl, shader, carFilePath);
        
        cars = new ArrayList<>();
        cars.add(car);
        cars.get(0).translate(-29.00f, -0.99f, 0.237f);
        cars.get(0).rotate(-1.0f, 90.0f, 0.0f);
        cars.get(0).scale(0.437f);
        cars.get(0).setSpeed(0.21f);
        cars.get(0).setTranslation(-0.260f);
        cars.get(0).setWheelTranslation(-0.316f);
        
        Wheel murci0frontWheels = new Wheel("murci0frontWheels", gl, shader, murciWheelsPath);
        murci0frontWheels.translate(-28.749f, -1.06f, 0.237f);
        murci0frontWheels.rotate(0.0f, 90.0f, 0.0f);
        murci0frontWheels.scale(0.255f);
        
        Wheel murci0backWheels = new Wheel("murci0backWheels", gl, shader, murciWheelsPath);
        murci0backWheels.translate(-29.297f, -1.06f, 0.237f);
        murci0backWheels.rotate(0.0f, 90.0f, 0.0f);
        murci0backWheels.scale(0.255f);
        
        cars.get(0).getWheels().add(murci0frontWheels);
        cars.get(0).getWheels().add(murci0backWheels);
        
        carFilePath = "./model/murci1/murci1.obj";
        car = new Vehicle("car", gl, shader, carFilePath);
        cars.add(car);
        cars.get(1).translate(-29.00f, -0.99f, -0.228f);
        cars.get(1).rotate(0.0f, 270.0f, 0.0f);
        cars.get(1).scale(0.437f);
        cars.get(1).setSpeed(-0.15f);
        cars.get(1).setTranslation(-0.260f);
        cars.get(1).setWheelTranslation(-0.316f);
        
        Wheel murci1frontWheels = new Wheel("murci1frontWheels", gl, shader, murciWheelsPath);
        murci1frontWheels.translate(-29.241f, -1.06f, -0.228f);
        murci1frontWheels.rotate(0.0f, 90.0f, 0.0f);
        murci1frontWheels.scale(0.255f);
        
        Wheel murci1backWheels = new Wheel("murci1backWheels", gl, shader, murciWheelsPath);
        murci1backWheels.translate(-28.704f, -1.06f, -0.228f);
        murci1backWheels.rotate(0.0f, 90.0f, 0.0f);
        murci1backWheels.scale(0.255f);
        
        cars.get(1).getWheels().add(murci1frontWheels);
        cars.get(1).getWheels().add(murci1backWheels);
              
        objects = new ArrayList<>();
        objects.add(truck);
        objects.add(truck.getDlc());
        objects.add(cars.get(0));
        objects.add(cars.get(1));
        objects.add(bridge.getBridgeObject());
        objects.add(frontWheels);
        objects.add(backWheels1);
        objects.add(backWheels2);
        objects.add(murci0frontWheels);
        objects.add(murci0backWheels);
        objects.add(murci1frontWheels);
        objects.add(murci1backWheels);
        
        //selected = objects.get(0);
        selected = skybox;
        selectedId = 0;
    }

    @Override
    public void dispose(GLAutoDrawable glad) {

    }

    @Override
    public void display(GLAutoDrawable glad) {
        gl.glClear(GL3.GL_COLOR_BUFFER_BIT | GL3.GL_DEPTH_BUFFER_BIT);
        processInput();
        
        cars.get(0).move(bridge);
        cars.get(1).move(bridge);
        
        camera.followObject(truck);
        camera.checkFirstPerson();
        camera.perpective();
        
        /* draw skybox */
        illumination.bindSkybox(camera.getPosX(),
                camera.getPosY(), camera.getPosZ());
        
        gl.glDisable(GL.GL_DEPTH_TEST);
        skybox.setPosX(camera.getPosX());
        skybox.setPosY(camera.getPosY());
        skybox.setPosZ(camera.getPosZ());
        skybox.draw();
        gl.glEnable(GL.GL_DEPTH_TEST);

        illumination.bindSun();
        
        /* draw truck */
        truck.draw();
        for(Wheel w : truck.getWheels()) {
            w.draw();
        }
        
        /* draw dlc */
        if(truck.isInterior()) {
            truck.getDlc().draw();
        }
        
        /* draw all bridge */
        bridge.reposition(truck.getPosX());
        bridge.draw();
        
        /* draw others cars */
        for(Vehicle c : cars) {
            c.draw(); 
            for (Wheel w : c.getWheels()) {
                w.draw();
            }         
            if (c.getPosX() > bridge.getPos3()[0]) {
                c.setPosX(bridge.getPos1()[0]);
                c.getWheels().get(0).setPosX(bridge.getPos1()[0] + 0.251f);
                c.getWheels().get(1).setPosX(bridge.getPos1()[0] - 0.297f);
            } else if(c.getPosX() < bridge.getPos1()[0]) {
                c.setPosX(bridge.getPos3()[0]);
                c.getWheels().get(0).setPosX(bridge.getPos3()[0] - 0.251f);
                c.getWheels().get(1).setPosX(bridge.getPos3()[0] + 0.297f);
            }
        }
        
        camera.checkFirstPerson2();
        
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
        System.out.format("\tview(%.3f, %.3f, %.3f)%n",
                camera.getViewX(), camera.getViewY(), camera.getViewZ());
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
        
        System.out.println("Object selected: " + selected.getName());
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
            
            if(input.isCameraRotateLeft()) {camera.spin(1.0f, 0.0f, 0.0f);}
            if(input.isCameraRotateRight()) {camera.spin(-1.0f, 0.0f, 0.0f);}
            
            if(input.isCameraRotateUp()) {camera.spin(0.0f, 1.0f, 0.0f);}
            if(input.isCameraRotateDown()) {camera.spin(0.0f, -1.0f, 0.0f);}
            
            float speedY = truck.move(bridge);
            camera.translate(truck.getSpeed(), speedY, 0.0f);
        } else {
            float to = 0.05f/100;
            float ro = 0.05f/100;
            float tc = 0.05f/100;
            float rc = 0.5f/100;
            
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

            if(input.objDec)        selected.scale(0.99f);
            if(input.objInc)        selected.scale(1.01f);

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

    public Camera getCamera() {
        return camera;
    }
}
