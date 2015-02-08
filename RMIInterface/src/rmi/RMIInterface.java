
package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

import common.*;


public interface RMIInterface extends Remote {
	public void receiveMessage(MessageInfo msg) throws RemoteException;
}
