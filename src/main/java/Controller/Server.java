package Controller;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
public class Server {

    
        //initialize socket and input stream
        private Socket socket = null;
        private ServerSocket server = null;
        private DataInputStream in     = null;
        private DataOutputStream out     = null;
    
        // constructor with port
        public Server(int port)
        {
            InetAddress ip;
            try {
                ip = InetAddress.getLocalHost();
                System.out.println("Current IP address : " + ip.getHostAddress());
            } catch (UnknownHostException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
    
            // starts server and waits for a connection
            try
            {
                server = new ServerSocket(port);
                System.out.println("Server started");
    
                System.out.println("Waiting for a client ...");
    
                socket = server.accept();
                System.out.println("Client accepted");
    
                // takes input from the client socket
                in = new DataInputStream(
                        new BufferedInputStream(socket.getInputStream()));
                out=new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                Scanner sin=new Scanner(System.in);
                String line = "", str2="";
    
                // reads message from client until "Over" is sent
                while (!line.equals("Over"))
                {
                    try
                    {
                        line = in.readUTF();
                        System.out.println("Client says: "+line);
    
                    }
                    catch(IOException i)
                    {
                        System.out.println(i);
                    }
                    System.out.println("Emter message for clinet");
                    str2=sin.next();
                    out.writeUTF(str2);
                    out.flush();
                }
    
                System.out.println("Closing connection");
    
                // close connection
                socket.close();
                in.close();
            }
            catch(IOException i)
            {
                System.out.println(i);
            }
        }
    
        public static void main(String args[])
        {
    
            Server server = new Server(5001);
    
        }
    }
    

  