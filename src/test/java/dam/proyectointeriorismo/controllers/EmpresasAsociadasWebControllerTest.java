package dam.proyectointeriorismo.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import dam.proyectointeriorismo.models.entities.EmpresaAsociadaEntity;
import dam.proyectointeriorismo.services.EmpresaAsocidadaService;

@WebMvcTest(controllers = EmpresasAsociadasWebController.class)
@ExtendWith(MockitoExtension.class)
class EmpresasAsociadasWebControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmpresaAsocidadaService empresaService;

    @Test
    void mostrarEmpresasAsociadasAddsModelAndReturnsView() throws Exception {
        EmpresaAsociadaEntity e = new EmpresaAsociadaEntity();
        e.setId(1);

        when(empresaService.findAllEmpresaAsociada()).thenReturn(List.of(e));

        mockMvc.perform(get("/verEmpresasAsociadas"))
                .andExpect(status().isOk())
                .andExpect(view().name("Empresas/verEmpresasAsociadas"))
                .andExpect(model().attributeExists("empresasAsociadas"));
    }

    @Test
    void updateEmpresaIdNotFoundAddsErrorModel() throws Exception {
        when(empresaService.findEmpresaById(99)).thenReturn(Optional.empty());

        mockMvc.perform(get("/actualizarEmpresa").param("id", "99"))
                .andExpect(status().isOk())
                .andExpect(view().name("Empresas/updateempresa"))
                .andExpect(model().attributeExists("tipo_operacion"))
                .andExpect(model().attributeExists("mensaje"))
                .andExpect(model().attributeExists("empresa"));
    }
}
