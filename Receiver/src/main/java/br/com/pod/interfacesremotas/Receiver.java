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
public interface Receiver extends Remote{
    
    public String receive(String msg) throws RemoteException;
    
}
