package ie.tudublin; // package declaration

import processing.core.PApplet; // import processing library for PApplet class
import processing.core.PConstants; // import processing library for PConstants class

public class RotatingPolygon extends PApplet { // class definition, extending PApplet class

  private static float angle = 0; // initialize static angle variable to zero
  private final static float rotationSpeed = 0.001f; // initialize constant rotationSpeed variable to 0.001
  private final static int numSides = 6; // initialize constant numSides variable to 6
  private final static int numPolygons = 5; // initialize constant numPolygons variable to 5
  private final static float baseRadius = 50; // initialize constant baseRadius variable to 50
  private final static float[] xPoints = new float[numSides]; // initialize static xPoints array with length of numSides
  private final static float[] yPoints = new float[numSides]; // initialize static yPoints array with length of numSides
  private final static int[] colors = { 0xFF42A5F5, 0xFFFFA726, 0xFFEC407A, 0xFF66BB6A, 0xFFFF7043 }; // initialize static colors array with five hexadecimal color values

  public static void render(PApplet p) { // method to render graphics, passing in PApplet object
    p.background(0); // set background color to black
    p.translate(p.width / 2, p.height / 2); // move origin to center of screen

    // Move and draw stars
    p.stroke(Player.map(((Visual) p).getSmoothedAmplitude(), 0, 0.6f, 0, 255), 255, 255);
    for (int i = 0; i < 100; i++) { // loop 100 times
      float x = p.random(-p.width / 2, p.width / 2); // generate a random x coordinate within screen bounds
      float y = p.random(-p.height / 2, p.height / 2); // generate a random y coordinate within screen bounds
      p.point(x, y); // draw a single pixel at (x, y)
    }

    for (int i = 0; i < numPolygons; i++) { // loop through numPolygons
      float x = p.random(-p.width / 2, p.width / 2); // generate a random x-coordinate within the canvas width
      float y = p.random(-p.height / 2, p.height / 2); // generate a random y-coordinate within the canvas height
      p.point(x, y); // draw a point at the generated x and y coordinates
    }
    
    for (int i = 0; i < numPolygons; i++) { // loop through numPolygons again
      int offset = p.frameCount / 10; // set the offset value for the polygon colour based on the current frame count(changes the colour of each polygon)
      int index = (i + offset) % colors.length; // set the color index for the current polygon using the offset and the colors array
      p.fill(colors[index]); // set the fill color for the current polygon
    
      // Calculate the radius of the polygon based on the current frame count and polygon index
      float radius = baseRadius * (1 + 0.1f * PApplet.sin(p.frameCount * 0.05f * (i + 1)));
      
      // Rotate the canvas by an angle based on the current frame count and polygon index
      p.rotate(angle * (i + 1));
    
      p.beginShape(); // begin drawing the polygon
      for (int j = 0; j < numSides; j++) { // loop through the polygon's sides
        // Calculate the angle for the current side based on the number of sides and Pi constant
        float angle = Player.map(j, 0, numSides, 0, PConstants.TWO_PI);
        xPoints[j] = Player.cos(angle) * radius * (i + 1); // calculate the x-coordinate for the current vertex
        yPoints[j] = Player.sin(angle) * radius * (i + 1); // calculate the y-coordinate for the current vertex
        p.vertex(xPoints[j], yPoints[j]); // add the current vertex to the polygon
      }
      p.endShape(PConstants.CLOSE); // finish drawing the polygon
    
      angle += rotationSpeed * 0.98; // increase the angle for the next polygon, scaled down slightly by a factor of 0.98
    }
  }
}
    