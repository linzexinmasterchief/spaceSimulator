package Application.logicUnit.worldComponents.physics;

import Application.logicUnit.World;
import Application.status.SystemStatus;
import Application.logicUnit.worldComponents.physics.physicsComponents.universeComponents.Star;
import javafx.application.Platform;
import models.systemComponentModels.ThreadModel;
import Application.logicUnit.worldComponents.physics.physicsComponents.Universe;

/**
 * Created by lzx on 2017/7/6.
 * physics thread is a independent module
 */
public class PhysicsThread extends ThreadModel {

    private Star[] cloneStarList;

    public PhysicsThread(World root_world){
        super(root_world);
        //the initialize block is executed inside the super constructor
    }

    @Override
    public void initialize(){
        //override default initialize block
//        Gravity.synchronize(world.getUniverse());
        cloneStarList = world.getUniverse().getStars();

    }

    //this is the function called on every Application.world.physics cycle
    //kind of like "fixed update" in unity
    private void PhysicsUpdate() {

        //initialize star amount
        world.getUniverse().setStarAmount(0);

        //create reference of universe
        Universe universe = world.getUniverse();

        universe.setTimeSpeed(world.getLauncher().getGameStage().getGameScene().getStatusBar().getTimeSpeed());

        cloneStarList = world.getUniverse().getStars();

        //iterate star list
        for (Star star : cloneStarList) {

            teleport(star, universe);

            if (star.inUniverse) {
                //count the amount of stars in the universe + 1
                world.getUniverse().setStarAmount(world.getUniverse().getStarAmount() + 1);

                if (!isPause()) {
                    //set the next position of star according to star speed
                    star.move(universe.getTimeSpeed());

                    Platform.runLater(() -> {
                        //use multi-thread to calculate the acceleration of star
//                        Gravity.synchronize(universe);
                        Gravity.step(cloneStarList ,star);
                    });

                }

            }else {
                if (SystemStatus.isNewStarExist()) {

                    //give the properties of buffer star to the empty star slot
                    star.cloneStar(world.getBufferStar());

                    world.getGraphicsThread().drawStar(world.getUniverse().getStars()[world.getUniverse().getStars().length - 1]);

                    //remove the buffer star (clear the values to default)
                    world.getBufferStar().remove();

                    //close the new star lock
                    SystemStatus.setNewStarExist(false);

                    world.getLauncher().getGameStage().getGameScene().getStatusBar().setStarAmount(world.getUniverse().getStarAmount());

                    //increase the amount of star by 1
                    world.getUniverse().setStarAmount(world.getUniverse().getStarAmount() + 1);
                }
            }


        }

        world.getUniverse().setStars(cloneStarList);

        world.getLauncher().getGameStage().getGameScene().getStatusBar().setStarAmount(universe.getStarAmount());
    }

    //transfer star to the other side if it is out of bound
    public void teleport(Star star, Universe universe){
        //allow the stars to cross the edge of the universe
        if ((star.centerX - star.r) > universe.getWidth()){

            //if star reaches right edge
            star.centerX = 0;

        }else if ((star.centerY - star.r) > universe.getHeight()){

            //if star reaches bottom edge
            star.centerY = 0;

        }else if ((star.centerX + star.r) < 0){

            //if star reaches left edge
            star.centerX = (float) universe.getWidth();

        }else if ((star.centerY + star.r) < 0){

            //if star reaches top edge
            star.centerX = (float) universe.getHeight();

        }
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

//            world.getUniverse().reFitStarListSize();
        }
    }

}
