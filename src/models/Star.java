package models;

/**
 * Created by lzx on 2017/3/27.
 * this is a model class for star, every star must be constructed as here
 */
public class Star {

    public double mass;
    public double r;

    public double centerX;
    public double centerY;

    public double vectorX;
    public double vectorY;

    public double accelerationX;
    public double accelerationY;

    public boolean onScreen;

    public Star() {
        initialize();
    }

    public Star(Star star) {
        mass = star.mass;
        r = star.r;
        vectorX = star.vectorX;
        vectorY = star.vectorY;
        accelerationX = star.accelerationX;
        accelerationY = star.accelerationY;
        onScreen = star.onScreen;
        centerX = star.centerX;
        centerY = star.centerY;
    }

    public void initialize() {
        mass = 1;
        r = 5;
        vectorX = vectorY = 0;
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
        onScreen = true;
        setPosition(input_centerX, input_centerY);
    }

    public void move() {
        vectorX = vectorX + accelerationX;
        vectorY = vectorY + accelerationY;
        setPosition(centerX + vectorX, centerY + vectorY);
    }

    public void remove() {
        setPosition(400, 280);
        initialize();
    }

}
