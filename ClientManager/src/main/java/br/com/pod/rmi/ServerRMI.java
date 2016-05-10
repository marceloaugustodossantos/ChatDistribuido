package br.com.pod.rmi;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import br.com.pod.clientmanager.ReceivingNotifyImpl;

public class ServerRMI {

    public static void main(String[] args) throws RemoteException, NotBoundException, AlreadyBoundException {
        System.out.println("client menager");
        Registry registry = LocateRegistry.createRegistry(1234);
        registry.bind("ReceiverNotify", new ReceivingNotifyImpl());
    }
}
