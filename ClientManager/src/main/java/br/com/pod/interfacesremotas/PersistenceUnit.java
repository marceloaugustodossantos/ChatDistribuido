/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pod.interfacesremotas;

import br.com.pod.objetosremotos.Mensagem;
import java.rmi.Remote;
import java.util.List;

/**
 *
 * @author Marcelo Augusto
 */
public interface PersistenceUnit extends Remote {

    public List<Mensagem> getMensagens(String token);
    
}
