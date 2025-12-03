package dam.proyectointeriorismo.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import dam.proyectointeriorismo.DataProvider;
import dam.proyectointeriorismo.models.entities.ClienteEntity;
import dam.proyectointeriorismo.models.entities.ProyectoEntity;
import dam.proyectointeriorismo.models.Enums.Estado;
import dam.proyectointeriorismo.models.repository.IClienteEntityRepository;
import dam.proyectointeriorismo.models.repository.IProyectoEntityRepository;


@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private IClienteEntityRepository clienteEntityRepository;

    @Mock
    private IProyectoEntityRepository proyectoEntityRepository;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    void buscarClientesTest() {

        List<ClienteEntity> clientes = DataProvider.getListaClientes();

        when(clienteEntityRepository.findAll()).thenReturn(clientes);

        List<ClienteEntity> resultado = clienteService.buscarClientes();
        
        verify(clienteEntityRepository,times(1)).findAll();
        assertNotNull(resultado);
        assertEquals(resultado.size(), clientes.size(), "La cantidad de clientes debe coincidir.");
        assertEquals("Ana García", clientes.get(0).getNombre(), "El nombre del primer cliente debe coincidir.");
    }

    @Test
    void findClienteByIdExistsTest() {
        ClienteEntity cliente = DataProvider.getListaClientes().get(0);

        when(clienteEntityRepository.findById(1)).thenReturn(Optional.of(cliente));

        Optional<ClienteEntity> resultado = clienteService.findClienteById(1);

        verify(clienteEntityRepository, times(1)).findById(1);
        assertTrue(resultado.isPresent());
        assertEquals("Ana García", resultado.get().getNombre());
    }

    @Test
    void findClienteByIdNotFoundTest() {
        when(clienteEntityRepository.findById(99)).thenReturn(Optional.empty());

        Optional<ClienteEntity> resultado = clienteService.findClienteById(99);

        verify(clienteEntityRepository, times(1)).findById(99);
        assertFalse(resultado.isPresent());
    }

    @Test
    void saveClienteSuccessTest() {
        ClienteEntity cliente = DataProvider.getListaClientes().get(0);
        ProyectoEntity proyecto = new ProyectoEntity();
        proyecto.setId(10);
        cliente.setProyecto(proyecto);

        when(proyectoEntityRepository.existsById(10)).thenReturn(true);
        when(clienteEntityRepository.save(cliente)).thenReturn(cliente);

        Optional<ClienteEntity> resultado = clienteService.saveCliente(cliente);

        verify(proyectoEntityRepository, times(1)).existsById(10);
        verify(clienteEntityRepository, times(1)).save(cliente);
        assertTrue(resultado.isPresent());
        assertEquals(cliente.getDni(), resultado.get().getDni());
    }

    @Test
    void saveClienteProjectMissingThrows() {
        ClienteEntity cliente = DataProvider.getListaClientes().get(0);
        ProyectoEntity proyecto = new ProyectoEntity();
        proyecto.setId(999);
        cliente.setProyecto(proyecto);

        when(proyectoEntityRepository.existsById(999)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> clienteService.saveCliente(cliente));

        verify(proyectoEntityRepository, times(1)).existsById(999);
    }

    @Test
    void saveClienteProjectNullThrows() {
        ClienteEntity cliente = DataProvider.getListaClientes().get(0);
        ProyectoEntity proyecto = new ProyectoEntity();
        proyecto.setId(999);
        cliente.setProyecto(null);

        when(clienteEntityRepository.save(cliente)).thenReturn(cliente);

        Optional<ClienteEntity> resultado = clienteService.saveCliente(cliente);
        assertTrue(resultado.isPresent());
        assertEquals(cliente.getDni(), resultado.get().getDni());
        verify(clienteEntityRepository, times(1)).save(cliente);
        verify(proyectoEntityRepository, times(0)).existsById(anyInt());
    }


    @Test
    void deleteClienteSuccessTest() {
        ClienteEntity cliente = DataProvider.getListaClientes().get(0);

        when(clienteEntityRepository.findById(1)).thenReturn(Optional.of(cliente));

        Optional<ClienteEntity> resultado = clienteService.deleteCliente(1);

        verify(clienteEntityRepository, times(1)).findById(1);
        verify(clienteEntityRepository, times(1)).deleteById(1);
        assertTrue(resultado.isPresent());
        assertEquals(cliente.getNombre(), resultado.get().getNombre());
    }

    @Test
    void deleteClienteNotFoundTest() {
        when(clienteEntityRepository.findById(5)).thenReturn(Optional.empty());

        Optional<ClienteEntity> resultado = clienteService.deleteCliente(5);

        verify(clienteEntityRepository, times(1)).findById(5);
        verify(clienteEntityRepository, times(0)).deleteById(5);
        assertFalse(resultado.isPresent());
    }

    @Test
    void findByNombreContainingIgnoreCaseTest() {
        List<ClienteEntity> clientes = DataProvider.getListaClientes();

        when(clienteEntityRepository.findByNombreContainingIgnoreCase("Ana")).thenReturn(List.of(clientes.get(0)));

        List<ClienteEntity> resultado = clienteService.findByNombreContainingIgnoreCase("Ana");

        verify(clienteEntityRepository, times(1)).findByNombreContainingIgnoreCase("Ana");
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Ana García", resultado.get(0).getNombre());
    }

    @Test
    void updateClienteSuccessTest() {
        ClienteEntity existing = DataProvider.getListaClientes().get(0);
        existing.setId(1);

        ClienteEntity updated = new ClienteEntity();
        updated.setId(1);
        updated.setDni(existing.getDni());
        updated.setNombre("Ana Actualizada");

        when(clienteEntityRepository.findById(1)).thenReturn(Optional.of(existing));
        when(clienteEntityRepository.save(updated)).thenReturn(updated);

        Optional<ClienteEntity> resultado = clienteService.updateCliente(updated);

        verify(clienteEntityRepository, times(1)).findById(1);
        verify(clienteEntityRepository, times(1)).save(updated);
        assertTrue(resultado.isPresent());
        assertEquals("Ana Actualizada", resultado.get().getNombre());
    }

    @Test
    void updateClienteNotFoundTest() {
        ClienteEntity updated = new ClienteEntity();
        updated.setId(77);
        updated.setNombre("No Existe");

        when(clienteEntityRepository.findById(77)).thenReturn(Optional.empty());

        Optional<ClienteEntity> resultado = clienteService.updateCliente(updated);

        verify(clienteEntityRepository, times(1)).findById(77);
        verify(clienteEntityRepository, times(0)).save(updated);
        assertFalse(resultado.isPresent());
    }

    @Test
    void findClienteByEstadoTest() {
        ClienteEntity cliente = DataProvider.getListaClientes().get(0);
        ProyectoEntity proyecto = new ProyectoEntity();
        proyecto.setEstado(Estado.CONFIRMADO);
        cliente.setProyecto(proyecto);

        when(clienteEntityRepository.findClientesByEstado(Estado.CONFIRMADO)).thenReturn(List.of(cliente));

        List<ClienteEntity> resultado = clienteService.findClienteByEstado(Estado.CONFIRMADO);

        verify(clienteEntityRepository, times(1)).findClientesByEstado(Estado.CONFIRMADO);
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(Estado.CONFIRMADO, resultado.get(0).getProyecto().getEstado());
    }

    @Test
    void findByDniFoundTest() {
        ClienteEntity cliente = DataProvider.getListaClientes().get(0);

        when(clienteEntityRepository.findByDni("11111111A")).thenReturn(Optional.of(cliente));

        Optional<ClienteEntity> resultado = clienteService.findByDni("11111111A");

        verify(clienteEntityRepository, times(1)).findByDni("11111111A");
        assertTrue(resultado.isPresent());
        assertEquals("11111111A", resultado.get().getDni());
    }

    @Test
    void findByDniNotFoundTest() {
        when(clienteEntityRepository.findByDni("noexists")).thenReturn(Optional.empty());

        Optional<ClienteEntity> resultado = clienteService.findByDni("noexists");

        verify(clienteEntityRepository, times(1)).findByDni("noexists");
        assertFalse(resultado.isPresent());
    }

    

}
