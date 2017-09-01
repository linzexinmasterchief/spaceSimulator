package Application.logicUnit.worldComponents.physics;

import Application.logicUnit.worldComponents.physics.physicsComponents.Universe;
import Application.logicUnit.worldComponents.physics.physicsComponents.universeComponents.Star;

/**
 * Created by lzx on 2017/4/13.
 * this class is used to perform collisions and gravities between stars
 */
public class GravityCalculate {

    private Star[] stars;

    public GravityCalculate(Star[] stars) {
        this.stars = stars;
    }

    public void synchronize(Universe universe) {
        stars = universe.getStars();
    }

    public void fire(Star s) {
        s.accelerationX = s.accelerationY = 0;
        for (Star star : stars) {
            if (star.inUniverse & star != s) {
                float xDiff = star.centerX - s.centerX;
                float yDiff = star.centerY - s.centerY;
                float distance = (float) Math.sqrt(xDiff * xDiff + yDiff * yDiff);

                if (distance > star.r & distance > s.r) {
                    //normal acceleration
                    s.accelerationX = (float) ((0.6673 * star.mass / Math.pow(distance, 2)) * (xDiff / distance));
                    s.accelerationY = (float) ((0.6673 * star.mass / Math.pow(distance, 2)) * (yDiff / distance));
                }else{
                    //collide
                    //think carefully before you try to replace these codes with a buffer star
                    float newR = (float) Math.sqrt(star.r * star.r + s.r * s.r);
                    float newMass = s.mass + star.mass;
                    float newAX = ((s.accelerationX * s.mass) + (star.accelerationX * star.mass)) / newMass;
                    float newAY = ((s.accelerationY * s.mass) + (star.accelerationY * star.mass)) / newMass;
                    float newVX = ((s.velocityX * s.mass) + (star.velocityX * star.mass)) / newMass;
                    float newVY = ((s.velocityY * s.mass) + (star.velocityY * star.mass)) / newMass;

                    if (s.r >= star.r) {
                        star.add(s.centerX, s.centerY);
                    } else {
                        star.add(star.centerX, star.centerY);
                    }
                    star.r = newR;
                    star.accelerationX = newAX;
                    star.accelerationY = newAY;
                    star.velocityX = newVX;
                    star.velocityY = newVY;
                    star.mass = newMass;
                    s.remove();

                    return;
                }

            }
        }
    }
}
