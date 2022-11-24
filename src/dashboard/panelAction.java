/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package dashboard;

import domain.Conexion;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.sql.*;
import javax.swing.JOptionPane;
import utilities.panelEditProduct;

/**
 *
 * @author andre
 */
public final class panelAction extends javax.swing.JPanel {

    String codigo;
    TableRowSorter trs = null;
    DefaultTableModel modelo = new DefaultTableModel();

    /**
     * Creates new form panelAction
     */
    public panelAction() {
        initComponents();
        modelo.addColumn("Codigo");
        modelo.addColumn("Nombre");
        modelo.addColumn("Fecha");
        modelo.addColumn("Estado");
        modelo.addColumn("Nombre");
        modelo.addColumn("Dni");
        modelo.addColumn("Nombre");
        modelo.addColumn("Dni");
        tablaBuscar.setModel(modelo);
        seleccionar();
    }

    public void seleccionar() {

        try {
            Conexion con = new Conexion();
            try ( Connection cx = con.getConexion()) {
                String SELECT = "SELECT codigo, nombreprod,fechaEnv, estado, nombreVend, dniVend, nombreComp,dniComp FROM test.basedatospro;";

                PreparedStatement ps = cx.prepareStatement(SELECT);
                ResultSet rs = ps.executeQuery();
                ResultSetMetaData rsmt = rs.getMetaData();
                int value = rsmt.getColumnCount();
                Object[] filas = new Object[value];
                while (rs.next()) {
                    for (int i = 0; i < value; i++) {
                        filas[i] = rs.getObject(i + 1);
                    }
                    modelo.addRow(filas);
                }
                cx.close();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error sql : " + ex.getMessage());
        }

    }

    public void clean() {
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i -= 1;
        }
    }

    public final void eliminar() {
        int fila = tablaBuscar.getSelectedRow();
        String valor = tablaBuscar.getValueAt(fila, 0).toString();

        try {
            Conexion con = new Conexion();
            try ( Connection cx = con.getConexion()) {
                String DELETE = "DELETE FROM test.basedatospro WHERE codigo = '" + valor + "'";
                PreparedStatement ps = cx.prepareStatement(DELETE);
                int respuesta = JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar este pedido?");
                if (respuesta == JOptionPane.YES_OPTION) {
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Se ha eliminado el pedido exitosamente");
                    clean();
                    seleccionar();
                } else {
                    return;
                }

                cx.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "error sql (borrar)" + e.getMessage());
        }
    }

