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
public class TxLocalTXT extends UnicastRemoteObject implements TxLocal{
    
    private Persistence persistence;
    private String notificacoes;
    private String grupos;
    
    public TxLocalTXT (TXTPersistence persistence)throws RemoteException{
        this.persistence = persistence;
    }
    
    @Override
    public void prepare() throws RemoteException {
        this.grupos = persistence.buscarGrupos();
        this.notificacoes = persistence.buscarNotificacoes();
    }

    @Override
    public void commit() throws RemoteException {
        this.grupos = null;
        this.notificacoes = null;
    }

    @Override
    public void rollback() throws RemoteException {
        persistence.atualizarGrupos(grupos);
        persistence.salvarNotificacoes(notificacoes);
    }
}
