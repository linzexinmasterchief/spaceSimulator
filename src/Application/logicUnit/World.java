package Application.logicUnit;

import Application.logicUnit.worldComponents.graphics.GraphicsThread;
import Application.logicUnit.worldComponents.operation.OperationThread;
import Application.logicUnit.worldComponents.physics.PhysicsThread;
import Application.Launcher;
import Application.logicUnit.worldComponents.physics.physicsComponents.Camera;
import Application.logicUnit.worldComponents.physics.physicsComponents.universeComponents.Star;
import Application.logicUnit.worldComponents.physics.physicsComponents.Universe;

import java.awt.*;

/**
 * Created by lzx on 2017/6/13.
 * initialize at start, then every components are created under it
 */
public class World {

    private Launcher launcher;

    private PhysicsThread physicsThreadModule;
    private GraphicsThread graphicsThreadModule;
    private OperationThread operationThreadModule;

    //used components
    //generate a universe
    private Universe universe;

    //use a bufferStar for new input star
    private Star bufferStar;
    //define a camera used for display
    private Camera camera;


    public World(Launcher starter) {
        launcher = starter;
        //call the function to initialize all the stars in the universe
        initialize();
    }

    //function used to initialize all the stars to their default value
    private void initialize() {
        //initialize the universe
        universe = new Universe(10000, 10000);

        //initialize the buffer star
        bufferStar = new Star();

        //initialize the camera
        Dimension screenSize = launcher.getScreenSize();
        camera = new Camera(
                screenSize.width,
                screenSize.height,
                universe.getWidth() / 2,
                universe.getHeight() / 2
        );


        //>>>>>>>>>>>>>>>>>>|[THREADS]|<<<<<<<<<<<<<<<<<<<<
        //physics module
        physicsThreadModule = new PhysicsThread(this);
        Thread physics = new Thread(physicsThreadModule);
        physics.start();
        //halt until game canvas is initialized

        //graphics module
        graphicsThreadModule = new GraphicsThread(this);
        Thread graphics = new Thread(graphicsThreadModule);
        graphics.start();
        //halt until game canvas is initialized

        operationThreadModule = new OperationThread(this);
        Thread operation = new Thread(operationThreadModule);
        operation.start();
        //halt until game canvas is initialized
    }

    //getter and setters
    public Launcher getLauncher(){
        return launcher;
    }

    public Star getBufferStar() {
        return bufferStar;
    }

    public Camera getCamera() {
        return camera;
    }

    public Universe getUniverse() {
        return universe;
    }

    public PhysicsThread getPhysicsThreadModule(){
        return physicsThreadModule;
    }

    public GraphicsThread getGraphicsThreadModule(){
        return graphicsThreadModule;
    }

}
