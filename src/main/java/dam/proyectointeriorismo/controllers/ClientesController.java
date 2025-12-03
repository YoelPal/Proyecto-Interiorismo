package dam.proyectointeriorismo.controllers;

import dam.proyectointeriorismo.models.entities.ClienteEntity;
import dam.proyectointeriorismo.services.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class ClientesController {
    private final ClienteService clienteService;


    public ClientesController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }


    @PostMapping("/altacliente")
    public String saveCliente(@ModelAttribute ClienteEntity cliente, RedirectAttributes redirectAttributes){
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
            return "redirect:/verClientes";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Error al crear el cliente: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipo_operacion", "error");
            return "redirect:/altacliente";
        }
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

        return "Clientes/updatecliente";
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



}
