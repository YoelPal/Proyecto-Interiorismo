package dam.proyectointeriorismo.models.dao;

import dam.proyectointeriorismo.models.entities.EmpresaAsociadaEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmpresaAsociadaEntityDAO extends CrudRepository<EmpresaAsociadaEntity,Integer> {
}
