/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pod.receiver;

import br.com.pod.interfacesremotas.Receiver;
import br.com.pod.interfacesremotas.ServerApp;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Marcelo Augusto
 */
public class ReceiverImpl extends UnicastRemoteObject implements Receiver{

    protected ReceiverImpl() throws RemoteException {
        super();
    }

    @Override
    public String receive(String msg) throws RemoteException {
        try {
            ServerApp serverApp = getServerApp();
            return serverApp.processMessage(msg);
        } catch (NotBoundException | RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ServerApp getServerApp() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry("localhost", 8080);
        ServerApp server = (ServerApp) registry.lookup("ServerApp");
        return server;
    }
}
