package Application.stages.MainStage.gameScene;

import Application.stages.MainStage.GameStage;
import Application.stages.MainStage.gameScene.canvas.GameCanvas;
import Application.stages.SettingStage.SettingStage;
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

    //create reference to root game stage
    private GameStage gameStage;

    //give access to the canvas
    private GameCanvas gameCanvas;

    //menu components
    private MenuButtonPrefab settingBtn;
    private MenuSliderPrefab massSlider;
    private MenuSliderPrefab raiusSlider;

    //menu toggle
    private Button menuButton;

    //option menu
    private SettingStage settingWindow;
    private boolean settingToggled;

    //constructor
    public GameScene(Group root, double width, double height, GameStage gameStage) {

        //construct the game scene as a scene
        super(root, width, height);
        this.gameStage = gameStage;

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
        settingBtn.setTranslateY(5);
        settingBtn.setOnAction(ae -> {
            settingToggled = !settingToggled;
            if (settingToggled) {
                settingWindow.show();
            }else {
                settingWindow.hide();
            }
        });
        root.getChildren().add(settingBtn);

        massSlider = new MenuSliderPrefab(40, 10);
        root.getChildren().add(massSlider);

        raiusSlider = new MenuSliderPrefab(80, 5);
        raiusSlider.setMax(20);
        root.getChildren().add(raiusSlider);

        //add a menu menuButton
        menuButton = new Button("+");
        menuButton.setBackground(new Background(new BackgroundFill(Color.GRAY, new CornerRadii(20), null)));
        menuButton.setOnMouseEntered(me -> menuButton.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, new CornerRadii(20), null))));
        menuButton.setOnMouseExited(me -> menuButton.setBackground(new Background(new BackgroundFill(Color.GRAY, new CornerRadii(20), null))));
        menuButton.setOnMouseClicked(me -> {
            setClicked(!isClicked());
            if (isClicked()) {
                //menuButton change
                menuButton.setTranslateX(170);
                menuButton.setText("-");
                //pop out
                settingBtn.setVisible(true);
                massSlider.setVisible(true);
                raiusSlider.setVisible(true);

            } else {
                //menuButton change
                menuButton.setTranslateX(10);
                menuButton.setText("+");
                //goes back
                settingBtn.setVisible(false);
                massSlider.setVisible(false);
                raiusSlider.setVisible(false);
            }
        });
        menuButton.setTranslateX(10);
        menuButton.setTranslateY(10);
        menuButton.setMinWidth(24);
        root.getChildren().add(menuButton);
    }


    private boolean isClicked() {
        return clicked;
    }

    private void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    public GameStage getGameStage() {
        return gameStage;
    }

    public GameCanvas getGameCanvas() {
        return gameCanvas;
    }

    public double getMass(){
        return massSlider.getValue();
    }

    public double getRadius(){
        return raiusSlider.getValue();
    }

}