package Application.logicUnit.worldComponents.physics;

import Application.logicUnit.worldComponents.physics.physicsComponents.Universe;
import Application.logicUnit.worldComponents.physics.physicsComponents.universeComponents.Star;

/**
 * Created by lzx on 2017/4/13.
 * this class is used to perform collisions and gravities between stars
 */
class Gravity {

    private static Star[] stars;
    private static float xDiff;
    private static float yDiff;
    private static float distance;
    private static float newMass;

    private static float scale = 100f;

    Gravity(Star[] stars) {
        Gravity.stars = stars;
    }

    static void synchronize(Universe universe) {
        stars = universe.getStars();
    }

    static void step(Star s) {
        s.accelerationX = s.accelerationY = 0;
        for (Star star : stars) {
            if (star.inUniverse & star != s) {
                xDiff = star.centerX - s.centerX;
                yDiff = star.centerY - s.centerY;
                distance = (float) Math.sqrt(xDiff * xDiff + yDiff * yDiff);

                if (distance > star.r * 2 / 3 & distance > s.r * 2 / 3) {
                    //normal acceleration
                    s.accelerationX += (6.673f * star.mass / (distance * distance)) * (xDiff / distance) * scale;
                    s.accelerationY += (6.673f * star.mass / (distance * distance)) * (yDiff / distance) * scale;
                }else{
                    //collide
                    //think carefully before you try to replace these codes with a buffer star
                    newMass = s.mass + star.mass;
                    if (s.r >= star.r) {
                        star.putIn(s.centerX, s.centerY);
                    } else {
                        star.putIn(star.centerX, star.centerY);
                    }
                    star.r = (float) Math.sqrt(star.r * star.r + s.r * s.r);
                    star.accelerationX = ((s.accelerationX * s.mass) + (star.accelerationX * star.mass)) / newMass;
                    star.accelerationY = ((s.accelerationY * s.mass) + (star.accelerationY * star.mass)) / newMass;
                    star.velocityX = ((s.velocityX * s.mass) + (star.velocityX * star.mass)) / newMass;
                    star.velocityY = ((s.velocityY * s.mass) + (star.velocityY * star.mass)) / newMass;
                    star.mass = newMass;
                    star.onScreen = true;
                    star.inUniverse = true;
                    s.remove();

                    return;
                }

            }
        }
    }
}
