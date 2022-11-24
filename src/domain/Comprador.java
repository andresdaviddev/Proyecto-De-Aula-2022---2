/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

/**
 *
 * @author andre
 */
public class Comprador extends Vendedor{
    protected String nombreComprador;
    protected String apellidoComprador;
    protected String emailComprador;
    protected int dniComprador;

    public Comprador() {
    }

    public Comprador(String nombreComprador, String apellidoComprador, String emailComprador, int dniComprador, String nombreVendedor, String apellidoVendedor, String emailVendedor, int dniVendedor) {
        super(nombreVendedor, apellidoVendedor, emailVendedor, dniVendedor);
        this.nombreComprador = nombreComprador;
        this.apellidoComprador = apellidoComprador;
        this.emailComprador = emailComprador;
        this.dniComprador = dniComprador;
    }
    
    
}
