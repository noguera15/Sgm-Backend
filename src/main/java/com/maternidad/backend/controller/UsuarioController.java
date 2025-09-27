package com.maternidad.backend.controller;

import com.maternidad.backend.dto.LoginRequest;
import com.maternidad.backend.entity.Usuario;
import com.maternidad.backend.service.UsuarioService;
import com.maternidad.backend.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Optional<Usuario> usuario = usuarioService.findByUsername(loginRequest.getUsername());
            
            if (usuario.isPresent() && usuario.get().getPassword().equals(loginRequest.getPassword())) {
                // Generar token JWT real
                String token = jwtService.generarToken(usuario.get().getUsername());
                
                // Crear respuesta con token y datos del usuario
                Map<String, Object> response = new HashMap<>();
                response.put("token", token);
                response.put("username", usuario.get().getUsername());
                response.put("nombre", usuario.get().getNombre());
                response.put("rol", usuario.get().getRol() != null ? usuario.get().getRol().getNombre() : "USUARIO");
                response.put("message", "Login exitoso");
                
                return ResponseEntity.ok().body(response);
            } else {
                return ResponseEntity.badRequest().body("Credenciales inválidas");
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error en el servidor: " + e.getMessage());
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody Usuario usuario) {
        try {
            // Verificar si el usuario ya existe
            if (usuarioService.findByUsername(usuario.getUsername()).isPresent()) {
                return ResponseEntity.badRequest().body("El usuario ya existe");
            }
            
            // Crear y guardar el usuario en la base de datos
            Usuario nuevoUsuario = Usuario.builder()
                    .username(usuario.getUsername())
                    .nombre(usuario.getNombre())
                    .password(usuario.getPassword())
                    .rol(usuario.getRol())
                    .build();
            
            // Guardar en la base de datos usando el servicio
            Usuario usuarioGuardado = usuarioService.save(nuevoUsuario);
            
            return ResponseEntity.ok().body("Usuario creado exitosamente: " + usuarioGuardado.getUsername());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al crear usuario: " + e.getMessage());
        }
    }
}
