package cl.duoc.levelupserver.repository;

import cl.duoc.levelupserver.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Buscar usuario por correo (Login)
    Usuario findByEmail(String email);
    
    // Buscar por RUN (Validación de registro)
    Usuario findByRun(String run);
}