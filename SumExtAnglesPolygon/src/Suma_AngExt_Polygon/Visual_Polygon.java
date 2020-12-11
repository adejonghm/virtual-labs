package Suma_AngExt_Polygon;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.LinkedList;
import javax.swing.*;

/**
 *
 * @author adejongh
 */
public class Visual_Polygon extends JPanel {

    private LinkedList<Color> colores;
    private LinkedList<Double> angulos_int, angulos_partida;
    private LinkedList<Polygon> Poligonos_V, Poligonos_L, Poligonos_E;
    private LinkedList<VerticeMedio> VerticesM;
    private Polygon P;
    private grafo poligono;
    private int flag, Radio;

    public Visual_Polygon(int n) {
        setBorder(BorderFactory.createTitledBorder("Suma de los ángulos exteriores de un Polígono Convexo"));
        setBackground(Color.white);

        colores = new LinkedList<Color>();
        Color[] arraycolor = new Color[]{Color.red, Color.blue, Color.green, Color.orange, Color.pink, Color.cyan, Color.magenta};
        for (int i = 0; i < arraycolor.length; i++) {
            colores.add(arraycolor[i]);
        }
        VerticesM = new LinkedList<VerticeMedio>();
        Radio = 50;
        poligono = new grafo(n);
        for (int i = 0; i < poligono.getAristas().size(); i++) {
            vertice v = poligono.getAristas().get(i).getVinicial();
            vertice w = poligono.getAristas().get(i).getVfinal();
            VerticeMedio vm = new VerticeMedio(VerticeMedio(v, w), "");
            VerticesM.add(vm);
        }
        flag = -1;
        Poligonos_V = new LinkedList<Polygon>();
        Poligonos_L = new LinkedList<Polygon>();
        Poligonos_E = new LinkedList<Polygon>();
        for (int i = 0; i < poligono.getVertices().size(); i++) {
            Polygon ptem = new Polygon(poligono.GetX(i), poligono.GetY(i), poligono.getCantVertices() - 1);
            Poligonos_V.add(ptem);
        }
        for (int i = 0; i < poligono.getVertices().size(); i++) {
            if (i != poligono.getCantVertices() - 1) {
                Polygon ptem = new Polygon(poligono.GetX(i, i + 1), poligono.GetY(i, i + 1), poligono.getCantVertices() - 2);
                Poligonos_L.add(ptem);
            } else {
                Polygon ptem = new Polygon(poligono.GetX(i, 0), poligono.GetY(i, 0), poligono.getCantVertices() - 2);
                Poligonos_L.add(ptem);
            }
        }
        for (int i = 0; i < poligono.getVertices().size(); i++) {

            int next = poligono.AdyacentesA(i).get(0);
            int back = poligono.AdyacentesA(i).get(1);
            Line2D.Double la = poligono.getAristas().get(next);
            Line2D.Double lb = poligono.getAristas().get(back);
            Polygon ptem = new Polygon(new int[]{(int) poligono.getVertices().get(i).x, (int) poligono.getVertices().get(next).x, (int) intersccionLineas(la, lb).x}, new int[]{(int) poligono.getVertices().get(i).y, (int) poligono.getVertices().get(next).y, (int) intersccionLineas(la, lb).y}, 3);
            Poligonos_E.add(ptem);


        }
        angulos_int = new LinkedList<Double>();

        angulos_partida = new LinkedList<Double>();
        P = new Polygon(poligono.GetX(), poligono.GetY(), poligono.getCantVertices());
        MouseListener pintar = new MouseListener() {

            public void mouseClicked(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {
                boolean selected = false;
                for (int i = 0; i < poligono.getVertices().size(); i++) {
                    if (poligono.getVertices().get(i).contains(new vertice(e.getX(), e.getY(), ""))) {
                        flag = i;
                        selected = true;
                        break;
                    }
                }
                for (int i = 0; i < VerticesM.size() && !selected; i++) {
                    if (VerticesM.get(i).contains(new vertice(e.getX(), e.getY(), ""))) {
                        flag = i + poligono.getCantVertices();
                        break;
                    } else {
                        flag = -1;
                    }
                }
                repaint();
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
                if (flag != -1) {
                    if (flag < poligono.getCantVertices()) {
                        int next = poligono.AdyacentesA(flag).get(0);
                        int back = poligono.AdyacentesA(flag).get(1);
                        vertice v2 = poligono.getVertices().get(next);
                        vertice v6 = poligono.getVertices().get(back);
                        vertice v3 = poligono.getVertices().get(poligono.AdyacentesA(next).get(0));
                        vertice v5 = poligono.getVertices().get(poligono.AdyacentesA(back).get(1));
                        Polygon p1 = Poligonos_V.get(flag);
                        if (ValidarDaigonales(new vertice(e.getX(), e.getY(), ""), flag) && Distancia(new vertice(e.getX(), e.getY(), ""), v2, v6) > 10 && (!p1.contains(new vertice(e.getX(), e.getY(), "")) && !(AnguloXVertices(v3, v2, new vertice(e.getX(), e.getY(), "")) > 178 || AnguloXVertices(v5, v6, new vertice(e.getX(), e.getY(), "")) > 178) && Validar(new vertice(e.getX(), e.getY(), ""), v6, v2, v3) && Validar(new vertice(e.getX(), e.getY(), ""), v2, v5, v6) && (Lado(v2, new vertice(e.getX(), e.getY(), "")) > 55 && Lado(v6, new vertice(e.getX(), e.getY(), "")) > 55))) {
                            poligono.getVertices().set(flag, new vertice(e.getX(), e.getY(), (char) Integer.parseInt((65 + flag) + "") + ""));
                        }

                    } else if (flag >= poligono.getCantVertices()) {
                        int pos = flag % poligono.getCantVertices();
                        int next = poligono.AdyacentesA(pos).get(0);
                        int back = poligono.AdyacentesA(pos).get(1);
                        vertice v1 = poligono.getVertices().get(pos);
                        vertice v2 = poligono.getVertices().get(next);
                        vertice v6 = poligono.getVertices().get(back);
                        vertice v3 = poligono.getVertices().get(poligono.AdyacentesA(next).get(0));
                        vertice v5 = poligono.getVertices().get(poligono.AdyacentesA(back).get(1));
                        vertice v4 = poligono.getVertices().get(poligono.AdyacentesA(poligono.AdyacentesA(next).get(0)).get(0));

                        Polygon pl12 = Poligonos_L.get(pos);
                        Polygon pl1 = Poligonos_V.get(pos);
                        Polygon pl2 = Poligonos_V.get(next);
                        if (!pl12.contains(new vertice(e.getX(), e.getY(), "")) && Distancia(new vertice(e.getX(), e.getY(), ""), v3, v6) > 10) {
                            double x = VerticesM.get(pos).x - e.getX();
                            double y = VerticesM.get(pos).y - e.getY();
                            double lado6 = Lado(new vertice((int) (v1.x - x), (int) (v1.y - y), ""), v6);
                            double lado2 = Lado(new vertice((int) (v2.x - x), (int) (v2.y - y), ""), v3);
                            double h1 = Distancia(new vertice((int) (v1.x - x), (int) (v1.y - y), "B"), new vertice((int) (v2.x - x), (int) (v2.y - y), "B"), v6);
                            double h2 = Distancia(new vertice((int) (v2.x - x), (int) (v2.y - y), "B"), new vertice((int) (v1.x - x), (int) (v1.y - y), "B"), v3);

                            if (AnguloXVertices(new vertice((int) (v2.x - x), (int) (v2.y - y), ""), v3, v4) < 178 && AnguloXVertices(new vertice((int) (v1.x - x), (int) (v1.y - y), ""), v6, v5) < 178 && Validar(new vertice((int) (v2.x - x), (int) (v2.y - y), "B"), v1, v3, v4) && Validar(new vertice((int) (v1.x - x), (int) (v1.y - y), "B"), v2, v5, v6) && (lado2 > 55 && lado6 > 55) && (h1 > 10 && h2 > 10 && !pl1.contains(new vertice((int) (v1.x - x), (int) (v1.y - y), "")) && !pl2.contains(new vertice((int) (v2.x - x), (int) (v2.y - y), "")))) {
                                VerticesM.set(pos, new VerticeMedio(new Point2D.Double(e.getX(), e.getY()), VerticesM.get(pos).getNombre()));
                                poligono.getVertices().set(pos, new vertice((int) (v1.x - x), (int) (v1.y - y), poligono.getVertices().get(pos).getNombre()));
                                poligono.getVertices().set(next, new vertice((int) (v2.x - x), (int) (v2.y - y), poligono.getVertices().get(next).getNombre()));
                            }
                        }
                    }
                }
                repaint();
            }

            public void mouseMoved(MouseEvent e) {
            }
        };
        addMouseMotionListener(pintMove);

    }

    public grafo getPoligono() {
        return poligono;
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

        g.setFont(new Font("Tahoma", Font.BOLD, 12));
        g2.setStroke(new BasicStroke(2.0f));
        Update();
        /*Aristas*/
        for (int i = 0; i < poligono.getCantVertices(); i++) {
            poligono.getAristas().get(i).pintar(g2);
        }
        /*Vertices*/
        for (int i = 0; i < poligono.getCantVertices(); i++) {
            poligono.getVertices().get(i).paint(g2);
        }
        for (int i = 0; i < poligono.getCantVertices(); i++) {
            VerticesM.get(i).paint(g2);
        }
        /*Angulos*/
//        g2.setStroke(new BasicStroke(2.0f));
        for (int i = 0; i < poligono.getCantVertices(); i++) {
            g2.setColor(colores.get(i));
            double angp = angulos_partida.get(i);
            double angi = angulos_int.get(i);
            g2.drawArc((int) (poligono.getVertices().get(i).x - Radio / 2), (int) (poligono.getVertices().get(i).y - Radio / 2), (int) Radio, (int) Radio, (int) angp, (int) angi - 180);
        }
        g2.setStroke(new BasicStroke());
        ImprimirDatos(g2);

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Suma de los ángulos exteriores de un Polígono Convexo") {

            @Override
            public void frameInit() {
                super.frameInit();
                setDefaultCloseOperation(EXIT_ON_CLOSE);
            }
        };
        JPanel p = new Visual_Polygon(6);
        frame.add(p);
        frame.setSize(830, 550);
        frame.show();
    }

