package Baric_Mediana;

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
public class Baric_Mediana extends JPanel {

    private vertice vA,  vB,  vC,  vA1,  vB1,  vC1;
    private Line2D l1,  l2,  l3,  M1,  M2,  M3;
    private int xBaricentro,  yBaricentro;
    private int flag = 0;

    public Baric_Mediana(int x1, int y1, int x2, int y2, int x3, int y3) {
        setPreferredSize(new Dimension(200, 200));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createTitledBorder("Propiedades del Baricentro y Las Medianas"));

        this.vA = new vertice(x1, y1, "A");
        this.vB = new vertice(x2, y2, "B");
        this.vC = new vertice(x3, y3, "C");
        l1 = new Line2D.Double(vA, vB);
        l2 = new Line2D.Double(vB, vC);
        l3 = new Line2D.Double(vC, vA);
        ptosMedios();
        Baricentro();

        MouseListener pintar = new MouseListener() {

            public void mouseClicked(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {
                if (vA.contains(new vertice(e.getX(), e.getY(), vA.getNombre()))) {
                    flag = 1;
                } else if (vB.contains(new vertice(e.getX(), e.getY(), vB.getNombre()))) {
                    flag = 2;
                } else if (vC.contains(new vertice(e.getX(), e.getY(), vC.getNombre()))) {
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
                if (flag == 1) {
                    vA = new vertice(e.getX(), e.getY(), vA.getNombre());
                } else if (flag == 2) {
                    vB = new vertice(e.getX(), e.getY(), vB.getNombre());
                } else if (flag == 3) {
                    vC = new vertice(e.getX(), e.getY(), vC.getNombre());
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
        g2.draw(l1);
        g2.draw(l2);
        g2.draw(l3);

        /* Medianas */
        g2.setColor(new Color(9, 69, 141));
        g2.setStroke(new BasicStroke(2.0f));
        g2.draw(M1);
        g2.draw(M2);
        g2.draw(M3);

        /* Baricentro y Puntos */
        g2.setColor(Color.black);
        vertice baricentro = new vertice(xBaricentro, yBaricentro, "O (Baricentro)");
        baricentro.paint(g2);
        vA.paint(g2);
        vB.paint(g2);
        vC.paint(g2);
        g2.setColor(new Color(171, 43, 52));
        vA1.paint(g2);
        g2.setColor(new Color(171, 43, 52));
        vB1.paint(g2);
        g2.setColor(new Color(171, 43, 52));
        vC1.paint(g2);

        /* Pintando los Datos */
//        g2.setColor(new Color(171, 43, 52));
        g2.setStroke(new BasicStroke(1.0f));
        g2.drawRect(315, 15, 110, 70);

        g2.drawString("OA = " + Math.ceil((Math.sqrt(Math.pow(vA.x - xBaricentro, 2) + Math.pow(vA.y - yBaricentro, 2))) / 37 * 10) / 10 + " cm", 325, 35);
        g2.drawString("OA  = " + Math.ceil((Math.sqrt(Math.pow(vA1.x - xBaricentro, 2) + Math.pow(vA1.y - yBaricentro, 2))) / 37 * 10) / 10 + " cm", 325, 55);
        g2.drawString("AA  = " + Math.ceil((Math.sqrt(Math.pow(vA.x - vA1.x, 2) + Math.pow(vA.y - vA1.y, 2))) / 37 * 10) / 10 + " cm", 325, 75);

        g2.drawRect(440, 15, 110, 70);
        g2.drawString("OB = " + Math.ceil((Math.sqrt(Math.pow(vB.x - xBaricentro, 2) + Math.pow(vB.y - yBaricentro, 2))) / 37 * 10) / 10 + " cm", 450, 35);
        g2.drawString("OB  = " + Math.ceil((Math.sqrt(Math.pow(vB1.x - xBaricentro, 2) + Math.pow(vB1.y - yBaricentro, 2))) / 37 * 10) / 10 + " cm", 450, 55);
        g2.drawString("BB  = " + Math.ceil(((Math.sqrt(Math.pow(vB.x - vB1.x, 2) + Math.pow(vB.y - vB1.y, 2)) - 1)) / 37 * 10) / 10 + " cm", 450, 75);

        g2.drawRect(565, 15, 110, 70);
        g2.drawString("OC = " + Math.ceil((Math.sqrt(Math.pow(vC.x - xBaricentro, 2) + Math.pow(vC.y - yBaricentro, 2))) / 37 * 10) / 10 + " cm", 575, 35);
        g2.drawString("OC  = " + Math.ceil((Math.sqrt(Math.pow(vC1.x - xBaricentro, 2) + Math.pow(vC1.y - yBaricentro, 2))) / 37 * 10) / 10 + " cm", 575, 55);
        g2.drawString("CC  = " + Math.ceil(((Math.sqrt(Math.pow(vC.x - vC1.x, 2) + Math.pow(vC.y - vC1.y, 2)) - 1)) / 37 * 10) / 10 + " cm", 575, 75);

        /* Pintando las lineas de los segmentos */
        //A
        g2.drawLine(325, 23, 340, 23);
        g2.drawLine(325, 43, 343, 43);
        g2.drawLine(325, 63, 343, 63);
        //B
        g2.drawLine(450, 23, 465, 23);
        g2.drawLine(450, 43, 468, 43);
        g2.drawLine(450, 63, 468, 63);
        //C
        g2.drawLine(575, 23, 590, 23);
        g2.drawLine(575, 43, 593, 43);
        g2.drawLine(575, 63, 593, 63);

        /* Relaciones */
        //A
        g2.drawRect(20, 310, 180, 39);
        g2.drawString("AA", 25, 325);
        g2.drawString("OA", 25, 345);
        g2.drawString("=", 53, 335);
        g2.drawLine(24, 330, 45, 330);

        g2.drawString("OA", 220, 325);
        g2.drawString("OA", 220, 345);
        g2.drawString("=", 248, 335);
        g2.drawLine(219, 330, 240, 330);

        g2.drawString("OA", 420, 325);
        g2.drawString("AA", 420, 345);
        g2.drawString("=", 447, 335);
        g2.drawLine(419, 330, 440, 330);

        //B
        g2.drawRect(215, 310, 180, 39);
        g2.drawString("BB", 70, 325);
        g2.drawString("OB", 70, 345);
        g2.drawString("=", 100, 335);
        g2.drawLine(69, 330, 92, 330);

        g2.drawString("OB", 265, 325);
        g2.drawString("OB", 265, 345);
        g2.drawString("=", 293, 335);
        g2.drawLine(265, 330, 285, 330);

        g2.drawString("OB", 465, 325);
        g2.drawString("BB", 465, 345);
        g2.drawString("=", 491, 335);
        g2.drawLine(464, 330, 483, 330);

        //C
        g2.drawRect(415, 310, 180, 39);
        g2.drawString("CC", 116, 325);
        g2.drawString("OC", 116, 345);
        g2.drawString("= 3.0cm", 140, 335);
        g2.drawLine(115, 330, 137, 330);

        g2.drawString("OC", 310, 325);
        g2.drawString("OC", 310, 345);
        g2.drawString("=", 338, 335);
        g2.drawLine(310, 330, 330, 330);
        g2.drawString("1", 355, 325);
        g2.drawLine(355, 330, 362, 330);
        g2.drawString("cm", 367, 334);
        g2.drawString("2", 355, 345);

        g2.drawString("OC", 510, 325);
        g2.drawString("CC", 510, 345);
        g2.drawString("=", 536, 335);
        g2.drawLine(509, 330, 528, 330);

        g2.drawString("2", 554, 325);
        g2.drawLine(553, 330, 562, 330);
        g2.drawString("cm", 568, 334);
        g2.drawString("3", 554, 345);


        /// SubIndice
        g2.setFont(new Font("Tahoma", Font.BOLD, 8));
        //A
        g2.drawString("1", 342, 55);
        g2.drawString("1", 342, 75);
        g2.drawString("1", (int) vA1.x + 19, (int) vA1.y + 12);
        g2.drawString("1", 42, 325);
        g2.drawString("1", 42, 345);
        g2.drawString("1", 238, 325);
        g2.drawString("1", 437, 345);

        //B
        g2.drawString("1", 466, 55);
        g2.drawString("1", 466, 75);
        g2.drawString("1", (int) vB1.x + 18, (int) vB1.y + 12);
        g2.drawString("1", 86, 325);
        g2.drawString("1", 86, 345);
        g2.drawString("1", 282, 325);
        g2.drawString("1", 480, 345);

        //C
        g2.drawString("1", 591, 55);
        g2.drawString("1", 591, 75);
        g2.drawString("1", (int) vC1.x + 19, (int) vC1.y + 12);
        g2.drawString("1", 132, 325);
        g2.drawString("1", 132, 345);
        g2.drawString("1", 327, 325);
        g2.drawString("1", 525, 345);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintT(g);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Propiedades del Baricentro y Las Medianas") {

            @Override
            public void frameInit() {
                super.frameInit();
                setDefaultCloseOperation(EXIT_ON_CLOSE);
            }
        };

        JPanel p = new Baric_Mediana(140, 50, 30, 200, 250, 200);
        frame.add(p);
        frame.setSize(700, 400);
        frame.setLocation(200, 150);
        frame.show();

    }

    private void Update() {
        if (flag == 1) {
            l1 = new Line2D.Double(vA, vB);
            l3 = new Line2D.Double(vC, vA);
        } else if (flag == 2) {
            l1 = new Line2D.Double(vA, vB);
            l2 = new Line2D.Double(vB, vC);
        } else if (flag == 3) {
            l3 = new Line2D.Double(vC, vA);
            l2 = new Line2D.Double(vB, vC);
        }
        ptosMedios();
        Baricentro();
    }

    public void ptosMedios() {
        vA1 = new vertice(((int) vB.x + (int) vC.x) / 2, ((int) vB.y + (int) vC.y) / 2, "A");
        vB1 = new vertice(((int) vA.x + (int) vC.x) / 2, ((int) vA.y + (int) vC.y) / 2, "B");
        vC1 = new vertice(((int) vA.x + (int) vB.x) / 2, ((int) vA.y + (int) vB.y) / 2, "C");

        M1 = new Line2D.Double(vA.x, vA.y, vA1.x, vA1.y);
        M2 = new Line2D.Double(vB.x, vB.y, vB1.x, vB1.y);
        M3 = new Line2D.Double(vC.x, vC.y, vC1.x, vC1.y);
    }

    public void Baricentro() {
        xBaricentro = (int) ((vA.x + vB.x + vC.x)) / 3;
        yBaricentro = (int) ((vA.y + vB.y + vC.y)) / 3;
    }
}
