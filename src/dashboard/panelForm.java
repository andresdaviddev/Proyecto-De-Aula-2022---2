/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package dashboard;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Image;
import domain.Conexion;
import domain.Producto;
import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import utilities.panelEditProduct;

/**
 *
 * @author andre
 */
public class panelForm extends javax.swing.JPanel {

    final static double inpuesto = 0.16;
    private static final String logo = "C:\\Users\\andre\\Documents\\java\\javaClases\\img\\barner.png";
    double porcentaje;
    double totalConInpuesto;
    long valorProd;

    String nombre;
    String apellido;

    /**
     * Creates new form panelForm
     *
     * @param nombre
     * @param apellido
     */
    public panelForm(String nombre, String apellido) {
        initComponents();
        this.nombre = nombre;
        this.apellido = apellido;
        
        
        SimpleDateFormat formato1 = new SimpleDateFormat("dd-MM-yyyy");
        String date = formato1.format(new java.util.Date());

        try {
            java.util.Date fecha = formato1.parse(date);
            txtFecha.setDate(fecha);

        } catch (ParseException ex) {
            Logger.getLogger(panelEditProduct.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public final void textosVacios() {
        txtCodigo.setText(null);
        txtNombreProducto.setText(null);
        txtPrecio.setText(null);
        txtFecha.setDate(null);
        txtNombreVendedor.setText(null);
        txtApellidoVendedor.setText(null);
        txtEmailVendedor.setText(null);
        txtDniVendedor.setText(null);
        txtNombreComprador.setText(null);
        txtApellidoComprador.setText(null);
        txtEmailComprador.setText(null);
        txtDniComprador.setText(null);
    }

    public final void agregar() {
        // instanciamos objeto de tipo producto
        Producto pro = new Producto();
        //producto
        try {
            pro.setCodigo(Integer.parseInt(txtCodigo.getText()));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error, debe Ingresar el código del producto");
            return;
        }

        pro.setNombreProducto(txtNombreProducto.getText());

        try {
            pro.setPrecioTotal(Long.parseLong(txtPrecio.getText()));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error, debe Ingresar el precio del producto");
            return;
        }

        try {
            SimpleDateFormat dates = new SimpleDateFormat("dd-MM-yyyy");
            String fecha = dates.format(txtFecha.getDate());
            pro.setFechaEnvio(fecha);
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Error, debe ingresar una fecha valida");
            return;
        }

        // vendedor
        pro.setNombreVendedor(txtNombreVendedor.getText());
        pro.setApellidoVendedor(txtApellidoVendedor.getText());
        pro.setEmailVendedor(txtEmailVendedor.getText());

        try {
            pro.setDniVendedor(Integer.parseInt(txtDniVendedor.getText()));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error, debe Ingresar el documento del vendedor");
            return;
        }
        // comprador
        pro.setNombreComprador(txtNombreComprador.getText());
        pro.setApellidoComprador(txtApellidoComprador.getText());
        pro.setEmailComprador(txtEmailComprador.getText());

        try {
            pro.setDniComprador(Integer.parseInt(txtDniComprador.getText()));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error, debe Ingresar el documento del comprador");
            return;
        }

        // conexion e insercion de datos a la base de datos
        Conexion con = new Conexion();
        Connection cx = con.getConexion();

        int code = pro.getCodigo();
        String estado = "Esperando";
        this.valorProd = pro.getPrecioTotal();
        this.porcentaje = this.valorProd * inpuesto;
        this.totalConInpuesto = this.valorProd + this.porcentaje;
        String INSERT = "INSERT INTO test.basedatospro (codigo, nombreProd, precio, fechaEnv, nombreVend,"
                + " apellidoVend, emailVend, dniVend, nombreComp, apellidoComp, emailComp, dniComp, estado) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try {
            try ( PreparedStatement ps = cx.prepareStatement(INSERT)) {
                ps.setInt(1, pro.getCodigo());
                ps.setString(2, pro.getNombreProducto());
                ps.setLong(3, pro.getPrecioTotal());
                ps.setString(4, pro.getFechaEnvio());

                ps.setString(5, pro.getNombreVendedor());
                ps.setString(6, pro.getApellidoVendedor());
                ps.setString(7, pro.getEmailVendedor());
                ps.setInt(8, pro.getDniVendedor());

                ps.setString(9, pro.getNombreComprador());
                ps.setString(10, pro.getApellidoComprador());
                ps.setString(11, pro.getEmailComprador());
                ps.setInt(12, pro.getDniComprador());
                ps.setString(13, estado);

                ps.executeUpdate();

                try {
                    Document documento = new Document();
                    String path = new File("C:\\Users\\andre\\Documents\\java\\javaClases\\facturas").getCanonicalPath();
                    String nombreArchivo = path + "/factura_producto" + code + ".pdf";

                    //añadimos un nuevo párrafo a nuestro documento
                    Image img = new Image(Image.getInstance(logo)) {

                    };
                    img.setAlignment(Chunk.TITLE);

                    Paragraph parrafo = new Paragraph();
                    parrafo.setAlignment(Paragraph.ALIGN_LEFT);
                    parrafo.add("\n\nFactura para el producto " + code + " Generado: " + "\n\nDetalles Del Comprador: " + "\n\nNombre: " + pro.getNombreComprador() + "\n"
                            + "Apellido: " + pro.getApellidoComprador() + "\nDocumento: " + pro.getDniComprador() + "\nEmail: " + pro.getEmailComprador()
                            + "\n\nDetalles Del Vendedor: " + "\n\nNombre: " + pro.getNombreVendedor() + "\nApellido: " + pro.getApellidoVendedor() + "\nDocumento: " + pro.getDniVendedor()
                            + "\nEmail: " + pro.getEmailVendedor() + "\n\nDetalles del producto: " + "\n\nNombre: " + pro.getNombreProducto() + "\n"
                            + "Fecha de envío: " + pro.getFechaEnvio() + "\nCódigo: " + pro.getCodigo() + "\nPrecio: " + pro.getPrecioTotal());
                    parrafo.setFont(FontFactory.getFont("Comic Sans MS", 18, Font.BOLD, BaseColor.BLACK));

                    Paragraph totalInpuesto = new Paragraph();
                    totalInpuesto.setAlignment(Paragraph.ALIGN_LEFT);
                    totalInpuesto.setPaddingTop(5);
                    totalInpuesto.add("\n\n\n\n\nTotal Producto: " + pro.getPrecioTotal() + "\nTotal de impuestos a pagar : " + this.porcentaje + "  COP");

                    Paragraph procesado = new Paragraph();
                    procesado.setAlignment(Paragraph.ALIGN_LEFT);
                    procesado.add("\nProcesado por:" + "\n" + this.nombre + " " + this.apellido);

                    PdfWriter.getInstance(documento, new FileOutputStream(new File(nombreArchivo)));
                    documento.open();
                    documento.add(img);
                    documento.add(parrafo);
                    documento.add(totalInpuesto);
                    documento.add(procesado);

                    documento.close();
                } catch (IOException | DocumentException e) {
                    JOptionPane.showMessageDialog(null, "Error de tipo document " + e.getMessage());
                }

                JOptionPane.showMessageDialog(null, "Se ha registrado con exito!");
                textosVacios();
            }
            cx.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "error sql : " + ex.getMessage());
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

        jTextField4 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtApellidoVendedor = new javax.swing.JTextField();
        txtDniVendedor = new javax.swing.JTextField();
        txtNombreVendedor = new javax.swing.JTextField();
        txtEmailVendedor = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtNombreComprador = new javax.swing.JTextField();
        txtApellidoComprador = new javax.swing.JTextField();
        txtEmailComprador = new javax.swing.JTextField();
        txtDniComprador = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        txtNombreProducto = new javax.swing.JTextField();
        txtPrecio = new javax.swing.JTextField();
        txtFecha = new com.toedter.calendar.JDateChooser();
        btnAgregar = new javax.swing.JButton();

        setBackground(new java.awt.Color(60, 63, 65));

        jLabel1.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Datos Del Vendedor");

        jLabel2.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Datos DelComprador");

        jLabel3.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Datos Del Producto");

        jLabel4.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Nombre");

        jLabel5.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Email");

        jLabel6.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Documento");

        jLabel7.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Apellido");

        txtApellidoVendedor.setFont(new java.awt.Font("Comic Sans MS", 1, 12)); // NOI18N
        txtApellidoVendedor.setForeground(new java.awt.Color(255, 255, 255));

        txtDniVendedor.setFont(new java.awt.Font("Comic Sans MS", 1, 12)); // NOI18N
        txtDniVendedor.setForeground(new java.awt.Color(255, 255, 255));
        txtDniVendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDniVendedorActionPerformed(evt);
            }
        });
        txtDniVendedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDniVendedorKeyTyped(evt);
            }
        });

        txtNombreVendedor.setFont(new java.awt.Font("Comic Sans MS", 1, 12)); // NOI18N
        txtNombreVendedor.setForeground(new java.awt.Color(255, 255, 255));

        txtEmailVendedor.setFont(new java.awt.Font("Comic Sans MS", 1, 12)); // NOI18N
        txtEmailVendedor.setForeground(new java.awt.Color(255, 255, 255));

        jLabel8.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Apellido");

        jLabel9.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Nombre");

        jLabel10.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Email");

        jLabel11.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Documento");

        txtNombreComprador.setFont(new java.awt.Font("Comic Sans MS", 1, 12)); // NOI18N
        txtNombreComprador.setForeground(new java.awt.Color(255, 255, 255));

        txtApellidoComprador.setFont(new java.awt.Font("Comic Sans MS", 1, 12)); // NOI18N
        txtApellidoComprador.setForeground(new java.awt.Color(255, 255, 255));

        txtEmailComprador.setFont(new java.awt.Font("Comic Sans MS", 1, 12)); // NOI18N
        txtEmailComprador.setForeground(new java.awt.Color(255, 255, 255));

        txtDniComprador.setFont(new java.awt.Font("Comic Sans MS", 1, 12)); // NOI18N
        txtDniComprador.setForeground(new java.awt.Color(255, 255, 255));
        txtDniComprador.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDniCompradorKeyTyped(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Código");

        jLabel13.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Nombre");

        jLabel14.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Precio");

        jLabel15.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Fecha Envio");

        txtCodigo.setFont(new java.awt.Font("Comic Sans MS", 1, 12)); // NOI18N
        txtCodigo.setForeground(new java.awt.Color(255, 255, 255));
        txtCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoActionPerformed(evt);
            }
        });
        txtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoKeyTyped(evt);
            }
        });

        txtNombreProducto.setFont(new java.awt.Font("Comic Sans MS", 1, 12)); // NOI18N
        txtNombreProducto.setForeground(new java.awt.Color(255, 255, 255));

        txtPrecio.setFont(new java.awt.Font("Comic Sans MS", 1, 12)); // NOI18N
        txtPrecio.setForeground(new java.awt.Color(255, 255, 255));
        txtPrecio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrecioActionPerformed(evt);
            }
        });

        txtFecha.setForeground(new java.awt.Color(255, 255, 255));
        txtFecha.setFont(new java.awt.Font("Comic Sans MS", 1, 12)); // NOI18N
        txtFecha.setPreferredSize(new java.awt.Dimension(70, 22));

        btnAgregar.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        btnAgregar.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/addButton.png"))); // NOI18N
        btnAgregar.setText("Registrar");
        btnAgregar.setBorder(null);
        btnAgregar.setBorderPainted(false);
        btnAgregar.setContentAreaFilled(false);
        btnAgregar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAgregar.setFocusPainted(false);
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAgregar)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(txtNombreVendedor, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                            .addComponent(txtApellidoVendedor)
                            .addComponent(txtEmailVendedor)
                            .addComponent(txtDniVendedor))
                        .addGap(164, 164, 164)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel12)
                                .addGap(220, 220, 220))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNombreComprador, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8)
                                    .addComponent(txtApellidoComprador, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10)
                                    .addComponent(txtEmailComprador, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11)
                                    .addComponent(txtDniComprador, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(159, 159, 159)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel14)
                                    .addComponent(jLabel15)
                                    .addComponent(txtCodigo)
                                    .addComponent(txtNombreProducto)
                                    .addComponent(txtPrecio)
                                    .addComponent(txtFecha, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE))
                                .addContainerGap(31, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3)
                                .addGap(132, 132, 132))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel9)
                    .addComponent(jLabel12))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombreVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombreComprador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel13))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtApellidoVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtApellidoComprador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombreProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel10)
                    .addComponent(jLabel14))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEmailVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEmailComprador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(jLabel6))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtDniVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtDniComprador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtFecha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(49, 49, 49)
                .addComponent(btnAgregar)
                .addContainerGap(119, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        if (txtNombreVendedor.getText().isEmpty() || txtApellidoVendedor.getText().isEmpty()
                || txtEmailVendedor.getText().isEmpty() || txtNombreComprador.getText().isEmpty()
                || txtApellidoComprador.getText().isEmpty() || txtEmailComprador.getText().isEmpty()
                || txtNombreProducto.getText().isEmpty()) {

            JOptionPane.showMessageDialog(null, "Asegurate de llenar todos los campos");

        } else {
            agregar();
        }


    }//GEN-LAST:event_btnAgregarActionPerformed

    private void txtCodigoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoKeyTyped
        int key = evt.getKeyChar();
        boolean numeros = key >= 48 && key <= 57;

        if (!numeros) {
            evt.consume();
        }

        if (txtCodigo.getText().trim().length() == 6) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCodigoKeyTyped

    private void txtDniCompradorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDniCompradorKeyTyped
        int key = evt.getKeyChar();
        boolean numeros = key >= 48 && key <= 57;

        if (!numeros) {
            evt.consume();
        }

        if (txtDniComprador.getText().trim().length() == 9) {
            evt.consume();
        }
    }//GEN-LAST:event_txtDniCompradorKeyTyped

    private void txtDniVendedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDniVendedorKeyTyped
        int key = evt.getKeyChar();
        boolean numeros = key >= 48 && key <= 57;

        if (!numeros) {
            evt.consume();
        }

        if (txtDniVendedor.getText().trim().length() == 9) {
            evt.consume();
        }
    }//GEN-LAST:event_txtDniVendedorKeyTyped

    private void txtCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoActionPerformed

    private void txtPrecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioActionPerformed

    private void txtDniVendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDniVendedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDniVendedorActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField txtApellidoComprador;
    private javax.swing.JTextField txtApellidoVendedor;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtDniComprador;
    private javax.swing.JTextField txtDniVendedor;
    private javax.swing.JTextField txtEmailComprador;
    private javax.swing.JTextField txtEmailVendedor;
    private com.toedter.calendar.JDateChooser txtFecha;
    private javax.swing.JTextField txtNombreComprador;
    private javax.swing.JTextField txtNombreProducto;
    private javax.swing.JTextField txtNombreVendedor;
    private javax.swing.JTextField txtPrecio;
    // End of variables declaration//GEN-END:variables
}
