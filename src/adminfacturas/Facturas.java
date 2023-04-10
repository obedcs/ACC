/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package adminfacturas;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
/*import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;*/
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.BadLocationException;
import net.sf.jasperreports.engine.JRException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *
 * @author Sistemas1
 */
public class Facturas extends javax.swing.JFrame {
    ResultSet rs = null;
    Statement stat;
    Connection con;
    Connection conMySQL;
    Connection conXML;
    conecxionbd Conexion = new conecxionbd();
    Queriis ConsultasSistema = new Queriis();
    Sistema Sistema = new Sistema();
    
    String Deduc = "";
    String Coaseg = "";
    String DescEspecial = "";
    String TotFactura = "";
    String Usuario = "";
    String aseguradora = "",numcia="";
    String nocompania = "";
    String mailaseg = "";
    String carpetaExcel="";
    String carpetaPDF = "";
    String montofolio="";
    String abonadofolio="";
    String adeudofolio="";
    String Folioo = "";
    String keyword_access = "UJAT";
    String SegMercado = "";
    String NumCto = "";
    String numcotizacion = "";
    String servername="",logo="",logoacc="";
    String carpeta="";
    String carpetaPDFReportes="";
    String ServerDB = "",PuertoDB = "",DBDB = "",UserDB = "",PwdDB = "";
    String ServerDBSQL = "",PuertoDBSQL = "",DBDBSQL = "",UserDBSQL = "",PwdDBSQL = "";
        
    String mail = "";
    String pwdmail = "";
    String port = "";
    String smtphost = "";
    String subject = "";
    String message = "";
    
    //String snmb = getMotherboardSN();
    String version = "3.0";
    String app = "ACC";
    
    float monto = 0;
    float abonos=0;
    float adeudo=0;
    
    int diasvigencia = 5;
    int facprovisiona = 0;
    
    DefaultTableModel modeloT6,modeloT14,modeloT15,modeloT21,
                      modeloT22,modeloT23,modeloT24,modeloT25;
    
    /**
     * Creates new form Facturas
     */
    public Facturas() throws ParseException {
        initComponents();
        
        modeloT6 = (DefaultTableModel)jTable6.getModel();
        modeloT14 = (DefaultTableModel)jTable14.getModel();
        modeloT15 = (DefaultTableModel)jTable15.getModel();
        modeloT21 = (DefaultTableModel)jTable21.getModel();
        modeloT22 = (DefaultTableModel)jTable22.getModel();
        modeloT23 = (DefaultTableModel)jTable23.getModel();
        modeloT24 = (DefaultTableModel)jTable24.getModel();
        modeloT25 = (DefaultTableModel)jTable25.getModel();
        
        CheckCombos();
        jComboBox3.setEnabled(false);
        jDateChooser7.setCalendar(Calendar.getInstance());
        
        jTextField53.setEnabled(true);
        jPanel13.setVisible(false);
        jPanel17.setVisible(false);
        jPanel18.setVisible(false);
        jPanel19.setVisible(false);
        jPanel21.setVisible(false);
        jPanel23.setVisible(false);
        jPanel24.setVisible(false);
        jPanel36.setVisible(false);
        jPanel37.setVisible(false);
        jPanel39.setVisible(false);
        jPanel49.setVisible(false);
        jPanel51.setVisible(false);
        jTextField43.setVisible(false);
        jTextField100.setVisible(false);
        jTextField112.setVisible(false);
        jDateChooser1.setVisible(false);
        jLabel93.setVisible(false);
        jButton5.setEnabled(false);
        jButton8.setVisible(false);
        jButton15.setEnabled(false);
        jButton30.setEnabled(false);
        jButton35.setVisible(false);
        jButton36.setEnabled(false);
        jButton47.setEnabled(false);
        jButton48.setEnabled(false);
        jButton49.setEnabled(false);
        jSpinner3.setEnabled(false);
        //jTextField17.setEnabled(false);
        jDateChooser19.setCalendar(ConvierteCal("2000-01-01"));
           
        LeeConfig();
        LeeConfigDB();
        
        con=Conexion.Conecta(ServerDBSQL,PuertoDBSQL,DBDBSQL,UserDBSQL,PwdDBSQL);
        conMySQL=Conexion.ConectaMySQL(ServerDB,PuertoDB,DBDB,UserDB,PwdDB);
        //conXML=Conexion.ConectaXML();
        Licencia(1);
        datosujat.setEnabled(false);
        this.setJMenuBar(menufacturas);
        DesbloqMenu(0);
        RecibeFolios(0);
    }
    
    public void cent_this(){
        this.pack();
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension ventana = this.getSize();
        this.setLocation(((pantalla.width - ventana.width)/2), ((pantalla.height - ventana.height)/2));
        this.setVisible(true);
    }
    
    public void cent_captura(){
        captura.pack();
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension ventana = captura.getSize();
        captura.setLocation(((pantalla.width - ventana.width)/2), ((pantalla.height - ventana.height)/2));
        captura.setVisible(true);
    }
    
    public void cent_folios(){
        SelecFolio.pack();
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension ventana = SelecFolio.getSize();
        SelecFolio.setLocation(((pantalla.width - ventana.width)/2), ((pantalla.height - ventana.height)/2));
        SelecFolio.setVisible(true);
    }
    
    public void cent_compania(){
        companiacaptura.pack();
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension ventana = companiacaptura.getSize();
        companiacaptura.setLocation(((pantalla.width - ventana.width)/2), ((pantalla.height - ventana.height)/2));
        companiacaptura.setVisible(true);
    }
    
    public void cent_impresion(){
        impresion.pack();
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension ventana = impresion.getSize();
        impresion.setLocation(((pantalla.width - ventana.width)/2), ((pantalla.height - ventana.height)/2));
        impresion.setVisible(true);
    }
    
    public void cent_consulta(){
        conss.pack();
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension ventana = conss.getSize();
        conss.setLocation(((pantalla.width - ventana.width)/2), ((pantalla.height - ventana.height)/2));
        conss.setVisible(true);
    }
    
    public void cent_selecaseg(){
        selecaseg.pack();
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension ventana = selecaseg.getSize();
        selecaseg.setLocation(((pantalla.width - ventana.width)/2), ((pantalla.height - ventana.height)/2));
        selecaseg.setVisible(true);
    }
    
    public void cent_contraesp(){
        contraesp.pack();
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension ventana = contraesp.getSize();
        contraesp.setLocation(((pantalla.width - ventana.width)/2), ((pantalla.height - ventana.height)/2));
        contraesp.setVisible(true);
    }
    
    public void buscapaciente(){
        buscapaciente.pack();
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension ventana = buscapaciente.getSize();
        buscapaciente.setLocation(((pantalla.width - ventana.width)/2), ((pantalla.height - ventana.height)/2));
        buscapaciente.setVisible(true);
    }
    
    public void cambiafactura(){
        cambiafactura.pack();
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension ventana = cambiafactura.getSize();
        cambiafactura.setLocation(((pantalla.width - ventana.width)/2), ((pantalla.height - ventana.height)/2));
        cambiafactura.setVisible(true);
    }
    
    public void cent_about(){
        acercade.pack();
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension ventana = acercade.getSize();
        acercade.setLocation(((pantalla.width - ventana.width)/2), ((pantalla.height - ventana.height)/2));
        acercade.setVisible(true);
    }
    
    public void cent_editmail(){
        conf_mail.pack();
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension ventana = conf_mail.getSize();
        conf_mail.setLocation(((pantalla.width - ventana.width)/2), ((pantalla.height - ventana.height)/2));
        conf_mail.setVisible(true);
    }
    
    public void cent_sendmail(){
        SendMail.pack();
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension ventana = SendMail.getSize();
        SendMail.setLocation(((pantalla.width - ventana.width)/2), ((pantalla.height - ventana.height)/2));
        SendMail.setVisible(true);
    }
    
    public void cent_datosujat(){
        datos_ujat.pack();
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension ventana = datos_ujat.getSize();
        datos_ujat.setLocation(((pantalla.width - ventana.width)/2), ((pantalla.height - ventana.height)/2));
        datos_ujat.setVisible(true);
    }
    
    public void cent_cotizacion(){
        cotizacion.pack();
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension ventana = cotizacion.getSize();
        cotizacion.setLocation(((pantalla.width - ventana.width)/2), ((pantalla.height - ventana.height)/2));
        cotizacion.setVisible(true);
    }
    
    public void cent_catalog(){
        CatalogoArticulosTCA.pack();
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension ventana = CatalogoArticulosTCA.getSize();
        CatalogoArticulosTCA.setLocation(((pantalla.width - ventana.width)/2), ((pantalla.height - ventana.height)/2));
        CatalogoArticulosTCA.setVisible(true);
    }
    
    public void cent_correo(){
        sendcorreo.pack();
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension ventana = sendcorreo.getSize();
        sendcorreo.setLocation(((pantalla.width - ventana.width)/2), ((pantalla.height - ventana.height)/2));
        sendcorreo.setVisible(true);
    }
    
    public void cent_ruta(){
        RutaFiles.pack();
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension ventana = RutaFiles.getSize();
        RutaFiles.setLocation(((pantalla.width - ventana.width)/2), ((pantalla.height - ventana.height)/2));
        RutaFiles.setVisible(true);
    }
    
    public void cent_tablastatus(){
        TablaStatus.pack();
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension ventana = TablaStatus.getSize();
        TablaStatus.setLocation(((pantalla.width - ventana.width)/2), ((pantalla.height - ventana.height)/2));
        TablaStatus.setVisible(true);
    }
    
    public void cent_tablaestad(){
        ExcelEstad.pack();
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension ventana = ExcelEstad.getSize();
        ExcelEstad.setLocation(((pantalla.width - ventana.width)/2), ((pantalla.height - ventana.height)/2));
        ExcelEstad.setVisible(true);
    }
    
    public void cent_key(){
        Key.pack();
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension ventana = Key.getSize();
        Key.setLocation(((pantalla.width - ventana.width)/2), ((pantalla.height - ventana.height)/2));
        Key.setVisible(true);
    }
    
    public void cent_conf(){
        Configuracion.pack();
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension ventana = Configuracion.getSize();
        Configuracion.setLocation(((pantalla.width - ventana.width)/2), ((pantalla.height - ventana.height)/2));
        Configuracion.setVisible(true);
    }
    
    public void LeeConfig(){
        try{
            Properties p = new Properties();
            p.load(new FileInputStream("Config.ini"));
            mail=p.getProperty("MailFrom");
            pwdmail=Sistema.Desencripta(p.getProperty("PasswordMail"));
            smtphost=p.getProperty("SMTPHost");
            port=p.getProperty("Port");
            subject=p.getProperty("Subject");
            message=p.getProperty("Message");
            servername=p.getProperty("ServerName");
            carpetaExcel=p.getProperty("ExcelFolder");
            carpetaPDF=p.getProperty("ReportFolderPDF");
            carpeta=p.getProperty("ReportFolder");
            logo=p.getProperty("Logo");
            logoacc=p.getProperty("LogoACC");
        } catch (Exception e) {System.out.println("error al leer el archivo .ini:"+ e.getMessage());}
        
        jTextField94.setText(mail);
        jPasswordField2.setText(pwdmail);
        jTextField96.setText(smtphost);
        jTextField95.setText(port);
        jTextField101.setText(subject);
        jTextArea13.setText(message);
    }
    
    public void LeeConfigDB(){
        try{
            Properties p = new Properties();
            p.load(new FileInputStream("ConfigDB.ini"));
            ServerDB=p.getProperty("Servidor");
            PwdDB=Sistema.Desencripta(p.getProperty("Passwd"));
            PuertoDB=p.getProperty("Puerto");
            DBDB=p.getProperty("DB");
            UserDB=p.getProperty("Usuario");
            
            ServerDBSQL=p.getProperty("ServidorSQL");
            PwdDBSQL=Sistema.Desencripta(p.getProperty("PasswdSQL"));
            System.out.println(PwdDBSQL);
            PuertoDBSQL=p.getProperty("PuertoSQL");
            DBDBSQL=p.getProperty("DBSQL");
            UserDBSQL=p.getProperty("UsuarioSQL");
        } catch (Exception e) {System.out.println("error al leer el archivo .ini:"+ e.getMessage());}
        
        jTextField126.setText(ServerDB);
        jPasswordField3.setText(PwdDB);
        jTextField132.setText(PuertoDB);
        jTextField134.setText(DBDB);
        jTextField133.setText(UserDB);
        
        jTextField135.setText(ServerDBSQL);
        jPasswordField4.setText(PwdDBSQL);
        jTextField136.setText(PuertoDBSQL);
        jTextField138.setText(DBDBSQL);
        jTextField137.setText(UserDBSQL);
    }
    
    public int EscribeConfig(){
        int bandera=0;
        
        try{
            Properties p = new Properties();
            p.load(new FileInputStream("Config.ini"));
            
            p.put("MailFrom", jTextField94.getText());
            p.put("PasswordMail", Sistema.Encripta(jPasswordField2.getText()));
            p.put("SMTPHost", jTextField96.getText());
            p.put("Port", jTextField95.getText());
            p.put("Subject", jTextField101.getText());
            p.put("Message", jTextArea13.getText());
            
            FileOutputStream out = new FileOutputStream("Config.ini");
            p.save(out, "/* properties updated */");
            
            bandera = 1;
            
        } catch (Exception e) {System.out.println("error al leer el archivo .ini:"+ e.getMessage());}
        return bandera;
    }
    
    public int EscribeConfigDB(){
        int bandera=0;
        
        try{
            Properties p = new Properties();
            p.load(new FileInputStream("ConfigDB.ini"));
            
            p.put("Servidor", jTextField126.getText());
            p.put("Passwd", Sistema.Encripta(jPasswordField3.getText()));
            p.put("Puerto", jTextField132.getText());
            p.put("DB", jTextField134.getText());
            p.put("Usuario", jTextField133.getText());
            
            p.put("ServidorSQL", jTextField135.getText());
            p.put("PasswdSQL", Sistema.Encripta(jPasswordField4.getText()));
            p.put("PuertoSQL", jTextField136.getText());
            p.put("DBSQL", jTextField138.getText());
            p.put("UsuarioSQL", jTextField137.getText());
            
            FileOutputStream out = new FileOutputStream("ConfigDB.ini");
            p.save(out, "/* properties updated */");
            
            bandera = 1;
            
        } catch (Exception e) {System.out.println("error al leer el archivo .ini:"+ e.getMessage());}
        return bandera;
    }
    
    public void ConsultaUsuarioTCA(){
        ResultSet rs=null;
        String user = jTextField1.getText().toUpperCase();
        String pass = jPasswordField1.getText().toUpperCase();
        String nombrecomp="";
        
        try{
           stat = con.createStatement();
           rs=ConsultasSistema.Estructura(con,"SELECT * FROM tcausr WHERE nombre = '"+user+"';");
           
           if(rs.next()){
               String uss=rs.getString("nombre").trim();
               String pwd=rs.getString("pwd").trim();
               if((uss.equals(user))&&(pass.equals(pwd))){
                   if(keyword_access.equals(rs.getString("puesto").trim())){
                       datosujat.setEnabled(true);
                   }
                   else{
                       datosujat.setEnabled(false);
                       DesbloqMenu(1);
                   }
                   nombrecomp=rs.getString("nombre_lar").trim();
                   Usuario = uss;
                   jLabel7.setText(uss);
                   jLabel9.setText(nombrecomp);
                   jLabel11.setText(Sistema.FechaSystem());
                   jLabel13.setText(Sistema.HoraSystem());
                   jTextField1.setEnabled(false);
                   jPasswordField1.setEnabled(false);
                   jButton1.setEnabled(false);
                   jButton2.setEnabled(false);
               }
               else{
                   JOptionPane.showMessageDialog(null, "contraseña incorrecta","error",JOptionPane.ERROR_MESSAGE);
               }
           }
           else{
               JOptionPane.showMessageDialog(null, "usuario no existe","error",JOptionPane.ERROR_MESSAGE);
           }
       }catch (Exception e){JOptionPane.showMessageDialog(null, e);}
    }
    
    public void DesbloqMenu(int status){
        if(status == 1){
            fac.setEnabled(true);
            acerca.setEnabled(true);
            licenciaa.setEnabled(true);
        }
        else{
            fac.setEnabled(false);
            acerca.setEnabled(false);
            licenciaa.setEnabled(false);
        }
    }
    
    public void ConsDatosFactura(String factura){
        ResultSet rs=null;
        String espacios = "",consulta="",consultanomb="",folioo="",espaciosfol="";
        int facturalength= 12 - factura.length();
        int foliolength=0,nomblargo=0;
        
        for(int i=0;i<facturalength;i++)
            espacios = espacios + " ";
        
        consulta = "SELECT f_fac,hosffa.status AS stat,Nombre,hosffa.folio,cve_ing_num,PacienteNombre,hosffa.total,p_res_fec,p_fec_sda,"+
                   "NumPoliza,NumAfiliacion,Siniestro,EmpResponsableDireccion,ClaveCliente,"+
                   "CASE WHEN DATEPART(YEAR,p_res_fec)-DATEPART(YEAR,p_fec_sda)=0 "+
                   "THEN DATEPART(DAYOFYEAR,p_fec_sda)-DATEPART(DAYOFYEAR,p_res_fec) "+
                   "ELSE DATEPART(DAYOFYEAR,p_fec_sda)+ "+
                   "(DATEPART(DAYOFYEAR,(CONVERT(VARCHAR(4), DATEPART(YEAR,p_res_fec))+'1231'))- "+
                   "DATEPART(DAYOFYEAR,p_res_fec))"+
                   "END AS DiasHosp,SegmentoMercado,p_num_cto "+
                   "FROM hosffa "+
                   "INNER JOIN hostxf ON hostxf.Area=hosffa.area AND hostxf.Folio=hosffa.folio and hostxf.Ext=hosffa.ext AND hostxf.Factura=hosffa.num_fac "+
                   "INNER JOIN hostra ON hostra.t_area=hostxf.Area AND hostra.t_folioext=hostxf.Folio+hostxf.Ext "+
                   "AND hostra.t_transaccion=hostxf.Transaccion INNER JOIN cxccli ON t_observaciones=ClaveCliente,hospac "+
                   "WHERE p_res_cve_num=cve_ing_num AND num_fac = '"+(espacios+factura)+"' AND recalculo = '' AND hostxf.TipoTra = 'C'";
        
        try{
           stat = con.createStatement();
           rs=ConsultasSistema.Estructura(con,consulta);
           
           if(rs.next()){
               jTextField2.setEnabled(false);
               jTextField3.setText(Sistema.ConversionDateTCA(rs.getString("f_fac")));
               jTextField4.setText(rs.getString("Nombre"));
               jTextField106.setText(Integer.toString(Integer.parseInt(rs.getString("ClaveCliente"))));
               jTextField5.setText(rs.getString("folio"));
               jTextField6.setText(rs.getString("cve_ing_num"));
               jTextField7.setText(rs.getString("PacienteNombre"));
               TotFactura = rs.getString("total");
               jTextField8.setText(Sistema.ConvierteMoneda(TotFactura));
               jTextField9.setText(Sistema.ConversionDateTCA(rs.getString("p_res_fec")));
               jTextField10.setText(Sistema.ConversionDateTCA(rs.getString("p_fec_sda")));
               jTextField98.setText(Integer.toString(Integer.parseInt(rs.getString("DiasHosp"))+1));
               jTextField11.setText(rs.getString("NumPoliza"));
               jTextField12.setText(rs.getString("Siniestro"));
               jTextField13.setText(rs.getString("NumAfiliacion"));
               jTextField63.setText(rs.getString("stat"));
               jTextArea1.setText(rs.getString("EmpResponsableDireccion"));
               SegMercado = rs.getString("SegmentoMercado");
               NumCto = rs.getString("p_num_cto");
               
               jButton4.setEnabled(true);
               
               consultanomb = "select * from hosffa where folio = '"+jTextField5.getText()+"'";
               try{
                   stat = con.createStatement();
                   rs=ConsultasSistema.Estructura(con,consultanomb);
                   if(rs.next()){
                       jTextField7.setText(rs.getString("nom_pac"));
                       consultanomb = "SELECT UUID FROM bocfdcomprobantes WHERE Factura=SUBSTRING('"+(espacios+factura)+"',4,12)";
                       try{
                           stat = con.createStatement();
                           rs=ConsultasSistema.Estructura(con,consultanomb);
                           if(rs.next())
                               jTextField33.setText(rs.getString("UUID"));
                           else
                               jTextField33.setText("Sin Timbre");
                       }catch (Exception e){JOptionPane.showMessageDialog(null, e);}
                   }
               }catch (Exception e){JOptionPane.showMessageDialog(null, e);}
           }
           else{
               JOptionPane.showMessageDialog(null, "factura no existe","error",JOptionPane.ERROR_MESSAGE);
           }
       }catch (Exception e){JOptionPane.showMessageDialog(null, e);}
    }
    
    public void ConsDatosFacturaModifica(String factura){
        ResultSet rs=null;
        
        try{
           stat = conMySQL.createStatement();
           rs=ConsultasSistema.Estructura(conMySQL,"SELECT * FROM company WHERE numfactura = "+factura+";");
           
           if(rs.next()){
               jTextField76.setEnabled(false);
               jTextField66.setText(rs.getString("fact_assist"));
               jTextField80.setText(rs.getString("compania"));
               jTextField79.setText(rs.getString("num_paciente"));
               jTextField78.setText(rs.getString("folio_ingreso"));
               jTextField81.setText(rs.getString("adicional"));
               jTextField82.setText(rs.getString("pac_nombre"));
               TotFactura = rs.getString("monto_total");
               jTextField77.setText(Sistema.ConvierteMoneda(TotFactura));
               jTextField67.setText(rs.getString("f_ingreso"));
               jTextField68.setText(rs.getString("f_alta"));
               jTextField83.setText(rs.getString("poliza"));
               jTextField84.setText(rs.getString("siniestro"));
               jTextField85.setText(rs.getString("afiliacion"));
               jTextField69.setText(rs.getString("empleado"));
               jTextField70.setText(rs.getString("nombre_emp"));
               jTextField71.setText(rs.getString("clave"));
               jComboBox11.setSelectedItem(rs.getString("ocupacion_emp"));
               jComboBox15.setSelectedItem(rs.getString("parentesco"));
               jTextField72.setText(rs.getString("ures"));
               jTextField73.setText(rs.getString("receta"));
               jTextField74.setText(rs.getString("pase"));
               jTextField75.setText(rs.getString("folio_interno"));
               jDateChooser10.setCalendar(ConvierteCal(rs.getString("fecha_recepcion")));
               jTextArea8.setText(rs.getString("obs_factura"));
               jTextArea9.setText(rs.getString("obs_cyc"));
               jTextArea10.setText(rs.getString("obs_adic"));
               
               jButton30.setEnabled(true);
           }
           else{
               JOptionPane.showMessageDialog(null, "factura no existe","error",JOptionPane.ERROR_MESSAGE);
           }
       }catch (Exception e){JOptionPane.showMessageDialog(null, e);}
    }
    
    public Calendar ConvierteCal(String Fecha) throws ParseException{
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        cal.setTime(sdf.parse(Fecha));// all done
        
        return cal;
    }
    
    public void ActualizaFactura(String numfactura){
        String adicional = jTextField81.getText();
        String poliza = jTextField83.getText();
        String siniestro = jTextField84.getText();
        String alta = jTextField68.getText();
        String noemp = jTextField69.getText();
        String clave = jTextField71.getText();
        String nombre = jTextField70.getText();
        String ures = jTextField72.getText();
        String receta = jTextField73.getText();
        String pase = jTextField74.getText();
        String ocupacion = jComboBox11.getSelectedItem().toString();
        String parentesco = jComboBox15.getSelectedItem().toString();
        String internas = jTextArea9.getText();
        String adicionales = jTextArea10.getText();
        
        String upd = "UPDATE company SET adicional='"+adicional+"', poliza='"+poliza+"', siniestro='"+siniestro+"', "+
                     "f_alta='"+alta+"', empleado='"+noemp+"', clave='"+clave+"', nombre_emp='"+nombre+"', ures='"+ures+"', "+
                     "receta='"+receta+"', pase='"+pase+"', ocupacion_emp='"+ocupacion+"', parentesco='"+parentesco+"', "+
                     "obs_cyc='"+internas+"', obs_adic='"+adicionales+"',elaboro_user='"+Usuario+"' WHERE numfactura="+numfactura+";";
        
        try{
            stat = con.createStatement();
            if(ConsultasSistema.InsertaActualiza(conMySQL, upd) > 0){
                JOptionPane.showMessageDialog(null, "Datos actualizados");
            }
        }catch (Exception e){JOptionPane.showMessageDialog(null, e);}
    }
    
    public void ConsMontoFactura(String factura){
        ResultSet rs=null;
        String consulta = "SELECT compania,monto_total,status_factura FROM company WHERE numfactura = "+factura+";";
        
        try{
           stat = con.createStatement();
           rs=ConsultasSistema.Estructura(conMySQL,consulta);
           
           if(rs.next()){
               jTextField44.setEnabled(false);
               jTextField45.setText(rs.getString("monto_total"));
               jTextField100.setText(rs.getString("compania"));
               
               if((rs.getString("status_factura").equals("Entregada"))||(rs.getString("status_factura").equals("Especial"))||
                  (rs.getString("status_factura").equals("EspecialUJAT"))||(rs.getString("status_factura").equals("En Revision")))
                   jButton15.setEnabled(true);
               else jButton15.setEnabled(false);
               
               ConsultaAbonos(factura);   
           }
           else{
               JOptionPane.showMessageDialog(null, "factura no existe","error",JOptionPane.ERROR_MESSAGE);
           }
       }catch (Exception e){JOptionPane.showMessageDialog(null, e);}
    }
    
    public void ConsMontoFolio(String folio){
        DefaultTableModel modelo = (DefaultTableModel)jTable9.getModel();
        double summonto=0,sumabonado=0,valor=0,valor2=0;
        double suma=0;
        ResultSet rs=null;
        String consulta = "SELECT compania,numfactura,monto_total,SUM(monto_total) AS suma,(SELECT SUM(abono) AS suma_abonos FROM abonos WHERE factura=numfactura) ";
        consulta=consulta+"AS total FROM company where folio_interno ="+folio+" GROUP BY numfactura;";
        
        for(int i=modelo.getRowCount()-1;i>=0;i--)
            modelo.removeRow(i);
        
        try{
           stat = con.createStatement();
           rs=ConsultasSistema.Estructura(conMySQL,consulta);
           
           while(rs.next()){
               suma += Double.parseDouble(rs.getString("suma"));
                Object []Fila = new Object [4];
                    Fila[0] = rs.getString("numfactura");
                    Fila[1] = rs.getString("monto_total");
                    Fila[2] = rs.getString("total");
                    Fila[3] = rs.getString("compania");
               modelo.addRow(Fila);
           }
           jLabel103.setText(Sistema.ConvierteMoneda(Double.toString(suma)));
           
           for(int i=0;i<modelo.getRowCount();i++){
               valor = Double.parseDouble(modelo.getValueAt(i, 1).toString());
               summonto += valor;
               valor2 = Double.parseDouble(modelo.getValueAt(i, 2).toString());
               sumabonado += valor2;
           }
           montofolio=Double.toString(summonto);
           abonadofolio=Double.toString(sumabonado);
           adeudofolio=Double.toString(summonto-sumabonado);
    
           jTextField56.setText(Sistema.ConvierteMoneda(montofolio));
           jTextField58.setText(Sistema.ConvierteMoneda(abonadofolio));
           jTextField59.setText(Sistema.ConvierteMoneda(adeudofolio));
           
       }catch (Exception e){JOptionPane.showMessageDialog(null, e);}
    }
    
    public void ConsultaAbonos(String Factura){
        ResultSet rs=null;
        String cons = "SELECT abono,fecha_abono,hora_abono FROM abonos WHERE factura = "+Factura+"";
        DefaultTableModel modelo = (DefaultTableModel)jTable8.getModel();
        
        for(int i=modelo.getRowCount()-1;i>=0;i--)
            modelo.removeRow(i);
        
        try{
           stat = con.createStatement();
           rs=ConsultasSistema.Estructura(conMySQL,cons);
           
           while(rs.next()){
               Object []Fila = new Object [2];
                    Fila[0] = rs.getString("fecha_abono")+" "+rs.getString("hora_abono");
                    Fila[1] = Sistema.ConvierteMoneda(rs.getString("abono"));
               modelo.addRow(Fila);
           }
           try{
               rs=null;
               stat = con.createStatement();
               rs=ConsultasSistema.Estructura(conMySQL,"SELECT SUM(abono) as total FROM abonos WHERE factura ="+Factura+";");
               
               if(rs.next())
                   jTextField47.setText(rs.getString("total"));
               
           }catch (Exception e){JOptionPane.showMessageDialog(null, e);}
           
           jTextField48.setText(Float.toString(Float.parseFloat(jTextField45.getText())-Float.parseFloat(jTextField47.getText())));
           
           monto = Float.parseFloat(jTextField45.getText());
           abonos=Float.parseFloat(jTextField47.getText());
           adeudo=Float.parseFloat(jTextField48.getText());
           
           jTextField45.setText(Sistema.ConvierteMoneda(jTextField45.getText()));
           jTextField47.setText(Sistema.ConvierteMoneda(jTextField47.getText()));
           jTextField48.setText(Sistema.ConvierteMoneda(jTextField48.getText()));
           
           /*if(adeudo == 0)
               jButton15.setEnabled(false);
           else
               jButton15.setEnabled(true);*/
           
       }catch (Exception e){JOptionPane.showMessageDialog(null, e);}
    }
    
    public void LimpiarCaptura(){
        jTextField2.setText("");
        jTextField3.setText("");
        jTextField4.setText("");
        jTextField106.setText("");
        jTextField5.setText("");
        jTextField6.setText("");
        jTextField7.setText("");
        jTextField8.setText("");
        jTextField9.setText("");
        jTextField10.setText("");
        jTextField26.setText("");
        jTextField11.setText("");
        jTextField12.setText("");
        jTextField13.setText("");
        jTextField22.setText("");
        jTextField23.setText("");
        jTextField27.setText("");
        jTextField14.setText("");
        jTextField15.setText("");
        jTextField18.setText("");
        jTextField19.setText("");
        jTextField20.setText("");
        jTextField21.setText("");
        jTextField63.setText("");
        jTextField98.setText("");
        jTextArea1.setText("");
        jTextArea2.setText("SIN OBSERVACIONES");
        jComboBox5.setSelectedIndex(0);
        jComboBox8.setSelectedIndex(0);
        jTextField91.setText("$ 0.00");
        jLabel54.setText("0");
    }
    
    public String SumaDeducible(int valor){
        float suma=0;
        
        if(valor == 1){
            Deduc = jTextField22.getText();
            Coaseg = jTextField23.getText();
            DescEspecial = jTextField27.getText();
        }
        else{
            Deduc = jTextField86.getText();
            Coaseg = jTextField87.getText();
            DescEspecial = jTextField88.getText();
        }
        
        if(Deduc.equals(""))
            Deduc = "0";
        if(Coaseg.equals(""))
            Coaseg = "0";
        if(DescEspecial.equals(""))
            DescEspecial = "0";
        
        suma = Float.parseFloat(Deduc) + Float.parseFloat(Coaseg) + Float.parseFloat(DescEspecial);
        
        return (Sistema.ConvierteMoneda(Float.toString(suma)));
    }
    
    public String CalculaFechaPago(Calendar c1, int diaspinner){
        c1.getInstance();
        int dia = c1.get(Calendar.DAY_OF_YEAR);
        int diapago = dia + diaspinner;
        
        c1.set(Calendar.YEAR, c1.get(Calendar.YEAR));
        c1.set(Calendar.MONTH, Calendar.JANUARY);
        c1.set(Calendar.DATE, 1);
        c1.add(Calendar.DATE, (diapago)-1);
        Date date = c1.getTime();
        
        String diaa = Integer.toString(c1.get(Calendar.DATE));
        String mes = Integer.toString(c1.get(Calendar.MONTH)+1);
        String annio = Integer.toString(c1.get(Calendar.YEAR));
        
        if(diaa.length() < 2)
            diaa = "0" + diaa;
        if(mes.length() < 2)
            mes = "0" + mes;
            
        return (annio+"-"+mes+"-"+diaa);
    }
    
    public void AgregaFactura(){
        String factura=jTextField2.getText();
        String UUID=jTextField33.getText();
        String folioing=jTextField6.getText();
        String nopaciente=jTextField5.getText();
        String compania=jTextField4.getText().toUpperCase();
        String numcomp=jTextField106.getText();
        String adicional=jTextField26.getText().toUpperCase();
        String paciente=jTextField7.getText();
        String poliza=jTextField11.getText().toUpperCase();
        String siniestro=jTextField12.getText().toUpperCase();
        String afiliacion=jTextField13.getText().toUpperCase();
        String deducible=jTextField22.getText();
        String coaseguro=jTextField23.getText();
        String especial=jTextField27.getText();
        String ffacturacion=jTextField3.getText();
        String fingreso=jTextField9.getText();
        String falta=jTextField10.getText();
        String dias=jTextField98.getText();
        String noempleado=jTextField14.getText();
        String nombreemp=jTextField15.getText().toUpperCase();
        String ocupacion=jComboBox8.getSelectedItem().toString().toUpperCase();
        String parentesco=jComboBox5.getSelectedItem().toString().toUpperCase();
        String clave=jTextField18.getText();
        String ures=jTextField19.getText();
        String receta=jTextField20.getText();
        String pase=jTextField21.getText();
        String obsfactura=jTextArea1.getText().toUpperCase();
        String obscyc=jTextArea2.getText().toUpperCase();
        String obsadic=jTextArea5.getText().toUpperCase();
        String statusfact="Por Entregar";
        String foliocontrol=jTextField25.getText();
        String diasliquidar="0";
        String fpago="2000-01-01";
        String frecep=Sistema.FechaPeriodo(jDateChooser7.getCalendar());
        String Insercion = "";
        int statusins = 0;
        
        if(deducible.equals(""))
            deducible = "0";
        if(coaseguro.equals(""))
            coaseguro = "0";
        if(especial.equals(""))
            especial = "0";
        
        Insercion="INSERT INTO company (numfactura,monto_total,folio_ingreso,num_paciente,compania,numcompania,adicional,pac_nombre,poliza,"+
                  "siniestro,afiliacion,deducible,coaseguro,descto_esp,fact_assist,f_ingreso,f_alta,obs_factura,obs_cyc,"+
                  "empleado,nombre_emp,ocupacion_emp,parentesco,clave,ures,receta,pase,folio_interno,fecha_liquida,"+
                  "dias_liquida,status_factura,stamp_bitacora,fecha_recepcion,elaboro_user,obs_adic,UUID,dias_hosp,segmer,cama) "+
                  "VALUES ('"+factura+"','"+TotFactura+"','"+folioing+"','"+nopaciente+"','"+compania+"',"+numcomp+",'"+adicional+
                  "','"+paciente+"','"+poliza+"','"+siniestro+"','"+afiliacion+"','"+deducible+"','"+coaseguro+"','"+especial+"','"+
                  ffacturacion+"','"+fingreso+"','"+falta+"','"+obsfactura+"','"+obscyc+"','"+noempleado+"','"+nombreemp+"','"+ocupacion+
                  "','"+parentesco+"','"+clave+"','"+ures+"','"+receta+"','"+pase+"',"+foliocontrol+",'"+fpago+"',"+diasliquidar+",'"+
                  statusfact+"',now(),'"+frecep+"','"+Usuario+"','"+obsadic+"','"+UUID+"',"+dias+",'"+SegMercado+"','"+NumCto+"')";
        
        if(ConsultasSistema.RevisaFolioBD(conMySQL, factura) == 1){
            JOptionPane.showMessageDialog(null, "La Factura ya Existe","Error",JOptionPane.ERROR_MESSAGE);
            LimpiarCaptura();
        }
        else{
            if(foliocontrol.equals(""))
                JOptionPane.showMessageDialog(null, "El Folio de Control no Puede Estar en Blanco","Error",JOptionPane.ERROR_MESSAGE);
            else
                statusins=ConsultasSistema.InsertaActualiza(conMySQL,Insercion);
        }
        
        System.out.println(Insercion);
        if(statusins > 0){
            JOptionPane.showMessageDialog(null, "Factura Guardada");
            jButton4.setEnabled(false);
            jButton3.setText("Nueva");
        }
    }
    
    public void RecibeFolios(int tipo){
        ResultSet rss=null;
        DefaultTableModel modelo = (DefaultTableModel)jTable7.getModel();
        
        for(int i=modelo.getRowCount()-1;i>=0;i--)
            modelo.removeRow(i);
        
        try{
           stat = conMySQL.createStatement();
           //rss = stat.executeQuery("SELECT folio FROM folioscontrol");
           rss=ConsultasSistema.EstructuraMySQL(conMySQL,"SELECT folio FROM folioscontrol");
           while(rss.next()){
                Object []Fila = new Object [1];
                    Fila[0] = rss.getString("folio");
                modelo.addRow(Fila);
           }
           
           for(int i=jComboBox1.getItemCount();i<modelo.getRowCount();i++){
               jComboBox2.addItem(modelo.getValueAt(i, 0));
               jComboBox4.addItem(modelo.getValueAt(i, 0));
               jComboBox10.addItem(modelo.getValueAt(i, 0));
               
               if(tipo == 1){
                   jComboBox1.addItem(modelo.getValueAt(i, 0));
                   jComboBox7.addItem(modelo.getValueAt(i, 0));
                   jComboBox12.addItem(modelo.getValueAt(i, 0));
               }
           }
           
       }catch (Exception e){JOptionPane.showMessageDialog(null, e);}
    }
    
    public void ObtieneDatosFolio(String Folio){
        ResultSet rs=null;
        DefaultTableModel modelo = (DefaultTableModel)jTable1.getModel();
        
        for(int i=modelo.getRowCount()-1;i>=0;i--)
            modelo.removeRow(i);
        
        try{
           stat = con.createStatement();
           rs=ConsultasSistema.EstructuraMySQL(conMySQL,"SELECT * FROM company WHERE folio_interno="+Folio+";");
           
           while(rs.next()){
               Object []Fila = new Object [3];
                    Fila[0] = rs.getString("numfactura");
                    Fila[1] = rs.getString("compania");
                    Fila[2] = Sistema.ConvierteMoneda(rs.getString("monto_total"));
                modelo.addRow(Fila);
           }
       }catch (Exception e){JOptionPane.showMessageDialog(null, e);}
    }
    
