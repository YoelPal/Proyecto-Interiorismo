package dam.proyectointeriorismo.controllers;

import dam.proyectointeriorismo.models.entities.ClienteEntity;
import dam.proyectointeriorismo.models.entities.ProyectoEntity;
import dam.proyectointeriorismo.services.ClienteService;
import dam.proyectointeriorismo.services.ProyectoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class ClienteWebController {
    private final ClienteService clienteService;
    private final ProyectoService proyectoService;

    public ClienteWebController(ClienteService clienteService, ProyectoService proyectoService) {
        this.clienteService = clienteService;
        this.proyectoService = proyectoService;
    }
    @GetMapping("/menuclientes")
    public String mostrarMenuClientes() {
        return "menuclientes"; // Debe coincidir con el nombre del HTML en templates
    }

    @GetMapping("/altacliente")
    public String altaCliente(Model model) {
//Pasamos al modelo una DepartamentoEntity vacío
        model.addAttribute("cliente", new ClienteEntity());
        return "Clientes/altacliente";
    }

    @PostMapping("/altacliente")
    public String saveCliente(@ModelAttribute ClienteEntity cliente,RedirectAttributes redirectAttributes){
        try {

            Optional<ClienteEntity> existingCliente = clienteService.findByDni(cliente.getDni());

            if (existingCliente.isPresent()) {
                // Si el DNI ya existe, no guardamos y mostramos un mensaje de error
                redirectAttributes.addFlashAttribute("mensaje", "Error: Ya existe un cliente con el DNI " + cliente.getDni());
                redirectAttributes.addFlashAttribute("tipo_operacion", "error");
                // Redirige de vuelta al formulario de creación para que el usuario pueda corregir
                return "redirect:/altacliente";
            }

            clienteService.saveCliente(cliente);
            redirectAttributes.addFlashAttribute("mensaje", "Cliente creado exitosamente!");
            redirectAttributes.addFlashAttribute("tipo_operacion", "ok");
            // Redirige a la lista de clientes o a otra página de éxito
            return "redirect:/verClientes";

        } catch (Exception e) {
            // Manejo genérico de otros errores (ej. problemas de base de datos)
            redirectAttributes.addFlashAttribute("mensaje", "Error al crear el cliente: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipo_operacion", "error");
            // Redirige de vuelta al formulario de creación
            return "redirect:/altacliente";
        }
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

        return "Clientes/updatecliente"; // Retorna la vista con el mensaje
    }

    @PostMapping("/deleteCliente")
    public String deleteCliente(@RequestParam("id")int id, RedirectAttributes redirectAttributes){
        Optional<ClienteEntity> deletedCliente = clienteService.deleteCliente(id);
        if (deletedCliente.isPresent()) {
            redirectAttributes.addFlashAttribute("successMessage", "Cliente eliminado correctamente.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Error: No se encontró el cliente con ID " + id + " para eliminar.");
        }
        return "redirect:/verClientes";
    }

    @GetMapping("/detalleCliente")
    public String detalleCliente(@RequestParam("id")int id,Model model){
        Optional<ClienteEntity> clienteDetalle = clienteService.findClienteById(id);
        if (clienteDetalle.isPresent()){
            model.addAttribute("cliente",clienteDetalle.get());
            return "Clientes/detallecliente";
        }else{
            model.addAttribute("tipo_operacion","error");
            model.addAttribute("mensaje","Cliente no encontrado.");
            return "Clientes/verClientes";
        }

    }





}
