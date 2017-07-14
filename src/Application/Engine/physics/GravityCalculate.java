package Application.Engine.physics;

import models.PhysicsComponents.Star;

/**
 * Created by lzx on 2017/4/13.
 * this class is used to perform collisions and gravities between stars
 */
public class GravityCalculate {
    private Star[] stars;

    public GravityCalculate(Star[] stars) {
        synchronize(stars);
    }

    public void synchronize(Star[] stars) {
        this.stars = stars;
    }

    public void gravityAcceleration(Star s) {
        s.accelerationX = s.accelerationY = 0;
        for (Star star : stars) {
            if (star.inUniverse & star != s) {
                double xDiff = star.centerX - s.centerX;
                double yDiff = star.centerY - s.centerY;
                double distance = Math.sqrt(xDiff * xDiff + yDiff * yDiff);

                if (distance > star.r + s.r & s.mass != 0) {
                    s.accelerationX = (0.06673 * star.mass / Math.pow(distance, 2)) * (xDiff / distance);
                    s.accelerationY = (0.06673 * star.mass / Math.pow(distance, 2)) * (yDiff / distance);
                }

                if (distance <= star.r + s.r) {

                    //think carefully before you try to replace these codes with a buffer star
                    double newR = Math.sqrt(star.r * star.r + s.r * s.r);
                    double newMass = s.mass + star.mass;
                    double newAX = ((s.accelerationX * s.mass) + (star.accelerationX * star.mass)) / newMass;
                    double newAY = ((s.accelerationY * s.mass) + (star.accelerationY * star.mass)) / newMass;
                    double newVX = ((s.vectorX * s.mass) + (star.vectorX * star.mass)) / newMass;
                    double newVY = ((s.vectorY * s.mass) + (star.vectorY * star.mass)) / newMass;

                    if (s.r >= star.r) {
                        star.add(s.centerX, s.centerY);
                    } else {
                        star.add(star.centerX, star.centerY);
                    }
                    star.r = newR;
                    star.accelerationX = newAX;
                    star.accelerationY = newAY;
                    star.vectorX = newVX;
                    star.vectorY = newVY;
                    star.mass = newMass;
                    s.remove();
                    return;
                }

            }
        }
    }
}
