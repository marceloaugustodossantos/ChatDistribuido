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
public interface PersistenceMenager extends Remote {
        
    public void salvarUsuario(Usuario usuario)throws RemoteException;
    
    public void salvarGrupo(Grupo grupo)throws RemoteException;

    public List<Grupo> listarGrupos()throws RemoteException;

    public List<Grupo> listarGruposDeUsuario(Long idUsuario)throws RemoteException;

    public void salvarUsuarioEmGrupo(long idGrupo, Long idUsuario)throws RemoteException;

    public void salvarMensagem(Long idGrupo, Mensagem mensagem)throws RemoteException;

    public void salvarNotificacaoDeUsuario(String token, List<Mensagem> mensagens)throws RemoteException;
    
    public Usuario buscarUsuario(long idUsuario) throws RemoteException;

    public Map<Long, Usuario> listarUsuarios()throws RemoteException;
}
