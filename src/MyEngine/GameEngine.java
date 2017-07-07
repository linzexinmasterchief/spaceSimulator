package MyEngine;

import MyEngine.graphics.GraphicsModule;
import MyEngine.physics.PhysicsModule;
import Stages.MainStage.GameStage;
import Stages.MainStarter;
import models.Camera;
import models.Star;
import models.Universe;

/**
 * Created by lzx on 2017/6/13.
 * initialize at start, then every components are created under it
 */
public class GameEngine{

    private MainStarter mainStarter;

    //this game stage is at the same level as game engine
    //they both belong to MainStarter
    private GameStage gameStage;

    private PhysicsModule physicsModule;
    private GraphicsModule graphicsModule;

    private Thread physics;
    private Thread graphics;

    //used components
    //generate a universe
    private Universe universe;
    //store all the stars in this universe
    private Star[] stars;
    //use a bufferStar for new input star
    private Star bufferStar;
    //define a camera used for display
    private Camera camera;

    public GameEngine(MainStarter starter) {
        mainStarter = starter;
        //call the function to initialize all the stars in the universe
        initialize();
    }

    //function used to initialize all the stars to their default value
    private void initialize() {
        //initialize the universe
        universe = new Universe(10000, 10000);
        //initialize the star list of the universe
        stars = new Star[50];
        //initialize every star in the star list
        //this is very important and necessary, do not delete it
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star();
        }

        //initialize the buffer star
        bufferStar = new Star();

        //initialize the camera
        camera = new Camera(
                1000,
                560,
                universe.getWidth() / 2,
                universe.getHeight() / 2
        );

        //physics module
        physicsModule = new PhysicsModule(this);
        physics = new Thread(physicsModule);
        //halt until game canvas is initialized

        //graphics module
        graphicsModule = new GraphicsModule(this);
        graphics = new Thread(graphicsModule);
        //halt until game canvas is initialized
    }

    public void EXIT(){
        physicsModule.setExit(true);
        graphicsModule.setExit(true);
    }

    //getter and setters
    public Star[] getStars() {
        return stars;
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

    public PhysicsModule getPhysicsModule(){
        return physicsModule;
    }

    public GraphicsModule getGraphicsModule(){
        return graphicsModule;
    }

    public Thread getPhysics(){
        return physics;
    }

    public Thread getGraphics(){
        return graphics;
    }

    public GameStage getGameStage(){
        return gameStage;
    }

    public void setGameStage(){
        gameStage = mainStarter.getGameStage();
    }

    public void setStars(Star[] stars) {
        this.stars = stars;
    }

    public void setUniverse(Universe universe) {
        this.universe = universe;
    }
}
