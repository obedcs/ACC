/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package adminfacturas;

import java.net.URL;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JDialog;
import javax.swing.JFrame;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.swing.JRViewer;
import net.sf.jasperreports.view.JasperViewer;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author Obed
 */
public class Report {
    //direccion para la imagen
    static Connection con = null;
    static Statement st = null;
    static ResultSet rs = null;
    private static JasperPrint report;
    
    public static Date ConvertirFecha(String Fecha){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        
	try {date = formatter.parse(Fecha);} catch (ParseException e) {e.printStackTrace();}
        
        return date;
    }
    
    public static void MostrarContrareciboNormal(int folio,Connection conMySQL,String status, String carpeta, String logo, String logoacc) throws SQLException, JRException {
        Map parametro = new HashMap();
        parametro.put("Folio", folio);
        parametro.put("statusfact", status);
        parametro.put("Logo", logo);
        parametro.put("LogoACC", logoacc);
        java.lang.String url = carpeta+"Contrarecibo.jrxml";
        con = conMySQL;

        JasperReport reportes = JasperCompileManager.compileReport(url);
        JasperPrint print = JasperFillManager.fillReport(reportes, parametro, con);
        
        //para mostrar el reporte        
        JDialog viewer = new JDialog(new JFrame(),"Vista Previa del Reporte", true);
        viewer.setSize(950,700);
        viewer.setLocationRelativeTo(null);
        JRViewer jrv = new JRViewer(print);
        viewer.getContentPane().add(jrv);
        viewer.show();
    }
    
    public static void MostrarContrareciboSaldos(int folio,Connection conMySQL, String carpeta, String logo, String logoacc) throws SQLException, JRException {
        Map parametro = new HashMap();
        parametro.put("Folio", folio);
        parametro.put("Logo", logo);
        parametro.put("LogoACC", logoacc);
        java.lang.String url = carpeta+"ContrareciboSaldos.jrxml";
        con = conMySQL;

        JasperReport reportes = JasperCompileManager.compileReport(url);
        JasperPrint print = JasperFillManager.fillReport(reportes, parametro, con);
        
        //para mostrar el reporte        
        JDialog viewer = new JDialog(new JFrame(),"Vista Previa del Reporte", true);
        viewer.setSize(950,700);
        viewer.setLocationRelativeTo(null);
        JRViewer jrv = new JRViewer(print);
        viewer.getContentPane().add(jrv);
        viewer.show();
    }
    
    public static void MostrarEdoCuenta(Connection conMySQL,String FechaInicio,String FechaFin,String Asegu,String Statuss,String PDF, String carpeta, String logo, String logoacc) throws SQLException, JRException {
        Date Finicio = ConvertirFecha(FechaInicio);
        Date FFin = ConvertirFecha(FechaFin);
        System.out.println(carpeta);
        Map parametro = new HashMap();
        parametro.put("Aseguradora", Asegu);
        parametro.put("FRepIni", Finicio);
        parametro.put("FRepFin", FFin);
        parametro.put("Stat_Factura", Statuss);
        parametro.put("Logo", logo);
        parametro.put("LogoACC", logoacc);
        java.lang.String url = carpeta+"EdoCuenta.jrxml";
        con = conMySQL;

        JasperReport reportes = JasperCompileManager.compileReport(url);
        JasperPrint print = JasperFillManager.fillReport(reportes, parametro, con);
        
        if(PDF.equals("")){
            //para mostrar el reporte        
            JDialog viewer = new JDialog(new JFrame(),"Vista Previa del Reporte", true);
            viewer.setSize(950,700);
            viewer.setLocationRelativeTo(null);
            JRViewer jrv = new JRViewer(print);
            viewer.getContentPane().add(jrv);
            viewer.show();
        }
        else{
            JasperExportManager.exportReportToPdfFile( print, PDF);
        }
        
    }
    
