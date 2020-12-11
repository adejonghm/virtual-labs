/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package distanciapuntorecta;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.math.BigDecimal;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author yise
 */
public class Main extends JPanel {

    private Vertice verpen1, verpen2, p, pp, extremoA, extremoB, d;
    private Recta ejex, ejey, pendiente;
    private int flag;

    public Main(int verpen1x, int verpen1y, int verpen2x, int verpen2y, int xp, int yp, int ejexx, int ejexy, int ejexx1, int ejexy1, int ejeyx, int ejeyy, int ejeyx1, int ejeyy1, int pendientexx, int pendientexy, int pendientexx1, int pendientexy1) {

        setBackground(Color.WHITE);
        verpen1 = new Vertice(verpen1x, verpen1y, "A");
        verpen2 = new Vertice(verpen2x, verpen2y, "B");
        reflejarVerpen1();
        reflejarVerpen2();
        p = new Vertice(xp, yp, "");
        pp = new Vertice(InterseccionX(), InterseccionY(), "");
        ejex = new Recta(ejexx, ejexy, ejexx1, ejexy1, "");
        ejey = new Recta(ejeyx, ejeyy, ejeyx1, ejeyy1, "");
        pendiente = new Recta(verpen1x, verpen1y, verpen2x, verpen2y, "Pendiente");
        d = new Vertice((p.getX() + pp.getX()) / 2, (p.getY() + pp.getY()) / 2, "d");

        MouseListener pint = new MouseListener() {

            public void mouseClicked(MouseEvent e) {

                repaint();
            }

            public void mousePressed(MouseEvent e) {
                if (p.contains(new Vertice(e.getX(), e.getY(), ""))) {
                    flag = 1;
                } else if (pp.contains(new Vertice(e.getX(), e.getY(), ""))) {
                    flag = 2;
                } else if (verpen1.contains(new Vertice(e.getX(), e.getY(), ""))) {
                    flag = 3;
                } else if (verpen2.contains(new Vertice(e.getX(), e.getY(), ""))) {
                    flag = 4;
                } else {
                    flag = 0;
                }

            }

            public void mouseReleased(MouseEvent e) {
                repaint();
            }

            public void mouseEntered(MouseEvent e) {
                repaint();
            }

            public void mouseExited(MouseEvent e) {
                repaint();
            }
        };
        addMouseListener(pint);

        MouseMotionListener pintmov = new MouseMotionListener() {

            public void mouseDragged(MouseEvent e) {
                if (flag == 1) {

                    //p=new Vertice(e.getX(), (p.getY()-pp.getY())/(p.getX()-pp.getX())*(e.getX()-pp.getX())+pp.getY(), "");
                    p = new Vertice(e.getX(), e.getY(), "");
                    pp = new Vertice(pp.getX(), pp.getY(), "");
                    if (p.getY() == 0) {
                        p.y = 5;
                        reflejarVerpen1();
                        reflejarVerpen2();
                    }


                }
                if (flag == 2) {

                    pp = new Vertice(e.getX(), ((verpen2.getY() - verpen1.getY()) / (verpen2.getX() - verpen1.getX())) * (e.getX() - verpen1.getX()) + verpen1.getY(), "");
                    p = new Vertice(pp.getX() - 80, pp.getY() - 112, "");
                    if (p.getY() == 0) {
                        p.y = 5;

                        reflejarVerpen1();
                        reflejarVerpen2();
                    }

                }
                if (flag == 3) {
                    verpen1 = new Vertice(verpen1.getX(), e.getY(), "");
                    verpen2 = new Vertice(verpen1.getX() + 430, verpen1.getY() - 300, "");
                    //pp=new Vertice(verpen1.getX()+270, verpen1.getY()-188, "PP");
                    //p=new Vertice(verpen1.getX()+198, verpen1.getY()-300, "");

                    reflejarVerpen1();
                    reflejarVerpen2();
                    if (verpen1.getX() <= 0) {
                        verpen1.x = 2;
                        verpen2.x = 442;
                        pp.x = pp.getX() + 2;


                        //pp=new Vertice(verpen1.getX()+270, verpen1.getY()-188, "PP");
                        //p=new Vertice(verpen1.getX()+198, verpen1.getY()-300, "");
                    }
                    if (verpen2.getX() >= 500) {
                        verpen2.x = 498;
                        verpen1.x = 62;
                        pp.x = pp.getX() - 2;

                        //pp=new Vertice(verpen1.getX()+270, verpen1.getY()-188, "PP");
                        //p=new Vertice(verpen1.getX()+198, verpen1.getY()-300, "");
                    }
                    if (verpen2.getY() <= 0) {
                        verpen2.y = 2;
                        verpen1.y = 298;
                        pp.y = pp.getX() + 2;

                        //pp=new Vertice(verpen1.getX()+270, verpen1.getY()-188, "PP");
                        //p=new Vertice(verpen1.getX()+198, verpen1.getY()-300, "");
                    }
                    if (verpen1.getY() >= 500) {
                        verpen1.y = 498;
                        verpen2.y = 202;
                        pp.y = pp.getY() - 2;

                        //pp=new Vertice(verpen1.getX()+270, verpen1.getY()-188, "PP");
                        //p=new Vertice(verpen1.getX()+198, verpen1.getY()-300, "");
                    }
                    if (p.getY() == 0) {
                        p.y = 5;
                    }
                    repaint();
                }
                if (flag == 4) {
                    verpen2 = new Vertice(verpen2.getX(), e.getY(), "");
                    verpen1 = new Vertice(verpen2.getX() - 440, verpen2.getY() + 300, "");
                    //pp=new Vertice(verpen2.getX()-160, verpen2.getY()+110, "PP");
                    //p=new Vertice(verpen2.getX()-240, verpen2.getY(), "");
                    if (verpen1.getX() <= 0) {
                        verpen1.x = 2;
                        verpen2.x = 442;

                        //pp=new Vertice(verpen2.getX()-160, verpen2.getY()+110, "PP");
                        //p=new Vertice(verpen2.getX()-240, verpen2.getY(), "");
                    }
                    if (verpen2.getX() >= 500) {
                        verpen2.x = 498;
                        verpen1.x = 62;

                        // pp=new Vertice(verpen2.getX()-160, verpen2.getY()+110, "PP");
                        //p=new Vertice(verpen2.getX()-240, verpen2.getY(), "");
                    }
                    if (verpen2.getY() <= 0) {
                        verpen2.y = 2;
                        verpen1.y = 298;

                        // pp=new Vertice(verpen2.getX()-160, verpen2.getY()+110, "PP");
                        //p=new Vertice(verpen2.getX()-240, verpen2.getY(), "");
                    }
                    if (verpen1.getY() >= 500) {
                        verpen1.y = 498;
                        verpen2.y = 202;
                        pp.y = pp.getY() + 2;

                        //pp=new Vertice(verpen2.getX()-160, verpen2.getY()+110, "PP");
                        //p=new Vertice(verpen2.getX()-240, verpen2.getY(), "");
                    }
                    if (p.getY() == 0) {
                        p.y = 5;
                    }
                    repaint();
                }

                reflejarVerpen1();
                reflejarVerpen2();
                pp = new Vertice(InterseccionX(), InterseccionY(), pp.getNombre());
                d = new Vertice((p.getX() + pp.getX()) / 2, (p.getY() + pp.getY()) / 2, "d");
                repaint();
            }

            public void mouseMoved(MouseEvent e) {
                repaint();
            }
        };
        addMouseMotionListener(pintmov);
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

        g2.setFont(new Font("Tahoma", Font.BOLD, 12));
        g2.setColor(Color.BLACK);
        g2.drawString("r", (int) verpen1.getX() + 35, (int) verpen1.getY() - 30);
        g2.setColor(Color.red);
        g2.drawString("d", (int) d.getX(), (int) d.getY());

        g2.setFont(new Font("Tahoma", Font.PLAIN, 12));
        g2.setColor(Color.BLACK);
        ejex.paint(g2);
        g2.setColor(Color.BLACK);
        ejey.paint(g2);

        // eje X
        int var = 25;
        for (int i = 0; i <= 500; i += 50) {
            g2.setColor(Color.BLACK);
            if (i < 500) {
                g2.drawLine(i, 245, i, 255);
            }

            if (i < 250) {
                g2.drawString("-" + var, i + 2, 265);
                var -= 5;
            }
            if (i == 250) {
                var = 0;
                g2.drawString("" + var, i - 10, 265);
            }
            if (i > 250 && i < 500) {
                var += 5;
                g2.drawString("" + var, i - 15, 265);
            }
        }
        g2.setFont(new Font("Tahoma", Font.BOLD, 11));
        g2.drawString("x", 495, 265);
        g2.drawLine(500, 250, 495, 248);
        g2.drawLine(500, 250, 495, 252);
        g2.setFont(new Font("Tahoma", Font.PLAIN, 11));

        // eje Y
        var = 30;
        for (int i = 0; i <= 500; i += 50) {
            g2.setColor(Color.BLACK);
            if (i > 0) {
                g2.drawLine(245, i, 255, i);
            }

            if (i <= 200) {
                var -= 5;
                if (var < 25) {
                    g2.drawString("" + var, 225, i + 10);
                }
            }
            if (i == 250) {
                var = 0;
            }
            if (i >= 300) {
                var += 5;
                g2.drawString("-" + var, 225, i);
            }
        }
        g2.setFont(new Font("Tahoma", Font.BOLD, 12));
        g2.drawString("y", 260, 15);
        g2.drawLine(250, 5, 252, 10);
        g2.drawLine(250, 5, 248, 10);


        g2.setColor(Color.BLUE);
        g2.drawLine((int) extremoA.getX(), (int) extremoA.getY(), (int) extremoB.getX(), (int) extremoB.getY());
        g2.setColor(Color.RED);
        verpen1.paint(g2);
        g2.setColor(Color.RED);
        verpen2.paint(g2);

        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Tahoma", Font.BOLD, 11));
        g2.drawString("d = " + distaciapuntos() + "u", 10, 20);
        g2.setColor(Color.BLACK);
        g2.drawString("Pendiente r = " + redondPiente(), 10, 40);
//        if(c()>=0){
//       g2.setColor(Color.BLACK);
//       g2.drawString(""+pendiente()+"x+"+"1"+"y+"+c()+"=0", 10, 50);
//       }
//       else{
//       g2.setColor(Color.BLACK);
//       g2.drawString(""+pendiente()+"x+"+"1"+"y+"+c()+"=0", 10, 50);
//       //g2.drawString(""+a()+"x+"+b()+"y"+c()+"=0", 10, 40);
//       }
        g2.setColor(Color.BLACK);
        g2.drawString("|Ar·xP + Br·yP + Cr|", 13, 63);
        g2.drawString("_________________", 10, 66);
        g2.drawString("  = " + distaciapuntos() + "u", 126, 70);
        g2.drawString("__________", 45, 68);
        g2.drawLine(40, 83, 44, 70);
        g2.drawLine(40, 83, 36, 74);
        g2.drawString("(A    + B    )", 46, 84);

        g2.setFont(new Font("Tahoma", Font.BOLD, 8));
        g2.drawString("r", 59, 87);
        g2.drawString("r", 90, 87);
        g2.setFont(new Font("Tahoma", Font.BOLD, 11));

        g2.drawString("2        2", 63, 80);
        if (n() >= 0) {
            g2.drawString("r: " + pendiente() + "x - y + " + n() + " = 0", 10, 105);
        } else {
            g2.drawString("r: " + pendiente() + "x - y - " + (-1 * n()) + " = 0", 10, 105);
        }

        g2.setColor(Color.BLACK);
        pp.paint(g2);

        g2.setColor(Color.BLACK);
        p.paint(g2);

        //1 cuadranteg
        if (p.getY() <= 250 && p.getX() >= 250) {
            g2.setColor(Color.BLACK);
            g2.drawString("  P  (" + cordenada1ercuadranteX() + "," + cordenada1ercuadranteY() + ")", (int) p.getX(), (int) p.getY());
        }
        //4 cuadrante
        if (p.getY() >= 250 && p.getX() >= 250) {
            g2.setColor(Color.BLACK);
            g2.drawString("   P (" + cordenada1ercuadranteX() + "," + cordenada1ercuadranteY() + ")", (int) p.getX(), (int) p.getY());
        }
        //3cuadrante
        if (p.getY() >= 250 && p.getX() <= 250) {
            g2.setColor(Color.BLACK);
            g2.drawString(" P   (" + cordenada1ercuadranteX() + "," + cordenada1ercuadranteY() + ")", (int) p.getX(), (int) p.getY());
        }
        //2cuadrante
        if (p.getY() <= 250 && p.getX() <= 250) {
            g2.setColor(Color.BLACK);
            g2.drawString("  P  (" + cordenada1ercuadranteX() + "," + "" + cordenada1ercuadranteY() + ")", (int) p.getX(), (int) p.getY());
        }
        g2.setColor(Color.RED);
        g2.setStroke(new BasicStroke(1f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1f, new float[]{4f}, 0f));
        g2.drawLine((int) p.getX(), (int) p.getY(), (int) pp.getX(), (int) pp.getY());

    }

