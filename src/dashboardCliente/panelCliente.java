/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package dashboardCliente;
//765, 650

import domain.Conexion;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author andre
 */
public class panelCliente extends javax.swing.JFrame {

    DefaultTableModel modelo = new DefaultTableModel();
    DefaultTableModel modelo2 = new DefaultTableModel();

    String documento;

    /**
     * Creates new form panelCliente
     *
     * @param documento
     */
    public panelCliente(String documento) {
        initComponents();
        this.setTitle("Software para la gestión de Exportación e Importación");

        this.documento = documento;

        pedidosRecibir();
        pedidosEnviar();
        tablaPedidosEnviar();
        tablaPedidosRecibir();
        seleccionarUsuarios();
    }

    public final void pedidosRecibir() {

        Conexion con = new Conexion();
        Connection cx = con.getConexion();
        String estado = "Despachado";
        String SELECT = "SELECT codigo FROM test.basedatospro WHERE dniComp = '" + this.documento + "'" + " and estado ='"+estado+"'";
        try {
            try ( PreparedStatement ps = cx.prepareStatement(SELECT);  ResultSet rs = ps.executeQuery()) {
                int value = 0;
                if (rs.next()) {
                    do {
                        value += 1;
                    } while (rs.next());
                }
                etiPedidosRecib.setText(value + "");

            }
            cx.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error sql : " + ex.getMessage());
        }

    }

    public final void pedidosEnviar() {
        Conexion con = new Conexion();
        Connection cx = con.getConexion();
        String estado = "Despachado";
        String SELECT = "SELECT codigo FROM test.basedatospro WHERE dniVend = '" + this.documento + "'" + " and estado ='"+estado+"'";
        try {
            try ( PreparedStatement ps = cx.prepareStatement(SELECT);  ResultSet rs = ps.executeQuery()) {
                int value = 0;
                if (rs.next()) {
                    do {
                        value += 1;
                    } while (rs.next());
                }
                etiPedidosEnv.setText(value + "");

            }
            cx.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error sql : " + ex.getMessage());
        }
    }