    public static void MostrarEdoFactura(Connection conMySQL,int Folio, String carpeta, String logo, String logoacc) throws SQLException, JRException {
        Map parametro = new HashMap();
        parametro.put("NumFactura", Folio);
        parametro.put("Logo", logo);
        parametro.put("LogoACC", logoacc);
        java.lang.String url = carpeta+"Factura.jrxml";
        con = conMySQL;

        JasperReport reportes = JasperCompileManager.compileReport(url);
        JasperPrint print = JasperFillManager.fillReport(reportes, parametro, con);
        
        //para mostrar el reporte        
        JDialog viewer = new JDialog(new JFrame(),"Vista Previa del Reporte", true);
        viewer.setSize(950,700);
        viewer.setLocationRelativeTo(null);
        JRViewer jrv = new JRViewer(print);
        viewer.getContentPane().add(jrv);
        viewer.show();
    }
    
    public static void MostrarRepEstPeriodo(Connection conMySQL,String FechaInicio,String FechaFin, String carpeta, String logo, String logoacc) throws SQLException, JRException {
        Date Finicio = ConvertirFecha(FechaInicio);
        Date FFin = ConvertirFecha(FechaFin);
        
        Map parametro = new HashMap();
        parametro.put("Inicio", Finicio);
        parametro.put("Fin", FFin);
        parametro.put("Logo", logo);
        parametro.put("LogoACC", logoacc);
        con = conMySQL;

        JasperReport reportes = JasperCompileManager.compileReport(carpeta+"ReporteEstadPeriodo.jrxml");
        JasperPrint print = JasperFillManager.fillReport(reportes, parametro, con);
        
        //para mostrar el reporte        
        JDialog viewer = new JDialog(new JFrame(),"Vista Previa del Reporte", true);
        viewer.setSize(950,700);
        viewer.setLocationRelativeTo(null);
        JRViewer jrv = new JRViewer(print);
        viewer.getContentPane().add(jrv);
        viewer.show();
    }
    
    public static void MostrarRepConcentrado(Connection conMySQL,String FechaInicio,String FechaFin, String carpeta, String logo, String logoacc) throws SQLException, JRException {
        Date Finicio = ConvertirFecha(FechaInicio);
        Date FFin = ConvertirFecha(FechaFin);
        
        Map parametro = new HashMap();
        parametro.put("Inicio", Finicio);
        parametro.put("Fin", FFin);
        parametro.put("Logo", logo);
        parametro.put("LogoACC", logoacc);
        con = conMySQL;

        JasperReport reportes = JasperCompileManager.compileReport(carpeta+"ReporteConcentrado.jrxml");
        JasperPrint print = JasperFillManager.fillReport(reportes, parametro, con);
        
        //para mostrar el reporte        
        JDialog viewer = new JDialog(new JFrame(),"Vista Previa del Reporte", true);
        viewer.setSize(950,700);
        viewer.setLocationRelativeTo(null);
        JRViewer jrv = new JRViewer(print);
        viewer.getContentPane().add(jrv);
        viewer.show();
    }
    
    public static void MostrarRepStatus(Connection conMySQL,String FechaInicio,String FechaFin, String carpeta, String logo, String logoacc) throws SQLException, JRException {
        Date Finicio = ConvertirFecha(FechaInicio);
        Date FFin = ConvertirFecha(FechaFin);
        
        Map parametro = new HashMap();
        parametro.put("Inicio", Finicio);
        parametro.put("Fin", FFin);
        parametro.put("Logo", logo);
        parametro.put("LogoACC", logoacc);
        con = conMySQL;

        JasperReport reportes = JasperCompileManager.compileReport(carpeta+"ReporteConcentradoStatus.jrxml");
        JasperPrint print = JasperFillManager.fillReport(reportes, parametro, con);
        
        //para mostrar el reporte        
        JDialog viewer = new JDialog(new JFrame(),"Vista Previa del Reporte", true);
        viewer.setSize(950,700);
        viewer.setLocationRelativeTo(null);
        JRViewer jrv = new JRViewer(print);
        viewer.getContentPane().add(jrv);
        viewer.show();
    }
    
