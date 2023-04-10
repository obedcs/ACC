/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package adminfacturas;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.security.MessageDigest;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author AreaInterpretación
 */
public class Sistema {
    
    public String FechaPeriodo(Calendar c1){
        c1.getInstance();
        c1.add(c1.DATE, 1);
        String dia = Integer.toString(c1.get(Calendar.DATE)-1);
        String mes = Integer.toString(c1.get(Calendar.MONTH)+1);
        String annio = Integer.toString(c1.get(Calendar.YEAR));
        
        if(dia.length() < 2)
            dia = "0" + dia;
        if(mes.length() < 2)
            mes = "0" + mes;
            
        return annio+"-"+mes+"-"+dia;
    }
    
    public String FechaSystem(){
        String fechaS = "";
        Date date = new Date();
        date.getDate();
        Calendar cal=Calendar.getInstance();
        cal.setTime(date);
        fechaS=FechaPeriodo(cal);
        
        return fechaS;
    }
    
    public String HoraSystem() {
        Date ahora = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("HH:mm:ss");
        
        return formateador.format(ahora).toString();
    }
    
    public String ConvierteMoneda(String Monto){
        Locale locale = new Locale("es","MX"); // elegimos Mexico
        NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
        
        return nf.format(Float.parseFloat(Monto));
    }
    
    public String ConversionFechasTCA(String fecha, int tipo){
        String dia="",mes="",annio="",fechaa="";
        char [] cadena= new char[7];
        cadena = fecha.toCharArray();
        
        annio=Character.toString(cadena[0])+Character.toString(cadena[1])+Character.toString(cadena[2])+Character.toString(cadena[3]);
        mes=Character.toString(cadena[5])+Character.toString(cadena[6]);
        dia=Character.toString(cadena[8])+Character.toString(cadena[9]);
        
        if(tipo == 0) fechaa = annio+"-"+mes+"-"+dia;
        else{ 
            if(tipo == 1) fechaa = annio+"_"+mes;
            else{ fechaa = dia+"/"+mes+"/"+annio; }
        }
        return fechaa;
    }
    
    public String ConversionDateTCA(String fecha){
        String dia="",mes="",annio="";
        char [] cadena= new char[7];
        cadena = fecha.toCharArray();
        
        annio=Character.toString(cadena[0])+Character.toString(cadena[1])+Character.toString(cadena[2])+Character.toString(cadena[3]);
        mes=Character.toString(cadena[4])+Character.toString(cadena[5]);
        dia=Character.toString(cadena[6])+Character.toString(cadena[7]);
        
        
        return annio+"-"+mes+"-"+dia;
    }
    
    public String ConversionFechasMySQLaTCA(String FechaMySQL){
        String FechaTCA="",anio="",mes="",dia="";
        char [] cadena= new char[9];
        cadena = FechaMySQL.toCharArray();
        anio=Character.toString(cadena[0])+Character.toString(cadena[1])+Character.toString(cadena[2])+Character.toString(cadena[3]);
        mes=Character.toString(cadena[5])+Character.toString(cadena[6]);
        dia=Character.toString(cadena[8])+Character.toString(cadena[9]);
        
        FechaTCA = anio+mes+dia;
        return FechaTCA;
    }
    
    public String ConversionFormatosTCA(int numero,String valor){
        int espacios = numero - valor.length();
        String nuevacadena="";
        
        for(int i=0;i<espacios;i++)
             nuevacadena = nuevacadena+" ";
        
        nuevacadena = nuevacadena+valor;
        
        return ("'"+nuevacadena+"'");
    }
    
    public int DiferenciaDias(String fecha){
        int dia = 0;
        try {
            DateFormat formatter ; 
            Date date ; 
            formatter = new SimpleDateFormat("yyyy-MM-dd");
            date = (Date)formatter.parse(fecha); 
            Calendar cal=Calendar.getInstance();
            cal.setTime(date);
            dia = cal.get(Calendar.DAY_OF_YEAR);
        }
        catch (ParseException e){System.out.println("Exception :"+e); dia=0;}
        
        return dia;
    }
    
    public String Encripta(String Texto){
        char array[]=Texto.toCharArray();
        String texto_encriptado="";
        
        for(int i=0;i<array.length;i++){
            array[i]=(char)(array[i]*(char)5);
        }
        texto_encriptado =String.valueOf(array);
        
        return texto_encriptado;
    }
    
    public String Desencripta(String Texto){
        char arrayD[]=Texto.toCharArray();
        String texto_desencriptado="";
        
        for(int i=0;i<arrayD.length;i++){
            arrayD[i]=(char)(arrayD[i]/(char)5);
        }
        texto_desencriptado =String.valueOf(arrayD);
        
        return texto_desencriptado;
    }
    
