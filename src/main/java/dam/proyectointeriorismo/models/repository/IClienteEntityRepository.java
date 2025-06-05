package dam.proyectointeriorismo.models.repository;

import dam.proyectointeriorismo.models.Enums.Estado;
import dam.proyectointeriorismo.models.entities.ClienteEntity;
import dam.proyectointeriorismo.models.entities.ProyectoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IClienteEntityRepository extends JpaRepository<ClienteEntity,Integer>, JpaSpecificationExecutor<ClienteEntity> {

    @Query("select c from ClienteEntity c where c.proyecto.estado = ?1")
    public List<ClienteEntity> findClientesByEstado(Estado estado);

    List<ClienteEntity> findByNombreContainingIgnoreCase(String nombre);

    Optional<ClienteEntity> findByDni(String dni);
}
