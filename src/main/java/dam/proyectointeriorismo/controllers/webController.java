package dam.proyectointeriorismo.controllers;

import dam.proyectointeriorismo.models.entities.ClienteEntity;
import dam.proyectointeriorismo.services.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class webController {


    private final ClienteService clienteService;

    public webController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/verClientes")
    public String mostrarClientes(Model model) {
        List<ClienteEntity> clientes = clienteService.buscarClientes();
        model.addAttribute("clientes",clientes);
        return "verClientes";
    }
}
