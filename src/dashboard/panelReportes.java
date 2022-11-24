    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package dashboard;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import domain.Conexion;
import java.awt.Desktop;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
/**
 *
 * @author andre
 */
public class panelReportes extends javax.swing.JPanel {
     private static final String logo = "C:\\Users\\andre\\Documents\\java\\javaClases\\img\\barner.png";
    /**
     * Creates new form panelReportes
     */
    public panelReportes() {
        initComponents();
        etiFecha.setVisible(false);
        fecha();
        resumenReportes();
        etiFechaUp.setText(etiFecha.getText());
    }

    public final String fecha() {
        SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");
        String fecha = date.format(new Date());
        etiFecha.setText(fecha);
        return fecha;
    }

    public final void resumenReportes() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Código");
        modelo.addColumn("Nombre");
        modelo.addColumn("Fecha");
        modelo.addColumn("Nombre");
        modelo.addColumn("Email");
        modelo.addColumn("Documento");
        modelo.addColumn("Nombre");
        modelo.addColumn("Email");
        modelo.addColumn("Documento");
        tablaReportes.setModel(modelo);

        Conexion con = new Conexion();
        Connection cx = con.getConexion();
        String fecha = etiFecha.getText();
        String SELECT = "SELECT codigo, nombreprod, fechaEnv, nombreVend, emailVend, dniVend, nombreComp, emailComp, dniComp FROM test.basedatospro WHERE fechaEnv = ?";
        try {
            PreparedStatement ps = cx.prepareStatement(SELECT);
            ps.setString(1, fecha);
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
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error sql : " + ex.getMessage());
        }
    }

    public final void crearReporte() throws IOException, BadElementException {
        Document documento = new Document();
        String path = new File("C:\\Users\\andre\\Documents\\java\\javaClases\\reportes").getCanonicalPath();
        SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");
        String fecha = date.format(new Date());
        String nombreArchivo = path + "/reporte_diario" + fecha + ".pdf";
        
            Image img = new Image(Image.getInstance(logo)) {

                    };
                    img.setAlignment(Chunk.TITLE);
        
            Paragraph parrafo = new Paragraph();
            parrafo.setAlignment(Paragraph.ALIGN_LEFT);
            parrafo.add("\n\n\n\nReporte del Día " + fecha +" Generado: \n\n");
            parrafo.setFont(FontFactory.getFont("Comic Sans MS" , 16 , Font.BOLD, BaseColor.BLACK));
            
        try {
            PdfWriter.getInstance(documento, new FileOutputStream(new File(nombreArchivo)));
            documento.open();
            documento.add(img);
            documento.add(parrafo);
            
            
            PdfPTable tabla = new PdfPTable(7);
            tabla.setWidthPercentage(100);
            tabla.addCell("Código Producto");
            tabla.addCell("Nombre Producto");
            tabla.addCell("Fecha Envio");
            tabla.addCell("Nombre Vendedor");
            tabla.addCell("Documento Vendedor");
            tabla.addCell("Nombre Comprador");
            tabla.addCell("Documento Comprador");
            
            try {
                Conexion con = new Conexion();
                Connection cx = con.getConexion();
                String SELECT = "SELECT codigo, nombreprod, fechaEnv, nombreVend, dniVend, nombreComp, dniComp FROM test.basedatospro WHERE fechaEnv = ?";
                PreparedStatement ps = cx.prepareStatement(SELECT);
                ps.setString(1, fecha);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    do {
                        tabla.addCell(rs.getString(1));
                        tabla.addCell(rs.getString(2));
                        tabla.addCell(rs.getString(3));
                        tabla.addCell(rs.getString(4));
                        tabla.addCell(rs.getString(5));
                        tabla.addCell(rs.getString(6));
                        tabla.addCell(rs.getString(7));
                    } while (rs.next());
                    documento.add(tabla);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "error sql " + ex.getMessage());
            }
            documento.close();
            JOptionPane.showMessageDialog(null, "Se ha creado el reporte!");
            // catch del pdf
        } catch (DocumentException | FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "error pdf " + ex.getMessage());
        }

    }

     public final void enviarCorreo() {
        String date = fecha();
        try {
            String correo = "andrepzk7@gmail.com";
            String contraseña = "tezsguiqkokzpemv";
            String remitente = "andresdavidpachecocuadro@gmail.com";

            Properties p = new Properties();
            p.put("mail.smtp.host", "smtp.gmail.com");
            p.setProperty("mail.smtp.starttls.enable", "true");
            p.put("mail.smtp.ssl.trust", "smtp.gmail.com");
            p.setProperty("mail.smtp.port", "587");
            p.setProperty("mail.smtp.user", correo);
            p.setProperty("mail.smtp.auth", "true");

            Session s = Session.getDefaultInstance(p);

            MimeMessage mensaje = new MimeMessage(s);
            mensaje.setFrom(new InternetAddress(correo));

            MimeBodyPart texto = new MimeBodyPart();
            texto.setText("mensaje adjunto: " + date);

            MimeBodyPart adjunto = new MimeBodyPart();
            adjunto.attachFile(new File("C:\\Users\\andre\\Documents\\java\\javaClases\\reportes\\reporte_diario" + fecha() + ".pdf"));
            
            MimeMultipart men = new MimeMultipart();
            men.addBodyPart(texto);
            men.addBodyPart(adjunto);
            
            mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(remitente));
            mensaje.setSubject("Reporte diario " + date);
            
            mensaje.setText("archivo :(");
            mensaje.setContent(men);
            
            Transport t = s.getTransport("smtp");
            t.connect(correo, contraseña);
            t.sendMessage(mensaje, mensaje.getAllRecipients());
            t.close();
            JOptionPane.showMessageDialog(null, "Se ha enviado el reporte al gerente con exito!");

        } catch (AddressException ex) {
            JOptionPane.showMessageDialog(null, "no se envio" + ex.getMessage());
        } catch (MessagingException ex) {
            JOptionPane.showMessageDialog(null, " no se envio" + ex.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(panelReportes.class.getName()).log(Level.SEVERE, null, ex);
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
        tablaReportes = new dashborads.TableDark();
        btnReportes = new javax.swing.JButton();
        etiFecha = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        etiFechaUp = new javax.swing.JLabel();
        btnAbrirPdf = new javax.swing.JButton();
        btnEnviarCorreo = new javax.swing.JButton();

        setBackground(new java.awt.Color(60, 63, 65));

        tablaReportes.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tablaReportes);

        btnReportes.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        btnReportes.setForeground(new java.awt.Color(255, 255, 255));
        btnReportes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/agregar-archivo.png"))); // NOI18N
        btnReportes.setText("Crear Reporte");
        btnReportes.setBorder(null);
        btnReportes.setBorderPainted(false);
        btnReportes.setContentAreaFilled(false);
        btnReportes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnReportes.setFocusPainted(false);
        btnReportes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReportesActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Reportes de hoy");

        etiFechaUp.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        etiFechaUp.setForeground(new java.awt.Color(255, 255, 255));

        btnAbrirPdf.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        btnAbrirPdf.setForeground(new java.awt.Color(255, 255, 255));
        btnAbrirPdf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/openPdf.png"))); // NOI18N
        btnAbrirPdf.setText("Abrir Reporte");
        btnAbrirPdf.setBorder(null);
        btnAbrirPdf.setBorderPainted(false);
        btnAbrirPdf.setContentAreaFilled(false);
        btnAbrirPdf.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAbrirPdf.setFocusPainted(false);
        btnAbrirPdf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbrirPdfActionPerformed(evt);
            }
        });

        btnEnviarCorreo.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        btnEnviarCorreo.setForeground(new java.awt.Color(255, 255, 255));
        btnEnviarCorreo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/send.png"))); // NOI18N
        btnEnviarCorreo.setText("Enviar Reporte");
        btnEnviarCorreo.setBorder(null);
        btnEnviarCorreo.setBorderPainted(false);
        btnEnviarCorreo.setContentAreaFilled(false);
        btnEnviarCorreo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEnviarCorreo.setFocusPainted(false);
        btnEnviarCorreo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarCorreoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1054, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(etiFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addGap(18, 18, 18)
                                        .addComponent(etiFechaUp, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnReportes)
                                        .addGap(36, 36, 36)
                                        .addComponent(btnAbrirPdf)
                                        .addGap(35, 35, 35)
                                        .addComponent(btnEnviarCorreo)))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(etiFechaUp, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 423, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnReportes)
                    .addComponent(btnAbrirPdf)
                    .addComponent(btnEnviarCorreo))
                .addGap(38, 38, 38)
                .addComponent(etiFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnReportesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportesActionPerformed
        try {
            crearReporte();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "error Io " + ex.getMessage());
        } catch (BadElementException ex) {
             Logger.getLogger(panelReportes.class.getName()).log(Level.SEVERE, null, ex);
         }
    }//GEN-LAST:event_btnReportesActionPerformed

    private void btnAbrirPdfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbrirPdfActionPerformed
        SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");
        String fecha = date.format(new Date());
        try {
            File path = new File("C:\\Users\\andre\\Documents\\java\\javaClases\\reportes\\reporte_diario" + fecha + ".pdf");
            Desktop.getDesktop().open(path);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Aún no se ha generado un reporte " + ex.getMessage());
        }
    }//GEN-LAST:event_btnAbrirPdfActionPerformed

    private void btnEnviarCorreoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarCorreoActionPerformed
       enviarCorreo();
    }//GEN-LAST:event_btnEnviarCorreoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAbrirPdf;
    private javax.swing.JButton btnEnviarCorreo;
    private javax.swing.JButton btnReportes;
    private javax.swing.JLabel etiFecha;
    private javax.swing.JLabel etiFechaUp;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private dashborads.TableDark tablaReportes;
    // End of variables declaration//GEN-END:variables
}
