package br.com.pod.interfacesremotas;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Marcelo Augusto
 */
public interface ReceivingNotify extends Remote{
    
    public void receberNotificacao(String notificacao)throws RemoteException,NotBoundException;  
}