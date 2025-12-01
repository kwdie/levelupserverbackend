package cl.duoc.levelupserver.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data  // Lombok crea los Getters y Setters solo
@Entity // Esto le dice a Spring: "Crea una tabla con esto"
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code; // El código que usas en el front (ej: "JM001")

    private String name;
    
    private String category; // "JM", "AC", etc.
    
    private Integer price;
    
    private Integer stock;
    
    private String img; // Ruta de la imagen (ej: "img/foto.png")

    @Column(length = 1000) // Para que quepan descripciones largas
    private String description; // En tu front se llama "desc", lo adaptaremos

    @ElementCollection // Esto crea una sub-tabla automática para la lista de detalles
    private List<String> details;
}