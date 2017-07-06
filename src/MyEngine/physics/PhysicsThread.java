package MyEngine.physics;

import MyEngine.GameEngine;
import javafx.application.Platform;
import models.Star;
import models.Universe;

/**
 * Created by lzx on 2017/7/6.
 * physics thread is a independent module
 */
public class PhysicsThread implements Runnable{

    private GameEngine engine;
    private Star[] stars;

    //install the gravity module
    private GravityCalculate gravityCalculate;

    public PhysicsThread(GameEngine root_engine){
        engine = root_engine;
        //initialize the gravity module object
        gravityCalculate = new GravityCalculate(stars);
    }

    //this is the function called on every MyEngine.physics cycle
    //kind of like "fixed update" in unity
    private void PhysicsUpdate() {
        if (engine.isPause()) {
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

    //the main thread cycle of the universe
    @Override
    public void run() {
        while (!engine.isExit()) {
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
