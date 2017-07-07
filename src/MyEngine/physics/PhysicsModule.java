package MyEngine.physics;

import MyEngine.GameEngine;
import javafx.application.Platform;
import models.Star;
import models.Universe;

/**
 * Created by lzx on 2017/7/6.
 * physics thread is a independent module
 */
public class PhysicsModule implements Runnable{

    private GameEngine engine;
    private Star[] stars;

    //some properties of the program
    private boolean isExit;
    private boolean isPause;

    //install the gravity module
    private GravityCalculate gravityCalculate;

    public PhysicsModule(GameEngine root_engine){
        engine = root_engine;
        //initialize the gravity module object
        gravityCalculate = new GravityCalculate(stars);

        //initialize program properties
        setExit(false);
        setPause(false);
    }

    //this is the function called on every MyEngine.physics cycle
    //kind of like "fixed update" in unity
    private void PhysicsUpdate() {
        if (isPause) {
            return;
        }

        stars = engine.getStars();
        Universe universe = engine.getUniverse();

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

        engine.setStars(stars);
        engine.setUniverse(universe);
    }

    public boolean isPause(){
        return isPause;
    }

    public void setPause(boolean pause){
        isPause = pause;
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
