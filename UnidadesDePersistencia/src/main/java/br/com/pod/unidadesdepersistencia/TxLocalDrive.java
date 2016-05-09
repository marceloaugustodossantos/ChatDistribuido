/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pod.unidadesdepersistencia;

import br.com.pod.interfacesremotas.Persistence;
import br.com.pod.interfacesremotas.TxLocal;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Marcelo Augusto
 */
public class TxLocalDrive extends UnicastRemoteObject implements TxLocal{

    private Persistence persistence;
    private String usuarios;
    private String mensagens;
    
    public TxLocalDrive (DrivePersistence persistence) throws RemoteException{
        this.persistence = persistence;
    }
    
    @Override
    public void prepare() throws RemoteException {
        this.usuarios = persistence.buscarUsuarios();
        this.mensagens = persistence.buscarMensagens();
    }

    @Override
    public void commit() throws RemoteException {
        this.usuarios = null;
        this.mensagens = null;
    }

    @Override
    public void rollback() throws RemoteException {
        persistence.salvarMensgens(mensagens);
        persistence.salvarUsuarios(usuarios);
    }
    
}
