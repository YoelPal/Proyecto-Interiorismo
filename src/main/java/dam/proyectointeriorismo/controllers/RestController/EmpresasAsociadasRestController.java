package dam.proyectointeriorismo.controllers.RestController;

import dam.proyectointeriorismo.models.entities.EmpresaAsociadaEntity;
import dam.proyectointeriorismo.services.EmpresaAsocidadaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/empresas")
public class EmpresasAsociadasRestController {
    private final EmpresaAsocidadaService empresaAsocidadaService;


    public EmpresasAsociadasRestController(EmpresaAsocidadaService empresaAsocidadaService) {
        this.empresaAsocidadaService = empresaAsocidadaService;
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<EmpresaAsociadaEntity>> buscarEmpresas(@RequestParam(required = false) String nombre) {
        List<EmpresaAsociadaEntity> resultados;

        if (nombre != null && !nombre.trim().isEmpty()) {
            resultados = empresaAsocidadaService.findByNombreContainingIgnoreCase(nombre);
        } else {
            resultados = empresaAsocidadaService.findAllEmpresaAsociada();
        }
        if (resultados.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(resultados);
    }

}
