package dam.proyectointeriorismo.controllers;

import dam.proyectointeriorismo.models.entities.ClienteEntity;
import dam.proyectointeriorismo.services.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class ClienteWebController {
    private final ClienteService clienteService;

    public ClienteWebController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }
    @GetMapping("/menuclientes")
    public String mostrarMenuClientes() {
        return "menuclientes"; // Debe coincidir con el nombre del HTML en templates
    }

    @GetMapping("/altacliente")
    public String altaCliente(Model model) {
//Pasamos al modelo una DepartamentoEntity vacío
        model.addAttribute("cliente", new ClienteEntity());
        return "altacliente";
    }

    @PostMapping("/altacliente")
    public String saveCliente(@ModelAttribute ClienteEntity cliente, Model model){
        if (clienteService.saveCliente(cliente).isPresent()){
            model.addAttribute("tipo_operacion", "ok");
            model.addAttribute("mensaje","Cliente creado correctamente.");
        }else{
            model.addAttribute("tipo_operacion","error");
            model.addAttribute("mensaje", "Error al crear el cliente.");
        }

        return "altacliente";
    }


    @GetMapping("/actualizarCliente")
    public String updateCliente(@RequestParam("id") int id, Model model){
        Optional<ClienteEntity> cliente = clienteService.findClienteById(id);
        if (cliente.isPresent()){
            model.addAttribute("cliente",cliente.get());
            return "updatecliente";
        }else {
            model.addAttribute("tipo_operacion", "error");
            model.addAttribute("mensaje", "Cliente no encontrado.");
            return "buscarclienteid";
        }
    }

    @GetMapping("/buscarclienteid")
    public String mostrarFormularioBusqueda( Model model) {
        List<ClienteEntity> clientes = clienteService.buscarClientes();
        model.addAttribute("clientes",clientes);
        return "buscarclienteid";  // Nombre del archivo HTML en templates
    }

    @PostMapping("/actualizarcliente")
    public String actualizarCliente(@ModelAttribute ClienteEntity cliente, Model model) {
        Optional<ClienteEntity> clienteUpdate = clienteService.updateCliente(cliente);
        if (clienteUpdate.isPresent()) {
            model.addAttribute("tipo_operacion", "ok");
            model.addAttribute("mensaje", "Cliente actualizado correctamente.");

            model.addAttribute("cliente",clienteUpdate.get());
        } else {
            model.addAttribute("tipo_operacion", "error");
            model.addAttribute("mensaje", "Error al actualizar el cliente.");

            model.addAttribute("cliente", cliente);
        }

        return "updatecliente"; // Retorna la vista con el mensaje
    }





}
