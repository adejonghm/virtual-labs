package Suma_AngExt_Polygon;

import java.awt.BorderLayout;
import java.awt.Graphics2D;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Applet_Polygon extends javax.swing.JApplet {

    private Visual_Polygon p;

    @Override
    public void init() {
        p = new Visual_Polygon(6);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
            
            java.awt.EventQueue.invokeAndWait(new Runnable() {

                public void run() {
                    initComponents();
                    setSize(840, 550);
                    p.setSize(840, 550);
                    p_contenedor.setSize(840, 550);
                    p_contenedor.setLayout(new BorderLayout(10, 5));
                    p_contenedor.add(p, BorderLayout.CENTER);

                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        p_contenedor = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btn_Add = new javax.swing.JButton();
        btn_Del = new javax.swing.JButton();

        javax.swing.GroupLayout p_contenedorLayout = new javax.swing.GroupLayout(p_contenedor);
        p_contenedor.setLayout(p_contenedorLayout);
        p_contenedorLayout.setHorizontalGroup(
            p_contenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 667, Short.MAX_VALUE)
        );
        p_contenedorLayout.setVerticalGroup(
            p_contenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 466, Short.MAX_VALUE)
        );

        btn_Add.setFont(new java.awt.Font("Tahoma", 1, 12));
        btn_Add.setText("Adicionar Vértice");
        btn_Add.setToolTipText("Esta opción adicina un lado al polígono");
        btn_Add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_AddActionPerformed(evt);
            }
        });

        btn_Del.setFont(new java.awt.Font("Tahoma", 1, 12));
        btn_Del.setText("Eliminar Vértice");
        btn_Del.setToolTipText("Esta opción elimina un lado dell polígono");
        btn_Del.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_DelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(128, Short.MAX_VALUE)
                .addComponent(btn_Add)
                .addGap(49, 49, 49)
                .addComponent(btn_Del)
                .addGap(230, 230, 230))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_Add)
                    .addComponent(btn_Del))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(p_contenedor, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(p_contenedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_AddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_AddActionPerformed
        if (p.getPoligono().getCantVertices() < 7) {
            p.Borrar((Graphics2D) p.getGraphics());
            p.reBuild(p.getPoligono().getCantVertices() + 1);
            p.paintT((Graphics2D) p.getGraphics());
        } else {
            btn_Add.setEnabled(false);
        }

        if (p.getPoligono().getCantVertices() > 5) {
            btn_Del.setEnabled(true);
        }
    }//GEN-LAST:event_btn_AddActionPerformed

    private void btn_DelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_DelActionPerformed
        if (p.getPoligono().getCantVertices() > 5) {
            p.Borrar((Graphics2D) p.getGraphics());
            p.reBuild(p.getPoligono().getCantVertices() - 1);
            p.paintT((Graphics2D) p.getGraphics());
        } else {
            btn_Del.setEnabled(false);
        }

        if (p.getPoligono().getCantVertices() < 7) {
            btn_Add.setEnabled(true);
        }
    }//GEN-LAST:event_btn_DelActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Add;
    private javax.swing.JButton btn_Del;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel p_contenedor;
    // End of variables declaration//GEN-END:variables
}
