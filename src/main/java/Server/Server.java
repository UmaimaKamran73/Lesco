
package Server;

import Controller.CustomerController;
import Controller.EmployeeController;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;


public class Server 
{

    
    private static Set<ClientHandler> clientHandlers=ConcurrentHashMap.newKeySet();
    public Server() 
    {
        int port=54321;
        try(ServerSocket serverSocket=new ServerSocket(port))
        {
            //System.out.println("Local Socket Address: "+ serverSocket.getLocalSocketAddress());
            //System.out.println("Channel: "+ serverSocket.getChannel());
            //System.out.println("Local Port: "+ serverSocket.getLocalPort());
            System.out.println("Inet Address: "+ serverSocket.getInetAddress());
            InetAddress localhost=InetAddress.getLocalHost();
            System.out.println("Server IP Address is "+ localhost.getHostAddress()+" "+localhost.getHostName());
            System.out.println("Server is running and waiting for connections...");
            
            while(true)
            {
                Socket clientSocket=serverSocket.accept();
                System.out.println("New client connected: "+clientSocket);
                
                InitializeAll init= new InitializeAll();
                CustomerController customerController= init.getCustomerController();
                EmployeeController employeeController= init.getEmployeeController();
                ClientHandler clientHandler=new ClientHandler(clientSocket,customerController,employeeController);
                clientHandlers.add(clientHandler);
                
                new Thread(clientHandler).start();
            }
        }   
        catch (IOException e) 
        {
            System.out.println("Server error: "+e.getMessage());
        }
    }
    
   /* public void showMessage(String e,ClientHandler c)
    {
        System.out.println(e);
    } */
    
    public static void removeClient(ClientHandler clientHandler) 
    {
        clientHandlers.remove(clientHandler);
    }
    
} 
