/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package clases;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.math.BigDecimal;

/**
 *
 * @author marcel
 */
public class raiz {
private double a, b;

    public raiz(double a, double b) {
        this.a = a;
        this.b = b;
    }

    public void paint(Graphics2D g2, int x, int y){
        g2.setStroke(new BasicStroke(1.0f));
        g2.setFont(new Font("Tahoma", Font.PLAIN, 11));
        g2.setColor(Color.BLACK);
        g2.drawLine(x+10, y, x+140, y);
        g2.drawLine(x, y+3, x+4, y+15);
        g2.drawLine(x+4, y+15, x+10, y);
        BigDecimal var1 = new BigDecimal(Math.sqrt(a*a+b*b));
        BigDecimal var = var1.setScale(2, BigDecimal.ROUND_HALF_UP);
        g2.drawString("(|x   - x   |) + (|y   - y  |)  = "+var.doubleValue()+"u", x+15, y+15);
        g2.setFont(new Font("Tahoma", Font.PLAIN, 8));
        g2.drawString("P      Q", x+30, y+17);
        g2.drawString("P      Q", x+95, y+17);
        g2.drawString("2", x+67, y+8);
        g2.drawString("2", x+132, y+8);
    }

}
