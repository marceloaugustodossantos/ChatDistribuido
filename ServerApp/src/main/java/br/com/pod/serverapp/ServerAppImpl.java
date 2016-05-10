/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pod.serverapp;

import br.com.pod.interfacesremotas.CoordenadorTransacao;
import br.com.pod.interfacesremotas.PersistenceMenager;
import br.com.pod.interfacesremotas.ServerApp;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Marcelo Augusto
 */
public class ServerAppImpl extends UnicastRemoteObject implements ServerApp {

    PersistenceMenager persistenceMenager = getPersistenceMenager();
    CoordenadorTransacao coorCoordenadorTransacao = getCoordenadorTransacao();
    Map<Long, Usuario> usuariosLogados = new HashMap<>();
    Map<Long, Usuario> usuarios = listarUsuarios();
    Map<Long, Grupo> grupos = getGruposMap();

    protected ServerAppImpl() throws RemoteException {
        super();
    }

    private Map<Long, Grupo> getGruposMap() {
        Map<Long, Grupo> grupos = new HashMap<>();
        try {
            for (Grupo grupo : listarGruposExistentes()) {
                grupos.put(grupo.getId(), grupo);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return grupos;
    }

    @Override
    public void login(String email) {
        Usuario user = getUsuarioPorEmail(email);
        usuariosLogados.put(user.getId(), user);
    }

    @Override
    public void logout(long idUsuario) throws RemoteException {
        usuariosLogados.remove(idUsuario);
    }

    @Override
    public void salvarUsuario(Usuario usuario) throws RemoteException {
        try {
            coorCoordenadorTransacao.prepareAll();
            persistenceMenager.salvarUsuario(usuario);
            coorCoordenadorTransacao.commitAll();
        } catch (RemoteException e) {
            try {
                coorCoordenadorTransacao.rollbackAll();
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        }
    }

    private Map<Long, Usuario> listarUsuarios() {
        Map<Long, Usuario> usuarios = new HashMap<>();
        try {
            List<Usuario> usuariosList = persistenceMenager.listarUsuarios();
            for (Usuario u : usuariosList) {
                usuarios.put(u.getId(), u);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    @Override
    public Usuario buscarUsuario(Long idUsuario) throws RemoteException {
        return usuarios.get(idUsuario);
    }

    @Override
    public List<Grupo> listarGruposExistentes() throws RemoteException {
        try {
            List<Grupo> grupos = persistenceMenager.listarGrupos();
            return grupos;
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Grupo> listarGruposDeUsuario(Long idUsuario) throws RemoteException {
        try {
            List<Grupo> grupos = persistenceMenager.listarGruposDeUsuario(idUsuario);
            return grupos;
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void salvarInscricaoDeUsuarioEmGrupo(long idGrupo, Long idUsuario) throws RemoteException {
        try {
            coorCoordenadorTransacao.prepareAll();
            persistenceMenager.salvarUsuarioEmGrupo(idGrupo, idUsuario);
            coorCoordenadorTransacao.commitAll();
            grupos.get(idGrupo).addUsuario(usuarios.get(idUsuario));
        } catch (RemoteException e) {
            try {
                coorCoordenadorTransacao.rollbackAll();
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void salvarMensagem(Mensagem mensagem) throws RemoteException {
        try {
            coorCoordenadorTransacao.prepareAll();
            persistenceMenager.salvarMensagem(mensagem);
            Set<Long> ids = usuarios.keySet();
            for (Long idUsuario : ids) {
                Notificacao notificacao = new Notificacao();
                notificacao.setId(System.currentTimeMillis());
                String token = "" + System.currentTimeMillis();
                notificacao.setToken(token);
                notificacao.setIdUsuario(idUsuario);
                List<Mensagem> msgs = new ArrayList<>();
                if (usuariosLogados.containsKey(idUsuario)) {
                    msgs.add(mensagem);
                    notificacao.setMensagens(msgs);
                } else {
                    msgs = getNotificacoesDeUsuario(idUsuario).getMensagens();
                    msgs.add(mensagem);
                    notificacao.setMensagens(msgs);
                }
                persistenceMenager.salvarNotificacaoDeUsuario(notificacao);
            }
            coorCoordenadorTransacao.commitAll();
        } catch (RemoteException e) {
            try {
                coorCoordenadorTransacao.rollbackAll();
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public String getTokenNotificationUser(Long idUsuario) throws RemoteException {
        Notificacao notificacao = getNotificacoesDeUsuario(idUsuario);
        String token = notificacao.getToken();
        persistenceMenager.removerNotificacao(notificacao.getToken());
        return token;
    }

    private Notificacao getNotificacoesDeUsuario(Long idUsuario) {
        Notificacao notificacao = null;
        try {
            coorCoordenadorTransacao.prepareAll();
            List<Notificacao> notificacoes = persistenceMenager.buscarNotificacoes();
            for (Notificacao n : notificacoes) {
                if (n.getIdUsuario() == idUsuario) {
                    notificacao = n;
                    break;
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return notificacao;
    }

    private Usuario getUsuarioPorEmail(String email) {
        Iterator<Usuario> usuariosIt = usuarios.values().iterator();
        for (Iterator<Usuario> iterator = usuariosIt; iterator.hasNext();) {
            Usuario next = iterator.next();
            if (next.getEmail().equals(email)) {
                return next;
            }
        }
        return null;
    }

    private PersistenceMenager getPersistenceMenager() {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1240);
            PersistenceMenager persistenceMenager = (PersistenceMenager) registry.lookup("PersistenceMenager");
            return persistenceMenager;
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private CoordenadorTransacao getCoordenadorTransacao() {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1237);
            CoordenadorTransacao coordenador = (CoordenadorTransacao) registry.lookup("CoordenadorTransacao");
            return coordenador;
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
