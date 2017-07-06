package MyEngine;

import MyEngine.physics.PhysicsThread;
import javafx.scene.Group;
import models.Camera;
import models.Star;
import models.Universe;
import Stages.MainStage.gameScene.GameScene;

/**
 * Created by lzx on 2017/6/13.
 * initialize at start, then every components are created under it
 */
public class GameEngine{

    //used components
    //create an object of the game scene
    public GameScene gameScene;
    //generate a universe
    private Universe universe;
    //store all the stars in this universe
    private Star[] stars;
    //use a bufferStar for new input star
    private Star bufferStar;
    //define a camera used for display
    private Camera camera;

    //some properties of the program
    private boolean isExit;
    private boolean isPause;

    public GameEngine() {

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

        //initialize components
        gameScene = new GameScene(new Group(), 1000, 560, this);

        //initialize the camera
        camera = new Camera(
                1000,
                560,
                universe.getWidth() / 2,
                universe.getHeight() / 2
        );

        //initialize program properties
        isExit = false;
        isPause = false;


        PhysicsThread physicsThread = new PhysicsThread(this);
        Thread physics = new Thread(physicsThread);
        physics.start();

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

    public boolean isPause(){
        return isPause;
    }

    public boolean isExit(){
        return isExit;
    }

    public void setExit(boolean exit) {
        isExit = exit;
    }

    public void setPause(boolean pause){
        isPause = pause;
    }


    public void setStars(Star[] stars) {
        this.stars = stars;
    }

    public void setUniverse(Universe universe) {
        this.universe = universe;
    }
}
