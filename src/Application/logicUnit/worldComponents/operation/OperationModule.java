package Application.logicUnit.worldComponents.operation;

import Application.logicUnit.World;
import Application.logicUnit.worldComponents.worldSettings.Speed;
import Application.graphicUnit.mainStageComponents.GameScene;
import Application.status.CanvasStatus;
import javafx.application.Platform;
import Application.logicUnit.worldComponents.physics.physicsComponents.universeComponents.Star;
import models.systemComponentModels.ThreadModuleModel;

/**
 * Created by lzx on 2017/7/13.
 *
 */
public class OperationModule extends ThreadModuleModel {

    private GameScene gameScene;
    private CanvasStatus canvasStatus;

    public OperationModule(World root_world){
        super(root_world);
    }

    @Override
    public void initialize(){
        //override default initialize block
        gameScene = World.getLauncher().getGameStage().getGameScene();

        canvasStatus = World.getLauncher().getCanvasStatus();
    }

    //determine if a new star should be created
    //left click to new a star
    private void addNewStar(){
        //open the new star lock
        systemStatus.setNewStarExist(true);

        //give the buffer star speed based on the distance mouse dragged
        World.getBufferStar().velocityX = (systemStatus.getDragLine()[2]
                - systemStatus.getDragLine()[0])
                / Speed.getDragSpeedConstant();
        World.getBufferStar().velocityY = (systemStatus.getDragLine()[3]
                - systemStatus.getDragLine()[1])
                / Speed.getDragSpeedConstant();

        //check if the new star lock is opened to avoid unnecessary star list iterations
        //check if there is empty star slot for a new star
        if (systemStatus.isNewStarExist()) {
            for (int i = 0; i < World.getUniverse().getStars().length; i++) {
                //not on screen means it is safe to clear
                if (systemStatus.isNewStarExist() & !World.getUniverse().getStars()[i].inUniverse) {

                    //prepare the empty slot for new star
                    World.getUniverse().getStars()[i].remove();
                    World.getUniverse().getStars()[i].initialize();

                    //give buffer star properties
                    World.getBufferStar().mass = gameScene.getMass();
                    World.getBufferStar().r = gameScene.getRadius();

                    //give the properties of buffer star to the empty star slot
                    World.getUniverse().getStars()[i] = new Star(World.getBufferStar());
                    //remove the buffer star (clear the values to default)
                    World.getBufferStar().remove();

                    //add the star according to the size of window(camera)
                    //and the enlarge scales
                    World.getUniverse().getStars()[i].add(
                            //convert the coordinate on screen to coordinate in the universe
                            //it's hard to explain the math, but it will be easy to understand
                            //once you draw it out on the paper, be careful changing it anyway
                            (World.getCamera().getCenterX() - World.getUniverse().getWidth() / 2)
                                    + (World.getUniverse().getWidth() - World.getCamera().getWidth()) / 2
                                    + systemStatus.getMouse_coordinate()[0] * World.getGraphicsModule().getScaleX(),
                            (World.getCamera().getCenterY() - World.getUniverse().getHeight() / 2)
                                    + (World.getUniverse().getHeight() - World.getCamera().getHeight()) / 2
                                    + systemStatus.getMouse_coordinate()[1] * World.getGraphicsModule().getScaleY()
                    );

                    //change the slot property from empty to full
                    World.getUniverse().getStars()[i].inUniverse = true;

                    //close the new star lock
                    systemStatus.setNewStarExist(false);

                    //refresh the screen
                    World.getGraphicsModule().drawShapes();
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
                        World.getPhysicsModule().clear();
                        break;
                    case MIDDLE:
                        //change pause value if middle button pressed
                        World.getPhysicsModule().setPause(!World.getPhysicsModule().isPause());
                        break;
                }
            }

            if (systemStatus.isMouseScrolled()) {
                systemStatus.setMouseReleased(false);
                systemStatus.setMousePressed(false);
                //on mouse wheel rolling back (minimize)
                if (systemStatus.getMouseScrollValue() < 0) {

                    //change the size of the camera (+)
                    World.getCamera().setWidth(World.getCamera().getWidth() + Speed.getSizeChangeSpeed());
                    World.getCamera().setHeight(World.getCamera().getHeight()
                            + Speed.getSizeChangeSpeed() * systemStatus.getHeightWidthScale());

                    //move the camera to the mouse coordinate to create an effect
                    World.getCamera().setCenterX(World.getCamera().getCenterX()
                                    - (systemStatus.getMouse_coordinate()[0] - canvasStatus.getCanvasWidth() / 2)
                                    / canvasStatus.getCanvasWidth() * Speed.getCameraMoveSpeed()
                    );
                    World.getCamera().setCenterY(World.getCamera().getCenterY()
                            - (systemStatus.getMouse_coordinate()[1] - canvasStatus.getCanvasHeight() / 2)
                            / canvasStatus.getCanvasHeight() * Speed.getCameraMoveSpeed()
                    );
                } else if (systemStatus.getMouseScrollValue() > 0) {
                    //on mouse wheel rolling back (enlarge)
                    World.getCamera().setWidth(World.getCamera().getWidth() - Speed.getSizeChangeSpeed());
                    World.getCamera().setHeight(World.getCamera().getHeight()
                            - Speed.getSizeChangeSpeed() * systemStatus.getHeightWidthScale()
                    );

                    //move the camera to the mouse coordinate to create an effect
                    World.getCamera().setCenterX(World.getCamera().getCenterX()
                            + (systemStatus.getMouse_coordinate()[0] - canvasStatus.getCanvasWidth() / 2)
                            / canvasStatus.getCanvasWidth() * Speed.getCameraMoveSpeed());
                    World.getCamera().setCenterY(World.getCamera().getCenterY()
                            + (systemStatus.getMouse_coordinate()[1] - canvasStatus.getCanvasHeight() / 2)
                            / canvasStatus.getCanvasHeight() * Speed.getCameraMoveSpeed());
                }

                //calculate the scale between camera and original camera
                World.getGraphicsModule().setScaleX(World.getCamera().getWidth() / World.getCamera().getOriginalWidth());
                World.getGraphicsModule().setScaleY(World.getCamera().getHeight() / World.getCamera().getOriginalHeight());

                //refresh the screen
                World.getGraphicsModule().drawShapes();
            }

            systemStatus.setMouseReleased(false);
            systemStatus.setMousePressed(false);
            systemStatus.setMouseScrolled(false);

            gameScene.getCreateStarMenu().getSettingBtn().setVisible(systemStatus.isCreateStarMenuOut());
            gameScene.getCreateStarMenu().getMassSlider().setVisible(systemStatus.isCreateStarMenuOut());
            gameScene.getCreateStarMenu().getRaiusSlider().setVisible(systemStatus.isCreateStarMenuOut());

            if (systemStatus.isSettingStageOut()) {
                Platform.runLater(() -> World.getLauncher().getSettingStage().show());
            }else {
                Platform.runLater(() -> World.getLauncher().getSettingStage().hide());
            }

            Platform.runLater(() -> gameScene.getStatusBar().update());
        }

    }
}
