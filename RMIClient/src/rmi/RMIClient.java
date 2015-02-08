
package rmi;

import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import common.Constant;


public class RMIClient {

	public static void main(String[] args) throws RemoteException {

		RMIInterface iRMIServer = null;

		// Check arguments for Server host and number of messages
		if (args.length < 2){
			System.out.println("Needs 2 arguments: ServerHostName/IPAddress, TotalMessageCount");
			System.exit(-1);
		}

		String urlServer = new String("rmi://" + args[0] + "/RMIServer");
		int numMessages = Integer.parseInt(args[1]);

		// TO-DO: Initialise Security Manager
		if (System.getSecurityManager() == null)
	    {
	        System.setSecurityManager   (new RMISecurityManager());
	    }
		// TO-DO: Bind to RMIServer
		

		// TO-DO: Attempt to send messages the specified number of times

	}
}
