/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ecuaciongeneral;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Frank
 */
public class EcuacionGeneral extends JPanel {

    private String signo;
    private vertice P, Q, P1, Q1;
    private Line2D linea, ejeX, ejeY;
    private String PuntoP = "";
    private String PuntoQ = "";
    private int flag;
    String m;

    public EcuacionGeneral(int x1, int y1, int x2, int y2) throws Exception {

        setBackground(Color.WHITE);
        signo = "+";
        this.P = new vertice(x1, y1, "P");
        this.Q = new vertice(x2, y2, "Q");
        m = "r: " + Ecuacion_M_pendiente(valorX((P.getX())), valorY((P.getY())), valorX((Q.getX())), valorY((Q.getY()))) + "x -y " + signo + "" + Ecuacion_N() + " = 0";
        ProlongarRectas(P, Q);

        linea = new Line2D.Double(P1, Q1);
        ejeY = new Line2D.Double(185, 3, 185, 320);
        ejeX = new Line2D.Double(0, 155, 385, 155);
        PuntoP = "P (" + valorX(x1) + "," + valorY(y1) + ")";
        PuntoQ = "Q (" + valorX(x2) + "," + valorY(y2) + ")";
        flag = 0;

        MouseListener pint = new MouseListener() {

            public void mouseClicked(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {
                if (P.contains(new vertice(e.getX(), e.getY(), ""))) {
                    flag = 1;
                } else if (Q.contains(new vertice(e.getX(), e.getY(), ""))) {
                    flag = 2;
                } else {
                    flag = 0;
                }
                repaint();
            }

            public void mouseReleased(MouseEvent e) {
                repaint();
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }
        };
        addMouseListener(pint);
        MouseMotionListener pintMove = new MouseMotionListener() {

            public void mouseDragged(MouseEvent e) {

                if (flag == 1) {
                    P = new vertice(e.getX(), e.getY(), "P");
                    ProlongarRectas(P, Q);
                    linea = new Line2D.Double(P1, Q1);
                    try {
                        PuntoP = "P (" + valorX((P.getX())) + "," + valorY((P.getY())) + ")";
                    } catch (Exception ex) {
                        Logger.getLogger(EcuacionGeneral.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (P.getX() != Q.getX()) {
                        if (P.getY() != Q.getY()) {
                            try {
                                m = "r: " + Ecuacion_M_pendiente(valorX((P.getX())), valorY((P.getY())), valorX((Q.getX())), valorY((Q.getY()))) + "x -y " + signo + "  " + Ecuacion_N() + " = 0";
                            } catch (Exception ex) {
                                Logger.getLogger(EcuacionGeneral.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            m = "r: y = " + valorY(Q.getY());
                        }

                    } else {
                        m = "r: x = " + valorX(P.getX());
                    }
                } else if (flag == 2) {

                    Q = new vertice(e.getX(), e.getY(), "Q");
                    ProlongarRectas(P, Q);
                    linea = new Line2D.Double(P1, Q1);
                    try {
                        PuntoQ = "Q (" + valorX((Q.getX())) + "," + valorY((Q.getY())) + ")";
                    } catch (Exception ex) {
                        Logger.getLogger(EcuacionGeneral.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (P.getX() != Q.getX()) {
                        if (P.getY() != Q.getY()) {
                            try {
                                m = "r: " + Ecuacion_M_pendiente(valorX((Q.getX())), valorY((Q.getY())), valorX((P.getX())), valorY((P.getY()))) + "x -y " + signo + " " + Ecuacion_N() + " = 0";
                            } catch (Exception ex) {
                                Logger.getLogger(EcuacionGeneral.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            m = "r: y = " + valorY(Q.getY());
                        }
                    } else {
                        m = "r: x = " + valorX(P.getX());
                    }
                }

                repaint();

            }

            public void mouseMoved(MouseEvent e) {
                repaint();
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


        g2.setColor(Color.black);
        //g2.drawRoundRect(7, 5, 90, 40, 4, 4);
        g2.setColor(Color.BLUE);
        g2.draw(linea);

        g2.setColor(Color.black);
        P.paint(g2);
        g2.setColor(Color.black);
        Q.paint(g2);

        g2.setColor(Color.black);
        g2.draw(ejeX);
        g2.setColor(Color.black);
        g2.draw(ejeY);

        dibujarX(g2);
        dibujarY(g2);

        g2.setFont(new Font("Tahoma", Font.BOLD, 12));
        g2.drawString(PuntoP, 12, 18);
        g2.drawString(PuntoQ, 12, 34);


        g2.drawString(m, 260, 18);
    }

    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("EcuacionGeneral") {

            public void frameInit() {
                super.frameInit();
                setDefaultCloseOperation(EXIT_ON_CLOSE);
            }
        };
        JPanel p = new EcuacionGeneral(100, 180, 180, 100);
        frame.add(p);
        frame.setSize(450, 400);
        frame.setVisible(true);
    }

    public void dibujarX(Graphics2D g2) {
        int var = 5;
        int nu = -9;
        int b = 0;
        String num = "" + nu;
        for (int i = 0; i < 18; i++) {
            g2.setColor(Color.black);
            g2.drawLine(var, 153, var, 157);
            g2.drawString(num, var - 2, 170);
            if (b == 0) {
                nu++;
            } else {
                nu++;
            }
            if (nu == 0) {
                b = 1;
                var += 20;
                nu++;
            }
            var += 20;
            num = "" + nu;

        }
        g2.setFont(new Font("Tahoma", Font.BOLD, 11));
        g2.drawString("x", 380, 170);
        g2.drawLine(385, 155, 380, 153);
        g2.drawLine(385, 155, 380, 157);
        g2.setFont(new Font("Tahoma", Font.PLAIN, 12));
    }

    public void dibujarY(Graphics2D g2) {
        int var = 5;
        int nu = 6;
        int b = 0;
        String num = "" + nu;
        for (int i = 0; i < 12; i++) {
            g2.setColor(Color.black);
            if (nu == 6) {
                nu--;
            } else {
                g2.drawLine(183, var, 187, var);
                g2.drawString(num, 166, var + 2);
                if (b == 0) {
                    nu--;
                } else {
                    nu--;
                }
                if (nu == 0) {
                    var += 25;
                    nu--;
                }
            }
            var += 25;
            num = "" + nu;
        }
        g2.setFont(new Font("Tahoma", Font.BOLD, 11));
        g2.drawString("y", 195, 12);
        g2.drawLine(185, 3, 183, 8);
        g2.drawLine(185, 3, 187, 8);

        g2.setFont(new Font("Tahoma", Font.PLAIN, 12));
        g2.drawString("0", 178, 166);
    }

    public double valorX(double x) {
        double p = 0;
        BigDecimal q = null;
        if (x >= 185) {
            p = (x - 185) / 20;
            q = new BigDecimal(p);
            q.setScale(2, BigDecimal.ROUND_HALF_UP);
            return q.doubleValue();
        } else {
            p = (185 - x) / 20;
            p *= -1;
            q = new BigDecimal(p);
            q.setScale(2, BigDecimal.ROUND_HALF_UP);

            return q.doubleValue();
        }
    }

    public double valorY(double y) {
        double p = 0;
        BigDecimal q = null;

        if (y >= 155) {
            p = (y - 155) / 25;
            p *= -1;
            q = new BigDecimal(p);
            q.setScale(2, BigDecimal.ROUND_HALF_UP);
            return q.doubleValue();
        } else {
            p = (155 - y) / 25;
            q = new BigDecimal(p);
            q.setScale(2, BigDecimal.ROUND_HALF_UP);
            return q.doubleValue();
        }

    }

    public double Ecuacion_M_pendiente(double x1, double y1, double x2, double y2) {
        double z = 0;
        if (x1 != x2) {
            z = (y2 - y1) / (x2 - x1);
        }

        BigDecimal p = new BigDecimal(z);
        BigDecimal rr = p.setScale(2, BigDecimal.ROUND_HALF_UP);
        return rr.doubleValue();
    }

    public double Ecuacion_N() throws Exception {
        if ((valorY((P.getY())) - (valorX((P.getX())) * (Ecuacion_M_pendiente(valorX((P.getX())), valorY((P.getY())), valorX((Q.getX())), valorY((Q.getY())))))) < 0) {
            signo = "-";
        } else {
            signo = "+";
        }
        double valor = ((valorY((P.getY())) - (valorX((P.getX())) * (Ecuacion_M_pendiente(valorX((P.getX())), valorY((P.getY())), valorX((Q.getX())), valorY((Q.getY())))))));
        if (valor < 0) {
            valor *= -1;
        }
        BigDecimal p = new BigDecimal(valor);
        BigDecimal rr = p.setScale(2, BigDecimal.ROUND_HALF_UP);
        return rr.doubleValue();

    }

    public void ProlongarRectas(vertice P, vertice Q) {
        P1 = new vertice((int) (2 * P.getX() - Q.getX()), (int) (2 * P.getY() - Q.getY()), "");
        Q1 = new vertice((int) (2 * Q.getX() - P.getX()), (int) (2 * Q.getY() - P.getY()), "");
    }
}
