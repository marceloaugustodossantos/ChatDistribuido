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
    Gson gson = new Gson();

    public PersistenceMenagerImpl() throws RemoteException {
        super();
    }

    @Override
    public void salvarUsuario(Usuario usuario) throws RemoteException {
        UsuarioRepository ur = gson.fromJson(drivePersistence.buscarUsuarios(), UsuarioRepository.class);
        ur.addUsuario(usuario);
        drivePersistence.salvarUsuarios(convertUsuariosToJson(ur));
    }

    @Override
    public void salvarUsuarioEmGrupo(Long idUsuario, Long idGrupo) throws RemoteException {
        GruposRepository gr = gson.fromJson(txtPersistence.buscarGrupos(), GruposRepository.class);
        UsuarioRepository ur = gson.fromJson(drivePersistence.buscarUsuarios(), UsuarioRepository.class);
        Usuario usuario = null;
        for (Usuario u : ur.usuarios) {
            if (u.getId() == idUsuario) {
                usuario = u;
                break;
            }
        }
        for (Grupo g : gr.grupos) {
            if (g.getId() == idGrupo) {
                g.addUsuario(usuario);
                break;
            }
        }
        txtPersistence.atualizarGrupos(convertGruposToJson(gr));
    }

    @Override
    public List<Grupo> listarGrupos() throws RemoteException {
        String gruposString = txtPersistence.buscarGrupos();
        GruposRepository gr = convertJsonToGrupos(gruposString);
        System.out.println(gr.toString());
        return convertJsonToGrupos(gruposString).grupos;
    }

    @Override
    public List<Grupo> listarGruposDeUsuario(Long idUsuario) throws RemoteException {
        List<Grupo> gruposDeUsuario = new ArrayList();
        for (Grupo grupo : listarGrupos()) {
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
    public List<Usuario> listarUsuarios() throws RemoteException {
        try {
            return gson.fromJson(drivePersistence.buscarUsuarios(), UsuarioRepository.class).usuarios;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public Usuario buscarUsuario(long idUsuario) throws RemoteException {
        Usuario user = null;
        for (Usuario u : listarUsuarios()) {
            if (u.getId() == idUsuario) {
                user = u;
                break;
            }
        }
        return user;
    }

    @Override
    public void salvarMensagem(Mensagem mensagem) throws RemoteException {
        MensagensRepository mr = convertJsonToMensagens(drivePersistence.buscarMensagens());
        mr.addMensagem(mensagem);
        drivePersistence.salvarMensgens(convertMensagensToJson(mr));
    }

    @Override
    public void atualizarGrupo(Grupo grupo) throws RemoteException {
        GruposRepository gruposRepository = convertJsonToGrupos(drivePersistence.buscarGrupos());
        List<Grupo> grupos = gruposRepository.grupos;
        int indice = 0;
        for (int i = 0; i < grupos.size(); i++) {
            if (grupos.get(i).getId() == grupo.getId()) {
                indice = i;
                break;
            }
        }
        grupos.remove(indice);
        grupos.add(grupo);
        drivePersistence.atualizarGrupos(convertGruposToJson(gruposRepository));
    }

    @Override
    public void salvarNotificacaoDeUsuario(Notificacao notificacao) throws RemoteException {
        NotificacaoRepository nr = convertJsonToNotificacoes(txtPersistence.buscarNotificacoes());
        nr.addNotificacao(notificacao);
        txtPersistence.salvarNotificacoes(convertNotificoesToJson(nr));
        dropPersistence.salvarNotificacoes(convertNotificoesToJson(nr));
    }

    @Override
    public List<Notificacao> buscarNotificacoes() throws RemoteException {
        NotificacaoRepository nr = convertJsonToNotificacoes(txtPersistence.buscarNotificacoes());
        return nr.notificacoes;
    }

    @Override
    public List<Mensagem> buscarNotificacoesDeUsuario(String token) throws RemoteException {
        NotificacaoRepository nr = convertJsonToNotificacoes(txtPersistence.buscarNotificacoes());
        Notificacao notificacao = null;
        for (Notificacao n : nr.notificacoes) {
            if (n.getToken() == token) {
                notificacao = n;
            }
        }
        return notificacao.getMensagens();
    }

    @Override
    public void removerNotificacao(String token) throws RemoteException {
        NotificacaoRepository nr = convertJsonToNotificacoes(txtPersistence.buscarNotificacoes());
        Notificacao notificacao = null;
        for (Notificacao n : nr.notificacoes) {
            if (n.getToken() == token) {
                notificacao = n;
            }
        }
        nr.notificacoes.remove(notificacao);
        txtPersistence.salvarNotificacoes(convertNotificoesToJson(nr));
        dropPersistence.salvarNotificacoes(convertNotificoesToJson(nr));
    }

    private Persistence getDrivePersistence() {
        try {
            Registry registry = LocateRegistry.getRegistry(1238);
            return (Persistence) registry.lookup("DrivePersistence");
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Persistence getDropboxPersistence() {
        try {
            Registry registry = LocateRegistry.getRegistry(1238);
            return (Persistence) registry.lookup("DropboxPersistence");
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Persistence getTxtPersistence() {
        try {
            Registry registry = LocateRegistry.getRegistry(1238);
            return (Persistence) registry.lookup("TXTPersistence");
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public UsuarioRepository convertJsonToUsuarios(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, UsuarioRepository.class);
    }

    public String convertUsuariosToJson(UsuarioRepository usuarioRepository) {
        Gson gson = new Gson();
        return gson.toJson(usuarioRepository);
    }

    public GruposRepository convertJsonToGrupos(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, GruposRepository.class);
    }

    public String convertGruposToJson(GruposRepository gruposRepository) {
        Gson gson = new Gson();
        return gson.toJson(gruposRepository);
    }

    public MensagensRepository convertJsonToMensagens(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, MensagensRepository.class);
    }

    public String convertMensagensToJson(MensagensRepository mensagem) {
        Gson gson = new Gson();
        return gson.toJson(mensagem);
    }

    public NotificacaoRepository convertJsonToNotificacoes(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, NotificacaoRepository.class);
    }

    public String convertNotificoesToJson(NotificacaoRepository notificacaoRepository) {
        Gson gson = new Gson();
        return gson.toJson(notificacaoRepository);
    }

    public Notificacao convertJsonToNotificacao(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, Notificacao.class);
    }

    public String convertNotificaoToJson(Notificacao notificacao) {
        Gson gson = new Gson();
        return gson.toJson(notificacao);
    }

}
