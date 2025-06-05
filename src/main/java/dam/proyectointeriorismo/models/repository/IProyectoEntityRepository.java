package dam.proyectointeriorismo.models.repository;

import dam.proyectointeriorismo.models.Enums.Estado;
import dam.proyectointeriorismo.models.entities.ProyectoEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProyectoEntityRepository extends CrudRepository<ProyectoEntity,Integer> {

    @Query("select distinct P.estado from ProyectoEntity P")
    List<String> findAllEstados();
    List<ProyectoEntity> findProyectoEntitiesByEstado(Estado estado);

    List<ProyectoEntity> findByNombreContainingIgnoreCase(String nombre);
}
