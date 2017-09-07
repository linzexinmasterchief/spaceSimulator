package Application.logicUnit.worldComponents.physics.physicsComponents.universeComponents;

import models.physicsComponentModels.PhysicsComponent;

/**
 * Created by lzx on 2017/3/27.
 * this is a model class for star, every star must be constructed as here
 */
public class Star implements PhysicsComponent{

    public float mass;
    public float r;

    public float centerX;
    public float centerY;

    public float velocityX;
    public float velocityY;

    public float accelerationX;
    public float accelerationY;

    public float vectorX;
    public float vectorY;

    public boolean onScreen;
    public boolean inUniverse;

    //normal constructor
    public Star() {
        initialize();
    }

    //second constructor
    //used to copy all the data from another star with out making directly pointer to the original object
    public void cloneStar(Star star) {

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
        inUniverse = false;
        centerX = 0;
        centerY = 0;
    }

    //move (or more like teleporting) to another point
    public void setPosition(float input_centerX, float input_centerY) {
        centerX = input_centerX;
        centerY = input_centerY;
    }

    //allow the star thee putIn on screen
    public void putIn(float input_centerX, float input_centerY) {
        onScreen = true;
        inUniverse = true;
        setPosition(input_centerX, input_centerY);
    }

    //update the position
    public void move(float time) {

        //calculate the speed of the star
        velocityX = velocityX + accelerationX * 0.1f * time;
        velocityY = velocityY + accelerationY * 0.1f * time;

        //calculate vector
        vectorX = velocityX * 0.1f * time;
        vectorY = velocityY * 0.1f * time;

        //calculate the position of the star
        setPosition(centerX + vectorX, centerY + vectorY);
    }

    //like it's name
    public void remove() {
        initialize();
    }

    @Override
    public void setX(float value) {
        centerX = value - (width / 2);
    }

    @Override
    public void setY(float value) {
        centerY = value - (height / 2);
    }

    @Override
    public void setCenterX(float value) {
        centerX = value;
    }

    @Override
    public void setCenterY(float value) {
        centerY = value;
    }

    @Override
    public void setWidth(float value) {
        r = value;
    }

    @Override
    public void setHeight(float value) {
        r = value;
    }
}
