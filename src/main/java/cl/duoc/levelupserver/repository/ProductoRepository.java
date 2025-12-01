package cl.duoc.levelupserver.repository;

import cl.duoc.levelupserver.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    Producto findByCode(String code);
}