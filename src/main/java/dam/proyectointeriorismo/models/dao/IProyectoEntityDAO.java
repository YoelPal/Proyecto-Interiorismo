package dam.proyectointeriorismo.models.dao;

import dam.proyectointeriorismo.models.entities.ProyectoEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProyectoEntityDAO extends CrudRepository<ProyectoEntity,Integer> {
}
