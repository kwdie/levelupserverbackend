package cl.duoc.levelupserver.controller;

import cl.duoc.levelupserver.model.Producto;
import cl.duoc.levelupserver.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity; // <--- NO OLVIDES ESTE IMPORT
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    private ProductoRepository repository;

    // 1. LISTAR (GET)
    @GetMapping
    public List<Producto> listar() {
        return repository.findAll();
    }

    // 2. OBTENER UNO (GET)
    @GetMapping("/{code}")
    public Producto obtener(@PathVariable String code) {
        return repository.findByCode(code);
    }

    // 3. CREAR (POST)
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Producto producto) {
        if (repository.findByCode(producto.getCode()) != null) {
            return ResponseEntity.badRequest().body("El código ya existe");
        }
        return ResponseEntity.ok(repository.save(producto));
    }

    // 4. ELIMINAR (DELETE) - ¡ESTE ES EL QUE FALTABA O FALLABA!
    @DeleteMapping("/{code}")
    public ResponseEntity<?> eliminar(@PathVariable String code) {
        try {
            Producto producto = repository.findByCode(code);
            if (producto == null) {
                return ResponseEntity.notFound().build();
            }
            
            repository.delete(producto);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace(); // Esto mostrará el error real en la consola si falla por FK
            return ResponseEntity.status(500).body("No se pudo eliminar: " + e.getMessage());
        }
    }

    // 5. ACTUALIZAR (PUT /api/products/{code})
    @PutMapping("/{code}")
    public ResponseEntity<?> actualizar(@PathVariable String code, @RequestBody Producto producto) {
        Producto productoExistente = repository.findByCode(code);
        
        if (productoExistente == null) {
            return ResponseEntity.notFound().build();
        }
        
        // Actualizamos los campos
        productoExistente.setName(producto.getName());
        productoExistente.setCategory(producto.getCategory());
        productoExistente.setPrice(producto.getPrice());
        productoExistente.setStock(producto.getStock());
        productoExistente.setImg(producto.getImg());
        productoExistente.setDescription(producto.getDescription());
        
        // Actualizar detalles si vienen
        if (producto.getDetails() != null) {
            productoExistente.setDetails(producto.getDetails());
        }
        
        repository.save(productoExistente);
        return ResponseEntity.ok(productoExistente);
    }
}