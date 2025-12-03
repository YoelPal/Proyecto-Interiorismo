package dam.proyectointeriorismo.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import dam.proyectointeriorismo.models.entities.ClienteEntity;
import dam.proyectointeriorismo.models.entities.FacturaEntity;
import dam.proyectointeriorismo.models.repository.IFacturaEntityRepository;

@ExtendWith(MockitoExtension.class)
class FacturaServiceTest {

    @Mock
    private IFacturaEntityRepository facturaRepository;

    @InjectMocks
    private FacturaService facturaService;

    @Test
    void findAllFacturasTest() {
        FacturaEntity f1 = new FacturaEntity();
        f1.setId(1);

        when(facturaRepository.findAll()).thenReturn(List.of(f1));

        List<FacturaEntity> resultado = facturaService.findAllFacturas();

        verify(facturaRepository, times(1)).findAll();
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }

    @Test
    void findFacturaByIdExistsTest() {
        FacturaEntity f = new FacturaEntity();
        f.setId(2);

        when(facturaRepository.existsById(2)).thenReturn(true);
        when(facturaRepository.findById(2)).thenReturn(Optional.of(f));

        Optional<FacturaEntity> resultado = facturaService.findFacturaById(2);

        verify(facturaRepository, times(1)).existsById(2);
        verify(facturaRepository, times(1)).findById(2);
        assertTrue(resultado.isPresent());
        assertEquals(2, resultado.get().getId());
    }

    @Test
    void findFacturaByIdNotExistsTest() {
        when(facturaRepository.existsById(99)).thenReturn(false);

        Optional<FacturaEntity> resultado = facturaService.findFacturaById(99);

        verify(facturaRepository, times(1)).existsById(99);
        assertFalse(resultado.isPresent());
    }

    @Test
    void saveFacturaTest() {
        FacturaEntity f = new FacturaEntity();
        f.setId(3);

        when(facturaRepository.save(f)).thenReturn(f);

        Optional<FacturaEntity> resultado = facturaService.saveFactura(f);

        verify(facturaRepository, times(1)).save(f);
        assertTrue(resultado.isPresent());
        assertEquals(3, resultado.get().getId());
    }

    @Test
    void deleteFacturaSuccessTest() {
        FacturaEntity f = new FacturaEntity();
        f.setId(7);

        when(facturaRepository.findById(7)).thenReturn(Optional.of(f));

        Optional<FacturaEntity> resultado = facturaService.deleteFactura(7);

        verify(facturaRepository, times(1)).findById(7);
        verify(facturaRepository, times(1)).deleteById(7);
        assertTrue(resultado.isPresent());
    }

    @Test
    void deleteFacturaNotFoundTest() {
        when(facturaRepository.findById(8)).thenReturn(Optional.empty());

        Optional<FacturaEntity> resultado = facturaService.deleteFactura(8);

        verify(facturaRepository, times(1)).findById(8);
        verify(facturaRepository, times(0)).deleteById(8);
        assertFalse(resultado.isPresent());
    }

    @Test
    void updateFacturaSuccessTest() {
        FacturaEntity existing = new FacturaEntity();
        existing.setId(20);

        FacturaEntity nuevo = new FacturaEntity();
        nuevo.setId(20);

        when(facturaRepository.findById(20)).thenReturn(Optional.of(existing));
        when(facturaRepository.save(nuevo)).thenReturn(nuevo);

        Optional<FacturaEntity> resultado = facturaService.updateFactura(nuevo,20);

        verify(facturaRepository, times(1)).findById(20);
        verify(facturaRepository, times(1)).save(nuevo);
        assertTrue(resultado.isPresent());
        assertEquals(20, resultado.get().getId());
    }

    @Test
    void updateFacturaNotFoundTest() {
        FacturaEntity nuevo = new FacturaEntity();

        when(facturaRepository.findById(77)).thenReturn(Optional.empty());

        Optional<FacturaEntity> resultado = facturaService.updateFactura(nuevo,77);

        verify(facturaRepository, times(1)).findById(77);
        assertFalse(resultado.isPresent());
    }

    @Test
    void findFacturasByClienteTest() {
        ClienteEntity cliente = new ClienteEntity();
        cliente.setId(1);

        FacturaEntity f1 = new FacturaEntity();
        f1.setId(100);

        Pageable pageable = PageRequest.of(0, 10);
        Page<FacturaEntity> page = new PageImpl<>(List.of(f1));

        when(facturaRepository.findByCliente(cliente, pageable)).thenReturn(page);

        Page<FacturaEntity> resultado = facturaService.findFacturasByCliente(cliente, pageable);

        verify(facturaRepository, times(1)).findByCliente(cliente,pageable);
        assertNotNull(resultado);
        assertEquals(1, resultado.getContent().size());
    }
}
