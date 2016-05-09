/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pod.serverapp;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.RemoteObject;
import java.util.Date;

/**
 *
 * @author Marcelo Augusto
 */
public class Mensagem implements Serializable {

    private long id;
    private long idUsuario;
    private long idGrupo;
    private String mensagem;
    private Date data;

    public Mensagem(long id, long idUsuario, long idGrupo, String mensagem, Date data) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.mensagem = mensagem;
        this.data = data;
        this.idGrupo = idGrupo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public long getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(long idGrupo) {
        this.idGrupo = idGrupo;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 97 * hash + (int) (this.idUsuario ^ (this.idUsuario >>> 32));
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
        final Mensagem other = (Mensagem) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.idUsuario != other.idUsuario) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Mensagem{" + "id=" + id + ", idUsuario=" + idUsuario + ", idGrupo=" + idGrupo + ", mensagem=" + mensagem + ", data=" + data + '}';
    }
    

}
