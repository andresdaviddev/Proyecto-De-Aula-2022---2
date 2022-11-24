/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package dashboard;

import domain.Conexion;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import java.sql.*;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author andre
 */
public class panelFinanciera extends javax.swing.JPanel {

    JPanel panelPed;
    JPanel panelIn;
    JPanel panel;
    String datos;
    String fecha;
    int beneficios = 0;
    int ingresos = 0;
    int registrados = 0;
    int despachados = 0;

    /**
     * Creates new form panelFinanciera
     *
     * @param panel
     * @param datos
     */
    public panelFinanciera(JPanel panel, String datos) {
        initComponents();
        this.panel = panel;
        this.datos = datos;

    }

    public final void graficoIngresos() {
        DefaultCategoryDataset datoss = new DefaultCategoryDataset();
        datoss.setValue(this.ingresos, "Ingresos", "");
        datoss.setValue(this.beneficios, "Beneficios", "");

        JFreeChart grafico = ChartFactory.createBarChart3D(
                "Ingresos",
                "",
                "Dinero en pesos COP",
                datoss,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        ChartPanel panel = new ChartPanel(grafico);
        this.panelIn = panel;
        panel.setMouseWheelEnabled(true);
        panel.setPreferredSize(new Dimension(293, 285));
        this.panelIngresos.setLayout(new BorderLayout());
        this.panelIngresos.add(panel, BorderLayout.CENTER);

        repaint();
    }

    public final void graficoPedidos() {
        DefaultCategoryDataset datoss = new DefaultCategoryDataset();
        datoss.setValue(this.registrados, "Registrados", "");
        datoss.setValue(this.despachados, "Despachados", "");

        JFreeChart graficoPed = ChartFactory.createBarChart3D(
                "Pedidos",
                "pedidos",
                "Cantidad",
                datoss,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        ChartPanel panel2 = new ChartPanel(graficoPed);
        this.panelPed = panel2;
        panel2.setMouseWheelEnabled(true);
        panel2.setPreferredSize(new Dimension(293, 285));
        this.panelPedidos.setLayout(new BorderLayout());
        this.panelPedidos.add(panel2, BorderLayout.CENTER);

        repaint();
    }

    public final void selectBeneficios() {

        Conexion con = new Conexion();
        Connection cx = con.getConexion();
        try {
            SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");
            this.fecha = date.format(txtFecha.getDate());

        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Error, debe ingresar una fecha valida");
            return;
        }
        String SELECT = "SELECT precio FROM basedatospro WHERE fechaEnv = '" + fecha + "'";
        try {
            PreparedStatement ps = cx.prepareStatement(SELECT);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                do {
                    this.beneficios += rs.getInt(1) * 0.16;
                } while (rs.next());
                etiBeneficios.setText(beneficios + "");
            }
            cx.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "error sql" + e.getMessage());
        }
    }

