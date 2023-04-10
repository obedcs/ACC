/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package adminfacturas;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;
import java.sql.*;

/**
 *
 * @author Sistemas1
 */
public class conecxionbd {
    Connection con=null;
    
    public Connection Conecta(String ServerDBSQL,String PuertoDBSQL,String DBDBSQL,String UserDBSQL,String PwdDBSQL){
        con=null;
        try{
            System.out.println("conectando con SQL Server ...");
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://"+ServerDBSQL+":"+PuertoDBSQL+";databaseName="+DBDBSQL+";user="+UserDBSQL+";password="+PwdDBSQL);
        }
        catch(Exception e){JOptionPane.showMessageDialog(null, e);}
        return con;
    }
    
    public Connection ConectaXML(){
        con=null;
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=XMLFACTURAS;user=sa;password=OK2506obed;integratedSecurity=true");
        }
        catch(Exception e){JOptionPane.showMessageDialog(null, e);}
        return con;
    }
    
    public Connection ConectaMySQL(String ServerDB,String PuertoDB,String DBDB,String UserDB,String PwdDB){
        con=null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection( "jdbc:mysql://"+ServerDB+":"+PuertoDB+"/"+DBDB,UserDB,PwdDB);
        }
        catch(Exception e){JOptionPane.showMessageDialog(null, e);}
        return con;
    }
}
