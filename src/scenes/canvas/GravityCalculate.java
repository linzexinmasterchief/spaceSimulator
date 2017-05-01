package scenes.canvas;

import models.Star;

/**
 * Created by lzx on 2017/4/13.
 * this class is used to perform collisions and gravities between stars
 */
class GravityCalculate {
    private static Star[] stars;

    GravityCalculate(Star[] stars) {
        synchronize(stars);
    }

    void synchronize(Star[] stars) {
        GravityCalculate.stars = stars;
    }

    void gravityAcceleration(Star s) {
        s.accelerationX = s.accelerationY = 0;
        for (Star star : stars) {
            if (star.onScreen & star != s) {
                double xDiff = (star.x + star.r) - (s.x + s.r);
                double yDiff = (star.y + star.r) - (s.y + s.r);
                double distance = Math.sqrt(xDiff * xDiff + yDiff * yDiff);

                if (distance > star.r + s.r & s.mass != 0) {
                    s.accelerationX += 0.6673 * star.mass * xDiff / Math.pow(distance, 3);
                    s.accelerationY += 0.6673 * star.mass * yDiff / Math.pow(distance, 3);
                }

                if (distance <= star.r + s.r) {

                    double newR = Math.sqrt(star.r * star.r + s.r * s.r);
                    int newMass = s.mass + star.mass;
                    double newAX = ((s.accelerationX * s.mass) + (star.accelerationX * star.mass)) / newMass;
                    double newAY = ((s.accelerationY * s.mass) + (star.accelerationY * star.mass)) / newMass;
                    double newVX = ((s.speedX * s.mass) + (star.speedX * star.mass)) / newMass;
                    double newVY = ((s.speedY * s.mass) + (star.speedY * star.mass)) / newMass;

                    if (s.r >= star.r) {
                        star.show(s.x, s.y);
                    } else {
                        star.show(star.x, star.y);
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
