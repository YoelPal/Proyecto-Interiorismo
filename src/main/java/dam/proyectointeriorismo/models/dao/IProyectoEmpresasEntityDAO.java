package dam.proyectointeriorismo.models.dao;

import dam.proyectointeriorismo.models.entities.ProyectoEmpresasEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProyectoEmpresasEntityDAO extends CrudRepository<ProyectoEmpresasEntity,Integer> {
}
