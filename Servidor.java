/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redes2;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Servidor {
    public static void main(String args[]){
        try{
        ServerSocket srv=new ServerSocket(1000);
        int i=0;   
            while(true){
                System.out.println("Escuchando Cliente");
                Socket clt=srv.accept();
                ObjectInputStream pack= new ObjectInputStream(clt.getInputStream());
                DataInputStream  ins = new  DataInputStream (clt.getInputStream());
                Object in=pack.readObject();//Objecto Packete
                //pack.close();
                if(in instanceof Package){
                    System.out.println("recibio paquete");
                    Package p =(Package)in;
                    clt.setReceiveBufferSize(p.getBuffSizel());
                    doCustomer(p,ins,clt.getInetAddress().getHostAddress());
                }
                pack.close();
                ins.close();
                clt.close();
                
            }
        

        }catch(Exception e){
            e.printStackTrace();
        }

    }
    
    private static void doCustomer(Package p ,  DataInputStream in , String addres) throws Exception{
        byte[] buffer = new byte[1500];
        String path=addres;
        System.out.println("Path"+path);
        File f = new  File(path);
        if(f.mkdir()){
             for (Map.Entry<Object,Object> entry : p.getFileTree().entrySet()) {
                     doType(entry.getKey(),entry.getValue(),in,path+"\\",p.getBuffSizel());
            }
        }
        
           
    }
    
    public static void doType(Object key,Object val ,final DataInputStream in ,final String path,final int buffSize) throws Exception{
        if(key instanceof String){
                String name=(String)key;
                int size=(Integer)val;
                System.out.println("name :"+name+" size:"+size);
                createFile(size,buffSize,name,in,path);
            
            }    
            else if(key instanceof HashMap){
                String nPath=path+(String)val;
                 File f = new  File(nPath);
                 if(f.mkdir()){
                  HashMap<Object,Object> p=(HashMap<Object,Object>)key;
                    for (Map.Entry<Object,Object> entry : p.entrySet()) {
                        doType(entry.getKey(),entry.getValue(),in,nPath+"\\",buffSize);
                    }   
                 }
            }
        
    }
    
    
    public static void createFile(int fileSize ,int buffSize ,String name , DataInputStream  in , String path)throws IOException{
        byte[] buff= new byte[buffSize];
        int temp=0;
        System.out.println("CrearCarchivo size"+fileSize);
        File f = new File(path+name);
        FileOutputStream fileOut = new FileOutputStream(f);
        int read=0;
        while(read<fileSize && read>=0){
            temp=in.read(buff, 0,buffSize);
            read+=temp;
             fileOut.write(buff,0, temp);
            fileOut.flush();
            f.createNewFile();
             System.out.println("read Data : "+((read*100)/fileSize));
        }
        fileOut.close();
    }
    
 
    
}
