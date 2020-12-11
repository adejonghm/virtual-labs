package Suma_AngExt_Polygon;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;

public class vertice extends Point2D.Double {

    public int ANCHO = 7;
    private Rectangle frontera;
    private Arc2D frontera2;
    private boolean marcado;
    private String nombre;

    public vertice(int x, int y, String nombre) {
        super(x, y);
        frontera = new Rectangle(x - (ANCHO), y - (ANCHO), 2 * ANCHO, 2 * ANCHO);
        this.nombre = nombre;
        frontera2 = new Arc2D.Double(x - 25, y - 25, 50, 50, 0, 360, Arc2D.CHORD);
    }

    public void setANCHO(int ANCHO) {
        this.ANCHO = ANCHO;
    }

    public vertice(Point2D.Double p, String nombre) {
        super(p.x, p.y);
        this.nombre = nombre;
    }

    public Arc2D getFrontera2() {
        return frontera2;
    }

    public Rectangle getFrontera() {
        return frontera;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean contains(Point2D p) {
        return frontera.contains(p);
    }

    public void paint(Graphics2D g) {
//        g.setColor(new Color(171, 43, 52));    
        g.setColor(Color.black);
        g.fillOval((int) (x - ANCHO / 2), (int) (y - ANCHO / 2), ANCHO, ANCHO);
        g.drawString(nombre, (int) this.x + 10, (int) this.y + 10);
    }

    public boolean isMarcado() {
        return marcado;
    }

    public void setMarcado(boolean marcado) {
        this.marcado = marcado;
    }
}
