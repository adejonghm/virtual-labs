package Mediatriz_Arco;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.*;
import javax.swing.*;

public class Mediatriz_Arco extends JPanel {

    private vertice centro,  v1,  v2,  v3,  E1,  ptoMedioA,  ext1,  ext3;
    private Line2D l1,  l2,  l3,  l4,  l5,  media1;
    private Arc2D circunferencia;
    private int flag = 0,  tt = 1,  bandera;
    private double radio,  Pm1,  StartAng;
    private Polygon polig;

    public Mediatriz_Arco(int x1, int y1, int x2, int y2, int x3, int y3, int xcentro, int ycentro) {
        setPreferredSize(new Dimension(200, 200));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createTitledBorder("Relación Mediatriz - Arco"));

        /** Mi constructor **/
        centro = new vertice(xcentro, ycentro, "O");
        radio = 100;
        circunferencia = new Arc2D.Double(xcentro - radio, ycentro - radio, 2 * radio, 2 * radio, 0, 360, Arc2D.CHORD);
        Pm1 = 0;
        this.v1 = new vertice(x1, y1, "A");
        this.v2 = new vertice(x2, y2, "B");
        this.E1 = new vertice(x3, y3, "M");
        this.v3 = new vertice((int) (2 * centro.x - E1.x), (int) (2 * centro.y - E1.y), "N");

        l1 = new Line2D.Double(v1, v2);
        l2 = new Line2D.Double(v1, centro);
        l3 = new Line2D.Double(v2, centro);
        ext1 = new vertice((int) (2 * E1.x - centro.x), (int) (2 * E1.y - centro.y), "1");
        ext3 = new vertice((int) (2 * centro.x - ext1.x), (int) (2 * centro.y - ext1.y), "3");
        polig = new Polygon(new int[]{(int) centro.x, (int) v1.x, (int) ext3.x, (int) v2.x}, new int[]{(int) centro.y, (int) v1.y, (int) ext3.y, (int) v2.y}, 4);

        Update();

