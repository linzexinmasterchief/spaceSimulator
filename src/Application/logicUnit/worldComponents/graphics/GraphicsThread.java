package Application.logicUnit.worldComponents.graphics;

import Application.graphicUnit.GameStagePack.GameStage;
import Application.graphicUnit.GameStagePack.mainStageComponents.gameSceneComponents.GameCanvas;
import Application.logicUnit.World;
import Application.logicUnit.worldComponents.physics.physicsComponents.universeComponents.Star;
import Application.status.SystemStatus;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import models.systemComponentModels.ThreadModel;

/**
 * Created by lzx on 2017/7/6.
 * graphics processor module
 */
public class GraphicsThread extends ThreadModel {

    private GameStage gameStage;

    //the scale between graphics and physics
    //original is 1:1
    private float scaleX = 1.0f;
    private float scaleY = 1.0f;

    private int starDisplayWidth = 1;
    private int starDisplayHeight = 1;

    private float biasX = 0;
    private float biasY = 0;

    private long update = 0;
    private long sleep = 0;
    private long before = 0;
    private long t = 0;

    private Color[] colors = {Color.RED};

    //determine if the current screen is screenAlpha
    private boolean flag;

    public GraphicsThread(World root_world){
        super(root_world);
    }

    @Override
    public void initialize(){
        //override default initialize block
        gameStage = world.getLauncher().getGameStage();

        //initialize program properties
        setExit(false);
        setPause(false);

        updateBias();

        flag = true;

    }

    //screen painting function
    public void updateFrame() {
        Platform.runLater(() -> {
            //fill the back ground with black color
            getCanvas(flag).getGraphicsContext2D().setFill(Color.BLACK);
            getCanvas(flag).getGraphicsContext2D().fillRect(0, 0, getCanvas(flag).getWidth(), getCanvas(flag).getHeight());

            //draw drag line
            getCanvas(flag).getGraphicsContext2D().setStroke(Color.RED);
            getCanvas(flag).getGraphicsContext2D().setLineWidth(1);
            getCanvas(flag).getGraphicsContext2D().strokeLine(
                    world.getDragLine()[0],
                    world.getDragLine()[1],
                    world.getDragLine()[2],
                    world.getDragLine()[3]
            );

            //iterate the star list to draw all the exist stars in the universe
            for (Star star : world.getUniverse().getStars()) {
                //only display the star if it is on screen
                //in the camera range
                if (isStarOnScreen(star)) {
                    drawStar(star);
                }
            }

            gameStage.getGameSceneComponents().getChildren().set(0, getCanvas(flag));
            flag = !flag;
        });

    }

    public void drawStar(Star star){
        //calculate the actual display size according to the scale
        starDisplayWidth = (int) ((star.r * 2) / scaleX) + 1;
        starDisplayHeight = (int) ((star.r * 2) / scaleY) + 1;
        getCanvas(flag).getGraphicsContext2D().strokeOval(

                //a little math here, should be reliable after 6 changes
                //also, this is related to the math above when the user
                //new a star, they are reverse functions to each other

                //this is convert the universe coordinates to display coordinates
                ((star.centerX - biasX) / scaleX) - (starDisplayWidth / 2),
                ((star.centerY - biasY) / scaleY) - (starDisplayHeight / 2),
                starDisplayWidth,
                starDisplayHeight
        );
    }

    public boolean isStarOnScreen(Star star) {
        return ! (
                //conditions for out of bound
                ((star.centerX - biasX) / scaleX) - (starDisplayWidth / 2) < 0 - starDisplayWidth
                || ((star.centerX - biasX) / scaleX) - (starDisplayWidth / 2) > SystemStatus.getScreenwidth() + starDisplayWidth
                || ((star.centerY - biasY) / scaleY) - (starDisplayHeight / 2) < 0 - starDisplayWidth
                || ((star.centerY - biasY) / scaleY) - (starDisplayHeight / 2) > SystemStatus.getScreenHeight() + starDisplayWidth
        );
    }

    public void updateBias(){

        biasX = ((world.getCamera().getCenterX() - (world.getUniverse().getWidth() / 2))
                + ((world.getUniverse().getWidth() - world.getCamera().getWidth()) / 2));

        biasY = ((world.getCamera().getCenterY() - (world.getUniverse().getHeight() / 2))
                + ((world.getUniverse().getHeight() - world.getCamera().getHeight()) / 2));

    }

    private GameCanvas getCanvas(boolean flag){
        if (flag){
            return gameStage.getGameScene().screenAlpha;
        }else{
            return gameStage.getGameScene().screenBeta;
        }
    }

    //getters and setters

    public double getScaleX(){
        return scaleX;
    }

    public double getScaleY(){
        return scaleY;
    }

    public void setScaleX(float new_scaleX){
        scaleX = new_scaleX;
    }

    public void setScaleY(float new_scaleY){
        scaleY = new_scaleY;
    }

    //the graphic thread operation function
    @Override
    public void run() {
        //main loop start, record time
        //time = 0
        update=0;//record rendering time
        sleep=0;//record thread sleep time
        while(!isExit()){
            before=System.nanoTime();//get current time (not real time)
            t=sleep+update;//record the time used by last frame

            //send the rendering job to a background thread
            updateFrame();//update frame
            update=(System.nanoTime()-before)/1000000L;//record total rendering time
            sleep=Math.max(2,16-update);
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
