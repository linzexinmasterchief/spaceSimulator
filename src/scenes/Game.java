package scenes;

import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import models.Star;
import space1.Frame;

import static javafx.scene.paint.Color.BLACK;

/**
 * Created by lzx on 2017/4/5.
 */
public class Game extends Scene {
    public static GraphicsContext gc;

    public static Star[] stars;

    public static double speedX = 0;
    public static double speedY = 0;

    public static double opacity = 1;
    public static double speedOpacity = 0.02;

    public Game(Parent root, double width, double height) {
        super(root, width, height);

        stars = new Star[50];

        Star earth = new Star();
        earth.setCenterX(250);
        earth.setCenterY(280);
        stars[0] = earth;

        MenuBar menuBar = new MenuBar();
        menuBar.setTranslateX(0);
        menuBar.setTranslateY(0);
        Menu menu1 = new Menu("File");
        Menu menu2 = new Menu("Options");
        Menu menu3 = new Menu("Help");
        menuBar.setPrefWidth(1030);
        menuBar.getMenus().addAll(menu1, menu2, menu3);
        menuBar.setBackground(new Background(new BackgroundFill(Color.GRAY, new CornerRadii(0), null)));

        Button backBtn = new Button("              Main menu              ");
        backBtn.setTranslateX(10);
        backBtn.setTranslateY(30);
        backBtn.setDefaultButton(true);
        backBtn.setOnMouseEntered(me -> backBtn.setBackground(new Background(new BackgroundFill(Color.CORNFLOWERBLUE, new CornerRadii(10), null))));
        backBtn.setOnMouseExited(me -> backBtn.setBackground(new Background(new BackgroundFill(Color.GRAY, new CornerRadii(10), null))));
        backBtn.setBackground(new Background(new BackgroundFill(Color.GRAY, new CornerRadii(10), null)));
        backBtn.setTextFill(Color.WHITE);
        backBtn.setOnAction((ActionEvent e) -> {
            Frame.stage.setScene(Frame.start);
        });

        Canvas canvas = new Canvas(825, 561);
        canvas.setTranslateX(200);
        canvas.setTranslateY(0);
        canvas.setOnMouseDragged(me -> {
            if (me.getButton() == MouseButton.PRIMARY) {
                speedX = (me.getX() - 20 - stars[0].getCenterX()) / 20;
                speedY = (me.getY() - 20 - stars[0].getCenterY()) / 20;
            } else if (me.getButton() == MouseButton.SECONDARY) {
                stars[0].setCenterX(me.getX() - 20);
                stars[0].setCenterY(me.getY() - 20);
                speedX = speedY = 0;
            }
        });
        canvas.setOnMouseClicked(me -> {
            if (me.getButton() == MouseButton.PRIMARY) {
                speedX = (me.getX() - 20 - stars[0].getCenterX()) / 100;
                speedY = (me.getY() - 20 - stars[0].getCenterY()) / 100;
            } else if (me.getButton() == MouseButton.SECONDARY) {
                stars[0].setCenterX(me.getX() - 20);
                stars[0].setCenterY(me.getY() - 20);
                speedX = speedY = 0;
            }
        });

        gc = canvas.getGraphicsContext2D();
        gc.setFill(BLACK);
        gc.fillRect(0, 0, 825, 561);
        drawShapes(gc);

        setFill(BLACK);

        Frame.group.getChildren().add(backBtn);
        Frame.group.getChildren().add(canvas);
        Frame.group.getChildren().add(menuBar);

    }

    public static void drawShapes(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, 825, 561);
        gc.setFill(Color.BLUE);
        gc.fillOval(stars[0].getCenterX(), stars[0].getCenterY(), 40, 40);
    }
}
