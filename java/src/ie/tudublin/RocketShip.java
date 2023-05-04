package ie.tudublin;

import ie.tudublin.Visual;
import processing.core.PApplet;

public class RocketShip extends PApplet {

    // Set up the initial position of the rocket
    static float rocketX = 200;
    static float rocketY = 500;
    static float saucerX = 75;
    static float saucerY = 75;
    static float flameSize = 30;
    static float trailSize = 60;
    static float[] trailX = new float[10];
    static float[] trailY = new float[10];
    float[] lerpBuff;

    // Constructor
    public RocketShip(int width) {
        this.width = width;
        lerpBuff = new float[width];
    }

    public static void render(Player p) {

        // Draw stars in the background
        p.background(0);
        p.stroke(255);
        p.strokeWeight(2);

        // Translate to the center of the screen
        p.translate(p.width / 2, p.height / 2);

        // Calculate the angle of rotation based on mouse position
        float rotationAngle = map(p.mouseX, 0, p.width, -PI / 4, PI / 4);

        // Rotate the canvas
        p.rotate(rotationAngle);

        // Draw the stars as if they are moving past the viewer's perspective
        for (int i = 0; i < 100; i++) {
            float x = p.random(-p.width / 2, p.width / 2);
            float y = p.random(-p.height / 2, p.height / 2);
            p.point(x, y);
        }

        // Draw the saucer trail
        p.noStroke();
        p.fill(255, 255, 255, 50);
        for (int i = 0; i < trailX.length; i++) {
            p.ellipse(trailX[i], trailY[i], trailSize, trailSize / 2);
            trailX[i] += p.random(-2, 2);
            trailY[i] += p.random(-2, 2);
            trailSize -= 3;
            if (trailSize < 0) {
                trailSize = 60;
                trailX[i] = saucerX + p.random(-10, 10);
                trailY[i] = saucerY + p.random(-10, 10);
            }
        }

        // Draw the saucer body
        p.noStroke();
        p.fill(80, 200, 80);
        p.ellipseMode(CENTER);
        p.ellipse(saucerX, saucerY, 150, 50);
        p.fill(0, 200, 0);
        p.ellipse(saucerX, saucerY, 120, 40);
        p.fill(80, 255, 80);
        p.ellipse(saucerX, saucerY, 100, 30);

        // Add some details to the saucer body
        p.fill(200, 0, 0);
        p.ellipse(saucerX - 45, saucerY, 20, 20);
        p.ellipse(saucerX + 45, saucerY, 20, 20);
        p.fill(255, 0, 0);
        p.ellipse(saucerX - 45, saucerY, 10, 10);
        p.ellipse(saucerX + 45, saucerY, 10, 10);
        p.fill(255, 255, 0);
        p.ellipse(saucerX, saucerY - 10, 20, 20);
        p.fill(255);
        p.ellipse(saucerX, saucerY - 10, 10, 10);

        // Draw the saucer flames
        p.fill(255, 150, 0, 200);
        p.ellipse(saucerX - 30, saucerY + 25, flameSize, flameSize * 2);
        p.ellipse(saucerX + 30, saucerY + 25, flameSize, flameSize * 2);
        flameSize += p.random(-5, 5);
        if (flameSize < 30 || flameSize > 40) {
            flameSize = 35;
        }

        // Move the saucer randomly
        saucerX += p.random(-5, 5);
        saucerY += p.random(-5, 5);
    }
}
