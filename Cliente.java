/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redes2;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import static redes2.Servidor.doType;

/**
 *
 * @author Wauricio
 */
public class Cliente {
    
    public static void sendFiles(HashMap<Object,Object> local , HashMap<Object,Object> outf ,String host , int port , int buff) throws Exception{
      
        Socket c=new Socket(host,port);
        c.setReceiveBufferSize(buff);
        c.setSendBufferSize(buff);
        ObjectOutputStream out = new ObjectOutputStream(c.getOutputStream());
       /* HashMap<Object,Object> file = new HashMap<Object,Object>();
        HashMap<Object,Object> file2 = new HashMap<Object,Object>();
        File f = new File("libros.sql");
        File f2 = new File("prueba.txt");
        File f3 = new File("prueba.txt");
        file2.put(f3.getName(),(int)f3.length());
        System.out.println(f.getName()+" "+f.length());
        
        file.put(f.getName(),(int)f.length());
         file.put(f2.getName(),(int)f2.length());
         file.put(file2,"nuevaCarpeta");*/
        Package pak=new Package(outf,buff,true,1000);
        out.writeObject(pak);
       // out.flush();
       // out.close();
        DataOutputStream outdt = new DataOutputStream(c.getOutputStream());
        sendFiles(local,outdt,1500);
        
        out.close();
        outdt.close();
        c.close();
            
            
            
            
        
        
        
        
    }
    
    public static void sendFiles(HashMap<Object,Object> files , final DataOutputStream out, final int buffSize) throws Exception{
        for (Map.Entry<Object,Object> entry : files.entrySet()) {
                if(entry.getKey() instanceof String){
                    String name = (String)entry.getKey();
                    int temp=0;
                    int send=0,size= (Integer)entry.getValue();
                    byte[] buff = new byte[buffSize];
                    FileInputStream fileout = new FileInputStream(name);
                    while(send<size){
                        temp=fileout.read(buff);
                        send+=temp;
                        out.write(buff,0,temp);
                        out.flush();
                        System.out.println("Informacion enviada send :"+send+" proce: "+ ((send*100)/size));
                    }  
                }
                else if (entry.getKey() instanceof HashMap)
                {   //HashMap<Object,Object> p=(HashMap<Object,Object>)entry.getKey();
                    /*for (Map.Entry<Object,Object> entry2 : p.entrySet()) {
                        doType(entry.getKey(),entry.getValue(),in,nPath+"\\",buffSize);
                    }  */
                    
                    
                    sendFiles((HashMap<Object,Object>)entry.getKey(),out,buffSize);
                }
           
        }
    }
    
    static void ArrayToMap(final HashMap<Object,Object> filesout ,final HashMap<Object,Object> filesAbs ,ArrayList<File> filesin) throws Exception{
        for(File f:filesin){
            if(f.isDirectory()){
                File[]files =f.listFiles();
                ArrayList<File> tem = new ArrayList<File>();
                HashMap<Object,Object> filetempout = new HashMap<Object,Object>();
                HashMap<Object,Object> filetempAbs = new HashMap<Object,Object>();
                for(File f1:files){
                     tem.add(f1);
                 }
                ArrayToMap(filetempout,filetempAbs,tem);               
                filesout.put(filetempout,f.getName());
                filesAbs.put(filetempAbs, f.getName());
                
            }else{
                filesout.put(f.getName(),(int)f.length());
                filesAbs.put(f.getCanonicalPath(),(int) f.length());
            }
   
        }

    }
    
    static void vamoAprobar(HashMap<Object,Object> in ){
        for (Map.Entry<Object,Object> entry : in.entrySet()) {
                if(entry.getKey() instanceof String){
                    String name = (String)entry.getKey();
                    int send=0,size= (Integer)entry.getValue();
                    System.out.println("Es un Archivo name: " +name+ "sizee "+size);
                    
                }
                else if (entry.getKey() instanceof HashMap)
                {  // HashMap<Object,Object> p=(HashMap<Object,Object>)entry.getKey();
                    /*for (Map.Entry<Object,Object> entry2 : p.entrySet()) {
                        doType(entry.getKey(),entry.getValue(),in,nPath+"\\",buffSize);
                    }  */
                    System.out.println("Es un directrio" + (String)entry.getValue() );
                    
                    vamoAprobar((HashMap<Object,Object>)entry.getKey());
                }
           
        }
    }
    
    
}
