package Application.logicUnit.worldComponents.physics.physicsComponents.universeComponents;

import models.physicsComponentModels.PhysicsComponent;

/**
 * Created by lzx on 2017/5/26.
 * this class is designed to be an blueprint of camera
 * in fact, the camera did not really take the job of
 * displaying graphics but only a data struct like the
 * star class and universe class
 */

public class Camera implements PhysicsComponent{

    private final float originalWidth;
    private final float originalHeight;

    private float width;
    private float height;
    private float centerX;
    private float centerY;

//    unused constructors
    //default constructor
    public Camera() {
        //give default values to width and height
        this.width = 100;
        this.height = 100;

        //store these initial data for scale calculations
        originalWidth = this.width;
        originalHeight = this.height;
    }

    //second constructor
    public Camera(float width, float height) {
        //take in width and height
        this.width = width;
        this.height = height;

        //store these initial data for scale calculations
        originalWidth = this.width;
        originalHeight = this.height;
    }

    //third constructor
    public Camera(float width, float height, float centerX, float centerY) {
        this.width = width;
        this.height = height;
        this.centerX = centerX;
        this.centerY = centerY;

        originalWidth = this.width;
        originalHeight = this.height;
    }

    //a whole bunch of getters and setters

    public float getWidth() {
        return width;
    }

    public void setWidth(float value) {
        width = value;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float value) {
        height = value;
    }

    @Override
    public void setX(float value) {

    }

    @Override
    public void setY(float value) {

    }

    public float getCenterX() {
        return centerX;
    }

    public void setCenterX(float value) {
        centerX = value;
    }

    public float getCenterY() {
        return centerY;
    }

    public void setCenterY(float value) {
        centerY = value;
    }

    public float getOriginalWidth() {
        return originalWidth;
    }

    public float getOriginalHeight() {
        return originalHeight;
    }

    public float getScaleX(){
        return getWidth() / getOriginalWidth();
    }

    public float getScaleY(){
        return getHeight() / getOriginalHeight();
    }

}