    public String EncriptarMD5(String texto) {
 
        String secretKey = "qualityinfosolutions"; //llave para encriptar datos
        String base64EncryptedString = "";
 
        try {
 
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
 
            SecretKey key = new SecretKeySpec(keyBytes, "DESede");
            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.ENCRYPT_MODE, key);
 
            byte[] plainTextBytes = texto.getBytes("utf-8");
            byte[] buf = cipher.doFinal(plainTextBytes);
            byte[] base64Bytes = Base64.encodeBase64(buf);
            base64EncryptedString = new String(base64Bytes);
 
        } catch (Exception ex) {}
        return base64EncryptedString;
    }
    
    public String DesencriptarMD5(String textoEncriptado) throws Exception {
 
        String secretKey = "qualityinfosolutions"; //llave para desenciptar datos
        String base64EncryptedString = "";
 
        try {
            byte[] message = Base64.decodeBase64(textoEncriptado.getBytes("utf-8"));
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
            SecretKey key = new SecretKeySpec(keyBytes, "DESede");
 
            Cipher decipher = Cipher.getInstance("DESede");
            decipher.init(Cipher.DECRYPT_MODE, key);
 
            byte[] plainText = decipher.doFinal(message);
 
            base64EncryptedString = new String(plainText, "UTF-8");
 
        } catch (Exception ex) {}
        return base64EncryptedString;
    }
    
    public int esConsonante(char a){
        if((a == 'A')||(a == 'E')||(a == 'I')||(a == 'O')||(a == 'U'))
            return 0; //0 quiere decir que es vocal
        else return 1; //1 quiere decir que es consonante
    }
    
    public int Divisible2(char a){
        int b = Integer.parseInt(String.valueOf(a)) % 2;
        if((b == 0)) return 0;  //0 quiere decir que el numero es divisible entre 2 o es par
        else return 1;
    }
    
    public int Letra(char a){
        if(Character.isDigit(a) == true) return 0; //0 es numero
        else return 1; //1 es letra
    }
    
    public int checkDVerif(char Lic[]){
        int suma = Integer.parseInt(String.valueOf(Lic[1]))+Integer.parseInt(String.valueOf(Lic[6]))+
                   Integer.parseInt(String.valueOf(Lic[10]))+Integer.parseInt(String.valueOf(Lic[12]))+
                   Integer.parseInt(String.valueOf(Lic[18]))+Integer.parseInt(String.valueOf(Lic[20]))+
                   Integer.parseInt(String.valueOf(Lic[27]))+Integer.parseInt(String.valueOf(Lic[29]));
        
        return suma/2;
    }
    
    public String[] VerificaLicencia(String licencia){
        char Lic[] = new char[30];
        String Regresa[] = new String[2];
        Lic = licencia.toCharArray();
        int verificador = 0;
        Regresa[0] = String.valueOf(""+Lic[1]+Lic[12]+Lic[10]+Lic[6]+Lic[5]+Lic[29]+Lic[27]+Lic[11]+Lic[20]+Lic[18]);
        
        if((esConsonante(Lic[0]) == 1)&&(esConsonante(Lic[16]) == 1)&&(esConsonante(Lic[26]) == 1)&&(esConsonante(Lic[15]) == 0)&&(esConsonante(Lic[28]) == 0)){
            if(Divisible2(Lic[2]) == 0){
                if((Lic[3] >= 'D')&&(Lic[3] <= 'K')){
                    if(Divisible2(Lic[4]) == 1){
                        if((Letra(Lic[7]) == 1)&&(Letra(Lic[8]) < 'L')&&(Letra(Lic[9]) == 1)){
                           if(Integer.parseInt(String.valueOf(Lic[13])) < 10){
                               if(Divisible2(Lic[14]) == 0){
                                   if((Integer.parseInt(String.valueOf(Lic[19])) >= 5)&&(Integer.parseInt(String.valueOf(Lic[19])) <= 9)){
                                        if((Letra(Lic[21]) == 0)&&(Letra(Lic[22]) == 1)){
                                            if((Integer.parseInt(String.valueOf(Lic[24])) >= 1)&&(Integer.parseInt(String.valueOf(Lic[24])) <= 6)){
                                                if((Lic[25] >= 'A')&&(Lic[25] <= 'F')){
                                                    if(Integer.parseInt(String.valueOf(Lic[30])) == checkDVerif(Lic)) verificador = 0;
                                                    else verificador ++;
                                                } else verificador ++;
                                            } else verificador ++;
                                        } else verificador ++;
                                   } else verificador ++;
                               } else verificador ++;
                           } else verificador ++;
                        } else verificador ++;
                    } else verificador ++;
                } else verificador ++;
            } else verificador ++;
        } else verificador ++;
        
        Regresa[1] = Integer.toString(verificador);
        return Regresa;
    }
}
