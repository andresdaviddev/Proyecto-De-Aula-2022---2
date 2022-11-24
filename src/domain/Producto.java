/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

/**
 *
 * @author andre
 */
public class Producto extends Comprador{
    private int codigo;
    private String nombreProducto;
    private long precioTotal;
    private String fechaEnvio;
    private String estado;
    
    public Producto() {
    }

    public Producto(int codigo, String nombreProducto, long precioTotal, String fechaEnvio, String estado, String nombreComprador, String apellidoComprador, String emailComprador, int dniComprador, String nombreVendedor, String apellidoVendedor, String emailVendedor, int dniVendedor) {
        super(nombreComprador, apellidoComprador, emailComprador, dniComprador, nombreVendedor, apellidoVendedor, emailVendedor, dniVendedor);
        this.codigo = codigo;
        this.nombreProducto = nombreProducto;
        this.precioTotal = precioTotal;
        this.fechaEnvio = fechaEnvio;
        this.estado = estado;
    }

    
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public long getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(long precioTotal) {
        this.precioTotal = precioTotal;
    }

    public String getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(String fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public String getNombreComprador() {
        return nombreComprador;
    }

    public void setNombreComprador(String nombreComprador) {
        this.nombreComprador = nombreComprador;
    }

    public String getApellidoComprador() {
        return apellidoComprador;
    }

    public void setApellidoComprador(String apellidoComprador) {
        this.apellidoComprador = apellidoComprador;
    }

    public String getEmailComprador() {
        return emailComprador;
    }

    public void setEmailComprador(String emailComprador) {
        this.emailComprador = emailComprador;
    }

    public int getDniComprador() {
        return dniComprador;
    }

    public void setDniComprador(int dniComprador) {
        this.dniComprador = dniComprador;
    }

    public String getNombreVendedor() {
        return nombreVendedor;
    }

    public void setNombreVendedor(String nombreVendedor) {
        this.nombreVendedor = nombreVendedor;
    }

    public String getApellidoVendedor() {
        return apellidoVendedor;
    }

    public void setApellidoVendedor(String apellidoVendedor) {
        this.apellidoVendedor = apellidoVendedor;
    }

    public String getEmailVendedor() {
        return emailVendedor;
    }

    public void setEmailVendedor(String emailVendedor) {
        this.emailVendedor = emailVendedor;
    }

    public int getDniVendedor() {
        return dniVendedor;
    }

    public void setDniVendedor(int dniVendedor) {
        this.dniVendedor = dniVendedor;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    
    
}
