/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pod.objetosremotos;

import com.sun.javafx.beans.IDProperty;
import java.rmi.RemoteException;
import java.rmi.server.RemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Marcelo Augusto
 */
public class Grupo extends RemoteObject {
    
    private long id;
    private String nome;
    private List<Usuario> usuarios; 
    private List<Mensagem> mensagens;

    public Grupo(long id, String nome) {
        this.id = id;
        this.nome = nome;
        this.usuarios = new ArrayList<>();
        this.mensagens = new ArrayList<>();
    }
    
    public void addUsuario(Usuario usuario){
        this.usuarios.add(usuario);
    }
    
    public void addMensagem(Mensagem mensagem){
        this.mensagens.add(mensagem);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }


    public List<Mensagem> getMensagens() {
        return mensagens;
    }

    public void setMensagens(List<Mensagem> mensagens) {
        this.mensagens = mensagens;
    }
    
}
