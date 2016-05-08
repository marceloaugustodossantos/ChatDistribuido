/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pod.unidadesdepersistencia;

import com.sun.javafx.beans.IDProperty;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.RemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Marcelo Augusto
 */
public class Grupo implements Serializable{
    
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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 71 * hash + Objects.hashCode(this.nome);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Grupo other = (Grupo) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Grupo{" + "id=" + id + ", nome=" + nome + ", usuarios=" + usuarios + ", mensagens=" + mensagens + '}';
    }
    
}