    public void ObtieneDatosFolio2(String Folio){
        ResultSet rs=null;
        DefaultTableModel modelo = (DefaultTableModel)jTable5.getModel();
        TableRowSorter<TableModel> elQueOrdena = new TableRowSorter<TableModel>(modelo);
        jTable5.setRowSorter(elQueOrdena);
        
        for(int i=modelo.getRowCount()-1;i>=0;i--)
            modelo.removeRow(i);
        
        try{
           stat = con.createStatement();
           rs=ConsultasSistema.EstructuraMySQL(conMySQL,"SELECT * FROM company WHERE folio_interno="+Folio+";");
           
           while(rs.next()){
               Object []Fila = new Object [5];
                    Fila[0] = rs.getString("numfactura");
                    Fila[1] = rs.getString("compania");
                    Fila[2] = Sistema.ConvierteMoneda(rs.getString("monto_total"));
                    Fila[3] = rs.getString("status_factura");
                    Fila[4] = Sistema.ConversionFechasTCA(rs.getString("fact_assist"),1);
                modelo.addRow(Fila);
           }
       }catch (Exception e){JOptionPane.showMessageDialog(null, e);}
    }
    
    public void ObtieneDatosFacturaMySQL(String Folio){
        ResultSet rs;
        DefaultTableModel modelo = (DefaultTableModel)jTable4.getModel();
        SimpleDateFormat formato = new java.text.SimpleDateFormat("yyyy-MM-dd"); 
        String ffactura="";
        Date fechaDate = new Date();
        
        for(int i=modelo.getRowCount()-1;i>=0;i--)
            modelo.removeRow(i);
        
        try{
           stat = con.createStatement();
           rs=ConsultasSistema.EstructuraMySQL(conMySQL,"SELECT * FROM company WHERE numfactura='"+Folio+"'");
           
           if(rs.next()){
               jComboBox3.setEnabled(true);
               jButton5.setEnabled(true);
               jTextField24.setEnabled(false);
               jTextField29.setText(Sistema.ConvierteMoneda(rs.getString("monto_total")));
               jTextField30.setText(rs.getString("compania"));
               jTextField31.setText(rs.getString("pac_nombre"));
               jTextField32.setText(rs.getString("folio_interno"));
               ffactura = rs.getString("fecha_liquida");
               jTextArea3.setText(rs.getString("obs_cyc"));
               jComboBox3.setSelectedItem(rs.getString("status_factura"));
               
               try {
                    fechaDate = formato.parse(ffactura);
                } catch (ParseException ex) {Logger.getLogger(Facturas.class.getName()).log(Level.SEVERE, null, ex);}
                jDateChooser31.setDate(fechaDate);
               
               if(jComboBox3.getSelectedItem().toString().equals("Liquidada"))
                   jButton5.setEnabled(false);
               
               try{
                   stat = con.createStatement();
                   rs=ConsultasSistema.EstructuraMySQL(conMySQL,"SELECT * FROM bitacora WHERE factura='"+Folio+"'");

                   while(rs.next()){
                       Object []Fila = new Object [4];
                            Fila[0] = rs.getString("status_fact");
                            Fila[1] = rs.getString("fecha_status");
                            Fila[2] = rs.getString("observacion");
                            Fila[3] = rs.getString("usuario_elaboro");
                       modelo.addRow(Fila);
                   }
            }catch (Exception e){JOptionPane.showMessageDialog(null, e);}
           }
           else{
               jComboBox3.setEnabled(false);
               JOptionPane.showMessageDialog(null, "No se encontraron datos con ese Número de Factura","Error",JOptionPane.ERROR_MESSAGE);
           }
       }catch (Exception e){JOptionPane.showMessageDialog(null, e);}
    }
    
    public void ObtieneHistorialFactura(){
        ResultSet rs;
        DefaultTableModel modeloPac = (DefaultTableModel)jTable12.getModel(); 
        DefaultTableModel modelo = (DefaultTableModel)jTable16.getModel(); 
        String Folio = modeloPac.getValueAt(jTable12.getSelectedRow(), 0).toString();
        
        for(int i=modelo.getRowCount()-1;i>=0;i--)
            modelo.removeRow(i);
        
        try{
            stat = con.createStatement();
            rs=ConsultasSistema.EstructuraMySQL(conMySQL,"SELECT * FROM bitacora WHERE factura='"+Folio+"'");
            
            while(rs.next()){
               Object []Fila = new Object [2];
                    Fila[0] = rs.getString("status_fact");
                    Fila[1] = rs.getString("observacion");
               modelo.addRow(Fila);
           }
        }catch (Exception e){JOptionPane.showMessageDialog(null, e);}
    }
    
    public void LimpiaConsultas(){
        DefaultTableModel modelo = (DefaultTableModel)jTable4.getModel();
        
        for(int i=modelo.getRowCount()-1;i>=0;i--)
            modelo.removeRow(i);
        
        jTextField24.setEnabled(true);
        jTextField24.setText("");
        jTextField29.setText("");
        jTextField30.setText("");
        jTextField31.setText("");
        jTextField32.setText("");
        jTextArea3.setText("");
        jComboBox3.setSelectedIndex(0);
    }
    
    public void ActualizaStatus(){
        int statactualiza=0;
        String update = "UPDATE company SET obs_cyc='"+jTextArea3.getText()+"',status_factura='";
        update=update+jComboBox3.getSelectedItem().toString()+"',dias_liquida="+jSpinner2.getValue().toString();
        update=update+",fecha_liquida='"+Sistema.FechaPeriodo(jDateChooser31.getCalendar())+"',stamp_bitacora=now(), ";
        update=update+"obs_adic='"+jTextArea7.getText()+"',elaboro_user='"+Usuario+"' WHERE numfactura='"+jTextField24.getText()+"';";
        
        try{
           stat = con.createStatement();
           statactualiza=ConsultasSistema.InsertaActualiza(conMySQL, update);

           if(statactualiza > 0){
               JOptionPane.showMessageDialog(null, "Status Actualizado");
               LimpiaConsultas();
           }
        }catch (Exception e){JOptionPane.showMessageDialog(null, e);}
    }
    
    public void AfectaFolio(String folio){
        int statactualiza=0;
        String fechaentrega = "";
        String update = "";
   
        if(jComboBox6.getSelectedItem().equals("Liquidada")){
            update = "UPDATE company SET status_factura='Liquidada',fecha_liquida='"+Sistema.FechaPeriodo(jDateChooser22.getCalendar())+
                     "',stamp_bitacora=now(),obs_cyc='"+jTextArea6.getText()+"',elaboro_user='"+Usuario+"' WHERE folio_interno='"+folio+"';";
        }
        else{
            if(jComboBox6.getSelectedItem().equals("Entregada")){
                fechaentrega = Sistema.FechaPeriodo(jDateChooser9.getCalendar());
                update = "UPDATE company SET status_factura='Entregada',dias_liquida="+
                         jSpinner3.getValue().toString()+",fecha_liquida='"+jTextField54.getText()+"',stamp_bitacora=now()"+
                         ",fecha_recepcion='"+fechaentrega+"',obs_cyc='"+jTextArea6.getText()+"',elaboro_user='"+Usuario+"' WHERE folio_interno='"+folio+"';";
            }
            else{
                update = "UPDATE company SET status_factura='"+jComboBox6.getSelectedItem().toString()+"',"+
                         "stamp_bitacora=now(),obs_cyc='"+jTextArea6.getText()+"',elaboro_user='"+Usuario+"' WHERE folio_interno='"+folio+"';";
            }
        }
        
        System.out.println(update);
        
        try{
           stat = con.createStatement();
           statactualiza=ConsultasSistema.InsertaActualiza(conMySQL, update);

           if(statactualiza > 0){
               JOptionPane.showMessageDialog(null, "Status Actualizado");
               LimpiaConsultas();
           }
        }catch (Exception e){JOptionPane.showMessageDialog(null, e);}
    }
    
    public void InsertaDeposito(){
        int statt=0;
        String insercion = "INSERT INTO depositos (compania,docto,autor,monto,tipo_pago,fecha,stampa) VALUES('";
        insercion=insercion+jTextField35.getText()+"','"+jTextField34.getText()+"','"+jTextField38.getText()+"','"+jTextField37.getText()+"','";
        insercion=insercion+jTextField36.getText()+"',curdate(),now());";
        
        try{
           stat = con.createStatement();
           statt=ConsultasSistema.InsertaActualiza(conMySQL, insercion);

           if(statt > 0){
               JOptionPane.showMessageDialog(null, "Datos Insertados");
               LimpiaDepositos();
           }
        }catch (Exception e){JOptionPane.showMessageDialog(null, e);}
    }
    
    public void LimpiaDepositos(){
        jTextField34.setText("");
        jTextField35.setText("");
        jTextField36.setText("");
        jTextField37.setText("");
        jTextField38.setText("");
    }
    
    public void BuscaCompania(String likee){
        ResultSet rs=null;
        DefaultTableModel modelo = (DefaultTableModel)jTable2.getModel();
        String senten="SELECT compania,docto FROM depositos WHERE compania like '%"+likee+"%' ";
        senten=senten+"OR docto like '%"+likee+"%' ORDER BY compania ASC;";
        
        for(int i=modelo.getRowCount()-1;i>=0;i--)
            modelo.removeRow(i);
        
        try{
           stat = con.createStatement();
           rs=ConsultasSistema.EstructuraMySQL(conMySQL,senten);
           
           while(rs.next()){
               Object []Fila = new Object [2];
                    Fila[0] = rs.getString("compania");
                    Fila[1] = rs.getString("docto");
                modelo.addRow(Fila);
           }
        }catch (Exception e){JOptionPane.showMessageDialog(null, e);}
    }
    
