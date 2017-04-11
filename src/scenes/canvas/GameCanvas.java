package scenes.canvas;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import models.Star;

/**
 * Created by lzx on 2017/4/6.
 */
public class GameCanvas extends Canvas implements Runnable {

    public static GraphicsContext gc;
    public static Star[] stars = new Star[50];
    public static Thread thread;
    public static boolean NEW;
    public static int MEX;
    public static int MEY;
    public static int InputMass;
    public static double InputVectorX;
    public static double InputVectorY;
    public static double InputAccelerationX;
    public static double InputAccelerationY;
    Star newStar = new Star();

    public GameCanvas(double width, double height) {
        super(width, height);
        InputMass = 0;
        InputVectorX = 0;
        InputVectorY = 0;
        InputAccelerationX = 0;
        InputAccelerationY = 0;

        NEW = false;
        MEX = 0;
        MEY = 0;

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
                NEW = true;
                MEX = (int) me.getX();
                MEY = (int) me.getY();
            }
        });

        gc = getGraphicsContext2D();

        drawShapes();
        thread = new Thread(this);
        thread.start();

    }

    public void drawShapes() {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, 800, 560);
        gc.setFill(Color.BLUE);

        for (int i = 0; i < stars.length; i++) {
            if (stars[i].onScreen) {
                getGraphicsContext2D().fillOval(stars[i].x, stars[i].y, stars[i].r * 2, stars[i].r * 2);
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

                if (distance > stars[i].r + s.r & s.mass != 0) {
                    s.accelerationX += 0.6673 * stars[i].mass * xDiff / Math.pow(distance, 3);
                    s.accelerationY += 0.6673 * stars[i].mass * yDiff / Math.pow(distance, 3);
                }

                if (distance <= stars[i].r + s.r) {

                    double newR = Math.sqrt(stars[i].r * stars[i].r + s.r * s.r);
                    int newMass = s.mass + stars[i].mass;
                    double newAX = ((s.accelerationX * s.mass) + (stars[i].accelerationX * stars[i].mass)) / newMass;
                    double newAY = ((s.accelerationY * s.mass) + (stars[i].accelerationY * stars[i].mass)) / newMass;
                    double newVX = ((s.speedX * s.mass) + (stars[i].speedX * stars[i].mass)) / newMass;
                    double newVY = ((s.speedY * s.mass) + (stars[i].speedY * stars[i].mass)) / newMass;

                    System.out.println(s.speedX + " " + stars[i].speedX);
                    if (s.r >= stars[i].r) {
                        stars[i].show(s.x, s.y);
                    } else {
                        stars[i].show(stars[i].x, stars[i].y);
                    }
                    stars[i].r = newR;
                    stars[i].accelerationX = newAX;
                    stars[i].accelerationY = newAY;
                    stars[i].speedX = newVX;
                    stars[i].speedY = newVY;
                    stars[i].mass = newMass;
                    s.remove();
                    return;
                }

            }
        }
        return;
    }

    public void clear() {
        for (int i = 0; i < stars.length; i++) {
            stars[i].remove();
        }
    }
    public void GameThread() {

        for (int i = 0; i < stars.length; i++) {
            if (stars[i].x > 810 + (2 * stars[i].r)
                    | stars[i].y > 570 + (2 * stars[i].r)
                    | stars[i].x < -10 - (2 * stars[i].r)
                    | stars[i].y < -10 - (2 * stars[i].r)) {
                stars[i].remove();
            }
            if (stars[i].onScreen) {
                stars[i].move();
                final int F = i;
                Platform.runLater(() -> {
                    gravityAcceleration(stars[F]);
                });

            } else {
                stars[i].initialize();
                if (NEW) {
                    stars[i].show(MEX - stars[i].r, MEY - stars[i].r);
                    stars[i].mass = InputMass;
                    stars[i].speedX = InputVectorX;
                    stars[i].speedY = InputVectorY;
                    stars[i].accelerationX = InputAccelerationX;
                    stars[i].accelerationY = InputAccelerationY;
                    NEW = false;
                }
            }
        }
        Platform.runLater(() -> {
            drawShapes();
        });
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            GameThread();
        }
    }
}
