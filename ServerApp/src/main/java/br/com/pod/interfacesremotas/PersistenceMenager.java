/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pod.interfacesremotas;

import br.com.pod.objetosremotos.Grupo;
import br.com.pod.objetosremotos.Mensagem;
import br.com.pod.objetosremotos.Usuario;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Marcelo Augusto
 */
public interface PersistenceMenager {
    
    public void salvarMensagem(String token, String mensagem);
    
    public String recuperarMensagem(String token, int pos);
    
    public List<String> recuperarConversa(String token);

    public Map<Long, Usuario> buscarUsuariosLogados();

    public void registrarLoginDeUsuario(long idUsuario);

    public void salvarUsuario(Usuario usuario);

    public List<Grupo> listarGrupos();

    public List<Grupo> listarGruposDeUsuario();

    public List<Grupo> listarGruposDeUsuario(Long idUsuario);

    public void salvarUsuarioEmGrupo(long idGrupo, Long idUsuario);

    public void salvarMensagem(long idGrupo, Mensagem mensagem);
    
}
