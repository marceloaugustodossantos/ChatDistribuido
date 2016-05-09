package br.com.pod.clientmanager;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import br.com.pod.interfacesremotas.ReceivingNotify;
import br.com.pod.objetosremotos.Mensagem;
import br.com.pod.rmi.ClientRMI;

/**
 *
 * @author Pris
 */
public class ReceivingNotifyImpl extends UnicastRemoteObject implements ReceivingNotify {

//	private ClientRMI clientPersistentUnit;
//        ClientManagerOperations operations;
    private List<Mensagem> mensagens;
    private long idUsuario;
    ClientManagerOperations operations;
    
    public ReceivingNotifyImpl() throws RemoteException {
        super();
    }

    @Override
    public void receberNotificacao(long idUsuario,String notificacao) throws RemoteException, NotBoundException {
        ThreadMessages threadMessages = new ThreadMessages(notificacao);
        threadMessages.run();
        operations.mensagens.put(idUsuario, mensagens);
    }

    public List<Mensagem> getMensagens() {
        return mensagens;
    }

    public void setMensagens(List<Mensagem> mensagens) {
        this.mensagens = mensagens;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    
}
