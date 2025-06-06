package dam.proyectointeriorismo.controllers.RestController;

import dam.proyectointeriorismo.models.entities.ProyectoEntity;
import dam.proyectointeriorismo.services.ProyectoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/proyectos")
public class ProyectosRestController {

    private final ProyectoService proyectoService;

    public ProyectosRestController(ProyectoService proyectoService) {
        this.proyectoService = proyectoService;
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ProyectoEntity>> buscarProyectos(@RequestParam(required = false) String nombre) {
        List<ProyectoEntity> resultados;

        if (nombre != null && !nombre.trim().isEmpty()) {
            resultados = proyectoService.findByNombreContainingIgnoreCase(nombre);
        } else {
            resultados = proyectoService.listaProyectos();
        }
        if (resultados.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(resultados);
    }
}
