package dam.proyectointeriorismo.controllers;

import dam.proyectointeriorismo.models.entities.EmpresaAsociadaEntity;
import dam.proyectointeriorismo.services.EmpresaAsocidadaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import java.util.Optional;

@Controller
public class EmpresasAsociadasWebController {

    private final EmpresaAsocidadaService empresaAsocidadaService;

    public EmpresasAsociadasWebController(EmpresaAsocidadaService empresaAsocidadaService) {
        this.empresaAsocidadaService = empresaAsocidadaService;
    }

    @GetMapping("/verEmpresasAsociadas")
    public String mostrarEmpresasAsociadas(Model model){
        List<EmpresaAsociadaEntity> empresasAsociadas = empresaAsocidadaService.findAllEmpresaAsociada();
        model.addAttribute("empresasAsociadas", empresasAsociadas);
        return "Empresas/verEmpresasAsociadas";
    }

    @GetMapping("/altaempresa")
    public String altaEmpresa(Model model) {
        model.addAttribute("empresa", new EmpresaAsociadaEntity());
        return "Empresas/altaempresa";
    }

    @GetMapping("/actualizarEmpresa")
    public String updateEmpresa(@RequestParam("id") int id, Model model) {
        Optional<EmpresaAsociadaEntity> optionalEmpresa = empresaAsocidadaService.findEmpresaById(id);
        if (optionalEmpresa.isPresent()){
            EmpresaAsociadaEntity empresa = optionalEmpresa.get();
            model.addAttribute("empresa", empresa);
        }else {
            model.addAttribute("tipo_operacion", "error");
            model.addAttribute("mensaje", "Empresa no encontrado.");
            model.addAttribute("empresa", new EmpresaAsociadaEntity());
        }
        return "Empresas/updateempresa";
    }

}
