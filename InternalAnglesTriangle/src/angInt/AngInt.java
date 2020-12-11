package angInt;

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
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author adejongh
 */
public class AngInt extends JPanel {

    private vertice v1, v2, v3;
    private Line2D l1, l2, l3;
    private int flag = 0;
    private double radio,  ang1,  ang2,  ang3,  ang12,  ang13,  ang23,  p12,  p23,  p13;

    public AngInt(int x1, int y1, int x2, int y2, int x3, int y3) {
        setPreferredSize(new Dimension(200, 200));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createTitledBorder("Angulos Interiores de un Triangulo"));

        radio = 50;
        this.v1 = new vertice(x1, y1, "A");
        this.v2 = new vertice(x2, y2, "B");
        this.v3 = new vertice(x3, y3, "C");
        CalcAngulos();
        l1 = new Line2D.Double(v1, v2);
        l2 = new Line2D.Double(v2, v3);
        l3 = new Line2D.Double(v3, v1);

        MouseListener pintar = new MouseListener() {

            public void mouseClicked(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {
                if (v1.contains(new vertice(e.getX(), e.getY(), v1.getNombre()))) {
                    flag = 1;
                } else if (v2.contains(new vertice(e.getX(), e.getY(), v2.getNombre()))) {
                    flag = 2;
                } else if (v3.contains(new vertice(e.getX(), e.getY(), v3.getNombre()))) {
                    flag = 3;
                }
            }

            public void mouseReleased(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }
        };
        addMouseListener(pintar);

        MouseMotionListener pintMove = new MouseMotionListener() {

            public void mouseDragged(MouseEvent e) {
                if (flag == 1 && Altura(new vertice(e.getX(), e.getY(), ""), v2, v3) > 5 && !Cruzado(new vertice(e.getX(), e.getY(), ""), v2, v3, v1)) {
                    v1 = new vertice(e.getX(), e.getY(), v1.getNombre());
                } else if (flag == 2 && Altura(new vertice(e.getX(), e.getY(), ""), v1, v3) > 5 && !Cruzado(new vertice(e.getX(), e.getY(), ""), v1, v3, v2)) {
                    v2 = new vertice(e.getX(), e.getY(), v2.getNombre());
                } else if (flag == 3 && Altura(new vertice(e.getX(), e.getY(), ""), v2, v1) > 5 && !Cruzado(new vertice(e.getX(), e.getY(), ""), v1, v2, v3)) {
                    v3 = new vertice(e.getX(), e.getY(), v3.getNombre());
                }
                repaint();
            }

            public void mouseMoved(MouseEvent e) {
            }
        };
        addMouseMotionListener(pintMove);
    }

