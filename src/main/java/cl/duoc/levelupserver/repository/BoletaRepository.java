package cl.duoc.levelupserver.repository;

import cl.duoc.levelupserver.model.Boleta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoletaRepository extends JpaRepository<Boleta, Long> {
    // Aquí podrías agregar métodos para buscar boletas por usuario si lo necesitas
}