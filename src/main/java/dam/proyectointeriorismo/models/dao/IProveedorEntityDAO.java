package dam.proyectointeriorismo.models.dao;

import dam.proyectointeriorismo.models.entities.ProveedorEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProveedorEntityDAO extends CrudRepository<ProveedorEntity,Integer> {
}
