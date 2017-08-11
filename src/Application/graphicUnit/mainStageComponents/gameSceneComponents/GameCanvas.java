package Application.graphicUnit.mainStageComponents.gameSceneComponents;

import Application.status.CanvasStatus;
import Application.status.Mouse;
import Application.status.SystemStatus;
import javafx.scene.canvas.Canvas;
import Application.graphicUnit.mainStageComponents.GameScene;
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

    //create reference to mouse object
    private final Mouse mouse;

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

        mouse = rootScene.getGameStage().getLauncher().getMouse();

        setOnMouseMoved(me -> {
            mouse.setMouse_coordinate(new double[]{me.getX(),me.getY()});
            if (me.isPrimaryButtonDown()){
                systemStatus.setDragLine(new double[]{
                        systemStatus.getDragLine()[0],
                        systemStatus.getDragLine()[1],
                        me.getX(),
                        me.getY()
                });
                System.out.println(mouse.getMouse_coordinate()[0]);
            }
        });

        setOnMouseDragged(me -> mouse.setMouse_coordinate(new double[]{me.getX(),me.getY()}));

        //listen the operations of mouse press
        setOnMousePressed(me -> {
            //get the mouse press coordinate
            double mouse_coordinate[] = new double[2];
            mouse_coordinate[0] = me.getX();
            mouse_coordinate[1] = me.getY();
            systemStatus.setDragLine(new double[]{
                    me.getX(),
                    me.getY(),
                    me.getX(),
                    me.getY()
            });
            mouse.setMouse_coordinate(mouse_coordinate);
            mouse.setActivatedMouseButton(me.getButton());
            mouse.setMousePressed(true);
            mouse.setMouseReleased(false);
            mouse.setMouseScrolled(false);

        });

        //set operations on mouse release
        //new and clear
        setOnMouseReleased(me -> {
            //get the mouse press coordinate
            double mouse_coordinate[] = new double[2];
            mouse_coordinate[0] = me.getX();
            mouse_coordinate[1] = me.getY();
            mouse.setMouse_coordinate(mouse_coordinate);
            mouse.setActivatedMouseButton(me.getButton());
            mouse.setMousePressed(false);
            mouse.setMouseReleased(true);
            mouse.setMouseScrolled(false);
        });

        //set operations on mouse scrolled
        //enlarge and minimize
        setOnScroll(se -> {
            //get the mouse press coordinate
            double mouse_coordinate[] = new double[2];
            mouse_coordinate[0] = se.getX();
            mouse_coordinate[1] = se.getY();
            mouse.setMouse_coordinate(mouse_coordinate);
            mouse.setMouseScrollValue(se.getDeltaY());
            mouse.setActivatedMouseButton(MouseButton.MIDDLE);
            mouse.setMousePressed(false);
            mouse.setMouseReleased(false);
            mouse.setMouseScrolled(true);

        });

    }

}
