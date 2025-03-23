package dam.proyectointeriorismo.models.dao;

import dam.proyectointeriorismo.models.entities.FacturaEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFacturaEntityDAO extends CrudRepository<FacturaEntity,Integer> {
}
