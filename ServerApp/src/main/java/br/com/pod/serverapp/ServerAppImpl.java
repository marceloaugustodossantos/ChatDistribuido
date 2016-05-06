/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pod.serverapp;

import br.com.pod.interfacesremotas.CoordenadorTransacao;
import br.com.pod.interfacesremotas.PersistenceMenager;
import br.com.pod.interfacesremotas.ServerApp;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;

/**
 *
 * @author Marcelo Augusto
 */
public class ServerAppImpl extends UnicastRemoteObject implements ServerApp {

    protected ServerAppImpl() throws RemoteException {
        super();
    }
    
    private PersistenceMenager getPersistenceMenager() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry("localhost", 10990);
        PersistenceMenager persistenceMenager = (PersistenceMenager) registry.lookup("PersistenceMenager");
        return persistenceMenager;
    }
    
    private CoordenadorTransacao getCoordenadorTransacao() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry("localhost", 10990);
        CoordenadorTransacao coordenador = (CoordenadorTransacao) registry.lookup("PersistenceMenager");
        return coordenador;
    }

    @Override
    public String processMessage(String msg) {
        return null;
    }
}
