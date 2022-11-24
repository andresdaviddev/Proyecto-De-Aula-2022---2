/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author DELL
 */
public class Conexion {

    private final String URL = "jdbc:mysql://localhost:3306/test";
    String USER = "root";
    String PASS = "admin";
    String DRIVER = "com.mysql.cj.jdbc.Driver";
    Connection con;

    public Connection getConexion() {
        try {
            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return con;
    }

    
}
