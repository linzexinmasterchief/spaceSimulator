package Application.logicUnit.worldComponents.physics;

import Application.logicUnit.World;
import Application.status.Mouse;
import javafx.application.Platform;
import Application.logicUnit.worldComponents.physics.physicsComponents.universeComponents.Star;
import models.systemComponentModels.ThreadModel;
import Application.logicUnit.worldComponents.physics.physicsComponents.Universe;

/**
 * Created by lzx on 2017/7/6.
 * physics thread is a independent module
 */
public class PhysicsThread extends ThreadModel {

    //install the gravity module
    private GravityCalculate gravityCalculate;

    public PhysicsThread(World root_world){
        super(root_world);
    }

    @Override
    public void initialize(){
        //override default initialize block
        gravityCalculate = new GravityCalculate(world.getUniverse().getStars());

    }

    //this is the function called on every Application.world.physics cycle
    //kind of like "fixed update" in unity
    private void PhysicsUpdate() {
        if (isPause()) {
            return;
        }

        //initialize star amount
        world.getUniverse().setStarAmount(0);

        //create reference of universe
        Universe universe = world.getUniverse();

        universe.setTimeSpeed(world.getLauncher().getGameStage().getGameScene().getStatusBar().getTimeSpeed());

        //iterate star list
        for (int i = 0; i < world.getUniverse().getStars().length; i++) {

            //allow the stars to cross the edge of the universe
            if ((world.getUniverse().getStars()[i].centerX - world.getUniverse().getStars()[i].r) > universe.getWidth()){
                //if star reaches right edge
                world.getUniverse().getStars()[i].centerX = 0;
            }else if ((world.getUniverse().getStars()[i].centerY - world.getUniverse().getStars()[i].r) > universe.getHeight()){
                //if star reaches bottom edge
                world.getUniverse().getStars()[i].centerY = 0;
            }else if ((world.getUniverse().getStars()[i].centerX + world.getUniverse().getStars()[i].r) < 0){
                //if star reaches left edge
                world.getUniverse().getStars()[i].centerX = (float) universe.getWidth();
            }else if ((world.getUniverse().getStars()[i].centerY + world.getUniverse().getStars()[i].r) < 0){
                //if star reaches top edge
                world.getUniverse().getStars()[i].centerX = (float) universe.getHeight();
            }

            if (world.getUniverse().getStars()[i].inUniverse) {
                //count the amount of stars in the universe + 1
                world.getUniverse().setStarAmount(world.getUniverse().getStarAmount() + 1);

                //set the next position of star according to star speed
                world.getUniverse().getStars()[i].move(universe.getTimeSpeed());

                //use multi-thread to calculate the acceleration of star
                int F = i;
                Platform.runLater(() -> {
                    gravityCalculate.synchronize(universe);
                    gravityCalculate.fire(world.getUniverse().getStars()[F]);
                });
            }
        }

        world.getUniverse().setStars(world.getUniverse().getStars());

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

            world.getUniverse().reFitStarListSize();
        }
    }

}
