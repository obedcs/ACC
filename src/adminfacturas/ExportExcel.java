/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adminfacturas;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JTable;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 *
 * @author Obed
 */
public class ExportExcel {
    private File file;
    private ArrayList<JTable> tabla;
    private ArrayList<String> nomfiles;
    
    public ExportExcel(File file, ArrayList<JTable> tabla,ArrayList<String> nomfiles) throws Exception{
        this.file = file;
        this.tabla = tabla;
        this.nomfiles = nomfiles;
        
        if(nomfiles.size() != tabla.size())
            throw new Exception ("Error");
    }
    
    public boolean Export(){
        try{
            DataOutputStream out = new DataOutputStream(new FileOutputStream(file));
            WritableWorkbook w = Workbook.createWorkbook(out);
            
            for(int i=0;i<tabla.size();i++){
                System.out.println("Tabla Size: "+tabla.size());
                JTable table = tabla.get(i);
                WritableSheet s = w.createSheet(nomfiles.get(i), 0);
                for(int j=0;j<table.getColumnCount();j++){
                    System.out.println("Columna: "+j);
                    for(int k=0;i<table.getRowCount();k++){
                        System.out.println("Fila: "+k);
                        rangeCheck(table.getRowCount());
                        Object object = table.getValueAt(k, j);
                        Label label = new Label(k, j, String.valueOf(object));
                        s.addCell(label);
                    }
                }
            }
            w.write();
            w.close();
            return true;
        }catch (IOException | WriteException e){return false;}
    }
    
    private void  rangeCheck(int index) {
     if (index >= 6)
          throw new IndexOutOfBoundsException(Integer.toString(index));
    }

}
