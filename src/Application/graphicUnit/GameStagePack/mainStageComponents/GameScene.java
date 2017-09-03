package Application.graphicUnit.GameStagePack.mainStageComponents;

import Application.graphicUnit.GameStagePack.GameStage;
import Application.graphicUnit.GameStagePack.mainStageComponents.gameSceneComponents.ui.CreateStarMenuSwitch;
import Application.graphicUnit.GameStagePack.mainStageComponents.gameSceneComponents.ui.CreateStarMenu;
import Application.graphicUnit.GameStagePack.mainStageComponents.gameSceneComponents.GameCanvas;
import javafx.scene.Group;
import javafx.scene.Scene;
import Application.graphicUnit.GameStagePack.mainStageComponents.gameSceneComponents.ui.UniverseStatusBar;
import models.uiComponentModels.betterUI.BetterSlider;

public class GameScene extends Scene{

    //create reference to root game stage
    private GameStage gameStage;

    //give access to the canvas
    public GameCanvas gameCanvas;

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
        gameCanvas = new GameCanvas(getWidth(), getHeight(), this);
        //putIn game canvas
        root.getChildren().add(gameCanvas);

        //listener for activating the console
//        setOnKeyTyped(ke -> {
//            if (ke.getCharacter().equals('~')
//                    | ke.getCharacter().equals('`')) {
//
//            }
//        });

        //putIn a menu
        createStarMenu = new CreateStarMenu(this, root);

        //putIn a menuButton
        createStarMenuSwitch = new CreateStarMenuSwitch("+",this);
        createStarMenuSwitch.setTranslateX(5);
        createStarMenuSwitch.setTranslateY(5);
        root.getChildren().add(createStarMenuSwitch);

        //putIn bottom PhysicsStatus bar
        universeStatusBar = new UniverseStatusBar(this);
        universeStatusBar.setWidth(getWidth());
        universeStatusBar.setHeight(25);
        universeStatusBar.setX(0);
        universeStatusBar.setY(getHeight() - universeStatusBar.getHeight());
        universeStatusBar.join(root);

    }

    public GameStage getGameStage() {
        return gameStage;
    }

    public GameCanvas getGameCanvas() {
        return gameCanvas;
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

}