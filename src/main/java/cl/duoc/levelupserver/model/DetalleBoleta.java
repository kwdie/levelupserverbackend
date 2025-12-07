package cl.duoc.levelupserver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "detalle_boletas")
public class DetalleBoleta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer cantidad;
    private Integer precioUnitario;

    // Relación inversa: Pertenece a una boleta
    @ManyToOne
    @JoinColumn(name = "boleta_id")
    @JsonIgnore // <--- 2. AGREGA ESTA ANOTACIÓN MÁGICA
    private Boleta boleta;

    // Relación: Apunta a un producto específico
    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;
}