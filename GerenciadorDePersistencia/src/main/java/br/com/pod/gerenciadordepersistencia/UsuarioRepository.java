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
public class UsuarioRepository {
    
    List<Usuario> usuarios;

    public UsuarioRepository() {
        usuarios = new ArrayList<>();
    }
    
    public void addUsuario(Usuario usuario){
        this.usuarios.add(usuario);
    }
}
