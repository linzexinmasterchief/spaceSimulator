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

    public boolean isMousePressed() {
        return MousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        MousePressed = mousePressed;
    }

    public boolean isMouseReleased() {
        return MouseReleased;
    }

    public void setMouseReleased(boolean mouseReleased) {
        MouseReleased = mouseReleased;
    }

    public boolean isMouseScrolled() {
        return MouseScrolled;
    }

    public void setMouseScrolled(boolean mouseScrolled) {
        MouseScrolled = mouseScrolled;
    }

    public double[] getMouse_coordinate() {
        return mouse_coordinate;
    }

    public void setMouse_coordinate(double[] mouse_coordinate) {
        this.mouse_coordinate = mouse_coordinate;
    }

    public MouseButton getActivatedMouseButton() {
        return activatedMouseButton;
    }

    public void setActivatedMouseButton(MouseButton activatedMouseButton) {
        this.activatedMouseButton = activatedMouseButton;
    }

    public double getMouseScrollValue() {
        return mouseScrollValue;
    }

    public void setMouseScrollValue(double mouseScrollValue) {
        this.mouseScrollValue = mouseScrollValue;
    }

}
