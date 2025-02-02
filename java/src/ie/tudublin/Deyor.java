package ie.tudublin;

import ie.tudublin.Visual;
import processing.core.PConstants;

public class Deyor {
    float theta = 0;
    int mode = 1;
    float angle = 0;
    boolean paused = false;
    float smoothedAmplitude = 0;
    float size = 0;
    float c = 0;
    float c2 = 0;
    float speed = 0;

    // Draw cube
    void drawCube(float cubeSpeed, Player p) {
        p.calculateAverageAmplitude();
        p.stroke(Player.map(p.getSmoothedAmplitude(), 0, 0.6f, 0, 255), 255, 255);
        p.strokeWeight(5);

        p.pushMatrix();

        p.camera(0, 0, 0, 0, 0, -1, 0, 1, 0);
        p.translate(0, 0, -200);
        p.rotateX(angle);
        p.rotateZ(angle);

        float boxSize = 20 + (120 * p.getSmoothedAmplitude());
        p.box(boxSize);

        p.popMatrix();

        angle += cubeSpeed * 0.5f;
    }

    // Draw border
    void drawBorder(float smoothedAmplitude, float colour, Player p) {
        float border = Player.map(smoothedAmplitude, 0, 0.15f, 3, 70);
        p.fill(colour, 255, 255, 150);
        p.stroke(colour, 255, 255);
        p.rect(0, 0, p.width, border); // Top
        p.rect(p.width - border, 0, border, p.height); // Right
        p.rect(0, p.height - border, p.width, border); // Bottom
        p.rect(0, 0, border, p.height); // Left
    }

    // Draws pyramid
    void drawPyramid(float t, float colour, Player p) {
        // Drawing the pyramid by drawing 4 triangles
        p.beginShape(PConstants.TRIANGLES);
        p.stroke(colour, 255, 255);

        // Drawing the triangles
        p.fill(colour, 255, 255, 150);
        p.vertex(-t, -t, -t);
        p.vertex(t, -t, -t);
        p.vertex(0, 0, t);

        p.fill(colour, 255, 255, 150);
        p.vertex(t, -t, -t);
        p.vertex(t, t, -t);
        p.vertex(0, 0, t);

        p.fill(colour, 255, 255, 150);
        p.vertex(t, t, -t);
        p.vertex(-t, t, -t);
        p.vertex(0, 0, t);

        p.fill(colour, 255, 255, 150);
        p.vertex(-t, t, -t);
        p.vertex(-t, -t, -t);
        p.vertex(0, 0, t);

        p.endShape();
    }

    // Render method that renders all the shapes
    public void render(Player p) {
        p.colorMode(Visual.HSB);
        p.background(0);
        smoothedAmplitude = p.getSmoothedAmplitude() / 8;

        // Changing size and colour with amplitude
        size = Player.map(smoothedAmplitude, 0, 0.1f, 10, 40);
        c = Player.map(smoothedAmplitude, 0, 0.055f, 0, 255);
        c2 = Player.map(smoothedAmplitude, 0.08f, 0, 0, 255);

        // Changing speed with amplitude
        speed = smoothedAmplitude * 1.6f;
        theta += speed;

        // Drawing border
        drawBorder(smoothedAmplitude, c2, p);

        // Making the pyramid rotate
        p.translate(p.width / 2, p.height / 2, 0);
        p.rotateX(theta);
        p.rotateY(theta);

        // Drawing center pyra1mid
        drawPyramid(size * 2.5f, c2, p);

        // Drawing sphere to rotate around the cube
        p.pushMatrix();
        p.translate(-p.width / 4, p.height / 4);
        p.sphere(size * 2.5f);
        p.popMatrix();

        // Second sphere
        p.pushMatrix();
        p.translate(p.width / 4, -p.height / 4);
        p.sphere(size * 2.5f);
        p.popMatrix();

        // Drawing the cube
        drawCube(speed, p);
    }
}