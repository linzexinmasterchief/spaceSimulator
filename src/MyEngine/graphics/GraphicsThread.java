package MyEngine.graphics;

import MyEngine.GameEngine;
import Stages.MainStage.gameScene.GameScene;
import Stages.MainStage.gameScene.canvas.GameCanvas;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import models.Star;

/**
 * Created by lzx on 2017/7/6.
 * graphics processor module
 */
public class GraphicsThread implements Runnable{

    private GameEngine engine;

    private GameScene gameScene;
    private GameCanvas gameCanvas;
    private GraphicsContext gc;

    //some properties of the program
    private boolean isExit;
    private boolean isPause;

    //the scale between graphics and physics
    //original is 1:1
    private double scaleX = 1;
    private double scaleY = 1;

    public GraphicsThread(GameEngine root_engine){
        engine = root_engine;
        isPause = true;
    }

    public void initialize(){
        engine.setGameStage();
        gameScene = engine.getGameStage().getGameScene();
        gameCanvas = gameScene.getGameCanvas();
        gc = gameCanvas.getGraphicsContext2D();

        //initialize program properties
        isExit = false;
        isPause = false;
    }

    //screen painting function
    public void drawShapes() {

        if (isPause){
            return;
        }

        //fill the back ground with black color
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());

        //iterate the star list to draw all the exist stars in the universe
        for (Star star : engine.getStars()) {

            int r = 0;
            int g = 0;
            int b = 0;

            //change the color of pen according to the mass of the star to paint the stars
            if (star.mass > 500){
                r = 0;
                g = 0;
                b = 255;
            }else if (star.mass < 0){
                r = 255;
                g = 0;
                b = 0;
            }else {
                if (star.mass > 255){
                    r = (int) (500 - star.mass);
                    g = (int) (500 - star.mass);
                    b = 255;
                }else {
                    r = 255;
                    g = (int) star.mass;
                    b = (int) star.mass;
                }
            }
            gc.setFill(Color.rgb(r, g, b));

            star.onScreen = true;
            star.onScreen = !(star.centerX < engine.getCamera().getCenterX() - engine.getCamera().getWidth() / 2 - star.r
                    || star.centerX > engine.getCamera().getCenterX() + engine.getCamera().getWidth() / 2 + star.r
                    || star.centerY < engine.getCamera().getCenterY() - engine.getCamera().getHeight() / 2 - star.r
                    || star.centerY > engine.getCamera().getCenterY() + engine.getCamera().getHeight() / 2 + star.r);

            //calculate the actual display size according to the scale
            double starWidth = star.r * 2 / scaleX;
            double starHeight = star.r * 2 / scaleY;

            //make sure there is always a small point displaying for every star, even the scale is crazily large
            if (starWidth < 1) {
                starWidth = 1;
            }
            if (starHeight < 1) {
                starHeight = 1;
            }

            //only display the star if it is on screen
            //in the camera range
            if (star.onScreen) {
                gc.fillOval(

                        //a little math here, should be reliable after 6 changes
                        //also, this is related to the math above when the user
                        //new a star, they are reverse functions to each other

                        //this is convert the universe coordinates to display coordinates
                        (
                                star.centerX - (engine.getCamera().getCenterX() - engine.getUniverse().getWidth() / 2)
                                        - (engine.getUniverse().getWidth() - engine.getCamera().getWidth()) / 2
                        )
                                / scaleX
                                - starWidth / 2,
                        (
                                star.centerY - (engine.getCamera().getCenterY() - engine.getUniverse().getHeight() / 2)
                                        - (engine.getUniverse().getHeight() - engine.getCamera().getHeight()) / 2
                        )
                                / scaleY
                                - starHeight / 2,
                        starWidth,
                        starHeight
                );
            }
        }
    }

    //function designed for screen cleaning
    //calling it will remove all the stars in the universe
    public void clear() {
        for (Star star : engine.getStars()) {
            star.remove();
        }
    }

    //getters and setters
    public boolean isPause(){
        return isPause;
    }

    public void setPause(boolean pause){
        isPause = pause;
    }

    public boolean isExit(){
        return isExit;
    }

    public void setExit(boolean exit) {
        isExit = exit;
    }

    public double getScaleX(){
        return scaleX;
    }

    public double getScaleY(){
        return scaleY;
    }

    public void setScaleX(double new_scaleX){
        scaleX = new_scaleX;
    }

    public void setScaleY(double new_scaleY){
        scaleY = new_scaleY;
    }

    //the graphic thread operation function
    @Override
    public void run() {
        while (!isExit) {
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //send the rendering job to a background thread
            Platform.runLater(this::drawShapes);
        }
    }
}
