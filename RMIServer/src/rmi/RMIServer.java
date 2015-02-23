/*
 * Created on 07-Sep-2004
 * @author bandara
 */
package rmi;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.ArrayList;

import rmi.RMIServerI;
import common.*;



public class RMIServer implements RMIServerI {                                    


	private static final long serialVersionUID = 52L;
	private Integer totalMessages = -1;
	private Boolean[] receivedMessages;
	private static final int port = 1099;

	public RMIServer() throws RemoteException {}

	public void receiveMessage(MessageInfo msg) throws 
	RemoteException,FileNotFoundException,
	UnsupportedEncodingException
	{
		// TO-DO: On receipt of first message, initialise the receive buffer
		if(msg.messageNum == 0)
		{
			totalMessages = msg.totalMessages;
			receivedMessages = new Boolean[totalMessages];
			for (Integer i = 0; i < totalMessages; i++)
				receivedMessages[i] = false;
		}
		
		// TO-DO: Log receipt of the message
		receivedMessages[msg.messageNum] = true;
				
		// TO-DO: If this is the last expected message, then identify
		//        any missing messages
		
		if(msg.messageNum == msg.totalMessages - 1)
		{
			ArrayList<Integer> missing = new ArrayList<Integer>();
			int totalRecieved = 0;
			for(Integer i = 0; i < totalMessages; i++)
			{
				if(!receivedMessages[i])
					missing.add(i);
				else
					totalRecieved++;
			}
			// Print info about lost messages
			System.out.println("Number of Recieved Messages: " + totalRecieved);
			System.out.println("Number of Lost messages: " + missing.size());
			System.out.println("See RMI_lost.txt for lost messages.");
			PrintWriter writer = new PrintWriter("RMI_lost.txt", "UTF-8");
			for (int i = 0; i < missing.size(); i++) 
			{
				writer.println(i);
			}
			writer.close();
		}
		
		
	}

	public static void main(String[] args) throws AlreadyBoundException, IOException 
	{

		RMIServer server = null;
		
		// TO-DO: Initialise Security Manager
		if (System.getSecurityManager() == null)
	        System.setSecurityManager(new RMISecurityManager());
		
		// TO-DO: Instantiate the server class
		
		
		// TO-DO: Bind to RMI registry		
//		try // try to bind to registry if already open
//		{
//			Registry registry = LocateRegistry.getRegistry(Registry.REGISTRY_PORT);
//			RMIServerI stub = (RMIServerI) UnicastRemoteObject.exportObject(server, Registry.REGISTRY_PORT);
//			registry.bind("RMIServer", stub);
//		}
//		catch(Exception e) // If not already open, create it and rebind
//		{
//			rebindServer("RMIServer", server);
//		}
		
		try
		{
			server = new RMIServer();
			rebindServer("RMIServer", server);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(-1);
		}
		System.out.println("Server bound to registry using port: " 
			+ port);
		System.out.println("Hit enter key to kill the server.");
		System.in.read();
		System.exit(0);
	}

	protected static void rebindServer(String serverName, 
			RMIServer server) throws RemoteException
	{

		// TO-DO:
		// Start / find the registry (hint use LocateRegistry.createRegistry(...)
		// If we *know* the registry is running we could skip this (eg run rmiregistry in the start script)
		Registry registry = LocateRegistry.createRegistry(port);
		RMIServerI stub = (RMIServerI) UnicastRemoteObject.
				exportObject(server, port);
		
		
		// TO-DO:
		// Now rebind the server to the registry (rebind replaces any existing servers bound to the serverURL)
		// Note - Registry.rebind (as returned by createRegistry / getRegistry) does something similar but
		// expects different things from the URL field.
		
		
		//registry.rebind(serverURL, server);
		
		registry.rebind(serverName, stub);
	}

}
