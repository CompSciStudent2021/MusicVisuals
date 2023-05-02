package ie.tudublin;

import ie.tudublin.Visual;

public class Sam {
    int width;
    float[] lerpedBuffer;
    float y;
    float angle = 0;
    float smoothedY;
    float smoothedAmplitude;

    // Constructor
    public Sam(int width) {
        this.width = width;
        lerpedBuffer = new float[width];
        y = 0;
        smoothedY = 0;
        smoothedAmplitude = 0;
    }

    // This is the render method that will be called in the main class
    public void render(Player p) {
        p.strokeWeight(1.5f);
        p.colorMode(Visual.HSB);
        float halfH = p.height / 2;
        float average = 0;
        float sum = 0;
        smoothedAmplitude = Visual.lerp(smoothedAmplitude, average, 0.1f);

        // For loop to loop through all of the visuals
        for (int i = 0; i < p.getAudioBuffer().size(); i++) {
            sum += Visual.abs(p.getAudioBuffer().get(i));
            lerpedBuffer[i] = Visual.lerp(lerpedBuffer[i], p.getAudioBuffer().get(i), 0.05f);
        }
        average = sum / (float) p.getAudioBuffer().size();

        sum = 0;

        p.background(0);

        // For loop to loop through all of the visuals
        for (int i = 0; i < p.getAudioBuffer().size(); i++) {
            // These are to map through the colour specturm and change colours while
            // visualising
            float c = Visual.map(i, 0, p.getAudioBuffer().size(), 0, 255);
            float c2 = Visual.map(i, 0, p.getAudioBuffer().size(), 255, 0);

            // These will add the border to all 4 sides and will be changed with the
            // frequency of the song
            p.stroke(c, 255, 255);
            float f1 = lerpedBuffer[i] * halfH * 2.5f;
            p.rect(i * 2, halfH * 1.95f + f1, i, halfH - f1);

            float f2 = lerpedBuffer[i] * halfH * 2.5f;
            p.rect(i * 2, 0 + f2, i, 0 - f2);

            float f3 = lerpedBuffer[i] * halfH * 2.0f;
            p.rect(0 + f3, i, 0 - f3, i);

            float f4 = lerpedBuffer[i] * halfH * 2.0f;
            p.rect(p.width - f4, i, p.height + f4, i);

            // These will add the circles to the middle of the screen and will be changed
            // with the frequency of the song
            p.noFill();
            p.stroke(c2, 255, 255);
            float fcircle = lerpedBuffer[i] * halfH * 4.0f;
            p.circle(p.width / 2, halfH, p.width * 0.1f + fcircle);
            p.stroke(c, 255, 255);
            p.circle(p.width / 2, halfH, p.width * 0.3f + fcircle);

        }
    }
}