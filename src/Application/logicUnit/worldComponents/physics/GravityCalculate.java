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
                double xDiff = star.centerX - s.centerX;
                double yDiff = star.centerY - s.centerY;
                double distance = Math.sqrt(xDiff * xDiff + yDiff * yDiff);

                if (distance > star.r & distance > s.r) {
                    //normal acceleration
                    s.accelerationX = (0.06673 * star.mass / Math.pow(distance, 2)) * (xDiff / distance);
                    s.accelerationY = (0.06673 * star.mass / Math.pow(distance, 2)) * (yDiff / distance);
                }else{
                    //collide
                    //think carefully before you try to replace these codes with a buffer star
                    double newR = Math.sqrt(star.r * star.r + s.r * s.r);
                    double newMass = s.mass + star.mass;
                    double newAX = ((s.accelerationX * s.mass) + (star.accelerationX * star.mass)) / newMass;
                    double newAY = ((s.accelerationY * s.mass) + (star.accelerationY * star.mass)) / newMass;
                    double newVX = ((s.velocityX * s.mass) + (star.velocityX * star.mass)) / newMass;
                    double newVY = ((s.velocityY * s.mass) + (star.velocityY * star.mass)) / newMass;

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
