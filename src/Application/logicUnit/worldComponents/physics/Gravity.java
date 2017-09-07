package Application.logicUnit.worldComponents.physics;

import Application.logicUnit.World;
import Application.logicUnit.worldComponents.physics.physicsComponents.Universe;
import Application.logicUnit.worldComponents.physics.physicsComponents.universeComponents.Star;

/**
 * Created by lzx on 2017/4/13.
 * this class is used to perform collisions and gravities between stars
 */
class Gravity {

    private static float xDiff;
    private static float yDiff;
    private static float distance;
    private static float newMass;

    private static float scale = 100f;

    Gravity() {
    }

    static void step(Star[] list,Star s) {
        s.accelerationX = s.accelerationY = 0;
        for (Star star : list) {
            if (star.inUniverse & star != s) {
                xDiff = star.centerX - s.centerX;
                yDiff = star.centerY - s.centerY;
                distance = (float) Math.sqrt(xDiff * xDiff + yDiff * yDiff);

                if ((int) distance > (int) star.r * 2 / 3 & (int) distance > (int) s.r * 2 / 3) {
                    //normal acceleration 6.673
                    s.accelerationX += (6 * star.mass / (distance * distance)) * (xDiff / distance) * scale;
                    s.accelerationY += (6 * star.mass / (distance * distance)) * (yDiff / distance) * scale;
                }else{
                    collide(star,s);
                    return;
                }

            }
        }
    }

    private static void collide(Star s, Star star){
        //collide
        //think carefully before you try to replace these codes with a buffer star
        newMass = s.mass + star.mass;
        if (s.r >= star.r) {
            s.putIn(s.centerX, s.centerY);
        } else {
            s.putIn(star.centerX, star.centerY);
        }
        s.r = (float) Math.sqrt(star.r * star.r + s.r * s.r);
        s.accelerationX = ((s.accelerationX * s.mass) + (star.accelerationX * star.mass)) / newMass;
        s.accelerationY = ((s.accelerationY * s.mass) + (star.accelerationY * star.mass)) / newMass;
        s.velocityX = ((s.velocityX * s.mass) + (star.velocityX * star.mass)) / newMass;
        s.velocityY = ((s.velocityY * s.mass) + (star.velocityY * star.mass)) / newMass;
        s.mass = newMass;
        s.onScreen = true;
        s.inUniverse = true;
        star.remove();

    }
}
