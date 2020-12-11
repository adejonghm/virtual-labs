/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package distanciapuntorecta;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

/**
 *
 * @author yise
 */
public class Recta extends Line2D.Double{
 private String nombre;

    public Recta(double x1, double y1, double x2, double y2, String nombre) {
        super(x1, y1, x2, y2);
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }


    public void paint(Graphics2D g) {
        g.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
        g.setColor(Color.BLACK);
        g.drawString(nombre, (int) this.x1, (int) this.y1 + 20);
        g.setColor(Color.BLUE);

    }
}
