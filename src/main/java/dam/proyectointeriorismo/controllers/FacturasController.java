package dam.proyectointeriorismo.controllers;

import dam.proyectointeriorismo.models.entities.FacturaEntity;
import dam.proyectointeriorismo.services.FacturaService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/facturas")
public class FacturasController {
    private final FacturaService facturaService;

    public FacturasController(FacturaService facturaService) {
        this.facturaService = facturaService;
    }

    @GetMapping
    public ResponseEntity<List<FacturaEntity>> findAllFacturas(){
        List<FacturaEntity> listaFacturas = facturaService.findAllFacturas();
        if (!listaFacturas.isEmpty()) {
            ResponseEntity.ok(listaFacturas);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacturaEntity> findFacturaById(@PathVariable(value = "id") int id){
        Optional<FacturaEntity> factura = facturaService.findFacturaById(id);
        if (factura.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(factura.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delteFacturaById(@PathVariable(value = "id")int id){
        if (facturaService.deleteFactura(id)){
            return ResponseEntity.ok().body("Factura Eliminada.");
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<FacturaEntity> saveFactura(@RequestBody FacturaEntity facturaEntity){
        Optional<FacturaEntity> newFactura = facturaService.saveFactura(facturaEntity);
        if (!newFactura.isEmpty()){
            ResponseEntity.ok(newFactura.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateFactura(@PathVariable(value = "id")int id,@RequestBody FacturaEntity factura){
        Optional<FacturaEntity> facturaUpdate = facturaService.updateFactura(factura,id);
        if (facturaUpdate.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(facturaUpdate.get());
    }
}