    public final void tablaPedidosRecibir() {
        modelo.addColumn("Código");
        modelo.addColumn("Nombre");
        modelo.addColumn("Valor");
        modelo.addColumn("Fecha De Envío");
        tablaArecibir.setModel(modelo);
        try {
            Conexion con = new Conexion();
            Connection cx = con.getConexion();
            
            String estado = "Despachado";
            String SELECT = "SELECT codigo, nombreprod, precio, fechaEnv FROM test.basedatospro WHERE dniComp = '" + this.documento + "'" + " and estado ='"+estado+"'";
            PreparedStatement ps = cx.prepareStatement(SELECT);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData rsmt = rs.getMetaData();
            int cantidad = rsmt.getColumnCount();
            Object[] filas = new Object[cantidad];
            while (rs.next()) {
                for (int i = 0; i < cantidad; i++) {
                    filas[i] = rs.getObject(i + 1);
                }
                modelo.addRow(filas);
            }
            cx.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public final void tablaPedidosEnviar() {
        modelo2.addColumn("Código");
        modelo2.addColumn("Nombre");
        modelo2.addColumn("Valor");
        modelo2.addColumn("Fecha De Envío");
        tablaPedidosEnviados.setModel(modelo2);
        try {
            Conexion con = new Conexion();
            Connection cx = con.getConexion();
            String estado = "Despachado";
            String SELECT = "SELECT codigo, nombreprod, precio, fechaEnv FROM test.basedatospro WHERE dniVend = '" + this.documento + "'" + " and estado ='"+estado+"'";
            PreparedStatement ps = cx.prepareStatement(SELECT);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData rsmt = rs.getMetaData();
            int cantidad = rsmt.getColumnCount();
            Object[] filas = new Object[cantidad];
            while (rs.next()) {
                for (int i = 0; i < cantidad; i++) {
                    filas[i] = rs.getObject(i + 1);
                }
                modelo2.addRow(filas);
            }
            cx.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public final void seleccionarUsuarios() {
        Conexion con = new Conexion();
        Connection cx = con.getConexion();
        String SELECT = "SELECT nombre, apellido, usuario, contraseña FROM test.usuarios WHERE documento = '" + this.documento + "'";
        try {
            try ( PreparedStatement ps = cx.prepareStatement(SELECT);  ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    txtNombre1.setText(rs.getString(1));
                    txtApellido1.setText(rs.getString(2));
                    txtUser1.setText(rs.getString(3));
                    txtPass1.setText(rs.getString(4));
                    txtNombre2.setText(rs.getString(1));
                    txtApellido2.setText(rs.getString(2));
                    txtUser2.setText(rs.getString(3));
                    txtPass2.setText(rs.getString(4));
                }
                etiBienvenida.setText(rs.getString(1) + " " + rs.getString(2));
            }

            cx.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error sql : " + ex.getMessage());
        }
    }

    public final void actualizar() {
        Conexion con = new Conexion();
        Connection cx = con.getConexion();
        String nombre = txtNombre2.getText();
        String apellido = txtApellido2.getText();
        String user = txtUser2.getText();
        String contra = txtPass2.getText();
        String UPDATE = "UPDATE test.usuarios"
                + " SET nombre = '" + nombre + "'"
                + ",apellido = '" + apellido + "'" + ", usuario = '" + user + "'"
                + ", contraseña = '" + contra + "'" + "WHERE documento = '" + this.documento + "'";

        try {
            PreparedStatement ps = cx.prepareStatement(UPDATE);
            int respuesta = JOptionPane.showConfirmDialog(null, "¿Estás seguro de editar tus datos?");
            if (respuesta == JOptionPane.YES_OPTION) {
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Datos Actualizados Exitosamente");
                seleccionarUsuarios();
            }

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

        jPanel1 = new javax.swing.JPanel();
        etiBienvenida = new javax.swing.JLabel();
        panelRound1 = new dashborads.PanelRound();
        jLabel4 = new javax.swing.JLabel();
        etiPedidosRecib = new javax.swing.JLabel();
        panelRound2 = new dashborads.PanelRound();
        jLabel5 = new javax.swing.JLabel();
        etiPedidosEnv = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaPedidosEnviados = new dashborads.TableDark();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaArecibir = new dashborads.TableDark();
        btnAbrirDetalles2 = new javax.swing.JButton();
        btnAbrirDetalles1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        txtUser1 = new javax.swing.JTextField();
        txtApellido1 = new javax.swing.JTextField();
        txtNombre1 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtPass1 = new javax.swing.JPasswordField();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        txtUser2 = new javax.swing.JTextField();
        txtApellido2 = new javax.swing.JTextField();
        txtNombre2 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        btnEditar = new javax.swing.JButton();
        txtPass2 = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(60, 63, 65));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        etiBienvenida.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        etiBienvenida.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.add(etiBienvenida, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, 240, 20));

        panelRound1.setBackground(new java.awt.Color(30, 30, 30));
        panelRound1.setRoundBottomLeft(40);
        panelRound1.setRoundBottomRight(40);
        panelRound1.setRoundTopLeft(40);
        panelRound1.setRoundTopRight(40);

        jLabel4.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Pedidos a Recibir");

        etiPedidosRecib.setFont(new java.awt.Font("Comic Sans MS", 1, 45)); // NOI18N
        etiPedidosRecib.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelRound1Layout = new javax.swing.GroupLayout(panelRound1);
        panelRound1.setLayout(panelRound1Layout);
        panelRound1Layout.setHorizontalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound1Layout.createSequentialGroup()
                .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelRound1Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel4))
                    .addGroup(panelRound1Layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(etiPedidosRecib, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        panelRound1Layout.setVerticalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(etiPedidosRecib, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(70, Short.MAX_VALUE))
        );

        jPanel1.add(panelRound1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 63, -1, -1));

        panelRound2.setBackground(new java.awt.Color(30, 30, 30));
        panelRound2.setRoundBottomLeft(40);
        panelRound2.setRoundBottomRight(40);
        panelRound2.setRoundTopLeft(40);
        panelRound2.setRoundTopRight(40);

        jLabel5.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Pedidos Enviados");

        etiPedidosEnv.setFont(new java.awt.Font("Comic Sans MS", 1, 45)); // NOI18N
        etiPedidosEnv.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelRound2Layout = new javax.swing.GroupLayout(panelRound2);
        panelRound2.setLayout(panelRound2Layout);
        panelRound2Layout.setHorizontalGroup(
            panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound2Layout.createSequentialGroup()
                .addGroup(panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelRound2Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jLabel5))
                    .addGroup(panelRound2Layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(etiPedidosEnv, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        panelRound2Layout.setVerticalGroup(
            panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(etiPedidosEnv, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(70, Short.MAX_VALUE))
        );

        jPanel1.add(panelRound2, new org.netbeans.lib.awtextra.AbsoluteConstraints(198, 63, -1, -1));

        jLabel6.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Bienvenido");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jPanel2.setBackground(new java.awt.Color(60, 63, 65));

        tablaPedidosEnviados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tablaPedidosEnviados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaPedidosEnviadosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaPedidosEnviados);

        tablaArecibir.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tablaArecibir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaArecibirMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tablaArecibir);

        btnAbrirDetalles2.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        btnAbrirDetalles2.setForeground(new java.awt.Color(255, 255, 255));
        btnAbrirDetalles2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/openPdf.png"))); // NOI18N
        btnAbrirDetalles2.setText("Abrir Detalles");
        btnAbrirDetalles2.setBorder(null);
        btnAbrirDetalles2.setContentAreaFilled(false);
        btnAbrirDetalles2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAbrirDetalles2.setFocusPainted(false);
        btnAbrirDetalles2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbrirDetalles2ActionPerformed(evt);
            }
        });

        btnAbrirDetalles1.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        btnAbrirDetalles1.setForeground(new java.awt.Color(255, 255, 255));
        btnAbrirDetalles1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/openPdf.png"))); // NOI18N
        btnAbrirDetalles1.setText("Abrir Detalles");
        btnAbrirDetalles1.setBorder(null);
        btnAbrirDetalles1.setContentAreaFilled(false);
        btnAbrirDetalles1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAbrirDetalles1.setFocusPainted(false);
        btnAbrirDetalles1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbrirDetalles1ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Pedidos Vendidos:");

        jLabel2.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Pedidos Comprados:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 718, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 718, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAbrirDetalles2)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAbrirDetalles1)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnAbrirDetalles1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnAbrirDetalles2)
                .addGap(16, 16, 16))
        );

        jTabbedPane1.addTab("Resumen De Pedidos", jPanel2);

        jPanel3.setBackground(new java.awt.Color(40, 40, 40));

        jPanel6.setBackground(new java.awt.Color(60, 63, 65));

        txtUser1.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        txtUser1.setEnabled(false);
        txtUser1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUser1ActionPerformed(evt);
            }
        });

        txtApellido1.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        txtApellido1.setEnabled(false);

        txtNombre1.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        txtNombre1.setEnabled(false);

        jLabel12.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Nombre:");

        jLabel13.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Apellido:");

        jLabel14.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Usuario");

        jLabel15.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Contraseña:");

        txtPass1.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        txtPass1.setEnabled(false);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                        .addComponent(txtPass1, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14))
                        .addGap(71, 71, 71)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtApellido1, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNombre1, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtUser1, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(331, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombre1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addGap(53, 53, 53)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtApellido1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addGap(56, 56, 56)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addGap(56, 56, 56)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtPass1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(199, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 755, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 537, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("Mis Datos", jPanel3);

        jPanel4.setBackground(new java.awt.Color(40, 40, 40));

        jPanel5.setBackground(new java.awt.Color(60, 63, 65));

        txtUser2.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N

        txtApellido2.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N

        txtNombre2.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Nombre:");

        jLabel9.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Apellido:");

        jLabel10.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Usuario");

        jLabel11.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Contraseña:");

        btnEditar.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        btnEditar.setForeground(new java.awt.Color(255, 255, 255));
        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        txtPass2.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addContainerGap(163, Short.MAX_VALUE)
                            .addComponent(btnEditar)
                            .addGap(189, 189, 189))
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addGap(37, 37, 37)
                            .addComponent(jLabel11)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtPass2, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10))
                        .addGap(71, 71, 71)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtApellido2, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNombre2, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtUser2, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(331, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombre2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(53, 53, 53)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtApellido2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(56, 56, 56)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(56, 56, 56)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtPass2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(72, 72, 72)
                .addComponent(btnEditar)
                .addContainerGap(105, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 755, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 537, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("Editar Datos", jPanel4);

        jPanel1.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 40, 730, 560));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1250, 650));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtUser1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUser1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUser1ActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        actualizar();
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnAbrirDetalles2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbrirDetalles2ActionPerformed
        int row = tablaPedidosEnviados.getSelectedRow();

        if (row > -1) {
            int fila = tablaArecibir.getSelectedRow();
            String valor = tablaArecibir.getValueAt(fila, 0).toString();

            try {
                File path = new File("C:\\Users\\andre\\Documents\\java\\javaClases\\facturas\\factura_producto" + valor + ".pdf");
                Desktop.getDesktop().open(path);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Aún no se ha generado un reporte " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecciona un pedido para ver los detalles");
        }


    }//GEN-LAST:event_btnAbrirDetalles2ActionPerformed

    private void btnAbrirDetalles1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbrirDetalles1ActionPerformed
        int row = tablaPedidosEnviados.getSelectedRow();
        if (row > -1) {
            int fila = tablaPedidosEnviados.getSelectedRow();
            String valor = tablaPedidosEnviados.getValueAt(fila, 0).toString();

            try {
                File path = new File("C:\\Users\\andre\\Documents\\java\\javaClases\\facturas\\factura_producto" + valor + ".pdf");
                Desktop.getDesktop().open(path);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Aún no se ha generado un reporte " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecciona un pedido para ver los detalles");
        }


    }//GEN-LAST:event_btnAbrirDetalles1ActionPerformed

    private void tablaArecibirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaArecibirMouseClicked
        btnAbrirDetalles2.setEnabled(true);
    }//GEN-LAST:event_tablaArecibirMouseClicked

    private void tablaPedidosEnviadosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaPedidosEnviadosMouseClicked
        btnAbrirDetalles1.setEnabled(true);
    }//GEN-LAST:event_tablaPedidosEnviadosMouseClicked

    /**
     * @param args the command line arguments
     * @throws javax.swing.UnsupportedLookAndFeelException
     */
//    public static void main(String args[]) throws UnsupportedLookAndFeelException {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(panelCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//        UIManager.setLookAndFeel(new FlatIntelliJLaf());
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new panelCliente().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAbrirDetalles1;
    private javax.swing.JButton btnAbrirDetalles2;
    private javax.swing.JButton btnEditar;
    private javax.swing.JLabel etiBienvenida;
    private javax.swing.JLabel etiPedidosEnv;
    private javax.swing.JLabel etiPedidosRecib;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private dashborads.PanelRound panelRound1;
    private dashborads.PanelRound panelRound2;
    private dashborads.TableDark tablaArecibir;
    private dashborads.TableDark tablaPedidosEnviados;
    private javax.swing.JTextField txtApellido1;
    private javax.swing.JTextField txtApellido2;
    private javax.swing.JTextField txtNombre1;
    private javax.swing.JTextField txtNombre2;
    private javax.swing.JPasswordField txtPass1;
    private javax.swing.JPasswordField txtPass2;
    private javax.swing.JTextField txtUser1;
    private javax.swing.JTextField txtUser2;
    // End of variables declaration//GEN-END:variables
}
