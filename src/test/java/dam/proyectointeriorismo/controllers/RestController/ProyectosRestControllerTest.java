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
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import dam.proyectointeriorismo.models.entities.ProyectoEntity;
import dam.proyectointeriorismo.services.ProyectoService;

@WebMvcTest(controllers = ProyectosRestController.class)
@ExtendWith(MockitoExtension.class)
class ProyectosRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProyectoService proyectoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void buscarProyectosWithoutNameReturnsOk() throws Exception {
        ProyectoEntity p = new ProyectoEntity();
        p.setId(1);
        p.setNombre("Casa");

        when(proyectoService.listaProyectos()).thenReturn(List.of(p));

        mockMvc.perform(get("/proyectos/buscar"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(p))));
    }

    @Test
    void buscarProyectosEmptyReturnsNoContent() throws Exception {
        when(proyectoService.listaProyectos()).thenReturn(List.of());

        mockMvc.perform(get("/proyectos/buscar"))
                .andExpect(status().isNoContent());
    }

    @Test
    void buscarProyectosWithNameDelegatesToService() throws Exception {
        ProyectoEntity p = new ProyectoEntity();
        p.setId(2);
        p.setNombre("Casa2");

        when(proyectoService.findByNombreContainingIgnoreCase("Casa2")).thenReturn(List.of(p));

        mockMvc.perform(get("/proyectos/buscar").param("nombre","Casa2"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(p))));
    }

}
