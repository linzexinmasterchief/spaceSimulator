package Application.Engine.physics;

import Application.Engine.Engine;
import javafx.application.Platform;
import Application.Engine.physics.physicsPrefabs.Star;
import models.SystemComponents.ThreadModuleModel;
import Application.Engine.physics.physicsPrefabs.Universe;

/**
 * Created by lzx on 2017/7/6.
 * physics thread is a independent module
 */
public class PhysicsModuleModel extends ThreadModuleModel {

    private Star[] stars;

    //install the gravity module
    private GravityCalculate gravityCalculate;

    public PhysicsModuleModel(Engine root_engine){
        super(root_engine);
    }

    @Override
    public void initialize(){
        //override default initialize block
        gravityCalculate = new GravityCalculate(stars);
    }

    //this is the function called on every Application.Engine.physics cycle
    //kind of like "fixed update" in unity
    private void PhysicsUpdate() {
        if (isPause()) {
            return;
        }

        engine.getUniverse().setStarAmount(0);

        stars = engine.getUniverse().getStars();
        Universe universe = engine.getUniverse();

        for (int i = 0; i < stars.length; i++) {

            if (stars[i].inUniverse){
                engine.getUniverse().setStarAmount(engine.getUniverse().getStarAmount() + 1);
            }

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

        engine.getUniverse().setStars(stars);
        engine.setUniverse(universe);
    }

    //function designed for screen cleaning
    //calling it will remove all the stars in the universe
    public void clear() {
        for (Star star : engine.getUniverse().getStars()) {
            star.remove();
        }
    }

    //the main thread cycle of the universe
    @Override
    public void run() {
        while (!isExit()) {
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