    private void Update() {
        if (flag != -1) {
            if (flag < poligono.getCantVertices()) {
                vertice vc = poligono.getVertices().get(flag);
                vertice vi = poligono.getVertices().get(poligono.AdyacentesA(flag).get(0));
                vertice vd = poligono.getVertices().get(poligono.AdyacentesA(flag).get(1));
                Arista la = new Arista(vc, vi, poligono.getCantVertices());
                Arista lb = new Arista(vd, vc, poligono.getCantVertices());

                poligono.getAristas().set(flag, la);
                VerticesM.set(flag, new VerticeMedio(PuntoMedio(vc, vi), ""));
                if (flag != 0) {
                    poligono.getAristas().set(flag - 1, lb);
                    VerticesM.set(flag - 1, new VerticeMedio(PuntoMedio(vc, vd), ""));
                } else {
                    poligono.getAristas().set(poligono.getCantVertices() - 1, lb);
                    VerticesM.set(poligono.getCantVertices() - 1, new VerticeMedio(PuntoMedio(vc, vd), ""));
                }

            } else {
                int pos = flag - poligono.getCantVertices();
                vertice v1 = poligono.getVertices().get(pos);
                vertice v2 = poligono.getVertices().get(poligono.AdyacentesA(pos).get(0));
                vertice v6 = poligono.getVertices().get(poligono.AdyacentesA(pos).get(1));
                vertice v3 = poligono.getVertices().get(poligono.AdyacentesA(poligono.AdyacentesA(pos).get(0)).get(0));
                Arista l1 = new Arista(v1, v2, poligono.getCantVertices());
                Arista l2 = new Arista(v2, v3, poligono.getCantVertices());
                Arista l6 = new Arista(v6, v1, poligono.getCantVertices());
                VerticeMedio vm23 = new VerticeMedio(PuntoMedio(v2, v3), "");
                VerticeMedio vm61 = new VerticeMedio(PuntoMedio(v6, v1), "");
                poligono.getAristas().set(pos, l1);
                poligono.getAristas().set(poligono.AdyacentesA(pos).get(0), l2);
                poligono.getAristas().set(poligono.AdyacentesA(pos).get(1), l6);
                VerticesM.set(poligono.AdyacentesA(pos).get(0), vm23);
                VerticesM.set(poligono.AdyacentesA(pos).get(1), vm61);
            }
            Poligonos_E.clear();
            Poligonos_V.clear();
            Poligonos_L.clear();
            for (int i = 0; i < poligono.getVertices().size(); i++) {
                Polygon ptem = new Polygon(poligono.GetX(i), poligono.GetY(i), poligono.getCantVertices() - 1);
                Poligonos_V.add(ptem);
            }
            for (int i = 0; i < poligono.getVertices().size(); i++) {
                if (i != poligono.getCantVertices() - 1) {
                    Polygon ptem = new Polygon(poligono.GetX(i, i + 1), poligono.GetY(i, i + 1), poligono.getCantVertices() - 2);
                    Poligonos_L.add(ptem);
                } else {
                    Polygon ptem = new Polygon(poligono.GetX(i, 0), poligono.GetY(i, 0), poligono.getCantVertices() - 2);
                    Poligonos_L.add(ptem);
                }
            }
            for (int i = 0; i < poligono.getVertices().size(); i++) {

                int next = poligono.AdyacentesA(i).get(0);
                int back = poligono.AdyacentesA(i).get(1);
                Line2D.Double la = poligono.getAristas().get(next);
                Line2D.Double lb = poligono.getAristas().get(back);
                Polygon ptem = new Polygon(new int[]{(int) poligono.getVertices().get(i).x, (int) poligono.getVertices().get(next).x, (int) intersccionLineas(la, lb).x}, new int[]{(int) poligono.getVertices().get(i).y, (int) poligono.getVertices().get(next).y, (int) intersccionLineas(la, lb).y}, 3);
                Poligonos_E.add(ptem);
            }
            calcularAngulos();
        }
        P = new Polygon(poligono.GetX(), poligono.GetY(), poligono.getCantVertices());
        calcularAngulos();

    }

