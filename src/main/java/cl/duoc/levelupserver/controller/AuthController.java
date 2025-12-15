package cl.duoc.levelupserver.controller;

import cl.duoc.levelupserver.model.Usuario;
import cl.duoc.levelupserver.repository.UsuarioRepository;
import cl.duoc.levelupserver.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173") // Tu React
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 1. LOGIN 
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");

        // Buscar usuario
        Usuario usuario = usuarioRepository.findByEmail(email);

        // Validar contraseña 
        if (usuario != null && usuario.getPassword().equals(password)) {
            // Generar Token
            String token = jwtUtil.generateToken(usuario.getEmail(), usuario.getRole());
            
            // Retornar Token + Datos del usuario
            return ResponseEntity.ok(Map.of(
                "token", token,
                "user", usuario
            ));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
    }

    // 2. REGISTRO 
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Usuario usuario) {
        if (usuarioRepository.findByEmail(usuario.getEmail()) != null) {
            return ResponseEntity.badRequest().body("El email ya existe");
        }
         
        Usuario nuevoUsuario = usuarioRepository.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
    }
}