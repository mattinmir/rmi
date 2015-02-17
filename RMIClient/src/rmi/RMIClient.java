
package rmi;

import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

import common.*;


public class RMIClient {

	public static void main(String[] args) throws RemoteException {

		RMIServerInterface iRMIServer = null;

		// Check arguments for Server host and number of messages
		if (args.length != 2){
			System.out.println("Needs 2 arguments: ServerHostName/IPAddress, TotalMessageCount");
			System.exit(-1);
		}

		String urlServer = new String("rmi://" + args[0] + "/RMIServer");
		int numMessages = Integer.parseInt(args[1]);

		// TO-DO: Initialise Security Manager
		if (System.getSecurityManager() == null)
	        System.setSecurityManager   (new RMISecurityManager());
	    
		// TO-DO: Bind to RMIServer
		Registry registry = LocateRegistry.getRegistry(Registry.REGISTRY_PORT);
		try {
			RMIServerInterface remote = (RMIServerInterface) registry.lookup(urlServer);
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TO-DO: Attempt to send messages the specified number of times
		ArrayList<Integer> missing = null;
		for (int i = 0; i < numMessages; i++) 
		{
			missing = iRMIServer.receiveMessage(new MessageInfo(numMessages, i));
		}
		
		// Print info about lost messages
		System.out.println("Lost messages numbers: ");
		for (int i = 0; i < missing.size(); i++) 
		{
			System.out.println(missing.get(i));
		}
	}
}
