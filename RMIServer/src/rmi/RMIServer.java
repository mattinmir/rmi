/*
 * Created on 07-Sep-2004
 * @author bandara
 */
package rmi;

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

import rmi.RMIInterface;



import common.*;



public class RMIServer extends UnicastRemoteObject implements RMIInterface {


	private static final long serialVersionUID = 52L;
	private Integer totalMessages = -1;
	private Boolean[] receivedMessages;

	public RMIServer() throws RemoteException {}

	public void receiveMessage(MessageInfo msg) throws RemoteException {

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
			for(Integer i = 0; i < totalMessages; i++)
			{
				if(!receivedMessages[i])
					missing.add(i);
			}
		}

	}


	public static void main(String[] args) throws RemoteException, AlreadyBoundException {

		RMIServer rmis = null;

		// TO-DO: Initialise Security Manager
		if (System.getSecurityManager() == null)
	    {
	        System.setSecurityManager   (new RMISecurityManager());
	    }
		// TO-DO: Instantiate the server class
		RMIServer server = new RMIServer();
		
		RMIInterface stub = (RMIInterface) UnicastRemoteObject.exportObject(server, 2222);
		
		// TO-DO: Bind to RMI registry
		Registry registry = LocateRegistry.getRegistry();
        registry.bind("Hello", stub);
	}

	protected static void rebindServer(String serverURL, RMIServer server) throws RemoteException {

		// TO-DO:
		// Start / find the registry (hint use LocateRegistry.createRegistry(...)
		// If we *know* the registry is running we could skip this (eg run rmiregistry in the start script)
		Registry registry = LocateRegistry.getRegistry(serverURL, Constant.RMI_PORT);
		// TO-DO:
		// Now rebind the server to the registry (rebind replaces any existing servers bound to the serverURL)
		// Note - Registry.rebind (as returned by createRegistry / getRegistry) does something similar but
		// expects different things from the URL field.
	}
}
