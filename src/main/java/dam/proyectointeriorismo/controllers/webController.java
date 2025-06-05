package dam.proyectointeriorismo.controllers;

import dam.proyectointeriorismo.models.Enums.Estado;
import dam.proyectointeriorismo.models.entities.ClienteEntity;
import dam.proyectointeriorismo.models.entities.EmpresaAsociadaEntity;
import dam.proyectointeriorismo.models.entities.ProyectoEntity;
import dam.proyectointeriorismo.services.ClienteService;
import dam.proyectointeriorismo.services.EmpresaAsocidadaService;
import dam.proyectointeriorismo.services.ProyectoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class webController {
    private final ClienteService clienteService;
    private final ProyectoService proyectoService;
    private final EmpresaAsocidadaService empresaAsocidadaService;

    public webController(ClienteService clienteService, ProyectoService proyectoService, EmpresaAsocidadaService empresaAsocidadaService) {
        this.clienteService = clienteService;
        this.proyectoService = proyectoService;
        this.empresaAsocidadaService = empresaAsocidadaService;
    }

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/verClientes")
    public String mostrarClientes(Model model, @RequestParam(name = "estado",required = false)String estado) {

        List<ClienteEntity> clientes ;
        List<String> estados = proyectoService.estados();
        model.addAttribute("estados",Estado.values());
        if (estado!=null && !estado.trim().isEmpty()){
            Estado estadoEnum = Estado.valueOf(estado);
            clientes = clienteService.findClienteByEstado(estadoEnum);
        }else{
            clientes =  clienteService.buscarClientes();

        }
        model.addAttribute("clientes",clientes);
        return "Clientes/verClientes";
    }






}
