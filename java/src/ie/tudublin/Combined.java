package ie.tudublin;

import ie.tudublin.Visual;
import java.util.ArrayList;

public class Combined {
    float[] lerpBuff;
    float width;
    float smoothedAmplitude;
    float swing;
    float swing2;
    float color, x, z, spinning, size;
    float smallPyramidDist = 1.8f;
    ArrayList<Circle> randCircles;

    // Constructor
    public Combined(int width) {
        this.width = width;
        lerpBuff = new float[width];
    }

    // Method to draw the circle visualiser
    public void drawCircleVisualiser(Player p, float rotateX, float rotateY, float rotateZ, float size, float react,
            float thickness) {
        // Convert the rotation values to radians
        rotateX = Visual.radians(rotateX);
        rotateY = Visual.radians(rotateY);
        rotateZ = Visual.radians(rotateZ);

        // Translate to the center of the screen
        p.pushMatrix();
        p.translate(p.width / 2, p.height / 2, 0);
        p.calculateAverageAmplitude();

        // Get the smoothed amplitude
        smoothedAmplitude = p.getSmoothedAmplitude();
        swing = Visual.lerp(swing, Visual.map(p.random(0f, 1.0f), 0, 1, -2, 2), 0.01f);

        // Rotate the visualiser
        p.rotateZ(swing + rotateZ);
        p.rotateX(swing / 2 + rotateX);
        p.rotateY(swing2 + rotateY);
        swing2 += smoothedAmplitude * 0.025f;
        p.beginShape();
        int i = 0;
        p.noFill();

        // Loop through the audio buffer
        for (float deg = 0; deg <= 360; deg += 20) {
            // Map the degree to the size of the audio buffer
            i = Visual.floor(Visual.map(deg, 0, 360, 0, p.getAudioBuffer().size() - 1));
            color = Visual.map(i, 0, p.getAudioBuffer().size() - 1, 0, 255);
            p.stroke(color, 200, 200);
            p.strokeWeight(thickness);
            x = (smoothedAmplitude * size) * Visual.sin(Visual.radians(deg));
            z = (smoothedAmplitude * size) * Visual.cos(Visual.radians(deg));

            // If the degree is 0 or 360, draw a vertex at the center of the screen
            if (deg == 360 || deg == 0) {
                p.curveVertex(x, lerpBuff[0] * (100 * react) * 1.5f, z);
                p.curveVertex(x, lerpBuff[0] * (100 * react) * 1.5f, z);
            } else {
                p.curveVertex(x, lerpBuff[i] * (100 * react) * 2.0f, z);
            }
        }
        p.endShape();
        p.popMatrix();
    }

    public void generateCircles(Player p, int amount) {
        this.randCircles = new ArrayList<Circle>();
        for (int i = 0; i < amount; i++) {
            float cx = p.random(-p.width / 2, p.width / 2);
            float cy = p.random(-p.height / 2, p.height / 2);
            float cz = p.random(-p.height / 2, p.height / 2);
            Circle c = new Circle(cx, cy, cz);
            randCircles.add(c);
        }

    }

    public void randomiseCircles(Player p) {
        for (Circle c : randCircles) {
            float cx = p.random(-p.width / 2, p.width / 2);
            float cy = p.random(-p.height / 2, p.height / 2);
            float cz = p.random(-p.height / 2, p.height / 2);
            c.cx = cx;
            c.cy = cy;
            c.cz = cz;
        }

    }

    public void render(Player p) {
        if (randCircles == null) {
            generateCircles(p, 15);
        }

        p.colorMode(Visual.HSB);
        p.background(0);
        for (int i = 0; i < p.getAudioBuffer().size(); i++) {
            lerpBuff[i] = Visual.lerp(lerpBuff[i], p.getAudioBuffer().get(i), 0.025f);
        }

        // Circle
        p.strokeWeight(1.0f);
        for (int i = 0; i < p.getAudioBuffer().size(); i++) {
            float c = Visual.map(i, 0, p.getAudioBuffer().size(), 0, 255);
            float c2 = Visual.map(i, 0, p.getAudioBuffer().size(), 255, 0);
            p.noFill();
            p.stroke(c2, 255, 255);
            float fcircle = lerpBuff[i] * (p.height / 2) * 4.0f;
            p.circle(p.width / 2, (p.height / 2), p.width * 0.01f + fcircle / 2);
            p.stroke(c, 255, 255);
            p.circle(p.width / 2, (p.height / 2), p.width * 0.03f + fcircle / 2);
        }

        // Circle Visualiser
        drawCircleVisualiser(p, smoothedAmplitude * 10, spinning, spinning, 580, 4f, 5);

        // Border and Pyramids
        p.strokeWeight(5);
        color = Player.map(smoothedAmplitude, 0.2f, 0.8f, 0, 255);
        p.Deyor.drawBorder(smoothedAmplitude / 8, color, p);

        p.pushMatrix();
        p.translate(p.width / 2, p.height / 2, 0);
        p.rotateX(Visual.radians(spinning));
        p.rotateY(Visual.radians(spinning * 2));
        size = Player.map(smoothedAmplitude, 0, 1, 10, 50);

        // 4 small shapes
        p.translate(150 * smallPyramidDist, 150 * smallPyramidDist);
        p.Deyor.drawPyramid(size, color, p);
        p.Deyor.drawCircle(size, p);

        p.translate(-300 * smallPyramidDist, -300 * smallPyramidDist);
        p.Deyor.drawPyramid(size, color, p);
        p.Deyor.drawCircle(size, p);

        p.translate(300 * smallPyramidDist, 0);
        p.Deyor.drawPyramid(size, color, p);
        p.Deyor.drawCircle(size, p);

        p.translate(-300 * smallPyramidDist, 300 * smallPyramidDist);
        p.Deyor.drawPyramid(size, color, p);
        p.Deyor.drawCircle(size, p);
        p.popMatrix();

        spinning += (smoothedAmplitude) * 4.0f;

        for (Circle c : randCircles) {
            float color = Visual.map(randCircles.indexOf(c), 0, randCircles.size(), 0, 255);
            p.fill(color, 255, 255);
            p.stroke(color, 255, 255);
            c.render(p, smoothedAmplitude);
        }

        if (p.frameCount % (10 + ((1 / smoothedAmplitude) * 40)) < 2) {
            randomiseCircles(p);
        }
    }
}