/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pod.interfacesremotas;

import br.com.pod.objetosremotos.Mensagem;
import br.com.pod.objetosremotos.Usuario;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Marcelo Augusto
 */
public interface EventBus extends Remote {

    public void publish(long idGrupo, Mensagem mensagem)throws RemoteException;

    public void subscribe(long idGrupo, Long idUsuario)throws RemoteException;

}
