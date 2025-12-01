package cl.duoc.levelupserver.service;

import cl.duoc.levelupserver.model.Usuario;
import cl.duoc.levelupserver.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // 1. Buscamos el usuario por su email en nuestra BD
        Usuario usuario = repository.findByEmail(email);

        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado con el email: " + email);
        }

        // 2. Retornamos un objeto "User" de Spring Security con los datos de nuestra BD
        // OJO: Aquí la contraseña debería estar encriptada en un caso real
        return new User(
                usuario.getEmail(), 
                usuario.getPassword(), 
                new ArrayList<>() // Aquí irían los roles/permisos
        );
    }
}