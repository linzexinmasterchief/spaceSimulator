package Application.status;

import javafx.scene.input.MouseButton;
import sun.plugin2.main.server.ModalitySupport;

/**
 * Created by lzx on 2017/8/11.
 */
public class Mouse {
    //mouse coordinate
    private static double[] mouse_coordinate;
    //mouse scroll length
    private static double mouseScrollValue;
    //activated mouse button
    private static MouseButton activatedMouseButton;
    //mouse PhysicsStatus
    private static boolean MousePressed;
    private static boolean MouseReleased;
    private static boolean MouseScrolled;

    public Mouse(){
        //initialize the variable used to store mouse operations
        mouse_coordinate = new double[2];
        mouseScrollValue = 0;
        activatedMouseButton = MouseButton.NONE;
        MousePressed = false;
        MouseReleased = false;
        MouseScrolled = false;
    }

    public static boolean isMousePressed() {
        return MousePressed;
    }

    public static void setMousePressed(boolean mousePressed) {
        MousePressed = mousePressed;
    }

    public static boolean isMouseReleased() {
        return MouseReleased;
    }

    public static void setMouseReleased(boolean mouseReleased) {
        MouseReleased = mouseReleased;
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

}
