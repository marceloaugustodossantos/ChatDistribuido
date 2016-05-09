/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pod.clientmanager;

import br.com.pod.interfacesremotas.PersistenceMenager;
import br.com.pod.objetosremotos.Mensagem;
import br.com.pod.objetosremotos.Notificacao;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pris
 */
public class ThreadMessages extends Thread {

    String token;
    PersistenceMenager persistenceMenager;
    ReceivingNotifyImpl notify;
    
    
    public ThreadMessages(String token) {
        this.token = token;
    }

    public void run() {
        try {
            notify.setMensagens(persistenceMenager.buscarNotificacoesDeUsuario(token));
        } catch (RemoteException ex) {
            Logger.getLogger(ThreadMessages.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
