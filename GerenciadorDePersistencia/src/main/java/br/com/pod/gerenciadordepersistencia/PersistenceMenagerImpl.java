/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pod.gerenciadordepersistencia;

import br.com.pod.interfacesremotas.Persistence;
import br.com.pod.interfacesremotas.PersistenceMenager;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 *
 * @author Marcelo Augusto
 */
public class PersistenceMenagerImpl extends UnicastRemoteObject implements PersistenceMenager{
    
    public PersistenceMenagerImpl() throws RemoteException{
        super();
    }

    private Persistence getDrivePersistence () throws RemoteException, NotBoundException{
        Registry registry = LocateRegistry.getRegistry(10990);
        return (Persistence) registry.lookup("DrivePersistence");
    }
    
    private Persistence getDropboxPersistence () throws RemoteException, NotBoundException{
        Registry registry = LocateRegistry.getRegistry(10990);
        return (Persistence) registry.lookup("DropboxPersistence");
    }
    
    private Persistence getTxtPersistence () throws RemoteException, NotBoundException{
        Registry registry = LocateRegistry.getRegistry(10990);
        return (Persistence) registry.lookup("TXTPersistence");
    }
    
    @Override
    public void salvarMensagem(String token, String mensagem) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String recuperarMensagem(String token, int pos) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> recuperarConversa(String token) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
