package dam.proyectointeriorismo.controllers;

import dam.proyectointeriorismo.models.Enums.Estado;
import dam.proyectointeriorismo.models.Enums.EstadoFactura;
import dam.proyectointeriorismo.models.Enums.Tipo;
import dam.proyectointeriorismo.models.entities.FacturaEntity;
import dam.proyectointeriorismo.models.entities.ProyectoEntity;
import dam.proyectointeriorismo.models.repository.IFacturaEntityRepository;
import dam.proyectointeriorismo.services.ClienteService;
import dam.proyectointeriorismo.services.EmpresaAsocidadaService;
import dam.proyectointeriorismo.services.FacturaService;
import dam.proyectointeriorismo.services.ProyectoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class FacturesWebController {

    private final FacturaService facturaService;
    private final ClienteService clienteService;
    private final ProyectoService proyectoService;
    private final EmpresaAsocidadaService empresaAsocidadaService;

    public FacturesWebController(FacturaService facturaService, ClienteService clienteService, ProyectoService proyectoService, EmpresaAsocidadaService empresaAsocidadaService) {
        this.facturaService = facturaService;
        this.clienteService = clienteService;
        this.proyectoService = proyectoService;
        this.empresaAsocidadaService = empresaAsocidadaService;
    }

    @GetMapping("/verFacturas")
    public String mostrarFacturas(Model model){
        List<FacturaEntity> facturas = facturaService.findAllFacturas();
        model.addAttribute("facturas",facturas);
        model.addAttribute("estadosFactura",EstadoFactura.values());
        Map<String, String> estadosMap = Arrays.stream(EstadoFactura.values())
                .collect(Collectors.toMap(
                        EstadoFactura::name,
                        EstadoFactura::getNombreVisible
                ));
        model.addAttribute("estadosMap", estadosMap);
        return "Facturas/verFacturas";
    }


    @GetMapping("/altafactura")
    public String altaFactura(Model model) {
//Pasamos al modelo una DepartamentoEntity vacío
        model.addAttribute("factura", new FacturaEntity());
        model.addAttribute("empresasDisponibles", empresaAsocidadaService.findAllEmpresaAsociada());
        model.addAttribute("clientesDisponibles", clienteService.buscarClientes());
        model.addAttribute("proyectosDisponibles", proyectoService.listaProyectos());
        // Para el enum:
        model.addAttribute("estadofactura", EstadoFactura.values()); // O una lista específica si no quieres todos los valores

        return "Facturas/altafactura";
    }


    @GetMapping("/actualizarFactura")
    public String updateFactura(@RequestParam("id") int id, Model model) {
        Optional<FacturaEntity> optionalFactura = facturaService.findFacturaById(id);
        if (optionalFactura.isPresent()){
            FacturaEntity factura = optionalFactura.get();
            model.addAttribute("factura", factura);
            model.addAttribute("estadofactura", EstadoFactura.values());

        }else {

            model.addAttribute("tipo_operacion", "error");
            model.addAttribute("mensaje", "Factura no encontrada.");
            model.addAttribute("factura", new FacturaEntity());

        }

        return "Facturas/updatefactura";
    }
}
