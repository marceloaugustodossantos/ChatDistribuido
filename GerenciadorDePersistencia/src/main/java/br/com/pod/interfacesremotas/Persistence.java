/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pod.interfacesremotas;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Marcelo Augusto
 */
public interface Persistence extends Remote {

    public String buscarMensagens() throws RemoteException;
    
    public void salvarMensgens(String mensagensJson) throws RemoteException;
    
    public String buscarUsuarios() throws RemoteException;
    
    public String salvarUsuarios(String usuariosJson) throws RemoteException;
    
    public void salvarNotificacao(String token, String notificacao) throws RemoteException;
    
    public String buscarNotificacoesDeUsuario(String token) throws RemoteException;
    
    public String buscarGrupos() throws RemoteException;
    
}
