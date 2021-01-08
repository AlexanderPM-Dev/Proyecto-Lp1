package vista;

import estructuras.Arte;
import estructuras.Persistencia;
import java.awt.Dimension;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import static vista.PanelPrincipal.jTelaPrincipal;

public class PaneldeBusquedaTombo extends javax.swing.JInternalFrame {

    private boolean existeNaLista(int tomb) {
        for (Arte a : cad) {
            if (a.getTombo() == tomb) {
                return true;
            }
        }
        return false;
    }
    public void setPosicao() {
        Dimension d = jTelaPrincipal.getSize();
        this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);
    }
    
    public PaneldeBusquedaTombo() {
        p.setupLer();
        if (p.getContinua() == true) {
            p.readRecords();
            p.cleanupLer();
            cad = p.getCad();
        }
        initComponents();
        getRootPane().setDefaultButton(BotaoContinuar);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        BotaoContinuar = new javax.swing.JButton();
        BotaoCancelar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtTombo = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);

        BotaoContinuar.setText("Continuar");
        BotaoContinuar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoContinuarActionPerformed(evt);
            }
        });

        BotaoCancelar.setText("Cancelar");
        BotaoCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoCancelarActionPerformed(evt);
            }
        });

        jLabel1.setText("Anote el número del trabajo que desea editar:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(98, 98, 98)
                        .addComponent(BotaoContinuar)
                        .addGap(62, 62, 62)
                        .addComponent(BotaoCancelar))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(txtTombo, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BotaoContinuar)
                    .addComponent(BotaoCancelar))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(151, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BotaoContinuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotaoContinuarActionPerformed
        boolean errado = true;

        while (errado) {
            if (errado == true) {
                try {
                    int tombo;

                    tombo = Integer.parseInt(txtTombo.getText());

                    if (existeNaLista(tombo)) {
                        this.dispose();
                        PaneldeRegistro painelE = new PaneldeRegistro(tombo);
                        painelE.setTitle("Editar Obra de Arte");
                        jTelaPrincipal.add(painelE);
                        painelE.setPosicao();
                        painelE.setVisible(true);
                        break;
                    } else {
                        JOptionPane.showMessageDialog(rootPane, "Tombo não Encontrado.", "Aviso", JOptionPane.ERROR_MESSAGE);
                    }

                } catch (NumberFormatException n) {
                    JOptionPane.showMessageDialog(rootPane, "Caractere digitado Inválido.", "Aviso", JOptionPane.ERROR_MESSAGE);
                }
                errado = false;
            }
        }
    }//GEN-LAST:event_BotaoContinuarActionPerformed

    private void BotaoCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotaoCancelarActionPerformed
         this.dispose();
    }//GEN-LAST:event_BotaoCancelarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BotaoCancelar;
    private javax.swing.JButton BotaoContinuar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txtTombo;
    // End of variables declaration//GEN-END:variables
    private static LinkedList<Arte> cad = new LinkedList<Arte>();
    private static Persistencia p = new Persistencia();
}