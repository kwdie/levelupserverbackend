package cl.duoc.levelupserver.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;
import java.util.Date;

@Data
@Entity
@Table(name = "boletas")
public class Boleta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date fecha;
    private Integer total;

    // Relación: Una boleta pertenece a UN usuario
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    // Relación: Una boleta tiene MUCHOS detalles
    @OneToMany(mappedBy = "boleta", cascade = CascadeType.ALL)
    private List<DetalleBoleta> detalles;
}