    private void calcularAngulos() {
        angulos_int.clear();
        angulos_partida.clear();
        for (int i = 0; i < poligono.getVertices().size(); i++) {
            vertice vc = poligono.getVertices().get(i);
            vertice vi = poligono.getVertices().get(poligono.AdyacentesA(i).get(0));
            vertice vd = poligono.getVertices().get(poligono.AdyacentesA(i).get(1));
            double ang = AnguloXVertices(vi, vc, vd);

            angulos_int.add(ang);
        }
        for (int i = 0; i < poligono.getVertices().size(); i++) {
            vertice v1 = poligono.getVertices().get(i);
            vertice v6 = poligono.getVertices().get(poligono.AdyacentesA(i).get(1));
            double ang16 = 0;
            if (v6.x != v1.x && v6.y != v1.y) {
                double p61 = (v6.y - v1.y) / (v6.x - v1.x);
                double angulotemporal1 = 360 - (180 + (Math.atan(p61) * 180 / Math.PI)) % 180;
                double angulotemporal2 = angulotemporal1 - 180;
                ang16 = (v6.y > v1.y) ? angulotemporal1 : angulotemporal2;

            } else if (v6.x != v1.x) {
                ang16 = (v1.x < v6.x) ? 0 : 180;
            } else {
                ang16 = (v6.y > v1.y) ? 270 : 90;

            }
            angulos_partida.add(ang16);
        }
    }

