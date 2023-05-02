package ie.tudublin;

public class Circle {

    float cx;
    float cy;
    float cz;
    float angle = 0;

    public Circle(float cx, float cy, float cz) {
        this.cx = cx;
        this.cy = cy;
        this.cz = cz;
    }

    public void render(Player p, float spinning) {
        p.pushMatrix();
        p.translate(p.width / 2, p.height / 2, 0);
        p.rotateX(Visual.radians(angle));
        p.rotateY(Visual.radians(angle * 5));
        p.rotateZ(Visual.radians(angle * 5));
        p.translate(cx, cy, cz);
        p.sphere(5 * p.getSmoothedAmplitude());
        p.popMatrix();
        angle += (spinning) * 1.5f;

    }
}