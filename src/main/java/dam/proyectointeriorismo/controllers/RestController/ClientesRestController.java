package dam.proyectointeriorismo.controllers.RestController;

import dam.proyectointeriorismo.models.entities.ClienteEntity;
import dam.proyectointeriorismo.services.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClientesRestController {

    private final ClienteService clienteService;

    public ClientesRestController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ClienteEntity>> buscarClientes(@RequestParam(required = false) String nombre) {
        List<ClienteEntity> resultados;

        if (nombre != null && !nombre.trim().isEmpty()) {
            resultados = clienteService.findByNombreContainingIgnoreCase(nombre);
        } else {
            resultados = clienteService.buscarClientes();
        }
        if (resultados.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(resultados);
    }
}
