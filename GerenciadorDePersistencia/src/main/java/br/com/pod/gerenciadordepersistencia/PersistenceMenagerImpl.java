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
        GruposRepository gr = gson.fromJson(drivePersistence.buscarGrupos(), GruposRepository.class);
        ur.addUsuario(usuario);
        
////  parei aqui!!
        
        drivePersistence.salvarUsuarios(convertUsuariosToJson(ur));
    }

    @Override
    public List<Grupo> listarGrupos() throws RemoteException {
        String gruposString = drivePersistence.buscarGrupos();
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
        return gson.fromJson(drivePersistence.buscarUsuarios(), UsuarioRepository.class).usuarios;
    }

    @Override
    public Usuario buscarUsuario(long idUsuario) throws RemoteException {
        Usuario user  = null;
        for(Usuario u : listarUsuarios()){
            if(u.getId() == idUsuario){
                user =  u;
                break;
            }
        }
        return user;
    }

    @Override
    public void salvarMensagem(Long idGrupo, Mensagem mensagem) {
       
    }
    
    @Override
    public void salvarGrupo(Grupo grupo) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void salvarNotificacaoDeUsuario(String token, List<Mensagem> mensagens) {
        try {
            Notificacao notificacao = new Notificacao();
            notificacao.setMensagens(mensagens);
            String notificacaoString = convertNotificaoToJson(notificacao);
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
