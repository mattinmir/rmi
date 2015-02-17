
package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import common.*;


public interface RMIServerInterface extends Remote 
{
	public ArrayList<Integer> receiveMessage(MessageInfo msg) throws RemoteException;
}