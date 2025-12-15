package cl.duoc.levelupserver.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data  
@Entity 
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code; 

    private String name;
    
    private String category; 
    
    private Integer price;
    
    private Integer stock;
    
    private String img; 

    @Column(length = 1000) 
    private String description; 

    @ElementCollection 
    private List<String> details;
}