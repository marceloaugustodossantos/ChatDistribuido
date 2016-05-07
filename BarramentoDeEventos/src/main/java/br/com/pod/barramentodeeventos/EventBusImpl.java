
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
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Marcelo Augusto
 */
public class EventBusImpl implements EventBus {

    Map<Long, Grupo> grupos = new HashMap<Long, Grupo>();
    private static ReceivingNotify receivingNotify = getReceivingNotify();
    private ServerApp serverApp = getServerApp();

    public EventBusImpl() {
        TaskMenager manager = new TaskMenager(this);
        manager.start();
    }

    @Override
    public void subscribe(long idGrupo, Usuario usuario) {
        grupos.get(idGrupo).addUsuario(usuario);
        serverApp.salvarInscricaoDeUsuarioEmGrupo(idGrupo, usuario);
    }

    @Override
    public void publish(long idGrupo, Mensagem mensagem) {
        grupos.get(idGrupo).addMensagem(mensagem);
        serverApp.salvarPublicacaoEmGrupo(idGrupo, mensagem);
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

}
