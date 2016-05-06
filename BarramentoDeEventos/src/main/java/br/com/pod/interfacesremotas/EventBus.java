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
public interface EventBus extends Remote {

    public void publish(String topic, String msg)throws RemoteException;

    public void subscribe(String topic, String ip, Integer port)throws RemoteException;

}
