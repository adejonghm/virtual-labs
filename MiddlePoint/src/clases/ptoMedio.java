/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author hvarias
 */
public class ptoMedio extends JPanel {

    private vertice p, q, m, aux1, aux2, pt1, pt2, pt3, pt4;
    private Line2D pq, ejex, ejey;
    private int flag;
    private Color negro, azul, rojo, gris;
    private double centrox, centroy;

    public ptoMedio(int x1, int y1, int x2, int y2) {
        setPreferredSize(new Dimension(200, 200));
        setBackground(Color.WHITE);
//        setBorder(BorderFactory.createTitledBorder("Baricentro"));

        this.p = new vertice(x1, y1, "P");
        this.q = new vertice(x2, y2, "Q");
        this.m = new vertice((p.x + q.x) / 2, (p.y + q.y) / 2, "M");
        this.pq = new Line2D.Double(p, q);
        this.centrox = 392 / 2;
        this.centroy = 266 / 2;
        this.negro = new Color(0, 0, 0);
        this.azul = new Color(9, 69, 141);
        this.rojo = new Color(171, 43, 52);
        this.gris = new Color(150, 149, 148);
        this.ejex = new Line2D.Double(0, centroy, (centrox * 2) + 10, centroy);
        this.ejey = new Line2D.Double(centrox, 5, centrox, centroy * 2 + 40);

        MouseListener pintar = new MouseListener() {

            public void mouseClicked(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {
                if (p.contains(new vertice(e.getX(), e.getY(), p.getNombre()))) {
                    flag = 1;
                } else {
                    if (q.contains(new vertice(e.getX(), e.getY(), q.getNombre()))) {
                        flag = 2;
                    } else {
                        flag = 0;
                    }
                }
            }

            public void mouseReleased(MouseEvent e) {
                repaint();
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }
        };
        addMouseListener(pintar);

        MouseMotionListener pintarMovimiento = new MouseMotionListener() {

            public void mouseDragged(MouseEvent e) {
                if (flag == 1) {
                    p = new vertice(e.getX(), e.getY(), p.getNombre());
                } else {
                    if (flag == 2) {
                        q = new vertice(e.getX(), e.getY(), q.getNombre());
                    }
                }
                repaint();
            }

            public void mouseMoved(MouseEvent e) {
                repaint();
            }
        };
        addMouseMotionListener(pintarMovimiento);

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        pintarTriangulo(g);
    }

    public void pintarTriangulo(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);

        g2.setFont(new Font("Tahoma", Font.BOLD, 12));
//Pintar ejes
        g2.setColor(gris);
        g2.draw(ejex);
        g2.draw(ejey);

//Pintar mediciones de los ejes
        //eje x
        g2.setFont(new Font("Tahoma", Font.BOLD, 10));
        int cont = -9;
        for (int i = 16; i < centrox; i += 20) {
            aux1 = new vertice(i, centroy - 3, "");
            aux2 = new vertice(i, centroy + 3, "");
            g2.drawLine((int) aux1.x, (int) aux1.y, (int) aux2.x, (int) aux2.y);
            if (cont != 0) {
                g2.drawString(cont + "", (int) aux2.x - 7, (int) aux2.y + 10);
            }
            cont++;
        }

        for (int i = (int) centrox; i < 400; i += 20) {
            aux1 = new vertice(i, centroy - 3, "");
            aux2 = new vertice(i, centroy + 3, "");
            if (cont != 0 && cont <= 9) {
                g2.drawLine((int) aux1.x, (int) aux1.y, (int) aux2.x, (int) aux2.y);
                g2.drawString(cont + "", (int) aux2.x - 2, (int) aux2.y + 10);
            }
            cont++;
        }

        //eje y
        cont = 6;
        for (int i = 13; i <= centroy; i += 20) {
            aux1 = new vertice(centrox - 3, i, "");
            aux2 = new vertice(centrox + 3, i, "");
            if (cont > 0 && cont <= 5) {
                g2.drawLine((int) aux1.x, (int) aux1.y, (int) aux2.x, (int) aux2.y);
                g2.drawString(cont + "", (int) aux1.x - 8, (int) aux1.y + 5);
            }
            if (cont == 0) {
                g2.drawString("0", (int) aux1.x - 8, (int) aux1.y + 13);
            }
            cont--;
        }


        cont++;
        for (int i = (int) centroy; i < 300; i += 20) {
            aux1 = new vertice(centrox - 3, i, "");
            aux2 = new vertice(centrox + 3, i, "");
            g2.drawLine((int) aux1.x, (int) aux1.y, (int) aux2.x, (int) aux2.y);
            if (cont < 0) {
                g2.drawString(cont + "", (int) aux1.x - 12, (int) aux1.y + 5);
            }
            cont--;
        }


//Pintar puntos y recta
        g2.setFont(new Font("Tahoma", Font.BOLD, 12));
        g2.setColor(rojo);
        p.paint(g2);
        q.paint(g2);
        m.paint(g2);

        g2.setColor(negro);
        g2.setFont(new Font("Tahoma", Font.BOLD, 11));
        g2.drawString("x", 400, ((int) centroy + 15));
        g2.setColor(gris);
        g2.drawLine(403, (int) centroy, 398, (int) centroy + 2);
        g2.drawLine(403, (int) centroy, 398, (int) centroy - 2);

        g2.setColor(negro);
        g2.drawString("y", ((int) centrox + 8), 12);
        g2.setColor(gris);
        g2.drawLine((int) centrox, 5, (int) centrox + 2, 10);
        g2.drawLine((int) centrox, 5, (int) centrox - 2, 10);

        g2.setStroke(new BasicStroke(2.0f));
        g2.setColor(azul);
        g2.draw(pq);

        Update();
        escribir(g2);
    }

    public void escribir(Graphics2D g2) {

        g2.setColor(rojo);
        g2.setFont(new Font("Tahoma", Font.BOLD, 12));
        g2.drawString("P(" + (p.x - centrox) / 20 + " , " + (centroy - p.y) / 20 + ")", 10, 15);
        g2.drawString("Q(" + (q.x - centrox) / 20 + " , " + (centroy - q.y) / 20 + ")", 10, 35);
        g2.drawString("M(" + (m.x - centrox) / 20 + " , " + (centroy - m.y) / 20 + ")", 10, 55);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Punto medio de un segmento") {

            public void frameInit() {
                super.frameInit();
                setDefaultCloseOperation(EXIT_ON_CLOSE);
            }
        };
        JPanel p = new ptoMedio(136, 150, 250, 80);
        frame.add(p);
        frame.setSize(450, 350);
        frame.show();
    }

    private void Update() {
        pq = new Line2D.Double(p, q);
        m = new vertice((p.x + q.x) / 2, (p.y + q.y) / 2, m.getNombre());
    }
}
