package scenes.gameScene.canvas;

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

    private final double HeightWidthScale = 0.56;
    private Universe universe;
    private Camera camera;

    private GraphicsContext gc;
    private double mouseEventX;
    private double mouseEventY;

    //determine how fast the star moves with the same drag distance
    private int dragSpeedConstant = 100;
    //determine how fast the camera enlarge/minify
    private int sizeChangeSpeed = 10;

    public GameCanvas(double width, double height) {
        super(width, height);
        universe = new Universe(2000, 2000);
        Thread universeThread = new Thread(universe);
        universeThread.start();

        camera = new Camera(this.getWidth(), this.getHeight(), universe.getWidth() / 2, universe.getHeight() / 2);

        mouseEventX = 0;
        mouseEventY = 0;

        gc = getGraphicsContext2D();

        double[] mouse_coordinate = new double[2];
        setOnMousePressed(me -> {
            mouse_coordinate[0] = me.getX();
            mouse_coordinate[1] = me.getY();
        });

        setOnMouseReleased(me -> {
            universe.getBufferStar().vectorX = (me.getX() - mouse_coordinate[0]) / dragSpeedConstant;
            universe.getBufferStar().vectorY = (me.getY() - mouse_coordinate[1]) / dragSpeedConstant;

            if (me.getButton() == MouseButton.PRIMARY) {
                universe.setNewStarExist(true);
                mouseEventX = me.getX();
                mouseEventY = me.getY();

                for (int i = 0; i < universe.getStars().length; i++) {
                    if (!universe.getStars()[i].onScreen) {
                        universe.getStars()[i].initialize();
                        if (universe.getNewStarExist()) {
                            universe.getStars()[i] = new Star(universe.getBufferStar());
                            universe.getBufferStar().remove();

                            universe.getStars()[i].show(
                                    (camera.getCenterX() - universe.getWidth() / 2)
                                            + (universe.getWidth() - camera.getWidth()) / 2
                                            + mouseEventX * (camera.getWidth() / camera.getOriginalWidth()),
                                    (camera.getCenterY() - universe.getHeight() / 2)
                                            + (universe.getHeight() - camera.getHeight()) / 2
                                            + mouseEventY * (camera.getHeight() / camera.getOriginalHeight())
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
            mouseEventX = se.getX();
            mouseEventY = se.getY();

            if (se.getDeltaY() < 0) {
                camera.setWidth(camera.getWidth() + sizeChangeSpeed);
                camera.setHeight(camera.getHeight() + sizeChangeSpeed * HeightWidthScale);

                camera.setCenterX(camera.getCenterX() - (mouseEventX - this.getWidth() / 2) / this.getWidth() * 10);
                camera.setCenterY(camera.getCenterY() - (mouseEventY - this.getHeight() / 2) / this.getHeight() * 10);
            } else {
                camera.setWidth(camera.getWidth() - sizeChangeSpeed);
                camera.setHeight(camera.getHeight() - sizeChangeSpeed * HeightWidthScale);

                camera.setCenterX(camera.getCenterX() + (mouseEventX - this.getWidth() / 2) / this.getWidth() * 10);
                camera.setCenterY(camera.getCenterY() + (mouseEventY - this.getHeight() / 2) / this.getHeight() * 10);
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
            double scaleX = camera.getOriginalWidth() / camera.getWidth();
            double scaleY = camera.getOriginalHeight() / camera.getHeight();

            double starWidth = star.r * 2 * scaleX;
            double starHeight = star.r * 2 * scaleY;
            if (starWidth < 1) {
                starWidth = 1;
            }
            if (starHeight < 1) {
                starHeight = 1;
            }
            if (star.onScreen) {
                getGraphicsContext2D().fillOval(
                        (
                                star.centerX - (camera.getCenterX() - universe.getWidth() / 2)
                                        - (universe.getWidth() - camera.getWidth()) / 2
                        )
                                * scaleX
                                - star.r,
                        (
                                star.centerY - (camera.getCenterY() - universe.getHeight() / 2)
                                        - (universe.getHeight() - camera.getHeight()) / 2
                        )
                                * scaleY
                                - star.r,
                        starWidth,
                        starHeight
                );
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
