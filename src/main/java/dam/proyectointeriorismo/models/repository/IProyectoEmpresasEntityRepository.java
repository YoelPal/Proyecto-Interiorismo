package dam.proyectointeriorismo.models.repository;

import dam.proyectointeriorismo.models.entities.ProyectoEmpresasEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProyectoEmpresasEntityRepository extends CrudRepository<ProyectoEmpresasEntity,Integer> {
}
