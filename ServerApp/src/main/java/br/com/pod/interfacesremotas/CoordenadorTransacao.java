/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pod.interfacesremotas;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Marcelo Augusto
 */
public interface CoordenadorTransacao extends Remote {

    void prepareAll() throws RemoteException;

    void commitAll() throws RemoteException;

    void rollbackAll() throws RemoteException;
}
