package cl.duoc.levelupserver.controller;

import cl.duoc.levelupserver.model.Usuario;
import cl.duoc.levelupserver.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UsuarioRepository repository;

    // 1. Listar
    @GetMapping
    public List<Usuario> listar() {
        return repository.findAll();
    }

    // 2. Obtener por RUN
    @GetMapping("/{run}")
    public ResponseEntity<Usuario> obtener(@PathVariable String run) {
        Usuario usuario = repository.findByRun(run);
        if (usuario == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(usuario);
    }

    // 3. Crear (Admin)
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Usuario usuario) {
        if (repository.findByRun(usuario.getRun()) != null) {
            return ResponseEntity.badRequest().body("El RUN ya existe");
        }
        if (repository.findByEmail(usuario.getEmail()) != null) {
            return ResponseEntity.badRequest().body("El Email ya existe");
        }
        return ResponseEntity.ok(repository.save(usuario));
    }

    // 4. Actualizar
    @PutMapping("/{run}")
    public ResponseEntity<?> actualizar(@PathVariable String run, @RequestBody Usuario usuario) {
        Usuario existente = repository.findByRun(run);
        if (existente == null) return ResponseEntity.notFound().build();

        existente.setFirstName(usuario.getFirstName());
        existente.setLastName(usuario.getLastName());
        existente.setEmail(usuario.getEmail());
        existente.setRole(usuario.getRole());
        existente.setRegion(usuario.getRegion());
        existente.setComuna(usuario.getComuna());
        existente.setAddress(usuario.getAddress());
        
        if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
            existente.setPassword(usuario.getPassword());
        }

        return ResponseEntity.ok(repository.save(existente));
    }

    // 5. Eliminar
    @DeleteMapping("/{run}")
    public ResponseEntity<?> eliminar(@PathVariable String run) {
        Usuario usuario = repository.findByRun(run);
        if (usuario == null) return ResponseEntity.notFound().build();
        
        try {
            repository.delete(usuario);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body("No se puede eliminar: tiene datos asociados.");
        }
    }
}