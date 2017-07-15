package models.PhysicsComponents;

/**
 * Created by lzx on 2017/5/26.
 * this class is the core of the application,
 * it is in charge of the position system
 * and the gravity calculate (gravity calculate
 * class is only a module)
 */
public class Universe {

    //define the size of the universe
    private double width;
    private double height;
    private double unitTimeSpeed;

    //unused constructor

//    //first constructor
//    public Universe() {
//        //give a default universe size
//        width = 0;
//        height = 0;
//
//        //call the function to initialize all the stars in the universe
//        initialize();
//    }

    //second constructor
    public Universe(double width, double height) {
        //define the size of the universe based on the value input
        this.width = width;
        this.height = height;

    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getUnitTimeSpeed() {
        return unitTimeSpeed;
    }

    public void setUnitTimeSpeed(double unitTimeSpeed) {
        this.unitTimeSpeed = unitTimeSpeed;
    }
}
