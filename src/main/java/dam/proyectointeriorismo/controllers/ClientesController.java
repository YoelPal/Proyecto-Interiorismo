package dam.proyectointeriorismo.controllers;

import dam.proyectointeriorismo.models.dao.IClienteEntityDAO;
import dam.proyectointeriorismo.models.dao.IProyectoEntityDAO;
import dam.proyectointeriorismo.models.entities.ClienteEntity;
import dam.proyectointeriorismo.services.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClientesController {
    private final ClienteService clienteService;

    public ClientesController(IClienteEntityDAO clienteEntityDAO, IProyectoEntityDAO proyectoEntityDAO, ClienteService clienteService) {
        this.clienteService = clienteService;
    }


    @GetMapping
    public List<ClienteEntity> findAllClientes(){
        return clienteService.buscarClientes();
    }


    @GetMapping("/{id}")
    public ResponseEntity<ClienteEntity> findClienteById(@PathVariable(value = "id")int id){
        Optional<ClienteEntity> clienteOpt = clienteService.findClienteById(id);

        return clienteOpt.map(
                clienteEntity
                        -> ResponseEntity.ok().body(clienteEntity)).orElseGet(
                                ()-> ResponseEntity.noContent().build());
    }

    @PostMapping
    public ResponseEntity<?> saveCliente(@RequestBody ClienteEntity clienteEntity){
        try{
            Optional<ClienteEntity> nuevoCliente = clienteService.saveCliente(clienteEntity);
            return ResponseEntity.ok(nuevoCliente);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCliente(@PathVariable(value = "id") int id){
        boolean eliminado = clienteService.deleteCliente(id);
        if (eliminado){
            return ResponseEntity.ok().body("Cliente Borrado Correctamente");
        }else {
            return ResponseEntity.notFound().build();
        }
    }


   /* @PutMapping("/{id}")
    public ResponseEntity<?> updateCliente (@RequestBody ClienteEntity cliente,@PathVariable (value = "id")int id){
        Optional<ClienteEntity> clienteOpt = clienteService.updateCliente(cliente,id);
        if (clienteOpt.isPresent()){
            return ResponseEntity.ok().body(clienteOpt.get());//No se si devolver mensaje de texto o el objeto
        }else {
            return ResponseEntity.notFound().build();
        }

    }*/
}
