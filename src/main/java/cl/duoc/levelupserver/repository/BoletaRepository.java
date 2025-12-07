package cl.duoc.levelupserver.repository;

import cl.duoc.levelupserver.model.Boleta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BoletaRepository extends JpaRepository<Boleta, Long> {
    // Buscar todas las boletas de un usuario por su email, ordenadas por fecha (más reciente primero)
    List<Boleta> findByUsuarioEmailOrderByFechaDesc(String email);
}