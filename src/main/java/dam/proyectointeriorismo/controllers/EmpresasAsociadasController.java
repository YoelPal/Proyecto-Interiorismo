package dam.proyectointeriorismo.controllers;

import dam.proyectointeriorismo.models.entities.EmpresaAsociadaEntity;
import dam.proyectointeriorismo.services.EmpresaAsocidadaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Optional;

@Controller
@RequestMapping("/empresasAsociadas")
public class EmpresasAsociadasController {
    private final EmpresaAsocidadaService empresaAsocidadaService;

    public EmpresasAsociadasController(EmpresaAsocidadaService empresaAsocidadaService) {
        this.empresaAsocidadaService = empresaAsocidadaService;
    }

    @PostMapping
    public String saveEmpresaAsociada(@ModelAttribute EmpresaAsociadaEntity empresaAsociada, Model model){
        try {
            Optional<EmpresaAsociadaEntity> newEmpresa = empresaAsocidadaService.saveEmpresa(empresaAsociada);
            if (newEmpresa.isPresent()) {
                model.addAttribute("tipo_operacion", "ok");
                model.addAttribute("mensaje", "Empresa creada correctamente.");
                model.addAttribute("empresa", newEmpresa.get()); // Pasa la empresa actualizada al modelo
            } else {
                model.addAttribute("tipo_operacion", "error");
                model.addAttribute("mensaje", "Error al crear la empresa .");
                model.addAttribute("empresa", empresaAsociada); // Mantiene los datos del formulario si hay un error
            }
        }catch (Exception e) {
            model.addAttribute("tipo_operacion", "error");
            model.addAttribute("mensaje", "Ocurrió un error inesperado al actualizar: " + e.getMessage());
            model.addAttribute("empresa", empresaAsociada); // Mantiene los datos del formulario si hay un error
        }

        return "Empresas/altaempresa";

    }

    @PostMapping("/deleteEmpresa")
    public String deleteEmpresa(@RequestParam("id")int id,RedirectAttributes redirectAttributes){
        Optional<EmpresaAsociadaEntity> empresaEliminada = empresaAsocidadaService.deleteEmpresa(id); // Asumiendo que deleteEmpresa devuelve Optional

        if (empresaEliminada.isPresent()) {
            redirectAttributes.addFlashAttribute("successMessage", "Empresa eliminada correctamente.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Error: No se encontró la empresa con ID " + id + " para eliminar.");
        }
        return "redirect:/verEmpresasAsociadas";
    }

    @PostMapping("/updateEmpresa")
    public String updateEmpresa(@ModelAttribute("empresa") EmpresaAsociadaEntity empresa, Model model) {

        try {
            Optional<EmpresaAsociadaEntity> empresaActualizada = empresaAsocidadaService.updateEmpresa(empresa);

            if (empresaActualizada.isPresent()) {
                model.addAttribute("tipo_operacion", "ok");
                model.addAttribute("mensaje", "Empresa actualizada correctamente.");
                model.addAttribute("empresa", empresaActualizada.get()); // Pasa la empresa actualizada al modelo
            } else {
                model.addAttribute("tipo_operacion", "error");
                model.addAttribute("mensaje", "Error al actualizar la empresa (ID no encontrado o problema en servicio).");
                model.addAttribute("empresa", empresa);
            }
        } catch (Exception e) {
            model.addAttribute("tipo_operacion", "error");
            model.addAttribute("mensaje", "Ocurrió un error inesperado al actualizar: " + e.getMessage());
            model.addAttribute("empresa", empresa);
        }

        return "Empresas/updateempresa";
    }

}
