package com.maternidad.backend.security;

import com.maternidad.backend.entity.Usuario;
import com.maternidad.backend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {

    private final UsuarioRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario u = repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return User.withUsername(u.getUsername())
                .password(u.getPassword())             // BCrypt ya guardado
                .roles(u.getRol().getNombre())         // "ADMIN", "USER"
                .build();
    }
}
