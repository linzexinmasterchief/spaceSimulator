package models;

import javafx.scene.shape.Circle;

/**
 * Created by lzx on 2017/3/27.
 */
public class Star extends Circle{

    public static double mass;
    public static String name;
    public static double r;

    public static double x;
    public static double y;

    public static double speedX;
    public static double speedY;

    public static boolean onScreen;

    public Star() {
        mass = 10;
        name = "";
        r = 5;
        speedX = speedY = 0;
        onScreen = false;
    }

}
