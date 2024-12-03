/*
package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;


public class Server 
{
    int port="8080";
    
    ExecuterService executor=Executors.newFixedThreadPool(5);
    public Server() 
    {
        try(ServerSocket serverSocket=new ServerSocket(port))
        {
            System.out.println("Server is running and waiting for clients...");
            
            while(true)
            {
                Socket clientSocket=serverSocket.accept();
                System.out.println("Client Connected: "+clientSocket.getInetAddress());
                executor.submit(new ClientHandler(clientSocket));
                
            }
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());   
        }
    }
    
    
} */
