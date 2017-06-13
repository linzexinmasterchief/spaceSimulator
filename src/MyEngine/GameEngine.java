package MyEngine;

import javafx.application.Platform;
import javafx.scene.Group;
import models.Camera;
import models.Star;
import models.Universe;
import physics.GravityCalculate;
import scenes.gameScene.GameScene;

/**
 * Created by lzx on 2017/6/13.
 * initialize at start, then every components are created under it
 */
public class GameEngine implements Runnable {

    //used components
    //create an object of the game scene
    public GameScene gameScene;
    //generate a universe
    private Universe universe;
    //store all the stars in this universe
    private Star[] stars;
    //use a bufferStar for new input star
    private Star bufferStar;
    //install the gravity module
    private GravityCalculate gravityCalculate;
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
        universe = new Universe(2000, 2000);
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
        //initialize the gravity module object
        gravityCalculate = new GravityCalculate(stars);
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


        Thread physics = new Thread(this);
        physics.start();

    }

    //this is the function called on every physics cycle
    //kind of like "fixed update" in unity
    private void PhysicsUpdate() {
        if (isPause) {
            return;
        }

        for (int i = 0; i < stars.length; i++) {
            if (((stars[i].centerX - stars[i].r) > (universe.getWidth() + 10 + (2 * stars[i].r)))
                    | ((stars[i].centerY - stars[i].r) > (universe.getHeight() + 10 + (2 * stars[i].r)))
                    | ((stars[i].centerX - stars[i].r) < (-10 - (2 * stars[i].r)))
                    | ((stars[i].centerY - stars[i].r) < (-10 - (2 * stars[i].r)))) {
                stars[i].remove();
            }

            if (stars[i].inUniverse) {
                stars[i].move();
                final int F = i;
                Platform.runLater(() -> {
                    gravityCalculate.synchronize(stars);
                    gravityCalculate.gravityAcceleration(stars[F]);
                });
            }
        }
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

    public void setExit(boolean exit) {
        isExit = exit;
    }

    //the main thread cycle of the universe
    @Override
    public void run() {
        while (!isExit) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //call the specific used function
            PhysicsUpdate();
        }
    }

}