    public void paintT(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        Update();

        g2.setFont(new Font("Tahoma", Font.BOLD, 12));
        g2.setStroke(new BasicStroke(2.0f));
        g2.setColor(Color.green);
        g2.drawArc((int) (v1.x - radio / 2), (int) (v1.y - radio / 2), (int) radio, (int) radio, (int) ang13, (int) ang1 - 180);
        g2.setColor(Color.blue);
        g2.drawArc((int) (v2.x - radio / 2), (int) (v2.y - radio / 2), (int) radio, (int) radio, (int) ang12, (int) ang2 - 180);
        g2.setColor(Color.orange);
        g2.drawArc((int) (v3.x - radio / 2), (int) (v3.y - radio / 2), (int) radio, (int) radio, (int) ang23, (int) ang3 - 180);

        g2.setColor(Color.black);
        g2.draw(l1);
        g2.draw(l2);
        g2.draw(l3);
        v1.paint(g2);
        v2.paint(g2);
        v3.paint(g2);

        g2.setStroke(new BasicStroke());
//        g2.setColor(new Color(171, 43, 52));
        g2.drawRect(250, 15, 250, 50);

    /* Pintando simbolos de ang. */
        g2.drawString("BAC =", 265, 30);
        g2.drawString("ABC =", 348, 30);
        g2.drawString("BCA =", 432, 30);
        g2.drawString(((int) ang2 + (int) ang3 - 180) + "º", 305, 30);
        g2.drawString((180 - (int) ang2) + "º", 388, 30);
        g2.drawString((180 - (int) ang3) + "º", 473, 30);
        g2.drawLine(255, 29, 262, 23);
        g2.drawLine(255, 29, 262, 29);
        g2.drawLine(338, 29, 345, 23);
        g2.drawLine(338, 29, 345, 29);
        g2.drawLine(422, 29, 429, 23);
        g2.drawLine(422, 29, 429, 29);

        g2.setStroke(new BasicStroke(1f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1f, new float[]{4}, 0f));
        g2.drawLine(260, 40, 486, 40);
        g2.setStroke(new BasicStroke());
        g2.drawString("BAC +    ABC +    BCA = 180º", 265, 60);
        g2.drawLine(255, 59, 262, 53);
        g2.drawLine(255, 59, 262, 59);        
        g2.drawLine(310, 59, 317, 53);
        g2.drawLine(310, 59, 317, 59);
        
        g2.drawLine(365, 59, 372, 53);
        g2.drawLine(365, 59, 372, 59);
    /* Fin */

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintT(g);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Angulos Interiores de un Triangulo") {

            @Override
            public void frameInit() {
                super.frameInit();
                setDefaultCloseOperation(EXIT_ON_CLOSE);
            }
        };

