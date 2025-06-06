package dam.proyectointeriorismo.controllers;

import dam.proyectointeriorismo.models.Enums.Estado;
import dam.proyectointeriorismo.models.Enums.Tipo;
import dam.proyectointeriorismo.models.entities.ClienteEntity;
import dam.proyectointeriorismo.models.entities.ProyectoEntity;
import dam.proyectointeriorismo.services.ClienteService;
import dam.proyectointeriorismo.services.ProyectoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class ProyectosWebController {

    private final ClienteService clienteService;
    private final ProyectoService proyectoService;

    public ProyectosWebController(ProyectoService proyectoService, ClienteService clienteService, ClienteService clienteService1) {
        this.proyectoService = proyectoService;
        this.clienteService = clienteService1;
    }


    @GetMapping("/verProyectos")
    public String mostrarProyectos(Model model){
        List<ProyectoEntity> proyectos = proyectoService.listaProyectos();
        model.addAttribute("proyectos",proyectos);
        return "Proyectos/verProyectos";
    }


    @GetMapping("/altaproyecto")
    public String altaProyecto(Model model) {
        List<ClienteEntity> clientes = clienteService.buscarClientes();
        model.addAttribute("clientes",clientes);
        model.addAttribute("proyecto", new ProyectoEntity());
        model.addAttribute("estadosProyecto", Estado.values());
        model.addAttribute("tipoProyecto", Tipo.values());
        return "Proyectos/altaproyecto";
    }


    @GetMapping("/actualizarProyecto")
    public String updateProyecto(@RequestParam("id") int id, Model model) {
        List<ClienteEntity> clientes = clienteService.buscarClientes();
        model.addAttribute("clientes",clientes);
        Optional<ProyectoEntity> optionalProyecto = proyectoService.findById(id);
        if (optionalProyecto.isPresent()){
            ProyectoEntity proyecto = optionalProyecto.get();
            model.addAttribute("proyecto", proyecto);
        }else {
            model.addAttribute("tipo_operacion", "error");
            model.addAttribute("mensaje", "Proyecto no encontrado.");
            model.addAttribute("proyecto", new ProyectoEntity());
        }
        model.addAttribute("estadosProyecto", Estado.values());
        model.addAttribute("tipoProyecto",Tipo.values());

        return "Proyectos/updateproyecto";
    }
}


