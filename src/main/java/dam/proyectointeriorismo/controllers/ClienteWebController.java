package dam.proyectointeriorismo.controllers;

import dam.proyectointeriorismo.models.Enums.Estado;
import dam.proyectointeriorismo.models.entities.ClienteEntity;
import dam.proyectointeriorismo.models.entities.FacturaEntity;
import dam.proyectointeriorismo.models.entities.ProyectoEntity;
import dam.proyectointeriorismo.services.ClienteService;
import dam.proyectointeriorismo.services.FacturaService;
import dam.proyectointeriorismo.services.ProyectoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@Controller
public class ClienteWebController {
    private final ClienteService clienteService;
    private final ProyectoService proyectoService;
    private final FacturaService facturaService;

    public ClienteWebController(ClienteService clienteService, ProyectoService proyectoService, FacturaService facturaService) {
        this.clienteService = clienteService;
        this.proyectoService = proyectoService;
        this.facturaService = facturaService;
    }

    @GetMapping("/verClientes")
    public String mostrarClientes(Model model, @RequestParam(name = "estado",required = false)String estado) {

        List<ClienteEntity> clientes ;
        model.addAttribute("estados", Estado.values());
        if (estado!=null && !estado.trim().isEmpty()){
            Estado estadoEnum = Estado.valueOf(estado);
            clientes = clienteService.findClienteByEstado(estadoEnum);
        }else{
            clientes =  clienteService.buscarClientes();

        }
        model.addAttribute("clientes",clientes);
        return "Clientes/verClientes";
    }

    @GetMapping("/altacliente")
    public String altaCliente(Model model) {
        model.addAttribute("cliente", new ClienteEntity());
        return "Clientes/altacliente";
    }


    @GetMapping("/actualizarCliente")
    public String updateCliente(@RequestParam("id") int id, Model model){
        List<ProyectoEntity> proyectos = proyectoService.listaProyectos();
        model.addAttribute("proyectos",proyectos);
        Optional<ClienteEntity> cliente = clienteService.findClienteById(id);
        if (cliente.isPresent()){
            model.addAttribute("cliente",cliente.get());
            return "Clientes/updatecliente";
        }else {
            model.addAttribute("tipo_operacion", "error");
            model.addAttribute("mensaje", "Cliente no encontrado.");
            return "Clientes/verClientes";
        }
    }

    @GetMapping("/detalleCliente")
    public String detalleCliente(@RequestParam("id")int id, Model model,
                                 @PageableDefault(size = 5, sort = "fechaEmision", direction = Sort.Direction.DESC) Pageable pageable){

        Optional<ClienteEntity> clienteDetalle = clienteService.findClienteById(id);
        if (clienteDetalle.isPresent()){
            Page<FacturaEntity> facturaEntityPage = facturaService.findFacturasByCliente(clienteDetalle.get(),pageable);
            model.addAttribute("cliente",clienteDetalle.get());
            model.addAttribute("facturasPage",facturaEntityPage);
            return "Clientes/detallecliente";
        }else{
            model.addAttribute("tipo_operacion","error");
            model.addAttribute("mensaje","Cliente no encontrado.");
            return "Clientes/verClientes";
        }

    }


}
