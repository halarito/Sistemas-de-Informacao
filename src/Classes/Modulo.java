package Classes;
import java.sql.*;
import javax.swing.JOptionPane;

public class Modulo {
    
    public static Connection conector(){
        
        java.sql.Connection conexao = null;
        
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/livraria";
        
        String user = "root";
        String pass = "";
        
        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, user, pass);
            return conexao;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            
            return null;
        }
    }
}