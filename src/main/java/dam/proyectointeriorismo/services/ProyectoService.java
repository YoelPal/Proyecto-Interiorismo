package dam.proyectointeriorismo.services;

import dam.proyectointeriorismo.models.entities.EmpresaAsociadaEntity;
import dam.proyectointeriorismo.models.repository.IProyectoEntityRepository;
import dam.proyectointeriorismo.models.entities.ProyectoEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProyectoService  {

    private final IProyectoEntityRepository proyectoEntityRepository;


    public ProyectoService(IProyectoEntityRepository proyectoEntityDAO) {
        this.proyectoEntityRepository = proyectoEntityDAO;
    }

    public List<ProyectoEntity> listaProyectos(){
        return (List<ProyectoEntity>) proyectoEntityRepository.findAll();
    }

    public Optional<ProyectoEntity> findById(int id){
        return proyectoEntityRepository.findById(id);
    }

    public Optional<ProyectoEntity> saveProyecto(ProyectoEntity proyecto){
        return Optional.of(proyectoEntityRepository.save(proyecto)) ;
    }

    public List<ProyectoEntity> findByNombreContainingIgnoreCase(String nombre) {
        return proyectoEntityRepository.findByNombreContainingIgnoreCase(nombre);
    }

    public Optional<ProyectoEntity>deleteProyecto(int id){
        Optional<ProyectoEntity> optionalProyecto = proyectoEntityRepository.findById(id) ;

        if (optionalProyecto.isPresent()){
            proyectoEntityRepository.deleteById(id);
            return optionalProyecto;
        }
            return Optional.empty();

    }
    public List<String> estados(){
       return  proyectoEntityRepository.findAllEstados();
    }

    public Optional<ProyectoEntity> updateProyecto(int id,ProyectoEntity proyecto){
        Optional<ProyectoEntity> proyectoEntityOptional = proyectoEntityRepository.findById(id);
        if (proyectoEntityOptional.isPresent()){
            proyecto.setId(id);
            return Optional.of( proyectoEntityRepository.save(proyecto));
        }
        return Optional.empty();
    }
}