    public final void despachar() {
        Conexion con = new Conexion();
        Connection cx = con.getConexion();
        String estado = "Despachado";
        int fila = tablaBuscar.getSelectedRow();
        String valor = tablaBuscar.getValueAt(fila, 0).toString();
        int value = 0;
        String update = "UPDATE test.basedatospro SET estado = '" + estado + "'" + " WHERE codigo = '" + valor + "'";
        try {
            PreparedStatement ps = cx.prepareStatement(update);
            int respuesta = JOptionPane.showInternalConfirmDialog(null, "¿Estás seguro de despachar este pedido?");
            if (respuesta == JOptionPane.YES_NO_OPTION) {
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Pedido Despachado con exito ");
            } else {
                return;
            }
            clean();
            seleccionar();
            cx.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "error al despachar " + e.getMessage());
        }
    }

    public final void enviarCodigo() {
        int fila = tablaBuscar.getSelectedRow();
        this.codigo = tablaBuscar.getValueAt(fila, 0).toString();

    }

    public final void cancelarPedido(){
        Conexion con = new Conexion();
        Connection cx = con.getConexion();
        String estado = "Cancelado";
        int fila = tablaBuscar.getSelectedRow();
        String valor = tablaBuscar.getValueAt(fila, 0).toString();
        int value = 0;
        String update = "UPDATE test.basedatospro SET estado = '" + estado + "'" + " WHERE codigo = '" + valor + "'";
        try {
            PreparedStatement ps = cx.prepareStatement(update);
            int respuesta = JOptionPane.showInternalConfirmDialog(null, "¿Estás seguro de cancelar este pedido?");
            if (respuesta == JOptionPane.YES_NO_OPTION) {
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Pedido cancelado con exito");
            } else {
                return;
            }
            clean();
            seleccionar();
            cx.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "error al despachar " + e.getMessage());
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tablaBuscar = new dashborads.TableDark();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnBorrar = new javax.swing.JButton();
        btnOpcion = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEnviar = new javax.swing.JButton();
        txtFiltrado = new utilities.TextField();

        setBackground(new java.awt.Color(60, 63, 65));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                formMouseExited(evt);
            }
        });

        tablaBuscar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tablaBuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaBuscarMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaBuscar);

        jLabel1.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Buscar Por:");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/lupa.png"))); // NOI18N
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel2MouseEntered(evt);
            }
        });

        btnBorrar.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        btnBorrar.setForeground(new java.awt.Color(255, 255, 255));
        btnBorrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/borrar.png"))); // NOI18N
        btnBorrar.setText("Eliminar");
        btnBorrar.setBorder(null);
        btnBorrar.setBorderPainted(false);
        btnBorrar.setContentAreaFilled(false);
        btnBorrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBorrar.setFocusPainted(false);
        btnBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarActionPerformed(evt);
            }
        });

        btnOpcion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Código", "Dni Vendedor", "Dni Comprador" }));

        jLabel3.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Producto");

        jLabel4.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Vendedor");

        jLabel5.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Comprador");

        btnCancelar.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        btnCancelar.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cancelar.png"))); // NOI18N
        btnCancelar.setText("Cancelar Pedido");
        btnCancelar.setBorder(null);
        btnCancelar.setBorderPainted(false);
        btnCancelar.setContentAreaFilled(false);
        btnCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancelar.setFocusPainted(false);
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnEditar.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        btnEditar.setForeground(new java.awt.Color(255, 255, 255));
        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/editar.png"))); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.setBorder(null);
        btnEditar.setBorderPainted(false);
        btnEditar.setContentAreaFilled(false);
        btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditar.setFocusPainted(false);
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnEnviar.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        btnEnviar.setForeground(new java.awt.Color(255, 255, 255));
        btnEnviar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/send.png"))); // NOI18N
        btnEnviar.setText("Despachar Pedido");
        btnEnviar.setBorder(null);
        btnEnviar.setBorderPainted(false);
        btnEnviar.setContentAreaFilled(false);
        btnEnviar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEnviar.setFocusPainted(false);
        btnEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarActionPerformed(evt);
            }
        });

        txtFiltrado.setBackground(new java.awt.Color(60, 63, 65));
        txtFiltrado.setForeground(new java.awt.Color(255, 255, 255));
        txtFiltrado.setShadowColor(new java.awt.Color(255, 102, 102));
        txtFiltrado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFiltradoKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(426, 426, 426)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(154, 154, 154)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnBorrar)
                                .addGap(60, 60, 60)
                                .addComponent(btnEditar)
                                .addGap(66, 66, 66)
                                .addComponent(btnEnviar)
                                .addGap(49, 49, 49)
                                .addComponent(btnCancelar))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnOpcion, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(9, 9, 9)
                                .addComponent(txtFiltrado, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(414, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnOpcion, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtFiltrado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 426, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBorrar)
                    .addComponent(btnCancelar)
                    .addComponent(btnEditar)
                    .addComponent(btnEnviar))
                .addGap(27, 27, 27))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarActionPerformed
        int row = tablaBuscar.getSelectedRow();
        if (row > -1) {
            eliminar();
        } else {
            JOptionPane.showMessageDialog(null, "por favor, seleccione una columna para borrar");
        }

    }//GEN-LAST:event_btnBorrarActionPerformed

    private void formMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseExited
    }//GEN-LAST:event_formMouseExited

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        int row = tablaBuscar.getSelectedRow();
        if (row > -1) {
            cancelarPedido();
        } else {
            JOptionPane.showMessageDialog(null, "por favor, seleccione un pedido para cancelar su envío");
        }
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        int row = tablaBuscar.getSelectedRow();
        if (row > -1) {
            enviarCodigo();
            panelEditProduct pe = new panelEditProduct(codigo);
            pe.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "por favor, seleccione una columna para editar");
        }

    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarActionPerformed
        int row = tablaBuscar.getSelectedRow();
        if (row > -1) {
            despachar();
        } else {
            JOptionPane.showMessageDialog(null, "por favor, seleccione un pedido para despachar");
        }
        
    }//GEN-LAST:event_btnEnviarActionPerformed

    private void jLabel2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseEntered

    }//GEN-LAST:event_jLabel2MouseEntered

    private void tablaBuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaBuscarMouseClicked

    }//GEN-LAST:event_tablaBuscarMouseClicked

    private void txtFiltradoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFiltradoKeyTyped
        String op = btnOpcion.getSelectedItem().toString();
        int value = 0;
        if (op.equalsIgnoreCase("Código")) {
            value = 1;
        }
        if (op.equalsIgnoreCase("Dni Vendedor")) {
            value = 2;
        }
        if (op.equalsIgnoreCase("Dni Comprador")) {
            value = 3;
        }

        switch (value) {

            case 1 -> {
                txtFiltrado.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyReleased(final KeyEvent e) {
                        trs.setRowFilter(RowFilter.regexFilter(txtFiltrado.getText(), 0));
                    }

                });
                trs = new TableRowSorter(modelo);
                tablaBuscar.setRowSorter(trs);
                break;
            }

            case 2 -> {
                txtFiltrado.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyReleased(final KeyEvent e) {
                        trs.setRowFilter(RowFilter.regexFilter(txtFiltrado.getText(), 5));
                    }

                });
                trs = new TableRowSorter(modelo);
                tablaBuscar.setRowSorter(trs);
                break;
            }

            case 3 -> {
                txtFiltrado.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyReleased(final KeyEvent e) {
                        trs.setRowFilter(RowFilter.regexFilter(txtFiltrado.getText(), 7));
                    }

                });
                trs = new TableRowSorter(modelo);
                tablaBuscar.setRowSorter(trs);
                break;
            }

        }
    }//GEN-LAST:event_txtFiltradoKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBorrar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEnviar;
    private javax.swing.JComboBox<String> btnOpcion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private dashborads.TableDark tablaBuscar;
    private utilities.TextField txtFiltrado;
    // End of variables declaration//GEN-END:variables
}
