package cl.duoc.levelupserver.controller;

import cl.duoc.levelupserver.model.Producto;
import cl.duoc.levelupserver.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*") // Permite que cualquiera (Postman, React) entre
public class ProductController {

    @Autowired
    private ProductoRepository repository;

    // 1. Obtener todos (GET /api/products)
    @GetMapping
    public List<Producto> listar() {
        return repository.findAll();
    }

    // 2. Obtener uno por código (GET /api/products/PS5-001)
    @GetMapping("/{code}")
    public Producto obtener(@PathVariable String code) {
        return repository.findByCode(code);
    }

    // 3. Crear producto (POST /api/products)
    @PostMapping
    public Producto crear(@RequestBody Producto producto) {
        return repository.save(producto);
    }
}