package dam.proyectointeriorismo.controllers;

import dam.proyectointeriorismo.models.entities.EmpresaAsociadaEntity;
import dam.proyectointeriorismo.models.entities.FacturaEntity;
import dam.proyectointeriorismo.models.entities.ProyectoEntity;
import dam.proyectointeriorismo.services.FacturaService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping
public class FacturasController {
    private final FacturaService facturaService;

    public FacturasController(FacturaService facturaService) {
        this.facturaService = facturaService;
    }

    @GetMapping("/verfacturas")
    public List<FacturaEntity> findAllProyectos(){
        return facturaService.findAllFacturas();
    }

    @PostMapping("/deletefactura")
    public String deleteFactura(@RequestParam("id")int id, RedirectAttributes redirectAttributes){
        Optional<FacturaEntity> deletedProyecto = facturaService.deleteFactura(id);
        if (deletedProyecto.isPresent()) {
            redirectAttributes.addFlashAttribute("successMessage", "Factura eliminada correctamente.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Error: No se encontró la factura con ID " + id + " para eliminar.");
        }
        return "redirect:/verFacturas";
    }


    @PostMapping("/savefactura")
    public String saveFactura(@ModelAttribute FacturaEntity facturaEntity, Model model){
        try {
            Optional<FacturaEntity> newFactura = facturaService.saveFactura(facturaEntity);
            if (newFactura.isPresent()) {
                model.addAttribute("tipo_operacion", "ok");
                model.addAttribute("mensaje", "Factura creada correctamente.");
                model.addAttribute("factura", newFactura.get()); // Pasa la empresa actualizada al modelo
            } else {
                model.addAttribute("tipo_operacion", "error");
                model.addAttribute("mensaje", "Error al crear la factura .");
                model.addAttribute("factura", facturaEntity); // Mantiene los datos del formulario si hay un error
            }
        }catch (Exception e) {
            // Captura cualquier otra excepción durante la actualización
            model.addAttribute("tipo_operacion", "error");
            model.addAttribute("mensaje", "Ocurrió un error inesperado al actualizar: " + e.getMessage());
            model.addAttribute("factura", facturaEntity); // Mantiene los datos del formulario si hay un error
        }

        return "Facturas/altafactura";

    }

    @PostMapping("/updateFactura")
    public String updateFactura(@ModelAttribute("factura") FacturaEntity factura, Model model) {

        try {
            // Llama a tu servicio para guardar/actualizar la empresa
            Optional<FacturaEntity> facturaActualizada = facturaService.updateFactura(factura, factura.getId());

            if (facturaActualizada.isPresent()) {
                model.addAttribute("tipo_operacion", "ok");
                model.addAttribute("mensaje", "Factura actualizada correctamente.");
                model.addAttribute("factura", facturaActualizada.get()); // Pasa la empresa actualizada al modelo
            } else {
                model.addAttribute("tipo_operacion", "error");
                model.addAttribute("mensaje", "Error al actualizar la factura (ID no encontrado o problema en servicio).");
                model.addAttribute("factura", factura); // Mantiene los datos del formulario si hay un error
            }
        } catch (Exception e) {
            // Captura cualquier otra excepción durante la actualización
            model.addAttribute("tipo_operacion", "error");
            model.addAttribute("mensaje", "Ocurrió un error inesperado al actualizar: " + e.getMessage());
            model.addAttribute("factura", factura); // Mantiene los datos del formulario si hay un error
        }

        return "Facturas/updatefactura"; // Vuelve a la misma página, mostrando los mensajes
    }
}
