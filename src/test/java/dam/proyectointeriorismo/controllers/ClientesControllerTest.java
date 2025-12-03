package dam.proyectointeriorismo.controllers;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import dam.proyectointeriorismo.models.entities.ClienteEntity;
import dam.proyectointeriorismo.services.ClienteService;

@WebMvcTest(controllers = ClientesController.class)
@ExtendWith(MockitoExtension.class)
class ClientesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ClienteService clienteService;

    @Autowired
    private ObjectMapper objectMapper;

    

    @Test
    void saveCliente_existingDniRedirectsBackWithError() throws Exception {
        ClienteEntity incoming = new ClienteEntity();
        incoming.setDni("11111111A");
        incoming.setNombre("Ana");

        when(clienteService.findByDni("11111111A")).thenReturn(Optional.of(incoming));

        mockMvc.perform(post("/altacliente")
                        .param("dni", "11111111A")
                        .param("nombre", "Ana"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/altacliente"))
                .andExpect(flash().attributeExists("mensaje"))
                .andExpect(flash().attributeExists("tipo_operacion"));

        verify(clienteService, times(1)).findByDni("11111111A");
    }

    @Test
    void saveCliente_successRedirectsToVerClientes() throws Exception {
        ClienteEntity incoming = new ClienteEntity();
        incoming.setDni("22222222B");
        incoming.setNombre("Luis");

        when(clienteService.findByDni("22222222B")).thenReturn(Optional.empty());
        when(clienteService.saveCliente(org.mockito.ArgumentMatchers.any(ClienteEntity.class))).thenReturn(Optional.of(incoming));

        mockMvc.perform(post("/altacliente")
                        .param("dni", "22222222B")
                        .param("nombre", "Luis"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/verClientes"))
                .andExpect(flash().attributeExists("mensaje"))
                .andExpect(flash().attributeExists("tipo_operacion"));
    }

    @Test
    void actualizarCliente_presentReturnsViewWithOk() throws Exception {
        ClienteEntity cliente = new ClienteEntity();
        cliente.setId(1);
        cliente.setNombre("Ana");

        when(clienteService.updateCliente(org.mockito.ArgumentMatchers.any(ClienteEntity.class))).thenReturn(Optional.of(cliente));

        mockMvc.perform(post("/actualizarcliente")
                        .param("id", "1")
                        .param("nombre", "Ana"))
                .andExpect(status().isOk())
                .andExpect(view().name("Clientes/updatecliente"))
                .andExpect(model().attributeExists("tipo_operacion"))
                .andExpect(model().attributeExists("mensaje"))
                .andExpect(model().attributeExists("cliente"));
    }

    @Test
    void deleteCliente_presentRedirectsToVerClientesWithFlash() throws Exception {
        ClienteEntity c = new ClienteEntity();
        c.setId(1);

        when(clienteService.deleteCliente(1)).thenReturn(Optional.of(c));

        mockMvc.perform(post("/deleteCliente").param("id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/verClientes"))
                .andExpect(flash().attributeExists("successMessage"));
    }

    @Test
    void deleteCliente_notFoundRedirectsToVerClientesWithErrorFlash() throws Exception {
        when(clienteService.deleteCliente(5)).thenReturn(Optional.empty());

        mockMvc.perform(post("/deleteCliente").param("id", "5"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/verClientes"))
                .andExpect(flash().attributeExists("errorMessage"));
    }
}
