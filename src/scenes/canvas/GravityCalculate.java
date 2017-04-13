package scenes.canvas;

import models.Star;

/**
 * Created by lzx on 2017/4/13.
 */
public class GravityCalculate {
    public static Star[] stars;

    public GravityCalculate(Star[] stars) {
        synchronize(stars);
    }

    public void synchronize(Star[] stars) {
        GravityCalculate.stars = stars;
    }

    public void gravityAcceleration(Star s) {
        s.accelerationX = s.accelerationY = 0;
        for (int i = 0; i < stars.length; i++) {
            if (stars[i].onScreen & stars[i] != s) {
                double xDiff = (stars[i].x + stars[i].r) - (s.x + s.r);
                double yDiff = (stars[i].y + stars[i].r) - (s.y + s.r);
                double distance = Math.sqrt(xDiff * xDiff + yDiff * yDiff);

                if (distance > stars[i].r + s.r & s.mass != 0) {
                    s.accelerationX += 0.6673 * stars[i].mass * xDiff / Math.pow(distance, 3);
                    s.accelerationY += 0.6673 * stars[i].mass * yDiff / Math.pow(distance, 3);
                }

                if (distance <= stars[i].r + s.r) {

                    double newR = Math.sqrt(stars[i].r * stars[i].r + s.r * s.r);
                    int newMass = s.mass + stars[i].mass;
                    double newAX = ((s.accelerationX * s.mass) + (stars[i].accelerationX * stars[i].mass)) / newMass;
                    double newAY = ((s.accelerationY * s.mass) + (stars[i].accelerationY * stars[i].mass)) / newMass;
                    double newVX = ((s.speedX * s.mass) + (stars[i].speedX * stars[i].mass)) / newMass;
                    double newVY = ((s.speedY * s.mass) + (stars[i].speedY * stars[i].mass)) / newMass;

                    System.out.println(s.speedX + " " + stars[i].speedX);
                    if (s.r >= stars[i].r) {
                        stars[i].show(s.x, s.y);
                    } else {
                        stars[i].show(stars[i].x, stars[i].y);
                    }
                    stars[i].r = newR;
                    stars[i].accelerationX = newAX;
                    stars[i].accelerationY = newAY;
                    stars[i].speedX = newVX;
                    stars[i].speedY = newVY;
                    stars[i].mass = newMass;
                    s.remove();
                    return;
                }

            }
        }
        return;
    }


}
