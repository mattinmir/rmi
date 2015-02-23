
package rmi;

import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

import common.*;


public class RMIClient {

	public static void main(String[] args) throws RemoteException 
	{

		RMIServerI iRMIServer = null;

		// Check arguments for Server host and number of messages
		if (args.length != 2){
			System.out.println("Needs 2 arguments: "
					+ "ServerHostName/IPAddress, TotalMessageCount");
			System.exit(-1);
		}

		String urlServer = new String(args[0]);
		int numMessages = Integer.parseInt(args[1]);

		if (System.getSecurityManager() == null)
	        System.setSecurityManager   (new RMISecurityManager());
		
		try 
		{
			Registry registry = LocateRegistry.getRegistry(urlServer);
			iRMIServer = (RMIServerI) registry.lookup("RMIServer");
			
			for (int i = 0; i < numMessages; i++) 
			{
				iRMIServer.receiveMessage(
						new MessageInfo(numMessages, i));
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
