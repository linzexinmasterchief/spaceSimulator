package Application.status;

import javafx.scene.input.MouseButton;

/**
 * Created by lzx on 2017/8/11.
 * 
 */
public class Mouse {
    //mouse coordinate
    private static double[] mouse_coordinate;
    //mouse scroll length
    private static double mouseScrollValue;
    //activated mouse button
    private static MouseButton activatedMouseButton;
    //mouse PhysicsStatus
    private static boolean MousePressing;
    private static boolean MousePressed;
    private static boolean MouseReleasing;
    private static boolean MouseScrolled;

    public Mouse(){
        //initialize the variable used to store mouse operations
        mouse_coordinate = new double[2];
        mouseScrollValue = 0;
        activatedMouseButton = MouseButton.NONE;
        MousePressing = false;
        MousePressed = false;
        MouseReleasing = false;
        MouseScrolled = false;
    }

    public static boolean isMousePressing() {
        return MousePressing;
    }

    public static void setMousePressing(boolean mousePressing) {
        MousePressing = mousePressing;
    }

    public static boolean isMouseReleasing() {
        return MouseReleasing;
    }

    public static void setMouseReleasing(boolean mouseReleasing) {
        MouseReleasing = mouseReleasing;
    }

    public static boolean isMouseScrolled() {
        return MouseScrolled;
    }

    public static void setMouseScrolled(boolean mouseScrolled) {
        MouseScrolled = mouseScrolled;
    }

    public static double[] getMouse_coordinate() {
        return mouse_coordinate;
    }

    public static void setMouse_coordinate(double[] coordinate) {
        mouse_coordinate = coordinate;
    }

    public static MouseButton getActivatedMouseButton() {
        return activatedMouseButton;
    }

    public static void setActivatedMouseButton(MouseButton mouseButton) {
        activatedMouseButton = mouseButton;
    }

    public static double getMouseScrollValue() {
        return mouseScrollValue;
    }

    public static void setMouseScrollValue(double value) {
        mouseScrollValue = value;
    }

    public static boolean isMousePressed() {
        return MousePressed;
    }

    public static void setMousePressed(boolean mousePressed) {
        MousePressed = mousePressed;
    }
}
