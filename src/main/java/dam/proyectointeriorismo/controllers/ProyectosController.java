package dam.proyectointeriorismo.controllers;

import dam.proyectointeriorismo.models.entities.ProyectoEntity;
import dam.proyectointeriorismo.services.ProyectoService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/proyectos")
public class ProyectosController {
    private final ProyectoService proyectoService;

    public ProyectosController( ProyectoService proyectoService) {
        this.proyectoService = proyectoService;

    }

    @GetMapping
    public List<ProyectoEntity> findAllProyectos(){
        return proyectoService.listaProyectos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProyectoEntity> findProyectoById(@PathVariable(value = "id")int id){
        Optional<ProyectoEntity> proyectoOpt = proyectoService.findById(id);
        if (proyectoOpt.isPresent()){
            return ResponseEntity.ok().body(proyectoOpt.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> saveProyecto(@RequestBody ProyectoEntity proyectoEntity){
        try {
            ProyectoEntity saveProyecto = proyectoService.saveProyecto(proyectoEntity);
            return ResponseEntity.ok().body(saveProyecto);
        }catch (IllegalArgumentException e ){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //No estoy seguro si al borrar mejor devolver el objeto para verlo o simplemente devolver el mensaje de confirmacion

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProyecto(@PathVariable(value = "id")int id){

        Optional<ProyectoEntity> proyectoBorrado= proyectoService.findById(id);

        if (proyectoService.deleteProyecto(id)){
            ResponseEntity.ok().body(proyectoBorrado.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProyectoEntity> updateProyecto(@RequestBody ProyectoEntity proyecto,@PathVariable(value = "id") int id){
        Optional<ProyectoEntity> proyectoOpt = proyectoService.updateProyecto(proyecto,id);
        if (proyectoOpt.isPresent()){
            return ResponseEntity.ok(proyectoOpt.get());
        }
        return ResponseEntity.notFound().build();
    }
}
