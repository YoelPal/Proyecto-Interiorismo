package dam.proyectointeriorismo.services;

import dam.proyectointeriorismo.models.dao.IProyectoEntityDAO;
import dam.proyectointeriorismo.models.entities.ProyectoEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProyectoService {

    private final IProyectoEntityDAO proyectoEntityDAO;


    public ProyectoService(IProyectoEntityDAO proyectoEntityDAO) {
        this.proyectoEntityDAO = proyectoEntityDAO;
    }

    public List<ProyectoEntity> listaProyectos(){
        return (List<ProyectoEntity>) proyectoEntityDAO.findAll();
    }

    public Optional<ProyectoEntity> findById(int id){
        return proyectoEntityDAO.findById(id);
    }

    public ProyectoEntity saveProyecto(ProyectoEntity proyecto){
        return proyectoEntityDAO.save(proyecto);
    }

    public boolean deleteProyecto(int id){
        if (proyectoEntityDAO.existsById(id)){
            proyectoEntityDAO.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<ProyectoEntity> updateProyecto(ProyectoEntity proyecto,int id){
        if (proyectoEntityDAO.findById(id).isPresent()){
            proyecto.setId(id);
            return Optional.of( proyectoEntityDAO.save(proyecto));
        }
        return Optional.empty();
    }
}
