package Teo_Thales;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.*;
import javax.swing.*;

public class Teo_Thales extends JPanel {

    private vertice centro,  v1,  v2,  v3;
    private Arc2D circunferencia;
    private Line2D l1,  l2,  l3,  l5,  l6;
    private int flag;
    private double radio;

    public Teo_Thales(int x1, int y1, int x2, int y2, int x3, int y3, int xcentro, int ycentro) {
        setPreferredSize(new Dimension(200, 200));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createTitledBorder("Teorema de Tales."));

        /** Mi constructor **/
        centro = new vertice(xcentro, ycentro, "O");
        radio = 100;
        circunferencia = new Arc2D.Double(xcentro - radio, ycentro - radio, 2 * radio, 2 * radio, 0, 360, Arc2D.CHORD);
        flag = 0;

        this.v1 = new vertice(x1, y1, "A");
        this.v2 = new vertice(x2, y2, "B");
        this.v3 = new vertice(x3, y3, "C");

        l1 = new Line2D.Double(v1, v2);
        l2 = new Line2D.Double(v2, v3);
        l3 = new Line2D.Double(v3, v1);
        Update();
        /** FIN **/
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
                } else if (centro.contains(new vertice(e.getX(), e.getY(), centro.getNombre()))) {
                    flag = 4;
                } else {
                    flag = 0;
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
                if (flag == 1) {
                    if (e.getX() != centro.x) {
                        int t = (e.getX() > centro.x) ? 1 : -1;
                        double p = (e.getY() - centro.y) / (e.getX() - centro.x);
                        double x = (t * radio / Math.sqrt(p * p + 1) + centro.x);
                        double y = p * (x - centro.x) + centro.y;
                        v1 = new vertice((int) x, (int) y, "A");
                    } else {
                        int t = (e.getY() > centro.y) ? 1 : -1;
                        double x = e.getX();
                        double y = centro.y + t * radio;
                        v1 = new vertice((int) x, (int) y, "A");
                    }
                    v3 = new vertice((int) (2 * centro.x - v1.x), (int) (2 * centro.y - v1.y), "C");
                } else if (flag == 2) {
                    if (e.getX() != centro.x) {
                        int t = (e.getX() > centro.x) ? 1 : -1;
                        double p = (e.getY() - centro.y) / (e.getX() - centro.x);
                        double x = (t * radio / Math.sqrt(p * p + 1) + centro.x);
                        double y = p * (x - centro.x) + centro.y;
                        v2 = new vertice((int) x, (int) y, "B");
                    } else {
                        int t = (e.getY() > centro.y) ? 1 : -1;
                        double x = e.getX();
                        double y = centro.y + t * radio;
                        v2 = new vertice((int) x, (int) y, "B");
                    }
                } else if (flag == 3) {
                    if (e.getX() != centro.x) {
                        int t = (e.getX() > centro.x) ? 1 : -1;
                        double p = (e.getY() - centro.y) / (e.getX() - centro.x);
                        double x = (t * radio / Math.sqrt(p * p + 1) + centro.x);
                        double y = p * (x - centro.x) + centro.y;
                        v3 = new vertice((int) x, (int) y, "C");
                    } else {
                        int t = (e.getY() > centro.y) ? 1 : -1;
                        double x = e.getX();
                        double y = centro.y + t * radio;
                        v3 = new vertice((int) x, (int) y, "C");
                    }
                    v1 = new vertice((int) (2 * centro.x - v3.x), (int) (2 * centro.y - v3.y), "A");
                } else if (flag == 4) {

                    centro = new vertice(e.getX(), e.getY(), "O");
                    radio = longitud(centro, v1);
                    circunferencia = new Arc2D.Double(centro.x - radio, centro.y - radio, 2 * radio, 2 * radio, 0, 360, Arc2D.CHORD);
                    v3 = new vertice((int) (centro.x * 2 - v1.x), (int) (centro.y * 2 - v1.y), "C");
                    l3 = new Line2D.Double(v3, v1);
                    if (v2.x != v3.x) {
                        double m = (v2.y - v3.y) / (v2.x - v3.x);
                        double k = centro.y;
                        double h = centro.x;
                        double x1 = v2.x;
                        double y1 = v2.y;
                        double P = y1 - k;
                        double a = m * m + 1;
                        double b = 2 * (m * P - h - m * m * x1);
                        double c = m * m * x1 * x1 - 2 * m * x1 * P + P * P + h * h - radio * radio;
                        int t = (v2.x > v3.x) ? 1 : -1;
                        double x = (-b + t * Math.sqrt(b * b - 4 * a * c)) / (2 * a);
                        double y = m * (x - x1) + y1;
                        v2 = new vertice((int) x, (int) y, "B");
                    } else {
                        double x = v2.x;
                        double k = centro.y;
                        double h = centro.x;
                        int t = (v2.y > v3.y) ? 1 : -1;
                        double y = t * (Math.sqrt(radio * radio - Math.pow(x - h, 2))) + k;
                        v2 = new vertice((int) x, (int) y, "B");
                    }
                    l1 = new Line2D.Double(v1, v2);
                    l2 = new Line2D.Double(v2, v3);

                }
                repaint();
            }

            public void mouseMoved(MouseEvent e) {
            }
        };
        addMouseMotionListener(pintMove);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintT(g);
    }

    public void paintT(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);

        Update();
        g2.setFont(new Font("Tahoma", Font.BOLD, 12));
        // <-- Circunferencia -->
        g2.setStroke(new BasicStroke(1.5f));
        g2.draw(circunferencia);

        // <-- Triangulo -->
        g2.setColor(Color.green);
        g2.fillPolygon(new int[]{(int) v1.x, (int) v2.x, (int) v3.x}, new int[]{(int) v1.y, (int) v2.y, (int) v3.y}, 3);
        g2.setColor(Color.black);
        g2.draw(l1);
        g2.draw(l2);
        g2.draw(l3);

        // <-- Angulo -->
        g2.setColor(Color.red);
        g2.draw(l5);
        g2.draw(l6);

        // <-- Puntos -->
        g2.setColor(Color.black);
        v1.paint(g2);
        v2.paint(g2);
        v3.paint(g2);
        centro.paint(g2);

        // <-- Datos Mostrados -->
        g2.setStroke(new BasicStroke(1.0f));
        g2.drawRect(295, 25, 100, 35);
        g2.drawString("ABC = 90º", 320, 45);
        g2.drawLine(305, 44, 315, 38);
        g2.drawLine(305, 44, 315, 44);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Demostración del Teorema de Tales.") {

            @Override
            public void frameInit() {
                super.frameInit();
                setDefaultCloseOperation(EXIT_ON_CLOSE);
            }
        };
        JPanel p = new Teo_Thales(100, 200, 200, 100, 300, 200, 200, 200);
        frame.add(p);
        frame.setSize(450, 400);
        frame.setLocation(250, 150);
        frame.show();
    }

    private void Update() {
        if (flag == 1) {
            l1 = new Line2D.Double(v1, v2);
            l2 = new Line2D.Double(v2, v3);
            l3 = new Line2D.Double(v3, v1);
        } else if (flag == 2) {
            l3 = new Line2D.Double(v1, v3);
            l2 = new Line2D.Double(v2, v3);
            l1 = new Line2D.Double(v1, v2);
        } else if (flag == 3) {
            l3 = new Line2D.Double(v1, v3);
            l2 = new Line2D.Double(v2, v3);
            l1 = new Line2D.Double(v1, v2);
        }
        ConstAngRecto();
    }

    public void ConstAngRecto() {
        double k1 = 0;
        double k2 = 0;
        vertice pt1, pt2, pt5;
        if (longitud(v1, v2) != 0 && longitud(v3, v2) != 0) {
            k1 = (longitud(v1, v2) < longitud(v3, v2)) ? longitud(v2, v3) / longitud(v2, v1) : 1;
            k2 = (longitud(v3, v2) < longitud(v1, v2)) ? longitud(v2, v1) / longitud(v2, v3) : 1;
        }
        if (longitud(new vertice((int) (((12 - k1) * v2.x + v1.x * k1)) / 12, (int) (((12 - k1) * v2.y + v1.y * k1)) / 12, ""), v2) > longitud(v1, v2)) {
            pt1 = new vertice((int) v1.x, (int) v1.y, "");
        } else {
            pt1 = new vertice((int) (((12 - k1) * v2.x + v1.x * k1)) / 12, (int) (((12 - k1) * v2.y + v1.y * k1)) / 12, "");
        }
        if (longitud(new vertice((int) (((12 - k2) * v2.x + v3.x * k2)) / 12, (int) (((12 - k2) * v2.y + v3.y * k2)) / 12, ""), v2) > longitud(v3, v2)) {
            pt2 = new vertice((int) v3.x, (int) v3.y, "");
        } else {
            pt2 = new vertice((int) (((12 - k2) * v2.x + v3.x * k2)) / 12, (int) (((12 - k2) * v2.y + v3.y * k2)) / 12, "");
        }
        pt5 = new vertice((int) (pt1.x + pt2.x - v2.x), (int) (pt1.y + pt2.y - v2.y), "");

        l5 = new Line2D.Double(pt1, pt5);
        l6 = new Line2D.Double(pt2, pt5);
    }

    public double longitud(vertice a, vertice b) {
        return Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
    }

    /*----------------------------------*/
}
