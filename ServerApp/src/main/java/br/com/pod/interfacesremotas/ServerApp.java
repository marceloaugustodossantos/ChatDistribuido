/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pod.interfacesremotas;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Marcelo Augusto
 */
public interface ServerApp {
    
    public void login(String email) throws RemoteException;

    public void logout(long idUsuario) throws RemoteException;
    
    public void salvarUsuario(Usuario usuario) throws RemoteException;

    public List<Grupo> listarGruposExistentes() throws RemoteException;

    public List<Grupo> listarGruposDeUsuario(Long idUsuario) throws RemoteException;
        
    public String getTokenNotificationUser (Long idUsuario) throws RemoteException;

    public void salvarInscricaoDeUsuarioEmGrupo(long idGrupo, Long idUsuario) throws RemoteException;

    public void salvarPublicacaoEmGrupo(long idGrupo, Mensagem mensagem) throws RemoteException;
    
}
