package clases;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

/**
 *
 * @author Marcel
 */
public class vertice extends Point2D.Double{
 public static int ANCHO = 5;
    private Rectangle frontera;
    private boolean marcado;
    private String nombre;

    public vertice(double x, double y, String nombre) {
        super(x,y);
        frontera = new Rectangle((int)x - (ANCHO / 2), (int)y - (ANCHO / 2), ANCHO, ANCHO);
        this.nombre = nombre;

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

    public void paint(Graphics2D g){
        g.fillOval((int)(x - ANCHO/2), (int)(y -ANCHO/2), ANCHO, ANCHO);        
        g.drawString(nombre, (int)this.x+10, (int)this.y+10);
    }


    public boolean isMarcado() {
        return marcado;
    }


    public void setMarcado(boolean marcado) {
        this.marcado = marcado;
    }
}