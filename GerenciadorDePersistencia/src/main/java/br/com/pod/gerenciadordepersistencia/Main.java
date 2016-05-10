/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pod.gerenciadordepersistencia;

import com.google.gson.Gson;
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
        System.out.println("gerenciador de persistencia");
        Registry registry = LocateRegistry.createRegistry(1240);
        registry.bind("PersistenceMenager", new PersistenceMenagerImpl());
 
    }

}
