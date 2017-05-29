package models;

/**
 * Created by lzx on 2017/5/26.
 */
public class Camera {

    private double originalWidth;
    private double originalHeight;

    private double width;
    private double height;
    private double centerX;
    private double centerY;

    public Camera() {
        this.width = 100;
        this.height = 100;

        originalWidth = this.width;
        originalHeight = this.height;
    }

    public Camera(double width, double height) {
        this.width = width;
        this.height = height;

        originalWidth = this.width;
        originalHeight = this.height;
    }

    public Camera(double width, double height, double centerX, double centerY) {
        this.width = width;
        this.height = height;
        this.centerX = centerX;
        this.centerY = centerY;


        originalWidth = this.width;
        originalHeight = this.height;
    }

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

    public void setOriginalWidth(double originalWidth) {
        this.originalWidth = originalWidth;
    }

    public double getOriginalHeight() {
        return originalHeight;
    }

    public void setOriginalHeight(double originalHeight) {
        this.originalHeight = originalHeight;
    }
}
