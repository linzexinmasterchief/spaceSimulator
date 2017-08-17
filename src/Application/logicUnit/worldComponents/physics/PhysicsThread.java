package Application.logicUnit.worldComponents.physics;

import Application.logicUnit.World;
import javafx.application.Platform;
import Application.logicUnit.worldComponents.physics.physicsComponents.universeComponents.Star;
import models.systemComponentModels.ThreadModel;
import Application.logicUnit.worldComponents.physics.physicsComponents.Universe;

/**
 * Created by lzx on 2017/7/6.
 * physics thread is a independent module
 */
public class PhysicsThread extends ThreadModel {

    private Star[] stars;

    //install the gravity module
    private GravityCalculate gravityCalculate;

    public PhysicsThread(World root_world){
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

        universe.setTimeSpeed(world.getLauncher().getGameStage().getGameScene().getStatusBar().getTimeSpeed());

        //iterate star list
        for (int i = 0; i < stars.length; i++) {

            if ((stars[i].centerX - stars[i].r) > universe.getWidth()){
                //if star reaches right edge
                stars[i].centerX = 0;
            }else if ((stars[i].centerY - stars[i].r) > universe.getHeight()){
                //if star reaches bottom edge
                stars[i].centerY = 0;
            }else if ((stars[i].centerX - stars[i].r) < 0){
                //if star reaches left edge
                stars[i].centerX = universe.getWidth();
            }else if ((stars[i].centerY - stars[i].r) < 0){
                //if star reaches top edge
                stars[i].centerX = universe.getHeight();
            }
            //remove the star from star list if it move out the universe
//            if (((stars[i].centerX - stars[i].r) > (universe.getWidth() + 10 + (2 * stars[i].r)))
//                    | ((stars[i].centerY - stars[i].r) > (universe.getHeight() + 10 + (2 * stars[i].r)))
//                    | ((stars[i].centerX - stars[i].r) < (-10 - (2 * stars[i].r)))
//                    | ((stars[i].centerY - stars[i].r) < (-10 - (2 * stars[i].r)))) {
//                stars[i].remove();
//            }

            if (stars[i].inUniverse) {
                //count the amount of stars in the universe + 1
                world.getUniverse().setStarAmount(world.getUniverse().getStarAmount() + 1);

                //set the next position of star according to star speed
                stars[i].move(universe.getTimeSpeed());

                //use multi-thread to calculate the acceleration of star
                final int F = i;
                Platform.runLater(() -> {
                    gravityCalculate.synchronize(universe);
                    gravityCalculate.fire(stars[F]);
                });
            }
        }

        world.getUniverse().setStars(stars);

        world.getLauncher().getGameStage().getGameScene().getStatusBar().setStarAmount(universe.getStarAmount());
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
