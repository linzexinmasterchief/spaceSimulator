package Stages.MainStage.gameScene;

import MyEngine.GameEngine;
import Stages.MainStage.gameScene.canvas.GameCanvas;
import Stages.SettingStage.SettingStage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import models.MenuButtonPrefab;
import models.MenuSliderPrefab;

public class GameScene extends Scene {

    private boolean clicked;

    //create reference to root game engine
    private GameEngine gameEngine;

    //give access to the canvas
    private GameCanvas gameCanvas;

    //menu components
    private MenuButtonPrefab settingBtn;
    private MenuSliderPrefab massSlider;

    //menu toggle
    private Button menuButton;

    //option menu
    private SettingStage settingWindow;
    private boolean settingToggled;

    //constructor
    public GameScene(Group root, double width, double height, GameEngine rootEngine) {

        //construct the game scene as a scene
        super(root, width, height);
        gameEngine = rootEngine;

        clicked = false;

        //initialize the canvas
        //size
        gameCanvas = new GameCanvas(1000, 560, this);
        //add game canvas
        root.getChildren().add(gameCanvas);

        //listener for activating the console
//        setOnKeyTyped(ke -> {
//            if (ke.getCharacter().equals('~')
//                    | ke.getCharacter().equals('`')) {
//
//            }
//        });

        //setting window
        settingWindow = new SettingStage(400, 400, 400, 400);
        settingWindow.setOnCloseRequest(event -> settingToggled = false);
        settingToggled = false;

        //add a menu
        //setting menuButton
        settingBtn = new MenuButtonPrefab("setting");
        settingBtn.setOnAction(ae -> {
            settingToggled = !settingToggled;
            if (settingToggled) {
                settingWindow.show();
            }else {
                settingWindow.hide();
            }
        });
        root.getChildren().add(settingBtn);

        massSlider = new MenuSliderPrefab();
        massSlider.setMin(0);
        massSlider.setMax(100);
        massSlider.setValue(10);
        massSlider.setMajorTickUnit(50);
        massSlider.setMinorTickCount(5);
        massSlider.setBlockIncrement(10);
//        massSlider.setMaxHeight(1);
        massSlider.setTranslateY(30);
        root.getChildren().add(massSlider);

        //add a menu menuButton
        menuButton = new Button("+");
        menuButton.setBackground(new Background(new BackgroundFill(Color.GRAY, new CornerRadii(20), null)));
        menuButton.setOnMouseEntered(me -> menuButton.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, new CornerRadii(20), null))));
        menuButton.setOnMouseExited(me -> menuButton.setBackground(new Background(new BackgroundFill(Color.GRAY, new CornerRadii(20), null))));
        menuButton.setOnMouseClicked(me -> {
            setClicked(!isClicked());
            if (isClicked()) {
                //pop out
                settingBtn.setVisible(true);
                massSlider.setVisible(true);

            } else {
                //goes back
                settingBtn.setVisible(false);
                massSlider.setVisible(false);
            }
        });
        menuButton.setTranslateX(10);
        menuButton.setTranslateY(10);
        root.getChildren().add(menuButton);
    }


    private boolean isClicked() {
        return clicked;
    }

    private void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    public GameEngine getRootEngine() {
        return gameEngine;
    }

    public GameCanvas getGameCanvas() {
        return gameCanvas;
    }

    public double getMass(){
        return massSlider.getValue();
    }

}