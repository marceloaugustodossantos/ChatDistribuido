/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pod.interfacesremotas;

import java.util.List;

/**
 *
 * @author Marcelo Augusto
 */
public interface PersistenceMenager {
    
    public void salvarMensagem(String token, String mensagem);
    
    public String recuperarMensagem(String token, int pos);
    
    public List<String> recuperarConversa(String token);
    
}
