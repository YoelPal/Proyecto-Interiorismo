package dam.proyectointeriorismo.controllers;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import dam.proyectointeriorismo.models.entities.ProyectoEntity;
import dam.proyectointeriorismo.services.ClienteService;
import dam.proyectointeriorismo.services.ProyectoService;

@WebMvcTest(controllers = ProyectosController.class)
@ExtendWith(MockitoExtension.class)
class ProyectosControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProyectoService proyectoService;
    

    @Test
    void deleteProyecto_successRedirectsWithFlash() throws Exception {
        ProyectoEntity p = new ProyectoEntity();
        p.setId(1);

        when(proyectoService.deleteProyecto(1)).thenReturn(Optional.of(p));

        mockMvc.perform(post("/deleteproyecto").param("id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/verProyectos"));
    }

    @Test
    void saveProyecto_successReturnsViewAndModel() throws Exception {
        ProyectoEntity p = new ProyectoEntity();
        p.setId(2);
        p.setNombre("Nuevo");

        when(proyectoService.saveProyecto(org.mockito.ArgumentMatchers.any(ProyectoEntity.class))).thenReturn(Optional.of(p));

        mockMvc.perform(post("/saveproyecto").param("nombre", "Nuevo"))
                .andExpect(status().isOk())
                .andExpect(view().name("Proyectos/altaproyecto"))
                .andExpect(model().attributeExists("tipo_operacion"))
                .andExpect(model().attributeExists("mensaje"))
                .andExpect(model().attributeExists("proyecto"));
    }

    @Test
    void updateProyecto_notFoundReturnsErrorModel() throws Exception {
        ProyectoEntity p = new ProyectoEntity();
        p.setId(99);
        p.setNombre("NoExiste");

        when(proyectoService.updateProyecto(99,p)).thenReturn(Optional.empty());

        mockMvc.perform(post("/updateProyecto").param("id","99").param("nombre","NoExiste"))
                .andExpect(status().isOk())
                .andExpect(view().name("Proyectos/updateproyecto"))
                .andExpect(model().attributeExists("tipo_operacion"))
                .andExpect(model().attributeExists("mensaje"))
                .andExpect(model().attributeExists("proyecto"));
    }
}