    public static void MostrarRepAntSald(Connection conMySQL,String FechaInicio,String FechaFin,int Tipo, String carpeta, String logo, String logoacc) throws SQLException, JRException {
        Date Finicio = ConvertirFecha(FechaInicio);
        Date FFin = ConvertirFecha(FechaFin);
        String Reporte="";
        
        if(Tipo == 0)
            Reporte="AntigSaldos.jrxml";
        else
            Reporte="AntigSaldosFFact.jrxml";
        
        System.out.println(carpeta+Reporte);
        
        Map parametro = new HashMap();
        parametro.put("Inicio", Finicio);
        parametro.put("Fin", FFin);
        parametro.put("Logo", logo);
        parametro.put("LogoACC", logoacc);
        con = conMySQL;

        JasperReport reportes = JasperCompileManager.compileReport(carpeta+Reporte);
        JasperPrint print = JasperFillManager.fillReport(reportes, parametro, con);
        
        //para mostrar el reporte        
        JDialog viewer = new JDialog(new JFrame(),"Vista Previa del Reporte", true);
        viewer.setSize(950,700);
        viewer.setLocationRelativeTo(null);
        JRViewer jrv = new JRViewer(print);
        viewer.getContentPane().add(jrv);
        viewer.show();
    }
    
    public static void MostrarRepLiq(Connection conMySQL,String FechaInicio,String FechaFin, String carpeta, String logo, String logoacc) throws SQLException, JRException {
        Date Finicio = ConvertirFecha(FechaInicio);
        Date FFin = ConvertirFecha(FechaFin);
        String Reporte="";
        
        Reporte="Estadist_Companias.jrxml";
        
        Map parametro = new HashMap();
        parametro.put("Ini", Finicio);
        parametro.put("Fin", FFin);
        parametro.put("Logo", logo);
        parametro.put("LogoACC", logoacc);
        con = conMySQL;

        JasperReport reportes = JasperCompileManager.compileReport(carpeta+Reporte);
        JasperPrint print = JasperFillManager.fillReport(reportes, parametro, con);
        
        //para mostrar el reporte        
        JDialog viewer = new JDialog(new JFrame(),"Vista Previa del Reporte", true);
        viewer.setSize(950,700);
        viewer.setLocationRelativeTo(null);
        JRViewer jrv = new JRViewer(print);
        viewer.getContentPane().add(jrv);
        viewer.show();
    }
    
    public static void MostrarRepUJAT(Connection conXML,String folio, String carpeta, String logo, String logoacc) throws SQLException, JRException {
        String Reporte=carpeta+"UJAT_Adicionales.jrxml";
        
        Map parametro = new HashMap();
        parametro.put("Folio", folio);
        parametro.put("Logo", logo);
        parametro.put("LogoACC", logoacc);
        
        con = conXML;

        JasperReport reportes = JasperCompileManager.compileReport(Reporte);
        JasperPrint print = JasperFillManager.fillReport(reportes, parametro, con);
        
        /*para mostrar el reporte        
        JDialog viewer = new JDialog(new JFrame(),"Vista Previa del Reporte", true);
        viewer.setSize(950,700);
        viewer.setLocationRelativeTo(null);
        JRViewer jrv = new JRViewer(print);
        viewer.getContentPane().add(jrv);
        viewer.show();*/
        
        JasperPrintManager.printReport(print, false);
    }
    
    public static void Cotizacionn(Connection conXML,String cotiz,int tiporep, String carpeta, String carpetaPDF) throws SQLException, JRException {
        Map parametro = new HashMap();
        parametro.put("cotiza", cotiz);
        java.lang.String url = carpeta+"Cotizacion.jrxml";
        con = conXML;

        JasperReport reportes = JasperCompileManager.compileReport(url);
        JasperPrint print = JasperFillManager.fillReport(reportes, parametro, con);
        
        if(tiporep == 0){
            //para mostrar el reporte        
            JDialog viewer = new JDialog(new JFrame(),"Vista Previa del Reporte", true);
            viewer.setSize(950,700);
            viewer.setLocationRelativeTo(null);
            JRViewer jrv = new JRViewer(print);
            viewer.getContentPane().add(jrv);
            viewer.show();
        }
        else{
            if(tiporep == 1) JasperPrintManager.printReport(print, false);
            else JasperExportManager.exportReportToPdfFile(print,carpetaPDF+cotiz+".pdf");
        }
    }
}


