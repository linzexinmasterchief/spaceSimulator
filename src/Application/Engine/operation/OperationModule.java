package Application.Engine.operation;

import Application.Engine.Engine;
import Application.Engine.settings.Speed;
import Application.stages.MainStage.gameScene.GameScene;
import javafx.application.Platform;
import models.PhysicsComponents.Star;
import models.SystemComponents.ThreadModule;

/**
 * Created by lzx on 2017/7/13.
 *
 */
public class OperationModule extends ThreadModule implements Runnable{

    private GameScene gameScene;

    public OperationModule(Engine root_engine){
        super(root_engine);
    }

    @Override
    public void initialize(){
        //override default initialize block
        gameScene = engine.getLauncher().getGameStage().getGameScene();
    }

    //determine if a new star should be created
    //left click to new a star
    private void addNewStar(){
        //open the new star lock
        systemStatus.setNewStarExist(true);

        //give the buffer star speed based on the distance mouse dragged
        engine.getBufferStar().velocityX = (systemStatus.getDragLine()[2] - systemStatus.getDragLine()[0]) / Speed.getDragSpeedConstant();
        engine.getBufferStar().velocityY = (systemStatus.getDragLine()[3] - systemStatus.getDragLine()[1]) / Speed.getDragSpeedConstant();

        //check if the new star lock is opened to avoid unnecessary star list iterations
        //check if there is empty star slot for a new star
        if (systemStatus.isNewStarExist()) {
            for (int i = 0; i < engine.getUniverse().getStars().length; i++) {
                //not on screen means it is safe to clear
                if (systemStatus.isNewStarExist() & !engine.getUniverse().getStars()[i].inUniverse) {

                    //prepare the empty slot for new star
                    engine.getUniverse().getStars()[i].remove();
                    engine.getUniverse().getStars()[i].initialize();

                    //give buffer star properties
                    engine.getBufferStar().mass = gameScene.getMass();
                    engine.getBufferStar().r = gameScene.getRadius();

                    //give the properties of buffer star to the empty star slot
                    engine.getUniverse().getStars()[i] = new Star(engine.getBufferStar());
                    //remove the buffer star (clear the values to default)
                    engine.getBufferStar().remove();

                    //add the star according to the size of window(camera)
                    //and the enlarge scales
                    engine.getUniverse().getStars()[i].add(
                            //convert the coordinate on screen to coordinate in the universe
                            //it's hard to explain the math, but it will be easy to understand
                            //once you draw it out on the paper, be careful changing it anyway
                            (engine.getCamera().getCenterX() - engine.getUniverse().getWidth() / 2)
                                    + (engine.getUniverse().getWidth() - engine.getCamera().getWidth()) / 2
                                    + systemStatus.getMouse_coordinate()[0] * engine.getGraphicsModule().getScaleX(),
                            (engine.getCamera().getCenterY() - engine.getUniverse().getHeight() / 2)
                                    + (engine.getUniverse().getHeight() - engine.getCamera().getHeight()) / 2
                                    + systemStatus.getMouse_coordinate()[1] * engine.getGraphicsModule().getScaleY()
                    );

                    //change the slot property from empty to full
                    engine.getUniverse().getStars()[i].inUniverse = true;

                    //close the new star lock
                    systemStatus.setNewStarExist(false);

                    //refresh the screen
                    engine.getGraphicsModule().drawShapes();
                }
            }
        }
        systemStatus.setMouseReleased(false);
    }


    @Override
    public void run() {
        while (!isExit()) {

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (systemStatus.isMousePressed()) {
                systemStatus.setMouseReleased(false);
                switch (systemStatus.getActivatedMouseButton()) {
                    case PRIMARY:
                        double dragline[] = systemStatus.getDragLine();
                        dragline[0] = systemStatus.getMouse_coordinate()[0];
                        dragline[1] = systemStatus.getMouse_coordinate()[1];
                        systemStatus.setDragLine(dragline);
                        break;
                }
                Platform.runLater(() -> gameScene.getStatusBar().update());
            }

            if (systemStatus.isMouseReleased()) {
                systemStatus.setMousePressed(false);
                switch (systemStatus.getActivatedMouseButton()) {
                    case PRIMARY:
                        double dragline[] = systemStatus.getDragLine();
                        dragline[2] = systemStatus.getMouse_coordinate()[0];
                        dragline[3] = systemStatus.getMouse_coordinate()[1];
                        systemStatus.setDragLine(dragline);
                        addNewStar();
                        break;
                    case SECONDARY:
                        engine.getPhysicsModule().clear();
                        break;
                    case MIDDLE:
                        //change pause value if middle button pressed
                        engine.getPhysicsModule().setPause(!engine.getPhysicsModule().isPause());
                        break;
                }
            }

            if (systemStatus.isMouseScrolled()) {
                systemStatus.setMouseReleased(false);
                systemStatus.setMousePressed(false);
                //on mouse wheel rolling back (minimize)
                if (systemStatus.getMouseScrollValue() < 0) {

                    //change the size of the camera (+)
                    engine.getCamera().setWidth(engine.getCamera().getWidth() + Speed.getSizeChangeSpeed());
                    engine.getCamera().setHeight(engine.getCamera().getHeight() + Speed.getSizeChangeSpeed() * systemStatus.getHeightWidthScale());

                    //move the camera to the mouse coordinate to create an effect
                    engine.getCamera().setCenterX(engine.getCamera().getCenterX() - (systemStatus.getMouse_coordinate()[0] - systemStatus.getCanvasWidth() / 2) / systemStatus.getCanvasWidth() * Speed.getCameraMoveSpeed());
                    engine.getCamera().setCenterY(engine.getCamera().getCenterY() - (systemStatus.getMouse_coordinate()[1] - systemStatus.getCanvasHeight() / 2) / systemStatus.getCanvasHeight() * Speed.getCameraMoveSpeed());
                } else if (systemStatus.getMouseScrollValue() > 0) {
                    //on mouse wheel rolling back (enlarge)
                    engine.getCamera().setWidth(engine.getCamera().getWidth() - Speed.getSizeChangeSpeed());
                    engine.getCamera().setHeight(engine.getCamera().getHeight() - Speed.getSizeChangeSpeed() * systemStatus.getHeightWidthScale());

                    //move the camera to the mouse coordinate to create an effect
                    engine.getCamera().setCenterX(engine.getCamera().getCenterX() + (systemStatus.getMouse_coordinate()[0] - systemStatus.getCanvasWidth() / 2) / systemStatus.getCanvasWidth() * Speed.getCameraMoveSpeed());
                    engine.getCamera().setCenterY(engine.getCamera().getCenterY() + (systemStatus.getMouse_coordinate()[1] - systemStatus.getCanvasHeight() / 2) / systemStatus.getCanvasHeight() * Speed.getCameraMoveSpeed());
                }

                //calculate the scale between camera and original camera
                engine.getGraphicsModule().setScaleX(engine.getCamera().getWidth() / engine.getCamera().getOriginalWidth());
                engine.getGraphicsModule().setScaleY(engine.getCamera().getHeight() / engine.getCamera().getOriginalHeight());

                //refresh the screen
                engine.getGraphicsModule().drawShapes();
            }

            systemStatus.setMouseReleased(false);
            systemStatus.setMousePressed(false);
            systemStatus.setMouseScrolled(false);
        }
    }
}