    public double distaciapuntos() {
        int di = (int) p.distance(pp) / 10;
        return di;
    }

    public double pendiente() {
        double y2 = (250 - verpen2.getY()) / 10;
        double y1 = -1 * (verpen1.getY() - 250) / 10;
        double x2 = (verpen2.getX() - 250) / 10;
        double x1 = -1 * (250 - verpen1.getX()) / 10;
        double m = (y2 - y1) / (x2 - x1);
        BigDecimal b = new BigDecimal(m);
        BigDecimal r = b.setScale(3, BigDecimal.ROUND_HALF_UP);

        return r.doubleValue();
    }

    public double pendientepint() {
        double m = b() / a();

        return m;
    }

    public double n() {
        double y1 = -1 * (verpen1.getY() - 250) / 10;
        double x1 = -1 * (250 - verpen1.getX()) / 10;
        double n = y1 - (pendiente() * x1);
        BigDecimal b = new BigDecimal(n);
        BigDecimal r = b.setScale(2, BigDecimal.ROUND_HALF_UP);
        return r.doubleValue();

    }

    public int a() {
        double y2 = (250 - verpen2.getY()) / 10;
        double y1 = -1 * (verpen1.getY() - 250) / 10;
        int a = (int) (y2 - y1);
        return a;
    }

