package br.com.pod.rmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RemoteObject;

/**
 *
 * @author Pris
 */
public class ClientRMI {

	public RemoteObject getEventBus() throws RemoteException, NotBoundException{
		Registry registry = LocateRegistry.getRegistry("", 0);
		RemoteObject server = (RemoteObject) registry.lookup("EventBus");
		return server;
	}
	public RemoteObject getServerApp() throws RemoteException, NotBoundException{
		Registry registry = LocateRegistry.getRegistry("", 0);
		RemoteObject server = (RemoteObject) registry.lookup("ServerApp");
		return server;
	}
	public RemoteObject getPersistentUnity() throws RemoteException, NotBoundException{
		Registry registry = LocateRegistry.getRegistry("", 0);
		RemoteObject server = (RemoteObject) registry.lookup("PersistentUnit");
		return server;
	}
}
