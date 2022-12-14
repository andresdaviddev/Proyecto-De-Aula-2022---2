/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package dashboard;

import domain.Conexion;
import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author andre
 */
public class panelMisDatos extends javax.swing.JPanel {

    String documento;

    /**
     * Creates new form panelConfig
     *
     * @param documento
     */
    public panelMisDatos(String documento) {
        initComponents();
        this.documento = documento;
        select();

    }

    public final void select() {
        Conexion con = new Conexion();
        Connection cx = con.getConexion();
        String SELECT = "SELECT nombre, apellido, usuario, contraseña FROM test.usuarios WHERE documento = '" + this.documento + "'";
        try {
            PreparedStatement ps = cx.prepareStatement(SELECT);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                txtNombreMisDatos.setText(rs.getString(1));
                txtApellidoMisDatos.setText(rs.getString(2));
                txtUserMisDatos.setText(rs.getString(3));
                txtContraMisDatos.setText(rs.getString(4));

                txtNombreEditar.setText(rs.getString(1));
                txtApellidoEditar.setText(rs.getString(2));
                txtUserEditar.setText(rs.getString(3));
                txtContraEditar.setText(rs.getString(4));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "error al seleccionar" + e.getMessage());
        }
    }

    public final void actualizar() {
        Conexion con = new Conexion();
        Connection cx = con.getConexion();
        String nombre = txtNombreEditar.getText();
        String apellido = txtApellidoEditar.getText();
        String user = txtUserEditar.getText();
        String contra = txtContraEditar.getText();
        String UPDATE = "UPDATE test.usuarios"
                + " SET nombre = '" + nombre + "'"
                + ",apellido = '" + apellido + "'" + ", usuario = '" + user + "'"
                + ", contraseña = '" + contra + "'" + "WHERE documento = '" + this.documento + "'";

        try {

            PreparedStatement ps = cx.prepareStatement(UPDATE);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Datos Actualizados Exitosamente");
            select();
            cx.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar " + e.getMessage());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel5 = new javax.swing.JLabel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        txtNombreMisDatos = new javax.swing.JTextField();
        txtApellidoMisDatos = new javax.swing.JTextField();
        txtUserMisDatos = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jlabel1 = new javax.swing.JLabel();
        txtContraMisDatos = new javax.swing.JPasswordField();
        btnVerContra = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        txtNombreEditar = new javax.swing.JTextField();
        txtApellidoEditar = new javax.swing.JTextField();
        txtUserEditar = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        txtContraEditar = new javax.swing.JPasswordField();
        btnVerContra1 = new javax.swing.JCheckBox();

        jLabel5.setText("jLabel5");

        setBackground(new java.awt.Color(60, 63, 65));

        jTabbedPane2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jPanel1.setBackground(new java.awt.Color(60, 63, 65));

        txtNombreMisDatos.setFont(new java.awt.Font("Comic Sans MS", 1, 12)); // NOI18N
        txtNombreMisDatos.setEnabled(false);
        txtNombreMisDatos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreMisDatosActionPerformed(evt);
            }
        });

        txtApellidoMisDatos.setFont(new java.awt.Font("Comic Sans MS", 1, 12)); // NOI18N
        txtApellidoMisDatos.setEnabled(false);

        txtUserMisDatos.setFont(new java.awt.Font("Comic Sans MS", 1, 12)); // NOI18N
        txtUserMisDatos.setEnabled(false);

        jLabel10.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Nombre:");

        jLabel11.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Apellido:");

        jLabel12.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Usuario:");

        jlabel1.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jlabel1.setForeground(new java.awt.Color(255, 255, 255));
        jlabel1.setText("Contraseña:");

        txtContraMisDatos.setFont(new java.awt.Font("Comic Sans MS", 1, 12)); // NOI18N
        txtContraMisDatos.setEnabled(false);

        btnVerContra.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        btnVerContra.setForeground(new java.awt.Color(255, 255, 255));
        btnVerContra.setText("Mostrar Contraseña");
        btnVerContra.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnVerContra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerContraActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jlabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel10))
                .addGap(63, 63, 63)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnVerContra)
                    .addComponent(txtUserMisDatos, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtContraMisDatos, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombreMisDatos, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtApellidoMisDatos, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(322, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtNombreMisDatos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(62, 62, 62)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtApellidoMisDatos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addGap(62, 62, 62)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtUserMisDatos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addGap(62, 62, 62)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtContraMisDatos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlabel1))
                .addGap(36, 36, 36)
                .addComponent(btnVerContra)
                .addContainerGap(120, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Mis Datos", jPanel1);

        jPanel2.setBackground(new java.awt.Color(60, 63, 65));

        txtNombreEditar.setFont(new java.awt.Font("Comic Sans MS", 1, 12)); // NOI18N
        txtNombreEditar.setForeground(new java.awt.Color(255, 255, 255));
        txtNombreEditar.setRequestFocusEnabled(false);

        txtApellidoEditar.setFont(new java.awt.Font("Comic Sans MS", 1, 12)); // NOI18N
        txtApellidoEditar.setForeground(new java.awt.Color(255, 255, 255));

        txtUserEditar.setFont(new java.awt.Font("Comic Sans MS", 1, 12)); // NOI18N
        txtUserEditar.setForeground(new java.awt.Color(255, 255, 255));

        jLabel6.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Nombre:");

        jLabel7.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Apellido:");

        jLabel8.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Usuario:");

        jLabel9.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Contraseña:");

        jButton1.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jButton1.setText("Editar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        txtContraEditar.setFont(new java.awt.Font("Comic Sans MS", 1, 12)); // NOI18N

        btnVerContra1.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        btnVerContra1.setForeground(new java.awt.Color(255, 255, 255));
        btnVerContra1.setText("Mostrar Contraseña");
        btnVerContra1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnVerContra1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerContra1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addGap(76, 76, 76)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnVerContra1)
                    .addComponent(txtNombreEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtApellidoEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtUserEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtContraEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addContainerGap(322, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtNombreEditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(62, 62, 62)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtApellidoEditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(62, 62, 62)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtUserEditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(62, 62, 62)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtContraEditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(31, 31, 31)
                .addComponent(btnVerContra1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(43, 43, 43))
        );

        jTabbedPane2.addTab("Editar Datos", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(132, 132, 132)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 736, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(192, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(36, Short.MAX_VALUE)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtNombreMisDatosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreMisDatosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreMisDatosActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        actualizar();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnVerContraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerContraActionPerformed
        if (btnVerContra.isSelected()) {
            txtContraMisDatos.setEchoChar((char) 0);
        } else {
            txtContraMisDatos.setEchoChar('*');
        }
    }//GEN-LAST:event_btnVerContraActionPerformed

    private void btnVerContra1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerContra1ActionPerformed
       if (btnVerContra.isSelected()) {
            txtContraEditar.setEchoChar((char) 0);
        } else {
            txtContraEditar.setEchoChar('*');
        }
    }//GEN-LAST:event_btnVerContra1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox btnVerContra;
    private javax.swing.JCheckBox btnVerContra1;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JLabel jlabel1;
    private javax.swing.JTextField txtApellidoEditar;
    private javax.swing.JTextField txtApellidoMisDatos;
    private javax.swing.JPasswordField txtContraEditar;
    private javax.swing.JPasswordField txtContraMisDatos;
    private javax.swing.JTextField txtNombreEditar;
    private javax.swing.JTextField txtNombreMisDatos;
    private javax.swing.JTextField txtUserEditar;
    private javax.swing.JTextField txtUserMisDatos;
    // End of variables declaration//GEN-END:variables
}
