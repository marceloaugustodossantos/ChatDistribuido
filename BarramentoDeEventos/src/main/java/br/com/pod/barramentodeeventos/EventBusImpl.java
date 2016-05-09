package br.com.pod.barramentodeeventos;

import br.com.pod.interfacesremotas.EventBus;
import br.com.pod.interfacesremotas.ReceivingNotify;
import br.com.pod.interfacesremotas.ServerApp;
import br.com.pod.objetosremotos.Grupo;
import br.com.pod.objetosremotos.Mensagem;
import br.com.pod.objetosremotos.Usuario;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RemoteObject;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marcelo Augusto
 */
public class EventBusImpl extends UnicastRemoteObject implements EventBus {

    Map<Long, Grupo> grupos = null;
    private static ReceivingNotify receivingNotify = getReceivingNotify();
    private ServerApp serverApp = getServerApp();

    public EventBusImpl() throws RemoteException {
        super();
        grupos = buscarGruposMap();
        TaskMenager manager = new TaskMenager(this);
        manager.start();
    }

    @Override
    public void subscribe(long idGrupo, Long idUsuario) throws RemoteException {
        Usuario usuario = serverApp.buscarUsuario(idUsuario);
        grupos.get(idGrupo).addUsuario(usuario);
        serverApp.salvarInscricaoDeUsuarioEmGrupo(idGrupo, idUsuario);
    }

    @Override
    public void publish(long idGrupo, Mensagem mensagem) throws RemoteException {
        grupos.get(idGrupo).addMensagem(mensagem);
        serverApp.salvarMensagem(mensagem);
    }

    public static void notify(Usuario usuario, String token) {
        try {
            receivingNotify.receberNotificacao(token);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }

    private static ReceivingNotify getReceivingNotify() {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 10990);
            ReceivingNotify receivingNotify = (ReceivingNotify) registry.lookup("ReceiverNotify");
            return receivingNotify;
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ServerApp getServerApp() {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 8080);
            ServerApp server = (ServerApp) registry.lookup("ServerApp");
            return server;
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Map<Long, Grupo> buscarGruposMap() {
        Map<Long, Grupo> gruposMap = null;
        try {
            List<Grupo> gruposList = serverApp.listarGruposExistentes();
            for (Grupo g : gruposList) {
                gruposMap.put(g.getId(), g);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(EventBusImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return gruposMap;

    }

}
