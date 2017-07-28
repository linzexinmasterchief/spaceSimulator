package Application.stages.MainStage.gameScene;

import Application.stages.MainStage.GameStage;
import Application.stages.MainStage.gameScene.UIprefabs.CreateStarMenuSwitch;
import Application.stages.MainStage.gameScene.UIprefabs.createStarMenu.CreateStarMenu;
import Application.stages.MainStage.gameScene.canvas.GameCanvas;
import javafx.scene.Group;
import javafx.scene.Scene;
import Application.stages.MainStage.gameScene.UIprefabs.universeStatusBar.UniverseStatusBar;

public class GameScene extends Scene {

    //create reference to root game stage
    private GameStage gameStage;

    //give access to the canvas
    private GameCanvas gameCanvas;

    private UniverseStatusBar universeStatusBar;

    private CreateStarMenu createStarMenu;

    //menu toggle
    private CreateStarMenuSwitch createStarMenuSwitch;

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

        //add bottom physicsStatus bar
        universeStatusBar = UniverseStatusBar.createUniverseStatusBar(this, root);

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

    public double getMass(){
        return createStarMenu.getMassSlider().getValue();
    }

    public double getRadius(){
        return createStarMenu.getRaiusSlider().getValue();
    }

}