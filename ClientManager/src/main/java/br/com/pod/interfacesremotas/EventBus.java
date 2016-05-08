/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pod.interfacesremotas;

import java.rmi.Remote;
import java.rmi.RemoteException;

import br.com.pod.objetosremotos.Mensagem;
import br.com.pod.objetosremotos.Usuario;

/**
 *
 * @author Marcelo Augusto
 */
public interface EventBus extends Remote {

    public void publish(long idGrupo, Mensagem msg)throws RemoteException;

    public void subscribe(long idGrupo, Usuario usuario)throws RemoteException;

}
