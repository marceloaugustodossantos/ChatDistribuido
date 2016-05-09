/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pod.interfacesremotas;

import br.com.pod.gerenciadordepersistencia.Grupo;
import br.com.pod.gerenciadordepersistencia.Mensagem;
import br.com.pod.gerenciadordepersistencia.Notificacao;
import br.com.pod.gerenciadordepersistencia.Usuario;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Marcelo Augusto
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

    public List<Notificacao> buscarNotificacoes() throws RemoteException;

    public Usuario buscarUsuario(long idUsuario) throws RemoteException;

    public List<Usuario> listarUsuarios() throws RemoteException;

    public void removerNotificacao(String token) throws RemoteException;
}
