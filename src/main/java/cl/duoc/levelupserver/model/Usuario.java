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

    private String firstName; 
    private String lastName;  

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    private String role; 

    private String region;
    private String comuna;
    private String address;
    
    private String birthDate;
}