package Application.Engine.physics.physicsPrefabs;

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
    //store all the stars in this universe
    private Star[] stars;

    //active star amount
    private int starAmount;

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

        //initialize the star list of the universe
        stars = new Star[200];
        //initialize every star in the star list
        //this is very important and necessary, do not delete it
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star();
        }

        //initialize star amount;
        starAmount = 0;

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

    public Star[] getStars() {
        return stars;
    }

    public void setStars(Star[] stars) {
        this.stars = stars;
    }

    public int getStarAmount() {
        return starAmount;
    }

    public void setStarAmount(int starAmount) {
        this.starAmount = starAmount;
    }
}
