package scenes.canvas;

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
    public static double InputMass;
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

        int c = 0;
        for (int i = 0; i < stars.length; i++) {
            if (stars[i].onScreen) {
                getGraphicsContext2D().fillOval(stars[i].x, stars[i].y, stars[i].r * 2, stars[i].r * 2);
                System.out.println(i);
            }
        }
        getGraphicsContext2D().save();
        System.out.println(c);
    }

    public void gravityAcceleration(Star s) {
        boolean REMOVE = false;
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
                    double R = Math.sqrt(stars[i].r * stars[i].r + s.r * s.r);
                    double newMass = s.mass + stars[i].mass;
                    double newSpeedX = ((s.speedX * s.mass) + (stars[i].speedX * stars[i].mass)) / (s.mass + stars[i].mass);
                    double newSpeedY = ((s.speedY * s.mass) + (stars[i].speedY * stars[i].mass)) / (s.mass + stars[i].mass);
                    stars[i].accelerationX = ((s.accelerationX * s.mass) + (stars[i].accelerationX * stars[i].mass)) / (s.mass + stars[i].mass);
                    stars[i].accelerationY = ((s.accelerationY * s.mass) + (stars[i].accelerationY * stars[i].mass)) / (s.mass + stars[i].mass);
                    if (s.r >= stars[i].r) {
                        stars[i].show(s.x - (s.r - R) / Math.sqrt(Math.pow(s.y - stars[i].y, 2) + Math.pow(s.x - stars[i].x, 2)) * (s.x - stars[i].x), s.y - (s.r - R) / Math.sqrt(Math.pow(s.y - stars[i].y, 2) + Math.pow(s.x - stars[i].x, 2)) * (s.y - stars[i].y));
                    } else {
                        stars[i].show(stars[i].x - (stars[i].r - R) / Math.sqrt(Math.pow(stars[i].x - s.x, 2) + Math.pow(stars[i].x - s.x, 2)) * (stars[i].x - s.x), stars[i].y - (stars[i].r - R) / Math.sqrt(Math.pow(stars[i].y - s.y, 2) + Math.pow(stars[i].x - s.x, 2) * (stars[i].y - s.y)));
                    }
                    stars[i].speedX = newSpeedX;
                    stars[i].speedY = newSpeedY;
                    stars[i].r = R;
                    stars[i].mass = newMass;
                    REMOVE = true;
                }
            }
        }
        if (REMOVE) {
            s.remove();
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
            if (stars[i].x > 810 + (2 * stars[i].r) | stars[i].y > 570 + (2 * stars[i].r) | stars[i].x < -10 - (2 * stars[i].r) | stars[i].y < -10 - (2 * stars[i].r)) {
                stars[i].remove();
            }
            if (stars[i].onScreen) {
                gravityAcceleration(stars[i]);
                if (stars[i].onScreen) {
                    stars[i].move();
                }
            } else {
                stars[i].remove();
                if (NEW) {
                    stars[i].initialize();
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
        drawShapes();
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
