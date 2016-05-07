/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pod.serverapp;

import br.com.pod.interfacesremotas.CoordenadorTransacao;
import br.com.pod.interfacesremotas.PersistenceMenager;
import br.com.pod.interfacesremotas.ServerApp;
import br.com.pod.objetosremotos.Grupo;
import br.com.pod.objetosremotos.Mensagem;
import br.com.pod.objetosremotos.Usuario;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
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
    Map<Long, Usuario> usuariosLogados;
    Map<Long, Usuario> usuarios;
    Map<Long, Grupo> grupos;

    protected ServerAppImpl() throws RemoteException {
        super();
        usuariosLogados = persistenceMenager.buscarUsuariosLogados();

    }

    @Override
    public void login(String email) {
//        try {
//            coorCoordenadorTransacao.prepareAll();
//            persistenceMenager.registrarLoginDeUsuario(idUsuario);
//            coorCoordenadorTransacao.commitAll();
        Usuario user = getUsuarioPorEmail(email);
        usuariosLogados.put(user.getId(), user);
//        } catch (RemoteException e) {
//            try {
//                coorCoordenadorTransacao.rollbackAll();
//            } catch (RemoteException ex) {
//                ex.printStackTrace();
//            }
//        }
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

    @Override
    public List<Grupo> listarGruposExistentes() throws RemoteException {
        try {
            coorCoordenadorTransacao.prepareAll();
            List<Grupo> grupos = persistenceMenager.listarGrupos();
            coorCoordenadorTransacao.commitAll();
            return grupos;
        } catch (RemoteException e) {
            try {
                coorCoordenadorTransacao.rollbackAll();
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public List<Grupo> listarGruposDeUsuario(Long idUsuario) throws RemoteException {
        try {
            coorCoordenadorTransacao.prepareAll();
            List<Grupo> grupos = persistenceMenager.listarGruposDeUsuario(idUsuario);
            coorCoordenadorTransacao.commitAll();
            return grupos;
        } catch (RemoteException e) {
            try {
                coorCoordenadorTransacao.rollbackAll();
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
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
    public void salvarPublicacaoEmGrupo(long idGrupo, Mensagem mensagem) throws RemoteException {
        try {
            coorCoordenadorTransacao.prepareAll();
            persistenceMenager.salvarMensagem(idGrupo, mensagem);
            coorCoordenadorTransacao.commitAll();
            grupos.get(idGrupo).addMensagem(mensagem);
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
        try {
            coorCoordenadorTransacao.prepareAll();
            String token = ""+System.currentTimeMillis();
            List<Mensagem> mensagens = getMensagensDeUsuario(idUsuario);
            persistenceMenager.salvarMensagensDeUsuario(token, mensagens);
            coorCoordenadorTransacao.commitAll();
            return token;
        } catch (RemoteException e) {
            try {
                coorCoordenadorTransacao.rollbackAll();
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    private List<Mensagem> getMensagensDeUsuario(Long idUsuario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private Usuario getUsuarioPorEmail(String email){
        Iterator<Usuario> usuariosIt = usuarios.values().iterator();
        for (Iterator<Usuario> iterator = usuariosIt; iterator.hasNext();) {
            Usuario next = iterator.next();
            if(next.getEmail().equals(email))
                return next;
        }
        return null;
    }
    
    private PersistenceMenager getPersistenceMenager() {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 10990);
            PersistenceMenager persistenceMenager = (PersistenceMenager) registry.lookup("PersistenceMenager");
            return persistenceMenager;
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private CoordenadorTransacao getCoordenadorTransacao() {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 10990);
            CoordenadorTransacao coordenador = (CoordenadorTransacao) registry.lookup("PersistenceMenager");
            return coordenador;
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
