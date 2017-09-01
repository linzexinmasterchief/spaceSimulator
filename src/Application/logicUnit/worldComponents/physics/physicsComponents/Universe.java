package Application.logicUnit.worldComponents.physics.physicsComponents;

import Application.logicUnit.worldComponents.physics.physicsComponents.universeComponents.Star;

/**
 * Created by lzx on 2017/5/26.
 * this class is the core of the application,
 * it is in charge of the position system
 * and the gravity calculate (gravity calculate
 * class is only a module)
 */
public class Universe {

    //define the size of the universe
    private final double width;
    private final double height;

    //determine how fast the time goes in the universe (does not affect the speed of thread)
    private float timeSpeed;

    //store all the stars in this universe
    private Star[] stars;

    //active star amount
    private int starAmount;

    //second constructor
    public Universe(double width, double height) {
        //define the size of the universe based on the value input
        this.width = width;
        this.height = height;

        timeSpeed = 1;

        //initialize the star list of the universe
        stars = new Star[10];
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

    public float getTimeSpeed() {
        return timeSpeed;
    }

    public void setTimeSpeed(float timeSpeed) {
        this.timeSpeed = timeSpeed;
    }

    public Star[] getStars() {
        return stars;
    }

    public void setStars(Star[] stars) {
        this.stars = stars;
    }

    public void reFitStarListSize(){

        if (Math.abs(getStars().length - getStarAmount()) > 5){
            return;
        }

        //create resized star list
        Star[] newStarList = new Star[((getStarAmount() / 10) + 1) * 10];

        //initialize new star list
        for (int i = 0;i < newStarList.length;i ++){
            newStarList[i] = new Star();
        }

        //copy original list to new list
        for (int i = 0;i < getStarAmount();i ++){
            if (getStars()[i].inUniverse){
                newStarList[i] = getStars()[i];
            }
        }

        //set new star list as current star list
        setStars(newStarList);
    }

    public int getStarAmount() {
        return starAmount;
    }

    public void setStarAmount(int starAmount) {
        this.starAmount = starAmount;
    }
}
