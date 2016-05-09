/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pod.unidadesdepersistencia;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author Marcelo Augusto
 */
public class Main {
    
    public static void main(String[] args) throws RemoteException, AlreadyBoundException{
        
        System.out.println("Persistence e TxLocal");
        
        DrivePersistence drivePersistence = new DrivePersistence();
        DropBoxPersistence dropBoxPersistence = new DropBoxPersistence();
        TXTPersistence tXTPersistence = new TXTPersistence();
        Registry registryPersistence = LocateRegistry.createRegistry(8094);
        registryPersistence.bind("DrivePersistence", drivePersistence);
        registryPersistence.bind("TXTPersistence", tXTPersistence);
        registryPersistence.bind("DropboxPersistence", dropBoxPersistence);
        
        TxLocalDrive txLocalDrive = new TxLocalDrive(drivePersistence);
        TxLocalDropbox txLocalDropbox = new TxLocalDropbox(dropBoxPersistence);
        TxLocalTXT txLocalTXT = new TxLocalTXT(tXTPersistence);
        Registry registryTXLocal = LocateRegistry.createRegistry(8096);
        registryTXLocal.bind("TxLocalDrive", txLocalDrive);
        registryTXLocal.bind("TxLocalTXT", txLocalTXT);
        registryTXLocal.bind("TxLocalDropbox", txLocalDropbox);
        
    }
    
}
