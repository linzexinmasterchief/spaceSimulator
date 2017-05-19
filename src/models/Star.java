package models;

/**
 * Created by lzx on 2017/3/27.
 * this is a model class for star, every star must be constructed as here
 */
public class Star {

    public int mass;
    public double r;

    public double centerX;
    public double centerY;

    public double speedX;
    public double speedY;

    public double accelerationX;
    public double accelerationY;

    public boolean onScreen;

    public Star() {
        initialize();
    }

    public void initialize() {
        mass = 1;
        r = 5;
        speedX = speedY = 0;
        accelerationX = 0;
        accelerationY = 0;
        onScreen = false;
        centerX = 0;
        centerY = 0;
    }

    private void setPosition(double input_centerX, double input_centerY) {
        centerX = input_centerX;
        centerY = input_centerY;
    }

    public void show(double input_centerX, double input_centerY) {
        initialize();
        onScreen = true;
        setPosition(input_centerX, input_centerY);
    }

    public void move() {
        speedX = speedX + accelerationX;
        speedY = speedY + accelerationY;
        setPosition(centerX + speedX, centerY + speedY);
    }

    public void remove() {
        setPosition(400, 280);
        initialize();
    }

}
