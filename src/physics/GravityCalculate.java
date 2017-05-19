package physics;

import models.Star;

/**
 * Created by lzx on 2017/4/13.
 * this class is used to perform collisions and gravities between stars
 */
public class GravityCalculate {
    private static Star[] stars;

    public GravityCalculate(Star[] stars) {
        synchronize(stars);
    }

    public void synchronize(Star[] stars) {
        GravityCalculate.stars = stars;
    }

    public void gravityAcceleration(Star s) {
        s.accelerationX = s.accelerationY = 0;
        for (Star star : stars) {
            if (star.onScreen & star != s) {
                double xDiff = star.centerX - s.centerX;
                double yDiff = star.centerY - s.centerY;
                double distance = Math.sqrt(xDiff * xDiff + yDiff * yDiff);

                if (distance > star.r + s.r & s.mass != 0) {
                    s.accelerationX = (6.673 * star.mass / Math.pow(distance, 2)) * (xDiff / distance);
                    s.accelerationY = (6.673 * star.mass / Math.pow(distance, 2)) * (yDiff / distance);
                }

                if (distance <= star.r + s.r) {

                    double newR = Math.sqrt(star.r * star.r + s.r * s.r);
                    int newMass = s.mass + star.mass;
                    double newAX = ((s.accelerationX * s.mass) + (star.accelerationX * star.mass)) / newMass;
                    double newAY = ((s.accelerationY * s.mass) + (star.accelerationY * star.mass)) / newMass;
                    double newVX = ((s.speedX * s.mass) + (star.speedX * star.mass)) / newMass;
                    double newVY = ((s.speedY * s.mass) + (star.speedY * star.mass)) / newMass;

                    if (s.r >= star.r) {
                        star.show(s.centerX, s.centerY);
                    } else {
                        star.show(star.centerX, star.centerY);
                    }
                    star.r = newR;
                    star.accelerationX = newAX;
                    star.accelerationY = newAY;
                    star.speedX = newVX;
                    star.speedY = newVY;
                    star.mass = newMass;
                    s.remove();
                    return;
                }

            }
        }
    }
}
