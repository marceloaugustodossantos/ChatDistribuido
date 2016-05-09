/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pod.interfacesremotas;

import br.com.pod.objetosremotos.Grupo;
import br.com.pod.objetosremotos.Mensagem;
import br.com.pod.objetosremotos.Notificacao;
import br.com.pod.objetosremotos.Usuario;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Pris
 */
public interface PersistenceMenager extends Remote {

    public void salvarUsuario(Usuario usuario) throws RemoteException;
    
    public void salvarUsuarioEmGrupo(Long idUsuario, Long idGrupo) throws RemoteException;    

    public void atualizarGrupo(Grupo grupo) throws RemoteException;

    public List<Grupo> listarGrupos() throws RemoteException;

    public List<Grupo> listarGruposDeUsuario(Long idUsuario) throws RemoteException;

    public void salvarMensagem(Mensagem mensagem) throws RemoteException;

    public void salvarNotificacaoDeUsuario(Notificacao notificacao) throws RemoteException;
    
    public List<Mensagem> buscarNotificacoesDeUsuario(String token) throws RemoteException;

    public Usuario buscarUsuario(long idUsuario) throws RemoteException;

    public List<Usuario> listarUsuarios() throws RemoteException;

}