    private double Distancia(vertice a, vertice b, vertice c) {
        double h = 0;
        if (c.x == b.x) {
            h = Math.abs(a.x - b.x);
        } else {
            double p = (b.y - c.y) / (b.x - c.x);
            double A = -p;
            double C = p * b.x - b.y;
            double B = 1;
            h = Math.abs(A * a.x + B * a.y + C) / (Math.sqrt(A * A + B * B));
        }
        return h;
    }

    private double Lado(vertice a, vertice b) {
        return Math.sqrt(Math.pow(a.y - b.y, 2) + Math.pow(a.x - b.x, 2));
    }

    private double Angulo(double a, double b, double c) {
        return Math.toDegrees(Math.acos((b * b + c * c - a * a) / (2 * b * c)));
    }

    private double AnguloXVertices(vertice vi, vertice vc, vertice vd) {
        double a = Lado(vi, vd);
        double b = Lado(vi, vc);
        double c = Lado(vd, vc);
        return Angulo(a, b, c);
    }

    private Point2D.Double PuntoMedio(vertice va, vertice vb) {
        return new Point2D.Double((va.x + vb.x) / 2, (va.y + vb.y) / 2);
    }

    private Point2D.Double Punto_InterAB(vertice a, double pa, double pb, vertice b) {
        double x = 0, y = 0;
        if (pa != Double.POSITIVE_INFINITY && pb != Double.POSITIVE_INFINITY) {
            x = (b.y - a.y + pa * a.x - pb * b.x) / (pa - pb);
            y = pa * (x - a.x) + a.y;
        } else if (pa == Double.POSITIVE_INFINITY) {
            x = a.x;
            y = pb * (x - b.x) + b.y;
        } else {
            x = b.x;
            y = pa * (x - a.x) + a.y;
        }
        return new Point2D.Double(x, y);
    }

