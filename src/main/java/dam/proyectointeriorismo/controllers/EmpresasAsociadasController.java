package dam.proyectointeriorismo.controllers;

import dam.proyectointeriorismo.models.entities.EmpresaAsociadaEntity;
import dam.proyectointeriorismo.services.EmpresaAsocidadaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/empresasAsociadas")
public class EmpresasAsociadasController {
    private final EmpresaAsocidadaService empresaAsocidadaService;

    public EmpresasAsociadasController(EmpresaAsocidadaService empresaAsocidadaService) {
        this.empresaAsocidadaService = empresaAsocidadaService;
    }

    @GetMapping
    public ResponseEntity<List<EmpresaAsociadaEntity>> findAllEmpresasAsociadas(){
        List<EmpresaAsociadaEntity> listaEmpresas = empresaAsocidadaService.findAllEmpresaAsociada();
        if (listaEmpresas.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listaEmpresas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpresaAsociadaEntity> findEmpresaById(@PathVariable(value = "id")int id){
        Optional<EmpresaAsociadaEntity> empresaAsociadaEntityOptional = empresaAsocidadaService.findEmpresaById(id);
        if (empresaAsociadaEntityOptional.isPresent()){
            return ResponseEntity.ok(empresaAsociadaEntityOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<EmpresaAsociadaEntity> saveEmpresaAsociada(@RequestBody EmpresaAsociadaEntity empresaAsociada){
        Optional<EmpresaAsociadaEntity> newEmpresa = empresaAsocidadaService.saveEmpresa(empresaAsociada);
        if (newEmpresa.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(newEmpresa.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmpresa(@PathVariable(value = "id")int id){
        Optional<EmpresaAsociadaEntity> empresaOpt = empresaAsocidadaService.deleteCliente(id);
        if (empresaOpt.isPresent()) {
           return ResponseEntity.ok(empresaOpt.get());
        }
        return ResponseEntity.notFound().build();
    }
}
