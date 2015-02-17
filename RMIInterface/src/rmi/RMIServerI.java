
package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import common.*;


public interface RMIServerI extends Remote 
{
	public void receiveMessage(MessageInfo msg) throws RemoteException;
}