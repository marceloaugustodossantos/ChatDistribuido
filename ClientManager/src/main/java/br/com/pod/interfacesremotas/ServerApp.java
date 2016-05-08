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

    public void logout(String email) throws RemoteException;
    
    public long salvarUsuario(Usuario usuario) throws RemoteException;

    public List<Grupo> listarGruposExistentes() throws RemoteException;

    public List<Grupo> listarGruposDeUsuario(long idUsuario) throws RemoteException;
        
    public String getTokenNotificationUser (Usuario usuario) throws RemoteException;

    public void salvarInscricaoDeUsuarioEmGrupo(long idGrupo, Usuario usuario) throws RemoteException;

    public void salvarPublicacaoEmGrupo(long idGrupo, Mensagem mensagem) throws RemoteException;
    
    public Usuario getUsuario(long idUsuario) throws RemoteException;
    
}
