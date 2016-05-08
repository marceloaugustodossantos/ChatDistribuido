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
    private String token;

    public ReceivingNotifyImpl() throws RemoteException {
        super();
    }

    @Override
    public void receberNotificacao(String notificacao) throws RemoteException, NotBoundException {
        this.token = notificacao;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