    public final void selectIngresos() {

        Conexion con = new Conexion();
        Connection cx = con.getConexion();
        try {
            SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");
            this.fecha = date.format(txtFecha.getDate());

        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Error, debe ingresar una fecha valida");
            return;
        }
        String SELECT = "SELECT precio FROM basedatospro WHERE fechaEnv = '" + fecha + "'";
        try {
            PreparedStatement ps = cx.prepareStatement(SELECT);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                do {
                    this.ingresos += rs.getInt(1);
                } while (rs.next());
                etiIngresos.setText(ingresos + "");
            }
            cx.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "error sql" + e.getMessage());
        }
    }

    public final void registrados() {

        Conexion con = new Conexion();
        Connection cx = con.getConexion();
        try {
            SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");
            this.fecha = date.format(txtFecha.getDate());

        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Error, debe ingresar una fecha valida");
            return;
        }
        String SELECT = "SELECT * FROM basedatospro WHERE fechaEnv = '" + fecha + "'";
        try {
            PreparedStatement ps = cx.prepareStatement(SELECT);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                
                do {
                    this.registrados += 1;
                } while (rs.next());
                etiRegistrados.setText(this.registrados + "");
            }
            cx.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "error sql" + e.getMessage());
        }
    }

    public final void despachados() {
        Conexion con = new Conexion();
        Connection cx = con.getConexion();
        try {
            SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");
            this.fecha = date.format(txtFecha.getDate());

        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Error, debe ingresar una fecha valida");
            return;
        }
        String estado = "Despachado";
        String SELECT = "SELECT * FROM basedatospro WHERE fechaEnv = '" + fecha + "'" + " and estado = '" + estado + "'";
        try {
            PreparedStatement ps = cx.prepareStatement(SELECT);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                do {
                    this.despachados += 1;
                } while (rs.next());
                etiDespachados.setText(this.despachados + "");
            }
            cx.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "error sql" + e.getMessage());
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

        contenido = new javax.swing.JPanel();
        btnRegresar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtFecha = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        panelRound3 = new dashborads.PanelRound();
        etiBeneficios = new javax.swing.JLabel();
        panelRound2 = new dashborads.PanelRound();
        etiIngresos = new javax.swing.JLabel();
        panelRound4 = new dashborads.PanelRound();
        etiRegistrados = new javax.swing.JLabel();
        panelRound5 = new dashborads.PanelRound();
        etiDespachados = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        panelIngresos = new javax.swing.JPanel();
        panelPedidos = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();

        setBackground(new java.awt.Color(60, 63, 65));

        contenido.setBackground(new java.awt.Color(60, 63, 65));

        btnRegresar.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        btnRegresar.setForeground(new java.awt.Color(255, 255, 255));
        btnRegresar.setText("Regresar al inicio");
        btnRegresar.setBorder(null);
        btnRegresar.setBorderPainted(false);
        btnRegresar.setContentAreaFilled(false);
        btnRegresar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRegresar.setFocusPainted(false);
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Resumen Financiero");

        txtFecha.setForeground(new java.awt.Color(255, 255, 255));
        txtFecha.setDateFormatString("d-MM-y");

        jLabel2.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Seleccione una fecha para ver su resumen");

        panelRound3.setBackground(new java.awt.Color(30, 30, 30));
        panelRound3.setRoundBottomLeft(40);
        panelRound3.setRoundBottomRight(40);
        panelRound3.setRoundTopLeft(40);
        panelRound3.setRoundTopRight(40);

        etiBeneficios.setFont(new java.awt.Font("Comic Sans MS", 1, 12)); // NOI18N
        etiBeneficios.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelRound3Layout = new javax.swing.GroupLayout(panelRound3);
        panelRound3.setLayout(panelRound3Layout);
        panelRound3Layout.setHorizontalGroup(
            panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(etiBeneficios, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelRound3Layout.setVerticalGroup(
            panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound3Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(etiBeneficios, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        panelRound2.setBackground(new java.awt.Color(30, 30, 30));
        panelRound2.setRoundBottomLeft(40);
        panelRound2.setRoundBottomRight(40);
        panelRound2.setRoundTopLeft(40);
        panelRound2.setRoundTopRight(40);

        etiIngresos.setFont(new java.awt.Font("Comic Sans MS", 1, 12)); // NOI18N
        etiIngresos.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelRound2Layout = new javax.swing.GroupLayout(panelRound2);
        panelRound2.setLayout(panelRound2Layout);
        panelRound2Layout.setHorizontalGroup(
            panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(etiIngresos, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelRound2Layout.setVerticalGroup(
            panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(etiIngresos, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        panelRound4.setBackground(new java.awt.Color(30, 30, 30));
        panelRound4.setRoundBottomLeft(40);
        panelRound4.setRoundBottomRight(40);
        panelRound4.setRoundTopLeft(40);
        panelRound4.setRoundTopRight(40);

        etiRegistrados.setFont(new java.awt.Font("Comic Sans MS", 1, 18)); // NOI18N
        etiRegistrados.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelRound4Layout = new javax.swing.GroupLayout(panelRound4);
        panelRound4.setLayout(panelRound4Layout);
        panelRound4Layout.setHorizontalGroup(
            panelRound4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound4Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(etiRegistrados, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );
        panelRound4Layout.setVerticalGroup(
            panelRound4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound4Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(etiRegistrados, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        panelRound5.setBackground(new java.awt.Color(30, 30, 30));
        panelRound5.setRoundBottomLeft(40);
        panelRound5.setRoundBottomRight(40);
        panelRound5.setRoundTopLeft(40);
        panelRound5.setRoundTopRight(40);

        etiDespachados.setFont(new java.awt.Font("Comic Sans MS", 1, 18)); // NOI18N
        etiDespachados.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelRound5Layout = new javax.swing.GroupLayout(panelRound5);
        panelRound5.setLayout(panelRound5Layout);
        panelRound5Layout.setHorizontalGroup(
            panelRound5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound5Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(etiDespachados, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );
        panelRound5Layout.setVerticalGroup(
            panelRound5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound5Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(etiDespachados, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jLabel3.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Pedidos:");

        jLabel4.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Ingresos:");

        panelIngresos.setBackground(new java.awt.Color(30, 30, 30));

        javax.swing.GroupLayout panelIngresosLayout = new javax.swing.GroupLayout(panelIngresos);
        panelIngresos.setLayout(panelIngresosLayout);
        panelIngresosLayout.setHorizontalGroup(
            panelIngresosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelIngresosLayout.setVerticalGroup(
            panelIngresosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        panelPedidos.setBackground(new java.awt.Color(30, 30, 30));

        javax.swing.GroupLayout panelPedidosLayout = new javax.swing.GroupLayout(panelPedidos);
        panelPedidos.setLayout(panelPedidosLayout);
        panelPedidosLayout.setHorizontalGroup(
            panelPedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 293, Short.MAX_VALUE)
        );
        panelPedidosLayout.setVerticalGroup(
            panelPedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel5.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Registrados");

        jLabel6.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Despachados");

        jLabel7.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Beneficios:");

        jLabel8.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Ingresos procesados:");

        jLabel9.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Graficos Comparativos:");

        btnBuscar.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        btnBuscar.setText("Buscar");
        btnBuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout contenidoLayout = new javax.swing.GroupLayout(contenido);
        contenido.setLayout(contenidoLayout);
        contenidoLayout.setHorizontalGroup(
            contenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contenidoLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(contenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(contenidoLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnBuscar)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(contenidoLayout.createSequentialGroup()
                        .addGroup(contenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(contenidoLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(0, 532, Short.MAX_VALUE))
                            .addGroup(contenidoLayout.createSequentialGroup()
                                .addGroup(contenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(contenidoLayout.createSequentialGroup()
                                        .addGroup(contenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(panelRound4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel5)
                                            .addComponent(jLabel4))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(contenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel6)
                                            .addComponent(panelRound5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(contenidoLayout.createSequentialGroup()
                                        .addGroup(contenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(contenidoLayout.createSequentialGroup()
                                                .addComponent(jLabel7)
                                                .addGap(57, 57, 57))
                                            .addComponent(panelRound3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnRegresar, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(12, 12, 12)
                                        .addGroup(contenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(panelRound2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel8))))
                                .addGap(84, 84, 84)
                                .addComponent(panelIngresos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addComponent(panelPedidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(74, 74, 74))
                    .addGroup(contenidoLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel9)
                        .addGap(300, 300, 300))))
        );
        contenidoLayout.setVerticalGroup(
            contenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contenidoLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel1)
                .addGap(98, 98, 98)
                .addGroup(contenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(47, 47, 47)
                .addGroup(contenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel9))
                .addGroup(contenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, contenidoLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(contenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(contenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(panelRound5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panelRound4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(39, 39, 39)
                        .addComponent(jLabel4)
                        .addGap(45, 45, 45)
                        .addGroup(contenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(contenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(panelRound2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panelRound3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(contenidoLayout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addGroup(contenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panelPedidos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelIngresos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(69, 69, 69)
                .addComponent(btnRegresar)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contenido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contenido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        panelHome ph = new panelHome("xs", this.datos);
        ph.setSize(1060, 650);
        ph.setLocation(0, 0);

        this.contenido.removeAll();
        this.contenido.add(ph, BorderLayout.CENTER);
        this.contenido.revalidate();
        this.contenido.repaint();
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        selectBeneficios();
        selectIngresos();
        registrados();
        despachados();
        graficoIngresos();
        graficoPedidos();

        this.beneficios = 0;
        this.ingresos = 0;
        this.registrados = 0;
        this.despachados = 0;

        this.panelIngresos.removeAll();
        this.panelIngresos.add(this.panelIn, BorderLayout.CENTER);
        this.panelIngresos.revalidate();
        this.panelIngresos.repaint();

        this.panelPedidos.removeAll();
        this.panelPedidos.add(this.panelPed, BorderLayout.CENTER);
        this.panelPedidos.revalidate();
        this.panelPedidos.repaint();
    }//GEN-LAST:event_btnBuscarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JPanel contenido;
    private javax.swing.JLabel etiBeneficios;
    private javax.swing.JLabel etiDespachados;
    private javax.swing.JLabel etiIngresos;
    private javax.swing.JLabel etiRegistrados;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel panelIngresos;
    private javax.swing.JPanel panelPedidos;
    private dashborads.PanelRound panelRound2;
    private dashborads.PanelRound panelRound3;
    private dashborads.PanelRound panelRound4;
    private dashborads.PanelRound panelRound5;
    private com.toedter.calendar.JDateChooser txtFecha;
    // End of variables declaration//GEN-END:variables
}
