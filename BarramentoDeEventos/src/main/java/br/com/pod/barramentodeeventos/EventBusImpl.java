/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pod.barramentodeeventos;

import br.com.pod.interfacesremotas.EventBus;
import br.com.pod.interfacesremotas.ReceivingNotify;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marcelo Augusto
 */
public class EventBusImpl implements EventBus {

    Map<String, List<String>> messages = new HashMap<String, List<String>>();
    Map<String, List<Subscriber>> subscribers = new HashMap<String, List<Subscriber>>();
    ReceivingNotify receivingNotify = getReceivingNotify();

    public EventBusImpl() {
        TaskMenager manager = new TaskMenager(this);
        manager.start();
    }

    @Override
    public void subscribe(String topic, String ip, Integer port) {
        List<Subscriber> list = subscribers.get(topic);
        if (list == null) {
            list = new ArrayList<Subscriber>();
            subscribers.put(topic, list);
        }
        list.add(new Subscriber(ip, port));
    }

    @Override
    public void publish(String topic, String message) {
        List<String> list = messages.get(topic);
        if (list == null) {
            list = new ArrayList<String>();
            messages.put(topic, list);
        }
        list.add(message);
    }

    public void notify(String ip, Integer port, String topic, String message) {
        try {
            receivingNotify.receberNotificacao(message);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }

    private ReceivingNotify getReceivingNotify() {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 10990);
            ReceivingNotify receivingNotify = (ReceivingNotify) registry.lookup("ReceiverNotify");
            return receivingNotify;
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
