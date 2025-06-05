package dam.proyectointeriorismo.models.repository;

import dam.proyectointeriorismo.models.entities.FacturaEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFacturaEntityRepository extends CrudRepository<FacturaEntity,Integer> {
}
