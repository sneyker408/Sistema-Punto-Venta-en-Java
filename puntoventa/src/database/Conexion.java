package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Conexion {
    
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";  
    private static final String DB_URL = "jdbc:mysql://localhost:3306/";  
    
    private final static String DB = "puntoventa";  
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "Chocolate2015";
    
    public Connection conectar;
    public static Conexion singleConnection;
    
    private Conexion() {
        this.conectar = null;
    }
    
    public Connection conectar() {
        try{
            Class.forName(DB_DRIVER); 
            this.conectar = DriverManager.getConnection(
            DB_URL + DB + "?serverTimezone=UTC",  DB_USERNAME, DB_PASSWORD  
            );
        }catch(ClassNotFoundException | SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
            System.exit(0);   
        }
        return this.conectar;
    }    
    
    public static Conexion getInstance() {
        if(singleConnection == null){
            singleConnection = new Conexion();
        }
        return singleConnection;
    }
    
    public void desconectar() {
        try{
            this.conectar.close();
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void desconnectar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}