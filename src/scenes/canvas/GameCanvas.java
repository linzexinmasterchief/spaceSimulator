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
    public static Star[] stars = new Star[50];
    public static double InputMass;
    public static double InputVectorX;
    public static double InputVectorY;
    public static double InputAccelerationX;
    public static double InputAccelerationY;

    public GameCanvas(double width, double height) {
        super(width, height);
        InputMass = 0;
        InputVectorX = 0;
        InputVectorY = 0;
        InputAccelerationX = 0;
        InputAccelerationY = 0;
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star();
        }
        setTranslateX(200);
        setTranslateY(0);

        setOnMouseDragged(me -> {
            requestFocus();
            if (me.getButton() == MouseButton.PRIMARY) {
//                for (int i = 0; i < stars.length; i++) {
//                    stars[i].accelerationX = (me.getX() - stars[i].r - stars[i].x) / stars[i].mass;
//                    stars[i].accelerationY = (me.getY() - stars[i].r - stars[i].y) / stars[i].mass;
//                }
            }
        });
        setOnMouseClicked(me -> {
            requestFocus();
            if (me.getButton() == MouseButton.PRIMARY) {
//                for (int i = 0; i < stars.length; i++) {
//                    stars[i].accelerationX = (me.getX() - stars[i].r - stars[i].x) / stars[i].mass;
//                    stars[i].accelerationY = (me.getY() - stars[i].r - stars[i].y) / stars[i].mass;
//                }
            } else if (me.getButton() == MouseButton.SECONDARY) {
                for (int i = 0; i < stars.length; i++) {
                    if (stars[i].onScreen == false) {
                        stars[i].show(me.getX() - stars[i].r, me.getY() - stars[i].r);
                        stars[i].mass = InputMass;
                        stars[i].speedX = InputVectorX;
                        stars[i].speedY = InputVectorY;
                        stars[i].accelerationX = InputAccelerationX;
                        stars[i].accelerationY = InputAccelerationY;
                        drawShapes(gc);
                        return;
                    }
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
    }

    public void gravityAcceleration(Star s) {
        s.accelerationX = s.accelerationY = 0;
        for (int i = 0; i < stars.length; i++) {
            if (stars[i].onScreen & stars[i] != s) {
                double xDiff = (stars[i].x + stars[i].r) - (s.x + s.r);
                double yDiff = (stars[i].y + stars[i].r) - (s.y + s.r);
                double distance = Math.sqrt(xDiff * xDiff + yDiff * yDiff);
                if (distance != 0 & s.mass != 0) {
                    s.accelerationX += 0.6673 * stars[i].mass * xDiff / Math.pow(distance, 3);
                    s.accelerationY += 0.6673 * stars[i].mass * yDiff / Math.pow(distance, 3);
                }
                if (distance < stars[i].r + s.r) {
                    Star newStar = new Star();
                    newStar.r = Math.sqrt(stars[i].r * stars[i].r + s.r * s.r);
                    if (s.r >= stars[i].r) {
                        newStar.show(s.x - (s.r - newStar.r) / Math.sqrt(Math.pow(s.y - stars[i].y, 2) + Math.pow(s.x - stars[i].x, 2)) * (s.x - stars[i].x), s.y - (s.r - newStar.r) / Math.sqrt(Math.pow(s.y - stars[i].y, 2) + Math.pow(s.x - stars[i].x, 2)) * (s.y - stars[i].y));
                    } else {
                        newStar.show(stars[i].x - (stars[i].r - newStar.r) / Math.sqrt(Math.pow(stars[i].x - s.x, 2) + Math.pow(stars[i].x - s.x, 2)) * (stars[i].x - s.x), stars[i].y - (stars[i].r - newStar.r) / Math.sqrt(Math.pow(stars[i].y - s.y, 2) + Math.pow(stars[i].x - s.x, 2) * (stars[i].y - s.y)));
                    }
                    newStar.r = Math.sqrt(stars[i].r * stars[i].r + s.r * s.r);
                    newStar.mass = s.mass + stars[i].mass;
                    newStar.speedX = ((s.speedX * s.mass) + (stars[i].speedX * stars[i].mass)) / (s.mass + stars[i].mass);
                    newStar.speedY = ((s.speedY * s.mass) + (stars[i].speedY * stars[i].mass)) / (s.mass + stars[i].mass);
                    newStar.accelerationX = ((s.accelerationX * s.mass) + (stars[i].accelerationX * stars[i].mass)) / (s.mass + stars[i].mass);
                    newStar.accelerationY = ((s.accelerationY * s.mass) + (stars[i].accelerationY * stars[i].mass)) / (s.mass + stars[i].mass);
                    s.remove();
                    stars[i].remove();
                    for (int j = 0; j < stars.length; j++) {
                        if (stars[j].onScreen == false) {
                            stars[j] = newStar;
                            stars[j].onScreen = true;
                            break;
                        }
                    }

                }
            }
        }
    }

    public void clear() {
        for (int i = 0; i < stars.length; i++) {
            stars[i].remove();
        }
    }
    public void GameThread() {

        for (int i = 0; i < stars.length; i++) {
            if (stars[i].x > 810 + (2 * stars[i].r) | stars[i].y > 570 + (2 * stars[i].r) | stars[i].x < -10 - (2 * stars[i].r) | stars[i].y < -10 - (2 * stars[i].r)) {
                stars[i].remove();
            }
            if (stars[i].onScreen) {
                gravityAcceleration(stars[i]);
                stars[i].move();
            }
        }
        drawShapes(gc);
    }

}
