package Suma_AngExt_Polygon;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

/**
 *
 * @author alsola
 */
public class VerticeMedio extends vertice {

    private String nombre;

    public VerticeMedio(Point2D.Double p, String nombre) {
        super((int) p.x, (int) p.y, nombre);
        this.nombre = nombre;
    }

    @Override
    public void paint(Graphics2D g) {
        g.setColor(new Color(227, 232, 27));
        g.fillOval((int) (x - 6 / 2), (int) (y - 6 / 2), 6, 6);
        g.drawString(nombre, (int) this.x + 10, (int) this.y + 10);
        g.setColor(Color.white);
    }
}
