package cl.duoc.levelupserver.repository;

import cl.duoc.levelupserver.model.Boleta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BoletaRepository extends JpaRepository<Boleta, Long> {
    List<Boleta> findByUsuarioEmailOrderByFechaDesc(String email);
}