    private double Pendiente(vertice va, vertice vb) throws ArithmeticException {
        if (va.x == vb.x) {
            throw new ArithmeticException();
        }
        return (va.y - vb.y) / (va.x - vb.x);
    }

    private boolean Validar(vertice va, vertice vb, vertice wa, vertice wb) {
        double pv = 0, pw = 0;
        try {
            pv = Pendiente(va, vb);
        } catch (ArithmeticException e) {
            pv = Double.POSITIVE_INFINITY;
        }
        try {
            pw = Pendiente(wa, wb);
        } catch (ArithmeticException e) {
            pw = Double.POSITIVE_INFINITY;
        }
        Point2D.Double interseccion = Punto_InterAB(va, pv, pw, wa);

        return !ValidarAuxiliar(va, vb, interseccion);
    }

    private boolean ValidarAuxiliar(vertice a, vertice b, Point2D.Double c) {
        if (a.x == b.x) {
            if ((c.y <= a.y && c.y >= b.y) || (c.y >= a.y && c.y <= b.y)) {
                return true;
            }
        } else if ((c.x <= a.x && c.x >= b.x) || (c.x >= a.x && c.x <= b.x)) {
            return true;
        }
        return false;
    }

    private vertice VerticeMedio(vertice a, vertice b) {
        return new vertice((int) PuntoMedio(a, b).x, (int) PuntoMedio(a, b).y, "");
    }

    private boolean ValidarDaigonales(vertice v, int pos) {
        Line2D.Double l = new Line2D.Double(v, poligono.getVertices().get(pos));
        for (int i = 0; i < poligono.getAristas().size(); i++) {

            if (pos == i || poligono.AdyacentesA(pos).get(1) == i) {
                continue;
            }
            if (poligono.getAristas().get(i).intersectsLine(l)) {
                return false;
            }

        }
        return true;
    }

    public Point2D.Double intersccionLineas(Line2D la, Line2D lb) {
        vertice a = new vertice((int) la.getP1().getX(), (int) la.getP1().getY(), "");
        vertice b = new vertice((int) lb.getP1().getX(), (int) lb.getP1().getY(), "");
        vertice a1 = new vertice((int) la.getP2().getX(), (int) la.getP2().getY(), "");
        vertice b1 = new vertice((int) lb.getP2().getX(), (int) lb.getP2().getY(), "");
        double pa = 0, pb = 0;
        try {
            pa = Pendiente(a, a1);
        } catch (ArithmeticException e) {
            pa = Double.POSITIVE_INFINITY;
        }
        try {
            pb = Pendiente(b, b1);
        } catch (ArithmeticException e) {
            pb = Double.POSITIVE_INFINITY;
        }

        return Punto_InterAB(a, pa, pb, b);
    }

    public void Borrar(Graphics2D g2) {
        g2.setColor(Color.white);
        g2.fillRect(10, 15, 795, 420);
    }

