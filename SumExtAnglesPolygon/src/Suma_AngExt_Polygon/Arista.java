package Suma_AngExt_Polygon;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Line2D;
import java.awt.Graphics2D;

/**
 *
 * @author alsola
 */
public class Arista extends Line2D.Double {

    private vertice vinicial;
    private vertice vfinal;
    private vertice exterior;
    private int n;

    public Arista(vertice vinicial, vertice vfinal, int n) {
        super(vinicial.x, vinicial.y, vfinal.x, vfinal.y);
        this.vinicial = vinicial;
        this.vfinal = vfinal;
        this.n = n;

        int x = (int) (2 * vinicial.x - vfinal.x);
        int y = (int) (2 * vinicial.y - vfinal.y);
        int nu = vinicial.getNombre().charAt(0) - 65 + n;
        exterior = new vertice(x, y, (char) Integer.parseInt((65 + nu) + "") + "");
    }

    public vertice getVfinal() {
        return vfinal;
    }

    public void setVfinal(vertice vfinal) {
        this.vfinal = vfinal;
    }

    public vertice getVinicial() {
        return vinicial;
    }

    public void setVinicial(vertice vinicial) {
        this.vinicial = vinicial;
    }

    public void pintar(Graphics2D g2) {
        exterior.setANCHO(0);
        exterior.paint(g2);
        Arista continua = new Arista(vinicial, vfinal, n);
        Arista discontinua = new Arista(vinicial, exterior, n);
        g2.draw(continua);
        g2.setColor(new Color(150, 149, 148));
        g2.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 2f, new float[]{4}, 0f));
        g2.draw(discontinua);
        g2.setStroke(new BasicStroke(2.0f));
    }
}
