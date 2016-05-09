/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pod.interfacesremotas;

import br.com.pod.objetosremotos.Grupo;
import br.com.pod.objetosremotos.Mensagem;
import br.com.pod.objetosremotos.Usuario;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Marcelo Augusto
 */
public interface ServerApp extends Remote {
    
     public void login(String email) throws RemoteException;

    public void logout(long idUsuario) throws RemoteException;
    
    public void salvarUsuario(Usuario usuario) throws RemoteException;

    public List<Grupo> listarGruposExistentes() throws RemoteException;

    public List<Grupo> listarGruposDeUsuario(Long idUsuario) throws RemoteException;
        
    public String getTokenNotificationUser (Long idUsuario) throws RemoteException;

    public void salvarInscricaoDeUsuarioEmGrupo(long idGrupo, Long idUsuario) throws RemoteException;

    public void salvarMensagem(Mensagem mensagem) throws RemoteException;

    public Usuario buscarUsuario(Long idUsuario)throws RemoteException;

}
