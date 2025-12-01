package cl.duoc.levelupserver.service;

import cl.duoc.levelupserver.dto.OrderItemDto;
import cl.duoc.levelupserver.dto.OrderRequest;
import cl.duoc.levelupserver.model.Boleta;
import cl.duoc.levelupserver.model.DetalleBoleta;
import cl.duoc.levelupserver.model.Producto;
import cl.duoc.levelupserver.model.Usuario;
import cl.duoc.levelupserver.repository.BoletaRepository;
import cl.duoc.levelupserver.repository.DetalleBoletaRepository;
import cl.duoc.levelupserver.repository.ProductoRepository;
import cl.duoc.levelupserver.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private BoletaRepository boletaRepository;

    @Autowired
    private DetalleBoletaRepository detalleRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional 
    public Boleta procesarCompra(String emailUsuario, OrderRequest request) {
        
        // 1. Buscar al Usuario
        Usuario usuario = usuarioRepository.findByEmail(emailUsuario);
        if (usuario == null) throw new RuntimeException("Usuario no encontrado");

        // 2. Crear la Boleta (Cabecera)
        Boleta boleta = new Boleta();
        boleta.setUsuario(usuario);
        boleta.setFecha(new Date());
        boleta.setTotal(0); // Lo calculamos abajo
        
        // Guardamos la boleta primero para tener un ID
        boleta = boletaRepository.save(boleta);

        int totalCompra = 0;
        List<DetalleBoleta> detalles = new ArrayList<>();

        // 3. Procesar cada producto del carrito
        for (OrderItemDto item : request.getItems()) {
            Producto producto = productoRepository.findByCode(item.getCode());
            
            if (producto == null) {
                throw new RuntimeException("Producto no encontrado: " + item.getCode());
            }

            // Validar Stock
            if (producto.getStock() < item.getQty()) {
                throw new RuntimeException("Sin stock suficiente para: " + producto.getName());
            }

            // Descontar Stock
            producto.setStock(producto.getStock() - item.getQty());
            productoRepository.save(producto); // Actualizamos producto en BD

            // Crear Detalle
            DetalleBoleta detalle = new DetalleBoleta();
            detalle.setBoleta(boleta);
            detalle.setProducto(producto);
            detalle.setCantidad(item.getQty());
            detalle.setPrecioUnitario(producto.getPrice());
            
            // Guardar detalle
            detalleRepository.save(detalle);
            detalles.add(detalle);

            // Sumar al total
            totalCompra += (producto.getPrice() * item.getQty());
        }

        // 4. Actualizar el total final de la boleta
        boleta.setTotal(totalCompra);
        boleta.setDetalles(detalles); // (Opcional, para devolverla completa)
        
        return boletaRepository.save(boleta);
    }
}