package Main;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import scenes.Game;
import scenes.Start;
import scenes.canvas.GameCanvas;

/**
 * Created by lzx on 2017/3/21.
 */

public class MainStage extends Application implements Runnable {

    public static Group group;
    public static StackPane stackPane;

    public static Stage stage;
    public static Start start;
    public static Game game;

    public static Thread thread;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage HAHAstage) {

        group = new Group();
        game = new Game(group, 1025, 561);

        stackPane = new StackPane();
        start = new Start(stackPane, 1025, 561);

        stage = new Stage();
        stage.setScene(start);
        stage.setResizable(false);
        stage.setTitle("SpaceSimulator");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("..\\pictures\\icon.png")));
        stage.setOnCloseRequest(event -> thread.stop());

        stage.show();
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (stage.getScene() == game) {
                GameCanvas.stars[0].setCenterX(GameCanvas.stars[0].getCenterX() + GameCanvas.speedX);
                Game.gameCanvas.stars[0].setCenterY(GameCanvas.stars[0].getCenterY() + GameCanvas.speedY);
                Game.gameCanvas.drawShapes(GameCanvas.gc);
            } else if (stage.getScene() == start) {
                if (Start.opacity >= 1) {
                    Start.speedOpacity = -0.02;
                } else if (Start.opacity <= 0.1) {
                    Start.speedOpacity = 0.02;
                }
                Start.opacity += Start.speedOpacity;
                Start.startTitle.setTextFill(Color.gray(Start.opacity));
            }
        }
    }
}
