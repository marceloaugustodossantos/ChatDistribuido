/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pod.gerenciadordepersistencia;

import br.com.pod.interfacesremotas.Persistence;
import br.com.pod.interfacesremotas.PersistenceMenager;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Marcelo Augusto
 */
public class PersistenceMenagerImpl extends UnicastRemoteObject implements PersistenceMenager {

    Persistence dropPersistence = getDropboxPersistence();
    Persistence drivePersistence = getDrivePersistence();
    Persistence txtPersistence = getTxtPersistence();

    public PersistenceMenagerImpl() throws RemoteException {
        super();
    }

    @Override
    public void salvarUsuario(Usuario usuario) {
        try {
            drivePersistence.salvarUsuario("" + usuario.getId(), convertUsuarioToJson(usuario));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void salvarGrupo(Grupo grupo) {
        try {
            drivePersistence.salvarGrupo("" + grupo.getId(), convertGrupoToJson(grupo));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Grupo> listarGrupos() throws RemoteException {
        List<Grupo> grupos = new ArrayList<>();
        try {
            String gruposString = drivePersistence.buscarGrupos();
            grupos = convertJsonToGrupoRepository(gruposString).grupos;
         } catch (RemoteException e) {
            e.printStackTrace();
        }
        return grupos;
    }

    @Override
    public List<Grupo> listarGruposDeUsuario(Long idUsuario) throws RemoteException {
        List<Grupo> todos = listarGrupos();
        List<Grupo> gruposDeUsuario = new ArrayList();
        for (Grupo grupo : todos) {
            List<Usuario> usuarios = grupo.getUsuarios();
            for (Usuario usuario : usuarios) {
                if (usuario.getId() == idUsuario) {
                    gruposDeUsuario.add(grupo);
                    break;
                }
            }
        }
        return gruposDeUsuario;
    }

    
    @Override
    public Map<Long, Usuario> listarUsuarios() throws RemoteException {
        return drivePersistence.listarUsuarios();
    }
    
    @Override
    public void salvarUsuarioEmGrupo(long idGrupo, Long idUsuario) throws RemoteException {
        String grupoStr = drivePersistence.buscarGrupo(idGrupo + "");
        Grupo grupo = convertJsonToGrupo(grupoStr);
        grupo.addUsuario(buscarUsuario(idUsuario));
        drivePersistence.atualizarGrupo("" + idGrupo, convertGrupoToJson(grupo));
    }

    @Override
    public Usuario buscarUsuario(long idUsuario) throws RemoteException {
        return convertJsonToUsuario(drivePersistence.buscarUsuario("" + idUsuario));
    }

    @Override
    public void salvarMensagem(Long idGrupo, Mensagem mensagem) {
        try {
            drivePersistence.salvarMensgem("" + mensagem.getId(), convertMensagemToJson(mensagem));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void salvarNotificacaoDeUsuario(String token, List<Mensagem> mensagens) {
        try {
            Notificacao notificacao = new Notificacao();
            notificacao.setMensagens(mensagens);
            String notificacaoString = convertNotificationToJson(notificacao);
            txtPersistence.salvarNotificacao(token, notificacaoString);
            dropPersistence.salvarNotificacao(token, notificacaoString);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private Persistence getDrivePersistence() {
        try {
            Registry registry = LocateRegistry.getRegistry(10990);
            return (Persistence) registry.lookup("DrivePersistence");
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Persistence getDropboxPersistence() {
        try {
            Registry registry = LocateRegistry.getRegistry(10990);
            return (Persistence) registry.lookup("DropboxPersistence");
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Persistence getTxtPersistence() {
        try {
            Registry registry = LocateRegistry.getRegistry(10990);
            return (Persistence) registry.lookup("TXTPersistence");
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Usuario convertJsonToUsuario(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, Usuario.class);
    }

    public String convertUsuarioToJson(Usuario usuario) {
        Gson gson = new Gson();
        return gson.toJson(usuario);
    }

    public Grupo convertJsonToGrupo(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, Grupo.class);
    }

    public String convertGrupoToJson(Grupo grupo) {
        Gson gson = new Gson();
        return gson.toJson(grupo);
    }

    public Mensagem convertJsonToMensagem(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, Mensagem.class);
    }

    public String convertMensagemToJson(Mensagem mensagem) {
        Gson gson = new Gson();
        return gson.toJson(mensagem);
    }

    public Notificacao convertJsonToNotification(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, Notificacao.class);
    }

    public String convertUsuarioToJson(Notificacao notificacao) {
        Gson gson = new Gson();
        return gson.toJson(notificacao);
    }
    
    public GruposRepository convertJsonToGrupoRepository(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, GruposRepository.class);
    }

    public String convertUsuarioToJson(Grupo grupo) {
        Gson gson = new Gson();
        return gson.toJson(grupo);
    }
    public Notificacao convertJsonToNotificationRepository(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, Notificacao.class);
    }

    public String convertNotificationToJson(Notificacao notificacao) {
        Gson gson = new Gson();
        return gson.toJson(notificacao);
    }

}
