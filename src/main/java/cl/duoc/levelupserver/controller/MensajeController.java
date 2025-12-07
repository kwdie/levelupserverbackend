package cl.duoc.levelupserver.controller;

import cl.duoc.levelupserver.model.Mensaje;
import cl.duoc.levelupserver.repository.MensajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@CrossOrigin(origins = "*")
public class MensajeController {

    @Autowired
    private MensajeRepository repository;

    // 1. Guardar mensaje (Público)
    @PostMapping
    public ResponseEntity<Mensaje> crear(@RequestBody Mensaje mensaje) {
        return ResponseEntity.ok(repository.save(mensaje));
    }

    // 2. Listar mensajes (Admin)
    @GetMapping
    public List<Mensaje> listar() {
        return repository.findAll();
    }

    // 3. Eliminar mensaje
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}