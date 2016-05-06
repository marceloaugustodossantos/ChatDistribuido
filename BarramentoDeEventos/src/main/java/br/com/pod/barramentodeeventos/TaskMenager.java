/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pod.barramentodeeventos;

import br.com.pod.interfacesremotas.EventBus;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Marcelo Augusto
 */
public class TaskMenager extends Thread {

    private EventBusImpl eventBus;

    public TaskMenager(EventBusImpl e) {
        eventBus = e;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(3000);
                task();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void task() {
        Set<String> topics = eventBus.messages.keySet();
        for (String t : topics) {
            List<Subscriber> s = eventBus.subscribers.get(t);
            if (s != null && s.size() > 0) {
                //recuperar as mensagens (por tópico)
                List<String> m = eventBus.messages.get(t);
                if (m != null && m.size() > 0) {
                    for (Subscriber subscriber : s) {
                        for (String message : m) {
                            //no final, notificar (por tópico e por inscrito)
                            eventBus.notify(subscriber.getIp(), subscriber.getPort(), t, message);
                            System.out.println("notificação realizada ");
                        }
                    }
                }
            }
        }
    }
}
