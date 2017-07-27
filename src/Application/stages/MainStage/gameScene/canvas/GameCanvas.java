package Application.stages.MainStage.gameScene.canvas;

import Application.status.CanvasStatus;
import Application.status.SystemStatus;
import javafx.scene.canvas.Canvas;
import Application.stages.MainStage.gameScene.GameScene;
import javafx.scene.input.MouseButton;

/**
 * Created by lzx on 2017/4/6.
 * this is the game canvas that will draw the stars
 * also, this is the only class that have the ability
 * of displaying graphics, which means, all of the
 * displaying command and modules should be called
 * in this class
 */
public class GameCanvas extends Canvas{

    //create reference to system status
    private final SystemStatus systemStatus;
    //create reference to canvas status
    private final CanvasStatus canvasStatus;

    //constructor
    public GameCanvas(double width, double height, GameScene rootScene) {
        //width and height is pretty necessary for things like
        //"this.getWidth()" to work properly
        super(width, height);

        systemStatus = rootScene.getGameStage().getLauncher().getSystemStatus();
        systemStatus.setHeightWidthScale(getHeight() / getWidth());

        canvasStatus = rootScene.getGameStage().getLauncher().getCanvasStatus();
        canvasStatus.setCanvasHeight(getHeight());
        canvasStatus.setCanvasWidth(getWidth());

        setOnMouseMoved(me -> systemStatus.setMouse_coordinate(new double[]{me.getX(),me.getY()}));

        //listen the operations of mouse press
        setOnMousePressed(me -> {
            //get the mouse press coordinate
            double mouse_coordinate[] = new double[2];
            mouse_coordinate[0] = me.getX();
            mouse_coordinate[1] = me.getY();
            systemStatus.setMouse_coordinate(mouse_coordinate);
            systemStatus.setActivatedMouseButton(me.getButton());
            systemStatus.setMousePressed(true);
            systemStatus.setMouseReleased(false);
            systemStatus.setMouseScrolled(false);

        });

        //set operations on mouse release
        //new and clear
        setOnMouseReleased(me -> {
            //get the mouse press coordinate
            double mouse_coordinate[] = new double[2];
            mouse_coordinate[0] = me.getX();
            mouse_coordinate[1] = me.getY();
            systemStatus.setMouse_coordinate(mouse_coordinate);
            systemStatus.setActivatedMouseButton(me.getButton());
            systemStatus.setMouseReleased(true);
            systemStatus.setMousePressed(false);
            systemStatus.setMouseScrolled(false);

        });

        //set operations on mouse scrolled
        //enlarge and minimize
        setOnScroll(se -> {
            //get the mouse press coordinate
            double mouse_coordinate[] = new double[2];
            mouse_coordinate[0] = se.getX();
            mouse_coordinate[1] = se.getY();
            systemStatus.setMouse_coordinate(mouse_coordinate);
            systemStatus.setMouseScrollValue(se.getDeltaY());
            systemStatus.setActivatedMouseButton(MouseButton.MIDDLE);
            systemStatus.setMouseReleased(false);
            systemStatus.setMousePressed(false);
            systemStatus.setMouseScrolled(true);

        });

    }

}
