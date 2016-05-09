package br.com.pod.clientmanager;

import br.com.pod.dao.DAO;
import br.com.pod.interfacesremotas.EventBus;
import br.com.pod.interfacesremotas.PersistenceMenager;
import br.com.pod.interfacesremotas.ServerApp;
import br.com.pod.objetosremotos.Grupo;
import br.com.pod.objetosremotos.Mensagem;
import br.com.pod.objetosremotos.Usuario;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import br.com.pod.rmi.ClientRMI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pris
 */
public class ClientManagerOperations {

    ClientRMI client;
    EventBus eventBus;
    ServerApp serverApp;
    PersistenceMenager persistenceMenager;
    ReceivingNotifyImpl notification;
    Map<Long, List<Mensagem>> mensagens;
    DAO<Object> dao;
    
    public ClientManagerOperations() {

    }

    //TODO
    public long signin(String credential, String nome, String ip, int port) {
        Usuario user = new Usuario(System.currentTimeMillis(), nome, credential, ip, port);
        try {
            serverApp.salvarUsuario(user);
        } catch (RemoteException ex) {
            Logger.getLogger(ClientManagerOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user.getId();
    }

    public void login(String credential) {
        try {
            serverApp.login(credential);
        } catch (RemoteException ex) {
            Logger.getLogger(ClientManagerOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void logout(String credential) {
        try {
            serverApp.logout(credential);
        } catch (RemoteException ex) {
            Logger.getLogger(ClientManagerOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//TODO
    public void publish(long idUsuario, long idGrupo, String conteudo){
        Date data = new Date();
        data.getTime();
        Mensagem mensagem = new Mensagem(System.currentTimeMillis(), idUsuario,
                idGrupo, conteudo, data);
        Mensagem backup = new Mensagem();
        boolean status = true;
        while (status) {
            try {
                eventBus.publish(idGrupo, mensagem);
                status = false;
            } catch (RemoteException ex) {
                dao.salvar(mensagem);
                try {
                    Thread.sleep(60000); //1min
                } catch (InterruptedException ex1) {
                    assert false;
                }
            }
        }
    }

    public void subscribe(long idUser, long idGroup) {
        try {
            eventBus.subscribe(idGroup, idUser);
        } catch (RemoteException ex) {
            Logger.getLogger(ClientManagerOperations.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String getAvailableGroups() {
        List<Grupo> grupos = null;
        String response = "";
        try {
            grupos = serverApp.listarGruposExistentes();
            response = formatterForGroups(grupos);
        } catch (RemoteException ex) {
            Logger.getLogger(ClientManagerOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }

    public String getRegisteredGroups(long idUsuario) {
        List<Grupo> gruposDeUsuario = null;
        String response = "";
        try {
            gruposDeUsuario = serverApp.listarGruposDeUsuario(idUsuario);
            response = formatterForGroups(gruposDeUsuario);
        } catch (RemoteException ex) {
            Logger.getLogger(ClientManagerOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }

    public String getMessages(long idUsuario) {
        List<Mensagem> msg = mensagens.get(idUsuario);
        String response = formatterForMessages(msg);
        return response;
    }

//<idGrupo>-<nomeGrupo>/
    static String formatterForGroups(List<Grupo> grupos) {
        String result = "";
        for (Grupo g : grupos) {
            result += g.getId() + "-" + g.getNome() + "\n";
        }
        return result;
    }
//<Data>-<idGrupo>-<mensagem>/

    static String formatterForMessages(List<Mensagem> mensagens) {
        String result = "";
        for (Mensagem m : mensagens) {
            result += m.getData() + "-" + m.getIdGrupo() + "-" + m.getMensagem() + "\n";
        }
        return result;
    }
}
