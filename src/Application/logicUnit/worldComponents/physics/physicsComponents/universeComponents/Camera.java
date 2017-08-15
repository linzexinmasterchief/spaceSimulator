package Application.logicUnit.worldComponents.physics.physicsComponents.universeComponents;

/**
 * Created by lzx on 2017/5/26.
 * this class is designed to be an blueprint of camera
 * in fact, the camera did not really take the job of
 * displaying graphics but only a data struct like the
 * star class and universe class
 */

public class Camera {

    private double originalWidth;
    private double originalHeight;

    private double width;
    private double height;
    private double centerX;
    private double centerY;

//    unused constructors
    //first constructor
    public Camera() {
        //give default values to width and height
        this.width = 100;
        this.height = 100;

        //store these initial data for scale calculations
        originalWidth = this.width;
        originalHeight = this.height;
    }

    //second constructor
    public Camera(double width, double height) {
        //take in width and height
        this.width = width;
        this.height = height;

        //store these initial data for scale calculations
        originalWidth = this.width;
        originalHeight = this.height;
    }

    //third constructor
    public Camera(double width, double height, double centerX, double centerY) {
        this.width = width;
        this.height = height;
        this.centerX = centerX;
        this.centerY = centerY;

        originalWidth = this.width;
        originalHeight = this.height;
    }

    //a whole bunch of getters and setters

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getCenterX() {
        return centerX;
    }

    public void setCenterX(double centerX) {
        this.centerX = centerX;
    }

    public double getCenterY() {
        return centerY;
    }

    public void setCenterY(double centerY) {
        this.centerY = centerY;
    }

    public double getOriginalWidth() {
        return originalWidth;
    }

    public double getOriginalHeight() {
        return originalHeight;
    }

}
