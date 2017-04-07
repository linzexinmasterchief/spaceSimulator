package models;

import javafx.scene.shape.Circle;

/**
 * Created by lzx on 2017/3/27.
 */
public class Star extends Circle{

    public double mass;
    public String name;
    public double r;

    public double x;
    public double y;

    public double speedX;
    public double speedY;

    public boolean onScreen;

    public Star() {
        mass = 10;
        name = "";
        r = 5;
        speedX = speedY = 0;
        onScreen = false;
    }

}
