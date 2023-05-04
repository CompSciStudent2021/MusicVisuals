package ie.tudublin;

import ie.tudublin.Visual;

public class Player extends Visual {

    public void settings() {
        size(800, 800, P3D);
        println("CWD: " + System.getProperty("user.dir"));
    }

    int mode = 1;
    boolean paused = true;

    public void keyPressed() {
        if (key >= '0' && key <= '5') {
            mode = key - '0';
        }

        switch (key) {
            case ' ': {
                // Pauses song
                if (paused) {
                    getAudioPlayer().play();
                    paused = false;
                }

                else {
                    getAudioPlayer().pause();
                    paused = true;
                }

                break;
            }

            // Play song
            case '1': {
                getAudioPlayer().play();
                break;
            }

            // Reset song
            case 'r': {
                getAudioPlayer().cue(0);
                getAudioPlayer().play();
            }
        }
    }

    public void setup() {
        colorMode(HSB);
        // noCursor();

        setFrameSize(1024);
        frameRate(60);

        startMinim();
        loadAudio("C:/Users/Ryanf/OneDrive/Desktop/MusicVisuals/MusicVisuals/java/src/ie/tudublin/Song1.mp3");

        getAudioPlayer().setGain(-25);
    }
    
    RocketShip Ryan = new RocketShip(1024);
    Deyor Deyor = new Deyor();
    Sam Sam = new Sam(1920);
    Satellite Satellite = new Satellite(1024);
    public Object trailX;

    public void draw() {

        switch (mode) {

            case 1: // Sam
            {
                Sam.render(this);
                break;
            }

            case 2: // Ryan
            {
                RocketShip.render(this);
                break;
            }

            case 3: // Deyor
            {
                Deyor.render(this);
                break;
            }

            case 4: // Combined
            {
                Satellite.render(this);
                break;
            }
        }
    }
}