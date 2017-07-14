package Application.Engine.graphics;

import Application.Engine.Engine;
import Application.stages.MainStage.gameScene.GameScene;
import Application.stages.MainStage.gameScene.canvas.GameCanvas;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import models.PhysicsComponents.Star;
import models.SystemComponents.ThreadModule;

/**
 * Created by lzx on 2017/7/6.
 * graphics processor module
 */
public class GraphicsModule extends ThreadModule implements Runnable{

    private GameScene gameScene;
    private GameCanvas gameCanvas;
    private GraphicsContext gc;

    //the scale between graphics and physics
    //original is 1:1
    private double scaleX = 1;
    private double scaleY = 1;

    public GraphicsModule(Engine root_engine){
        super(root_engine);
    }

    @Override
    public void initialize(){
        //override default initialize block
        gameScene = engine.getLauncher().getGameStage().getGameScene();
        gameCanvas = gameScene.getGameCanvas();
        gc = gameCanvas.getGraphicsContext2D();

        //initialize program properties
        setExit(false);
        setPause(false);
    }

    //screen painting function
    public void drawShapes() {

        //fill the back ground with black color
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());

        //iterate the star list to draw all the exist stars in the universe
        for (Star star : engine.getStars()) {

            int r, g, b;

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

//        gc.fillText(""+systemStatus.getStarAmount(),400,15);

    }

    //getters and setters

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
        while (!isExit()) {
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (isPause()){
                continue;
            }
            //send the rendering job to a background thread
            Platform.runLater(this::drawShapes);
        }
    }
}
