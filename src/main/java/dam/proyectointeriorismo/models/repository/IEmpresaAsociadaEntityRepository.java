package dam.proyectointeriorismo.models.repository;

import dam.proyectointeriorismo.models.entities.EmpresaAsociadaEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEmpresaAsociadaEntityRepository extends CrudRepository<EmpresaAsociadaEntity,Integer> {

    List<EmpresaAsociadaEntity> findByNombreContainingIgnoreCase(String nombre);
}
