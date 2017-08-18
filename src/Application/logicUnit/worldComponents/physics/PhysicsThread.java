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

    private Star[] stars;

    //install the gravity module
    private GravityCalculate gravityCalculate;

    public PhysicsThread(World root_world){
        super(root_world);
    }

    @Override
    public void initialize(){
        //override default initialize block
        this.gravityCalculate = new GravityCalculate(this.stars);

        //create reference of star list
        this.stars = this.world.getUniverse().getStars();
    }

    //this is the function called on every Application.world.physics cycle
    //kind of like "fixed update" in unity
    private void PhysicsUpdate() {
        if (this.isPause()) {
            return;
        }

        //initialize star amount
        this.world.getUniverse().setStarAmount(0);

        //create reference of universe
        Universe universe = this.world.getUniverse();

        universe.setTimeSpeed(this.world.getLauncher().getGameStage().getGameScene().getStatusBar().getTimeSpeed());

        //iterate star list
        for (int i = 0; i < this.stars.length; i++) {

            if ((this.stars[i].centerX - this.stars[i].r) > universe.getWidth()){
                //if star reaches right edge
                this.stars[i].centerX = 0;
            }else if ((this.stars[i].centerY - this.stars[i].r) > universe.getHeight()){
                //if star reaches bottom edge
                this.stars[i].centerY = 0;
            }else if ((this.stars[i].centerX + this.stars[i].r) < 0){
                //if star reaches left edge
                this.stars[i].centerX = universe.getWidth();
            }else if ((this.stars[i].centerY + this.stars[i].r) < 0){
                //if star reaches top edge
                this.stars[i].centerX = universe.getHeight();
            }

            if (this.stars[i].inUniverse) {
                //count the amount of stars in the universe + 1
                this.world.getUniverse().setStarAmount(this.world.getUniverse().getStarAmount() + 1);

                //set the next position of star according to star speed
                this.stars[i].move(universe.getTimeSpeed());

                //use multi-thread to calculate the acceleration of star
                int F = i;
                Platform.runLater(() -> {
                    this.gravityCalculate.synchronize(universe);
                    this.gravityCalculate.fire(this.stars[F]);
                });
            }
        }

        this.world.getUniverse().setStars(this.stars);

        this.world.getLauncher().getGameStage().getGameScene().getStatusBar().setStarAmount(universe.getStarAmount());
    }

    //function designed for screen cleaning
    //calling it will remove all the stars in the universe
    public void clear() {
        for (Star star : this.world.getUniverse().getStars()) {
            star.remove();
        }
    }

    //the main thread cycle of the universe
    @Override
    public void run() {
        while (!this.isExit()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //call the specific used function
            this.PhysicsUpdate();

        }
    }

}
