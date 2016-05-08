/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pod.clientmanager;


import br.com.pod.interfacesremotas.PersistenceUnit;
import java.util.ArrayList;

/**
 *
 * @author Pris
 */
public class ThreadMessages {
    
    String token;
    PersistenceUnit persistenceUnit;
    ClientManagerOperations operations;
    
    public ThreadMessages(String token) {
        this.token = token;
        operations.mensagens = new ArrayList<>();
    }
    
    public void run(){
        operations.mensagens = persistenceUnit.getMensagens(token);
    }
}
