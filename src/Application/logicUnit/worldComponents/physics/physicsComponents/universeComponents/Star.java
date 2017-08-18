package Application.logicUnit.worldComponents.physics.physicsComponents.universeComponents;

/**
 * Created by lzx on 2017/3/27.
 * this is a model class for star, every star must be constructed as here
 */
public class Star {

    public double mass;
    public double r;

    public double centerX;
    public double centerY;

    public double velocityX;
    public double velocityY;

    public double accelerationX;
    public double accelerationY;

    public double vectorX;
    public double vectorY;

    public boolean onScreen;
    public boolean inUniverse;

    //normal constructor
    public Star() {
        initialize();
    }

    //second constructor
    //used to copy all the data from another star with out making directly pointer to the original object
    public Star(Star star) {

        //clean the star slot for new values
        initialize();

        //this should be pretty straight forward to understand

        //mass of the star
        mass = star.mass;

        //radius of the star
        r = star.r;

        //velocities(x,y) of the star
        velocityX = star.velocityX;
        velocityY = star.velocityY;

        //accelerations(x,y) of the star
        accelerationX = star.accelerationX;
        accelerationY = star.accelerationY;

        //vector(x,y)(how much will the star move) of the star
        vectorX = star.vectorX;
        vectorY = star.vectorY;


        onScreen = star.onScreen;
        inUniverse = star.inUniverse;
        centerX = star.centerX;
        centerY = star.centerY;
    }

    //initialize the properties
    public void initialize() {
        mass = 1;
        r = 5;
        velocityX = velocityY = 0;
        accelerationX = accelerationY = 0;
        vectorX = vectorY = 0;
        onScreen = false;
        inUniverse = false;
        centerX = 0;
        centerY = 0;
    }

    //move (or more like teleporting) to another point
    private void setPosition(double input_centerX, double input_centerY) {
        centerX = input_centerX;
        centerY = input_centerY;
    }

    //allow the star thee add on screen
    public void add(double input_centerX, double input_centerY) {
        onScreen = true;
        inUniverse = true;
        setPosition(input_centerX, input_centerY);
    }

    //update the position
    public void move(double time) {

        //calculate the speed of the star
        velocityX = velocityX + accelerationX;
        velocityY = velocityY + accelerationY;

        //calculate vector
        vectorX = velocityX * time;
        vectorY = velocityY * time;

        //calculate the position of the star
        setPosition(centerX + vectorX, centerY + vectorY);
    }

    //like it's name
    public void remove() {
        initialize();
    }

}
