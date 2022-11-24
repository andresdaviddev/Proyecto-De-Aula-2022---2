/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

/**
 *
 * @author andre
 */
public class Vendedor {
    protected String nombreVendedor;
    protected String apellidoVendedor;
    protected String emailVendedor;
    protected int dniVendedor;

    public Vendedor() {
    }

    public Vendedor(String nombreVendedor, String apellidoVendedor, String emailVendedor, int dniVendedor) {
        this.nombreVendedor = nombreVendedor;
        this.apellidoVendedor = apellidoVendedor;
        this.emailVendedor = emailVendedor;
        this.dniVendedor = dniVendedor;
    }
    
    
}
