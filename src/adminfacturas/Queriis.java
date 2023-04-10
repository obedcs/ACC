/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package adminfacturas;

import java.sql.*;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author Obed
 */
public class Queriis {
    Statement stat;
    conecxionbd Conect = new conecxionbd();
    
    public ResultSet Estructura(Connection con,String consulta){
       ResultSet rs=null;
       try{
           stat = con.createStatement();
           rs = stat.executeQuery(consulta);
        }
        catch (Exception e){JOptionPane.showMessageDialog(null, e);}
       
       return rs;
   }
    
    public ResultSet EstructuraMySQL(Connection con, String consulta){
       System.out.println("entre al metodo");
        ResultSet rs=null;
       try{
           stat = con.createStatement();
           rs = stat.executeQuery(consulta);
        }
        catch (Exception e){JOptionPane.showMessageDialog(null, e);}
       
       return rs;
   }
   
   public int InsertaActualiza(Connection conMySQL, String Comando){
       int status=0;
       try{
           stat = conMySQL.createStatement();
           stat.executeUpdate(Comando);
           status++;
       }catch (Exception e){JOptionPane.showMessageDialog(null, e);}
       return status;
   }
   
   public int RevisaFolioBD(Connection conMySQL,String Folio){
        ResultSet rs=null;
        String consulta="";
        int encontro = 0;
        
        consulta = "SELECT numfactura FROM company WHERE numfactura='"+Folio+"'";
        try{
           stat = conMySQL.createStatement();
           rs = stat.executeQuery(consulta);
           
           if(rs.next())
               encontro = 1;
           else
               encontro = 0;
        }
        catch (Exception e){JOptionPane.showMessageDialog(null, e);}
       
       return encontro;
    }
   
   public String NuevoFolio(Connection conMySQL){
       ResultSet rs=null;
       String consulta="",Folio="";
       int insertastat=0;
       
        consulta = "SELECT folio FROM folioscontrol WHERE status_folio='ocupado' ORDER BY folio DESC";
        try{
           stat = conMySQL.createStatement();
           rs = stat.executeQuery(consulta);
           
           if(rs.next()){
               String ultimofol = Integer.toString(Integer.parseInt(rs.getString("folio"))+1);
               consulta = "INSERT INTO folioscontrol (folio,status_folio) VALUES ("+ultimofol+",'ocupado')";
               insertastat = InsertaActualiza(conMySQL,consulta);
               
               if(insertastat > 0)
                   Folio = ultimofol;
           }
        }
        catch (Exception e){JOptionPane.showMessageDialog(null, e);}
       
       return Folio;
   }
}
