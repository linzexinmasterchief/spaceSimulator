package Application.logicUnit;

import Application.logicUnit.worldComponents.graphics.GraphicsThread;
import Application.logicUnit.worldComponents.operation.OperationThread;
import Application.logicUnit.worldComponents.physics.PhysicsThread;
import Application.Launcher;
import Application.logicUnit.worldComponents.physics.physicsComponents.universeComponents.Camera;
import Application.logicUnit.worldComponents.physics.physicsComponents.universeComponents.Star;
import Application.logicUnit.worldComponents.physics.physicsComponents.Universe;
import Application.status.SystemStatus;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Created by lzx on 2017/6/13.
 * initialize at start, then every components are created under it
 */
public class World {

    private final Launcher launcher;

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
    //a line used to tell the new star direction and speed
    private double[] dragLine;

    public World(Launcher starter) {
        launcher = starter;
        //call the function to initialize all the stars in the universe
        initialize();
    }

    //function used to initialize all the stars to their default value
    private void initialize() {
        //initialize the universe
        universe = new Universe(100000, 100000);

        //initialize the buffer star
        bufferStar = new Star();

        //initialize the camera
        camera = new Camera(
                SystemStatus.getScreenwidth(),
                SystemStatus.getScreenHeight(),
                universe.getWidth() / 2,
                universe.getHeight() / 2
        );

        dragLine = new double[4];

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

    //clear drag line
    public void clearDragLine(){
        dragLine[0] = 0;
        dragLine[1] = 0;
        dragLine[2] = 0;
        dragLine[3] = 0;
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

    public double[] getDragLine(){
        return dragLine;
    }

    public PhysicsThread getPhysicsThread(){
        return physicsThreadModule;
    }

    public GraphicsThread getGraphicsThread(){
        return graphicsThreadModule;
    }

    public void setBufferStar(Star star) {
        bufferStar.cloneStar(star);
    }
}
