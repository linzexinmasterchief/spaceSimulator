package Application.Engine.physics;

import Application.Engine.world;
import javafx.application.Platform;
import Application.Engine.physics.physicsPrefabs.Star;
import models.SystemComponents.ThreadModuleModel;
import Application.Engine.physics.physicsPrefabs.Universe;

/**
 * Created by lzx on 2017/7/6.
 * physics thread is a independent module
 */
public class PhysicsModule extends ThreadModuleModel {

    private Star[] stars;

    //install the gravity module
    private GravityCalculate gravityCalculate;

    public PhysicsModule(world root_world){
        super(root_world);
    }

    @Override
    public void initialize(){
        //override default initialize block
        gravityCalculate = new GravityCalculate(stars);
    }

    //this is the function called on every Application.world.physics cycle
    //kind of like "fixed update" in unity
    private void PhysicsUpdate() {
        if (isPause()) {
            return;
        }

        //initialize star amount
        world.getUniverse().setStarAmount(0);

        //create reference of star list
        stars = world.getUniverse().getStars();

        //create reference of universe
        Universe universe = world.getUniverse();

        //iterate star list
        for (int i = 0; i < stars.length; i++) {

            //remove the star from star list if it move out the universe
            if (((stars[i].centerX - stars[i].r) > (universe.getWidth() + 10 + (2 * stars[i].r)))
                    | ((stars[i].centerY - stars[i].r) > (universe.getHeight() + 10 + (2 * stars[i].r)))
                    | ((stars[i].centerX - stars[i].r) < (-10 - (2 * stars[i].r)))
                    | ((stars[i].centerY - stars[i].r) < (-10 - (2 * stars[i].r)))) {
                stars[i].remove();
            }

            if (stars[i].inUniverse) {
                //count the amount of stars in the universe + 1
                world.getUniverse().setStarAmount(world.getUniverse().getStarAmount() + 1);

                //set the next position of star according to star speed
                stars[i].move();

                //use multi-thread to calculate the acceleration of star
                final int F = i;
                Platform.runLater(() -> {
                    gravityCalculate.synchronize(stars);
                    gravityCalculate.fire(stars[F]);
                });
            }
        }

        world.getUniverse().setStars(stars);
    }

    //function designed for screen cleaning
    //calling it will remove all the stars in the universe
    public void clear() {
        for (Star star : world.getUniverse().getStars()) {
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
