package dam.proyectointeriorismo.controllers;

import dam.proyectointeriorismo.models.entities.FacturaEntity;
import dam.proyectointeriorismo.services.FacturaService;
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
                model.addAttribute("factura", newFactura.get());
            } else {
                model.addAttribute("tipo_operacion", "error");
                model.addAttribute("mensaje", "Error al crear la factura .");
                model.addAttribute("factura", facturaEntity);
            }
        }catch (Exception e) {
            // Captura cualquier otra excepción durante la actualización
            model.addAttribute("tipo_operacion", "error");
            model.addAttribute("mensaje", "Ocurrió un error inesperado al actualizar: " + e.getMessage());
            model.addAttribute("factura", facturaEntity);
        }
        return "Facturas/altafactura";
    }

    @PostMapping("/updateFactura")
    public String updateFactura(@ModelAttribute("factura") FacturaEntity factura, Model model) {

        try {
            Optional<FacturaEntity> facturaActualizada = facturaService.updateFactura(factura, factura.getId());
            if (facturaActualizada.isPresent()) {
                model.addAttribute("tipo_operacion", "ok");
                model.addAttribute("mensaje", "Factura actualizada correctamente.");
                model.addAttribute("factura", facturaActualizada.get());
            } else {
                model.addAttribute("tipo_operacion", "error");
                model.addAttribute("mensaje", "Error al actualizar la factura (ID no encontrado o problema en servicio).");
                model.addAttribute("factura", factura);
            }
        } catch (Exception e) {
            model.addAttribute("tipo_operacion", "error");
            model.addAttribute("mensaje", "Ocurrió un error inesperado al actualizar: " + e.getMessage());
            model.addAttribute("factura", factura);
        }
        return "Facturas/updatefactura";
    }
}