    public int b() {
        double x2 = (verpen2.getX() - 250) / 10;
        double x1 = -1 * (250 - verpen1.getX()) / 10;
        int b = (int) (x2 - x1);
        return b;
    }

    public int c() {
        int c = -b() * (int) n();
        return c;
    }

    public double ecuafinal() {
        double ecua = a() * (verpen2.getX() - 250) / 10 + b() * (250 - verpen2.getY()) / 10 + c() / Math.sqrt(Math.pow(a(), 2) + Math.pow(b(), 2));
        double num = (pendiente() * this.cordenada1ercuadranteX()) - this.cordenada1ercuadranteY() + n();
        double den = Math.sqrt(1 + Math.pow(pendiente(), 2));
        return num / den;
    }

    public double Redondeo() {
        double var = 0;
        try {
            BigDecimal q = new BigDecimal(ecuafinal());
            q = q.setScale(2, BigDecimal.ROUND_HALF_UP);
            var = Double.parseDouble(q.toString());
        } catch (NumberFormatException e) {
            return 0;
        }
        return var;
    }

    public double redondPiente() {
        double var = 0;
        try {
            BigDecimal q = new BigDecimal(pendiente());
            q = q.setScale(2, BigDecimal.ROUND_HALF_UP);
            var = Double.parseDouble(q.toString());
        } catch (NumberFormatException e) {
            return 0;
        }
        return var;
    }

