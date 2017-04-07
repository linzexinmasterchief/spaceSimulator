package scenes.canvas;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import models.Star;

import static javafx.scene.paint.Color.BLACK;

/**
 * Created by lzx on 2017/4/6.
 */
public class GameCanvas extends Canvas {

    public static GraphicsContext gc;
    public static Star[] stars = new Star[10];

    private boolean isMousePressed;
    private boolean isMouseClicked;

    public GameCanvas(double width, double height) {
        super(width, height);
        isMouseClicked = false;
        isMousePressed = false;

        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star();
        }
        stars[0].onScreen = true;
        System.out.println(stars[0].x);
        setTranslateX(200);
        setTranslateY(0);
        setOnMousePressed(me -> {
            if (me.getButton() == MouseButton.PRIMARY) {
                for (int i = 0; i < stars.length; i++) {
                    stars[i].speedX = (me.getX() - stars[i].r - stars[i].x) / 20;
                    stars[i].speedY = (me.getY() - stars[i].r - stars[i].y) / 20;
                }
            } else if (me.getButton() == MouseButton.SECONDARY) {
                for (int i = 0; i < stars.length; i++) {
                    stars[i].setPosition(me.getX() - stars[i].r, me.getY() - stars[i].r);
                    stars[i].speedX = stars[i].speedY = 0;
                }
            }
        });
        setOnMouseClicked(me -> {
            if (me.getButton() == MouseButton.PRIMARY) {
                for (int i = 0; i < stars.length; i++) {
                    stars[i].speedX = (me.getX() - stars[i].r - stars[i].x) / 100;
                    stars[i].speedY = (me.getY() - stars[i].r - stars[i].y) / 100;
                }
            } else if (me.getButton() == MouseButton.SECONDARY) {
                for (int i = 0; i < stars.length; i++) {
                    stars[i].setPosition(me.getX() - stars[i].r, me.getY() - stars[i].r);
                    stars[i].speedX = stars[i].speedY = 0;
                }
            }
        });

        gc = getGraphicsContext2D();
        gc.setFill(BLACK);
        gc.fillRect(0, 0, 800, 560);
        drawShapes(gc);
    }

    public void drawShapes(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, 800, 560);
        gc.setFill(Color.BLUE);

        for (int i = 0; i < stars.length; i++) {
            if (stars[i].onScreen) {
                gc.fillOval(stars[i].x, stars[i].y, stars[i].r * 2, stars[i].r * 2);
            }
        }
        System.out.println("painting");
    }

    public void GameThread() {
        for (int i = 0; i < stars.length; i++) {
            if (stars[i].onScreen) {
                stars[i].x = stars[i].x + stars[i].speedX;
                stars[i].y = stars[i].y + stars[i].speedY;
            }
        }
        drawShapes(gc);
        System.out.println("GameRunning");
    }

}
