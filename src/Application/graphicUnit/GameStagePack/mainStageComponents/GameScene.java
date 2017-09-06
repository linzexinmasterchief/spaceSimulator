package Application.graphicUnit.GameStagePack.mainStageComponents;

import Application.graphicUnit.GameStagePack.GameStage;
import Application.graphicUnit.GameStagePack.mainStageComponents.gameSceneComponents.ui.CreateStarMenuSwitch;
import Application.graphicUnit.GameStagePack.mainStageComponents.gameSceneComponents.ui.CreateStarMenu;
import Application.graphicUnit.GameStagePack.mainStageComponents.gameSceneComponents.GameCanvas;
import Application.status.KeyBoard;
import Application.status.Mouse;
import Application.status.SystemStatus;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import Application.graphicUnit.GameStagePack.mainStageComponents.gameSceneComponents.ui.UniverseStatusBar;
import javafx.scene.input.MouseButton;
import models.uiComponentModels.betterUI.BetterSlider;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class GameScene extends Scene{

    //create reference to root game stage
    private GameStage gameStage;

    //give access to the canvas
    public GameCanvas screenAlpha;

    public GameCanvas screenBeta;

    private Group ui;

    private UniverseStatusBar universeStatusBar;

    private CreateStarMenu createStarMenu;

    //menu toggle
    private final CreateStarMenuSwitch createStarMenuSwitch;

    //constructor
    public GameScene(Group root, double width, double height, GameStage gameStage) {

        //construct the game scene as a scene
        super(root, width, height);
        this.gameStage = gameStage;

        //initialize the canvas
        //size
        screenAlpha = new GameCanvas(getWidth(), getHeight(), this);
        screenBeta = new GameCanvas(getWidth(), getHeight(), this);
        //putIn game canvas
        root.getChildren().add(screenAlpha);

        //listener for activating the console
//        setOnKeyTyped(ke -> {
//            if (ke.getCharacter().equals('~')
//                    | ke.getCharacter().equals('`')) {
//
//            }
//        });

        ui = new Group();
        //putIn a menu
        createStarMenu = new CreateStarMenu();
        ui.getChildren().add(createStarMenu);

        //putIn a menuButton
        createStarMenuSwitch = new CreateStarMenuSwitch("+",this);
        createStarMenuSwitch.setTranslateX(5);
        createStarMenuSwitch.setTranslateY(5);
        createStarMenuSwitch.setOnAction(ae -> {
            SystemStatus.setCreateStarMenuOut(!SystemStatus.isCreateStarMenuOut());

            if(SystemStatus.isCreateStarMenuOut()){
                getCreateStarMenu().setX(5);
            }else {
                getCreateStarMenu().setX(-150);
            }
        });
        ui.getChildren().add(createStarMenuSwitch);

        //putIn bottom PhysicsStatus bar
        universeStatusBar = new UniverseStatusBar();
        universeStatusBar.setWidth(getWidth());
        universeStatusBar.setHeight(25);
        universeStatusBar.setX(0);
        universeStatusBar.setY(getHeight() - universeStatusBar.getHeight());
        ui.getChildren().add(universeStatusBar);

        root.getChildren().add(ui);

        setOnMouseMoved(me -> {
            Mouse.setMouse_coordinate(new double[]{me.getX(),me.getY()});
        });

        setOnMouseDragged(me -> {
            Mouse.setMouse_coordinate(new double[]{me.getX(),me.getY()});
            Mouse.setMouse_coordinate(
                    new double[]{
                            me.getX(), me.getY()
                    }
            );

        });

        //listen the operations of mouse press
        setOnMousePressed(me -> {
            Mouse.setMouse_coordinate(new double[]{me.getX(),me.getY()});
            Mouse.setActivatedMouseButton(me.getButton());
            Mouse.setMousePressed(true);
            Mouse.setMousePressing(true);
            Mouse.setMouseReleasing(false);
            Mouse.setMouseScrolled(false);

        });

        //set operations on mouse release
        //new and clear
        setOnMouseReleased(me -> {
            Mouse.setMouse_coordinate(new double[]{me.getX(),me.getY()});
            Mouse.setActivatedMouseButton(me.getButton());
            Mouse.setMousePressed(false);
            Mouse.setMousePressing(false);
            Mouse.setMouseReleasing(true);
            Mouse.setMouseScrolled(false);
        });

        //set operations on mouse scrolled
        //enlarge and minimize
        setOnScroll(se -> {
            Mouse.setMouse_coordinate(new double[]{se.getX(),se.getY()});
            Mouse.setMouseScrollValue(se.getDeltaY());
            Mouse.setActivatedMouseButton(MouseButton.MIDDLE);
            Mouse.setMousePressed(false);
            Mouse.setMousePressing(false);
            Mouse.setMouseReleasing(false);
            Mouse.setMouseScrolled(true);
        });

        setOnKeyPressed(ke -> {
            KeyBoard.activeKey = ke.getCode();
            KeyBoard.isKeyReleasing = true;
            KeyBoard.isKeyPressed = true;

        });

        setOnKeyReleased(ke -> {
            KeyBoard.activeKey = ke.getCode();
            KeyBoard.isKeyPressed = false;

        });

    }

    //getter and setters
    public GameStage getGameStage() {
        return gameStage;
    }

    public GameCanvas getScreenAlpha() {
        return screenAlpha;
    }

    public UniverseStatusBar getStatusBar(){
        return universeStatusBar;
    }

    public CreateStarMenu getCreateStarMenu(){
        return createStarMenu;
    }

    public BetterSlider getMassSlider(){
        return createStarMenu.getMassSlider();
    }

    public BetterSlider getRadiusSlider(){
        return createStarMenu.getRadiusSlider();
    }

    public Group getUi() {
        return ui;
    }

}