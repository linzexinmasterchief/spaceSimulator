package models;

import javafx.scene.shape.Circle;

/**
 * Created by lzx on 2017/3/27.
 */
public class Star extends Circle{

    private static double mass;
    private static String type;
    private static String name;
    private static double r;

    public static double getMass() {
        return mass;
    }

    public static void setMass(double m) {
        mass = m;
    }

    public static String getType() {
        return type;
    }

    public static void setType(String type) {
        Star.type = type;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        Star.name = name;
    }

}
