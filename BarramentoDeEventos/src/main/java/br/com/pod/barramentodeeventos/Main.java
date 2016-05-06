/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pod.barramentodeeventos;

import br.com.pod.interfacesremotas.EventBus;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author Marcelo Augusto
 */
public class Main {
    
    public static void main(String[] args) throws RemoteException, AlreadyBoundException {       
        System.out.println("Barramento de eventos");
        Registry registry = LocateRegistry.createRegistry(8081);
        registry.bind("EventBusImpl", new EventBusImpl());
    }

}