    public int cordenada1ercuadranteX() {
        int aux = (int) p.getX() - 250;
        return aux / 10;
    }

    public int cordenada1ercuadranteY() {
        int aux = 250 - (int) p.getY();
        return aux / 10;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("puntorecta") {

            @Override
            public void frameInit() {
                super.frameInit();
                setDefaultCloseOperation(EXIT_ON_CLOSE);
            }
        };
        JPanel p = new Main(30, 350, 460, 50, 180, 50, 0, 250, 500, 250, 250, 5, 250, 500, 0, 350, 500, 50);
        frame.add(p);
        frame.setSize(550, 550);
        frame.show();
    }

    // TODO code application logic here
    public int InterseccionX() {
        try {
            double pi = pendientePP();

            double P = -1 / pi;
            double n = verpen1.getY() - (verpen1.getX() * pi);
            double N = this.p.getY() - (this.p.getX() * P);
            int Xi = (int) ((n - N) / (P - pi));
            return Xi;
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public int InterseccionY() {
        try {
            double P = -1 / pendientePP();
            double N = this.p.getY() - (this.p.getX() * P);
            int Y = (int) (P * InterseccionX() + N);
            return Y;
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public void reflejarVerpen1() {
        double x = 2 * verpen1.getX() - verpen2.getX();
        double y = 2 * verpen1.getY() - verpen2.getY();
        extremoA = new Vertice(x, y, "");
    }

    public void reflejarVerpen2() {
        double x = 2 * verpen2.getX() - verpen1.getX();
        double y = 2 * verpen2.getY() - verpen1.getY();
        extremoB = new Vertice(x, y, "");
    }

    public double pendientePP() {
        double y2 = verpen2.getY();
        double y1 = verpen1.getY();
        double x2 = verpen2.getX();
        double x1 = verpen1.getX();
        double m = (y2 - y1) / (x2 - x1);
//    System.out.println("x1:"+x1+" y1: "+y1+" x2: "+x2+"  y2: "+y2);
        BigDecimal b = new BigDecimal(m);
        BigDecimal r = b.setScale(3, BigDecimal.ROUND_HALF_UP);

        return r.doubleValue();
    }
}