    public void reBuild(int n) {
        poligono = new grafo(n);
        Poligonos_E = new LinkedList<Polygon>();
        Poligonos_L = new LinkedList<Polygon>();
        Poligonos_V = new LinkedList<Polygon>();
        angulos_int = new LinkedList<Double>();
        angulos_partida = new LinkedList<Double>();
        VerticesM = new LinkedList<VerticeMedio>();
        for (int i = 0; i < poligono.getAristas().size(); i++) {
            vertice v = poligono.getAristas().get(i).getVinicial();
            vertice w = poligono.getAristas().get(i).getVfinal();
            VerticeMedio vm = new VerticeMedio(VerticeMedio(v, w), "");
            VerticesM.add(vm);
        }
    }

    public void ImprimirDatos(Graphics2D g2) {
        if (poligono.getCantVertices() == 5) {
            g2.setColor(Color.black);
            g2.setStroke(new BasicStroke(1.0f));

            g2.drawString("FAE +   GBA +   HCB +   IDC +   JED = 360º", 416, 90);
            g2.drawLine(405, 89, 412, 81);
            g2.drawLine(405, 89, 412, 89);            
            g2.drawLine(454, 89, 461, 81);
            g2.drawLine(454, 89, 461, 89);            
            g2.drawLine(505, 89, 512, 81);
            g2.drawLine(505, 89, 512, 89);            
            g2.drawLine(556, 89, 563, 81);
            g2.drawLine(556, 89, 563, 89);
            
            g2.drawLine(605, 89, 611, 81);
            g2.drawLine(605, 89, 611, 89);

            g2.drawRect(400, 20, 405, 80);
            double ang1 = angulos_int.get(0);
            double ang2 = angulos_int.get(1);
            double ang3 = angulos_int.get(2);
            double ang4 = angulos_int.get(3);
            double ang5 = angulos_int.get(4);
            g2.drawString("FAE  = " + (int) (180 - ang1) + "º", 416, 35);
            g2.drawString("GBA = " + (int) (180 - ang2) + "º", 416, 55);
            g2.drawString("HCB  = " + (int) (180 - ang3) + "º", 521, 35);
            g2.drawString("IDC  = " + (int) (180 - ang4) + "º", 521, 55);
            g2.drawString("JED = " + (int) (180 - ang5) + "º", 626, 35);



            /*Simbolos*/
            g2.setStroke(new BasicStroke(1f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1f, new float[]{4}, 0f));
            g2.drawLine(415, 70, 790, 70);
            g2.setStroke(new BasicStroke());             
            g2.drawLine(405, 34, 412, 26);
            g2.drawLine(405, 34, 412, 34);
            g2.drawLine(405, 54, 412, 46);
            g2.drawLine(405, 54, 412, 54);
            g2.drawLine(510, 34, 517, 26);
            g2.drawLine(510, 34, 517, 34);
            g2.drawLine(510, 54, 517, 46);
            g2.drawLine(510, 54, 517, 54);

            g2.drawLine(615, 34, 622, 26);
            g2.drawLine(615, 34, 622, 34);
        }
        if (poligono.getCantVertices() == 6) {
            g2.setColor(Color.black);
            g2.setStroke(new BasicStroke(1.0f));

            g2.drawString("GAF +   HBA +   ICB +   JDC +   KED +   LFE = 360º", 416, 90);
            g2.drawLine(405, 89, 412, 81);
            g2.drawLine(405, 89, 412, 89);			
            g2.drawLine(454, 89, 461, 81);
            g2.drawLine(454, 89, 461, 89);			
            g2.drawLine(506, 89, 513, 81);
            g2.drawLine(506, 89, 513, 89);			
            g2.drawLine(553, 89, 560, 81);
            g2.drawLine(553, 89, 560, 89);			
            g2.drawLine(603, 89, 610, 81);
            g2.drawLine(603, 89, 610, 89);			
            g2.drawLine(653, 89, 660, 81);
            g2.drawLine(653, 89, 660, 89);

            g2.drawRect(400, 20, 405, 80);
            double ang1 = angulos_int.get(0);
            double ang2 = angulos_int.get(1);
            double ang3 = angulos_int.get(2);
            double ang4 = angulos_int.get(3);
            double ang5 = angulos_int.get(4);
            double ang6 = angulos_int.get(5);
            g2.drawString("GAF  = " + (int) (180 - ang1) + "º", 416, 35);
            g2.drawString("HBA = " + (int) (180 - ang2) + "º", 416, 55);
            g2.drawString("ICB  = " + (int) (180 - ang3) + "º", 521, 35);
            g2.drawString("JDC  = " + (int) (180 - ang4) + "º", 521, 55);
            g2.drawString("KED = " + (int) (180 - ang5) + "º", 626, 35);
            g2.drawString("LFE  = " + (int) (180 - ang6) + "º", 626, 55);


            /*Simbolos*/
            g2.setStroke(new BasicStroke(1f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1f, new float[]{4}, 0f));
            g2.drawLine(415, 70, 790, 70);           
            g2.setStroke(new BasicStroke());
            g2.drawLine(405, 35, 412, 27);
            g2.drawLine(405, 35, 412, 35);
            g2.drawLine(405, 55, 412, 47);
            g2.drawLine(405, 55, 412, 55);
            g2.drawLine(510, 35, 517, 27);
            g2.drawLine(510, 35, 517, 35);
            g2.drawLine(510, 55, 517, 47);
            g2.drawLine(510, 55, 517, 55);
            g2.drawLine(615, 35, 622, 27);
            g2.drawLine(615, 35, 622, 35);
            g2.drawLine(615, 55, 622, 47);
            g2.drawLine(615, 55, 622, 55);

        }
        if (poligono.getCantVertices() == 7) {
            g2.setColor(Color.black);
            g2.setStroke(new BasicStroke(1.0f));

            g2.drawString("HAG +   IBA +   JCB +   KDC +   LED +   MFE +   NGF= 360º", 416, 90);
            g2.drawLine(405, 89, 412, 81);
            g2.drawLine(405, 89, 412, 89);
            g2.drawLine(458, 89, 465, 81);
            g2.drawLine(458, 89, 465, 89);
            g2.drawLine(506, 89, 513, 81);
            g2.drawLine(506, 89, 513, 89);
            g2.drawLine(553, 89, 560, 81);
            g2.drawLine(553, 89, 560, 89);            
            g2.drawLine(604, 89, 611, 81);
            g2.drawLine(604, 89, 611, 89);            
            g2.drawLine(654, 89, 661, 81);
            g2.drawLine(654, 89, 661, 89);

            g2.drawLine(704, 89, 711, 81);
            g2.drawLine(704, 89, 711, 89);

            g2.drawRect(400, 20, 405, 80);
            double ang1 = angulos_int.get(0);
            double ang2 = angulos_int.get(1);
            double ang3 = angulos_int.get(2);
            double ang4 = angulos_int.get(3);
            double ang5 = angulos_int.get(4);
            double ang6 = angulos_int.get(5);
            double ang7 = angulos_int.get(6);
            g2.drawString("HAG  = " + (int) (180 - ang1) + "º", 416, 35);
            g2.drawString("IBA = " + (int) (180 - ang2) + "º", 416, 55);
            g2.drawString("JCB  = " + (int) (180 - ang3) + "º", 521, 35);
            g2.drawString("KDC  = " + (int) (180 - ang4) + "º", 521, 55);
            g2.drawString("LED = " + (int) (180 - ang5) + "º", 626, 35);
            g2.drawString("MFE  = " + (int) (180 - ang6) + "º", 626, 55);
            g2.drawString("NGF  = " + (int) (180 - ang7) + "º", 731, 35);
//

            /*Simbolos*/
            g2.setStroke(new BasicStroke(1f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1f, new float[]{4}, 0f));
            g2.drawLine(415, 70, 790, 70);            
            g2.setStroke(new BasicStroke());            
            g2.drawLine(405, 34, 412, 26);
            g2.drawLine(405, 34, 412, 34);            
            g2.drawLine(405, 54, 412, 46);
            g2.drawLine(405, 54, 412, 54);            
            g2.drawLine(510, 34, 517, 26);
            g2.drawLine(510, 34, 517, 34);            
            g2.drawLine(510, 54, 517, 46);
            g2.drawLine(510, 54, 517, 54);            
            g2.drawLine(615, 34, 622, 26);
            g2.drawLine(615, 34, 622, 34);            
            g2.drawLine(615, 54, 622, 46);
            g2.drawLine(615, 54, 622, 54);

            g2.drawLine(720, 34, 727, 26);
            g2.drawLine(720, 34, 727, 34);
        }
    }
}
