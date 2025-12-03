package dam.proyectointeriorismo.controllers.RestController;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import dam.proyectointeriorismo.models.entities.EmpresaAsociadaEntity;
import dam.proyectointeriorismo.services.EmpresaAsocidadaService;

@WebMvcTest(controllers = EmpresasAsociadasRestController.class)
@ExtendWith(MockitoExtension.class)
class EmpresasAsociadasRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmpresaAsocidadaService empresaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void buscarEmpresasWithoutNameReturnsOk() throws Exception {
        EmpresaAsociadaEntity e = new EmpresaAsociadaEntity();
        e.setId(1);
        e.setNombre("Muebles");

        when(empresaService.findAllEmpresaAsociada()).thenReturn(List.of(e));

        mockMvc.perform(get("/empresas/buscar"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(e))));
    }

    @Test
    void buscarEmpresasEmptyReturnsNoContent() throws Exception {
        when(empresaService.findAllEmpresaAsociada()).thenReturn(List.of());

        mockMvc.perform(get("/empresas/buscar"))
                .andExpect(status().isNoContent());
    }

    @Test
    void buscarEmpresasWithNameDelegatesToService() throws Exception {
        EmpresaAsociadaEntity e = new EmpresaAsociadaEntity();
        e.setId(2);
        e.setNombre("Muebles2");

        when(empresaService.findByNombreContainingIgnoreCase("Muebles2")).thenReturn(List.of(e));

        mockMvc.perform(get("/empresas/buscar").param("nombre", "Muebles2"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(e))));
    }
}
