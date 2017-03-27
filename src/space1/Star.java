package space1;

import javafx.scene.shape.Circle;

/**
 * Created by lzx on 2017/3/27.
 */
public class Star extends Circle{

    private static double mass;
    private static String type;
    private static String name;

    public Star(String n, String t) {
        name = n;
        type = t;
    }

    public static double getMass() {
        return mass;
    }

    public static void setMass(double m) {
        mass = m;
    }

}
