/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pod.coordenadortransacao;

import br.com.pod.interfacesremotas.CoordenadorTransacao;
import br.com.pod.interfacesremotas.TxLocal;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author Marcelo Augusto
 */
public class CoordenadorTransacaoImpl implements CoordenadorTransacao {

    private TxLocal getTxLocalDrive() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry("localhost", 10991);
        TxLocal txLocal = (TxLocal) registry.lookup("TxLocalDrive");
        return txLocal;
    }

    private TxLocal getTxLocalDropbox() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry("localhost", 10991);
        TxLocal txLocal = (TxLocal) registry.lookup("TxLocalDropbox");
        return txLocal;
    }
    
    private TxLocal getTxLocalTXT() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry("localhost", 10991);
        TxLocal txLocal = (TxLocal) registry.lookup("TxLocalTXT");
        return txLocal;
    }
    
    @Override
    public void prepareAll() throws RemoteException {
        try{
            TxLocal txLocalDrive = getTxLocalDrive();
            TxLocal txLocalDropbox = getTxLocalDropbox();
            TxLocal txLocalDTXT = getTxLocalTXT();
            
            txLocalDTXT.prepare();
            txLocalDropbox.prepare();
            txLocalDrive.commit();
            
        }catch (NotBoundException e){
            e.printStackTrace();
        }
    }

    @Override
    public void commitAll() throws RemoteException {
        try{
            TxLocal txLocalDrive = getTxLocalDrive();
            TxLocal txLocalDropbox = getTxLocalDropbox();
            TxLocal txLocalDTXT = getTxLocalTXT();
            
            txLocalDTXT.commit();
            txLocalDropbox.commit();
            txLocalDrive.commit();
            
        }catch (NotBoundException e){
            e.printStackTrace();
        }
    }

    @Override
    public void rollbackAll() throws RemoteException {
        try{
            TxLocal txLocalDrive = getTxLocalDrive();
            TxLocal txLocalDropbox = getTxLocalDropbox();
            TxLocal txLocalDTXT = getTxLocalTXT();
            
            txLocalDTXT.rollback();
            txLocalDropbox.rollback();
            txLocalDrive.rollback();
            
        }catch (NotBoundException e){
            e.printStackTrace();
        }
    }

}