    public void CoincideCompa(String likee){
        ResultSet rs;
        DefaultTableModel modelo = (DefaultTableModel)jTable2.getModel();
        
        for(int i=modelo.getRowCount()-1;i>=0;i--)
            modelo.removeRow(i);
        
        String cons="SELECT compania,docto FROM depositos WHERE compania like '%"+likee+"%' ";
        cons=cons+"OR docto like '%"+likee+"%' ORDER BY compania ASC;";
        try{
            stat = con.createStatement();
            rs = ConsultasSistema.EstructuraMySQL(conMySQL,cons);
            while(rs.next()){
                Object []Fila = new Object [2];
                    Fila[0]=rs.getString("compania");
                    Fila[1]=rs.getString("docto");
                modelo.addRow(Fila);
            }
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null,"Error obteniendo coincidencias");
        }
    }
    
    public void CoincideCompaEdoCta(String likee,int tipo){
        ResultSet rs;
        TableRowSorter<TableModel> elQueOrdena = new TableRowSorter<TableModel>(modeloT6);
        jTable6.setRowSorter(elQueOrdena);
        String cons="";
        
        for(int i=modeloT6.getRowCount()-1;i>=0;i--)
            modeloT6.removeRow(i);
        
        if(tipo == 0){
            cons="SELECT DISTINCT ClaveCliente,Nombre,rtrim(Email) as Email\n"+
                 "FROM cxccli\n" +
                 "WHERE Nombre like '%"+likee+"%'\n" +
                 "ORDER BY Nombre ASC;";
        }
        else{
            cons="SELECT DISTINCT ClaveCliente,Nombre,rtrim(Email) as Email\n" +
                 "FROM cxccli\n" +
                 "WHERE ClaveCliente = '"+armaClave(likee)+"'\n" +
                 "ORDER BY Nombre ASC;";
        }
        try{
            stat = con.createStatement();
            rs = ConsultasSistema.EstructuraMySQL(con,cons);
            while(rs.next()){
                Object []Fila = new Object [3];
                    Fila[0]=rs.getString("ClaveCliente");
                    Fila[1]=rs.getString("Nombre");
                    Fila[2]=rs.getString("Email");
                modeloT6.addRow(Fila);
            }
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }
    
    public String armaClave(String clave){
        String cero="";
        for(int i=0;i<(9-clave.length());i++)
            cero += "0";
        return cero+clave;
    }
    
    public void CoincidePaciente(String likee){
        ResultSet rs;
        DefaultTableModel modelo = (DefaultTableModel)jTable11.getModel();
        
        for(int i=modelo.getRowCount()-1;i>=0;i--)
            modelo.removeRow(i);
        
        String cons="SELECT pac_nombre,num_paciente FROM company WHERE pac_nombre LIKE '%"+likee+"%'";
        try{
            stat = con.createStatement();
            rs = ConsultasSistema.EstructuraMySQL(conMySQL,cons);
            while(rs.next()){
                Object []Fila = new Object [2];
                    Fila[0]=rs.getString("pac_nombre");
                    Fila[1]=rs.getString("num_paciente");
                modelo.addRow(Fila);
            }
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }
    
    public void AbonoFactura(String abono){
        String inserta = "";
        float cantabono = Float.parseFloat(abono),total=adeudo-cantabono;
        int opc = 0;
        jLabel84.setText(Float.toString(adeudo));
        
        if(cantabono > adeudo)
            JOptionPane.showMessageDialog(null, "El Abono no Puede Exceder el Adeudo","Error",JOptionPane.ERROR_MESSAGE);
        else{
            opc = JOptionPane.showConfirmDialog(null, "¿Abonar "+Sistema.ConvierteMoneda(abono)+" y dejar la cuenta en "+Sistema.ConvierteMoneda(Float.toString(total))+
                                                      "? CUIDADO! se puede modificar el estatus también, verifique","Confirme",JOptionPane.YES_NO_OPTION);
            if(opc == 0){
                inserta = "INSERT INTO abonos (factura,abono,fecha_abono,hora_abono,compannia) VALUES ("+jTextField44.getText()+",'";
                inserta = inserta+cantabono+"','"+Sistema.FechaPeriodo(jDateChooser30.getCalendar())+"',now(),'"+jTextField100.getText()+"');";
                if(ConsultasSistema.InsertaActualiza(conMySQL, inserta)>0){
                    if(total == 0){
                        inserta="UPDATE company SET status_factura='Liquidada',obs_cyc='FACTURA LIQUIDADA CON EXITO POR ABONO AUTOMATICAMENTE',"+
                                "stamp_bitacora=now(),fecha_liquida='"+Sistema.FechaPeriodo(jDateChooser30.getCalendar())+
                                "',elaboro_user='"+Usuario+"' WHERE numfactura="+jTextField44.getText()+";";
                        if(ConsultasSistema.InsertaActualiza(conMySQL, inserta)>0){
                            JOptionPane.showMessageDialog(null, "Abono Grabado y Factura Liquidada");
                        }
                    }
                    else
                        JOptionPane.showMessageDialog(null, "Abono Grabado");
                    jButton15.setEnabled(false);
                    jButton36.setEnabled(true);
                }
            }
        }
    }
    
    public void AbonaFolio(){
        ConsMontoFolio(jComboBox12.getSelectedItem().toString());
        DefaultTableModel modelo = (DefaultTableModel)jTable9.getModel();
        float monto=0,abonado=0,adeudo=0,abono=Float.parseFloat(jTextField57.getText()),totabono=Float.parseFloat(adeudofolio);
        String factura="",inserta="";
        
        if(abono > totabono)
            JOptionPane.showMessageDialog(null,"el abono no puede exceder el adeudo","error",JOptionPane.ERROR_MESSAGE);
        else{
            for(int i=0;i<modelo.getRowCount();i++){
                factura = modelo.getValueAt(i, 0).toString();
                monto = Float.parseFloat(modelo.getValueAt(i, 1).toString());
                abonado = Float.parseFloat(modelo.getValueAt(i, 2).toString());
                adeudo = monto - abonado;

                if(abono == 0){
                    JOptionPane.showMessageDialog(null,"no se han realizado operaciones","error",JOptionPane.ERROR_MESSAGE);
                    break;
                }
                else{
                    if(adeudo == 0)
                        continue;
                    else{
                        if(adeudo < abono){ //Liquida la Factura
                            inserta = "INSERT INTO abonos (compannia,factura,abono,fecha_abono,hora_abono) VALUES (";
                            inserta=inserta+"(SELECT compania from company WHERE numfactura='"+factura+"'),"+factura;
                            inserta=inserta+",'"+adeudo+"','"+Sistema.FechaPeriodo(jDateChooser29.getCalendar())+"',now());";
                            abono = abono - adeudo;
                            ConsultasSistema.InsertaActualiza(conMySQL, inserta);
                            inserta="UPDATE company SET status_factura='Liquidada',obs_cyc='FACTURA LIQUIDADA CON EXITO POR ABONO AUTOMATICAMENTE',"+
                                    "elaboro_user='"+Usuario+"',stamp_bitacora=now(),fecha_liquida='"+Sistema.FechaPeriodo(jDateChooser29.getCalendar())+
                                    "' WHERE numfactura="+factura+";";
                            ConsultasSistema.InsertaActualiza(conMySQL, inserta);
                        }
                        else{
                            if(abono < adeudo){ //Abona a Factura
                                inserta = "INSERT INTO abonos (compannia,factura,abono,fecha_abono,hora_abono) VALUES (";
                                inserta=inserta+"(SELECT compania from company WHERE numfactura='"+factura+"'),"+factura;
                                inserta=inserta+",'"+abono+"','"+Sistema.FechaPeriodo(jDateChooser29.getCalendar())+"',now());";
                                ConsultasSistema.InsertaActualiza(conMySQL, inserta);
                                abono = 0;
                            }
                        }
                    }
                    System.out.println(inserta);
                }
            }
            JOptionPane.showMessageDialog(null, "Operaciones Termnadas Satisfactoriamente");
        }
    }
    
    public void CalculaDias(int tipo,String Factura){
        ResultSet rs = null;
        String cons = "";
        int dinternado=0,dfacturar=0,dcxc=0,dentrega=0,dliquida=0;
        
        if(tipo == 0){
            cons = "SELECT f_ingreso,f_alta,fact_assist,fecha_recepcion,\n"+
                   "      (SELECT DATE(fecha_status)\n"+
                   "       FROM bitacora\n"+
                   "       WHERE factura=numfactura\n"+
                   "       AND status_fact='Entregada') AS fecha_entrega,\n"+
                   "      (SELECT DATE(fecha_status)\n"+
                   "       FROM bitacora\n"+
                   "       WHERE factura=numfactura\n"+
                   "       AND status_fact='Liquidada') AS fecha_liquida\n"+
                   "FROM company \n"+
                   "WHERE numfactura = "+Factura+";";
        }
        else{
            cons = "SELECT RTRIM(compania) AS c,numfactura,f_ingreso,f_alta,fact_assist,fecha_recepcion,\n"+
                   "       IFNULL((SELECT MAX(DATE(fecha_status))\n"+
                   "              FROM bitacora\n"+
                   "              WHERE factura=numfactura\n"+
                   "              AND status_fact='Entregada'),'0') AS fecha_entrega,\n"+
                   "       IFNULL((SELECT MAX(DATE(fecha_status))\n"+
                   "               FROM bitacora\n"+
                   "               WHERE factura=numfactura\n"+
                   "               AND status_fact='Liquidada'),'0') AS fecha_liquida\n"+
                   "FROM company \n"+
                   "WHERE fecha_recepcion BETWEEN '"+Sistema.FechaPeriodo(jDateChooser27.getCalendar())+
                                           "' AND '"+Sistema.FechaPeriodo(jDateChooser28.getCalendar())+"'";
        }
        
        
        try{
            stat = con.createStatement();
            rs = ConsultasSistema.EstructuraMySQL(conMySQL,cons);
            
            if(tipo == 0){
                if(rs.next()){
                    dinternado = Sistema.DiferenciaDias(rs.getString("f_alta")) - Sistema.DiferenciaDias(rs.getString("f_ingreso"));
                    dfacturar = Sistema.DiferenciaDias(rs.getString("fact_assist")) - Sistema.DiferenciaDias(rs.getString("f_alta"));
                    dcxc = Sistema.DiferenciaDias(rs.getString("fecha_recepcion")) - Sistema.DiferenciaDias(rs.getString("fact_assist"));
                    dentrega = Sistema.DiferenciaDias(rs.getString("fecha_entrega")) - Sistema.DiferenciaDias(rs.getString("fecha_recepcion"));
                    dliquida = Sistema.DiferenciaDias(rs.getString("fecha_liquida")) - Sistema.DiferenciaDias(rs.getString("fecha_entrega"));
                }
                jTextField41.setText(Integer.toString(dinternado));
                jTextField49.setText(Integer.toString(dfacturar));
                jTextField50.setText(Integer.toString(dcxc));
                jTextField51.setText(Integer.toString(dentrega));
                jTextField52.setText(Integer.toString(dliquida));
            }
            else{
                for(int i=modeloT25.getRowCount()-1;i>=0;i--)
                    modeloT25.removeRow(i);
                while(rs.next()){
                    dinternado = Sistema.DiferenciaDias(rs.getString("f_alta")) - Sistema.DiferenciaDias(rs.getString("f_ingreso"));
                    dfacturar = Sistema.DiferenciaDias(rs.getString("fact_assist")) - Sistema.DiferenciaDias(rs.getString("f_alta"));
                    dcxc = Sistema.DiferenciaDias(rs.getString("fecha_recepcion")) - Sistema.DiferenciaDias(rs.getString("fact_assist"));
                    dentrega = Sistema.DiferenciaDias(rs.getString("fecha_entrega")) - Sistema.DiferenciaDias(rs.getString("fecha_recepcion"));
                    dliquida = Sistema.DiferenciaDias(rs.getString("fecha_liquida")) - Sistema.DiferenciaDias(rs.getString("fecha_entrega"));
                    
                    Object []Fila = new Object [7];
                    Fila[0]=rs.getString("c");
                    Fila[1]=rs.getString("numfactura");
                    Fila[2]=dinternado;
                    Fila[3]=dfacturar;
                    Fila[4]=dcxc;
                    Fila[5]=dentrega;
                    Fila[6]=dliquida;
                    modeloT25.addRow(Fila);
                }
                cent_tablaestad();
            }
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }
    
    public void MuestraDatosAll(String Tipo){
        ResultSet rs=null;
        String consulta="";
        DefaultTableModel modelo = (DefaultTableModel)jTable3.getModel();
        
        for(int i=modelo.getRowCount()-1;i>=0;i--)
            modelo.removeRow(i);
        
        if(jCheckBox3.isSelected() == true){
            consulta="SELECT *,'' AS entrega FROM company WHERE fact_assist BETWEEN '"+Sistema.FechaPeriodo(jDateChooser11.getCalendar())+
                     "' AND '"+Sistema.FechaPeriodo(jDateChooser14.getCalendar())+"';";
        }
        else{
            if(Tipo.equals("Compania")){
            consulta="SELECT *,IFNULL((SELECT DISTINCT MAX(a.fecha_status) AS fecha\n" +
                     "                 FROM bitacora a\n" +
                     "                 WHERE a.status_fact = 'Entregada'\n" +
                     "                 AND a.factura=b.numfactura\n" +
                     "                 GROUP BY a.factura),'') AS entrega\n" +
                     "FROM company b\n" +
                     "WHERE numcompania = '"+numcia+"'\n" +
                     "AND (fact_assist BETWEEN '"+Sistema.FechaPeriodo(jDateChooser2.getCalendar())+"' AND '" +
                     Sistema.FechaPeriodo(jDateChooser3.getCalendar())+"');";
            }
            else{
                if(Tipo.equals("Parametros")){
                    if(jComboBox16.getSelectedIndex() == 0)
                        consulta = "SELECT *,'' AS entrega FROM company";
                    else
                        consulta = "SELECT *,'' AS entrega FROM company where fecha_recepcion BETWEEN '"+Sistema.FechaPeriodo(jDateChooser11.getCalendar())+
                                   "' AND '"+Sistema.FechaPeriodo(jDateChooser14.getCalendar())+"'";
                }
                else{
                    if(Tipo.equals("Folio")){
                        if(jComboBox13.getSelectedIndex() == 0)
                            consulta="SELECT *,'' AS entrega FROM company WHERE folio_interno = "+jComboBox4.getSelectedItem().toString();
                        else
                            consulta="SELECT *,'' AS entrega FROM company WHERE folio_interno = "+jComboBox4.getSelectedItem().toString()+" AND status_factura='"+
                                     jComboBox13.getSelectedItem().toString()+"'";
                    }
                    else
                        JOptionPane.showMessageDialog(null, "no se reconoce (String Tipo) en la función","error",JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        
        try{
            stat = con.createStatement();
            rs = ConsultasSistema.EstructuraMySQL(conMySQL,consulta);
            while(rs.next()){
                Object []Fila = new Object [39];
                    Fila[0]=rs.getString("numfactura");
                    Fila[1]=rs.getString("UUID");
                    Fila[2]=rs.getString("monto_total");
                    Fila[3]=rs.getString("folio_ingreso");
                    Fila[4]=rs.getString("num_paciente");
                    Fila[5]=rs.getString("compania");
                    Fila[6]=rs.getString("numcompania");
                    Fila[7]=rs.getString("adicional");
                    Fila[8]=rs.getString("pac_nombre");
                    Fila[9]=rs.getString("poliza");
                    Fila[10]=rs.getString("siniestro");
                    Fila[11]=rs.getString("afiliacion");
                    Fila[12]=rs.getString("deducible");
                    Fila[13]=rs.getString("coaseguro");
                    Fila[14]=rs.getString("descto_esp");
                    Fila[15]=Sistema.ConversionFechasTCA(rs.getString("fact_assist"),2);
                    Fila[16]=Sistema.ConversionFechasTCA(rs.getString("f_ingreso"),2);
                    Fila[17]=Sistema.ConversionFechasTCA(rs.getString("f_alta"),2);
                    Fila[18]=rs.getString("obs_factura");
                    Fila[19]=rs.getString("obs_cyc");
                    Fila[20]=rs.getString("empleado");
                    Fila[21]=rs.getString("nombre_emp");
                    Fila[22]=rs.getString("ocupacion_emp");
                    Fila[23]=rs.getString("parentesco");
                    Fila[24]=rs.getString("clave");
                    Fila[25]=rs.getString("ures");
                    Fila[26]=rs.getString("receta");
                    Fila[27]=rs.getString("pase");
                    Fila[28]=rs.getString("folio_interno");
                    Fila[29]=Sistema.ConversionFechasTCA(rs.getString("fecha_liquida"),2);
                    Fila[30]=rs.getString("dias_liquida");
                    Fila[31]=rs.getString("status_factura");
                    Fila[32]=rs.getString("stamp_bitacora");
                    Fila[33]=Sistema.ConversionFechasTCA(rs.getString("fecha_recepcion"),2);
                    Fila[34]=Sistema.ConversionFechasTCA(rs.getString("fecha_refact"),2);
                    Fila[35]=rs.getString("factura_sust");
                    Fila[36]=rs.getString("elaboro_user");
                    Fila[37]=rs.getString("cama");
                    Fila[38]=rs.getString("entrega");
                modelo.addRow(Fila);
            }
            //cent_contraesp();
            ExcelEspeciales();
            jTextField53.setEnabled(true);
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }
    
    public void ConsFacturaNoPaciente(String NoPaciente){
        ResultSet rs=null;
        String consulta="SELECT numfactura,compania,monto_total FROM company WHERE num_paciente="+NoPaciente+";";
        DefaultTableModel modelo = (DefaultTableModel)jTable12.getModel();
        
        for(int i=modelo.getRowCount()-1;i>=0;i--)
            modelo.removeRow(i);
        
        try{
            stat = con.createStatement();
            rs = ConsultasSistema.EstructuraMySQL(conMySQL,consulta);
            while(rs.next()){
                Object []Fila = new Object [3];
                    Fila[0]=rs.getString("numfactura");
                    Fila[1]=rs.getString("compania");
                    Fila[2]=rs.getString("monto_total");
                modelo.addRow(Fila);
            }
        }
        catch (Exception e){JOptionPane.showMessageDialog(null,e);}
    }
    
    public String GeneraRandom(){
        String cve_generada="";
        Random randomGenerator = new Random();
        int randomInt = 0;
        
        for(int i=0;i<4;i++){
            randomInt = randomGenerator.nextInt(10);
            cve_generada += Integer.toString(randomInt);
        }
        return cve_generada;
    }
    
    public void ExcelEspeciales(){
        DefaultTableModel modeloT1 = (DefaultTableModel) jTable3.getModel();
        int lng = modeloT1.getRowCount();
        int i=0;
        HSSFRow fila0=null;
        HSSFCell numfactura0=null;
        String NombreArchivo = Sistema.FechaPeriodo(Calendar.getInstance())+"_"+GeneraRandom()+".xls";
        
        if(lng == 0)
            JOptionPane.showMessageDialog(null,"no hay registros", "error", JOptionPane.ERROR_MESSAGE);

        else{
            HSSFWorkbook libro = new HSSFWorkbook();
            HSSFSheet hoja = libro.createSheet("Contrarecibo");
            fila0 = hoja.createRow(0);
            
            for(int j=0;j<modeloT1.getColumnCount();j++){
                numfactura0=fila0.createCell((short) j);
                numfactura0.setCellValue(new HSSFRichTextString(modeloT1.getColumnName(j)));
            }
            for(int j=0;j<modeloT1.getColumnCount();j++){
                for(int k=0;k<modeloT1.getRowCount();k++){
                    fila0 = hoja.createRow(k+1);
                    numfactura0=fila0.createCell((short) j);
                    numfactura0.setCellValue(new HSSFRichTextString(modeloT1.getValueAt(k, j).toString()));
                }
            }
            
            //---------------------------
            try{
                FileOutputStream elFichero = new FileOutputStream(carpetaExcel + NombreArchivo);
                libro.write(elFichero);
                elFichero.close();
            }catch (Exception e){e.printStackTrace();}
        }
        try{
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+carpetaExcel + NombreArchivo);
        }catch (IOException ee){ee.printStackTrace();}   
    }
    
    public void ExcelEstadistica(){
        int lng = modeloT25.getRowCount();
        int i=0;
        HSSFRow fila0=null;
        HSSFCell numfactura0=null;
        String NombreArchivo = "Concentrado-Estadistico_"+Sistema.FechaPeriodo(Calendar.getInstance())+"_"+GeneraRandom()+".xls";
        
        if(lng == 0)
            JOptionPane.showMessageDialog(null,"no hay registros", "error", JOptionPane.ERROR_MESSAGE);

        else{
            HSSFWorkbook libro = new HSSFWorkbook();
            HSSFSheet hoja = libro.createSheet("Estadistica");
            fila0 = hoja.createRow(0);
            
            for(int j=0;j<modeloT25.getColumnCount();j++){
                numfactura0=fila0.createCell((short) j);
                numfactura0.setCellValue(new HSSFRichTextString(modeloT25.getColumnName(j)));
            }
            for(int j=0;j<modeloT25.getColumnCount();j++){
                for(int k=0;k<modeloT25.getRowCount();k++){
                    fila0 = hoja.createRow(k+1);
                    numfactura0=fila0.createCell((short) j);
                    numfactura0.setCellValue(new HSSFRichTextString(modeloT25.getValueAt(k, j).toString()));
                }
            }
            
            //---------------------------
            try{
                FileOutputStream elFichero = new FileOutputStream(carpetaExcel + NombreArchivo);
                libro.write(elFichero);
                elFichero.close();
            }catch (Exception e){e.printStackTrace();}
        }
        try{
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+carpetaExcel + NombreArchivo);
        }catch (IOException ee){ee.printStackTrace();}
    }
    
    public void ExcelStatus(){
        int lng = modeloT23.getRowCount();
        int i=0;
        HSSFRow fila0=null;
        HSSFCell numfactura0=null;
        String NombreArchivo = "Concentrado-Status_"+Sistema.FechaPeriodo(Calendar.getInstance())+"_"+GeneraRandom()+".xls";
        
        if(lng == 0)
            JOptionPane.showMessageDialog(null,"no hay registros", "error", JOptionPane.ERROR_MESSAGE);

        else{
            HSSFWorkbook libro = new HSSFWorkbook();
            HSSFSheet hoja = libro.createSheet("Contrarecibo");
            fila0 = hoja.createRow(0);
            
            for(int j=0;j<modeloT23.getColumnCount();j++){
                numfactura0=fila0.createCell((short) j);
                numfactura0.setCellValue(new HSSFRichTextString(modeloT23.getColumnName(j)));
            }
            for(int j=0;j<modeloT23.getColumnCount();j++){
                for(int k=0;k<modeloT23.getRowCount();k++){
                    fila0 = hoja.createRow(k+1);
                    numfactura0=fila0.createCell((short) j);
                    numfactura0.setCellValue(new HSSFRichTextString(modeloT23.getValueAt(k, j).toString()));
                }
            }
            
            //---------------------------
            try{
                FileOutputStream elFichero = new FileOutputStream(carpetaExcel + NombreArchivo);
                libro.write(elFichero);
                elFichero.close();
            }catch (Exception e){e.printStackTrace();}
        }
        try{
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+carpetaExcel + NombreArchivo);
        }catch (IOException ee){ee.printStackTrace();}   
    }
    
    public void RastreaFactura(String texto){
        ResultSet rs;
        DefaultTableModel modelo = (DefaultTableModel)jTable10.getModel();
        String cons = "SELECT num_fac,ext,f_fac,u_fac,status,fac_nueva FROM hosffa where folio = "+Sistema.ConversionFormatosTCA(8, texto)+";";
        
        for(int i=modelo.getRowCount()-1;i>=0;i--)
            modelo.removeRow(i);
        
        try{
            stat = con.createStatement();
            rs = ConsultasSistema.Estructura(con,cons);
            while(rs.next()){
                Object []Fila = new Object [6];
                    Fila[0]=rs.getString("num_fac");
                    Fila[1]=rs.getString("ext");
                    Fila[2]=Sistema.ConversionDateTCA(rs.getString("f_fac"));
                    Fila[3]=rs.getString("u_fac");
                    Fila[4]=rs.getString("status");
                    Fila[5]=rs.getString("fac_nueva");
                modelo.addRow(Fila);
            }
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }
    
    public void SelecFolioFactura(String factura){
        ResultSet rs=null;
        String folio="";
        String consulta = "SELECT folio_interno,compania FROM company WHERE numfactura = "+factura+";";
        DefaultTableModel modelo = (DefaultTableModel)jTable13.getModel();
        
        for(int i=modelo.getRowCount()-1;i>=0;i--)
            modelo.removeRow(i);
        
        try{
            stat = con.createStatement();
            rs = ConsultasSistema.Estructura(conMySQL,consulta);
            if(rs.next()){
                jTextField61.setEnabled(false);
                folio = rs.getString("folio_interno");
                jTextField62.setText(folio);
                /*try{
                    consulta="SELECT DISTINCT folio_interno,compania FROM company WHERE compania = ";
                    consulta=consulta+"ANY (SELECT compania FROM company WHERE numfactura="+factura+") ORDER BY folio_interno ASC;";
                    stat = con.createStatement();
                    rs = ConsultasSistema.Estructura(conMySQL,consulta);
                    while(rs.next()){
                        Object []Fila = new Object [2];
                            Fila[0]=rs.getString("folio_interno");
                            Fila[1]=rs.getString("compania");
                        modelo.addRow(Fila);
                    }
                }catch (Exception e){JOptionPane.showMessageDialog(null,e);}*/
            }
        }catch (Exception e){JOptionPane.showMessageDialog(null,e);}
    }
    
    public void TransfiereFacturaFolio(String factura){
        String actualiza = "UPDATE company SET folio_interno="+jComboBox10.getSelectedItem().toString()+" WHERE numfactura ="+factura+";";
        if(ConsultasSistema.InsertaActualiza(conMySQL, actualiza) > 0)
            JOptionPane.showMessageDialog(null, "Factura Transferida Satisfactoriamente");
        NuevaTransferencia();
    }
    
    public void NuevaTransferencia(){
        jTextField61.setEnabled(true);
        jTextField61.setText("");
    }
    
    public void EdoCtaRadio(){
        if(jRadioButton1.isSelected()==true){
            jPanel13.setVisible(true);
            jTextField43.setVisible(false);
            jPanel23.setVisible(false);
            jLabel93.setVisible(false);
        }
        else{
            if(jRadioButton2.isSelected()==true){
                jPanel13.setVisible(false);
                jTextField43.setVisible(true);
                jPanel23.setVisible(false);
                jLabel93.setVisible(true);
            }
            else{
                if(jRadioButton7.isSelected()==true){
                    jPanel13.setVisible(false);
                    jTextField43.setVisible(false);
                    jPanel23.setVisible(true);
                    jLabel93.setVisible(false);
                }
            }
        }
    }
    
    public void ConciliaFacturas(){
        ResultSet rs=null, rs2=null;
        String consulta = "SELECT hosffa.status as stat,monto,Nombre,ltrim(num_fac) AS num_fac,f_fac\n" +
                          "FROM hosffa,hosffacxc\n" +
                          "INNER JOIN cxccli ON hosffacxc.cliente=ClaveCliente\n" +
                          "WHERE hosffacxc.factura=hosffa.num_fac and hosffa.status='F'\n" +
                          "AND hosffacxc.cliente!='' AND (f_fac between '"+
                          Sistema.ConversionFechasMySQLaTCA(Sistema.FechaPeriodo(jDateChooser15.getCalendar()))+"' AND '"+
                          Sistema.ConversionFechasMySQLaTCA(Sistema.FechaPeriodo(jDateChooser16.getCalendar()))+"')";
        
        String fact="",factmy="",ffac="";
        int i=0,j=0,statt=0,contador=0;
        
        jTextArea4.setText("");
        
        for(i=modeloT14.getRowCount()-1;i>=0;i--)
            modeloT14.removeRow(i);
        for(j=modeloT15.getRowCount()-1;j>=0;j--)
            modeloT15.removeRow(j);
        
        try{
            stat = con.createStatement();
            rs = ConsultasSistema.Estructura(con,consulta);
            while(rs.next()){
                statt=0;
                fact = rs.getString("num_fac");
                ffac = Sistema.ConversionDateTCA(rs.getString("f_fac"));
                Object []Fila = new Object [5];
                Fila[0]=fact;
                Fila[1]=rs.getString("stat");
                Fila[2]=ffac;
                Fila[3]=rs.getString("Nombre");
                Fila[4]=Sistema.ConvierteMoneda(rs.getString("monto"));
                consulta="SELECT numfactura FROM company WHERE numfactura ="+fact+";";
                    try{
                        stat = conMySQL.createStatement();
                        rs2 = ConsultasSistema.Estructura(conMySQL,consulta);
                        jTextArea4.setText(jTextArea4.getText()+"Analizando Factura "+fact);
                        if(rs2.next()){
                            factmy = rs2.getString("numfactura");
                            jTextArea4.setText(jTextArea4.getText()+" (Encontrada)");
                            statt = 1;
                            Object []Fila2 = new Object [2];
                            Fila2[0]=fact;
                            Fila2[1]=factmy;
                            modeloT14.addRow(Fila2);
                        }
                        jTextArea4.setText(jTextArea4.getText()+"\n");
                    }catch (Exception e){JOptionPane.showMessageDialog(null,"Error en tabla 2 col: "+e);}
                if(statt == 0){
                    modeloT15.addRow(Fila);
                }
                contador++;
            }
        }catch (Exception e){JOptionPane.showMessageDialog(null,"Error en tabla 5 col: "+e);}
        
        jTextField64.setText(Integer.toString(contador));
        jTextField65.setText(Integer.toString(modeloT14.getRowCount()));
    }
    
    public void ConciliaFacturas2(){
        ResultSet rs=null, rs2=null;
        String consulta = "SELECT hosffa.status as stat,monto,Nombre,ltrim(num_fac) AS num_fac,f_fac\n" +
                          "FROM hosffa,hosffacxc\n" +
                          "INNER JOIN cxccli ON hosffacxc.cliente=ClaveCliente\n" +
                          "WHERE hosffacxc.factura=hosffa.num_fac and hosffa.status='F'\n" +
                          "AND hosffacxc.cliente!='' AND (f_fac between '"+
                          Sistema.ConversionFechasMySQLaTCA(Sistema.FechaPeriodo(jDateChooser15.getCalendar()))+"' AND '"+
                          Sistema.ConversionFechasMySQLaTCA(Sistema.FechaPeriodo(jDateChooser16.getCalendar()))+"')";
        
        String fact="",factmy="",ffac="";
        int i=0,j=0,statt=0,contador=0;
        
        jTextArea4.setText("");
        
        /*for(i=modeloT14.getRowCount()-1;i>=0;i--)
            modeloT14.removeRow(i);
        for(j=modeloT15.getRowCount()-1;j>=0;j--)
            modeloT15.removeRow(j);*/
        
        try{
            stat = con.createStatement();
            rs = ConsultasSistema.Estructura(con,consulta);
            if(rs.next()){
                statt=0;
                fact = rs.getString("num_fac");
                ffac = Sistema.ConversionFechasTCA(rs.getString("f_fac"),0);
                Object []Fila = new Object [2];
                Fila[0]=fact;
                Fila[1]=rs.getString("stat");
                /*Fila[2]=ffac;
                Fila[3]=rs.getString("Nombre");
                Fila[4]=Sistema.ConvierteMoneda(rs.getString("monto"));*/
                
                modeloT14.addRow(Fila);
                
                contador++;
            }
        }catch (Exception e){JOptionPane.showMessageDialog(null,"Error en tabla 5 col: "+e);}
        
        //jTextField64.setText(Integer.toString(contador));
        //jTextField65.setText(Integer.toString(modeloT14.getRowCount()));
    }
    
    public void CheckCombos(){
        if(jComboBox3.getSelectedIndex()==5){
            jDateChooser6.setEnabled(false);
            jTextField42.setEnabled(false);
            jDateChooser8.setEnabled(true);
            jSpinner2.setValue(30);
        }
        else{
            if(jComboBox3.getSelectedIndex()==9){
                jDateChooser6.setEnabled(true);
                jTextField42.setEnabled(true);
                jDateChooser8.setEnabled(false);
                jSpinner2.setEnabled(false);
            }
            else{
                jDateChooser6.setEnabled(false);
                jTextField42.setEnabled(false);
                jDateChooser8.setEnabled(false);
                jSpinner2.setEnabled(false);
            }
        }
    }
    
    public void AddCompania(){
        DefaultTableModel modelo = (DefaultTableModel)jTable6.getModel();
        
        if(jTable6.getSelectedRow() < 0)
            JOptionPane.showMessageDialog(null, "Seleccione una Fila","Error",JOptionPane.ERROR_MESSAGE);

        else{
            nocompania = Integer.toString(Integer.parseInt(modelo.getValueAt(jTable6.getSelectedRow(), 0).toString()));
            aseguradora = modelo.getValueAt(jTable6.getSelectedRow(),1).toString();
            mailaseg = modelo.getValueAt(jTable6.getSelectedRow(),2).toString();
            numcia = Integer.toString(Integer.parseInt(modelo.getValueAt(jTable6.getSelectedRow(),0).toString()));
            System.out.println(numcia);
            selecaseg.dispose();
        }
    }
    
    public void Concentrado(){
        ResultSet rs;
        DefaultTableModel modelo = (DefaultTableModel)jTable17.getModel();
        TableRowSorter<TableModel> elQueOrdena = new TableRowSorter<TableModel>(modelo);
        jTable17.setRowSorter(elQueOrdena);
        
        String consulta = "select compania,sum(monto_total) as acumulado,(select sum(abono) from abonos where compannia=compania) as abonado " +
                          "from company where fecha_recepcion between '"+Sistema.FechaPeriodo(jDateChooser17.getCalendar())+"' and '"+
                          Sistema.FechaPeriodo(jDateChooser18.getCalendar())+"' group by compania order by compania asc";
        String tot="",paga="";
        
        for(int i=modelo.getRowCount()-1;i>=0;i--)
            modelo.removeRow(i);
        
        try{
            stat = conMySQL.createStatement();
            rs = ConsultasSistema.Estructura(conMySQL,consulta);
            while(rs.next()){
                tot = rs.getString("acumulado");
                paga = rs.getString("abonado");
                
                if(tot == null)
                    tot = "0";
                if(paga == null)
                    paga = "0";
                
                Object []Fila = new Object [4];
                Fila[0]=rs.getString("compania");
                Fila[1]=Sistema.ConvierteMoneda(tot);
                Fila[2]=Sistema.ConvierteMoneda(paga);
                Fila[3]=Sistema.ConvierteMoneda(Float.toString(Float.parseFloat(tot)-Float.parseFloat(paga)));
                modelo.addRow(Fila);
            }
        }catch (Exception e){JOptionPane.showMessageDialog(null,e);}
    }
    
    public void AntiguedadSaldos(){
        ResultSet rs;
        DefaultTableModel modelo = (DefaultTableModel)jTable18.getModel();
        TableRowSorter<TableModel> elQueOrdena = new TableRowSorter<TableModel>(modelo);
        jTable18.setRowSorter(elQueOrdena);
        String FInicio = Sistema.FechaPeriodo(jDateChooser20.getCalendar());
        String FFin = Sistema.FechaPeriodo(jDateChooser21.getCalendar());
        String consulta = "SELECT *, \n" +
                            "IF(\n" +
                            "(Alta-30) > 30,\n" +
                            "(30Dias+60Dias+120Dias+365Dias+720Dias+mas720Dias),0\n" +
                            ")as SaldoVencido,\n" +
                            "IF(\n" +
                            "(Alta-30) <= 30,\n" +
                            "(30Dias+60Dias+120Dias+365Dias+720Dias+mas720Dias),0\n" +
                            ")as SaldoPorVencer\n" +
                            " FROM (\n" +
                            "SELECT *, (Alta-30) as Vencido,\n" +
                            "IF(\n" +
                            "  (Alta-30) < 30, monto_total,0\n" +
                            ")as 30Dias,\n" +
                            "IF(\n" +
                            "  (((Alta-30) > 30) && (Alta-30) < 60), monto_total,0\n" +
                            ")as 60Dias,\n" +
                            "IF(\n" +
                            "  (((Alta-30) > 60) && (Alta-30) < 120), monto_total,0\n" +
                            ")as 120Dias,\n" +
                            "IF(\n" +
                            "  (((Alta-30) > 120) && (Alta-30) < 365), monto_total,0\n" +
                            ")as 365Dias,\n" +
                            "IF(\n" +
                            "  (((Alta-30) > 365) && (Alta-30) < 720), monto_total,0\n" +
                            ")as 720Dias,\n" +
                            "IF(\n" +
                            "  ((Alta-30) > 720), monto_total,0\n" +
                            ")as mas720Dias\n" +
                            "FROM (SELECT compania,numfactura, DATE(fecha_status) as fecha_status,monto_total,\n" +
                            "		  IF(\n" +
                            "    (EXTRACT(YEAR FROM curdate())-EXTRACT(year from (fecha_status))) = 0,\n" +
                            "     DAYOFYEAR(curdate())-DAYOFYEAR(fecha_status),\n" +
                            "\n" +
                            " IF(\n" +
                            "    (EXTRACT(YEAR FROM  curdate())-EXTRACT(YEAR FROM  (fecha_status))) < 0,\n" +
                            "     DAYOFYEAR(\n" +
                            "        CONCAT(\"\",EXTRACT(YEAR FROM(fecha_status)),\"-12-31\"))\n" +
                            "               -DAYOFYEAR(fecha_status)\n" +
                            "               +DAYOFYEAR(curdate()),\n" +
                            " 'Error'\n" +
                            " )\n" +
                            ") as Alta\n" +
                            "FROM company\n" +
                            "INNER JOIN bitacora ON factura = numfactura AND fecha_status=stamp_bitacora AND status_fact=status_factura\n" +
                            "WHERE status_factura = 'Entregada'\n" +
                            "AND "+jComboBox17.getSelectedItem()+" BETWEEN '"+FInicio+"' AND '"+FFin+"'\n" +
                            ") as SubQuery) as SumaTotal\n" +
                            "ORDER BY compania, cast(Alta AS UNSIGNED),numfactura ASC";
        System.out.println(consulta);
        
        for(int i=modelo.getRowCount()-1;i>=0;i--)
            modelo.removeRow(i);
        
        try{
            stat = conMySQL.createStatement();
            rs = ConsultasSistema.Estructura(conMySQL,consulta);
            while(rs.next()){
                Object []Fila = new Object [14];
                Fila[0]=rs.getString("compania");
                Fila[1]=Integer.parseInt(rs.getString("numfactura"));
                Fila[2]=rs.getString("fecha_status");
                Fila[3]=Double.parseDouble(rs.getString("monto_total"));
                Fila[4]=Integer.parseInt(rs.getString("Alta"));
                Fila[5]=rs.getString("Vencido");
                Fila[6]=Sistema.ConvierteMoneda(rs.getString("30Dias"));
                Fila[7]=Sistema.ConvierteMoneda(rs.getString("60Dias"));
                Fila[8]=Sistema.ConvierteMoneda(rs.getString("120Dias"));
                Fila[9]=Sistema.ConvierteMoneda(rs.getString("365Dias"));
                Fila[10]=Sistema.ConvierteMoneda(rs.getString("720Dias"));
                Fila[11]=Sistema.ConvierteMoneda(rs.getString("mas720Dias"));
                Fila[12]=Sistema.ConvierteMoneda(rs.getString("SaldoVencido"));
                Fila[13]=Sistema.ConvierteMoneda(rs.getString("SaldoPorVencer"));
                modelo.addRow(Fila);
            }
        }catch (Exception e){JOptionPane.showMessageDialog(null,e);}
    }
    
    public void getRutaAttatchment(int tipo){
        JFileChooser filechooser = new JFileChooser();
        int result = 0;
        File file = null;
        String ruta="";
        
        if(tipo == 0){
            result = filechooser.showOpenDialog(null);
            if(result == JFileChooser.APPROVE_OPTION){
                file = filechooser.getSelectedFile();
                ruta = String.valueOf(file);
            }
        }
        else{
            result = filechooser.showSaveDialog(null);
            if(result == JFileChooser.APPROVE_OPTION){
                file = filechooser.getSelectedFile();
                ruta = String.valueOf(file)+".pdf";
                ConsEdoCuenta(ruta);
            }
        }
        jTextField93.setText(ruta);
        jTextField97.setText(file.getName());
    }
    
    public String stripExtension(String texto){
        if (texto == null)
            return null;
        int pos = texto.lastIndexOf(".");
        if (pos == -1) 
            return texto;
        
        return texto.substring(0, pos);
    }
    
    public void ConsEdoCuenta(String PDF){
        String FechaInicio="",FechaFin="",Asegu="",Statuss="";
        FechaInicio=Sistema.FechaPeriodo(jDateChooser4.getCalendar());
        FechaFin=Sistema.FechaPeriodo(jDateChooser5.getCalendar());
        Asegu=nocompania;
        Statuss=jComboBox9.getSelectedItem().toString();
        ConsultaEdoCta(FechaInicio,FechaFin,Asegu,Statuss,PDF);
        jTextField16.setEnabled(true);
        jTextField16.setText("");
    }
    
    public void ConsUJATadicionales(String folio){
        ResultSet rs = null;
        int folioo = 8-folio.length();
        String espacios = "",name="",dir="",tel="",respnm="",respdir="",resptel="";
        
        for(int i=0;i<folioo;i++)
            espacios += " ";
        espacios += folio;
        
        String consTCABDHSU = "select RTRIM(p_nom)+' '+RTRIM(p_apellp)+' '+RTRIM(p_apellm)+' ' as Nombre," +
                              "RTRIM(PacienteDireccion)+' '+RTRIM(PacienteColonia)+'. '+RTRIM(PacienteCiudad)+" +
                              "', '+RTRIM(PacienteDelegacion)+' '+RTRIM(PacienteEstado)+' '+RTRIM(PacientePais)+' ' as Dir," +
                              "RTRIM(PacienteTelefono) as tel, RTRIM(ResponsableNombre) as Nm,RTRIM(ResponsableDireccion)"+
                              "+' '+RTRIM(ResponsableColonia)+' '+RTRIM(ResponsableCiudad)+' '+RTRIM(ResponsableDelegacion)"+
                              "+' '+RTRIM(ResponsableEstado)+' '+RTRIM(ResponsablePais) as DirResp, RTRIM(ResponsableTelefono)"+
                              " as RespTel from hospac where p_fol_cto = '"+espacios+"'";
        String cons = "SELECT * FROM assist_responsableujat WHERE folio = '"+folio+"'";
        
        try{
            stat = con.createStatement();
            rs = ConsultasSistema.Estructura(con,consTCABDHSU);
            
            if(rs.next()){
                name = rs.getString("Nombre");
                dir = rs.getString("Dir");
                tel = rs.getString("tel");
                respnm = rs.getString("Nm");
                respdir = rs.getString("DirResp");
                resptel = rs.getString("RespTel");
                jTextField103.setEnabled(false);
                try{
                    stat = con.createStatement();
                    rs = ConsultasSistema.Estructura(conXML,cons);
                    if(rs.next()){
                        jButton47.setEnabled(false);
                        jButton48.setEnabled(true);
                        jButton49.setEnabled(true);
                        jTextField104.setText(rs.getString("pac_nombre"));
                        jComboBox18.setSelectedItem(rs.getString("pac_parent_con_resp"));
                        jComboBox21.setSelectedItem(rs.getString("pac_categoria"));
                        jTextField105.setText(rs.getString("pac_adscrip"));
                        jTextField107.setText(rs.getString("pac_ficha"));
                        jTextField108.setText(rs.getString("pac_domicilio"));
                        jTextField109.setText(rs.getString("pac_tel"));
                        jTextField110.setText(rs.getString("resp_nombre"));
                        jComboBox19.setSelectedItem(rs.getString("resp_parent_con_pac"));
                        jComboBox20.setSelectedItem(rs.getString("resp_categoria"));
                        jTextField111.setText(rs.getString("resp_adscrip"));
                        jTextField113.setText(rs.getString("resp_ficha"));
                        jTextField114.setText(rs.getString("resp_domicilio"));
                        jTextField115.setText(rs.getString("resp_tel"));
                        jTextField116.setText(rs.getString("resp_docto_ident"));
                    }
                    else{
                        jButton47.setEnabled(true);
                        jButton48.setEnabled(false);
                        jButton49.setEnabled(false);
                        jTextField104.setText(name);
                        jTextField108.setText(dir);
                        jTextField109.setText(tel);
                        jTextField110.setText(respnm);
                        jTextField114.setText(respdir);
                        jTextField115.setText(resptel);
                    }
                }catch (Exception e){JOptionPane.showMessageDialog(null,e);}
            }
        }catch (Exception e){JOptionPane.showMessageDialog(null,e);}
    }
    
    public void InsertaUJATadicionales(int tipo){
        String folio = jTextField103.getText();
        String name_pac = jTextField104.getText();
        String pac_parent_resp = jComboBox18.getSelectedItem().toString();
        String pac_cat = jComboBox21.getSelectedItem().toString();
        String pac_ads = jTextField105.getText().toUpperCase();
        String pac_fic = jTextField107.getText();
        String pac_dom = jTextField108.getText().toUpperCase();
        String pac_tel = jTextField109.getText();
        String resp_name = jTextField110.getText();
        String resp_parent = jComboBox19.getSelectedItem().toString();
        String resp_cat = jComboBox20.getSelectedItem().toString();
        String resp_ads = jTextField111.getText().toUpperCase();
        String resp_ficha = jTextField113.getText();
        String resp_dom = jTextField114.getText().toUpperCase();
        String resp_tel = jTextField115.getText();
        String resp_docto = jTextField116.getText().toUpperCase();
        int stat=0;
        
        String insert = "INSERT INTO assist_responsableujat(folio,pac_nombre,pac_parent_con_resp,pac_categoria,pac_adscrip,pac_ficha,"+
                        "pac_domicilio,pac_tel,resp_nombre,resp_parent_con_pac,resp_categoria,resp_adscrip,resp_ficha,resp_domicilio,"+
                        "resp_tel,resp_docto_ident) VALUES('"+folio+"','"+name_pac+"','"+pac_parent_resp+"','"+pac_cat+"','"+pac_ads+
                        "','"+pac_fic+"','"+pac_dom+"','"+pac_tel+"','"+resp_name+"','"+resp_parent+"','"+resp_cat+"','"+resp_ads+
                        "','"+resp_ficha+"','"+resp_dom+"','"+resp_tel+"','"+resp_docto+"');";
        
        String update = "UPDATE assist_responsableujat SET pac_parent_con_resp='"+pac_parent_resp+"',pac_categoria='"+pac_cat+"',"+
                        "pac_adscrip='"+pac_ads+"',pac_ficha='"+pac_fic+"',pac_domicilio='"+pac_dom+"',pac_tel='"+pac_tel+"',resp_nombre='"+
                        resp_name+"',resp_parent_con_pac='"+resp_parent+"',resp_categoria='"+resp_cat+"',resp_adscrip='"+resp_ads+"',"+
                        "resp_ficha='"+resp_ficha+"',resp_domicilio='"+resp_dom+"',resp_tel='"+resp_tel+"',resp_docto_ident='"+
                        resp_docto+"' WHERE folio='"+folio+"' AND pac_nombre='"+name_pac+"';";
        
        if(tipo == 0) stat = ConsultasSistema.InsertaActualiza(conXML, insert);
        else stat = ConsultasSistema.InsertaActualiza(conXML, update);
        
        if(stat > 0){
            if(tipo == 0)
                RepUJATadicionales(jTextField103.getText());
            LimpiaUJAT();
        }
    }
    
    public void LimpiaUJAT(){
        jTextField103.setEnabled(true);
        jButton47.setEnabled(false);
        jButton48.setEnabled(false);
        jButton49.setEnabled(false);
        jTextField103.setText("");
        jTextField104.setText("");
        jComboBox18.setSelectedIndex(0);
        jComboBox21.setSelectedIndex(0);
        jTextField105.setText("");
        jTextField107.setText("");
        jTextField108.setText("");
        jTextField109.setText("");
        jTextField110.setText("");
        jComboBox19.setSelectedIndex(0);
        jComboBox20.setSelectedIndex(0);
        jTextField111.setText("");
        jTextField113.setText("");
        jTextField114.setText("");
        jTextField115.setText("");
        jTextField116.setText("");
    }
    
    public void CheckBoxMismo(){
        if(jCheckBox4.isSelected() == true){
            jTextField110.setText(jTextField104.getText());
            jComboBox19.setSelectedIndex(jComboBox18.getSelectedIndex());
            jComboBox20.setSelectedIndex(jComboBox21.getSelectedIndex());
            jTextField111.setText(jTextField105.getText());
            jTextField113.setText(jTextField107.getText());
            jTextField114.setText(jTextField108.getText());
            jTextField115.setText(jTextField109.getText());
        }
        else{
            jTextField110.setText("");
            jComboBox19.setSelectedIndex(0);
            jComboBox20.setSelectedIndex(0);
            jTextField111.setText("");
            jTextField113.setText("");
            jTextField114.setText("");
            jTextField115.setText("");
        }
    }
    
    public void Porcentual(String inicio,String fin){
        ResultSet rs;
        DefaultTableModel modelo = (DefaultTableModel)jTable20.getModel();
        float porciento=0;
        String cons = "SELECT compania,\n" +
                      "SUM(IF(status_factura='Entregada',1,0))AS Entregado,\n" +
                      "SUM(IF(status_factura='Liquidada',1,0))AS Liquidado\n" +
                      "FROM company\n" +
                      "WHERE fecha_recepcion BETWEEN '"+inicio+"' AND '"+fin+"'\n" +
                      "GROUP BY numcompania\n" +
                      "ORDER BY compania ASC";
        
        for(int i=modelo.getRowCount()-1;i>=0;i--)
            modelo.removeRow(i);
        
        try{
            stat = con.createStatement();
            rs = ConsultasSistema.Estructura(conMySQL,cons);
            while(rs.next()){
                Object []Fila = new Object [4];
                    Fila[0]=rs.getString("compania");
                    Fila[1]=rs.getString("Entregado");
                    Fila[2]=rs.getString("Liquidado");
                modelo.addRow(Fila);
            }
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
        
        for(int i=0;i<modelo.getRowCount();i++){
            porciento = (Float.parseFloat(modelo.getValueAt(i, 2).toString())/Float.parseFloat(modelo.getValueAt(i, 1).toString()))*100;
            modelo.setValueAt(porciento, i, 3);
        }
    }
    
    public void CatArtTCA(String desc){
        ResultSet rs;
        String cons = "SELECT inviar.art,des1,ROUND(precio_vta0,2) AS p,imp1\n" +
                      "FROM inviar\n" +
                      "INNER JOIN invart ON inviar.art=invart.art\n" +
                      "WHERE des1 LIKE '%"+desc+"%'\n" +
                      "AND inviar.status = '00'\n" +
                      "ORDER BY des1 ASC";
        
        for(int i=modeloT22.getRowCount()-1;i>=0;i--)
            modeloT22.removeRow(i);
        
        try{
            stat = con.createStatement();
            rs = ConsultasSistema.Estructura(con,cons);
            while(rs.next()){
                Object []Fila = new Object [4];
                Fila[0]=rs.getString("art");
                Fila[1]=rs.getString("des1");
                Fila[2]=rs.getString("p");
                Fila[3]=rs.getString("imp1");
                modeloT22.addRow(Fila);
            }
        }catch (Exception e){ JOptionPane.showMessageDialog(null,e); }
    }
    
    public void CalcTotal(){
        float total=0;
        
        for(int i=0;i<modeloT21.getRowCount();i++){
            total += Float.parseFloat(modeloT21.getValueAt(i,1).toString())*
                     Float.parseFloat(modeloT21.getValueAt(i,3).toString());
        }
        jTextField123.setText(Sistema.ConvierteMoneda(Float.toString(total)));
    }
    
    public String GeneraRandom(int ciclos){
        String cve_generada="";
        Random randomGenerator = new Random();
        int randomInt = 0;
        
        for(int i=0;i<ciclos;i++){
            randomInt = randomGenerator.nextInt(10);
            cve_generada += Integer.toString(randomInt);
        }
        return cve_generada;
    }
    
    public void InsertaCot(String cotiza, String vigencia){
        numcotizacion = cotiza;
        int stt=0;
        String cliente = jTextField122.getText(), ins="";
        
        if(cliente.equals(""))
            JOptionPane.showMessageDialog(null, "Ingrese nombre de cliente","Error",JOptionPane.ERROR_MESSAGE);
        else{
            ins = "INSERT INTO cotizaciones (cotizacion,fecha,cliente,usuario,vigencia)"+
                  "VALUES('"+cotiza+"',SYSDATETIME(),'"+cliente+"','"+
                  Usuario+"','"+vigencia+"')";
            if(ConsultasSistema.InsertaActualiza(conXML,ins)>0){
                for(int i=0;i<modeloT21.getRowCount();i++){
                    ins = "INSERT INTO cotizacionescont (cotiz,clave,cant,precio,impuesto) VALUES('"+cotiza+"','"+
                          modeloT21.getValueAt(i,0)+"',"+modeloT21.getValueAt(i,1)+","+modeloT21.getValueAt(i,3)+",'"+
                          modeloT21.getValueAt(i,4)+"')";
                    stt += ConsultasSistema.InsertaActualiza(conXML,ins);
                }
                if(stt == modeloT21.getRowCount()) cent_correo();
            }
        }
    }
    
    public void sendMail(String correodest){
        final String username = mail;
        final String password = pwdmail; 
        String subject = "Hospital del Sureste A.C. [Cotizacion]";
        Properties props = new Properties();
        
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.host", smtphost);
        props.put("mail.smtp.port", port);

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(correodest));
            message.setSubject(subject);
            message.setText("Enviamos su Cotización");

            BodyPart adjunto = new MimeBodyPart();
            //adjunto.setDataHandler(new DataHandler(new FileDataSource(carpetaPDF+numcotizacion+".pdf")));
            adjunto.setFileName("Cotizacion "+numcotizacion+".pdf");
            MimeMultipart multiParte = new MimeMultipart();
            //multiParte.addBodyPart("Hola");
            multiParte.addBodyPart(adjunto);
            message.setContent(multiParte);
            
            Transport.send(message);
            JOptionPane.showMessageDialog(null,"correo enviado");
            jTextField124.setText("");
            LimpiarCotizacion();
            sendcorreo.dispose();

        } catch (MessagingException e) { JOptionPane.showMessageDialog(null,e); }
    }
    
    public void LimpiarCotizacion(){
        for(int i=modeloT21.getRowCount()-1;i>=0;i--)
            modeloT21.removeRow(i);
        jTextField121.setText("");
        jTextField122.setText("");
        jTextField123.setText("");
        jTextField121.grabFocus();
    }
    
    public void ForFacturas(){
        DefaultTableModel modelo = (DefaultTableModel)jTable5.getModel();
        String carpeta="",factura="";
        String ruta = getRutaFolder();
        
        for(int i=0;i<modelo.getRowCount();i++){
            carpeta = modelo.getValueAt(i,4).toString();
            factura = modelo.getValueAt(i,0).toString();
            
            CopiaFacturas(ruta,carpeta,factura);
        }
        jTextField125.setText("");
        RutaFiles.dispose();
        AbrirDirectorio(ruta);
    }
    
    public String getRutaFolder(){
        JFileChooser filechooser = new JFileChooser();
        int result = 0;
        File file = null;
        String ruta="";
        
        result = filechooser.showSaveDialog(null);
        if(result == JFileChooser.APPROVE_OPTION){
            file = filechooser.getCurrentDirectory();
            ruta = String.valueOf(file);
        }
        return ruta+"\\";
    }
    
    public void CopiaFacturas(String ruta,String carpeta,String factura){
        File origenPDF = new File("\\\\192.168.3.3\\Assist\\BOCFD\\FACTURAS\\"+carpeta+"\\PDF\\HSU-"+factura+".pdf");
        File destinoPDF = new File(ruta+factura+".pdf");
        File origenXML = new File("\\\\192.168.3.3\\Assist\\BOCFD\\FACTURAS\\"+carpeta+"\\HSU-"+factura+".xml");
        File destinoXML = new File(ruta+factura+".xml");

        try {
                InputStream in = new FileInputStream(origenPDF);
                OutputStream out = new FileOutputStream(destinoPDF);
                InputStream inX = new FileInputStream(origenXML);
                OutputStream outX = new FileOutputStream(destinoXML);

                byte[] buf = new byte[1024];
                int len;

                while ((len = in.read(buf)) > 0) {out.write(buf, 0, len);}
                while ((len = inX.read(buf)) > 0) {outX.write(buf, 0, len);}

                in.close(); out.close();
                inX.close(); outX.close();
        } catch (IOException ioe){ioe.printStackTrace();}
    }
    
    public void AbrirDirectorio(String directorio){
        try {Process p = new ProcessBuilder("explorer.exe",directorio).start();}
        catch (IOException ex) {Logger.getLogger(Facturas.class.getName()).log(Level.SEVERE, null, ex);}
    }
    
    /*public void getReporteStatTabla(String Inicio,String Fin){
        ResultSet rs = null;
        String cons = "CALL Orden_status('"+Inicio+"','"+Fin+"');";
        
        for(int i=modeloT23.getRowCount()-1;i>=0;i--)
            modeloT23.removeRow(i);
        
        try{
            stat = conMySQL.createStatement();
            rs = ConsultasSistema.Estructura(conMySQL,cons);
            while(rs.next()){
                Object []Fila = new Object [7];
                    Fila[0]=rs.getString("numcompania");
                    Fila[1]=rs.getString("compania");
                    Fila[2]=rs.getString("Liquidado");
                    Fila[3]=rs.getString("Cancelado");
                    Fila[4]=rs.getString("Entregado");
                    Fila[5]=rs.getString("PorEntregar");
                    Fila[6]=rs.getString("EnFacturacion");
                modeloT23.addRow(Fila);
            }
        }
        catch (Exception e){ JOptionPane.showMessageDialog(null,e); }
    }*/
    
    public void getReporteStatTabla(String Inicio,String Fin){
        ResultSet rs = null;
        String cons = "SELECT DISTINCT numcompania,compania\n" +
                      "FROM company\n" +
                      "WHERE fact_assist BETWEEN '"+Inicio+"' AND '"+Fin+"'\n" +
                      "ORDER BY numcompania ASC";
        String valor="";
        
        for(int i=modeloT23.getRowCount()-1;i>=0;i--)
            modeloT23.removeRow(i);
        
        try{
            stat = conMySQL.createStatement();
            rs = ConsultasSistema.Estructura(conMySQL,cons);
            while(rs.next()){
                Object []Fila = new Object [7];
                    Fila[0]=rs.getString("numcompania");
                    Fila[1]=rs.getString("compania");
                modeloT23.addRow(Fila);
            }
            for(int j=0;j<modeloT23.getRowCount();j++){
                try{
                    cons="SELECT IFNULL(SUM(monto_total),0) AS Liquidado\n" +
                         "FROM company\n" +
                         "WHERE numcompania = "+modeloT23.getValueAt(j,0)+"\n" +
                         "AND status_factura='Liquidada'\n" +
                         "AND fact_assist BETWEEN '"+Inicio+"' AND '"+Fin+"'";
                    stat = conMySQL.createStatement();
                    rs = ConsultasSistema.Estructura(conMySQL,cons);
                    if(rs.next()) valor = rs.getString("Liquidado");
                    else valor = "0";
                    modeloT23.setValueAt(valor,j,2);
                }catch (Exception e){ JOptionPane.showMessageDialog(null,e); }
                try{
                    cons="SELECT IFNULL(SUM(monto_total),0) AS Cancelado\n" +
                         "FROM company\n" +
                         "WHERE numcompania = "+modeloT23.getValueAt(j,0)+"\n" +
                         "AND status_factura='Cancelada'\n" +
                         "AND fact_assist BETWEEN '"+Inicio+"' AND '"+Fin+"'";
                    stat = conMySQL.createStatement();
                    rs = ConsultasSistema.Estructura(conMySQL,cons);
                    if(rs.next()) valor = rs.getString("Cancelado");
                    else valor = "0";
                    modeloT23.setValueAt(valor,j,3);
                }catch (Exception e){ JOptionPane.showMessageDialog(null,e); }
                try{
                    cons="SELECT IFNULL(SUM(monto_total),0) AS Entregado\n" +
                         "FROM company\n" +
                         "WHERE numcompania = "+modeloT23.getValueAt(j,0)+"\n" +
                         "AND status_factura='Entregada'\n" +
                         "AND fact_assist BETWEEN '"+Inicio+"' AND '"+Fin+"'";
                    stat = conMySQL.createStatement();
                    rs = ConsultasSistema.Estructura(conMySQL,cons);
                    if(rs.next()) valor = rs.getString("Entregado");
                    else valor = "0";
                    modeloT23.setValueAt(valor,j,4);
                }catch (Exception e){ JOptionPane.showMessageDialog(null,e); }
                try{
                    cons="SELECT IFNULL(SUM(monto_total),0) AS PorEntregar\n" +
                         "FROM company\n" +
                         "WHERE numcompania = "+modeloT23.getValueAt(j,0)+"\n" +
                         "AND status_factura='Por Entregar'\n" +
                         "AND fact_assist BETWEEN '"+Inicio+"' AND '"+Fin+"'";
                    stat = conMySQL.createStatement();
                    rs = ConsultasSistema.Estructura(conMySQL,cons);
                    if(rs.next()) valor = rs.getString("PorEntregar");
                    else valor = "0";
                    modeloT23.setValueAt(valor,j,5);
                }catch (Exception e){ JOptionPane.showMessageDialog(null,e); }
                try{
                    cons="SELECT IFNULL(SUM(monto_total),0) AS EnFacturacion\n" +
                         "FROM company\n" +
                         "WHERE numcompania = "+modeloT23.getValueAt(j,0)+"\n" +
                         "AND status_factura='En Facturacion'\n" +
                         "AND fact_assist BETWEEN '"+Inicio+"' AND '"+Fin+"'";
                    stat = conMySQL.createStatement();
                    rs = ConsultasSistema.Estructura(conMySQL,cons);
                    if(rs.next()) valor = rs.getString("EnFacturacion");
                    else valor = "0";
                    modeloT23.setValueAt(valor,j,6);
                }catch (Exception e){ JOptionPane.showMessageDialog(null,e); }
            }
        }catch (Exception e){ JOptionPane.showMessageDialog(null,e); }
    }
    
    public void ConsFacProvisiona(String fact){
        ResultSet rs=null;
        String espacios = " ",factura="";
        for(int i=0;i<(12-fact.length());i++)
            factura += espacios;
        factura += fact;
        String cons = "SELECT LTRIM(a.num_fac) AS num_fac,c.ClaveCliente,a.status,"+
                       "      RTRIM(c.Nombre) AS nombre,a.total\n" +
                      "FROM hosffa a\n" +
                      "INNER JOIN hospac b ON b.p_fol_cto=a.folio\n" +
                      "INNER JOIN cxccli c ON c.ClaveCliente=b.p_cod_cia\n" +
                      "WHERE a.num_fac = '"+factura+"'";
        
        String cons_company = "SELECT numfactura\n" +
                              "FROM company\n" +
                              "WHERE numfactura = '"+fact+"'";
        
        String cons_provision = "SELECT factura\n" +
                                "FROM provisiones\n" +
                                "WHERE factura = '"+fact+"'";
        
        try{
            stat = conMySQL.createStatement();
            rs = ConsultasSistema.Estructura(conMySQL,cons_company);
            if(rs.next())
                JOptionPane.showMessageDialog(null, "factura ya existe","Error",JOptionPane.ERROR_MESSAGE);
            else{
                stat = conMySQL.createStatement();
                rs = ConsultasSistema.Estructura(conMySQL,cons_provision);
                if(rs.next())
                    JOptionPane.showMessageDialog(null, "factura ya provisionada","Error",JOptionPane.ERROR_MESSAGE);
                else{
                    stat = con.createStatement();
                    rs = ConsultasSistema.Estructura(con,cons);
                    if(rs.next()){
                        Object []Fila = new Object [5];
                            Fila[0]=rs.getString("num_fac");
                            Fila[1]=rs.getString("ClaveCliente");
                            Fila[2]=rs.getString("nombre");
                            Fila[3]=rs.getString("total");
                            Fila[4]=rs.getString("status");
                        modeloT24.addRow(Fila);
                        jTextField127.setText("");
                        jTextField127.grabFocus();
                    }
                    else
                        JOptionPane.showMessageDialog(null, "factura no existe","Error",JOptionPane.ERROR_MESSAGE);
                }
            }
        }catch (Exception e){ JOptionPane.showMessageDialog(null,e); }
    }
    
    public void IngresaProvision(){
        String folio = "SELECT max(folio) as folio FROM provisiones",insert="",fecha="";
        int a=0;
        
        if(jTextField129.getText().equals("HOY")) fecha="curdate()";
        else fecha="'"+jTextField129.getText()+"'";
        
        try{
            stat = conMySQL.createStatement();
            rs = ConsultasSistema.Estructura(conMySQL,folio);
            if(rs.next()) folio = Integer.toString(Integer.parseInt(rs.getString("folio"))+1);
            jTextField128.setText(folio);
        }catch (Exception e){ folio = "1"; }
        
        for(int i=0;i<modeloT24.getRowCount();i++){
            insert = "INSERT INTO provisiones (factura,status,compania,monto,fecha_recep,folio,nombre) "+
                     "VALUES('"+modeloT24.getValueAt(i,0)+"','"+modeloT24.getValueAt(i,4)+"','"+
                     modeloT24.getValueAt(i,1)+"','"+modeloT24.getValueAt(i,3)+"',"+fecha+",'"+folio+
                     "','"+modeloT24.getValueAt(i,2)+"')";
            ConsultasSistema.InsertaActualiza(conMySQL,insert);
            a++;
        }
        
        if(a == modeloT24.getRowCount()){
            JOptionPane.showMessageDialog(null, "facturas provisionadas de forma correcta");
            LimpiaProvision();
        }
    }
    
    public void LimpiaProvision(){
        for(int i=modeloT24.getRowCount()-1;i>=0;i--)
            modeloT24.removeRow(i);
        
        jTextField128.setText("");
        jTextField129.setText("HOY");
    }
    
    public String[] ConsProvision(String factura){
        String cons_provision = "SELECT factura,fecha_recep\n" +
                                "FROM provisiones\n" +
                                "WHERE factura = '"+factura+"'";
        String []datos = new String[2];
        
        try{
            stat = conMySQL.createStatement();
            rs = ConsultasSistema.Estructura(conMySQL,cons_provision);
            if(rs.next()){ datos[0] = "1"; datos[1] = rs.getString("fecha_recep");}
        }catch (Exception e){ JOptionPane.showMessageDialog(null,e); }
        return datos;
    }
    
    public void EliminaProvision(String factura){
        String delete = "DELETE FROM provisiones WHERE factura='"+factura+"'";
        
        if(ConsultasSistema.InsertaActualiza(conMySQL,delete) > 0)
            JOptionPane.showMessageDialog(null, "transferencia correcta de factura");
    }
    
    public int checkFormatoValidez(String validez){
        int pasa = 0; // 0=licencia caducada, 1=licencia correcta
        String mes="", dia="", anio="";
        char Array[] = validez.toCharArray();
        
        dia += String.valueOf(Array[8]);
        dia += String.valueOf(Array[9]);
        
        mes += String.valueOf(Array[5]);
        mes += String.valueOf(Array[6]);
        
        anio += String.valueOf(Array[0]);
        anio += String.valueOf(Array[1]);
        anio += String.valueOf(Array[2]);
        anio += String.valueOf(Array[3]);
        
        if((Integer.parseInt(mes) >= 1)&&(Integer.parseInt(mes) <= 12)){
            if((Integer.parseInt(dia) >= 1)&&(Integer.parseInt(dia) <= 31)){
                if(Integer.parseInt(anio) >= 2020){
                    pasa = 1;
                }
            }
        }
        return pasa;
    }
    
    public void Licencia(int tipo){
        String Obtiene[] = new String[2];
        String cadena="",validez="";
        
        if(tipo == 0)
            cadena = jTextField130.getText() + jTextField131.getText();
        else{
            try{
                stat = conMySQL.createStatement();
                rs = ConsultasSistema.Estructura(conMySQL,"SELECT * FROM servlicencias WHERE status = 1");
                if(rs.next())
                    cadena=Sistema.DesencriptarMD5(rs.getString("clave"))+rs.getString("digito");
                else{
                    JOptionPane.showMessageDialog(null, "no se encontró licencia activa","error",JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                }
            }catch (Exception e){ JOptionPane.showMessageDialog(null,e); }
        }
        
        Obtiene = Sistema.VerificaLicencia(cadena.toUpperCase());
        if(Obtiene[1].equals("1"))
            JOptionPane.showMessageDialog(null, "formato incorrecto de licencia","error",JOptionPane.ERROR_MESSAGE);
        else{
            validez = Obtiene[0].toString();
            try {
                if(checkValidez(validez) == 0){
                    jLabel223.setText(validez);
                    jTextField1.setEnabled(true); jPasswordField1.setEnabled(true);
                    jButton61.setEnabled(true); cent_this();
                    if(tipo == 1){
                        if(checkFormatoValidez(validez) == 0){
                            JOptionPane.showMessageDialog(null, "licencia caducada ...","error",JOptionPane.ERROR_MESSAGE);
                            System.exit(0);
                        }
                        else{
                            JOptionPane.showMessageDialog(null,"Licencia Correcta, vigencia: "+validez);
                            UpdateLicencia(cadena);
                        }
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "licencia caducada","error",JOptionPane.ERROR_MESSAGE);
                    jTextField1.setEnabled(false); jPasswordField1.setEnabled(false);
                    jButton61.setEnabled(true); cent_key();
                }
            } catch (ParseException ex) { Logger.getLogger(Facturas.class.getName()).log(Level.SEVERE, null, ex); }
        }
    }
    
    public void UpdateLicencia(String cadena){
        char cad[] = new char[30];
        String fecha="",licencia="",digito="";
        cad = cadena.toCharArray();
        
        fecha = String.valueOf(""+cad[1]+cad[12]+cad[10]+cad[6]+cad[5]+cad[29]+cad[27]+cad[11]+cad[20]+cad[18]);
        digito = String.valueOf(""+cad[29]+cad[30]);
        
        for(int i=0;i<=28;i++)
            licencia += String.valueOf(cad[i]);
    }
    
    public int checkValidez(String fecha) throws ParseException{
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        Date factual = formato.parse(FechaServer());
        Date fobtiene = formato.parse(fecha);
        int resultado = 0;
        
        if(fobtiene.before(factual) == true) resultado = 1; // licencia caducada
        return resultado;
    }
    
    public String FechaServer(){
        String cons = "SELECT curdate() AS fecha;";
        
        try{
            stat = conMySQL.createStatement();
            rs = ConsultasSistema.Estructura(conMySQL,cons);
            
            if(rs.next()) cons = rs.getString("fecha");
        }catch (Exception e){ JOptionPane.showMessageDialog(null,e); }
        
        return cons;
    }
        
    //---------- IMPRESION ------------
    public void ImpContrarecibo(int Folio){
        try {
            Report.MostrarContrareciboNormal(Folio,conMySQL,jComboBox14.getSelectedItem().toString(), carpeta, logo, logoacc);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
            Logger.getLogger(Facturas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, ex);
            Logger.getLogger(Facturas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void ImpContrareciboSaldos(int Folio){
        try { Report.MostrarContrareciboSaldos(Folio,conMySQL, carpeta, logo, logoacc);
        } catch (SQLException ex) { Logger.getLogger(Facturas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) { Logger.getLogger(Facturas.class.getName()).log(Level.SEVERE, null, ex); }
    }
    
    public void ConsultaEdoCta(String FechaInicio,String FechaFin,String Asegu,String Statuss,String PDF){
        try { Report.MostrarEdoCuenta(conMySQL,FechaInicio,FechaFin,Asegu,Statuss,PDF, carpeta, logo, logoacc);
        } catch (SQLException ex) { 
            JOptionPane.showMessageDialog(null, ex);
            Logger.getLogger(Facturas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) { 
            JOptionPane.showMessageDialog(null, ex);
            Logger.getLogger(Facturas.class.getName()).log(Level.SEVERE, null, ex); }
    }
    
    public void ConsultaFactura(int NumFactura){
        try { Report.MostrarEdoFactura(conMySQL,NumFactura, carpeta, logo, logoacc);
        } catch (SQLException ex) { Logger.getLogger(Facturas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) { Logger.getLogger(Facturas.class.getName()).log(Level.SEVERE, null, ex); }
    }
    
    public void RepEstPeriodo(String Inicio, String Fin){
        try { Report.MostrarRepEstPeriodo(conMySQL, Inicio, Fin, carpeta, logo, logoacc);
        } catch (SQLException ex) { Logger.getLogger(Facturas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) { Logger.getLogger(Facturas.class.getName()).log(Level.SEVERE, null, ex); }
    }
    
    public void RepConcentrado(String Inicio, String Fin){
        try { Report.MostrarRepConcentrado(conMySQL, Inicio, Fin, carpeta, logo, logoacc);
        } catch (SQLException ex) { 
            JOptionPane.showMessageDialog(null, ex);
            Logger.getLogger(Facturas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) { 
            JOptionPane.showMessageDialog(null, ex);
            Logger.getLogger(Facturas.class.getName()).log(Level.SEVERE, null, ex); }
    }
    
    public void RepStatus(String Inicio, String Fin){
        try { Report.MostrarRepStatus(conMySQL, Inicio, Fin, carpeta, logo, logoacc);
        } catch (SQLException ex) { 
            JOptionPane.showMessageDialog(null, ex);
            Logger.getLogger(Facturas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) { 
            JOptionPane.showMessageDialog(null, ex);
            Logger.getLogger(Facturas.class.getName()).log(Level.SEVERE, null, ex); }
    }
    
    public void RepAntigSaldos(String Inicio, String Fin,int Tipo){
        try { Report.MostrarRepAntSald(conMySQL, Inicio, Fin, Tipo, carpeta, logo, logoacc);
        } catch (SQLException ex) { Logger.getLogger(Facturas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) { Logger.getLogger(Facturas.class.getName()).log(Level.SEVERE, null, ex); }
    }
    
    public void RepLiquidadas(String Inicio, String Fin){
        try { Report.MostrarRepLiq(conMySQL, Inicio, Fin, carpeta, logo, logoacc);
        } catch (SQLException ex) { Logger.getLogger(Facturas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) { Logger.getLogger(Facturas.class.getName()).log(Level.SEVERE, null, ex); }
    }
    
    public void RepUJATadicionales(String folio){
        try { Report.MostrarRepUJAT(con, folio, carpeta, logo, logoacc);
        } catch (SQLException ex) { Logger.getLogger(Facturas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) { Logger.getLogger(Facturas.class.getName()).log(Level.SEVERE, null, ex); }
    }
    
    public void Cotiza(int tipo){
        try { Report.Cotizacionn(con,numcotizacion,tipo, carpeta, carpetaPDFReportes);
        } catch (SQLException ex) { Logger.getLogger(Facturas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) { Logger.getLogger(Facturas.class.getName()).log(Level.SEVERE, null, ex); }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        acercade = new javax.swing.JDialog();
        jScrollPane27 = new javax.swing.JScrollPane();
        jTextArea11 = new javax.swing.JTextArea();
        jLabel162 = new javax.swing.JLabel();
        captura = new javax.swing.JDialog();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jTextField14 = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jTextField15 = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jTextField18 = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jTextField19 = new javax.swing.JTextField();
        jTextField20 = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jTextField21 = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        jComboBox8 = new javax.swing.JComboBox();
        jComboBox5 = new javax.swing.JComboBox();
        jLabel36 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel37 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jButton4 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel46 = new javax.swing.JLabel();
        jTextField25 = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jDateChooser7 = new com.toedter.calendar.JDateChooser();
        jLabel41 = new javax.swing.JLabel();
        jLabel94 = new javax.swing.JLabel();
        jTextField63 = new javax.swing.JTextField();
        jLabel95 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jTextField26 = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jTextField11 = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jTextField12 = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jTextField13 = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        jTextField22 = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        jTextField23 = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        jTextField27 = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        jTextField91 = new javax.swing.JTextField();
        jLabel154 = new javax.swing.JLabel();
        jTextField33 = new javax.swing.JTextField();
        jLabel174 = new javax.swing.JLabel();
        jTextField98 = new javax.swing.JTextField();
        jLabel195 = new javax.swing.JLabel();
        jTextField106 = new javax.swing.JTextField();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jScrollPane20 = new javax.swing.JScrollPane();
        jTextArea5 = new javax.swing.JTextArea();
        jLabel120 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel50 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jPanel10 = new javax.swing.JPanel();
        jSpinner2 = new javax.swing.JSpinner();
        jLabel117 = new javax.swing.JLabel();
        jDateChooser8 = new com.toedter.calendar.JDateChooser();
        jDateChooser6 = new com.toedter.calendar.JDateChooser();
        jLabel75 = new javax.swing.JLabel();
        jLabel118 = new javax.swing.JLabel();
        jDateChooser19 = new com.toedter.calendar.JDateChooser();
        jLabel76 = new javax.swing.JLabel();
        jTextField42 = new javax.swing.JTextField();
        jComboBox3 = new javax.swing.JComboBox();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jTextField32 = new javax.swing.JTextField();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jPanel28 = new javax.swing.JPanel();
        jButton9 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton40 = new javax.swing.JButton();
        jPanel30 = new javax.swing.JPanel();
        jLabel42 = new javax.swing.JLabel();
        jTextField24 = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        jTextField29 = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        jTextField30 = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        jTextField31 = new javax.swing.JTextField();
        jLabel211 = new javax.swing.JLabel();
        jDateChooser31 = new com.toedter.calendar.JDateChooser();
        jScrollPane22 = new javax.swing.JScrollPane();
        jTextArea7 = new javax.swing.JTextArea();
        jLabel121 = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox();
        jLabel88 = new javax.swing.JLabel();
        jComboBox6 = new javax.swing.JComboBox();
        jButton12 = new javax.swing.JButton();
        jPanel21 = new javax.swing.JPanel();
        jSpinner3 = new javax.swing.JSpinner();
        jTextField54 = new javax.swing.JTextField();
        jLabel122 = new javax.swing.JLabel();
        jDateChooser9 = new com.toedter.calendar.JDateChooser();
        jLabel119 = new javax.swing.JLabel();
        jScrollPane21 = new javax.swing.JScrollPane();
        jTextArea6 = new javax.swing.JTextArea();
        jDateChooser22 = new com.toedter.calendar.JDateChooser();
        jLabel173 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jLabel57 = new javax.swing.JLabel();
        jTextField34 = new javax.swing.JTextField();
        jLabel58 = new javax.swing.JLabel();
        jTextField35 = new javax.swing.JTextField();
        jLabel59 = new javax.swing.JLabel();
        jTextField36 = new javax.swing.JTextField();
        jLabel60 = new javax.swing.JLabel();
        jTextField37 = new javax.swing.JTextField();
        jLabel61 = new javax.swing.JLabel();
        jTextField38 = new javax.swing.JTextField();
        jButton11 = new javax.swing.JButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        jPanel18 = new javax.swing.JPanel();
        jLabel77 = new javax.swing.JLabel();
        jTextField44 = new javax.swing.JTextField();
        jLabel78 = new javax.swing.JLabel();
        jTextField45 = new javax.swing.JTextField();
        jLabel79 = new javax.swing.JLabel();
        jTextField46 = new javax.swing.JTextField();
        jLabel80 = new javax.swing.JLabel();
        jTextField47 = new javax.swing.JTextField();
        jButton15 = new javax.swing.JButton();
        jScrollPane11 = new javax.swing.JScrollPane();
        jTable8 = new javax.swing.JTable();
        jLabel81 = new javax.swing.JLabel();
        jLabel82 = new javax.swing.JLabel();
        jTextField48 = new javax.swing.JTextField();
        jButton19 = new javax.swing.JButton();
        jButton36 = new javax.swing.JButton();
        jTextField100 = new javax.swing.JTextField();
        jLabel225 = new javax.swing.JLabel();
        jDateChooser30 = new com.toedter.calendar.JDateChooser();
        jLabel84 = new javax.swing.JLabel();
        jLabel83 = new javax.swing.JLabel();
        jPanel24 = new javax.swing.JPanel();
        jLabel97 = new javax.swing.JLabel();
        jLabel98 = new javax.swing.JLabel();
        jTextField56 = new javax.swing.JTextField();
        jLabel99 = new javax.swing.JLabel();
        jTextField57 = new javax.swing.JTextField();
        jLabel100 = new javax.swing.JLabel();
        jTextField58 = new javax.swing.JTextField();
        jLabel101 = new javax.swing.JLabel();
        jTextField59 = new javax.swing.JTextField();
        jComboBox12 = new javax.swing.JComboBox();
        jLabel102 = new javax.swing.JLabel();
        jLabel103 = new javax.swing.JLabel();
        jLabel104 = new javax.swing.JLabel();
        jScrollPane12 = new javax.swing.JScrollPane();
        jTable9 = new javax.swing.JTable();
        jButton23 = new javax.swing.JButton();
        jButton37 = new javax.swing.JButton();
        jLabel224 = new javax.swing.JLabel();
        jDateChooser29 = new com.toedter.calendar.JDateChooser();
        jRadioButton8 = new javax.swing.JRadioButton();
        jPanel31 = new javax.swing.JPanel();
        jButton29 = new javax.swing.JButton();
        jPanel32 = new javax.swing.JPanel();
        jLabel123 = new javax.swing.JLabel();
        jTextField66 = new javax.swing.JTextField();
        jLabel124 = new javax.swing.JLabel();
        jTextField67 = new javax.swing.JTextField();
        jLabel125 = new javax.swing.JLabel();
        jTextField68 = new javax.swing.JTextField();
        jPanel33 = new javax.swing.JPanel();
        jLabel126 = new javax.swing.JLabel();
        jTextField69 = new javax.swing.JTextField();
        jLabel127 = new javax.swing.JLabel();
        jTextField70 = new javax.swing.JTextField();
        jLabel130 = new javax.swing.JLabel();
        jTextField71 = new javax.swing.JTextField();
        jLabel131 = new javax.swing.JLabel();
        jTextField72 = new javax.swing.JTextField();
        jTextField73 = new javax.swing.JTextField();
        jLabel132 = new javax.swing.JLabel();
        jTextField74 = new javax.swing.JTextField();
        jLabel133 = new javax.swing.JLabel();
        jLabel128 = new javax.swing.JLabel();
        jComboBox11 = new javax.swing.JComboBox();
        jLabel129 = new javax.swing.JLabel();
        jComboBox15 = new javax.swing.JComboBox();
        jLabel134 = new javax.swing.JLabel();
        jScrollPane24 = new javax.swing.JScrollPane();
        jTextArea8 = new javax.swing.JTextArea();
        jLabel135 = new javax.swing.JLabel();
        jScrollPane25 = new javax.swing.JScrollPane();
        jTextArea9 = new javax.swing.JTextArea();
        jButton30 = new javax.swing.JButton();
        jPanel34 = new javax.swing.JPanel();
        jLabel136 = new javax.swing.JLabel();
        jTextField75 = new javax.swing.JTextField();
        jDateChooser10 = new com.toedter.calendar.JDateChooser();
        jLabel137 = new javax.swing.JLabel();
        jPanel35 = new javax.swing.JPanel();
        jLabel138 = new javax.swing.JLabel();
        jTextField76 = new javax.swing.JTextField();
        jLabel139 = new javax.swing.JLabel();
        jTextField77 = new javax.swing.JTextField();
        jLabel140 = new javax.swing.JLabel();
        jTextField78 = new javax.swing.JTextField();
        jLabel141 = new javax.swing.JLabel();
        jTextField79 = new javax.swing.JTextField();
        jLabel142 = new javax.swing.JLabel();
        jTextField80 = new javax.swing.JTextField();
        jLabel143 = new javax.swing.JLabel();
        jTextField81 = new javax.swing.JTextField();
        jLabel144 = new javax.swing.JLabel();
        jTextField82 = new javax.swing.JTextField();
        jLabel145 = new javax.swing.JLabel();
        jTextField83 = new javax.swing.JTextField();
        jLabel146 = new javax.swing.JLabel();
        jTextField84 = new javax.swing.JTextField();
        jLabel147 = new javax.swing.JLabel();
        jTextField85 = new javax.swing.JTextField();
        jLabel148 = new javax.swing.JLabel();
        jTextField86 = new javax.swing.JTextField();
        jLabel149 = new javax.swing.JLabel();
        jTextField87 = new javax.swing.JTextField();
        jLabel150 = new javax.swing.JLabel();
        jTextField88 = new javax.swing.JTextField();
        jLabel151 = new javax.swing.JLabel();
        jTextField90 = new javax.swing.JTextField();
        jScrollPane26 = new javax.swing.JScrollPane();
        jTextArea10 = new javax.swing.JTextArea();
        jLabel157 = new javax.swing.JLabel();
        jPanel50 = new javax.swing.JPanel();
        jLabel214 = new javax.swing.JLabel();
        jTextField127 = new javax.swing.JTextField();
        jScrollPane37 = new javax.swing.JScrollPane();
        jTable24 = new javax.swing.JTable();
        jButton59 = new javax.swing.JButton();
        jLabel215 = new javax.swing.JLabel();
        jTextField128 = new javax.swing.JTextField();
        jLabel216 = new javax.swing.JLabel();
        jTextField129 = new javax.swing.JTextField();
        jButton62 = new javax.swing.JButton();
        jButton63 = new javax.swing.JButton();
        SelecFolio = new javax.swing.JDialog();
        jLabel27 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton8 = new javax.swing.JButton();
        jButton35 = new javax.swing.JButton();
        conss = new javax.swing.JDialog();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel15 = new javax.swing.JPanel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jPanel13 = new javax.swing.JPanel();
        jLabel67 = new javax.swing.JLabel();
        jTextField16 = new javax.swing.JTextField();
        jLabel68 = new javax.swing.JLabel();
        jDateChooser4 = new com.toedter.calendar.JDateChooser();
        jDateChooser5 = new com.toedter.calendar.JDateChooser();
        jLabel69 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        jComboBox9 = new javax.swing.JComboBox();
        jButton16 = new javax.swing.JButton();
        jButton41 = new javax.swing.JButton();
        jLabel175 = new javax.swing.JLabel();
        jTextField99 = new javax.swing.JTextField();
        jRadioButton2 = new javax.swing.JRadioButton();
        jTextField43 = new javax.swing.JTextField();
        jRadioButton7 = new javax.swing.JRadioButton();
        jPanel23 = new javax.swing.JPanel();
        jLabel89 = new javax.swing.JLabel();
        jTextField28 = new javax.swing.JTextField();
        jScrollPane15 = new javax.swing.JScrollPane();
        jTable12 = new javax.swing.JTable();
        jButton27 = new javax.swing.JButton();
        jScrollPane23 = new javax.swing.JScrollPane();
        jTable16 = new javax.swing.JTable();
        jLabel93 = new javax.swing.JLabel();
        jTextField112 = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        jPanel38 = new javax.swing.JPanel();
        jLabel63 = new javax.swing.JLabel();
        jTextField40 = new javax.swing.JTextField();
        jLabel64 = new javax.swing.JLabel();
        jTextField41 = new javax.swing.JTextField();
        jLabel65 = new javax.swing.JLabel();
        jTextField49 = new javax.swing.JTextField();
        jLabel66 = new javax.swing.JLabel();
        jTextField50 = new javax.swing.JTextField();
        jLabel70 = new javax.swing.JLabel();
        jTextField51 = new javax.swing.JTextField();
        jLabel71 = new javax.swing.JLabel();
        jTextField52 = new javax.swing.JTextField();
        jCheckBox14 = new javax.swing.JCheckBox();
        jPanel51 = new javax.swing.JPanel();
        jLabel217 = new javax.swing.JLabel();
        jLabel218 = new javax.swing.JLabel();
        jDateChooser27 = new com.toedter.calendar.JDateChooser();
        jDateChooser28 = new com.toedter.calendar.JDateChooser();
        jLabel219 = new javax.swing.JLabel();
        jButton60 = new javax.swing.JButton();
        jPanel39 = new javax.swing.JPanel();
        jScrollPane29 = new javax.swing.JScrollPane();
        jTable18 = new javax.swing.JTable();
        jLabel153 = new javax.swing.JLabel();
        jDateChooser20 = new com.toedter.calendar.JDateChooser();
        jDateChooser21 = new com.toedter.calendar.JDateChooser();
        jButton31 = new javax.swing.JButton();
        jButton32 = new javax.swing.JButton();
        jComboBox17 = new javax.swing.JComboBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox13 = new javax.swing.JCheckBox();
        jPanel49 = new javax.swing.JPanel();
        jLabel212 = new javax.swing.JLabel();
        jDateChooser25 = new com.toedter.calendar.JDateChooser();
        jLabel213 = new javax.swing.JLabel();
        jDateChooser26 = new com.toedter.calendar.JDateChooser();
        jButton58 = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jLabel85 = new javax.swing.JLabel();
        jTextField53 = new javax.swing.JTextField();
        jLabel86 = new javax.swing.JLabel();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jLabel87 = new javax.swing.JLabel();
        jDateChooser3 = new com.toedter.calendar.JDateChooser();
        jButton20 = new javax.swing.JButton();
        jRadioButton5 = new javax.swing.JRadioButton();
        jRadioButton6 = new javax.swing.JRadioButton();
        jPanel36 = new javax.swing.JPanel();
        jComboBox4 = new javax.swing.JComboBox();
        jComboBox13 = new javax.swing.JComboBox();
        jLabel40 = new javax.swing.JLabel();
        jLabel152 = new javax.swing.JLabel();
        jLabel158 = new javax.swing.JLabel();
        jComboBox16 = new javax.swing.JComboBox();
        jPanel37 = new javax.swing.JPanel();
        jDateChooser11 = new com.toedter.calendar.JDateChooser();
        jLabel159 = new javax.swing.JLabel();
        jLabel160 = new javax.swing.JLabel();
        jDateChooser14 = new com.toedter.calendar.JDateChooser();
        jButton33 = new javax.swing.JButton();
        jButton34 = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        jCheckBox3 = new javax.swing.JCheckBox();
        jPanel25 = new javax.swing.JPanel();
        jLabel105 = new javax.swing.JLabel();
        jTextField55 = new javax.swing.JTextField();
        jScrollPane13 = new javax.swing.JScrollPane();
        jTable10 = new javax.swing.JTable();
        jPanel26 = new javax.swing.JPanel();
        jLabel106 = new javax.swing.JLabel();
        jLabel107 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel108 = new javax.swing.JLabel();
        jLabel109 = new javax.swing.JLabel();
        jLabel110 = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        jButton28 = new javax.swing.JButton();
        jScrollPane17 = new javax.swing.JScrollPane();
        jTable14 = new javax.swing.JTable();
        jScrollPane18 = new javax.swing.JScrollPane();
        jTable15 = new javax.swing.JTable();
        jLabel96 = new javax.swing.JLabel();
        jTextField64 = new javax.swing.JTextField();
        jLabel116 = new javax.swing.JLabel();
        jTextField65 = new javax.swing.JTextField();
        jScrollPane19 = new javax.swing.JScrollPane();
        jTextArea4 = new javax.swing.JTextArea();
        jLabel161 = new javax.swing.JLabel();
        jDateChooser15 = new com.toedter.calendar.JDateChooser();
        jDateChooser16 = new com.toedter.calendar.JDateChooser();
        jLabel163 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel164 = new javax.swing.JLabel();
        jDateChooser17 = new com.toedter.calendar.JDateChooser();
        jLabel165 = new javax.swing.JLabel();
        jDateChooser18 = new com.toedter.calendar.JDateChooser();
        jButton38 = new javax.swing.JButton();
        jScrollPane28 = new javax.swing.JScrollPane();
        jTable17 = new javax.swing.JTable();
        jButton39 = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox12 = new javax.swing.JCheckBox();
        jPanel42 = new javax.swing.JPanel();
        jLabel196 = new javax.swing.JLabel();
        jDateChooser23 = new com.toedter.calendar.JDateChooser();
        jDateChooser24 = new com.toedter.calendar.JDateChooser();
        jButton50 = new javax.swing.JButton();
        jScrollPane33 = new javax.swing.JScrollPane();
        jTable20 = new javax.swing.JTable();
        jButton51 = new javax.swing.JButton();
        companiacaptura = new javax.swing.JDialog();
        jButton10 = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel62 = new javax.swing.JLabel();
        jTextField39 = new javax.swing.JTextField();
        buttonGroup1 = new javax.swing.ButtonGroup();
        impresion = new javax.swing.JDialog();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel14 = new javax.swing.JPanel();
        jLabel72 = new javax.swing.JLabel();
        jComboBox7 = new javax.swing.JComboBox();
        jButton14 = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        jButton18 = new javax.swing.JButton();
        jLabel112 = new javax.swing.JLabel();
        jComboBox14 = new javax.swing.JComboBox();
        jButton56 = new javax.swing.JButton();
        jPanel27 = new javax.swing.JPanel();
        jLabel113 = new javax.swing.JLabel();
        jLabel114 = new javax.swing.JLabel();
        jDateChooser12 = new com.toedter.calendar.JDateChooser();
        jDateChooser13 = new com.toedter.calendar.JDateChooser();
        jLabel115 = new javax.swing.JLabel();
        jButton25 = new javax.swing.JButton();
        selecaseg = new javax.swing.JDialog();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTable6 = new javax.swing.JTable();
        jLabel74 = new javax.swing.JLabel();
        jTextField17 = new javax.swing.JTextField();
        buttonGroup2 = new javax.swing.ButtonGroup();
        tablafolios = new javax.swing.JDialog();
        jScrollPane10 = new javax.swing.JScrollPane();
        jTable7 = new javax.swing.JTable();
        buttonGroup3 = new javax.swing.ButtonGroup();
        contraesp = new javax.swing.JDialog();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        buttonGroup4 = new javax.swing.ButtonGroup();
        buscapaciente = new javax.swing.JDialog();
        jLabel111 = new javax.swing.JLabel();
        jTextField60 = new javax.swing.JTextField();
        jScrollPane14 = new javax.swing.JScrollPane();
        jTable11 = new javax.swing.JTable();
        jButton24 = new javax.swing.JButton();
        cambiafactura = new javax.swing.JDialog();
        jLabel90 = new javax.swing.JLabel();
        jTextField61 = new javax.swing.JTextField();
        jLabel91 = new javax.swing.JLabel();
        jComboBox10 = new javax.swing.JComboBox();
        jButton21 = new javax.swing.JButton();
        jLabel92 = new javax.swing.JLabel();
        jTextField62 = new javax.swing.JTextField();
        jButton22 = new javax.swing.JButton();
        jButton26 = new javax.swing.JButton();
        jScrollPane16 = new javax.swing.JScrollPane();
        jTable13 = new javax.swing.JTable();
        conf_mail = new javax.swing.JDialog();
        jLabel168 = new javax.swing.JLabel();
        jTextField94 = new javax.swing.JTextField();
        jLabel169 = new javax.swing.JLabel();
        jPasswordField2 = new javax.swing.JPasswordField();
        jLabel170 = new javax.swing.JLabel();
        jTextField95 = new javax.swing.JTextField();
        jLabel171 = new javax.swing.JLabel();
        jTextField96 = new javax.swing.JTextField();
        jButton46 = new javax.swing.JButton();
        jLabel176 = new javax.swing.JLabel();
        jTextField101 = new javax.swing.JTextField();
        jLabel177 = new javax.swing.JLabel();
        jScrollPane32 = new javax.swing.JScrollPane();
        jTextArea13 = new javax.swing.JTextArea();
        SendMail = new javax.swing.JDialog();
        jPanel40 = new javax.swing.JPanel();
        jRadioButton9 = new javax.swing.JRadioButton();
        jRadioButton10 = new javax.swing.JRadioButton();
        jLabel155 = new javax.swing.JLabel();
        jTextField89 = new javax.swing.JTextField();
        jLabel156 = new javax.swing.JLabel();
        jTextField92 = new javax.swing.JTextField();
        jLabel166 = new javax.swing.JLabel();
        jScrollPane30 = new javax.swing.JScrollPane();
        jTextArea12 = new javax.swing.JTextArea();
        jLabel167 = new javax.swing.JLabel();
        jTextField93 = new javax.swing.JTextField();
        jButton42 = new javax.swing.JButton();
        jButton43 = new javax.swing.JButton();
        jButton44 = new javax.swing.JButton();
        jScrollPane31 = new javax.swing.JScrollPane();
        jTable19 = new javax.swing.JTable();
        jButton45 = new javax.swing.JButton();
        jLabel172 = new javax.swing.JLabel();
        jTextField97 = new javax.swing.JTextField();
        buttonGroup5 = new javax.swing.ButtonGroup();
        Activacion = new javax.swing.JDialog();
        jLabel178 = new javax.swing.JLabel();
        jTextField102 = new javax.swing.JTextField();
        datos_ujat = new javax.swing.JDialog();
        jPanel41 = new javax.swing.JPanel();
        jLabel179 = new javax.swing.JLabel();
        jTextField103 = new javax.swing.JTextField();
        jLabel180 = new javax.swing.JLabel();
        jTextField104 = new javax.swing.JTextField();
        jLabel181 = new javax.swing.JLabel();
        jComboBox18 = new javax.swing.JComboBox();
        jLabel182 = new javax.swing.JLabel();
        jTextField105 = new javax.swing.JTextField();
        jLabel183 = new javax.swing.JLabel();
        jLabel184 = new javax.swing.JLabel();
        jTextField107 = new javax.swing.JTextField();
        jLabel185 = new javax.swing.JLabel();
        jTextField108 = new javax.swing.JTextField();
        jLabel186 = new javax.swing.JLabel();
        jTextField109 = new javax.swing.JTextField();
        jComboBox21 = new javax.swing.JComboBox();
        jPanel43 = new javax.swing.JPanel();
        jLabel187 = new javax.swing.JLabel();
        jTextField110 = new javax.swing.JTextField();
        jLabel188 = new javax.swing.JLabel();
        jComboBox19 = new javax.swing.JComboBox();
        jLabel189 = new javax.swing.JLabel();
        jTextField111 = new javax.swing.JTextField();
        jLabel190 = new javax.swing.JLabel();
        jLabel191 = new javax.swing.JLabel();
        jTextField113 = new javax.swing.JTextField();
        jLabel192 = new javax.swing.JLabel();
        jTextField114 = new javax.swing.JTextField();
        jLabel193 = new javax.swing.JLabel();
        jTextField115 = new javax.swing.JTextField();
        jLabel194 = new javax.swing.JLabel();
        jTextField116 = new javax.swing.JTextField();
        jComboBox20 = new javax.swing.JComboBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jButton13 = new javax.swing.JButton();
        jButton47 = new javax.swing.JButton();
        jButton48 = new javax.swing.JButton();
        jButton49 = new javax.swing.JButton();
        cotizacion = new javax.swing.JDialog();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        jPanel44 = new javax.swing.JPanel();
        jLabel197 = new javax.swing.JLabel();
        jComboBox22 = new javax.swing.JComboBox();
        jLabel198 = new javax.swing.JLabel();
        jTextField117 = new javax.swing.JTextField();
        jPanel46 = new javax.swing.JPanel();
        jCheckBox5 = new javax.swing.JCheckBox();
        jCheckBox6 = new javax.swing.JCheckBox();
        jCheckBox7 = new javax.swing.JCheckBox();
        jCheckBox8 = new javax.swing.JCheckBox();
        jCheckBox9 = new javax.swing.JCheckBox();
        jCheckBox10 = new javax.swing.JCheckBox();
        jCheckBox11 = new javax.swing.JCheckBox();
        jLabel199 = new javax.swing.JLabel();
        jTextField118 = new javax.swing.JTextField();
        jLabel200 = new javax.swing.JLabel();
        jTextField119 = new javax.swing.JTextField();
        jLabel201 = new javax.swing.JLabel();
        jTextField120 = new javax.swing.JTextField();
        jButton52 = new javax.swing.JButton();
        jButton53 = new javax.swing.JButton();
        jLabel202 = new javax.swing.JLabel();
        jLabel203 = new javax.swing.JLabel();
        jLabel204 = new javax.swing.JLabel();
        jComboBox23 = new javax.swing.JComboBox();
        jPanel45 = new javax.swing.JPanel();
        jLabel205 = new javax.swing.JLabel();
        jTextField121 = new javax.swing.JTextField();
        jScrollPane34 = new javax.swing.JScrollPane();
        jTable21 = new javax.swing.JTable();
        jButton54 = new javax.swing.JButton();
        jTextField122 = new javax.swing.JTextField();
        jLabel206 = new javax.swing.JLabel();
        jTextField123 = new javax.swing.JTextField();
        jLabel207 = new javax.swing.JLabel();
        CatalogoArticulosTCA = new javax.swing.JDialog();
        jScrollPane35 = new javax.swing.JScrollPane();
        jTable22 = new javax.swing.JTable();
        sendcorreo = new javax.swing.JDialog();
        jPanel47 = new javax.swing.JPanel();
        jLabel208 = new javax.swing.JLabel();
        jTextField124 = new javax.swing.JTextField();
        jButton55 = new javax.swing.JButton();
        RutaFiles = new javax.swing.JDialog();
        jPanel48 = new javax.swing.JPanel();
        jLabel209 = new javax.swing.JLabel();
        jTextField125 = new javax.swing.JTextField();
        jLabel210 = new javax.swing.JLabel();
        TablaStatus = new javax.swing.JDialog();
        jScrollPane36 = new javax.swing.JScrollPane();
        jTable23 = new javax.swing.JTable();
        jButton57 = new javax.swing.JButton();
        ExcelEstad = new javax.swing.JDialog();
        jScrollPane38 = new javax.swing.JScrollPane();
        jTable25 = new javax.swing.JTable();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jButton64 = new javax.swing.JButton();
        Key = new javax.swing.JDialog();
        jLabel220 = new javax.swing.JLabel();
        jTextField130 = new javax.swing.JTextField();
        jLabel221 = new javax.swing.JLabel();
        jTextField131 = new javax.swing.JTextField();
        jButton61 = new javax.swing.JButton();
        Configuracion = new javax.swing.JDialog();
        jButton65 = new javax.swing.JButton();
        jPanel29 = new javax.swing.JPanel();
        jLabel226 = new javax.swing.JLabel();
        jTextField126 = new javax.swing.JTextField();
        jLabel227 = new javax.swing.JLabel();
        jTextField132 = new javax.swing.JTextField();
        jLabel228 = new javax.swing.JLabel();
        jTextField133 = new javax.swing.JTextField();
        jLabel230 = new javax.swing.JLabel();
        jTextField134 = new javax.swing.JTextField();
        jLabel229 = new javax.swing.JLabel();
        jPasswordField3 = new javax.swing.JPasswordField();
        jPanel52 = new javax.swing.JPanel();
        jLabel231 = new javax.swing.JLabel();
        jTextField135 = new javax.swing.JTextField();
        jLabel232 = new javax.swing.JLabel();
        jTextField136 = new javax.swing.JTextField();
        jLabel233 = new javax.swing.JLabel();
        jTextField137 = new javax.swing.JTextField();
        jLabel234 = new javax.swing.JLabel();
        jTextField138 = new javax.swing.JTextField();
        jLabel235 = new javax.swing.JLabel();
        jPasswordField4 = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jPasswordField1 = new javax.swing.JPasswordField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel222 = new javax.swing.JLabel();
        jLabel223 = new javax.swing.JLabel();
        menufacturas = new javax.swing.JMenuBar();
        fac = new javax.swing.JMenu();
        captu = new javax.swing.JMenuItem();
        cons = new javax.swing.JMenuItem();
        repor = new javax.swing.JMenuItem();
        cambiafac = new javax.swing.JMenuItem();
        acerca = new javax.swing.JMenu();
        licenciaa = new javax.swing.JMenu();
        datosujat = new javax.swing.JMenu();
        aniadir = new javax.swing.JMenuItem();
        cotizador = new javax.swing.JMenuItem();
        config = new javax.swing.JMenu();
        conexion = new javax.swing.JMenuItem();

        acercade.setTitle(".:. Hospital del Sureste A.C. .:. - Acerca de ...");
        acercade.setModal(true);

        jTextArea11.setEditable(false);
        jTextArea11.setBackground(new java.awt.Color(227, 226, 226));
        jTextArea11.setColumns(20);
        jTextArea11.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTextArea11.setRows(5);
        jTextArea11.setText("Hospital del Sureste A.C.\nACC Control\nVersión 6.0\n\nPuesta en Marcha:\n22 Diciembre 2014\n\nDesarrollado Por:\nM.T.I. Obed Campos Solano\nobed_cs@hotmail.com");
        jTextArea11.setOpaque(false);
        jScrollPane27.setViewportView(jTextArea11);

        jLabel162.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/HSTE_about.png"))); // NOI18N

        javax.swing.GroupLayout acercadeLayout = new javax.swing.GroupLayout(acercade.getContentPane());
        acercade.getContentPane().setLayout(acercadeLayout);
        acercadeLayout.setHorizontalGroup(
            acercadeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(acercadeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel162)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane27, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                .addContainerGap())
        );
        acercadeLayout.setVerticalGroup(
            acercadeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(acercadeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(acercadeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel162, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane27))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        captura.setTitle(".:. Captura de Datos .:.");
        captura.setModal(true);

        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/corregir.png"))); // NOI18N
        jButton3.setText("Corregir");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Fechas"));
        jPanel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        jLabel15.setText("Fecha de Facturación ASSIST:");

        jTextField3.setEditable(false);
        jTextField3.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel21.setText("Fecha de Ingreso Paciente:");

        jTextField9.setEditable(false);
        jTextField9.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel23.setText("Fecha de Alta Paciente:");

        jTextField10.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel21)
                            .addComponent(jLabel23))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField9, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                            .addComponent(jTextField10))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 13, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Datos Adicionales"));

        jLabel28.setText("No. Empleado:");

        jLabel29.setText("Nombre:");

        jTextField15.setEditable(false);

        jLabel30.setText("Ocupación:");

        jLabel31.setText("Parentesco:");

        jLabel32.setText("Clave:");

        jLabel33.setText("URES:");

        jLabel34.setText("No. Receta:");

        jLabel35.setText("No. Pase:");

        jComboBox8.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ninguno", "Apoyo Administrativo", "Auxiliar Administrativo", "Auxiliar de Biblioteca", "Auxiliar de Campo", "Auxiliar de Clinica", "Auxiliar de Imprenta", "Auxiliar de Mantenimiento", "Auxiliar de Laboratorio", "Auxiliar de Servicio", "Chofer de Confianza", "Coordinador", "Desarrollo de Sistemas", "Jefe de Departamento", "Jefe de Departamento Administrativo", "Jefe de Unidad", "Jubilado Docente", "Logistica", "Medico General", "Oficial Administrativo", "Operador de Transporte", "Pensionado Docente", "Pensionado Jubilado", "Profesor Investigador Asignatura", "Profesor Investigador MT", "Profesor Investigador TC", "Rectoria", "Secretaria", "Secretaria de Admision", "Secretaria de Confianza", "Tecnico Academico MT", "Tecnico Academico TC", "Velador" }));

        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ninguno", "Concubina", "Esposa", "Esposo", "Hija", "Hijo", "Madre", "Padre", "Viuda", "Viudo" }));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel28)
                        .addComponent(jLabel34, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addComponent(jLabel30))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jTextField20, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel33)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField19))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel32)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jComboBox8, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel35)
                    .addComponent(jLabel31)
                    .addComponent(jLabel29))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField21, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(47, 47, 47))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29)
                    .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32)
                    .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33)
                    .addComponent(jTextField20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34)
                    .addComponent(jLabel35)
                    .addComponent(jTextField21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(jComboBox8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31)
                    .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jLabel36.setText("Observaciones Factura:");

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jLabel37.setText("Observaciones Internas:");

        jTextArea2.setColumns(20);
        jTextArea2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextArea2.setLineWrap(true);
        jTextArea2.setRows(5);
        jTextArea2.setText("SIN OBSERVACIONES");
        jTextArea2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextArea2KeyTyped(evt);
            }
        });
        jScrollPane2.setViewportView(jTextArea2);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/guardar.png"))); // NOI18N
        jButton4.setText("Guardar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Acciones CyC"));

        jLabel46.setText("Folio de Control:");

        jTextField25.setEditable(false);
        jTextField25.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jButton6.setText("Nuevo");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText("Añadir Mas");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jDateChooser7.setDateFormatString("yyyy-MM-dd");

        jLabel41.setText("Recepción:");

        jLabel94.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel94.setText("Status:");

        jTextField63.setEditable(false);
        jTextField63.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextField63.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel95.setText("F=Facturada   X=Cancelada");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel46)
                    .addComponent(jLabel41)
                    .addComponent(jLabel94))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel95)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jDateChooser7, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                            .addComponent(jTextField25))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton7)))
                    .addComponent(jTextField63, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel46)
                    .addComponent(jTextField25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jDateChooser7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel94)
                    .addComponent(jTextField63, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel95)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Datos de Factura"));

        jLabel14.setText("Factura:");

        jTextField2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jTextField2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jLabel20.setText("Monto:");

        jTextField8.setEditable(false);
        jTextField8.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel18.setText("Folio de Ingreso:");

        jTextField6.setEditable(false);
        jTextField6.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel17.setText("No. de Paciente:");

        jTextField5.setEditable(false);
        jTextField5.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel16.setText("Aseguradora:");

        jTextField4.setEditable(false);

        jLabel22.setText("Adicional:");

        jTextField26.setHorizontalAlignment(javax.swing.JTextField.LEFT);

        jLabel19.setText("Paciente:");

        jTextField7.setEditable(false);

        jLabel24.setText("Póliza:");

        jLabel25.setText("Siniestro:");

        jLabel26.setText("Afiliación:");

        jLabel38.setText("Deducible:");

        jTextField22.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField22.setText("0");
        jTextField22.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField22KeyReleased(evt);
            }
        });

        jLabel39.setText("Coaseguro:");

        jTextField23.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField23.setText("0");
        jTextField23.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField23KeyReleased(evt);
            }
        });

        jLabel48.setText("Descto. Esp:");

        jTextField27.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField27.setText("0");
        jTextField27.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField27KeyReleased(evt);
            }
        });

        jLabel49.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel49.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel49.setText("Total:");

        jTextField91.setEditable(false);
        jTextField91.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jTextField91.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField91.setText("$0.00");

        jLabel154.setText("UUID:");

        jTextField33.setEditable(false);

        jLabel174.setText("Días de Hospitalización:");

        jTextField98.setEditable(false);
        jTextField98.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel195.setText("Cliente:");

        jTextField106.setEditable(false);
        jTextField106.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel16)
                            .addComponent(jLabel19)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel20)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField6, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE))
                            .addComponent(jTextField4)
                            .addComponent(jTextField7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel17)
                            .addComponent(jLabel22)
                            .addComponent(jLabel24)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel26)
                            .addComponent(jLabel154))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel38)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField22, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel39))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jTextField33, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel174)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField23, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
                            .addComponent(jTextField98))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel48, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel195, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField26))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel7Layout.createSequentialGroup()
                                    .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jLabel25)))
                            .addComponent(jTextField27, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField12)
                            .addComponent(jTextField91, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)))
                    .addComponent(jTextField106, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22)
                    .addComponent(jTextField26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24)
                    .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25)
                    .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel38)
                    .addComponent(jTextField22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel39)
                    .addComponent(jTextField23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel48)
                    .addComponent(jTextField27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel49, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField91, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel154)
                    .addComponent(jTextField33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel174)
                    .addComponent(jTextField98, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel195)
                    .addComponent(jTextField106, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabel53.setText("Caracteres (Máximo 100):");

        jLabel54.setText("0");

        jTextArea5.setColumns(20);
        jTextArea5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextArea5.setLineWrap(true);
        jTextArea5.setRows(5);
        jTextArea5.setText("SIN OBSERVACIONES");
        jTextArea5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextArea5KeyTyped(evt);
            }
        });
        jScrollPane20.setViewportView(jTextArea5);

        jLabel120.setText("Observaciones Adicionales:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel36)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jButton3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton4))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel37)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel54, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel53, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel120)
                            .addComponent(jScrollPane20, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel36)
                            .addComponent(jLabel120, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton4)
                            .addComponent(jButton3)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane20, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel37)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel53)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel54))
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(76, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Captura de Datos", jPanel3);

        jLabel50.setText("Observaciones Internas:");

        jTextArea3.setColumns(20);
        jTextArea3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextArea3.setLineWrap(true);
        jTextArea3.setRows(5);
        jTextArea3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextArea3KeyTyped(evt);
            }
        });
        jScrollPane4.setViewportView(jTextArea3);

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Status", "Fecha Status", "Observaciones", "Elaboro"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane7.setViewportView(jTable4);
        if (jTable4.getColumnModel().getColumnCount() > 0) {
            jTable4.getColumnModel().getColumn(0).setMinWidth(100);
            jTable4.getColumnModel().getColumn(0).setMaxWidth(100);
            jTable4.getColumnModel().getColumn(1).setMinWidth(150);
            jTable4.getColumnModel().getColumn(1).setMaxWidth(150);
            jTable4.getColumnModel().getColumn(3).setMinWidth(80);
            jTable4.getColumnModel().getColumn(3).setMaxWidth(80);
        }

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Extras"));

        jSpinner2.setModel(new javax.swing.SpinnerNumberModel(0, 0, 60, 1));
        jSpinner2.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinner2StateChanged(evt);
            }
        });

        jLabel117.setText("Entrega:");

        jDateChooser8.setDateFormatString("yyyy-MM-dd");

        jDateChooser6.setDateFormatString("yyyy-MM-dd");

        jLabel75.setText("Refacturacion:");

        jLabel118.setText("Días Liquidar:");

        jDateChooser19.setDateFormatString("yyyy-MM-dd");

        jLabel76.setText("Sustituye:");

        jTextField42.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField42.setText("0");

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "A Firma", "Cancelada", "Devuelta", "En Facturacion", "En Revision", "Entregada", "Especial", "Liquidada", "Por Entregar", "Refacturada", "EspecialUJAT", "En Admision", "En Urgencias" }));
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });

        jLabel51.setText("Status:");

        jLabel52.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel52.setText("Folio:");

        jTextField32.setEditable(false);
        jTextField32.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jDateChooser19, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel10Layout.createSequentialGroup()
                            .addGap(17, 17, 17)
                            .addComponent(jLabel118)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jSpinner2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel10Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel75)
                                .addComponent(jLabel117)
                                .addComponent(jLabel76)
                                .addComponent(jLabel51)
                                .addComponent(jLabel52))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jDateChooser8, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                                .addComponent(jDateChooser6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextField42)
                                .addComponent(jTextField32)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel118)
                    .addComponent(jSpinner2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDateChooser19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jDateChooser8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel117, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jDateChooser6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel75, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel76)
                    .addComponent(jTextField42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel51))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel52)
                    .addComponent(jTextField32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel55.setText("(Máx 100):");

        jLabel56.setText("0");

        jPanel28.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/corregir.png"))); // NOI18N
        jButton9.setText("Corregir");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/guardar.png"))); // NOI18N
        jButton5.setText("Guardar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/update.png"))); // NOI18N
        jButton40.setText("Regresar Status");
        jButton40.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton40ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton40)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5)
                    .addComponent(jButton9)
                    .addComponent(jButton40))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel30.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Datos de Factura"));

        jLabel42.setText("Factura:");

        jTextField24.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jTextField24.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField24ActionPerformed(evt);
            }
        });

        jLabel44.setText("Monto:");

        jTextField29.setEditable(false);
        jTextField29.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel45.setText("Aseguradora:");

        jTextField30.setEditable(false);

        jLabel47.setText("Paciente:");

        jTextField31.setEditable(false);

        jLabel211.setText("Fecha Liquida:");

        jDateChooser31.setDateFormatString("yyyy-MM-dd");

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel45)
                    .addComponent(jLabel47)
                    .addComponent(jLabel42))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jTextField30)
                    .addGroup(jPanel30Layout.createSequentialGroup()
                        .addComponent(jTextField24, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel44)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField29, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel211)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateChooser31, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE))
                    .addComponent(jTextField31))
                .addGap(110, 110, 110))
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel42)
                        .addComponent(jTextField24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel44)
                        .addComponent(jTextField29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel211))
                    .addComponent(jDateChooser31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel45)
                    .addComponent(jTextField30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel47)
                    .addComponent(jTextField31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTextArea7.setColumns(20);
        jTextArea7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextArea7.setLineWrap(true);
        jTextArea7.setRows(5);
        jScrollPane22.setViewportView(jTextArea7);

        jLabel121.setText("Observaciones Adicionales:");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jScrollPane7)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, 518, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(620, 620, 620))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel50))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel121)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel55)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane22))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel50)
                            .addComponent(jLabel121)
                            .addComponent(jLabel56)
                            .addComponent(jLabel55))
                        .addGap(3, 3, 3)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane22)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Modificar Status", jPanel8);

        jLabel43.setText("Folio:");

        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jLabel88.setText("Status:");

        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "A Firma", "Cancelada", "Devuelta", "En Facturacion", "En Revision", "Entregada", "Especial", "Liquidada", "Por Entregar", "Refacturada", "EspecialUJAT", "En Admision", "En Urgencias" }));
        jComboBox6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox6ActionPerformed(evt);
            }
        });

        jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/aceptar.png"))); // NOI18N
        jButton12.setText("Afectar");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jPanel21.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Días Para Liquidar"));

        jSpinner3.setModel(new javax.swing.SpinnerNumberModel(0, 0, 60, 1));
        jSpinner3.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinner3StateChanged(evt);
            }
        });

        jTextField54.setEditable(false);
        jTextField54.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField54.setText("2001-01-01");

        jLabel122.setText("Fecha de Entrega:");

        jDateChooser9.setDateFormatString("yyyy-MM-dd");

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jDateChooser9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addComponent(jSpinner3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField54, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE))
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addComponent(jLabel122)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jSpinner3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField54, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel122, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDateChooser9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel119.setText("Comentarios Generales");

        jTextArea6.setColumns(20);
        jTextArea6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextArea6.setLineWrap(true);
        jTextArea6.setRows(5);
        jScrollPane21.setViewportView(jTextArea6);

        jDateChooser22.setDateFormatString("yyyy-MM-dd");

        jLabel173.setText("Liquidación:");

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton12)
                    .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel20Layout.createSequentialGroup()
                            .addGap(40, 40, 40)
                            .addComponent(jLabel43)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel20Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel20Layout.createSequentialGroup()
                                    .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel88)
                                        .addComponent(jLabel173))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jComboBox6, 0, 115, Short.MAX_VALUE)
                                        .addComponent(jDateChooser22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel20Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jLabel119))
                        .addGroup(jPanel20Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jScrollPane21, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(783, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel43))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel88)
                    .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel173)
                    .addComponent(jDateChooser22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel119)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton12)
                .addContainerGap(217, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Modificar Status de Folio", jPanel20);

        jPanel17.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Depositos"));

        jLabel57.setText("Número de Documento:");

        jTextField34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField34ActionPerformed(evt);
            }
        });

        jLabel58.setText("Aseguradora:");

        jTextField35.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField35ActionPerformed(evt);
            }
        });
        jTextField35.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField35KeyReleased(evt);
            }
        });

        jLabel59.setText("Tipo de Pago / Transfer:");

        jLabel60.setText("Monto Total:");

        jLabel61.setText("Autorizacion:");

        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/guardar.png"))); // NOI18N
        jButton11.setText("Guardar");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton11)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel57)
                            .addComponent(jLabel58)
                            .addComponent(jLabel59)
                            .addComponent(jLabel61))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField35, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
                            .addGroup(jPanel17Layout.createSequentialGroup()
                                .addComponent(jTextField34, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel60)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField37, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jTextField36)
                            .addComponent(jTextField38))))
                .addContainerGap(63, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel57)
                    .addComponent(jTextField34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel60)
                    .addComponent(jTextField37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel58)
                    .addComponent(jTextField35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel59)
                    .addComponent(jTextField36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel61)
                    .addComponent(jTextField38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addComponent(jButton11)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        buttonGroup3.add(jRadioButton3);
        jRadioButton3.setText("Depositos");
        jRadioButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton3ActionPerformed(evt);
            }
        });

        buttonGroup3.add(jRadioButton4);
        jRadioButton4.setText("Abono/Pago a Factura");
        jRadioButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton4ActionPerformed(evt);
            }
        });

        jPanel18.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Abono a Factura"));

        jLabel77.setText("Factura:");

        jTextField44.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField44.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField44ActionPerformed(evt);
            }
        });

        jLabel78.setText("Monto:");

        jTextField45.setEditable(false);

        jLabel79.setText("Abono:");

        jLabel80.setText("Total Abonado:");

        jTextField47.setEditable(false);

        jButton15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/pagar.png"))); // NOI18N
        jButton15.setText("Abono / Pago");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        jTable8.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Fecha", "Monto"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane11.setViewportView(jTable8);
        if (jTable8.getColumnModel().getColumnCount() > 0) {
            jTable8.getColumnModel().getColumn(1).setMinWidth(120);
            jTable8.getColumnModel().getColumn(1).setMaxWidth(120);
        }

        jLabel81.setText("HISTORIAL DE ABONOS");

        jLabel82.setText("Adeudo:");

        jTextField48.setEditable(false);

        jButton19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/corregir.png"))); // NOI18N
        jButton19.setText("Corregir");
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });

        jButton36.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/modif_status.png"))); // NOI18N
        jButton36.setText("Mod. Status");
        jButton36.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton36ActionPerformed(evt);
            }
        });

        jTextField100.setEditable(false);

        jLabel225.setText("Fecha:");

        jDateChooser30.setDateFormatString("yyyy-MM-dd");

        jLabel84.setText("0.00");

        jLabel83.setText("Adeudo Preciso:");

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jLabel77)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField44, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField100, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton15)
                            .addComponent(jButton36, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel18Layout.createSequentialGroup()
                                .addComponent(jLabel78)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField45))
                            .addGroup(jPanel18Layout.createSequentialGroup()
                                .addComponent(jLabel79)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField46, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 44, Short.MAX_VALUE))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel225)
                            .addComponent(jLabel82)
                            .addComponent(jLabel80)
                            .addComponent(jLabel83))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel18Layout.createSequentialGroup()
                                .addComponent(jTextField47)
                                .addGap(12, 12, 12))
                            .addComponent(jTextField48, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel84, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jDateChooser30, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel81)
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel77)
                    .addComponent(jTextField44, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel81)
                    .addComponent(jTextField100, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel78)
                            .addComponent(jTextField45, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel79)
                            .addComponent(jTextField46, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel80)
                            .addComponent(jTextField47, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel82)
                            .addComponent(jTextField48, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel225, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jDateChooser30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel84)
                            .addComponent(jLabel83))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton36)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton19))
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel24.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Abono a Folio"));

        jLabel97.setText("Folio:");

        jLabel98.setText("Monto:");

        jTextField56.setEditable(false);

        jLabel99.setText("Abono:");

        jLabel100.setText("Total Abonado:");

        jTextField58.setEditable(false);

        jLabel101.setText("Adeudo:");

        jTextField59.setEditable(false);

        jComboBox12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox12ActionPerformed(evt);
            }
        });

        jLabel102.setText("Adeudo Preciso:");

        jLabel103.setText("0.00");

        jLabel104.setText("HISTORIAL DE ABONOS");

        jTable9.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Factura", "Monto", "Abonado", "Compania"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane12.setViewportView(jTable9);
        if (jTable9.getColumnModel().getColumnCount() > 0) {
            jTable9.getColumnModel().getColumn(0).setMinWidth(90);
            jTable9.getColumnModel().getColumn(0).setMaxWidth(90);
            jTable9.getColumnModel().getColumn(1).setMinWidth(120);
            jTable9.getColumnModel().getColumn(1).setMaxWidth(120);
            jTable9.getColumnModel().getColumn(3).setMinWidth(1);
            jTable9.getColumnModel().getColumn(3).setMaxWidth(1);
        }

        jButton23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/pagar.png"))); // NOI18N
        jButton23.setText("Abono / Pago");
        jButton23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton23ActionPerformed(evt);
            }
        });

        jButton37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/modif_status.png"))); // NOI18N
        jButton37.setText("Mod. Status");
        jButton37.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton37ActionPerformed(evt);
            }
        });

        jLabel224.setText("Fecha:");

        jDateChooser29.setDateFormatString("yyyy-MM-dd");

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel97)
                            .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel101)
                                    .addComponent(jLabel100))
                                .addComponent(jLabel99, javax.swing.GroupLayout.Alignment.TRAILING)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel103, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField57)
                            .addComponent(jTextField59)
                            .addComponent(jTextField58)
                            .addGroup(jPanel24Layout.createSequentialGroup()
                                .addComponent(jComboBox12, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(22, 22, 22)
                                .addComponent(jLabel98)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField56, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))))
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel224)
                            .addComponent(jLabel102))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateChooser29, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel104)
                    .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel24Layout.createSequentialGroup()
                            .addComponent(jButton37, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButton23))))
                .addContainerGap())
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel97)
                    .addComponent(jComboBox12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField56, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel98))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel99)
                    .addComponent(jTextField57, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel100)
                    .addComponent(jTextField58, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel101)
                    .addComponent(jTextField59, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel102)
                    .addComponent(jLabel103))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jDateChooser29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel224, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel104)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton37, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton23))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        buttonGroup3.add(jRadioButton8);
        jRadioButton8.setText("Abono/Pago a Folio");
        jRadioButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel9Layout.createSequentialGroup()
                        .addComponent(jRadioButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButton8))
                    .addComponent(jPanel17, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel18, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(97, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jRadioButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jRadioButton8))
                    .addComponent(jRadioButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Depósitos Y Pagos", jPanel9);

        jButton29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/corregir.png"))); // NOI18N
        jButton29.setText("Corregir");
        jButton29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton29ActionPerformed(evt);
            }
        });

        jPanel32.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Fechas"));
        jPanel32.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        jLabel123.setText("Fecha de Facturación ASSIST:");

        jTextField66.setEditable(false);
        jTextField66.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel124.setText("Fecha de Ingreso Paciente:");

        jTextField67.setEditable(false);
        jTextField67.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel125.setText("Fecha de Alta Paciente:");

        jTextField68.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel32Layout.createSequentialGroup()
                        .addComponent(jLabel123)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField66, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel32Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel124)
                            .addComponent(jLabel125))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField67, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                            .addComponent(jTextField68))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel123)
                    .addComponent(jTextField66, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel124)
                    .addComponent(jTextField67, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel125)
                    .addComponent(jTextField68, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 13, Short.MAX_VALUE))
        );

        jPanel33.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Datos Adicionales"));

        jLabel126.setText("No. Empleado:");

        jTextField69.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel127.setText("Nombre:");

        jLabel130.setText("Clave:");

        jTextField71.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel131.setText("URES:");

        jTextField72.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField73.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel132.setText("No. Receta:");

        jTextField74.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel133.setText("No. Pase:");

        jLabel128.setText("Ocupación:");

        jComboBox11.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NINGUNO", "APOYO ADMINISTRATIVO", "AUXILIAR ADMINISTRATIVO", "AUXILIAR DE BIBLIOTECA", "AUXILIAR DE CAMPO", "AUXILIAR DE CLINICA", "AUXILIAR DE IMPRENTA", "AUXILIAR DE MANTENIMIENTO", "AUXILIAR DE SERVICIO", "AUXILIAR DE SERVICIO", "CHOFER DE CONFIANZA", "COORDINADOR", "DESARROLLO DE SISTEMAS", "JEFE DE DEPARTAMENTO", "JEFE DE DEPARTAMENTO ADMINISTRATIVO", "JEFE DE UNIDAD", "JUBILADO DOCENTE", "LOGISTICA", "MEDICO GENERAL", "OFICIAL ADMINISTRATIVO", "OPERADOR DE TRANSPORTE", "PENSIONADO DOCENTE", "PENSIONADO JUBILADO", "PROFESOR INVESTIGADOR ASIGNATURA", "PROFESOR INVESTIGADOR MT", "PROFESOR INVESTIGADOR MT", "PROFESOR INVESTIGADOR TC", "PROFESOR INVESTIGADOR TC", "RECTORIA", "SECRETARIA", "SECRETARIA DE ADMISION", "SECRETARIA DE CONFIANZA", "TECNICO ACADEMICO MT", "TECNICO ACADEMICO TC", "VELADOR" }));

        jLabel129.setText("Parentesco:");

        jComboBox15.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NINGUNO", "CONCUBINA", "ESPOSA", "ESPOSO", "HIJA", "HIJO", "MADRE", "PADRE" }));

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel33Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel132)
                    .addComponent(jLabel131)
                    .addComponent(jLabel126))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField69)
                    .addComponent(jTextField73, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField72, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel33Layout.createSequentialGroup()
                        .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel130)
                            .addComponent(jLabel127))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel33Layout.createSequentialGroup()
                                .addComponent(jTextField71, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel133)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField74, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jTextField70)))
                    .addGroup(jPanel33Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel128)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel129)
                            .addComponent(jComboBox15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(52, Short.MAX_VALUE))
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel126)
                    .addComponent(jTextField69, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel127)
                    .addComponent(jTextField70, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel33Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel131)
                            .addComponent(jTextField72, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel130)
                            .addComponent(jTextField71, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel133)
                            .addComponent(jTextField74, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel33Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel129)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField73, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel132)
                    .addComponent(jLabel128)
                    .addComponent(jComboBox11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jLabel134.setText("Observaciones Factura:");

        jTextArea8.setEditable(false);
        jTextArea8.setColumns(20);
        jTextArea8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextArea8.setLineWrap(true);
        jTextArea8.setRows(5);
        jScrollPane24.setViewportView(jTextArea8);

        jLabel135.setText("Observaciones Internas:");

        jTextArea9.setColumns(20);
        jTextArea9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextArea9.setLineWrap(true);
        jTextArea9.setRows(5);
        jTextArea9.setText("SIN OBSERVACIONES");
        jTextArea9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextArea9KeyTyped(evt);
            }
        });
        jScrollPane25.setViewportView(jTextArea9);

        jButton30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/guardar.png"))); // NOI18N
        jButton30.setText("Guardar");
        jButton30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton30ActionPerformed(evt);
            }
        });

        jPanel34.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Acciones CyC"));

        jLabel136.setText("Folio:");

        jTextField75.setEditable(false);
        jTextField75.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jDateChooser10.setDateFormatString("yyyy-MM-dd");
        jDateChooser10.setEnabled(false);

        jLabel137.setText("Recepción:");

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel34Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel136)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField75))
                    .addGroup(jPanel34Layout.createSequentialGroup()
                        .addComponent(jLabel137)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateChooser10, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 17, Short.MAX_VALUE))
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel136)
                    .addComponent(jTextField75, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jDateChooser10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel137, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel35.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Datos de Factura"));

        jLabel138.setText("Factura:");

        jTextField76.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jTextField76.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField76.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField76ActionPerformed(evt);
            }
        });

        jLabel139.setText("Monto:");

        jTextField77.setEditable(false);
        jTextField77.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel140.setText("Folio de Ingreso:");

        jTextField78.setEditable(false);
        jTextField78.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel141.setText("No. de Paciente:");

        jTextField79.setEditable(false);
        jTextField79.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel142.setText("Aseguradora:");

        jTextField80.setEditable(false);

        jLabel143.setText("Adicional:");

        jTextField81.setHorizontalAlignment(javax.swing.JTextField.LEFT);

        jLabel144.setText("Paciente:");

        jTextField82.setEditable(false);

        jLabel145.setText("Póliza:");

        jLabel146.setText("Siniestro:");

        jLabel147.setText("Afiliación:");

        jTextField85.setEditable(false);

        jLabel148.setText("Deducible:");

        jTextField86.setEditable(false);
        jTextField86.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField86.setText("0");
        jTextField86.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField86KeyReleased(evt);
            }
        });

        jLabel149.setText("Coaseguro:");

        jTextField87.setEditable(false);
        jTextField87.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField87.setText("0");
        jTextField87.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField87KeyReleased(evt);
            }
        });

        jLabel150.setText("Descto. Esp:");

        jTextField88.setEditable(false);
        jTextField88.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField88.setText("0");
        jTextField88.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField88KeyReleased(evt);
            }
        });

        jLabel151.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel151.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel151.setText("Total:");

        jTextField90.setEditable(false);
        jTextField90.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jTextField90.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField90.setText("$0.00");

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel142)
                    .addComponent(jLabel138)
                    .addComponent(jLabel144)
                    .addComponent(jLabel147))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel35Layout.createSequentialGroup()
                        .addComponent(jTextField85, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(jLabel148)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField86, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel149)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField87))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel35Layout.createSequentialGroup()
                        .addComponent(jTextField76, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel139)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField77, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                        .addComponent(jLabel140)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField78, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTextField80, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField82, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel141)
                    .addComponent(jLabel143)
                    .addComponent(jLabel145)
                    .addComponent(jLabel150))
                .addGap(4, 4, 4)
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jTextField79, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField81))
                    .addGroup(jPanel35Layout.createSequentialGroup()
                        .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField88, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
                            .addComponent(jTextField83))
                        .addGap(4, 4, Short.MAX_VALUE)
                        .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel146, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel151, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField84)
                            .addComponent(jTextField90, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel138)
                    .addComponent(jTextField76, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel139)
                    .addComponent(jTextField77, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel140)
                    .addComponent(jTextField78, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel141)
                    .addComponent(jTextField79, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel142)
                    .addComponent(jTextField80, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel143)
                    .addComponent(jTextField81, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel144)
                        .addComponent(jTextField82, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel145)
                        .addComponent(jTextField83, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel146)
                        .addComponent(jTextField84, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel35Layout.createSequentialGroup()
                        .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel147)
                            .addComponent(jTextField85, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel148)
                            .addComponent(jTextField86, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel149)
                            .addComponent(jTextField87, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel35Layout.createSequentialGroup()
                        .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField88, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel150)
                            .addComponent(jLabel151, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField90, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jTextArea10.setColumns(20);
        jTextArea10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextArea10.setLineWrap(true);
        jTextArea10.setRows(5);
        jTextArea10.setText("SIN OBSERVACIONES");
        jTextArea10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextArea10KeyTyped(evt);
            }
        });
        jScrollPane26.setViewportView(jTextArea10);

        jLabel157.setText("Observaciones Adicionales:");

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel31Layout.createSequentialGroup()
                        .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel134)
                            .addGroup(jPanel31Layout.createSequentialGroup()
                                .addComponent(jButton29)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton30))
                            .addComponent(jScrollPane24, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel135)
                            .addComponent(jScrollPane25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(7, 7, 7)
                        .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel31Layout.createSequentialGroup()
                                .addComponent(jLabel157)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel31Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jScrollPane26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(486, 486, 486))))
                    .addGroup(jPanel31Layout.createSequentialGroup()
                        .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel31Layout.createSequentialGroup()
                                .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel31Layout.createSequentialGroup()
                        .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel134)
                            .addComponent(jLabel135))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane24, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane25, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton30)
                            .addComponent(jButton29)))
                    .addGroup(jPanel31Layout.createSequentialGroup()
                        .addComponent(jLabel157)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane26, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                            .addComponent(jPanel34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(82, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Modificar Datos de Factura", jPanel31);

        jLabel214.setText("Factura:");

        jTextField127.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField127.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField127ActionPerformed(evt);
            }
        });

        jTable24.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "FACTURA", "IDCOMP", "COMPAÑIA", "MONTO", "STATUS"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane37.setViewportView(jTable24);
        if (jTable24.getColumnModel().getColumnCount() > 0) {
            jTable24.getColumnModel().getColumn(2).setMinWidth(400);
            jTable24.getColumnModel().getColumn(2).setMaxWidth(400);
            jTable24.getColumnModel().getColumn(4).setMinWidth(55);
            jTable24.getColumnModel().getColumn(4).setMaxWidth(55);
        }

        jButton59.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/provision.png"))); // NOI18N
        jButton59.setText("Provisionar");
        jButton59.setToolTipText("provisiona las facturas");
        jButton59.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton59.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton59ActionPerformed(evt);
            }
        });

        jLabel215.setText("Folio:");

        jTextField128.setEditable(false);
        jTextField128.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel216.setText("Fecha de Recepción:");

        jTextField129.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField129.setText("HOY");

        jButton62.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/corregir.png"))); // NOI18N
        jButton62.setText("Corregir");
        jButton62.setToolTipText("limpia la tabla");
        jButton62.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton62.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton62ActionPerformed(evt);
            }
        });

        jButton63.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cancelar.png"))); // NOI18N
        jButton63.setText("Elim Selec");
        jButton63.setToolTipText("elimina la factura seleccionada");
        jButton63.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton63.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton63ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel50Layout = new javax.swing.GroupLayout(jPanel50);
        jPanel50.setLayout(jPanel50Layout);
        jPanel50Layout.setHorizontalGroup(
            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel50Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel50Layout.createSequentialGroup()
                        .addComponent(jLabel214)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField127, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel216)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField129, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel215)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField128, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane37, javax.swing.GroupLayout.PREFERRED_SIZE, 698, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton59, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton62, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton63, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(195, Short.MAX_VALUE))
        );
        jPanel50Layout.setVerticalGroup(
            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel50Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel50Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton63)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton62)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton59))
                    .addGroup(jPanel50Layout.createSequentialGroup()
                        .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel214)
                            .addComponent(jTextField127, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel215)
                            .addComponent(jTextField128, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel216)
                            .addComponent(jTextField129, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane37, javax.swing.GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Provisión de Facturas", jPanel50);

        javax.swing.GroupLayout capturaLayout = new javax.swing.GroupLayout(captura.getContentPane());
        captura.getContentPane().setLayout(capturaLayout);
        capturaLayout.setHorizontalGroup(
            capturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(capturaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1027, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        capturaLayout.setVerticalGroup(
            capturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(capturaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        SelecFolio.setTitle(".:. Seleccion de Folio Interno .:.");
        SelecFolio.setModal(true);

        jLabel27.setText("Selecciona Folio:");

        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Factura", "Aseguradora", "Monto"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(1).setMinWidth(250);
            jTable1.getColumnModel().getColumn(2).setMinWidth(100);
        }

        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/aceptar.png"))); // NOI18N
        jButton8.setText("Seleccionar");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/aceptar.png"))); // NOI18N
        jButton35.setText("Seleccionar");
        jButton35.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton35ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout SelecFolioLayout = new javax.swing.GroupLayout(SelecFolio.getContentPane());
        SelecFolio.getContentPane().setLayout(SelecFolioLayout);
        SelecFolioLayout.setHorizontalGroup(
            SelecFolioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SelecFolioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(SelecFolioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 505, Short.MAX_VALUE)
                    .addGroup(SelecFolioLayout.createSequentialGroup()
                        .addComponent(jLabel27)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton35)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        SelecFolioLayout.setVerticalGroup(
            SelecFolioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SelecFolioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(SelecFolioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton8)
                    .addComponent(jButton35))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        conss.setTitle(".:. Consultas .:.");
        conss.setModal(true);

        jTabbedPane2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        buttonGroup2.add(jRadioButton1);
        jRadioButton1.setText("Aseguradora");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Datos Adicionales"));

        jLabel67.setText("Compañia:");

        jTextField16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField16ActionPerformed(evt);
            }
        });
        jTextField16.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField16KeyReleased(evt);
            }
        });

        jLabel68.setText("Desde:");

        jDateChooser4.setDateFormatString("yyyy-MM-dd");

        jDateChooser5.setDateFormatString("yyyy-MM-dd");

        jLabel69.setText("Hasta:");

        jLabel73.setText("Estatus:");

        jComboBox9.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "A Firma", "Cancelada", "Devuelta", "En Facturacion", "En Revision", "Entregada", "Especial", "Liquidada", "Por Entregar", "Refacturada", "EspecialUJAT", "En Admision", "En Urgencias" }));

        jButton16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/generar.png"))); // NOI18N
        jButton16.setText("generar");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        jButton41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/email.png"))); // NOI18N
        jButton41.setText("e-mail");
        jButton41.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton41ActionPerformed(evt);
            }
        });

        jLabel175.setText("e-mail:");

        jTextField99.setEditable(false);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel67)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField16))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel13Layout.createSequentialGroup()
                                        .addComponent(jLabel68)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jDateChooser4, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel69))
                                    .addGroup(jPanel13Layout.createSequentialGroup()
                                        .addComponent(jLabel73)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jComboBox9, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jDateChooser5, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel13Layout.createSequentialGroup()
                                        .addComponent(jButton16)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton41))))
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addComponent(jLabel175)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField99, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel67)
                    .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jDateChooser4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel68, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jDateChooser5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel69, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel73)
                    .addComponent(jComboBox9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton16)
                    .addComponent(jButton41))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel175)
                    .addComponent(jTextField99, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 11, Short.MAX_VALUE))
        );

        buttonGroup2.add(jRadioButton2);
        jRadioButton2.setText("Factura");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        jTextField43.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField43ActionPerformed(evt);
            }
        });

        buttonGroup2.add(jRadioButton7);
        jRadioButton7.setText("Paciente");
        jRadioButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton7ActionPerformed(evt);
            }
        });

        jPanel23.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Estado de Cuenta por Numero de Paciente"));

        jLabel89.setText("No. Paciente:");

        jTextField28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField28ActionPerformed(evt);
            }
        });

        jTable12.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Factura", "Compañia", "Monto"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable12MouseClicked(evt);
            }
        });
        jTable12.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTable12KeyReleased(evt);
            }
        });
        jScrollPane15.setViewportView(jTable12);
        if (jTable12.getColumnModel().getColumnCount() > 0) {
            jTable12.getColumnModel().getColumn(1).setHeaderValue("Compañia");
            jTable12.getColumnModel().getColumn(2).setMinWidth(100);
            jTable12.getColumnModel().getColumn(2).setMaxWidth(100);
            jTable12.getColumnModel().getColumn(2).setHeaderValue("Monto");
        }

        jButton27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/generar.png"))); // NOI18N
        jButton27.setText("generar");
        jButton27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton27ActionPerformed(evt);
            }
        });

        jTable16.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Status", "Observaciones"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane23.setViewportView(jTable16);
        if (jTable16.getColumnModel().getColumnCount() > 0) {
            jTable16.getColumnModel().getColumn(0).setMinWidth(90);
            jTable16.getColumnModel().getColumn(0).setMaxWidth(90);
        }

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane23)
                    .addComponent(jScrollPane15, javax.swing.GroupLayout.DEFAULT_SIZE, 505, Short.MAX_VALUE)
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addComponent(jLabel89)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField28, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton27)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel89)
                    .addComponent(jTextField28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton27))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane15, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane23, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel93.setText("Número de Factura:");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel93)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField43))
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jRadioButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRadioButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButton7)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField112, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(612, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2)
                    .addComponent(jRadioButton7))
                .addGap(18, 18, 18)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel93)
                    .addComponent(jTextField43, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField112, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25))))
        );

        jTabbedPane2.addTab("Estado de Cuenta", jPanel15);

        jPanel38.setBorder(javax.swing.BorderFactory.createTitledBorder("Rastreo"));

        jLabel63.setText("Factura:");

        jTextField40.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField40.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField40ActionPerformed(evt);
            }
        });

        jLabel64.setText("Dias de Estancia:");

        jTextField41.setEditable(false);

        jLabel65.setText("Dias Antes de Facturar:");

        jTextField49.setEditable(false);

        jLabel66.setText("Facturación - CXC:");

        jTextField50.setEditable(false);

        jLabel70.setText("CXC - Envío a Cobro:");

        jTextField51.setEditable(false);

        jLabel71.setText("Retardo en Liquidar:");

        jTextField52.setEditable(false);

        jCheckBox14.setText("Exportar Estadística a Excel");
        jCheckBox14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox14ActionPerformed(evt);
            }
        });

        jPanel51.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel217.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel217.setText("FECHA DE RECEPCIÓN");

        jLabel218.setText("Desde:");

        jDateChooser27.setDateFormatString("yyyy-MM-dd");

        jDateChooser28.setDateFormatString("yyyy-MM-dd");

        jLabel219.setText("Hasta:");

        jButton60.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/excel.png"))); // NOI18N
        jButton60.setText("Exportar");
        jButton60.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton60ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel51Layout = new javax.swing.GroupLayout(jPanel51);
        jPanel51.setLayout(jPanel51Layout);
        jPanel51Layout.setHorizontalGroup(
            jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel51Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel51Layout.createSequentialGroup()
                        .addComponent(jLabel217)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel51Layout.createSequentialGroup()
                        .addGroup(jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel218)
                            .addComponent(jLabel219))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jDateChooser28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jDateChooser27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel51Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton60)))
                .addContainerGap())
        );
        jPanel51Layout.setVerticalGroup(
            jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel51Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel217)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jDateChooser27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel218, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jDateChooser28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel219, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton60)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel38Layout = new javax.swing.GroupLayout(jPanel38);
        jPanel38.setLayout(jPanel38Layout);
        jPanel38Layout.setHorizontalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel38Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel51, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel38Layout.createSequentialGroup()
                        .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel38Layout.createSequentialGroup()
                                .addComponent(jLabel63)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField40, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel38Layout.createSequentialGroup()
                                    .addGap(34, 34, 34)
                                    .addComponent(jLabel64)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jTextField41, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel38Layout.createSequentialGroup()
                                    .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel66)
                                        .addComponent(jLabel65)
                                        .addComponent(jLabel70)
                                        .addComponent(jLabel71))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jTextField49, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextField50, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextField51, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextField52, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jCheckBox14))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel38Layout.setVerticalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel38Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel63)
                    .addComponent(jTextField40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel64)
                    .addComponent(jTextField41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel65)
                    .addComponent(jTextField49, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel66)
                    .addComponent(jTextField50, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel70)
                    .addComponent(jTextField51, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel71)
                    .addComponent(jTextField52, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jCheckBox14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel51, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel39.setBorder(javax.swing.BorderFactory.createTitledBorder("Antigüedad"));

        jTable18.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jTable18.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Compañia", "Factura", "Entrega", "Monto", "Alta", "Venc", "30D", "60D", "120D", "365D", "720D", ">720D", "SaldoV", "SaldoP"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane29.setViewportView(jTable18);
        if (jTable18.getColumnModel().getColumnCount() > 0) {
            jTable18.getColumnModel().getColumn(0).setMinWidth(150);
            jTable18.getColumnModel().getColumn(0).setMaxWidth(150);
            jTable18.getColumnModel().getColumn(1).setMinWidth(50);
            jTable18.getColumnModel().getColumn(1).setMaxWidth(50);
            jTable18.getColumnModel().getColumn(2).setMinWidth(65);
            jTable18.getColumnModel().getColumn(2).setMaxWidth(65);
            jTable18.getColumnModel().getColumn(3).setMinWidth(60);
            jTable18.getColumnModel().getColumn(3).setMaxWidth(60);
            jTable18.getColumnModel().getColumn(4).setMinWidth(40);
            jTable18.getColumnModel().getColumn(4).setMaxWidth(40);
            jTable18.getColumnModel().getColumn(5).setMinWidth(40);
            jTable18.getColumnModel().getColumn(5).setMaxWidth(40);
        }

        jLabel153.setText("Periodo:");

        jDateChooser20.setDateFormatString("yyyy-MM-dd");

        jDateChooser21.setDateFormatString("yyyy-MM-dd");

        jButton31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/extraer.png"))); // NOI18N
        jButton31.setText("Extraer");
        jButton31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton31ActionPerformed(evt);
            }
        });

        jButton32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/reporte.png"))); // NOI18N
        jButton32.setText("Reporte");
        jButton32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton32ActionPerformed(evt);
            }
        });

        jComboBox17.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "fecha_status", "fact_assist" }));

        javax.swing.GroupLayout jPanel39Layout = new javax.swing.GroupLayout(jPanel39);
        jPanel39.setLayout(jPanel39Layout);
        jPanel39Layout.setHorizontalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel39Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane29, javax.swing.GroupLayout.DEFAULT_SIZE, 965, Short.MAX_VALUE)
                    .addGroup(jPanel39Layout.createSequentialGroup()
                        .addComponent(jLabel153)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateChooser20, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateChooser21, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel39Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton31)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton32)))
                .addContainerGap())
        );
        jPanel39Layout.setVerticalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel39Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jDateChooser21, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel153, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jDateChooser20, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jComboBox17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane29, javax.swing.GroupLayout.DEFAULT_SIZE, 495, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton31)
                    .addComponent(jButton32))
                .addContainerGap())
        );

        jCheckBox2.setText("Antigüedad de Saldos");
        jCheckBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox2ActionPerformed(evt);
            }
        });

        jCheckBox13.setText("Reporte de Facturas Liquidadas");
        jCheckBox13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox13ActionPerformed(evt);
            }
        });

        jPanel49.setBorder(javax.swing.BorderFactory.createTitledBorder("Reporte"));

        jLabel212.setText("Desde:");

        jDateChooser25.setDateFormatString("yyyy-MM-dd");

        jLabel213.setText("Hasta:");

        jDateChooser26.setDateFormatString("yyyy-MM-dd");

        jButton58.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/reporte.png"))); // NOI18N
        jButton58.setText("Reporte");
        jButton58.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton58ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel49Layout = new javax.swing.GroupLayout(jPanel49);
        jPanel49.setLayout(jPanel49Layout);
        jPanel49Layout.setHorizontalGroup(
            jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel49Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel49Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton58))
                    .addGroup(jPanel49Layout.createSequentialGroup()
                        .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel212)
                            .addComponent(jLabel213))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jDateChooser26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jDateChooser25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel49Layout.setVerticalGroup(
            jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel49Layout.createSequentialGroup()
                .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jDateChooser25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel212, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jDateChooser26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel213, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton58)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jCheckBox2)
                    .addComponent(jCheckBox13)
                    .addComponent(jPanel49, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jPanel38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel49, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane2.addTab("Estadistica", jPanel11);

        jPanel19.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Compañia", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jLabel85.setText("Aseguradora:");

        jTextField53.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField53ActionPerformed(evt);
            }
        });

        jLabel86.setText("Desde:");

        jDateChooser2.setDateFormatString("yyyy-MM-dd");

        jLabel87.setText("Hasta:");

        jDateChooser3.setDateFormatString("yyyy-MM-dd");

        jButton20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/excel.png"))); // NOI18N
        jButton20.setText("Generar");
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addComponent(jLabel85)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField53))
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addComponent(jLabel86)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel87)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateChooser3, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton20)
                        .addGap(0, 10, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel85)
                    .addComponent(jTextField53, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jDateChooser2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel86, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel87, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jDateChooser3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton20))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        buttonGroup4.add(jRadioButton5);
        jRadioButton5.setText("Compañia");
        jRadioButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton5ActionPerformed(evt);
            }
        });

        buttonGroup4.add(jRadioButton6);
        jRadioButton6.setText("Folio / Parámetros");
        jRadioButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton6ActionPerformed(evt);
            }
        });

        jPanel36.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Folio / Parámetros", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jComboBox13.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Todos", "A Firma", "Cancelada", "Devuelta", "En Facturacion", "En Revision", "Entregada", "Especial", "Liquidada", "Por Entregar", "Refacturada", "EspecialUJAT", "En Admision", "En Urgencias" }));
        jComboBox13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox13ActionPerformed(evt);
            }
        });

        jLabel40.setText("Folio:");

        jLabel152.setText("Estatus:");

        jLabel158.setText("Fecha:");

        jComboBox16.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Todas", "Periodo" }));
        jComboBox16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox16ActionPerformed(evt);
            }
        });

        jPanel37.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jDateChooser11.setDateFormatString("yyyy-MM-dd");

        jLabel159.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel159.setText("Desde:");

        jLabel160.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel160.setText("Hasta:");

        jDateChooser14.setDateFormatString("yyyy-MM-dd");

        javax.swing.GroupLayout jPanel37Layout = new javax.swing.GroupLayout(jPanel37);
        jPanel37.setLayout(jPanel37Layout);
        jPanel37Layout.setHorizontalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel160, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel159, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel37Layout.createSequentialGroup()
                        .addComponent(jDateChooser11, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jDateChooser14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel37Layout.setVerticalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel37Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel159, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jDateChooser11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel160, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jDateChooser14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jButton33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/excel.png"))); // NOI18N
        jButton33.setText("Generar");
        jButton33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton33ActionPerformed(evt);
            }
        });

        jButton34.setText("OK");
        jButton34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton34ActionPerformed(evt);
            }
        });

        jSeparator3.setForeground(new java.awt.Color(0, 0, 0));

        jCheckBox3.setText("Especial Dictamen");

        javax.swing.GroupLayout jPanel36Layout = new javax.swing.GroupLayout(jPanel36);
        jPanel36.setLayout(jPanel36Layout);
        jPanel36Layout.setHorizontalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel36Layout.createSequentialGroup()
                        .addComponent(jLabel40)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel152)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton34))
                    .addGroup(jPanel36Layout.createSequentialGroup()
                        .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel36Layout.createSequentialGroup()
                                .addComponent(jLabel158)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton33))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCheckBox3)
                            .addComponent(jPanel37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(jSeparator3)
        );
        jPanel36Layout.setVerticalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel40)
                    .addComponent(jLabel152)
                    .addComponent(jButton34))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel36Layout.createSequentialGroup()
                        .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel158)
                            .addComponent(jComboBox16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton33))
                    .addComponent(jPanel37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBox3)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jRadioButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButton6))
                    .addComponent(jPanel36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(744, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton5)
                    .addComponent(jRadioButton6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(274, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Contrarecibos Especiales", jPanel12);

        jLabel105.setText("Folio (No. de Paciente):");

        jTextField55.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField55ActionPerformed(evt);
            }
        });

        jTable10.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Factura", "Ext", "Fecha Fact", "User", "Status", "Nueva Fact"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane13.setViewportView(jTable10);
        if (jTable10.getColumnModel().getColumnCount() > 0) {
            jTable10.getColumnModel().getColumn(0).setMinWidth(100);
            jTable10.getColumnModel().getColumn(0).setMaxWidth(100);
            jTable10.getColumnModel().getColumn(1).setMinWidth(40);
            jTable10.getColumnModel().getColumn(1).setMaxWidth(40);
            jTable10.getColumnModel().getColumn(2).setMinWidth(100);
            jTable10.getColumnModel().getColumn(2).setMaxWidth(100);
            jTable10.getColumnModel().getColumn(3).setMinWidth(60);
            jTable10.getColumnModel().getColumn(3).setMaxWidth(60);
            jTable10.getColumnModel().getColumn(4).setMinWidth(50);
            jTable10.getColumnModel().getColumn(4).setMaxWidth(50);
            jTable10.getColumnModel().getColumn(5).setMinWidth(100);
            jTable10.getColumnModel().getColumn(5).setMaxWidth(100);
        }

        jPanel26.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Lectura de Información"));

        jLabel106.setText("Ext  0 = Cuenta de Particular");

        jLabel107.setText("Ext  1 = Cuenta de Compañia");

        jLabel108.setText("Status  X = Factura Cancelada");

        jLabel109.setText("Status  F = Factura Activa");

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel109, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                    .addComponent(jLabel108, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                    .addComponent(jLabel106, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel107, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel106, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel107)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel108, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel109, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel110.setText("NOTA: ésta información es extraída de la base de datos del sistema Assist");

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel25Layout.createSequentialGroup()
                        .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel25Layout.createSequentialGroup()
                                .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jLabel110, javax.swing.GroupLayout.DEFAULT_SIZE, 771, Short.MAX_VALUE)))
                    .addGroup(jPanel25Layout.createSequentialGroup()
                        .addComponent(jLabel105)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField55, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel105)
                    .addComponent(jTextField55, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 579, Short.MAX_VALUE)
                    .addGroup(jPanel25Layout.createSequentialGroup()
                        .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel110)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jTabbedPane2.addTab("Rastreo de Facturas", jPanel25);

        jButton28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/conciliar.png"))); // NOI18N
        jButton28.setText("Conciliar");
        jButton28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton28ActionPerformed(evt);
            }
        });

        jTable14.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ASSIST", "CXC"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane17.setViewportView(jTable14);

        jTable15.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "FALTA", "ST", "FECHA", "COMPAÑIA", "MONTO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane18.setViewportView(jTable15);
        if (jTable15.getColumnModel().getColumnCount() > 0) {
            jTable15.getColumnModel().getColumn(1).setMinWidth(25);
            jTable15.getColumnModel().getColumn(1).setMaxWidth(25);
            jTable15.getColumnModel().getColumn(2).setMinWidth(85);
            jTable15.getColumnModel().getColumn(2).setMaxWidth(85);
            jTable15.getColumnModel().getColumn(3).setMinWidth(450);
            jTable15.getColumnModel().getColumn(3).setMaxWidth(450);
            jTable15.getColumnModel().getColumn(4).setMinWidth(80);
            jTable15.getColumnModel().getColumn(4).setMaxWidth(80);
        }

        jLabel96.setText("Analizadas:");

        jTextField64.setEditable(false);
        jTextField64.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel116.setText("Coincidentes:");

        jTextField65.setEditable(false);
        jTextField65.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextArea4.setEditable(false);
        jTextArea4.setColumns(20);
        jTextArea4.setLineWrap(true);
        jTextArea4.setRows(5);
        jScrollPane19.setViewportView(jTextArea4);

        jLabel161.setText("Fecha de Facturación ASSIST:");

        jDateChooser15.setDateFormatString("yyyy-MM-dd");

        jDateChooser16.setDateFormatString("yyyy-MM-dd");

        jLabel163.setText("-");

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane17, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jLabel161, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addComponent(jDateChooser15, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel163, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateChooser16, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton28))
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addComponent(jScrollPane18, javax.swing.GroupLayout.PREFERRED_SIZE, 713, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel22Layout.createSequentialGroup()
                                .addComponent(jLabel96)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField64, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel116)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField65, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane19, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton28))
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel161, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jDateChooser15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jDateChooser16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel163, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addComponent(jScrollPane19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel96)
                            .addComponent(jTextField64, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel116)
                            .addComponent(jTextField65, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane18, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 565, Short.MAX_VALUE)
                    .addComponent(jScrollPane17, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap())
        );

        jTabbedPane2.addTab("Conciliación", jPanel22);

        jLabel164.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel164.setText("Desde:");

        jDateChooser17.setDateFormatString("yyyy-MM-dd");

        jLabel165.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel165.setText("Hasta:");

        jDateChooser18.setDateFormatString("yyyy-MM-dd");

        jButton38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/aceptar.png"))); // NOI18N
        jButton38.setText("OK");
        jButton38.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton38ActionPerformed(evt);
            }
        });

        jTable17.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Compañia", "Monto Total", "Pagado", "Adeudo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane28.setViewportView(jTable17);
        if (jTable17.getColumnModel().getColumnCount() > 0) {
            jTable17.getColumnModel().getColumn(1).setMinWidth(100);
            jTable17.getColumnModel().getColumn(1).setMaxWidth(100);
            jTable17.getColumnModel().getColumn(2).setMinWidth(100);
            jTable17.getColumnModel().getColumn(2).setMaxWidth(100);
            jTable17.getColumnModel().getColumn(3).setMinWidth(100);
            jTable17.getColumnModel().getColumn(3).setMaxWidth(100);
        }

        jButton39.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/imprimereportes.png"))); // NOI18N
        jButton39.setText("Imprimir Reporte");
        jButton39.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton39ActionPerformed(evt);
            }
        });

        jCheckBox1.setText("Reporte Por Estatus");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        jCheckBox12.setText("Mostrar Tabla");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane28)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(jLabel164)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateChooser17, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel165)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateChooser18, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton38)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton39)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox12)
                        .addGap(0, 433, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton38)
                        .addComponent(jButton39)
                        .addComponent(jCheckBox1)
                        .addComponent(jCheckBox12))
                    .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel165, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jDateChooser18, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel164, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jDateChooser17, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 64, Short.MAX_VALUE)
                .addComponent(jScrollPane28, javax.swing.GroupLayout.PREFERRED_SIZE, 508, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Concentrado", jPanel16);

        jLabel196.setText("Rango de Fechas:");

        jDateChooser23.setDateFormatString("yyyy-MM-dd");

        jDateChooser24.setDateFormatString("yyyy-MM-dd");

        jButton50.setText("OK");
        jButton50.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton50ActionPerformed(evt);
            }
        });

        jTable20.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Compañia", "Entregadas", "Liquidadas", "Porcentaje"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane33.setViewportView(jTable20);
        if (jTable20.getColumnModel().getColumnCount() > 0) {
            jTable20.getColumnModel().getColumn(0).setMinWidth(500);
            jTable20.getColumnModel().getColumn(0).setMaxWidth(500);
        }

        jButton51.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/reporte.png"))); // NOI18N
        jButton51.setText("Reporte");
        jButton51.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton51ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel42Layout = new javax.swing.GroupLayout(jPanel42);
        jPanel42.setLayout(jPanel42Layout);
        jPanel42Layout.setHorizontalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel42Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel42Layout.createSequentialGroup()
                        .addComponent(jScrollPane33, javax.swing.GroupLayout.PREFERRED_SIZE, 714, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton51))
                    .addGroup(jPanel42Layout.createSequentialGroup()
                        .addComponent(jLabel196)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateChooser23, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateChooser24, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton50)))
                .addContainerGap(420, Short.MAX_VALUE))
        );
        jPanel42Layout.setVerticalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel42Layout.createSequentialGroup()
                .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel42Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton51))
                    .addGroup(jPanel42Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel196, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jDateChooser23, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jDateChooser24, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jButton50))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane33, javax.swing.GroupLayout.DEFAULT_SIZE, 573, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jTabbedPane2.addTab("Reporte Porcentual", jPanel42);

        javax.swing.GroupLayout conssLayout = new javax.swing.GroupLayout(conss.getContentPane());
        conss.getContentPane().setLayout(conssLayout);
        conssLayout.setHorizontalGroup(
            conssLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2)
        );
        conssLayout.setVerticalGroup(
            conssLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(conssLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2))
        );

        companiacaptura.setTitle(".:. Seleccione Compañia .:.");
        companiacaptura.setModal(true);

        jButton10.setText("Seleccionar");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Compañia", "No. Documento"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(jTable2);
        if (jTable2.getColumnModel().getColumnCount() > 0) {
            jTable2.getColumnModel().getColumn(0).setMinWidth(300);
            jTable2.getColumnModel().getColumn(0).setMaxWidth(300);
        }

        jLabel62.setText("Palabra:");

        jTextField39.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField39KeyReleased(evt);
            }
        });

        javax.swing.GroupLayout companiacapturaLayout = new javax.swing.GroupLayout(companiacaptura.getContentPane());
        companiacaptura.getContentPane().setLayout(companiacapturaLayout);
        companiacapturaLayout.setHorizontalGroup(
            companiacapturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, companiacapturaLayout.createSequentialGroup()
                .addComponent(jLabel62)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField39)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton10))
            .addGroup(companiacapturaLayout.createSequentialGroup()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 504, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        companiacapturaLayout.setVerticalGroup(
            companiacapturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(companiacapturaLayout.createSequentialGroup()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(companiacapturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton10)
                    .addComponent(jLabel62)
                    .addComponent(jTextField39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        impresion.setTitle(".:. Impresión de Reportes .:.");
        impresion.setModal(true);

        jTabbedPane3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jLabel72.setText("Seleccionar:");

        jComboBox7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox7ActionPerformed(evt);
            }
        });

        jButton14.setText("ver reporte");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        jTable5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Factura", "Aseguradora", "Monto", "Status", "Facturacion"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane8.setViewportView(jTable5);
        if (jTable5.getColumnModel().getColumnCount() > 0) {
            jTable5.getColumnModel().getColumn(1).setMinWidth(250);
            jTable5.getColumnModel().getColumn(2).setMinWidth(100);
            jTable5.getColumnModel().getColumn(3).setMinWidth(150);
            jTable5.getColumnModel().getColumn(3).setMaxWidth(150);
        }

        jButton18.setText("consultar saldos");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });

        jLabel112.setText("Status:");

        jComboBox14.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "A Firma", "Cancelada", "Devuelta", "En Facturacion", "En Revision", "Entregada", "Especial", "Liquidada", "Por Entregar", "Refacturada", "EspecialUJAT", "En Admision", "En Urgencias" }));
        jComboBox14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox14ActionPerformed(evt);
            }
        });

        jButton56.setText("PDF y XML");
        jButton56.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton56ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane8)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel72)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel112)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton56)
                        .addGap(0, 175, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel72)
                    .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton14)
                    .addComponent(jButton18)
                    .addComponent(jLabel112)
                    .addComponent(jComboBox14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton56))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane3.addTab("Contrarecibos", jPanel14);

        jLabel113.setText("Periodo:");

        jLabel114.setText("Desde:");

        jDateChooser12.setDateFormatString("yyyy-MM-dd");

        jDateChooser13.setDateFormatString("yyyy-MM-dd");

        jLabel115.setText("Hasta:");

        jButton25.setText("Reporte");
        jButton25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton25ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel113)
                    .addGroup(jPanel27Layout.createSequentialGroup()
                        .addComponent(jLabel114)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateChooser12, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel115)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateChooser13, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton25)))
                .addContainerGap(337, Short.MAX_VALUE))
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton25)
                    .addGroup(jPanel27Layout.createSequentialGroup()
                        .addComponent(jLabel113)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jDateChooser12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel114, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel115, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jDateChooser13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(482, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Reporte Estadístico", jPanel27);

        javax.swing.GroupLayout impresionLayout = new javax.swing.GroupLayout(impresion.getContentPane());
        impresion.getContentPane().setLayout(impresionLayout);
        impresionLayout.setHorizontalGroup(
            impresionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(impresionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane3)
                .addContainerGap())
        );
        impresionLayout.setVerticalGroup(
            impresionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(impresionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane3)
                .addContainerGap())
        );

        selecaseg.setTitle(".:. Aseguradora .:.");
        selecaseg.setModal(true);

        jTable6.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cliente", "Aseguradora", "Email"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable6KeyPressed(evt);
            }
        });
        jScrollPane9.setViewportView(jTable6);
        if (jTable6.getColumnModel().getColumnCount() > 0) {
            jTable6.getColumnModel().getColumn(0).setMinWidth(75);
            jTable6.getColumnModel().getColumn(0).setMaxWidth(75);
            jTable6.getColumnModel().getColumn(2).setMinWidth(170);
            jTable6.getColumnModel().getColumn(2).setMaxWidth(170);
        }

        jLabel74.setText("Búsqueda:");

        jTextField17.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField17KeyReleased(evt);
            }
        });

        javax.swing.GroupLayout selecasegLayout = new javax.swing.GroupLayout(selecaseg.getContentPane());
        selecaseg.getContentPane().setLayout(selecasegLayout);
        selecasegLayout.setHorizontalGroup(
            selecasegLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(selecasegLayout.createSequentialGroup()
                .addGroup(selecasegLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, selecasegLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel74)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField17))
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 659, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        selecasegLayout.setVerticalGroup(
            selecasegLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(selecasegLayout.createSequentialGroup()
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 435, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(selecasegLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel74)
                    .addComponent(jTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTable7.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Folio"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane10.setViewportView(jTable7);

        javax.swing.GroupLayout tablafoliosLayout = new javax.swing.GroupLayout(tablafolios.getContentPane());
        tablafolios.getContentPane().setLayout(tablafoliosLayout);
        tablafoliosLayout.setHorizontalGroup(
            tablafoliosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        tablafoliosLayout.setVerticalGroup(
            tablafoliosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        contraesp.setTitle(".:. Contrarecibos Especiales .:.");
        contraesp.setModal(true);

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "numfactura", "UUID", "monto_total", "folio_ingreso", "num_paciente", "compania", "numcompania", "adicional", "pac_nombre", "poliza", "siniestro", "afiliacion", "deducible", "coaseguro", "descto_esp", "fact_assist", "f_ingreso", "f_alta", "obs_factura", "obs_cyc", "empleado", "nombre_emp", "ocupacion_emp", "parentesco", "clave", "ures", "receta", "pase", "folio_interno", "fecha_liquida", "dias_liquida", "status_factura", "stamp_bitacora", "fecha_recepcion", "fecha_refact", "factura_sust", "elaboro_user", "cama", "fecha_entrega"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane6.setViewportView(jTable3);

        javax.swing.GroupLayout contraespLayout = new javax.swing.GroupLayout(contraesp.getContentPane());
        contraesp.getContentPane().setLayout(contraespLayout);
        contraespLayout.setHorizontalGroup(
            contraespLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contraespLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 1154, Short.MAX_VALUE)
                .addContainerGap())
        );
        contraespLayout.setVerticalGroup(
            contraespLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contraespLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 578, Short.MAX_VALUE)
                .addContainerGap())
        );

        buscapaciente.setTitle(".:. Búsqueda de Pacientes .:.");
        buscapaciente.setModal(true);

        jLabel111.setText("Nombre:");

        jTextField60.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField60KeyReleased(evt);
            }
        });

        jTable11.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "No. Paciente"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane14.setViewportView(jTable11);
        if (jTable11.getColumnModel().getColumnCount() > 0) {
            jTable11.getColumnModel().getColumn(0).setMinWidth(350);
            jTable11.getColumnModel().getColumn(0).setMaxWidth(350);
        }

        jButton24.setText("insertar");
        jButton24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton24ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout buscapacienteLayout = new javax.swing.GroupLayout(buscapaciente.getContentPane());
        buscapaciente.getContentPane().setLayout(buscapacienteLayout);
        buscapacienteLayout.setHorizontalGroup(
            buscapacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buscapacienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(buscapacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(buscapacienteLayout.createSequentialGroup()
                        .addComponent(jLabel111)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField60, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton24))
                    .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        buscapacienteLayout.setVerticalGroup(
            buscapacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buscapacienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(buscapacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel111)
                    .addComponent(jTextField60, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton24))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        cambiafactura.setTitle(".:. Transferencia de Facturas .:.");

        jLabel90.setText("Factura:");

        jTextField61.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField61.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField61ActionPerformed(evt);
            }
        });

        jLabel91.setText("Cambiar a Folio:");

        jButton21.setText("Nuevo");
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21ActionPerformed(evt);
            }
        });

        jLabel92.setText("Folio Actual:");

        jTextField62.setEditable(false);
        jTextField62.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jButton22.setText("OK");
        jButton22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton22ActionPerformed(evt);
            }
        });

        jButton26.setText("Nueva Factura");
        jButton26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton26ActionPerformed(evt);
            }
        });

        jTable13.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Folio", "Compañia"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane16.setViewportView(jTable13);
        if (jTable13.getColumnModel().getColumnCount() > 0) {
            jTable13.getColumnModel().getColumn(1).setMinWidth(350);
            jTable13.getColumnModel().getColumn(1).setMaxWidth(350);
        }

        javax.swing.GroupLayout cambiafacturaLayout = new javax.swing.GroupLayout(cambiafactura.getContentPane());
        cambiafactura.getContentPane().setLayout(cambiafacturaLayout);
        cambiafacturaLayout.setHorizontalGroup(
            cambiafacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cambiafacturaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(cambiafacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(cambiafacturaLayout.createSequentialGroup()
                        .addGroup(cambiafacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(cambiafacturaLayout.createSequentialGroup()
                                .addComponent(jLabel91)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox10, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(cambiafacturaLayout.createSequentialGroup()
                                .addComponent(jLabel90)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField61, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(cambiafacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(cambiafacturaLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton21))
                            .addGroup(cambiafacturaLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel92)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField62, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(cambiafacturaLayout.createSequentialGroup()
                        .addComponent(jButton22, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton26))
                    .addComponent(jScrollPane16, javax.swing.GroupLayout.PREFERRED_SIZE, 418, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        cambiafacturaLayout.setVerticalGroup(
            cambiafacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cambiafacturaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(cambiafacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel90)
                    .addComponent(jTextField61, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel92)
                    .addComponent(jTextField62, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(cambiafacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel91)
                    .addComponent(jComboBox10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton21))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane16, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(cambiafacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton26)
                    .addComponent(jButton22))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        conf_mail.setTitle(".:. Configuracion de E-Mail .:.");
        conf_mail.setModal(true);

        jLabel168.setText("Cuenta:");

        jLabel169.setText("Passwd:");

        jLabel170.setText("Puerto:");

        jLabel171.setText("SMTP Host:");

        jButton46.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/guardar.png"))); // NOI18N
        jButton46.setText("Grabar");
        jButton46.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton46ActionPerformed(evt);
            }
        });

        jLabel176.setText("Asunto:");

        jLabel177.setText("Mensaje:");

        jTextArea13.setColumns(20);
        jTextArea13.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jTextArea13.setLineWrap(true);
        jTextArea13.setRows(5);
        jScrollPane32.setViewportView(jTextArea13);

        javax.swing.GroupLayout conf_mailLayout = new javax.swing.GroupLayout(conf_mail.getContentPane());
        conf_mail.getContentPane().setLayout(conf_mailLayout);
        conf_mailLayout.setHorizontalGroup(
            conf_mailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(conf_mailLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(conf_mailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, conf_mailLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton46))
                    .addGroup(conf_mailLayout.createSequentialGroup()
                        .addComponent(jLabel176)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField101))
                    .addGroup(conf_mailLayout.createSequentialGroup()
                        .addGroup(conf_mailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(conf_mailLayout.createSequentialGroup()
                                .addComponent(jLabel168)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField94, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(conf_mailLayout.createSequentialGroup()
                                .addComponent(jLabel169)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPasswordField2)))
                        .addGap(0, 131, Short.MAX_VALUE))
                    .addGroup(conf_mailLayout.createSequentialGroup()
                        .addComponent(jLabel177)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane32))
                    .addGroup(conf_mailLayout.createSequentialGroup()
                        .addComponent(jLabel170)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField95, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel171)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField96)))
                .addContainerGap())
        );
        conf_mailLayout.setVerticalGroup(
            conf_mailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(conf_mailLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(conf_mailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel168)
                    .addComponent(jTextField94, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(conf_mailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel169)
                    .addComponent(jPasswordField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(conf_mailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel170)
                    .addComponent(jTextField95, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel171)
                    .addComponent(jTextField96, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(conf_mailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField101, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel176))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(conf_mailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel177)
                    .addComponent(jScrollPane32, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton46)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        SendMail.setTitle(".:. Enviar Correo Electrónico .:.");
        SendMail.setModal(true);

        jPanel40.setBorder(javax.swing.BorderFactory.createTitledBorder("Enviar Correo Electrónico"));

        buttonGroup5.add(jRadioButton9);
        jRadioButton9.setText("Generar PDF");
        jRadioButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton9ActionPerformed(evt);
            }
        });

        buttonGroup5.add(jRadioButton10);
        jRadioButton10.setText("Buscar Archivo");

        jLabel155.setText("E-Mail:");

        jLabel156.setText("Asunto:");

        jLabel166.setText("Mensaje:");

        jTextArea12.setColumns(20);
        jTextArea12.setRows(5);
        jScrollPane30.setViewportView(jTextArea12);

        jLabel167.setText("Archivo:");

        jTextField93.setEditable(false);

        jButton42.setText("...");
        jButton42.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton42ActionPerformed(evt);
            }
        });

        jButton43.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/enviar.png"))); // NOI18N
        jButton43.setText("Enviar");
        jButton43.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton43ActionPerformed(evt);
            }
        });

        jButton44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cancelar.png"))); // NOI18N
        jButton44.setText("Cancel");
        jButton44.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton44ActionPerformed(evt);
            }
        });

        jTable19.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Estatus"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable19MouseClicked(evt);
            }
        });
        jTable19.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTable19KeyReleased(evt);
            }
        });
        jScrollPane31.setViewportView(jTable19);

        jButton45.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/editar.png"))); // NOI18N
        jButton45.setText("Edicion");
        jButton45.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton45ActionPerformed(evt);
            }
        });

        jLabel172.setText("Nombre del Archivo:");

        javax.swing.GroupLayout jPanel40Layout = new javax.swing.GroupLayout(jPanel40);
        jPanel40.setLayout(jPanel40Layout);
        jPanel40Layout.setHorizontalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane30)
                    .addGroup(jPanel40Layout.createSequentialGroup()
                        .addComponent(jLabel155)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField89, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel156)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField92))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel40Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton45)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton44)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton43))
                    .addComponent(jScrollPane31, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 495, Short.MAX_VALUE)
                    .addGroup(jPanel40Layout.createSequentialGroup()
                        .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel40Layout.createSequentialGroup()
                                .addComponent(jRadioButton9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jRadioButton10))
                            .addComponent(jLabel166))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel40Layout.createSequentialGroup()
                        .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel40Layout.createSequentialGroup()
                                .addComponent(jLabel172)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField97))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel40Layout.createSequentialGroup()
                                .addComponent(jLabel167)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField93)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton42)))
                .addContainerGap())
        );
        jPanel40Layout.setVerticalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton9)
                    .addComponent(jRadioButton10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel155)
                    .addComponent(jTextField89, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel156)
                    .addComponent(jTextField92, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel166)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane30, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel167)
                    .addComponent(jTextField93, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton42))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel172, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField97, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane31, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66)
                .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton43)
                    .addComponent(jButton44)
                    .addComponent(jButton45))
                .addContainerGap())
        );

        javax.swing.GroupLayout SendMailLayout = new javax.swing.GroupLayout(SendMail.getContentPane());
        SendMail.getContentPane().setLayout(SendMailLayout);
        SendMailLayout.setHorizontalGroup(
            SendMailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SendMailLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );
        SendMailLayout.setVerticalGroup(
            SendMailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SendMailLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel40, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        Activacion.setTitle(".:. Activación .:.");

        jLabel178.setText("Licencia:");

        javax.swing.GroupLayout ActivacionLayout = new javax.swing.GroupLayout(Activacion.getContentPane());
        Activacion.getContentPane().setLayout(ActivacionLayout);
        ActivacionLayout.setHorizontalGroup(
            ActivacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ActivacionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel178)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField102, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)
                .addContainerGap())
        );
        ActivacionLayout.setVerticalGroup(
            ActivacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ActivacionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ActivacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel178)
                    .addComponent(jTextField102, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(269, Short.MAX_VALUE))
        );

        datos_ujat.setTitle(".:. Datos Adicionales UJAT .:.");
        datos_ujat.setModal(true);

        jPanel41.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Paciente", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jLabel179.setText("Folio:");

        jTextField103.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField103ActionPerformed(evt);
            }
        });

        jLabel180.setText("Nombre:");

        jTextField104.setEditable(false);

        jLabel181.setText("Parentesco Con Resp:");

        jComboBox18.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ninguno", "Concubina", "Esposa", "Esposo", "Hija", "Hijo", "Madre", "Padre", "Viuda", "Viudo" }));

        jLabel182.setText("Categoria:");

        jLabel183.setText("Adscripción:");

        jLabel184.setText("Ficha:");

        jLabel185.setText("Domicilio:");

        jLabel186.setText("Teléfono:");

        jComboBox21.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ninguno", "Apoyo Administrativo", "Auxiliar Administrativo", "Auxiliar de Biblioteca", "Auxiliar de Campo", "Auxiliar de Clinica", "Auxiliar de Imprenta", "Auxiliar de Mantenimiento", "Auxiliar de Laboratorio", "Auxiliar de Servicio", "Chofer de Confianza", "Coordinador", "Desarrollo de Sistemas", "Jefe de Departamento", "Jefe de Departamento Administrativo", "Jefe de Unidad", "Jubilado Docente", "Logistica", "Medico General", "Oficial Administrativo", "Operador de Transporte", "Pensionado Docente", "Pensionado Jubilado", "Profesor Investigador Asignatura", "Profesor Investigador MT", "Profesor Investigador TC", "Rectoria", "Secretaria", "Secretaria de Admision", "Secretaria de Confianza", "Tecnico Academico MT", "Tecnico Academico TC", "Velador" }));

        javax.swing.GroupLayout jPanel41Layout = new javax.swing.GroupLayout(jPanel41);
        jPanel41.setLayout(jPanel41Layout);
        jPanel41Layout.setHorizontalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel41Layout.createSequentialGroup()
                        .addComponent(jLabel185)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField108))
                    .addGroup(jPanel41Layout.createSequentialGroup()
                        .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel41Layout.createSequentialGroup()
                                .addComponent(jLabel179)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField103, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel41Layout.createSequentialGroup()
                                .addComponent(jLabel180)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField104, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel181)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel41Layout.createSequentialGroup()
                                .addComponent(jLabel186)
                                .addGap(2, 2, 2)
                                .addComponent(jTextField109, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel41Layout.createSequentialGroup()
                        .addComponent(jLabel182)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel183)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField105, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel184)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField107)))
                .addContainerGap())
        );
        jPanel41Layout.setVerticalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel179)
                    .addComponent(jTextField103, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel180)
                    .addComponent(jTextField104, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel181)
                    .addComponent(jComboBox18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel182)
                    .addComponent(jTextField105, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel183)
                    .addComponent(jLabel184)
                    .addComponent(jTextField107, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel185)
                    .addComponent(jTextField108, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel186)
                    .addComponent(jTextField109, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jPanel43.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Responsable", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jLabel187.setText("Nombre:");

        jLabel188.setText("Parentesco Con Paciente:");

        jComboBox19.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ninguno", "Concubina", "Esposa", "Esposo", "Hija", "Hijo", "Madre", "Padre", "Viuda", "Viudo" }));

        jLabel189.setText("Categoria:");

        jLabel190.setText("Adscripcion:");

        jLabel191.setText("Ficha:");

        jLabel192.setText("Domicilio:");

        jLabel193.setText("Teléfono:");

        jLabel194.setText("Documento de Identificación:");

        jComboBox20.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ninguno", "Apoyo Administrativo", "Auxiliar Administrativo", "Auxiliar de Biblioteca", "Auxiliar de Campo", "Auxiliar de Clinica", "Auxiliar de Imprenta", "Auxiliar de Mantenimiento", "Auxiliar de Laboratorio", "Auxiliar de Servicio", "Chofer de Confianza", "Coordinador", "Desarrollo de Sistemas", "Jefe de Departamento", "Jefe de Departamento Administrativo", "Jefe de Unidad", "Jubilado Docente", "Logistica", "Medico General", "Oficial Administrativo", "Operador de Transporte", "Pensionado Docente", "Pensionado Jubilado", "Profesor Investigador Asignatura", "Profesor Investigador MT", "Profesor Investigador TC", "Rectoria", "Secretaria", "Secretaria de Admision", "Secretaria de Confianza", "Tecnico Academico MT", "Tecnico Academico TC", "Velador" }));

        jCheckBox4.setText("El Mismo");
        jCheckBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel43Layout = new javax.swing.GroupLayout(jPanel43);
        jPanel43.setLayout(jPanel43Layout);
        jPanel43Layout.setHorizontalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel43Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel43Layout.createSequentialGroup()
                        .addComponent(jLabel192)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField114))
                    .addGroup(jPanel43Layout.createSequentialGroup()
                        .addComponent(jLabel187)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField110, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel188)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 95, Short.MAX_VALUE))
                    .addGroup(jPanel43Layout.createSequentialGroup()
                        .addComponent(jLabel189)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel190)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField111, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel191)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField113))
                    .addGroup(jPanel43Layout.createSequentialGroup()
                        .addComponent(jLabel193)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField115, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel194)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField116, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jCheckBox4)))
                .addContainerGap())
        );
        jPanel43Layout.setVerticalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel43Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel187)
                    .addComponent(jTextField110, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel188)
                    .addComponent(jComboBox19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel189)
                    .addComponent(jTextField111, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel190)
                    .addComponent(jLabel191)
                    .addComponent(jTextField113, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel192)
                    .addComponent(jTextField114, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField115, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel193)
                    .addComponent(jLabel194)
                    .addComponent(jTextField116, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox4))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jButton13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/corregir.png"))); // NOI18N
        jButton13.setText("Corregir");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jButton47.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/guardar.png"))); // NOI18N
        jButton47.setText("Guardar");
        jButton47.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton47ActionPerformed(evt);
            }
        });

        jButton48.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/update.png"))); // NOI18N
        jButton48.setText("Update");
        jButton48.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton48ActionPerformed(evt);
            }
        });

        jButton49.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/imprimereportes.png"))); // NOI18N
        jButton49.setText("Print");
        jButton49.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton49ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout datos_ujatLayout = new javax.swing.GroupLayout(datos_ujat.getContentPane());
        datos_ujat.getContentPane().setLayout(datos_ujatLayout);
        datos_ujatLayout.setHorizontalGroup(
            datos_ujatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(datos_ujatLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(datos_ujatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, datos_ujatLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton49, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton48, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton47)))
                .addContainerGap())
        );
        datos_ujatLayout.setVerticalGroup(
            datos_ujatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, datos_ujatLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel43, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(datos_ujatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton47)
                    .addGroup(datos_ujatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton13)
                        .addComponent(jButton48)
                        .addComponent(jButton49)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        cotizacion.setTitle(".:. Cotizador de Servicios .:.");
        cotizacion.setModal(true);

        jLabel197.setText("Zona:");

        jLabel198.setText("KM Adicionales:");

        jTextField117.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jPanel46.setBorder(javax.swing.BorderFactory.createTitledBorder("Servicios Adicionales"));

        jCheckBox5.setText("Médico de Traslado");

        jCheckBox6.setText("Enfermera");

        jCheckBox7.setText("Médico Auxiliar");

        jCheckBox8.setText("Oxígeno");

        jCheckBox9.setText("Desfibrilador");

        jCheckBox10.setText("Ventilador Adulto");

        jCheckBox11.setText("Ventilador Neonatal");

        javax.swing.GroupLayout jPanel46Layout = new javax.swing.GroupLayout(jPanel46);
        jPanel46.setLayout(jPanel46Layout);
        jPanel46Layout.setHorizontalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel46Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel46Layout.createSequentialGroup()
                        .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCheckBox5)
                            .addComponent(jCheckBox6)
                            .addComponent(jCheckBox7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCheckBox10)
                            .addComponent(jCheckBox9)
                            .addComponent(jCheckBox8)))
                    .addComponent(jCheckBox11))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel46Layout.setVerticalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel46Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox5)
                    .addComponent(jCheckBox8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox6)
                    .addComponent(jCheckBox9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox7)
                    .addComponent(jCheckBox10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox11)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel199.setText("Nombre del Paciente:");

        jLabel200.setText("Correo:");

        jLabel201.setText("Teléfono:");

        jButton52.setText("Enviar");

        jButton53.setText("Cancelar");

        jLabel202.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel202.setText("Total:");

        jLabel203.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel203.setText("$0.00");

        jLabel204.setText("Tipo de Servicio:");

        javax.swing.GroupLayout jPanel44Layout = new javax.swing.GroupLayout(jPanel44);
        jPanel44.setLayout(jPanel44Layout);
        jPanel44Layout.setHorizontalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel44Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel44Layout.createSequentialGroup()
                        .addComponent(jLabel199)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField118))
                    .addGroup(jPanel44Layout.createSequentialGroup()
                        .addComponent(jLabel200)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField119, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel201)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField120))
                    .addGroup(jPanel44Layout.createSequentialGroup()
                        .addComponent(jPanel46, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel44Layout.createSequentialGroup()
                                .addComponent(jLabel202)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jLabel203, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel44Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton53)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton52))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel44Layout.createSequentialGroup()
                        .addComponent(jLabel204)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox23, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                        .addComponent(jLabel197)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox22, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel198)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField117, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel44Layout.setVerticalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel44Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel197)
                        .addComponent(jComboBox22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel204)
                        .addComponent(jComboBox23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel198)
                        .addComponent(jTextField117, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel46, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel44Layout.createSequentialGroup()
                        .addComponent(jLabel202)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel203)))
                .addGap(18, 18, 18)
                .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel199)
                    .addComponent(jTextField118, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel200)
                    .addComponent(jTextField119, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel201)
                    .addComponent(jTextField120, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 92, Short.MAX_VALUE)
                .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton52)
                    .addComponent(jButton53))
                .addContainerGap())
        );

        jTabbedPane4.addTab("Ambulancia", jPanel44);

        jLabel205.setText("Descripción:");

        jTextField121.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField121ActionPerformed(evt);
            }
        });

        jTable21.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CLAVE", "CANT", "DESCRIPCION", "PRECIO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable21MouseClicked(evt);
            }
        });
        jTable21.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jTable21PropertyChange(evt);
            }
        });
        jScrollPane34.setViewportView(jTable21);
        if (jTable21.getColumnModel().getColumnCount() > 0) {
            jTable21.getColumnModel().getColumn(0).setMinWidth(80);
            jTable21.getColumnModel().getColumn(0).setMaxWidth(70);
            jTable21.getColumnModel().getColumn(1).setMinWidth(50);
            jTable21.getColumnModel().getColumn(1).setMaxWidth(50);
            jTable21.getColumnModel().getColumn(3).setMinWidth(70);
            jTable21.getColumnModel().getColumn(3).setMaxWidth(70);
        }

        jButton54.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/email.png"))); // NOI18N
        jButton54.setText("Enviar");
        jButton54.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton54ActionPerformed(evt);
            }
        });

        jTextField122.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel206.setText("Total:");

        jTextField123.setEditable(false);

        jLabel207.setText("Cliente:");

        javax.swing.GroupLayout jPanel45Layout = new javax.swing.GroupLayout(jPanel45);
        jPanel45.setLayout(jPanel45Layout);
        jPanel45Layout.setHorizontalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel45Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel45Layout.createSequentialGroup()
                        .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel45Layout.createSequentialGroup()
                                .addComponent(jLabel205)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField121, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 4, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel45Layout.createSequentialGroup()
                        .addComponent(jLabel206)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField123, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel207)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField122)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton54)))
                .addContainerGap())
        );
        jPanel45Layout.setVerticalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel45Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel205)
                    .addComponent(jTextField121, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane34, javax.swing.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton54)
                    .addComponent(jLabel206)
                    .addComponent(jTextField123, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel207)
                    .addComponent(jTextField122, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTabbedPane4.addTab("Servicios", jPanel45);

        javax.swing.GroupLayout cotizacionLayout = new javax.swing.GroupLayout(cotizacion.getContentPane());
        cotizacion.getContentPane().setLayout(cotizacionLayout);
        cotizacionLayout.setHorizontalGroup(
            cotizacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cotizacionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 481, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        cotizacionLayout.setVerticalGroup(
            cotizacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cotizacionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        CatalogoArticulosTCA.setTitle(".:. Catálogo de Artículos TCA .:.");
        CatalogoArticulosTCA.setModal(true);

        jTable22.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CLAVE", "DESCRIPCION", "PRECIO", "IMP"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable22.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable22KeyPressed(evt);
            }
        });
        jScrollPane35.setViewportView(jTable22);
        if (jTable22.getColumnModel().getColumnCount() > 0) {
            jTable22.getColumnModel().getColumn(0).setMinWidth(80);
            jTable22.getColumnModel().getColumn(0).setMaxWidth(80);
            jTable22.getColumnModel().getColumn(1).setMinWidth(410);
            jTable22.getColumnModel().getColumn(1).setMaxWidth(410);
            jTable22.getColumnModel().getColumn(2).setMinWidth(90);
            jTable22.getColumnModel().getColumn(2).setMaxWidth(90);
            jTable22.getColumnModel().getColumn(3).setMinWidth(40);
            jTable22.getColumnModel().getColumn(3).setMaxWidth(40);
        }

        javax.swing.GroupLayout CatalogoArticulosTCALayout = new javax.swing.GroupLayout(CatalogoArticulosTCA.getContentPane());
        CatalogoArticulosTCA.getContentPane().setLayout(CatalogoArticulosTCALayout);
        CatalogoArticulosTCALayout.setHorizontalGroup(
            CatalogoArticulosTCALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CatalogoArticulosTCALayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane35, javax.swing.GroupLayout.PREFERRED_SIZE, 621, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        CatalogoArticulosTCALayout.setVerticalGroup(
            CatalogoArticulosTCALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CatalogoArticulosTCALayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane35, javax.swing.GroupLayout.PREFERRED_SIZE, 539, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        sendcorreo.setModal(true);
        sendcorreo.setUndecorated(true);

        jPanel47.setBackground(new java.awt.Color(204, 204, 255));
        jPanel47.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel208.setText("Correo:");

        jTextField124.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField124.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField124ActionPerformed(evt);
            }
        });

        jButton55.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/imprimereportes.png"))); // NOI18N
        jButton55.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton55ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel47Layout = new javax.swing.GroupLayout(jPanel47);
        jPanel47.setLayout(jPanel47Layout);
        jPanel47Layout.setHorizontalGroup(
            jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel47Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel47Layout.createSequentialGroup()
                        .addComponent(jLabel208)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField124))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel47Layout.createSequentialGroup()
                        .addGap(0, 160, Short.MAX_VALUE)
                        .addComponent(jButton55)))
                .addContainerGap())
        );
        jPanel47Layout.setVerticalGroup(
            jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel47Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel208)
                    .addComponent(jTextField124, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton55)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout sendcorreoLayout = new javax.swing.GroupLayout(sendcorreo.getContentPane());
        sendcorreo.getContentPane().setLayout(sendcorreoLayout);
        sendcorreoLayout.setHorizontalGroup(
            sendcorreoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sendcorreoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel47, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        sendcorreoLayout.setVerticalGroup(
            sendcorreoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sendcorreoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel47, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        RutaFiles.setModal(true);
        RutaFiles.setUndecorated(true);

        jPanel48.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel209.setText("Ruta:");

        jTextField125.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField125ActionPerformed(evt);
            }
        });

        jLabel210.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel210.setText("Pegue la ruta de la carpeta donde quiere guardar sus archivos y presione ENTER... Salir=Alt+F4");

        javax.swing.GroupLayout jPanel48Layout = new javax.swing.GroupLayout(jPanel48);
        jPanel48.setLayout(jPanel48Layout);
        jPanel48Layout.setHorizontalGroup(
            jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel48Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel209)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel210, javax.swing.GroupLayout.DEFAULT_SIZE, 553, Short.MAX_VALUE)
                    .addComponent(jTextField125))
                .addContainerGap())
        );
        jPanel48Layout.setVerticalGroup(
            jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel48Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel209)
                    .addComponent(jTextField125, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel210)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout RutaFilesLayout = new javax.swing.GroupLayout(RutaFiles.getContentPane());
        RutaFiles.getContentPane().setLayout(RutaFilesLayout);
        RutaFilesLayout.setHorizontalGroup(
            RutaFilesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RutaFilesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel48, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        RutaFilesLayout.setVerticalGroup(
            RutaFilesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RutaFilesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel48, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        TablaStatus.setModal(true);

        jTable23.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Compania", "Liquidado", "Cancelado", "Entregado", "Por Entregar", "En Facturacion"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane36.setViewportView(jTable23);
        if (jTable23.getColumnModel().getColumnCount() > 0) {
            jTable23.getColumnModel().getColumn(1).setMinWidth(300);
            jTable23.getColumnModel().getColumn(1).setMaxWidth(300);
        }

        jButton57.setText("Excel");
        jButton57.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton57ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout TablaStatusLayout = new javax.swing.GroupLayout(TablaStatus.getContentPane());
        TablaStatus.getContentPane().setLayout(TablaStatusLayout);
        TablaStatusLayout.setHorizontalGroup(
            TablaStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TablaStatusLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(TablaStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane36, javax.swing.GroupLayout.DEFAULT_SIZE, 858, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, TablaStatusLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton57)))
                .addContainerGap())
        );
        TablaStatusLayout.setVerticalGroup(
            TablaStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TablaStatusLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane36, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton57)
                .addContainerGap())
        );

        ExcelEstad.setTitle(".:. Estadistica de Facturas .:.");
        ExcelEstad.setModal(true);

        jTable25.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Compañia", "Factura", "Estancia", "Antes de Fac", "Fac-CXC", "CXC-Cobro", "Retardo"
            }
        ));
        jScrollPane38.setViewportView(jTable25);
        if (jTable25.getColumnModel().getColumnCount() > 0) {
            jTable25.getColumnModel().getColumn(0).setMinWidth(350);
            jTable25.getColumnModel().getColumn(0).setMaxWidth(350);
        }

        jButton64.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/excel.png"))); // NOI18N
        jButton64.setText("Exportar a Excel");
        jButton64.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton64ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ExcelEstadLayout = new javax.swing.GroupLayout(ExcelEstad.getContentPane());
        ExcelEstad.getContentPane().setLayout(ExcelEstadLayout);
        ExcelEstadLayout.setHorizontalGroup(
            ExcelEstadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ExcelEstadLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ExcelEstadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ExcelEstadLayout.createSequentialGroup()
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton64))
                    .addGroup(ExcelEstadLayout.createSequentialGroup()
                        .addComponent(jScrollPane38, javax.swing.GroupLayout.PREFERRED_SIZE, 790, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        ExcelEstadLayout.setVerticalGroup(
            ExcelEstadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ExcelEstadLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane38, javax.swing.GroupLayout.DEFAULT_SIZE, 495, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ExcelEstadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton64))
                .addContainerGap())
        );

        Key.setTitle(".:. Activación .:.");
        Key.setModal(true);

        jLabel220.setText("Clave de Activación:");

        jTextField130.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel221.setText("Dígito Verificador:");

        jTextField131.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jButton61.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/activar.png"))); // NOI18N
        jButton61.setText("Activar");
        jButton61.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton61ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout KeyLayout = new javax.swing.GroupLayout(Key.getContentPane());
        Key.getContentPane().setLayout(KeyLayout);
        KeyLayout.setHorizontalGroup(
            KeyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(KeyLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(KeyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(KeyLayout.createSequentialGroup()
                        .addGroup(KeyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel221)
                            .addComponent(jLabel220))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(KeyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField130)
                            .addGroup(KeyLayout.createSequentialGroup()
                                .addComponent(jTextField131, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 183, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, KeyLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton61)))
                .addContainerGap())
        );
        KeyLayout.setVerticalGroup(
            KeyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(KeyLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(KeyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel220)
                    .addComponent(jTextField130, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(KeyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel221)
                    .addComponent(jTextField131, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton61)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Configuracion.setTitle(".:. Conexión .:.");
        Configuracion.setModal(true);

        jButton65.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/guardar.png"))); // NOI18N
        jButton65.setText("Guardar");
        jButton65.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton65ActionPerformed(evt);
            }
        });

        jPanel29.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "MySQL", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        jLabel226.setText("Servidor:");

        jLabel227.setText("Puerto:");

        jLabel228.setText("Usuario:");

        jLabel230.setText("Base Datos:");

        jLabel229.setText("Contraseña:");

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel230)
                    .addComponent(jLabel229)
                    .addComponent(jLabel228)
                    .addComponent(jLabel227)
                    .addComponent(jLabel226))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField126)
                    .addComponent(jTextField132, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField133)
                    .addComponent(jPasswordField3)
                    .addComponent(jTextField134, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel226)
                    .addComponent(jTextField126, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel227)
                    .addComponent(jTextField132, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel228)
                    .addComponent(jTextField133, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel230)
                    .addComponent(jTextField134, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel229)
                    .addComponent(jPasswordField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel52.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "SQL Server", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        jLabel231.setText("Servidor:");

        jLabel232.setText("Puerto:");

        jLabel233.setText("Usuario:");

        jLabel234.setText("Base Datos:");

        jLabel235.setText("Contraseña:");

        javax.swing.GroupLayout jPanel52Layout = new javax.swing.GroupLayout(jPanel52);
        jPanel52.setLayout(jPanel52Layout);
        jPanel52Layout.setHorizontalGroup(
            jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel52Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel234)
                    .addComponent(jLabel235)
                    .addComponent(jLabel233)
                    .addComponent(jLabel232)
                    .addComponent(jLabel231))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField135)
                    .addComponent(jTextField136, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField137)
                    .addComponent(jPasswordField4)
                    .addComponent(jTextField138, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel52Layout.setVerticalGroup(
            jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel52Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel231)
                    .addComponent(jTextField135, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel232)
                    .addComponent(jTextField136, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel233)
                    .addComponent(jTextField137, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel234)
                    .addComponent(jTextField138, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel235)
                    .addComponent(jPasswordField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout ConfiguracionLayout = new javax.swing.GroupLayout(Configuracion.getContentPane());
        Configuracion.getContentPane().setLayout(ConfiguracionLayout);
        ConfiguracionLayout.setHorizontalGroup(
            ConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ConfiguracionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel29, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton65)
                    .addComponent(jPanel52, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        ConfiguracionLayout.setVerticalGroup(
            ConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ConfiguracionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel52, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton65)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle(".:. ACC Control: Sistema de Control de Cuentas por Cobrar .:.");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Logo Cobranza.jpg"))); // NOI18N

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Sistema de Control de");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Cuentas por Cobrar");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/usuario.png"))); // NOI18N
        jLabel2.setText("USUARIO:");

        jTextField1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(0, 0, 255));
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/contraseña.png"))); // NOI18N
        jLabel5.setText("PASSW:");

        jPasswordField1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPasswordField1.setForeground(new java.awt.Color(0, 0, 255));
        jPasswordField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPasswordField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPasswordField1ActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/aceptar.png"))); // NOI18N
        jButton1.setText("aceptar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cancelar.png"))); // NOI18N
        jButton2.setText("cancelar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("SESION:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setText("INGRESO:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setText("FECHA:");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel12.setText("HORA:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jLabel222.setText("Vigencia de la Licencia:");

        jLabel223.setText("jLabel223");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField1)
                                    .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 10, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel222)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel223, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField1)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel222)
                    .addComponent(jLabel223))
                .addGap(7, 7, 7))
        );

        menufacturas.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        fac.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/facturacion.png"))); // NOI18N
        fac.setText("Cartera y Cobranza");
        fac.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        captu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/captura.png"))); // NOI18N
        captu.setText("Captura de Datos");
        captu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                captuActionPerformed(evt);
            }
        });
        fac.add(captu);

        cons.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/consultas.png"))); // NOI18N
        cons.setText("Consultas");
        cons.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                consActionPerformed(evt);
            }
        });
        fac.add(cons);

        repor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/imprimereportes.png"))); // NOI18N
        repor.setText("Impresión de Reportes");
        repor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reporActionPerformed(evt);
            }
        });
        fac.add(repor);

        cambiafac.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/transfer.png"))); // NOI18N
        cambiafac.setText("Transferencia de Facturas");
        cambiafac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cambiafacActionPerformed(evt);
            }
        });
        fac.add(cambiafac);

        menufacturas.add(fac);

        acerca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/acercade.png"))); // NOI18N
        acerca.setText("Acerca de ...");
        acerca.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        acerca.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                acercaMouseClicked(evt);
            }
        });
        menufacturas.add(acerca);

        licenciaa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/activate.png"))); // NOI18N
        licenciaa.setText("Activación");
        licenciaa.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        licenciaa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                licenciaaMouseClicked(evt);
            }
        });
        menufacturas.add(licenciaa);

        datosujat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/adicionales.png"))); // NOI18N
        datosujat.setText("Adicionales");
        datosujat.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        aniadir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/adic_ujat.png"))); // NOI18N
        aniadir.setText("Datos de UJAT");
        aniadir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aniadirActionPerformed(evt);
            }
        });
        datosujat.add(aniadir);

        cotizador.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cotiza.png"))); // NOI18N
        cotizador.setText("Cotizador");
        cotizador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cotizadorActionPerformed(evt);
            }
        });
        datosujat.add(cotizador);

        menufacturas.add(datosujat);

        config.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/configuracion.png"))); // NOI18N
        config.setText("Config");
        config.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        conexion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/conexion.png"))); // NOI18N
        conexion.setText("Conexión");
        conexion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                conexionActionPerformed(evt);
            }
        });
        config.add(conexion);

        menufacturas.add(config);

        setJMenuBar(menufacturas);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 412, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        ConsultaUsuarioTCA();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void captuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_captuActionPerformed
        // TODO add your handling code here:
        jButton4.setEnabled(false);
        jButton6.setEnabled(true);
        cent_captura();
    }//GEN-LAST:event_captuActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        jTextField25.setText(jComboBox1.getSelectedItem().toString());
        SelecFolio.dispose();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
        ObtieneDatosFolio(jComboBox1.getSelectedItem().toString());
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
        ConsultaUsuarioTCA();
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jPasswordField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPasswordField1ActionPerformed
        // TODO add your handling code here:
        ConsultaUsuarioTCA();
    }//GEN-LAST:event_jPasswordField1ActionPerformed

    private void jTextField39KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField39KeyReleased
        // TODO add your handling code here:
        CoincideCompa(jTextField39.getText());
    }//GEN-LAST:event_jTextField39KeyReleased

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        DefaultTableModel modelo = (DefaultTableModel)jTable2.getModel();
        
        if(jTable2.getSelectedRow() < 0)
            JOptionPane.showMessageDialog(null, "Seleccione una Fila","Error",JOptionPane.ERROR_MESSAGE);
        
        else{
            jTextField35.setText(modelo.getValueAt(jTable2.getSelectedRow(), 0).toString());
            jTextField34.setText(modelo.getValueAt(jTable2.getSelectedRow(), 1).toString());
            companiacaptura.dispose();
        }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jComboBox7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox7ActionPerformed
        // TODO add your handling code here:
        ObtieneDatosFolio2(jComboBox7.getSelectedItem().toString());
    }//GEN-LAST:event_jComboBox7ActionPerformed

    private void reporActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reporActionPerformed
        // TODO add your handling code here:
        RecibeFolios(1);
        cent_impresion();
    }//GEN-LAST:event_reporActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:
        ImpContrarecibo(Integer.parseInt(jComboBox7.getSelectedItem().toString()));
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        // TODO add your handling code here:
        ConsEdoCuenta("");
    }//GEN-LAST:event_jButton16ActionPerformed

    private void consActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_consActionPerformed
        // TODO add your handling code here:
        cent_consulta();
    }//GEN-LAST:event_consActionPerformed

    private void jTextField16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField16ActionPerformed
        // TODO add your handling code here:
        jTextField17.setEnabled(true);
        cent_selecaseg();
        
        jTextField16.setText(aseguradora);
        jTextField112.setText(nocompania);
        jTextField99.setText(mailaseg);
        jTextField16.setEnabled(false);
        jTextField17.setEnabled(false);
    }//GEN-LAST:event_jTextField16ActionPerformed

    private void jTextField17KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField17KeyReleased
        // TODO add your handling code here:
        CoincideCompaEdoCta(jTextField17.getText(),0);
    }//GEN-LAST:event_jTextField17KeyReleased

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        // TODO add your handling code here:
        EdoCtaRadio();
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void jTextField43ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField43ActionPerformed
        // TODO add your handling code here:
        ConsultaFactura(Integer.parseInt(jTextField43.getText()));
    }//GEN-LAST:event_jTextField43ActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        // TODO add your handling code here:
        EdoCtaRadio();
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void jTextField16KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField16KeyReleased
        // TODO add your handling code here:
        /*ResultSet rs=null;
        String nombre="";
        int numero = jTextField16.getText().length();
        String datobusqueda = "";
        
        try{
           stat = con.createStatement();
           rs=ConsultasSistema.EstructuraMySQL(con,"SELECT TOP 1 NombreComercial FROM cxccli WHERE NombreComercial LIKE '"+jTextField16.getText()+"%';");
           
           if(rs.next()){
               nombre = rs.getString("NombreComercial");
               datobusqueda = nombre.substring(jTextField16.getText().length(), nombre.length());
                try{
                    jTextField16.getDocument().insertString(jTextField16.getCaretPosition(), datobusqueda, null);
                } catch (BadLocationException ex) {
                    ex.printStackTrace();}
                jTextField16.select(numero, jTextField16.getText().length());
                
                if (evt.getKeyCode() == evt.VK_ENTER){
                    jTextField16.setText(nombre);
                }
           }
       }catch (Exception e){JOptionPane.showMessageDialog(null, e);}*/
    }//GEN-LAST:event_jTextField16KeyReleased

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        // TODO add your handling code here:
        ImpContrareciboSaldos(Integer.parseInt(jComboBox7.getSelectedItem().toString()));
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jTextField40ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField40ActionPerformed
        // TODO add your handling code here:
        CalculaDias(0,jTextField40.getText());
    }//GEN-LAST:event_jTextField40ActionPerformed

    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed
        // TODO add your handling code here:
        MuestraDatosAll("Compania");
    }//GEN-LAST:event_jButton20ActionPerformed

    private void jTextField53ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField53ActionPerformed
        // TODO add your handling code here:
        try{
            CoincideCompaEdoCta(Integer.toString(Integer.parseInt(jTextField53.getText())),1);
        }catch(Exception e){ CoincideCompaEdoCta(jTextField53.getText(),0); }
        
        cent_selecaseg();
        jTextField53.setText(aseguradora);
        jTextField53.setEnabled(false);
    }//GEN-LAST:event_jTextField53ActionPerformed

    private void jRadioButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton5ActionPerformed
        // TODO add your handling code here:
        if(jRadioButton5.isSelected() == true){
            jPanel19.setVisible(true);
            jPanel36.setVisible(false);
        }
        else{
            jPanel19.setVisible(false);
            jPanel36.setVisible(true);
        }
    }//GEN-LAST:event_jRadioButton5ActionPerformed

    private void jRadioButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton6ActionPerformed
        // TODO add your handling code here:
        if(jRadioButton5.isSelected() == true){
            jPanel19.setVisible(true);
            jPanel36.setVisible(false);
        }
        else{
            jPanel19.setVisible(false);
            jPanel36.setVisible(true);
        }
    }//GEN-LAST:event_jRadioButton6ActionPerformed

    private void jRadioButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton7ActionPerformed
        // TODO add your handling code here:
        EdoCtaRadio();
    }//GEN-LAST:event_jRadioButton7ActionPerformed

    private void jTextField55ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField55ActionPerformed
        // TODO add your handling code here:
        String texto = jTextField55.getText();
        if(texto.length() == 0){ buscapaciente(); }
        else{ RastreaFactura(texto); }
    }//GEN-LAST:event_jTextField55ActionPerformed

    private void jTextField60KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField60KeyReleased
        // TODO add your handling code here:
        CoincidePaciente(jTextField60.getText());
    }//GEN-LAST:event_jTextField60KeyReleased

    private void jButton24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton24ActionPerformed
        // TODO add your handling code here:
        if(jTable11.getSelectedRow() < 0)
            JOptionPane.showMessageDialog(null, "error","seleccione una fila",JOptionPane.ERROR_MESSAGE);
        
        else{
            DefaultTableModel modelo = (DefaultTableModel)jTable11.getModel();
            jTextField55.setText(modelo.getValueAt(jTable11.getSelectedRow(), 1).toString());
            buscapaciente.dispose();
        }
    }//GEN-LAST:event_jButton24ActionPerformed

    private void jComboBox13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox13ActionPerformed

    private void jComboBox14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox14ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox14ActionPerformed

    private void jButton25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton25ActionPerformed
        // TODO add your handling code here:
        RepEstPeriodo(Sistema.FechaPeriodo(jDateChooser12.getCalendar()),Sistema.FechaPeriodo(jDateChooser13.getCalendar()));
    }//GEN-LAST:event_jButton25ActionPerformed

    private void jTextField28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField28ActionPerformed
        // TODO add your handling code here:
        ConsFacturaNoPaciente(jTextField28.getText());
    }//GEN-LAST:event_jTextField28ActionPerformed

    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed
        // TODO add your handling code here:
        jComboBox10.addItem(ConsultasSistema.NuevoFolio(conMySQL));
        jComboBox10.setSelectedIndex(jComboBox10.getItemCount()-1);
    }//GEN-LAST:event_jButton21ActionPerformed

    private void cambiafacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cambiafacActionPerformed
        // TODO add your handling code here:
        cambiafactura();
    }//GEN-LAST:event_cambiafacActionPerformed

    private void jTextField61ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField61ActionPerformed
        // TODO add your handling code here:
        SelecFolioFactura(jTextField61.getText());
    }//GEN-LAST:event_jTextField61ActionPerformed

    private void jButton22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton22ActionPerformed
        // TODO add your handling code here:
        TransfiereFacturaFolio(jTextField61.getText());
    }//GEN-LAST:event_jButton22ActionPerformed

    private void jButton26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton26ActionPerformed
        // TODO add your handling code here:
        NuevaTransferencia();
    }//GEN-LAST:event_jButton26ActionPerformed

    private void jButton27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton27ActionPerformed
        // TODO add your handling code here:
        DefaultTableModel modelo = (DefaultTableModel)jTable12.getModel();
        if(jTable12.getSelectedRow() < 0)
            JOptionPane.showMessageDialog(null,"seleccione una factura","error",JOptionPane.ERROR_MESSAGE);
        else
            ConsultaFactura(Integer.parseInt(modelo.getValueAt(jTable12.getSelectedRow(), 0).toString()));
    }//GEN-LAST:event_jButton27ActionPerformed

    private void jButton28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton28ActionPerformed
        // TODO add your handling code here:
        ConciliaFacturas();
    }//GEN-LAST:event_jButton28ActionPerformed

    private void jTable12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable12MouseClicked
        // TODO add your handling code here:
        ObtieneHistorialFactura();
    }//GEN-LAST:event_jTable12MouseClicked

    private void jTable12KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable12KeyReleased
        // TODO add your handling code here:
        ObtieneHistorialFactura();
    }//GEN-LAST:event_jTable12KeyReleased

    private void jButton33ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton33ActionPerformed
        // TODO add your handling code here:
        MuestraDatosAll("Parametros");
    }//GEN-LAST:event_jButton33ActionPerformed

    private void jButton34ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton34ActionPerformed
        // TODO add your handling code here:
        MuestraDatosAll("Folio");
    }//GEN-LAST:event_jButton34ActionPerformed

    private void jComboBox16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox16ActionPerformed
        // TODO add your handling code here:
        if(jComboBox16.getSelectedIndex() == 0)
            jPanel37.setVisible(false);
        else
            jPanel37.setVisible(true);
    }//GEN-LAST:event_jComboBox16ActionPerformed

    private void jButton35ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton35ActionPerformed
        // TODO add your handling code here:
        jTextField75.setText(jComboBox1.getSelectedItem().toString());
        SelecFolio.dispose();
    }//GEN-LAST:event_jButton35ActionPerformed

    private void jTextArea10KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea10KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextArea10KeyTyped

    private void jTextField88KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField88KeyReleased
        // TODO add your handling code here:
        jTextField91.setText(SumaDeducible(2));
    }//GEN-LAST:event_jTextField88KeyReleased

    private void jTextField87KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField87KeyReleased
        // TODO add your handling code here:
        jTextField91.setText(SumaDeducible(2));
    }//GEN-LAST:event_jTextField87KeyReleased

    private void jTextField86KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField86KeyReleased
        // TODO add your handling code here:
        jTextField91.setText(SumaDeducible(2));
    }//GEN-LAST:event_jTextField86KeyReleased

    private void jTextField76ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField76ActionPerformed
        // TODO add your handling code here:
        ConsDatosFacturaModifica(jTextField76.getText());
    }//GEN-LAST:event_jTextField76ActionPerformed

    private void jButton30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton30ActionPerformed
        // TODO add your handling code here:
        ActualizaFactura(jTextField76.getText());
    }//GEN-LAST:event_jButton30ActionPerformed

    private void jTextArea9KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea9KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextArea9KeyTyped

    private void jButton29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton29ActionPerformed
        // TODO add your handling code here:
        jTextField76.setEnabled(true);
        jButton30.setEnabled(false);
    }//GEN-LAST:event_jButton29ActionPerformed

    private void jRadioButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton8ActionPerformed
        // TODO add your handling code here:
        if(jRadioButton8.isSelected() == true){
            jPanel17.setVisible(false);
            jPanel18.setVisible(false);
            jPanel24.setVisible(true);
        }
        else{
            jPanel17.setVisible(false);
            jPanel18.setVisible(false);
            jPanel24.setVisible(true);
        }
    }//GEN-LAST:event_jRadioButton8ActionPerformed

    private void jButton23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton23ActionPerformed
        // TODO add your handling code here:
        AbonaFolio();
    }//GEN-LAST:event_jButton23ActionPerformed

    private void jComboBox12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox12ActionPerformed
        // TODO add your handling code here:
        ConsMontoFolio(jComboBox12.getSelectedItem().toString());
    }//GEN-LAST:event_jComboBox12ActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        // TODO add your handling code here:
        jTextField44.setEnabled(true);
        jTextField44.setText("");
        jButton15.setEnabled(false);
        jButton36.setEnabled(false);
    }//GEN-LAST:event_jButton19ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        // TODO add your handling code here:
        AbonoFactura(jTextField46.getText());
        jTextField44.setEnabled(true);
        jTextField44.setText("");
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jTextField44ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField44ActionPerformed
        // TODO add your handling code here:
        ConsMontoFactura(jTextField44.getText());
    }//GEN-LAST:event_jTextField44ActionPerformed

    private void jRadioButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton4ActionPerformed
        // TODO add your handling code here:
        if(jRadioButton3.isSelected() == true){
            jPanel17.setVisible(true);
            jPanel18.setVisible(false);
            jPanel24.setVisible(false);
        }
        else{
            jPanel17.setVisible(false);
            jPanel18.setVisible(true);
            jPanel24.setVisible(false);
        }
    }//GEN-LAST:event_jRadioButton4ActionPerformed

    private void jRadioButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton3ActionPerformed
        // TODO add your handling code here:
        if(jRadioButton3.isSelected() == true){
            jPanel17.setVisible(true);
            jPanel18.setVisible(false);
            jPanel24.setVisible(false);
        }
        else{
            jPanel17.setVisible(false);
            jPanel18.setVisible(true);
            jPanel24.setVisible(false);
        }
    }//GEN-LAST:event_jRadioButton3ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
        String docto = jTextField34.getText();
        String aseg = jTextField35.getText();
        String tipo = jTextField36.getText();
        String monto = jTextField37.getText();

        InsertaDeposito();
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jTextField35KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField35KeyReleased
        // TODO add your handling code here:
        ResultSet rs=null;
        String nombre="";
        int numero = jTextField35.getText().length();
        String datobusqueda = "";

        try{
            stat = con.createStatement();
            rs=ConsultasSistema.EstructuraMySQL(con,"SELECT TOP 1 NombreComercial FROM cxccli WHERE NombreComercial LIKE '"+jTextField35.getText()+"%';");

            if(rs.next()){
                nombre = rs.getString("NombreComercial");
                datobusqueda = nombre.substring(jTextField35.getText().length(), nombre.length());
                try{
                    jTextField35.getDocument().insertString(jTextField35.getCaretPosition(), datobusqueda, null);
                } catch (BadLocationException ex) {
                    ex.printStackTrace();}
                jTextField35.select(numero, jTextField35.getText().length());

                if (evt.getKeyCode() == evt.VK_ENTER){
                    jTextField35.setText(nombre);
                }
            }
        }catch (Exception e){JOptionPane.showMessageDialog(null, e);}
        //BuscaClientes(jTextField35.getText());
    }//GEN-LAST:event_jTextField35KeyReleased

    private void jTextField35ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField35ActionPerformed
        // TODO add your handling code here:
        /*BuscaCompania(jTextField35.getText());
        cent_compania();*/
        cent_selecaseg();
        jTextField35.setText(aseguradora);
        jTextField35.setEnabled(false);
    }//GEN-LAST:event_jTextField35ActionPerformed

    private void jTextField34ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField34ActionPerformed
        // TODO add your handling code here:
        BuscaCompania(jTextField34.getText());
        cent_compania();
    }//GEN-LAST:event_jTextField34ActionPerformed

    private void jSpinner3StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinner3StateChanged
        // TODO add your handling code here:
        java.util.Date fecha = new java.util.Date();
        fecha.getDate();
        jDateChooser1.setDate(fecha);

        jTextField54.setText(CalculaFechaPago(jDateChooser1.getCalendar(),Integer.parseInt(jSpinner3.getValue().toString())));
    }//GEN-LAST:event_jSpinner3StateChanged

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
        AfectaFolio(jComboBox2.getSelectedItem().toString());
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jComboBox6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox6ActionPerformed
        // TODO add your handling code here:
        if(jComboBox6.getSelectedItem().toString().equals("Entregada")){
            jPanel21.setVisible(true);
            jSpinner3.setValue(30);
        }
        else
        jPanel21.setVisible(false);
    }//GEN-LAST:event_jComboBox6ActionPerformed

    private void jTextField24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField24ActionPerformed
        // TODO add your handling code here:
        ObtieneDatosFacturaMySQL(jTextField24.getText());
    }//GEN-LAST:event_jTextField24ActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        // TODO add your handling code here:
        CheckCombos();
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        ActualizaStatus();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        LimpiaConsultas();
        jComboBox3.setEnabled(false);
        jButton5.setEnabled(false);
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jSpinner2StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinner2StateChanged
        // TODO add your handling code here:
        java.util.Date fecha = new java.util.Date();
        fecha.getDate();
        jDateChooser1.setDate(fecha);
    }//GEN-LAST:event_jSpinner2StateChanged

    private void jTextArea3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea3KeyTyped
        // TODO add your handling code here:
        int contador=jTextArea3.getText().length();
        jLabel56.setText(Integer.toString(contador));
        String texto = jTextArea3.getText();

        if(contador >= 100){
            String Cadena=texto.substring(0, texto.length()-1);
            jTextArea3.setText(Cadena);
        }
    }//GEN-LAST:event_jTextArea3KeyTyped

    private void jTextArea5KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea5KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextArea5KeyTyped

    private void jTextField27KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField27KeyReleased
        // TODO add your handling code here:
        jTextField91.setText(SumaDeducible(1));
    }//GEN-LAST:event_jTextField27KeyReleased

    private void jTextField23KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField23KeyReleased
        // TODO add your handling code here:
        jTextField91.setText(SumaDeducible(1));
    }//GEN-LAST:event_jTextField23KeyReleased

    private void jTextField22KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField22KeyReleased
        // TODO add your handling code here:
        jTextField91.setText(SumaDeducible(1));
    }//GEN-LAST:event_jTextField22KeyReleased

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
        String factura = jTextField2.getText();
        int opc = 0;
        Date fechaDate = null;
        
        if(ConsProvision(factura)[0] == "1"){
            opc = JOptionPane.showConfirmDialog(null,"Factura en provisión, ¿transferir?","confirme",JOptionPane.YES_NO_OPTION);
            if(opc == 0) {
                facprovisiona = 1;
                ConsDatosFactura(factura);
                SimpleDateFormat formato = new java.text.SimpleDateFormat("yyyy-MM-dd"); 
                try {
                    fechaDate = formato.parse(ConsProvision(factura)[1]);
                } catch (ParseException ex) {Logger.getLogger(Facturas.class.getName()).log(Level.SEVERE, null, ex);}
                jDateChooser7.setDate(fechaDate);
            }
        }
        else { facprovisiona = 0; ConsDatosFactura(factura); }
        
        
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        jButton8.setVisible(true);
        jButton35.setVisible(false);
        RecibeFolios(1);
        cent_folios();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        jButton6.setEnabled(false);
        jTextField25.setText(ConsultasSistema.NuevoFolio(conMySQL));
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        if(facprovisiona == 1) EliminaProvision(jTextField2.getText());
        AgregaFactura();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTextArea2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea2KeyTyped
        // TODO add your handling code here:
        int contador=jTextArea2.getText().length();
        jLabel54.setText(Integer.toString(contador));
        String texto = jTextArea2.getText();

        if(contador >= 100){
            String Cadena=texto.substring(0, texto.length()-1);
            jTextArea2.setText(Cadena);
        }
    }//GEN-LAST:event_jTextArea2KeyTyped

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        jTextField2.setEnabled(true);
        jButton3.setText("Corregir");
        jButton4.setEnabled(false);
        jButton6.setEnabled(true);
        LimpiarCaptura();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton36ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton36ActionPerformed
        // TODO add your handling code here:
        jTextField24.setText(jTextField44.getText());
        ObtieneDatosFacturaMySQL(jTextField24.getText());
        jTabbedPane1.setSelectedIndex(1);
    }//GEN-LAST:event_jButton36ActionPerformed

    private void jButton37ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton37ActionPerformed
        // TODO add your handling code here:
        jComboBox2.setSelectedItem(jComboBox12.getSelectedItem());
        jTabbedPane1.setSelectedIndex(2);
    }//GEN-LAST:event_jButton37ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void acercaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_acercaMouseClicked
        // TODO add your handling code here:
        cent_about();
    }//GEN-LAST:event_acercaMouseClicked

    private void jButton38ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton38ActionPerformed
        // TODO add your handling code here:
        Concentrado();
    }//GEN-LAST:event_jButton38ActionPerformed

    private void jButton39ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton39ActionPerformed
        // TODO add your handling code here:
        String Inicio = Sistema.FechaPeriodo(jDateChooser17.getCalendar());
        String Fin = Sistema.FechaPeriodo(jDateChooser18.getCalendar());
        
        if((jCheckBox1.isSelected() == true)&&(jCheckBox12.isSelected() == false))
            //JOptionPane.showMessageDialog(null, "el administrador del sistema ha deshabilitado esta opcion","Error",JOptionPane.ERROR_MESSAGE);
            RepStatus(Inicio,Fin);
        else{
            if((jCheckBox1.isSelected() == true)&&(jCheckBox12.isSelected() == true)){
                getReporteStatTabla(Inicio,Fin);
                cent_tablastatus();
            }
            else RepConcentrado(Inicio,Fin);
        }
    }//GEN-LAST:event_jButton39ActionPerformed

    private void jButton40ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton40ActionPerformed
        // TODO add your handling code here:
        jComboBox3.setEnabled(true);
        jButton5.setEnabled(true);
    }//GEN-LAST:event_jButton40ActionPerformed

    private void jButton31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton31ActionPerformed
        // TODO add your handling code here:
        AntiguedadSaldos();
    }//GEN-LAST:event_jButton31ActionPerformed

    private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox2ActionPerformed
        // TODO add your handling code here:
        if(jCheckBox2.isSelected() == true){
            jPanel38.setVisible(false);
            jPanel39.setVisible(true);
            jPanel49.setVisible(false);
            jCheckBox13.setSelected(false);
        }
        else{
            jPanel38.setVisible(true);
            jPanel39.setVisible(false);
            jPanel49.setVisible(false);
        }
    }//GEN-LAST:event_jCheckBox2ActionPerformed

    private void jButton32ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton32ActionPerformed
        // TODO add your handling code here:
        RepAntigSaldos(Sistema.FechaPeriodo(jDateChooser20.getCalendar()),Sistema.FechaPeriodo(jDateChooser21.getCalendar()),jComboBox17.getSelectedIndex());
    }//GEN-LAST:event_jButton32ActionPerformed

    private void jButton41ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton41ActionPerformed
        // TODO add your handling code here:
        jTextField89.setText(jTextField99.getText());
        jTextField92.setText(jTextField101.getText());
        jTextArea12.setText(jTextArea13.getText());
        cent_sendmail();
    }//GEN-LAST:event_jButton41ActionPerformed

    private void jButton44ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton44ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton44ActionPerformed

    private void jTable19MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable19MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable19MouseClicked

    private void jTable19KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable19KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable19KeyReleased

    private void jButton42ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton42ActionPerformed
        // TODO add your handling code here:
        getRutaAttatchment(0);
    }//GEN-LAST:event_jButton42ActionPerformed

    private void jButton45ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton45ActionPerformed
        // TODO add your handling code here:
        LeeConfig();
        cent_editmail();
    }//GEN-LAST:event_jButton45ActionPerformed

    private void jButton46ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton46ActionPerformed
        // TODO add your handling code here:
        EscribeConfig();
        conf_mail.dispose();
    }//GEN-LAST:event_jButton46ActionPerformed

    private void jButton43ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton43ActionPerformed
        // TODO add your handling code here:
        /*try {
            //SendeMail(smtphost,jTextField97.getText()+".pdf");
        } catch (MessagingException ex) {
            Logger.getLogger(Facturas.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }//GEN-LAST:event_jButton43ActionPerformed

    private void jRadioButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton9ActionPerformed
        // TODO add your handling code here:
        getRutaAttatchment(1);
    }//GEN-LAST:event_jRadioButton9ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
        LimpiaUJAT();
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton47ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton47ActionPerformed
        // TODO add your handling code here:
        InsertaUJATadicionales(0);
    }//GEN-LAST:event_jButton47ActionPerformed

    private void aniadirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aniadirActionPerformed
        // TODO add your handling code here:
        cent_datosujat();
    }//GEN-LAST:event_aniadirActionPerformed

    private void jTextField103ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField103ActionPerformed
        // TODO add your handling code here:
        ConsUJATadicionales(jTextField103.getText());
    }//GEN-LAST:event_jTextField103ActionPerformed

    private void jButton48ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton48ActionPerformed
        // TODO add your handling code here:
        InsertaUJATadicionales(1);
    }//GEN-LAST:event_jButton48ActionPerformed

    private void jButton49ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton49ActionPerformed
        // TODO add your handling code here:
        RepUJATadicionales(jTextField103.getText());
    }//GEN-LAST:event_jButton49ActionPerformed

    private void jCheckBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox4ActionPerformed
        // TODO add your handling code here:
        CheckBoxMismo();
    }//GEN-LAST:event_jCheckBox4ActionPerformed

    private void jButton50ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton50ActionPerformed
        // TODO add your handling code here:
        Porcentual(Sistema.FechaPeriodo(jDateChooser23.getCalendar()),Sistema.FechaPeriodo(jDateChooser24.getCalendar()));
    }//GEN-LAST:event_jButton50ActionPerformed

    private void jTextField121ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField121ActionPerformed
        // TODO add your handling code here:
        CatArtTCA(jTextField121.getText());
        cent_catalog();
    }//GEN-LAST:event_jTextField121ActionPerformed

    private void cotizadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cotizadorActionPerformed
        // TODO add your handling code here:
        cent_cotizacion();
    }//GEN-LAST:event_cotizadorActionPerformed

    private void jTable22KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable22KeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER){
            Object []Fila = new Object [4];
            Fila[0]=modeloT22.getValueAt(jTable22.getSelectedRow(),0);
            Fila[1]="1";
            Fila[2]=modeloT22.getValueAt(jTable22.getSelectedRow(),1);
            Fila[3]=modeloT22.getValueAt(jTable22.getSelectedRow(),2);
            modeloT21.addRow(Fila);
            CalcTotal();
            CatalogoArticulosTCA.dispose();
        }
    }//GEN-LAST:event_jTable22KeyPressed

    private void jTable21PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jTable21PropertyChange
        // TODO add your handling code here:
        CalcTotal();
    }//GEN-LAST:event_jTable21PropertyChange

    private void jButton54ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton54ActionPerformed
        // TODO add your handling code here:
        String fechavig = CalculaFechaPago(Calendar.getInstance(),diasvigencia);
        InsertaCot(GeneraRandom(5),fechavig);
    }//GEN-LAST:event_jButton54ActionPerformed

    private void jTextField124ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField124ActionPerformed
        // TODO add your handling code here:
        Cotiza(2);
        sendMail(jTextField124.getText());
    }//GEN-LAST:event_jTextField124ActionPerformed

    private void jButton55ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton55ActionPerformed
        // TODO add your handling code here:
        Cotiza(0);
    }//GEN-LAST:event_jButton55ActionPerformed

    private void jTable21MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable21MouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) modeloT21.removeRow(jTable21.getSelectedRow()); CalcTotal();
    }//GEN-LAST:event_jTable21MouseClicked

    private void jButton56ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton56ActionPerformed
        // TODO add your handling code here:
        ForFacturas(); //cent_ruta();
    }//GEN-LAST:event_jButton56ActionPerformed

    private void jTextField125ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField125ActionPerformed
        // TODO add your handling code here:
        /*if(!jTextField125.getText().equals(""))
            ForFacturas(jTextField125.getText());*/
    }//GEN-LAST:event_jTextField125ActionPerformed

    private void jButton57ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton57ActionPerformed
        // TODO add your handling code here:
        if(modeloT23.getRowCount() > 0)
            ExcelStatus();
    }//GEN-LAST:event_jButton57ActionPerformed

    private void jCheckBox13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox13ActionPerformed
        // TODO add your handling code here:
        if(jCheckBox13.isSelected() == true){
            jPanel38.setVisible(false);
            jPanel39.setVisible(false);
            jPanel49.setVisible(true);
            jCheckBox2.setSelected(false);
        }
        else{
            jPanel38.setVisible(true);
            jPanel39.setVisible(false);
            jPanel49.setVisible(false);
        }
    }//GEN-LAST:event_jCheckBox13ActionPerformed

    private void jButton58ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton58ActionPerformed
        // TODO add your handling code here:
        RepLiquidadas(Sistema.FechaPeriodo(jDateChooser25.getCalendar()),Sistema.FechaPeriodo(jDateChooser26.getCalendar()));
    }//GEN-LAST:event_jButton58ActionPerformed

    private void jTextField127ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField127ActionPerformed
        // TODO add your handling code here:
        ConsFacProvisiona(jTextField127.getText());
    }//GEN-LAST:event_jTextField127ActionPerformed

    private void jButton59ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton59ActionPerformed
        // TODO add your handling code here:
        IngresaProvision();
    }//GEN-LAST:event_jButton59ActionPerformed

    private void jCheckBox14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox14ActionPerformed
        // TODO add your handling code here:
        if(jCheckBox14.isSelected() == true) jPanel51.setVisible(true);
        else jPanel51.setVisible(false);
    }//GEN-LAST:event_jCheckBox14ActionPerformed

    private void jButton60ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton60ActionPerformed
        // TODO add your handling code here:
        CalculaDias(1,jTextField40.getText());
    }//GEN-LAST:event_jButton60ActionPerformed

    private void jButton61ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton61ActionPerformed
        // TODO add your handling code here:
        Licencia(0);
    }//GEN-LAST:event_jButton61ActionPerformed

    private void licenciaaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_licenciaaMouseClicked
        // TODO add your handling code here:
        cent_key();
    }//GEN-LAST:event_licenciaaMouseClicked

    private void jButton62ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton62ActionPerformed
        // TODO add your handling code here:
        LimpiaProvision();
    }//GEN-LAST:event_jButton62ActionPerformed

    private void jButton63ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton63ActionPerformed
        // TODO add your handling code here:
        int a = jTable24.getSelectedRow();
        
        if(a < 0)
            JOptionPane.showMessageDialog(null, "seleccione una factura","Error",JOptionPane.ERROR_MESSAGE);
        else
            modeloT24.removeRow(jTable24.getSelectedRow());
    }//GEN-LAST:event_jButton63ActionPerformed

    private void jButton64ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton64ActionPerformed
        // TODO add your handling code here:
        if(modeloT25.getRowCount() == 0)
            JOptionPane.showMessageDialog(null, "tabla sin datos","Error",JOptionPane.ERROR_MESSAGE);
        else ExcelEstadistica();
    }//GEN-LAST:event_jButton64ActionPerformed

    private void jButton65ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton65ActionPerformed
        // TODO add your handling code here:
        if(EscribeConfigDB() == 1) JOptionPane.showMessageDialog(null, "datos actualizados");
    }//GEN-LAST:event_jButton65ActionPerformed

    private void conexionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_conexionActionPerformed
        // TODO add your handling code here:
        cent_conf();
    }//GEN-LAST:event_conexionActionPerformed

    private void jTable6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable6KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
            AddCompania();
    }//GEN-LAST:event_jTable6KeyPressed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jButton51ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton51ActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(null, "No existe desarrollo para este reporte","error",JOptionPane.ERROR_MESSAGE);
    }//GEN-LAST:event_jButton51ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Facturas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Facturas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Facturas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Facturas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Facturas().setVisible(true);
                } catch (ParseException ex) {
                    Logger.getLogger(Facturas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog Activacion;
    private javax.swing.JDialog CatalogoArticulosTCA;
    private javax.swing.JDialog Configuracion;
    private javax.swing.JDialog ExcelEstad;
    private javax.swing.JDialog Key;
    private javax.swing.JDialog RutaFiles;
    private javax.swing.JDialog SelecFolio;
    private javax.swing.JDialog SendMail;
    private javax.swing.JDialog TablaStatus;
    private javax.swing.JMenu acerca;
    private javax.swing.JDialog acercade;
    private javax.swing.JMenuItem aniadir;
    private javax.swing.JDialog buscapaciente;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.ButtonGroup buttonGroup5;
    private javax.swing.JMenuItem cambiafac;
    private javax.swing.JDialog cambiafactura;
    private javax.swing.JMenuItem captu;
    private javax.swing.JDialog captura;
    private javax.swing.JDialog companiacaptura;
    private javax.swing.JMenuItem conexion;
    private javax.swing.JDialog conf_mail;
    private javax.swing.JMenu config;
    private javax.swing.JMenuItem cons;
    private javax.swing.JDialog conss;
    private javax.swing.JDialog contraesp;
    private javax.swing.JDialog cotizacion;
    private javax.swing.JMenuItem cotizador;
    private javax.swing.JDialog datos_ujat;
    private javax.swing.JMenu datosujat;
    private javax.swing.JMenu fac;
    private javax.swing.JDialog impresion;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton22;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton25;
    private javax.swing.JButton jButton26;
    private javax.swing.JButton jButton27;
    private javax.swing.JButton jButton28;
    private javax.swing.JButton jButton29;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton30;
    private javax.swing.JButton jButton31;
    private javax.swing.JButton jButton32;
    private javax.swing.JButton jButton33;
    private javax.swing.JButton jButton34;
    private javax.swing.JButton jButton35;
    private javax.swing.JButton jButton36;
    private javax.swing.JButton jButton37;
    private javax.swing.JButton jButton38;
    private javax.swing.JButton jButton39;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton40;
    private javax.swing.JButton jButton41;
    private javax.swing.JButton jButton42;
    private javax.swing.JButton jButton43;
    private javax.swing.JButton jButton44;
    private javax.swing.JButton jButton45;
    private javax.swing.JButton jButton46;
    private javax.swing.JButton jButton47;
    private javax.swing.JButton jButton48;
    private javax.swing.JButton jButton49;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton50;
    private javax.swing.JButton jButton51;
    private javax.swing.JButton jButton52;
    private javax.swing.JButton jButton53;
    private javax.swing.JButton jButton54;
    private javax.swing.JButton jButton55;
    private javax.swing.JButton jButton56;
    private javax.swing.JButton jButton57;
    private javax.swing.JButton jButton58;
    private javax.swing.JButton jButton59;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton60;
    private javax.swing.JButton jButton61;
    private javax.swing.JButton jButton62;
    private javax.swing.JButton jButton63;
    private javax.swing.JButton jButton64;
    private javax.swing.JButton jButton65;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox10;
    private javax.swing.JCheckBox jCheckBox11;
    private javax.swing.JCheckBox jCheckBox12;
    private javax.swing.JCheckBox jCheckBox13;
    private javax.swing.JCheckBox jCheckBox14;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JCheckBox jCheckBox6;
    private javax.swing.JCheckBox jCheckBox7;
    private javax.swing.JCheckBox jCheckBox8;
    private javax.swing.JCheckBox jCheckBox9;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox10;
    private javax.swing.JComboBox jComboBox11;
    private javax.swing.JComboBox jComboBox12;
    private javax.swing.JComboBox jComboBox13;
    private javax.swing.JComboBox jComboBox14;
    private javax.swing.JComboBox jComboBox15;
    private javax.swing.JComboBox jComboBox16;
    private javax.swing.JComboBox jComboBox17;
    private javax.swing.JComboBox jComboBox18;
    private javax.swing.JComboBox jComboBox19;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox20;
    private javax.swing.JComboBox jComboBox21;
    private javax.swing.JComboBox jComboBox22;
    private javax.swing.JComboBox jComboBox23;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JComboBox jComboBox4;
    private javax.swing.JComboBox jComboBox5;
    private javax.swing.JComboBox jComboBox6;
    private javax.swing.JComboBox jComboBox7;
    private javax.swing.JComboBox jComboBox8;
    private javax.swing.JComboBox jComboBox9;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser10;
    private com.toedter.calendar.JDateChooser jDateChooser11;
    private com.toedter.calendar.JDateChooser jDateChooser12;
    private com.toedter.calendar.JDateChooser jDateChooser13;
    private com.toedter.calendar.JDateChooser jDateChooser14;
    private com.toedter.calendar.JDateChooser jDateChooser15;
    private com.toedter.calendar.JDateChooser jDateChooser16;
    private com.toedter.calendar.JDateChooser jDateChooser17;
    private com.toedter.calendar.JDateChooser jDateChooser18;
    private com.toedter.calendar.JDateChooser jDateChooser19;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private com.toedter.calendar.JDateChooser jDateChooser20;
    private com.toedter.calendar.JDateChooser jDateChooser21;
    private com.toedter.calendar.JDateChooser jDateChooser22;
    private com.toedter.calendar.JDateChooser jDateChooser23;
    private com.toedter.calendar.JDateChooser jDateChooser24;
    private com.toedter.calendar.JDateChooser jDateChooser25;
    private com.toedter.calendar.JDateChooser jDateChooser26;
    private com.toedter.calendar.JDateChooser jDateChooser27;
    private com.toedter.calendar.JDateChooser jDateChooser28;
    private com.toedter.calendar.JDateChooser jDateChooser29;
    private com.toedter.calendar.JDateChooser jDateChooser3;
    private com.toedter.calendar.JDateChooser jDateChooser30;
    private com.toedter.calendar.JDateChooser jDateChooser31;
    private com.toedter.calendar.JDateChooser jDateChooser4;
    private com.toedter.calendar.JDateChooser jDateChooser5;
    private com.toedter.calendar.JDateChooser jDateChooser6;
    private com.toedter.calendar.JDateChooser jDateChooser7;
    private com.toedter.calendar.JDateChooser jDateChooser8;
    private com.toedter.calendar.JDateChooser jDateChooser9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabel107;
    private javax.swing.JLabel jLabel108;
    private javax.swing.JLabel jLabel109;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel110;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel112;
    private javax.swing.JLabel jLabel113;
    private javax.swing.JLabel jLabel114;
    private javax.swing.JLabel jLabel115;
    private javax.swing.JLabel jLabel116;
    private javax.swing.JLabel jLabel117;
    private javax.swing.JLabel jLabel118;
    private javax.swing.JLabel jLabel119;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel120;
    private javax.swing.JLabel jLabel121;
    private javax.swing.JLabel jLabel122;
    private javax.swing.JLabel jLabel123;
    private javax.swing.JLabel jLabel124;
    private javax.swing.JLabel jLabel125;
    private javax.swing.JLabel jLabel126;
    private javax.swing.JLabel jLabel127;
    private javax.swing.JLabel jLabel128;
    private javax.swing.JLabel jLabel129;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel130;
    private javax.swing.JLabel jLabel131;
    private javax.swing.JLabel jLabel132;
    private javax.swing.JLabel jLabel133;
    private javax.swing.JLabel jLabel134;
    private javax.swing.JLabel jLabel135;
    private javax.swing.JLabel jLabel136;
    private javax.swing.JLabel jLabel137;
    private javax.swing.JLabel jLabel138;
    private javax.swing.JLabel jLabel139;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel140;
    private javax.swing.JLabel jLabel141;
    private javax.swing.JLabel jLabel142;
    private javax.swing.JLabel jLabel143;
    private javax.swing.JLabel jLabel144;
    private javax.swing.JLabel jLabel145;
    private javax.swing.JLabel jLabel146;
    private javax.swing.JLabel jLabel147;
    private javax.swing.JLabel jLabel148;
    private javax.swing.JLabel jLabel149;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel150;
    private javax.swing.JLabel jLabel151;
    private javax.swing.JLabel jLabel152;
    private javax.swing.JLabel jLabel153;
    private javax.swing.JLabel jLabel154;
    private javax.swing.JLabel jLabel155;
    private javax.swing.JLabel jLabel156;
    private javax.swing.JLabel jLabel157;
    private javax.swing.JLabel jLabel158;
    private javax.swing.JLabel jLabel159;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel160;
    private javax.swing.JLabel jLabel161;
    private javax.swing.JLabel jLabel162;
    private javax.swing.JLabel jLabel163;
    private javax.swing.JLabel jLabel164;
    private javax.swing.JLabel jLabel165;
    private javax.swing.JLabel jLabel166;
    private javax.swing.JLabel jLabel167;
    private javax.swing.JLabel jLabel168;
    private javax.swing.JLabel jLabel169;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel170;
    private javax.swing.JLabel jLabel171;
    private javax.swing.JLabel jLabel172;
    private javax.swing.JLabel jLabel173;
    private javax.swing.JLabel jLabel174;
    private javax.swing.JLabel jLabel175;
    private javax.swing.JLabel jLabel176;
    private javax.swing.JLabel jLabel177;
    private javax.swing.JLabel jLabel178;
    private javax.swing.JLabel jLabel179;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel180;
    private javax.swing.JLabel jLabel181;
    private javax.swing.JLabel jLabel182;
    private javax.swing.JLabel jLabel183;
    private javax.swing.JLabel jLabel184;
    private javax.swing.JLabel jLabel185;
    private javax.swing.JLabel jLabel186;
    private javax.swing.JLabel jLabel187;
    private javax.swing.JLabel jLabel188;
    private javax.swing.JLabel jLabel189;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel190;
    private javax.swing.JLabel jLabel191;
    private javax.swing.JLabel jLabel192;
    private javax.swing.JLabel jLabel193;
    private javax.swing.JLabel jLabel194;
    private javax.swing.JLabel jLabel195;
    private javax.swing.JLabel jLabel196;
    private javax.swing.JLabel jLabel197;
    private javax.swing.JLabel jLabel198;
    private javax.swing.JLabel jLabel199;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel200;
    private javax.swing.JLabel jLabel201;
    private javax.swing.JLabel jLabel202;
    private javax.swing.JLabel jLabel203;
    private javax.swing.JLabel jLabel204;
    private javax.swing.JLabel jLabel205;
    private javax.swing.JLabel jLabel206;
    private javax.swing.JLabel jLabel207;
    private javax.swing.JLabel jLabel208;
    private javax.swing.JLabel jLabel209;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel210;
    private javax.swing.JLabel jLabel211;
    private javax.swing.JLabel jLabel212;
    private javax.swing.JLabel jLabel213;
    private javax.swing.JLabel jLabel214;
    private javax.swing.JLabel jLabel215;
    private javax.swing.JLabel jLabel216;
    private javax.swing.JLabel jLabel217;
    private javax.swing.JLabel jLabel218;
    private javax.swing.JLabel jLabel219;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel220;
    private javax.swing.JLabel jLabel221;
    private javax.swing.JLabel jLabel222;
    private javax.swing.JLabel jLabel223;
    private javax.swing.JLabel jLabel224;
    private javax.swing.JLabel jLabel225;
    private javax.swing.JLabel jLabel226;
    private javax.swing.JLabel jLabel227;
    private javax.swing.JLabel jLabel228;
    private javax.swing.JLabel jLabel229;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel230;
    private javax.swing.JLabel jLabel231;
    private javax.swing.JLabel jLabel232;
    private javax.swing.JLabel jLabel233;
    private javax.swing.JLabel jLabel234;
    private javax.swing.JLabel jLabel235;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel46;
    private javax.swing.JPanel jPanel47;
    private javax.swing.JPanel jPanel48;
    private javax.swing.JPanel jPanel49;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel50;
    private javax.swing.JPanel jPanel51;
    private javax.swing.JPanel jPanel52;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JPasswordField jPasswordField2;
    private javax.swing.JPasswordField jPasswordField3;
    private javax.swing.JPasswordField jPasswordField4;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton10;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JRadioButton jRadioButton5;
    private javax.swing.JRadioButton jRadioButton6;
    private javax.swing.JRadioButton jRadioButton7;
    private javax.swing.JRadioButton jRadioButton8;
    private javax.swing.JRadioButton jRadioButton9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane19;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane20;
    private javax.swing.JScrollPane jScrollPane21;
    private javax.swing.JScrollPane jScrollPane22;
    private javax.swing.JScrollPane jScrollPane23;
    private javax.swing.JScrollPane jScrollPane24;
    private javax.swing.JScrollPane jScrollPane25;
    private javax.swing.JScrollPane jScrollPane26;
    private javax.swing.JScrollPane jScrollPane27;
    private javax.swing.JScrollPane jScrollPane28;
    private javax.swing.JScrollPane jScrollPane29;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane30;
    private javax.swing.JScrollPane jScrollPane31;
    private javax.swing.JScrollPane jScrollPane32;
    private javax.swing.JScrollPane jScrollPane33;
    private javax.swing.JScrollPane jScrollPane34;
    private javax.swing.JScrollPane jScrollPane35;
    private javax.swing.JScrollPane jScrollPane36;
    private javax.swing.JScrollPane jScrollPane37;
    private javax.swing.JScrollPane jScrollPane38;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSpinner jSpinner2;
    private javax.swing.JSpinner jSpinner3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTabbedPane jTabbedPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable10;
    private javax.swing.JTable jTable11;
    private javax.swing.JTable jTable12;
    private javax.swing.JTable jTable13;
    private javax.swing.JTable jTable14;
    private javax.swing.JTable jTable15;
    private javax.swing.JTable jTable16;
    private javax.swing.JTable jTable17;
    private javax.swing.JTable jTable18;
    private javax.swing.JTable jTable19;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable20;
    private javax.swing.JTable jTable21;
    private javax.swing.JTable jTable22;
    private javax.swing.JTable jTable23;
    private javax.swing.JTable jTable24;
    private javax.swing.JTable jTable25;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTable jTable5;
    private javax.swing.JTable jTable6;
    private javax.swing.JTable jTable7;
    private javax.swing.JTable jTable8;
    private javax.swing.JTable jTable9;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea10;
    private javax.swing.JTextArea jTextArea11;
    private javax.swing.JTextArea jTextArea12;
    private javax.swing.JTextArea jTextArea13;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextArea jTextArea3;
    private javax.swing.JTextArea jTextArea4;
    private javax.swing.JTextArea jTextArea5;
    private javax.swing.JTextArea jTextArea6;
    private javax.swing.JTextArea jTextArea7;
    private javax.swing.JTextArea jTextArea8;
    private javax.swing.JTextArea jTextArea9;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField100;
    private javax.swing.JTextField jTextField101;
    private javax.swing.JTextField jTextField102;
    private javax.swing.JTextField jTextField103;
    private javax.swing.JTextField jTextField104;
    private javax.swing.JTextField jTextField105;
    private javax.swing.JTextField jTextField106;
    private javax.swing.JTextField jTextField107;
    private javax.swing.JTextField jTextField108;
    private javax.swing.JTextField jTextField109;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField110;
    private javax.swing.JTextField jTextField111;
    private javax.swing.JTextField jTextField112;
    private javax.swing.JTextField jTextField113;
    private javax.swing.JTextField jTextField114;
    private javax.swing.JTextField jTextField115;
    private javax.swing.JTextField jTextField116;
    private javax.swing.JTextField jTextField117;
    private javax.swing.JTextField jTextField118;
    private javax.swing.JTextField jTextField119;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField120;
    private javax.swing.JTextField jTextField121;
    private javax.swing.JTextField jTextField122;
    private javax.swing.JTextField jTextField123;
    private javax.swing.JTextField jTextField124;
    private javax.swing.JTextField jTextField125;
    private javax.swing.JTextField jTextField126;
    private javax.swing.JTextField jTextField127;
    private javax.swing.JTextField jTextField128;
    private javax.swing.JTextField jTextField129;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField130;
    private javax.swing.JTextField jTextField131;
    private javax.swing.JTextField jTextField132;
    private javax.swing.JTextField jTextField133;
    private javax.swing.JTextField jTextField134;
    private javax.swing.JTextField jTextField135;
    private javax.swing.JTextField jTextField136;
    private javax.swing.JTextField jTextField137;
    private javax.swing.JTextField jTextField138;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField20;
    private javax.swing.JTextField jTextField21;
    private javax.swing.JTextField jTextField22;
    private javax.swing.JTextField jTextField23;
    private javax.swing.JTextField jTextField24;
    private javax.swing.JTextField jTextField25;
    private javax.swing.JTextField jTextField26;
    private javax.swing.JTextField jTextField27;
    private javax.swing.JTextField jTextField28;
    private javax.swing.JTextField jTextField29;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField30;
    private javax.swing.JTextField jTextField31;
    private javax.swing.JTextField jTextField32;
    private javax.swing.JTextField jTextField33;
    private javax.swing.JTextField jTextField34;
    private javax.swing.JTextField jTextField35;
    private javax.swing.JTextField jTextField36;
    private javax.swing.JTextField jTextField37;
    private javax.swing.JTextField jTextField38;
    private javax.swing.JTextField jTextField39;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField40;
    private javax.swing.JTextField jTextField41;
    private javax.swing.JTextField jTextField42;
    private javax.swing.JTextField jTextField43;
    private javax.swing.JTextField jTextField44;
    private javax.swing.JTextField jTextField45;
    private javax.swing.JTextField jTextField46;
    private javax.swing.JTextField jTextField47;
    private javax.swing.JTextField jTextField48;
    private javax.swing.JTextField jTextField49;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField50;
    private javax.swing.JTextField jTextField51;
    private javax.swing.JTextField jTextField52;
    private javax.swing.JTextField jTextField53;
    private javax.swing.JTextField jTextField54;
    private javax.swing.JTextField jTextField55;
    private javax.swing.JTextField jTextField56;
    private javax.swing.JTextField jTextField57;
    private javax.swing.JTextField jTextField58;
    private javax.swing.JTextField jTextField59;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField60;
    private javax.swing.JTextField jTextField61;
    private javax.swing.JTextField jTextField62;
    private javax.swing.JTextField jTextField63;
    private javax.swing.JTextField jTextField64;
    private javax.swing.JTextField jTextField65;
    private javax.swing.JTextField jTextField66;
    private javax.swing.JTextField jTextField67;
    private javax.swing.JTextField jTextField68;
    private javax.swing.JTextField jTextField69;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField70;
    private javax.swing.JTextField jTextField71;
    private javax.swing.JTextField jTextField72;
    private javax.swing.JTextField jTextField73;
    private javax.swing.JTextField jTextField74;
    private javax.swing.JTextField jTextField75;
    private javax.swing.JTextField jTextField76;
    private javax.swing.JTextField jTextField77;
    private javax.swing.JTextField jTextField78;
    private javax.swing.JTextField jTextField79;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField80;
    private javax.swing.JTextField jTextField81;
    private javax.swing.JTextField jTextField82;
    private javax.swing.JTextField jTextField83;
    private javax.swing.JTextField jTextField84;
    private javax.swing.JTextField jTextField85;
    private javax.swing.JTextField jTextField86;
    private javax.swing.JTextField jTextField87;
    private javax.swing.JTextField jTextField88;
    private javax.swing.JTextField jTextField89;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JTextField jTextField90;
    private javax.swing.JTextField jTextField91;
    private javax.swing.JTextField jTextField92;
    private javax.swing.JTextField jTextField93;
    private javax.swing.JTextField jTextField94;
    private javax.swing.JTextField jTextField95;
    private javax.swing.JTextField jTextField96;
    private javax.swing.JTextField jTextField97;
    private javax.swing.JTextField jTextField98;
    private javax.swing.JTextField jTextField99;
    private javax.swing.JMenu licenciaa;
    private javax.swing.JMenuBar menufacturas;
    private javax.swing.JMenuItem repor;
    private javax.swing.JDialog selecaseg;
    private javax.swing.JDialog sendcorreo;
    private javax.swing.JDialog tablafolios;
    // End of variables declaration//GEN-END:variables
}
