/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pod.coordenadortransacao;

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
        System.out.println("cordenador de transação");
        Registry registry = LocateRegistry.createRegistry(1237);
        registry.bind("CoordenadorTransacao", new CoordenadorTransacaoImpl());
    }
    
}
