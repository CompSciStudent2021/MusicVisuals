package ie.tudublin;

import ie.tudublin.Visual;
import java.util.ArrayList;

public class Ryan {

    float ang = 0;
    int width;
    float[] lerpBuff;
    float[] lerpColors;
    float x, z, prev = 0;
    float smoothedAmplitude;
    float swing, swing2 = 0, color = 0, color2 = 0;
    float spinning;
    float cx, cy, cz;
    ArrayList<Circle> randCircles;

    // Constructor
    public Ryan(int width) {
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

        p.pushMatrix();
        p.translate(p.width / 2, p.height / 2, 0);
        p.calculateAverageAmplitude();
        smoothedAmplitude = p.getSmoothedAmplitude();
        swing = Visual.lerp(swing, Visual.map(p.random(0f, 1.0f), 0, 1, -2, 2), 0.01f);
        p.rotateZ(swing + rotateZ);
        p.rotateX(swing / 2 + rotateX);
        p.rotateY(swing2 + rotateY);
        swing2 += smoothedAmplitude * 0.025f;
        p.beginShape();
        int i = 0;
        p.noFill();

        for (float deg = 0; deg <= 360; deg += 20) {
            i = Visual.floor(Visual.map(deg, 0, 360, 0, p.getAudioBuffer().size() - 1));
            color = Visual.map(i, 0, p.getAudioBuffer().size() - 1, 0, 255);
            p.stroke(color, 200, 200);
            p.strokeWeight(thickness);
            x = (smoothedAmplitude * size) * Visual.sin(Visual.radians(deg));
            z = (smoothedAmplitude * size) * Visual.cos(Visual.radians(deg));
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

        p.background(0);
        p.colorMode(Visual.HSB);
        p.noFill();
        p.stroke(200, 200, 200);
        for (int i = 0; i < p.getAudioBuffer().size(); i++) {
            lerpBuff[i] = Visual.lerp(lerpBuff[i], p.getAudioBuffer().get(i), 0.025f);
        }
        p.fill(255);
        p.noStroke();
        color2 += smoothedAmplitude * 5;
        if (color2 > 255) {
            color2 = 0;
        }
        p.pushMatrix();
        p.translate(p.width / 2, p.height / 2, 0);
        p.circle(0, 0, smoothedAmplitude * p.width / 3);
        p.fill(color2, 255, 255);
        p.circle(0, 0, (smoothedAmplitude * p.width / 3) - p.width * 0.01f);
        p.popMatrix();

        drawCircleVisualiser(p, 0, -30, 0, 500, 2.5f, 5);
        drawCircleVisualiser(p, 0, -30, -(spinning + smoothedAmplitude), 380, 1f, 5);
        drawCircleVisualiser(p, 0 + spinning * 3, 0, 90 + spinning + smoothedAmplitude, 220, 1f, 5);
        spinning += 1;
        p.pushMatrix();

        for (Circle c : randCircles) {
            float color = Visual.map(randCircles.indexOf(c), 0, randCircles.size(), 0, 255);
            p.fill(color, 255, 255);
            p.stroke(color, 255, 255);
            c.render(p, smoothedAmplitude);
        }

        if (p.frameCount % (10 + ((1 / smoothedAmplitude) * 40)) < 2) {
            randomiseCircles(p);
        }
        p.popMatrix();
    }
}