package dam.proyectointeriorismo.controllers;

import dam.proyectointeriorismo.models.entities.ClienteEntity;
import dam.proyectointeriorismo.models.entities.EmpresaAsociadaEntity;
import dam.proyectointeriorismo.models.entities.ProyectoEntity;
import dam.proyectointeriorismo.services.ClienteService;
import dam.proyectointeriorismo.services.EmpresaAsocidadaService;
import dam.proyectointeriorismo.services.ProyectoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class webController {
    private final ClienteService clienteService;
    private final ProyectoService proyectoService;
    private final EmpresaAsocidadaService empresaAsocidadaService;

    public webController(ClienteService clienteService, ProyectoService proyectoService, ProyectoService proyectoService1, EmpresaAsocidadaService empresaAsocidadaService) {
        this.clienteService = clienteService;
        this.proyectoService = proyectoService1;
        this.empresaAsocidadaService = empresaAsocidadaService;
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

    @GetMapping("/verProyectos")
    public String mostrarProyectos(Model model){
        List<ProyectoEntity> proyectos = proyectoService.listaProyectos();
        model.addAttribute("proyectos",proyectos);
        return "verProyectos";
    }

    @GetMapping("/verEmpresasAsociadas")
    public String mostrarEmpresasAsociadas(Model model){
        List<EmpresaAsociadaEntity> empresasAsociadas = empresaAsocidadaService.findAllEmpresaAsociada();
        model.addAttribute("empresasAsociadas", empresasAsociadas);
        return "verEmpresasAsociadas";
    }
}
