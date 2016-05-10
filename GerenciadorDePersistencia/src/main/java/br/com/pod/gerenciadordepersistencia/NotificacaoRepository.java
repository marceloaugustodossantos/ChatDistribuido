/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pod.gerenciadordepersistencia;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Marcelo Augusto
 */
public class NotificacaoRepository {
    
    List<Notificacao> notificacoes;

    public NotificacaoRepository() {
        this.notificacoes = new ArrayList<>();
    }
      
    public void addNotificacao (Notificacao notificacao){
        this.notificacoes.add(notificacao);
    }

    public List<Notificacao> getNotificacoes() {
        return notificacoes;
    }

    public void setNotificacoes(List<Notificacao> notificacoes) {
        this.notificacoes = notificacoes;
    }

    @Override
    public String toString() {
        return "NotificacaoRepository{" + "notificacoes=" + notificacoes + '}';
    }
    
    
    
}
