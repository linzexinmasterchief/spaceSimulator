package space1;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import scenes.Game;
import scenes.Start;

/**
 * Created by lzx on 2017/3/21.
 */

/**
 * TODO use stars list to contain 100 star unit
 * TODO gravity (long long time later)
 * TODO replace the function of right-click from initialize to new star
 * TODO delete the star if it moves out of the screen
 * TODO determine if there is still place for a new star
 */

public class Frame extends Application implements Runnable {

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
    public void start(Stage pstage) {

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
                Game.stars[0].setCenterX(Game.stars[0].getCenterX() + Game.speedX);
                Game.stars[0].setCenterY(Game.stars[0].getCenterY() + Game.speedY);
                Game.drawShapes(Game.gc);
            } else if (stage.getScene() == start) {
                if (Game.opacity >= 1) {
                    Game.speedOpacity = -0.02;
                } else if (Game.opacity <= 0.1) {
                    Game.speedOpacity = 0.02;
                }
                Game.opacity += Game.speedOpacity;
                Start.startTitle.setTextFill(Color.gray(Game.opacity));
            }
        }
    }
}
