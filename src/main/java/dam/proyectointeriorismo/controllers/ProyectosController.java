package dam.proyectointeriorismo.controllers;

import dam.proyectointeriorismo.models.dao.IProyectoEntityDAO;
import dam.proyectointeriorismo.models.entities.ProyectoEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/proyectos")
public class ProyectosController {
    private final IProyectoEntityDAO proyectoEntityDAO;

    public ProyectosController(IProyectoEntityDAO proyectoEntityDAO) {
        this.proyectoEntityDAO = proyectoEntityDAO;
    }

    @GetMapping
    public List<ProyectoEntity> findAllProyectos(){
        return (List<ProyectoEntity>) proyectoEntityDAO.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProyectoEntity> findProyectoById(@PathVariable(value = "id")int id){
        Optional<ProyectoEntity> proyectoOpt = proyectoEntityDAO.findById(id);
            return proyectoOpt.map(
                    proyectoEntity -> ResponseEntity.ok().body(proyectoEntity)).orElseGet(
                            () ->ResponseEntity.notFound().build());

    }

    @PostMapping
    public ProyectoEntity saveProyecto(@RequestBody ProyectoEntity proyectoEntity){
        return proyectoEntityDAO.save(proyectoEntity);
    }
}
