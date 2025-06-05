package dam.proyectointeriorismo.controllers;

import dam.proyectointeriorismo.models.repository.IClienteEntityRepository;
import dam.proyectointeriorismo.models.repository.IProyectoEntityRepository;
import dam.proyectointeriorismo.models.entities.ClienteEntity;
import dam.proyectointeriorismo.services.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClientesController {
    private final ClienteService clienteService;

    public ClientesController(IClienteEntityRepository clienteEntityDAO, IProyectoEntityRepository proyectoEntityDAO, ClienteService clienteService) {
        this.clienteService = clienteService;
    }


    @GetMapping
    public List<ClienteEntity> findAllClientes(){
        return clienteService.buscarClientes();
    }
    @GetMapping("/filtro")
    public String findAllClientesConFiltro(Model model,String nombre, String dni){
        model.addAttribute("nombreSelecionado",nombre);
        model.addAttribute("dniSeleccionado", dni);

        List<ClienteEntity> clientes = clienteService.buscarConFiltros(nombre, dni);
        model.addAttribute("clientes",clientes);

        return "Clientes/verClientes";
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
        Optional<ClienteEntity> optionalCliente = clienteService.findByDni(clienteEntity.getDni());
        if (optionalCliente.isPresent()){
            return ResponseEntity.badRequest().body("El Dni ya esta siendo utilizado.");
        }
        try{
            Optional<ClienteEntity> nuevoCliente = clienteService.saveCliente(clienteEntity);
            return ResponseEntity.ok(nuevoCliente);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
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
