package com.maternidad.backend.service;

import com.maternidad.backend.entity.Usuario;
import com.maternidad.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Optional<Usuario> autenticar(String username, String password) {
        Optional<Usuario> usuario = usuarioRepository.findByUsername(username);
        return usuario.filter(u -> u.getPassword().equals(password));
    }

    public Optional<Usuario> findByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
}
