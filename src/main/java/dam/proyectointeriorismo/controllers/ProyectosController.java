package dam.proyectointeriorismo.controllers;

import dam.proyectointeriorismo.models.entities.ClienteEntity;
import dam.proyectointeriorismo.models.entities.ProyectoEntity;
import dam.proyectointeriorismo.services.ProyectoService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import java.util.Optional;

@Controller
public class ProyectosController {
    private final ProyectoService proyectoService;

    public ProyectosController( ProyectoService proyectoService) {
        this.proyectoService = proyectoService;
    }


    @GetMapping("/listaProyectos")
    public ResponseEntity<List<ProyectoEntity>> findAllProyectos(){

        return ResponseEntity.ok(proyectoService.listaProyectos()) ;
    }
    

    @PostMapping("/deleteproyecto")
    public String deleteProyecto(@RequestParam("id")int id, RedirectAttributes redirectAttributes){
        Optional<ProyectoEntity> deletedProyecto = proyectoService.deleteProyecto(id);
        if (deletedProyecto.isPresent()) {
            redirectAttributes.addFlashAttribute("successMessage", "Proyecto eliminado correctamente.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Error: No se encontró el proyecto con ID " + id + " para eliminar.");
        }
        return "redirect:/verProyectos";
    }


    @PostMapping("/saveproyecto")
    public String saveProyecto(@ModelAttribute ProyectoEntity proyecto, Model model){
        try {
            Optional<ProyectoEntity> newProyecto = proyectoService.saveProyecto(proyecto);
            if (newProyecto.isPresent()) {
                model.addAttribute("tipo_operacion", "ok");
                model.addAttribute("mensaje", "Proyecto creado correctamente.");
                model.addAttribute("proyecto", newProyecto.get());
            } else {
                model.addAttribute("tipo_operacion", "error");
                model.addAttribute("mensaje", "Error al crear el proyecto .");
                model.addAttribute("proyecto", proyecto);
            }
        }catch (Exception e) {
            model.addAttribute("tipo_operacion", "error");
            model.addAttribute("mensaje", "Ocurrió un error inesperado al actualizar: " + e.getMessage());
            model.addAttribute("proyecto", proyecto);
        }

        return "Proyectos/altaproyecto";

    }

    @PostMapping("/updateProyecto")
    public String updateProyecto(@ModelAttribute("proyecto") ProyectoEntity proyecto, Model model) {

        try {
            Optional<ProyectoEntity> optionalProyecto = proyectoService.updateProyecto(proyecto.getId(),proyecto);

            if (optionalProyecto.isPresent()) {
                model.addAttribute("tipo_operacion", "ok");
                model.addAttribute("mensaje", "Proyecto actualizada correctamente.");
                model.addAttribute("proyecto", optionalProyecto.get());
            } else {
                model.addAttribute("tipo_operacion", "error");
                model.addAttribute("mensaje", "Error al actualizar la empresa (ID no encontrado o problema en servicio).");
                model.addAttribute("proyecto", proyecto);
            }
        } catch (Exception e) {
            model.addAttribute("tipo_operacion", "error");
            model.addAttribute("mensaje", "Ocurrió un error inesperado al actualizar: " + e.getMessage());
            model.addAttribute("proyecto", proyecto);
        }
        return "Proyectos/updateproyecto";
    }



}
