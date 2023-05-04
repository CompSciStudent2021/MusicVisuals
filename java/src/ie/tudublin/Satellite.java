package ie.tudublin;

import ie.tudublin.Visual;
import processing.core.PApplet;

public class Satellite extends PApplet {

    float rotateX;
    float rotateY;
    float[] lerpBuff;
    float smoothedAmplitude = 0;
    float size = 0;
    float c = 0;
    float c2 = 0;
    float theta = 0;
    float speed = 0;
  
    //Constructor
    public Satellite(int width) {
      this.width = width;
      lerpBuff = new float[width];
    }
    
    public void render(Player p) {

        //p.stroke(Player.map(p.getSmoothedAmplitude(), 0, 0.6f, 0, 255), 255, 255);

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

        p.lights();
        p.background(0);
        p.translate(width/2, height/2, 0);
        p.rotateX(theta / 2);
        p.rotateY(theta / 2);

        /*for (int i = 0; i < p.getAudioBuffer().size(); i++) {
            // These are to map through the colour specturm and change colours while
            // visualising
            float c = Visual.map(i, 0, p.getAudioBuffer().size(), 0, 255);
            float c2 = Visual.map(i, 0, p.getAudioBuffer().size(), 255, 0);

            // These will add the border to all 4 sides and will be changed with the
            // frequency of the song
            p.stroke(c, 255, 255);
            float f1 = lerpBuff[i] * halfH * 2.5f;
            p.rect(i * 2, halfH * 1.95f + f1, i, halfH - f1);

            float f2 = lerpBuff[i] * halfH * 2.5f;
            p.rect(i * 2, 0 + f2, i, 0 - f2);

            float f3 = lerpBuff[i] * halfH * 2.0f;
            p.rect(0 + f3, i, 0 - f3, i);

            float f4 = lerpBuff[i] * halfH * 2.0f;
            p.rect(p.width - f4, i, p.height + f4, i);
        }*/

        // Draw the head
        p.pushMatrix();
        p.translate(p.width / 2, p.height / 2, 0);
        p.sphere(size * 2.5f);
        p.popMatrix();
        
        // Draw the eyes
        p.pushMatrix();
        p.translate(p.width / 2, p.height / 2, 0);
        p.rotateX(theta / 2);
        p.rotateY(theta / 2);
        p.box(30);
        p.popMatrix();
        
        p.pushMatrix();
        p.translate(p.width / 2, p.height / 2, 0);
        p.rotateX(theta / 2);
        p.rotateY(theta / 2);
        p.box(50);
        p.popMatrix();
        
        // Draw the pupils
        p.pushMatrix();
        p.translate(p.width / 2, p.height / 2, 0);
        p.fill(0);
        p.sphere(50);
        p.popMatrix();
        
        p.pushMatrix();
        p.translate(p.width / 2, p.height / 2, 0);
        p.fill(0);
        p.sphere(50);
        p.popMatrix();
        
        // Draw the nose
        p.pushMatrix();
        p.translate(p.width / 2, p.height / 2, 0);
        p.rotateX(theta / 2);
        cone(40, 60);
        p.popMatrix();
        
        // Draw the mouth
        p.pushMatrix();
        p.translate(p.width / 2, p.height / 2, 0);
        p.rotateX(theta / 2);
        p.box(size, 40, 40);
        p.popMatrix();
    }

    void drawBorder(float smoothedAmplitude, float colour, Player p) {
        float border = Player.map(smoothedAmplitude, 0, 0.15f, 3, 70);
        p.fill(colour, 255, 255, 150);
        p.stroke(colour, 255, 255);
        p.pushMatrix(); // save current transformation state
        p.translate(-p.width/2, -p.height/2); // undo current translation
        //p.resetMatrix(); // reset the rotation to 0
        p.rect(0, 0, p.width, border); // Top
        p.rect(p.width - border, 0, border, p.height); // Right
        p.rect(0, p.height - border, p.width, border); // Bottom
        p.rect(0, 0, border, p.height); // Left
        p.popMatrix(); // restore previous transformation state
    }

    private void cone(int i, int j) {
    }
}
