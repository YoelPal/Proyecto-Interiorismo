package dam.proyectointeriorismo.models.repository;

import dam.proyectointeriorismo.models.entities.ClienteEntity;
import dam.proyectointeriorismo.models.entities.FacturaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFacturaEntityRepository extends CrudRepository<FacturaEntity,Integer> {
    Page<FacturaEntity> findByCliente(ClienteEntity cliente, Pageable pageable);
}
