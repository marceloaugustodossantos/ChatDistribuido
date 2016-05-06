/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pod.interfacesremotas;

/**
 *
 * @author Marcelo Augusto
 */
public interface Persistence {
    
    public void salvarMensgem(String token, String mensagem);

    public String getMensagem(String token, int indice);

    public String getMensagens(String token);
    
}
