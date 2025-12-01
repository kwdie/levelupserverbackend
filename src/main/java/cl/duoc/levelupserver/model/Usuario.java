package cl.duoc.levelupserver.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String run;

    private String firstName; // En frontend es firstName
    private String lastName;  // En frontend es lastName

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    private String role; // "Administrador", "Cliente", "Vendedor"

    private String region;
    private String comuna;
    private String address;
    
    private String birthDate;
}