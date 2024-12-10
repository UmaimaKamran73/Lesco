package Controller;


import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class client{
    public static void main(String[] args) throws IOException{  
       Socket s=new Socket("192.168.56.1",12345);  
       DataInputStream din=new DataInputStream(s.getInputStream());  
       DataOutputStream dout=new DataOutputStream(s.getOutputStream());  
       BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
       String str="",str2="";  
       while(!str.equals("stop")){  
       str=br.readLine();  
       dout.writeUTF(str);  
       dout.flush();
       System.out.println("waiting fo server reply");
       str2=din.readUTF();  
       System.out.println("Server says: "+str2);  
       }  
       dout.close();  
       s.close();  

    }
}

