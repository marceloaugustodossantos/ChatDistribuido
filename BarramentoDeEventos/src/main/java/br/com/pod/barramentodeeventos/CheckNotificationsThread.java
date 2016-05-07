/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pod.barramentodeeventos;

import br.com.pod.interfacesremotas.ServerApp;
import br.com.pod.objetosremotos.Usuario;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marcelo Augusto
 */
public class CheckNotificationsThread extends Thread {

    private Usuario usuario;
    private ServerApp serverApp = getServerApp();

    public CheckNotificationsThread(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public void run() {
        super.run();
        checkNotifications();
    }

    private void checkNotifications() {
        try {
            //verifica o token de acesso as notificações deste usuário caso exista alguma
            String token = serverApp.getTokenNotificationUser(usuario);
            if (token != null) {
                EventBusImpl.notify(usuario, token);
            }
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
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