        JPanel p = new AngInt(120, 50, 30, 200, 200, 200);
        frame.add(p);
        frame.setSize(530, 300);
        frame.setLocation(300, 200);
        frame.show();

    }

    private void Update() {
        if (flag == 1) {
            l1 = new Line2D.Double(v1, v2);
            l3 = new Line2D.Double(v3, v1);
        } else if (flag == 2) {
            l1 = new Line2D.Double(v1, v2);
            l2 = new Line2D.Double(v2, v3);
        } else if (flag == 3) {
            l3 = new Line2D.Double(v3, v1);
            l2 = new Line2D.Double(v2, v3);
        }
        CalcAngulos();
    }

    private void CalcAngulos() {
        if (v1.x != v2.x && v3.x != v1.x) {
            p12 = (v1.y - v2.y) / (v1.x - v2.x);
            p13 = (v1.y - v3.y) / (v1.x - v3.x);
            if (p12 == -1 / p13) {
                ang1 = 90;
            } else {
                ang1 = (180 + (Math.atan((p12 - p13) / (1 + p12 * p13))) * 180 / Math.PI) % 180;
            }
        } else if (v1.x != v2.x) {
            p12 = (v1.y - v2.y) / (v1.x - v2.x);
            ang1 = 90 + Math.atan(p12) * 180 / Math.PI;
        } else {
            p13 = (v3.y - v1.y) / (v3.x - v1.x);
            ang1 = 90 + Math.atan(p13) * 180 / Math.PI;
            ang1 = 180 - ang1;
        }

        if (v1.x != v2.x) {
            p12 = (v1.y - v2.y) / (v1.x - v2.x);
            double angulotemporal1 = 360 - (180 + (Math.atan(p12) * 180 / Math.PI)) % 180;
            double angulotemporal2 = angulotemporal1 - 180;
            ang12 = (v1.y > v2.y) ? angulotemporal1 : angulotemporal2;
        } else {
            ang12 = (v1.y > v2.y) ? 270 : 90;
        }
        ang1 = 180 - ang1;
        if (v1.x != v2.x && v3.x != v2.x) {
            p12 = (v1.y - v2.y) / (v1.x - v2.x);
            p23 = (v2.y - v3.y) / (v2.x - v3.x);
            if (p12 == -1 / p23) {
                ang2 = 90;
            } else {
                ang2 = (180 + (Math.atan((p12 - p23) / (1 + p12 * p23))) * 180 / Math.PI) % 180;
            }
        } else if (v1.x != v2.x) {
            p12 = (v1.y - v2.y) / (v1.x - v2.x);
            ang2 = 90 + Math.atan(p12) * 180 / Math.PI;
        } else {
            p23 = (v3.y - v2.y) / (v3.x - v2.x);
            ang2 = 90 + Math.atan(p23) * 180 / Math.PI;
            ang2 = 180 - ang2;
        }
        if (v2.x != v3.x) {
            p23 = (v3.y - v2.y) / (v3.x - v2.x);
            double angulotemporal1 = 360 - (180 + (Math.atan(p23) * 180 / Math.PI)) % 180;
            double angulotemporal2 = angulotemporal1 - 180;
            ang23 = (v2.y > v3.y) ? angulotemporal1 : angulotemporal2;
        } else {
            ang23 = (v2.y > v3.y) ? 270 : 90;
        }
        if (v1.x != v3.x && v3.x != v2.x) {
            p13 = (v1.y - v3.y) / (v1.x - v3.x);
            p23 = (v2.y - v3.y) / (v2.x - v3.x);
            if (p13 == -1 / p23) {
                ang3 = 90;
            } else {
                ang3 = (180 + (Math.atan((p13 - p23) / (1 + p13 * p23))) * 180 / Math.PI) % 180;
            }
        } else if (v1.x != v3.x) {
            p13 = (v1.y - v3.y) / (v1.x - v3.x);
            ang3 = 90 + Math.atan(p13) * 180 / Math.PI;
        } else {
            p23 = (v3.y - v2.y) / (v3.x - v2.x);
            ang3 = 90 + Math.atan(p23) * 180 / Math.PI;
            ang3 = 180 - ang3;
        }
        if (v1.x != v3.x) {
            p13 = (v3.y - v1.y) / (v3.x - v1.x);
            double angulotemporal1 = 360 - (180 + (Math.atan(p13) * 180 / Math.PI)) % 180;
            double angulotemporal2 = angulotemporal1 - 180;
            ang13 = (v3.y > v1.y) ? angulotemporal1 : angulotemporal2;
        } else {
            ang13 = (v3.y > v1.y) ? 270 : 90;
        }
        ang3 = 180 - ang3;
    }

    public double Altura(vertice A, vertice B, vertice C) {
        double altura = 0;
        if (C.x == B.x) {
            altura = Math.abs(A.x - B.x);
        } else {
            double p = (B.y - C.y) / (B.x - C.x);
            double Ta = -p;
            double Tc = p * B.x - B.y;
            double Tb = 1;
            altura = Math.abs(Ta * A.x + Tb * A.y + Tc) / (Math.sqrt(Ta * Ta + Tb * Tb));
        }
        return altura;
    }

    public boolean Cruzado(vertice e, vertice a, vertice b, vertice r) {
        boolean ok = true;
        if (e.x == r.x && a.x == b.x) {
            ok = false;
        } else {
            if (e.x != r.x && a.x != b.x && (e.y - r.y) / (e.x - r.x) == (a.y - b.y) / (a.x - b.x)) {
                ok = false;
            } else {
                double x, y, p, k;
                if (e.x != r.x && a.x != b.x) {
                    p = (e.y - r.y) / (e.x - r.x);
                    k = (a.y - b.y) / (a.x - b.x);
                    x = (-r.y + b.y - k * b.x + p * r.x) / (p - k);
                    y = p * (x - r.x) + r.y;
                } else if (e.x != r.x) {
                    p = (e.y - r.y) / (e.x - r.x);
                    x = b.x;
                    y = p * (x - r.x) + r.y;
                } else {
                    k = (a.y - b.y) / (a.x - b.x);
                    x = r.x;
                    y = k * (x - b.x) + b.y;
                }
                if (!((x <= r.x && x >= e.x && r.x >= e.x) || (x >= r.x && x <= e.x && e.x >= r.x))) {
                    ok = false;
                }
            }
        }
        return ok;
    }
}
