/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pod.interfacesremotas;

import br.com.pod.unidadesdepersistencia.Notificacao;
import br.com.pod.unidadesdepersistencia.Usuario;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Marcelo Augusto
 */
public interface Persistence extends Remote {

    public void salvarNotificacao(String token, String notificacao) throws RemoteException;
    
    public String buscarNotificacoesDeUsuario(String token) throws RemoteException;

    public void salvarMensgem(String string, String msgText) throws RemoteException;

    public void salvarUsuario(String string, String convertUsuarioToJson) throws RemoteException;

    public void salvarGrupo(String string, String convertGrupoToJson) throws RemoteException;

    public String buscarGrupos() throws RemoteException;

    public String buscarGrupo(String idGrupo) throws RemoteException;

    public void atualizarGrupo(String string, String convertGrupoToJson) throws RemoteException;

    public String buscarUsuario(String string) throws RemoteException;
    
    public Map<Long, Usuario> listarUsuarios() throws RemoteException;

    
}
