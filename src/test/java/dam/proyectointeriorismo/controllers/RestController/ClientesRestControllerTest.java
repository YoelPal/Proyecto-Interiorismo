package dam.proyectointeriorismo.controllers.RestController;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import dam.proyectointeriorismo.models.entities.ClienteEntity;
import dam.proyectointeriorismo.services.ClienteService;

@WebMvcTest(controllers = ClientesRestController.class)
@ExtendWith(MockitoExtension.class)
class ClientesRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void buscarClientesWithoutNameReturnsOk() throws Exception {
        ClienteEntity c = new ClienteEntity();
        c.setId(1);
        c.setNombre("Ana");

        when(clienteService.buscarClientes()).thenReturn(List.of(c));

        mockMvc.perform(get("/clientes/buscar"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(c))));
    }

    @Test
    void buscarClientesEmptyReturnsNoContent() throws Exception {
        when(clienteService.buscarClientes()).thenReturn(List.of());

        mockMvc.perform(get("/clientes/buscar"))
                .andExpect(status().isNoContent());
    }

    @Test
    void buscarClientesWithNameDelegatesToService() throws Exception {
        ClienteEntity c = new ClienteEntity();
        c.setId(2);
        c.setNombre("Luis");

        when(clienteService.findByNombreContainingIgnoreCase("Luis")).thenReturn(List.of(c));

        mockMvc.perform(get("/clientes/buscar").param("nombre", "Luis"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(c))));
    }
}
