
package br.com.pod.barramentodeeventos;

import br.com.pod.objetosremotos.Grupo;
import br.com.pod.objetosremotos.Usuario;
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
        Set<Long> grupos = eventBus.grupos.keySet();
        for (Long idGrupo : grupos) {
            Grupo grupo = eventBus.grupos.get(idGrupo);
            for (Usuario usuario : grupo.getUsuarios()) {
                CheckNotificationsThread checkNotifications = new CheckNotificationsThread(usuario);
                checkNotifications.start();
            }
        }
    }
}
