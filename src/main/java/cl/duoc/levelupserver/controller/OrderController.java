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
@RequestMapping("/api/orders") 
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BoletaRepository boletaRepository;

    // 1. CREAR PEDIDO 
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

    // 2. VER  PEDIDOs ADMIN
    @GetMapping
    public ResponseEntity<List<Boleta>> listarTodas() {
        return ResponseEntity.ok(boletaRepository.findAll());
    }

    // 3. ver PEDIDOS  CLIENTE
    @GetMapping("/my-orders")
    public ResponseEntity<List<Boleta>> misPedidos(@RequestHeader("Authorization") String tokenHeader) {
        try {
            String token = tokenHeader.substring(7);
            String email = jwtUtil.extractUsername(token);
            List<Boleta> boletas = boletaRepository.findByUsuarioEmailOrderByFechaDesc(email);
            return ResponseEntity.ok(boletas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}