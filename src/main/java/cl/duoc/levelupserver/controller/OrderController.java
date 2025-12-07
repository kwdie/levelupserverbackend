package cl.duoc.levelupserver.controller;

import cl.duoc.levelupserver.dto.OrderRequest;
import cl.duoc.levelupserver.model.Boleta;
import cl.duoc.levelupserver.repository.BoletaRepository;
import cl.duoc.levelupserver.security.JwtUtil;
import cl.duoc.levelupserver.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders") // Ruta base para todos
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BoletaRepository boletaRepository;

    // 1. CREAR PEDIDO (POST /api/orders)
    @PostMapping
    public ResponseEntity<?> crearPedido(
            @RequestHeader("Authorization") String tokenHeader,
            @RequestBody OrderRequest request) {
        try {
            String token = tokenHeader.substring(7);
            String email = jwtUtil.extractUsername(token);
            Boleta nuevaBoleta = orderService.procesarCompra(email, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaBoleta);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar");
        }
    }

    // 2. VER TODOS LOS PEDIDOS (GET /api/orders) - Para el ADMIN
    @GetMapping
    public ResponseEntity<List<Boleta>> listarTodas() {
        // En un caso real, aquí validarías que el usuario sea Admin
        return ResponseEntity.ok(boletaRepository.findAll());
    }

    // 3. VER MIS PEDIDOS (GET /api/orders/my-orders) - Para el CLIENTE
    @GetMapping("/my-orders")
    public ResponseEntity<List<Boleta>> misPedidos(@RequestHeader("Authorization") String tokenHeader) {
        try {
            String token = tokenHeader.substring(7);
            String email = jwtUtil.extractUsername(token);
            // Buscamos solo las de este usuario
            List<Boleta> boletas = boletaRepository.findByUsuarioEmailOrderByFechaDesc(email);
            return ResponseEntity.ok(boletas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}