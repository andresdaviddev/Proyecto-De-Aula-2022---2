/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package dashboard;

import domain.Conexion;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class panelHome extends javax.swing.JPanel {
    double totalIngresos;
    double contadorPrecio;
    int cantidad;
    int cantidadHoy;
    int despachados;
    String documento;
    String datos;
    /**
     * Creates new form panelHome
     *
     * @param documento
     * @param datos
     */
    public panelHome(String documento, String datos) {
        initComponents();
        this.documento = documento;
        this.datos = datos;
        cantidadRegistros();
        cantidadRegistrosHoy();
        pedidosDespachados();
        fecha();
        grafica();
        etiDatos.setText(this.datos);
        SELECT();
    }

    public panelHome(){
        
    }
    
    public final void grafica() {
        DefaultCategoryDataset datoss = new DefaultCategoryDataset();
        datoss.setValue(this.cantidad, " Pedidos Totales", "");
        datoss.setValue(this.cantidadHoy, " Pedidos de Hoy", "");
        datoss.setValue(this.despachados, "Pedidos Despachados", "");
        
        JFreeChart grafico = ChartFactory.createBarChart3D(
                "Gráfica de pedidos",
                "Pedidos",
                "Número de pedidos",
                datoss,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
                
        );
        
        ChartPanel panel = new ChartPanel(grafico);
        panel.setMouseWheelEnabled(true);
        panel.setPreferredSize(new Dimension(360, 413));
        panelGrafica.setLayout(new BorderLayout());
        panelGrafica.add(panel, BorderLayout.CENTER);

        repaint();

    }

    public final void fecha() {

        SimpleDateFormat dia = new SimpleDateFormat("dd");
        String a = dia.format(new Date());
        etiDia.setText(a);
        SimpleDateFormat mes = new SimpleDateFormat("MM");
        String b = mes.format(new Date());
        etiMes.setText(b);
        SimpleDateFormat año = new SimpleDateFormat("YYYY");
        String c = año.format(new Date());
        etiAño.setText(c);
    }

    public final void cantidadRegistrosHoy() {

        Conexion con = new Conexion();
        Connection cx = con.getConexion();
        SimpleDateFormat fecha = new SimpleDateFormat("dd-MM-yyyy");
        String date = fecha.format(new Date());
        String SELECT = "SELECT codigo FROM test.basedatospro WHERE fechaEnv = ?";
        try {
            try ( PreparedStatement ps = cx.prepareStatement(SELECT)) {
                ps.setString(1, date);
                try ( ResultSet rs = ps.executeQuery()) {
                    int valor = 0;
                    while (rs.next()) {
                        valor += 1;
                    }
                    etiValue2.setText(valor + "");
                    this.cantidadHoy = valor;
                }
            }
            cx.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error sql : " + ex.getMessage());
        }
    }

    public final void cantidadRegistros() {

        Conexion con = new Conexion();
        Connection cx = con.getConexion();
        String SELECT = "SELECT codigo, precio FROM test.basedatospro;";
        try {
            try ( PreparedStatement ps = cx.prepareStatement(SELECT);  ResultSet rs = ps.executeQuery()) {
                int value = 0;
                int val = 0;
                if (rs.next()) {
                    do {
                        value += 1;
                        this.contadorPrecio += rs.getDouble(2);
                        val = val + rs.getInt(2);
                    } while (rs.next());
                    etiValue.setText(value + "");
                    this.totalIngresos = this.contadorPrecio * 0.16;
                    etiIngresos.setText("$ "+this.totalIngresos);
                    etiIngresosProcesados.setText("$ "+val);
                }
                this.cantidad = value;
            }
            cx.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error sql : " + ex.getMessage());
        }
    }
    
    public final void pedidosDespachados(){
        Conexion con = new Conexion();
        Connection cx = con.getConexion();
        String estado = "Despachado";
        String select = "SELECT codigo FROM test.basedatospro WHERE estado = '"+estado+"'";
        try {
            try ( PreparedStatement ps = cx.prepareStatement(select);  ResultSet rs = ps.executeQuery()) {
                int value = 0;
                if (rs.next()) {
                    do {
                        value += 1;
                    } while (rs.next());
                    etiPedidosDespachados.setText(value + "");
                }
                this.despachados = value;
            }
            cx.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error sql : " + ex.getMessage());
        }
    }

    public final void SELECT() {

        try {
            Conexion con = new Conexion();
            try ( Connection cx = con.getConexion()) {
                String select = "SELECT nombre, apellido  FROM test.usuarios WHERE documento = '" + this.documento + "'";
                PreparedStatement ps = cx.prepareStatement(select);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    etiDatos.setText("Bienvenido " + rs.getString(1) + " " + rs.getString(2));
                }
                    
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al seleccionar " + e.getMessage());
        }
    }

    /**
     *
     * /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        contenido = new javax.swing.JPanel();
        etiDatos = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        panelRound6 = new dashborads.PanelRound();
        etiIngresos = new javax.swing.JLabel();
        panelRound7 = new dashborads.PanelRound();
        etiIngresosProcesados = new javax.swing.JLabel();
        panelRound2 = new dashborads.PanelRound();
        panelRound3 = new dashborads.PanelRound();
        etiValue = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        panelRound1 = new dashborads.PanelRound();
        etiValue2 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        panelRound4 = new dashborads.PanelRound();
        jLabel4 = new javax.swing.JLabel();
        etiPedidosDespachados = new javax.swing.JLabel();
        panelRound5 = new dashborads.PanelRound();
        etiDia = new javax.swing.JLabel();
        etiMes = new javax.swing.JLabel();
        etiAño = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        panelGrafica = new dashborads.PanelRound();
        btnResumenFinaciero = new javax.swing.JButton();

        setBackground(new java.awt.Color(60, 63, 65));

        contenido.setBackground(new java.awt.Color(60, 63, 65));

        etiDatos.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        etiDatos.setForeground(new java.awt.Color(255, 255, 255));

        jLabel5.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Beneficios:");

        jLabel6.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Ingresos Procesados:");

        panelRound6.setBackground(new java.awt.Color(30, 30, 30));
        panelRound6.setRoundBottomLeft(40);
        panelRound6.setRoundBottomRight(40);
        panelRound6.setRoundTopLeft(40);
        panelRound6.setRoundTopRight(40);

        etiIngresos.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        etiIngresos.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelRound6Layout = new javax.swing.GroupLayout(panelRound6);
        panelRound6.setLayout(panelRound6Layout);
        panelRound6Layout.setHorizontalGroup(
            panelRound6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound6Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(etiIngresos, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );
        panelRound6Layout.setVerticalGroup(
            panelRound6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound6Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(etiIngresos, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        panelRound7.setBackground(new java.awt.Color(30, 30, 30));
        panelRound7.setRoundBottomLeft(40);
        panelRound7.setRoundBottomRight(40);
        panelRound7.setRoundTopLeft(40);
        panelRound7.setRoundTopRight(40);

        etiIngresosProcesados.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        etiIngresosProcesados.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelRound7Layout = new javax.swing.GroupLayout(panelRound7);
        panelRound7.setLayout(panelRound7Layout);
        panelRound7Layout.setHorizontalGroup(
            panelRound7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound7Layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(etiIngresosProcesados, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelRound7Layout.setVerticalGroup(
            panelRound7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound7Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(etiIngresosProcesados, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        panelRound2.setBackground(new java.awt.Color(40, 40, 40));
        panelRound2.setRoundBottomLeft(40);
        panelRound2.setRoundBottomRight(40);
        panelRound2.setRoundTopLeft(40);
        panelRound2.setRoundTopRight(40);

        panelRound3.setBackground(new java.awt.Color(30, 30, 30));
        panelRound3.setRoundBottomLeft(50);
        panelRound3.setRoundBottomRight(50);
        panelRound3.setRoundTopLeft(50);
        panelRound3.setRoundTopRight(50);

        etiValue.setFont(new java.awt.Font("Comic Sans MS", 1, 45)); // NOI18N
        etiValue.setForeground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Total Pedidos");

        javax.swing.GroupLayout panelRound3Layout = new javax.swing.GroupLayout(panelRound3);
        panelRound3.setLayout(panelRound3Layout);
        panelRound3Layout.setHorizontalGroup(
            panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound3Layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(etiValue, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap(73, Short.MAX_VALUE))
        );
        panelRound3Layout.setVerticalGroup(
            panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(36, 36, 36)
                .addComponent(etiValue, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(59, Short.MAX_VALUE))
        );

        panelRound1.setBackground(new java.awt.Color(30, 30, 30));
        panelRound1.setRoundBottomLeft(50);
        panelRound1.setRoundBottomRight(50);
        panelRound1.setRoundTopLeft(50);
        panelRound1.setRoundTopRight(50);

        etiValue2.setFont(new java.awt.Font("Comic Sans MS", 1, 45)); // NOI18N
        etiValue2.setForeground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Pedidos De Hoy");

        javax.swing.GroupLayout panelRound1Layout = new javax.swing.GroupLayout(panelRound1);
        panelRound1.setLayout(panelRound1Layout);
        panelRound1Layout.setHorizontalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound1Layout.createSequentialGroup()
                .addContainerGap(61, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(56, 56, 56))
            .addGroup(panelRound1Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(etiValue2, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelRound1Layout.setVerticalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(32, 32, 32)
                .addComponent(etiValue2, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(55, Short.MAX_VALUE))
        );

        panelRound4.setBackground(new java.awt.Color(30, 30, 30));
        panelRound4.setRoundBottomLeft(50);
        panelRound4.setRoundBottomRight(50);
        panelRound4.setRoundTopLeft(50);
        panelRound4.setRoundTopRight(50);
        panelRound4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Pedidos Despachados");
        panelRound4.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, -1, -1));

        etiPedidosDespachados.setFont(new java.awt.Font("Comic Sans MS", 1, 50)); // NOI18N
        etiPedidosDespachados.setForeground(new java.awt.Color(255, 255, 255));
        panelRound4.add(etiPedidosDespachados, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 70, 90, 60));

        panelRound5.setBackground(new java.awt.Color(30, 30, 30));
        panelRound5.setRoundBottomLeft(50);
        panelRound5.setRoundBottomRight(50);
        panelRound5.setRoundTopLeft(50);
        panelRound5.setRoundTopRight(50);

        etiDia.setFont(new java.awt.Font("Comic Sans MS", 1, 45)); // NOI18N
        etiDia.setForeground(new java.awt.Color(255, 255, 255));

        etiMes.setFont(new java.awt.Font("Comic Sans MS", 1, 45)); // NOI18N
        etiMes.setForeground(new java.awt.Color(255, 255, 255));

        etiAño.setFont(new java.awt.Font("Comic Sans MS", 1, 45)); // NOI18N
        etiAño.setForeground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Fecha");

        javax.swing.GroupLayout panelRound5Layout = new javax.swing.GroupLayout(panelRound5);
        panelRound5.setLayout(panelRound5Layout);
        panelRound5Layout.setHorizontalGroup(
            panelRound5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound5Layout.createSequentialGroup()
                .addContainerGap(40, Short.MAX_VALUE)
                .addGroup(panelRound5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound5Layout.createSequentialGroup()
                        .addComponent(etiDia, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(etiMes, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(17, 17, 17))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound5Layout.createSequentialGroup()
                        .addComponent(etiAño, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound5Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33))))
        );
        panelRound5Layout.setVerticalGroup(
            panelRound5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound5Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addGroup(panelRound5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(etiDia, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(etiMes, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(etiAño, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panelGrafica.setBackground(new java.awt.Color(30, 30, 30));
        panelGrafica.setPreferredSize(new java.awt.Dimension(350, 350));
        panelGrafica.setRoundBottomLeft(50);
        panelGrafica.setRoundBottomRight(50);
        panelGrafica.setRoundTopLeft(50);
        panelGrafica.setRoundTopRight(50);

        javax.swing.GroupLayout panelGraficaLayout = new javax.swing.GroupLayout(panelGrafica);
        panelGrafica.setLayout(panelGraficaLayout);
        panelGraficaLayout.setHorizontalGroup(
            panelGraficaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 360, Short.MAX_VALUE)
        );
        panelGraficaLayout.setVerticalGroup(
            panelGraficaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelRound2Layout = new javax.swing.GroupLayout(panelRound2);
        panelRound2.setLayout(panelRound2Layout);
        panelRound2Layout.setHorizontalGroup(
            panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound2Layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addGroup(panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelRound2Layout.createSequentialGroup()
                        .addComponent(panelRound4, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(panelRound5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelRound2Layout.createSequentialGroup()
                        .addComponent(panelRound3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addComponent(panelRound1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addComponent(panelGrafica, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
        );
        panelRound2Layout.setVerticalGroup(
            panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelGrafica, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 413, Short.MAX_VALUE)
                    .addGroup(panelRound2Layout.createSequentialGroup()
                        .addGroup(panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(panelRound1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panelRound3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panelRound4, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panelRound5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        btnResumenFinaciero.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        btnResumenFinaciero.setForeground(new java.awt.Color(255, 255, 255));
        btnResumenFinaciero.setText("Ir al resumen finaciero");
        btnResumenFinaciero.setBorder(null);
        btnResumenFinaciero.setBorderPainted(false);
        btnResumenFinaciero.setContentAreaFilled(false);
        btnResumenFinaciero.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnResumenFinaciero.setFocusPainted(false);
        btnResumenFinaciero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResumenFinacieroActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout contenidoLayout = new javax.swing.GroupLayout(contenido);
        contenido.setLayout(contenidoLayout);
        contenidoLayout.setHorizontalGroup(
            contenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contenidoLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(contenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, contenidoLayout.createSequentialGroup()
                        .addComponent(etiDatos, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(contenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panelRound6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnResumenFinaciero))
                        .addGap(18, 18, 18)
                        .addGroup(contenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panelRound7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(39, 39, 39))
                    .addGroup(contenidoLayout.createSequentialGroup()
                        .addComponent(panelRound2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(39, Short.MAX_VALUE))))
        );
        contenidoLayout.setVerticalGroup(
            contenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contenidoLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(contenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5)
                    .addComponent(etiDatos, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(contenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelRound7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelRound6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnResumenFinaciero)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addComponent(panelRound2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
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

    private void btnResumenFinacieroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResumenFinacieroActionPerformed
        panelFinanciera pf = new panelFinanciera(this.contenido, etiDatos.getText());
        pf.setSize(1060, 650);
        pf.setLocation(0, 0);
        
        this.contenido.removeAll();
        this.contenido.add(pf, BorderLayout.CENTER);
        this.contenido.revalidate();
        this.contenido.repaint();
        etiDatos.setText(this.datos);
    }//GEN-LAST:event_btnResumenFinacieroActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnResumenFinaciero;
    private javax.swing.JPanel contenido;
    private javax.swing.JLabel etiAño;
    private javax.swing.JLabel etiDatos;
    private javax.swing.JLabel etiDia;
    private javax.swing.JLabel etiIngresos;
    private javax.swing.JLabel etiIngresosProcesados;
    private javax.swing.JLabel etiMes;
    private javax.swing.JLabel etiPedidosDespachados;
    private javax.swing.JLabel etiValue;
    private javax.swing.JLabel etiValue2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private dashborads.PanelRound panelGrafica;
    private dashborads.PanelRound panelRound1;
    private dashborads.PanelRound panelRound2;
    private dashborads.PanelRound panelRound3;
    private dashborads.PanelRound panelRound4;
    private dashborads.PanelRound panelRound5;
    private dashborads.PanelRound panelRound6;
    private dashborads.PanelRound panelRound7;
    // End of variables declaration//GEN-END:variables
}
