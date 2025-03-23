package dam.proyectointeriorismo.models.dao;

import dam.proyectointeriorismo.models.entities.ClienteEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClienteEntityDAO extends CrudRepository<ClienteEntity,Integer> {
}
