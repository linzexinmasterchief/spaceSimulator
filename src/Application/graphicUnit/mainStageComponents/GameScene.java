package Application.graphicUnit.mainStageComponents;

import Application.graphicUnit.GameStage;
import Application.graphicUnit.mainStageComponents.gameSceneComponents.ui.CreateStarMenuSwitch;
import Application.graphicUnit.mainStageComponents.gameSceneComponents.ui.CreateStarMenu;
import Application.graphicUnit.mainStageComponents.gameSceneComponents.GameCanvas;
import javafx.scene.Group;
import javafx.scene.Scene;
import Application.graphicUnit.mainStageComponents.gameSceneComponents.ui.UniverseStatusBar;

public class GameScene extends Scene{

    //create reference to root game stage
    private final GameStage gameStage;

    //give access to the canvas
    private final GameCanvas gameCanvas;

    private final UniverseStatusBar universeStatusBar;

    private final CreateStarMenu createStarMenu;

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
        //add game canvas
        root.getChildren().add(gameCanvas);

        //listener for activating the console
//        setOnKeyTyped(ke -> {
//            if (ke.getCharacter().equals('~')
//                    | ke.getCharacter().equals('`')) {
//
//            }
//        });

        //add a menu
        createStarMenu = new CreateStarMenu(this, root);

        //add a menuButton
        createStarMenuSwitch = new CreateStarMenuSwitch("+",this);
        createStarMenuSwitch.setTranslateX(5);
        createStarMenuSwitch.setTranslateY(5);
        root.getChildren().add(createStarMenuSwitch);

        //add bottom PhysicsStatus bar
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

    public float getMass(){
        return (float) createStarMenu.getMassSlider().getValue();
    }

    public float getRadius(){
        return (float) createStarMenu.getRadiusSlider().getValue();
    }

}