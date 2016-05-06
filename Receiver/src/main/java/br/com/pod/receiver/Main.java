/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pod.receiver;

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

    public static void main(String[] args) throws AccessException, RemoteException, AlreadyBoundException {

        System.out.println("receiver");
        
        Registry registry = LocateRegistry.createRegistry(8081);

        registry.bind("Receiver", new ReceiverImpl());

    }
}
