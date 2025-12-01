package cl.duoc.levelupserver.controller;

import cl.duoc.levelupserver.dto.OrderRequest;
import cl.duoc.levelupserver.model.Boleta;
import cl.duoc.levelupserver.security.JwtUtil;
import cl.duoc.levelupserver.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<?> crearPedido(
            @RequestHeader("Authorization") String tokenHeader,
            @RequestBody OrderRequest request) {
        
        try {
            // 1. Sacamos el email del Token (Así sabemos quién compra sin que nos mientan)
            String token = tokenHeader.substring(7); // Quitar "Bearer "
            String email = jwtUtil.extractUsername(token);

            // 2. Procesamos la compra
            Boleta nuevaBoleta = orderService.procesarCompra(email, request);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaBoleta);

        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar la compra");
        }
    }
}