        MouseListener pintar = new MouseListener() {

            public void mouseClicked(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {
                if (v1.contains(new vertice(e.getX(), e.getY(), v1.getNombre()))) {
                    flag = 1;
                } else if (v2.contains(new vertice(e.getX(), e.getY(), v2.getNombre()))) {
                    flag = 2;
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
                        double p = (e.getY() - centro.y) / (e.getX() - centro.y);
                        double x = (t * radio / Math.sqrt(p * p + 1) + centro.x);
                        double y = p * (x - centro.x) + centro.y;
                        v1 = new vertice((int) x, (int) y, "A");
                    } else {
                        int t = (e.getY() > centro.y) ? 1 : -1;
                        double x = e.getX();
                        double y = centro.y + t * radio;
                        v1 = new vertice((int) x, (int) y, "A");
                    }
                    v3 = new vertice((int) (2 * centro.x - E1.x), (int) (2 * centro.y - E1.y), "N");
                } else if (flag == 2) {
                    if (e.getX() != centro.x) {
                        int t = (e.getX() > centro.x) ? 1 : -1;
                        double p = (e.getY() - centro.y) / (e.getX() - centro.y);
                        double x = (t * radio / Math.sqrt(p * p + 1) + centro.x);
                        double y = p * (x - centro.x) + centro.y;
                        v2 = new vertice((int) x, (int) y, "B");
                    } else {
                        int t = (e.getY() > centro.y) ? 1 : -1;
                        double x = e.getX();
                        double y = centro.y + t * radio;
                        v2 = new vertice((int) x, (int) y, "B");
                    }
                    v3 = new vertice((int) (2 * centro.x - E1.x), (int) (2 * centro.y - E1.y), "N");
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
        // <-- pintando la circunferencia -->
//        g2.setStroke(new BasicStroke(2.0f));
//        g2.setColor(Color.black);
//        g2.draw(circunferencia);

        // <-- Figura -->
        g2.setColor(new Color(9, 69, 141));
        g2.draw(l1);
        g2.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 2f, new float[]{4}, 0f));
        g2.setColor(new Color(150, 149, 148));
        g2.draw(media1);
        g2.draw(l2);
        g2.draw(l3);
        g2.setStroke(new BasicStroke());
        g2.setColor(new Color(171, 43, 52));
        g2.draw(l4);
        g2.draw(l5);

        //Repintar Arcos Iguales
        g2.setStroke(new BasicStroke(2.0f));
        AngPartida();
        g2.setColor(Color.green);
        g2.drawArc((int) (centro.x - radio), (int) (centro.y - radio), (int) radio * 2, (int) radio * 2, (int) StartAng, (int) Math.toDegrees(CalcAnguloAON()));
        g2.setColor(Color.orange);
        g2.drawArc((int) (centro.x - radio), (int) (centro.y - radio), (int) radio * 2, (int) radio * 2, (int) StartAng + 180, (360 - 2 * Math.abs((int) Math.toDegrees(CalcAnguloAON()))) / 2);
        g2.setColor(Color.green);
        g2.drawArc((int) (centro.x - radio), (int) (centro.y - radio), (int) radio * 2, (int) radio * 2, (int) StartAng, -(int) Math.toDegrees(CalcAnguloAON()));
        g2.setColor(Color.orange);
        g2.drawArc((int) (centro.x - radio), (int) (centro.y - radio), (int) radio * 2, (int) radio * 2, (int) StartAng + 180, -(360 - 2 * Math.abs((int) Math.toDegrees(CalcAnguloAON()))) / 2);

        // <-- Puntos -->
        g2.setColor(Color.black);
        v1.paint(g2);
        v2.paint(g2);
        g2.setColor(new Color(171, 43, 52));
        E1.paint(g2);
        g2.setColor(new Color(171, 43, 52));
        ptoMedioA.paint(g2);
        g2.setColor(new Color(171, 43, 52));
        v3.paint(g2);        
        centro.paint(g2);
        // ext3.paint(g2);

        // <-- Datos -->
        g2.setStroke(new BasicStroke(1.0f));
        g2.setColor(Color.black);
        g2.drawRect(400, 20, 175, 150);
        g2.drawString("OCA = 90º", 425, 41);
        g2.drawString("AC = " + Math.ceil((longitud(v1, ptoMedioA) / 37) * 10) / 10 + " cm", 410, 60);
        g2.drawString("BC = " + Math.ceil((longitud(v1, ptoMedioA) / 37) * 10) / 10 + " cm", 410, 80);
        g2.drawString("Longitud de AM = " + Math.ceil((CalcAnguloAOM() * radio / 37) * 10) / 10 + " cm", 410, 100);
        g2.drawString("Longitud de BM = " + Math.ceil((CalcAnguloAOM() * radio / 37) * 10) / 10 + " cm", 410, 120);
        g2.drawString("Longitud de AN = " + Math.ceil((CalcAnguloAON() * radio / 37) * 10) / 10 + " cm", 410, 140);
        g2.drawString("Longitud de BN = " + Math.ceil((CalcAnguloAON() * radio / 37) * 10) / 10 + " cm", 410, 160);

        // <-- Los Simbólos -->
        g2.drawLine(410, 40, 420, 32);
        g2.drawLine(410, 40, 420, 40);
        g2.drawLine(410, 48, 425, 48);
        g2.drawLine(410, 68, 425, 68);
        g2.drawArc(492, 85, 15, 12, 15, 155);
        g2.drawArc(490, 105, 16, 12, 15, 155);
        g2.drawArc(490, 125, 15, 12, 15, 155);
        g2.drawArc(490, 145, 15, 12, 15, 155);

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Relación Mediatriz - Arco") {

            @Override
            public void frameInit() {
                super.frameInit();
                setDefaultCloseOperation(EXIT_ON_CLOSE);
            }
        };
        JPanel p = new Mediatriz_Arco(100, 200, 200, 100, 271, 271, 200, 200);
        frame.add(p);
        frame.setLocation(250, 100);
        frame.setSize(600, 400);
        frame.show();
    }

    private void Update() {
        if (flag == 1) {
            l1 = new Line2D.Double(v1, v2);
            l2 = new Line2D.Double(v1, centro);

        } else if (flag == 2) {
            l3 = new Line2D.Double(v2, centro);
            l1 = new Line2D.Double(v1, v2);
        }

        CrearMediatriz();
        polig = new Polygon(new int[]{(int) centro.x, (int) v1.x, (int) ext3.x, (int) v2.x}, new int[]{(int) centro.y, (int) v1.y, (int) ext3.y, (int) v2.y}, 4);
        ConstAngRecto();

    }

    private void CrearMediatriz() {
        ptoMedioA = new vertice((int) (v1.x + v2.x) / 2, (int) (v1.y + v2.y) / 2, "C");
        /* Pendiente de la Mediatriz 1 */
        if (v1.x == v2.x && v1.y != v2.y) {
            Pm1 = 0;
        } else {
            Pm1 = (v2.y - v1.y) / (v2.x - v1.x);
            if (Pm1 != 0) {
                Pm1 = -(1 / Pm1);
            }
        }
        bandera = (ptoMedioA.x < centro.x) ? 1 : -1;
        double cx = 0;
        double cy = 0;
        if (v1.y != v2.y) {
            // System.out.println(Pm1);
            cx = bandera * radio / Math.sqrt(1 + Math.pow(Pm1, 2)) + centro.x;
            cy = Pm1 * (cx - centro.x) + centro.y;
        } else if (v1.x != v2.x) {
            cx = centro.x;
            cy = bandera * radio + centro.y;
        } else {
            // System.out.println("aquiiiii");
            cx = v1.x;
            cy = v2.y;
        }
        E1 = new vertice((int) cx, (int) cy, "M");
        if (!polig.contains(E1)) {
            ext1 = new vertice((int) (2 * E1.x - centro.x), (int) (2 * E1.y - centro.y), "1");
            ext3 = new vertice((int) (2 * centro.x - ext1.x), (int) (2 * centro.y - ext1.y), "3");
        } else {
            ext3 = new vertice((int) (2 * E1.x - centro.x), (int) (2 * E1.y - centro.y), "1");
            ext1 = new vertice((int) (2 * centro.x - ext3.x), (int) (2 * centro.y - ext3.y), "3");
        }
        media1 = new Line2D.Double(ext3, ext1);
    }

    public void ConstAngRecto() {
        double k1 = 0;
        double k2 = 0;
        vertice pt1, pt2, pt5;
        if (longitud(v1, ptoMedioA) != 0 && longitud(centro, ptoMedioA) != 0) {
            k1 = (longitud(v1, ptoMedioA) < longitud(centro, ptoMedioA)) ? longitud(ptoMedioA, centro) / longitud(ptoMedioA, v1) : 1;
            k2 = (longitud(centro, ptoMedioA) < longitud(v1, ptoMedioA)) ? longitud(ptoMedioA, v1) / longitud(ptoMedioA, centro) : 1;
        }
        if (longitud(new vertice((int) (((7 - k1) * ptoMedioA.x + v1.x * k1)) / 7, (int) (((7 - k1) * ptoMedioA.y + v1.y * k1)) / 7, ""), ptoMedioA) > longitud(v1, ptoMedioA)) {
            pt1 = new vertice((int) v1.x, (int) v1.y, "");
        } else {
            pt1 = new vertice((int) (((7 - k1) * ptoMedioA.x + v1.x * k1)) / 7, (int) (((7 - k1) * ptoMedioA.y + v1.y * k1)) / 7, "");
        }
        if (longitud(new vertice((int) (((7 - k2) * ptoMedioA.x + centro.x * k2)) / 7, (int) (((7 - k2) * ptoMedioA.y + centro.y * k2)) / 7, ""), ptoMedioA) > longitud(centro, ptoMedioA)) {
            pt2 = new vertice((int) centro.x, (int) centro.y, "");
        } else {
            pt2 = new vertice((int) (((7 - k2) * ptoMedioA.x + centro.x * k2)) / 7, (int) (((7 - k2) * ptoMedioA.y + centro.y * k2)) / 7, "");
        }
        pt5 = new vertice((int) (pt1.x + pt2.x - ptoMedioA.x), (int) (pt1.y + pt2.y - ptoMedioA.y), "");

        l4 = new Line2D.Double(pt1, pt5);
        l5 = new Line2D.Double(pt2, pt5);
    }

    public double CalcAnguloAOM() {
        double c, cos = 0;
        c = longitud(v1, E1);
        cos = (radio * radio + radio * radio - c * c) / (2 * (radio * radio));
        return Math.acos(cos);
    }

    public double CalcAnguloAON() {
        double c, cos = 0;
        c = longitud(v1, v3);
        cos = (radio * radio + radio * radio - c * c) / (2 * (radio * radio));
        return Math.acos(cos);
    }

    private void AngPartida() {
        double ang_tem = Double.POSITIVE_INFINITY;
        if (v3.x != centro.x && v3.y != centro.y ) {
            double m = (v3.y - centro.y) / (v3.x - centro.x);
            ang_tem = Math.toDegrees(Math.atan(m));
            ang_tem = (180 - ang_tem) % 180;
            ang_tem = (v3.y < centro.y) ? ang_tem : ang_tem + 180;
        } else if(v3.y != centro.y){
            ang_tem = (v3.y < centro.y) ? 90 : 270;
        }
        else{
         ang_tem = (v3.x < centro.x) ? 180 : 0;
        }
        StartAng = ang_tem;
    }

    public double longitud(vertice a, vertice b) {
        return Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
    }
    /*----------------------------------*/
}
