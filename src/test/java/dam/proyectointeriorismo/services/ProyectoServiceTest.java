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

import dam.proyectointeriorismo.models.entities.ProyectoEntity;
import dam.proyectointeriorismo.models.repository.IProyectoEntityRepository;

@ExtendWith(MockitoExtension.class)
class ProyectoServiceTest {

    @Mock
    private IProyectoEntityRepository proyectoEntityRepository;

    @InjectMocks
    private ProyectoService proyectoService;

    @Test
    void listaProyectosTest() {
        ProyectoEntity p1 = new ProyectoEntity();
        p1.setId(1);
        p1.setNombre("Casa Verde");

        ProyectoEntity p2 = new ProyectoEntity();
        p2.setId(2);
        p2.setNombre("Apartamento Azul");

        when(proyectoEntityRepository.findAll()).thenReturn(List.of(p1, p2));

        List<ProyectoEntity> resultado = proyectoService.listaProyectos();

        verify(proyectoEntityRepository, times(1)).findAll();
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Casa Verde", resultado.get(0).getNombre());
    }

    @Test
    void findByIdExistsTest() {
        ProyectoEntity p = new ProyectoEntity();
        p.setId(10);
        p.setNombre("Proyecto X");

        when(proyectoEntityRepository.findById(10)).thenReturn(Optional.of(p));

        Optional<ProyectoEntity> resultado = proyectoService.findById(10);

        verify(proyectoEntityRepository, times(1)).findById(10);
        assertTrue(resultado.isPresent());
        assertEquals("Proyecto X", resultado.get().getNombre());
    }

    @Test
    void findByIdNotFoundTest() {
        when(proyectoEntityRepository.findById(999)).thenReturn(Optional.empty());

        Optional<ProyectoEntity> resultado = proyectoService.findById(999);

        verify(proyectoEntityRepository, times(1)).findById(999);
        assertFalse(resultado.isPresent());
    }

    @Test
    void saveProyectoTest() {
        ProyectoEntity p = new ProyectoEntity();
        p.setNombre("Nuevo");

        when(proyectoEntityRepository.save(p)).thenReturn(p);

        Optional<ProyectoEntity> resultado = proyectoService.saveProyecto(p);

        verify(proyectoEntityRepository, times(1)).save(p);
        assertTrue(resultado.isPresent());
        assertEquals("Nuevo", resultado.get().getNombre());
    }

    @Test
    void findByNombreContainingIgnoreCaseTest() {
        ProyectoEntity p = new ProyectoEntity();
        p.setNombre("Mi Casa");

        when(proyectoEntityRepository.findByNombreContainingIgnoreCase("Casa")).thenReturn(List.of(p));

        List<ProyectoEntity> resultado = proyectoService.findByNombreContainingIgnoreCase("Casa");

        verify(proyectoEntityRepository, times(1)).findByNombreContainingIgnoreCase("Casa");
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }

    @Test
    void deleteProyectoSuccessTest() {
        ProyectoEntity p = new ProyectoEntity();
        p.setId(5);

        when(proyectoEntityRepository.findById(5)).thenReturn(Optional.of(p));

        Optional<ProyectoEntity> resultado = proyectoService.deleteProyecto(5);

        verify(proyectoEntityRepository, times(1)).findById(5);
        verify(proyectoEntityRepository, times(1)).deleteById(5);
        assertTrue(resultado.isPresent());
    }

    @Test
    void deleteProyectoNotFoundTest() {
        when(proyectoEntityRepository.findById(4)).thenReturn(Optional.empty());

        Optional<ProyectoEntity> resultado = proyectoService.deleteProyecto(4);

        verify(proyectoEntityRepository, times(1)).findById(4);
        assertFalse(resultado.isPresent());
    }

    @Test
    void estadosTest() {
        when(proyectoEntityRepository.findAllEstados()).thenReturn(List.of("CONFIRMADO","TERMINADO"));

        List<String> resultado = proyectoService.estados();

        verify(proyectoEntityRepository, times(1)).findAllEstados();
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
    }

    @Test
    void updateProyectoSuccessTest() {
        ProyectoEntity old = new ProyectoEntity();
        old.setId(11);

        ProyectoEntity nuevo = new ProyectoEntity();
        nuevo.setNombre("Actualizado");

        when(proyectoEntityRepository.findById(11)).thenReturn(Optional.of(old));
        when(proyectoEntityRepository.save(nuevo)).thenReturn(nuevo);

        Optional<ProyectoEntity> resultado = proyectoService.updateProyecto(11, nuevo);

        verify(proyectoEntityRepository, times(1)).findById(11);
        verify(proyectoEntityRepository, times(1)).save(nuevo);
        assertTrue(resultado.isPresent());
    }

    @Test
    void updateProyectoNotFoundTest() {
        ProyectoEntity nuevo = new ProyectoEntity();

        when(proyectoEntityRepository.findById(99)).thenReturn(Optional.empty());

        Optional<ProyectoEntity> resultado = proyectoService.updateProyecto(99, nuevo);

        verify(proyectoEntityRepository, times(1)).findById(99);
        assertFalse(resultado.isPresent());
    }
}
