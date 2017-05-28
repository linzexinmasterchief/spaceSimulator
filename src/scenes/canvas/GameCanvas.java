package scenes.canvas;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import models.Camera;
import models.Star;
import models.Universe;

/**
 * Created by lzx on 2017/4/6.
 * this is the game canvas that will draw the stars
 */
public class GameCanvas extends Canvas implements Runnable {

    private Universe universe;
    private Camera camera;

    private GraphicsContext gc;

    private int mouseEventX;
    private int mouseEventY;

    private final double HeightWidthScale = 0.56;

    public GameCanvas() {
        universe = new Universe(2000, 2000);
        Thread universeThread = new Thread(universe);
        universeThread.start();

        camera = new Camera(1000, 560, 1000,1000);

        mouseEventX = 0;
        mouseEventY = 0;

        gc = getGraphicsContext2D();

        double[] mouse_coordinate = new double[2];
        setOnMousePressed(me -> {
            mouse_coordinate[0] = me.getX();
            mouse_coordinate[1] = me.getY();
        });

        setOnMouseReleased(me -> {
            universe.getBufferStar().vectorX = (me.getX() - mouse_coordinate[0]) / 100;
            universe.getBufferStar().vectorY = (me.getY() - mouse_coordinate[1]) / 100;

            if (me.getButton() == MouseButton.PRIMARY) {
                universe.setNewStarExist(true);
                mouseEventX = (int) me.getX();
                mouseEventY = (int) me.getY();

                for (int i = 0; i < universe.getStars().length; i++) {
//                    checkBound(i);
                    if (!universe.getStars()[i].onScreen) {
                        universe.getStars()[i].initialize();
                        if (universe.getNewStarExist()) {
                            universe.getStars()[i] = new Star(universe.getBufferStar());
                            universe.getBufferStar().remove();

                            universe.getStars()[i].show(
                                    ((camera.getWidth() / 1000) - 1) * (1000 / 2)
                                            - (camera.getWidth() / 1000) * (mouseEventX - camera.getCenterX()),
                                    ((camera.getHeight() / 560) - 1) * (560 / 2)
                                            - (camera.getHeight() / 560) * (mouseEventY - camera.getCenterY())
                            );
                            universe.getStars()[i].onScreen = true;
                            universe.setNewStarExist(false);
                            drawShapes();
                        }
                    }
                }
            }
            if (me.getButton() == MouseButton.SECONDARY) {
                clear();
            }
        });

        setOnScroll(se -> {
            if (se.getDeltaY() < 0) {
                camera.setWidth(camera.getWidth() - 10);
                camera.setHeight(camera.getHeight() - 10 * HeightWidthScale);
            }else {
                camera.setWidth(camera.getWidth() + 10);
                camera.setHeight(camera.getHeight() + 10 * HeightWidthScale);
            }
        });

        drawShapes();
        Thread thread = new Thread(this);
        thread.start();

    }

    private void drawShapes() {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, this.getWidth(), this.getHeight());
        gc.setFill(Color.BLUE);

        for (Star star : universe.getStars()) {
            if (star.onScreen) {
                getGraphicsContext2D().fillOval(
                        (1 - (camera.getWidth() / 1000)) * (1000 / 2)
                                + (camera.getWidth() / 1000) * (star.centerX - camera.getCenterX())
                                - star.r,
                        (1 - (camera.getHeight() / 560)) * (560 / 2)
                                + (camera.getHeight() / 560) * (star.centerY - camera.getCenterY())
                                - star.r,
                        star.r * 2 * (camera.getWidth() / this.getWidth()),
                        star.r * 2 * (camera.getHeight() / this.getHeight())
                );
                System.out.println((1 - (camera.getWidth() / 1000)) * (1000 / 2)
                        - (camera.getWidth() / 1000) * (star.centerX - camera.getCenterX())
                        - star.r);
            }
        }
    }

    private void clear() {
        for (Star star : universe.getStars()) {
            star.remove();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(this::drawShapes);
        }
    }
}
