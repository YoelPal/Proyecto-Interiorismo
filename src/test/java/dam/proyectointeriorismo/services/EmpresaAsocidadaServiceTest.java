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

import dam.proyectointeriorismo.models.entities.EmpresaAsociadaEntity;
import dam.proyectointeriorismo.models.repository.IEmpresaAsociadaEntityRepository;

@ExtendWith(MockitoExtension.class)
class EmpresaAsocidadaServiceTest {

    @Mock
    private IEmpresaAsociadaEntityRepository empresaRepository;

    @InjectMocks
    private EmpresaAsocidadaService empresaService;

    @Test
    void findByNombreContainingIgnoreCaseTest() {
        EmpresaAsociadaEntity e = new EmpresaAsociadaEntity();
        e.setId(1);
        e.setNombre("Muebles S.A.");

        when(empresaRepository.findByNombreContainingIgnoreCase("Muebles")).thenReturn(List.of(e));

        List<EmpresaAsociadaEntity> resultado = empresaService.findByNombreContainingIgnoreCase("Muebles");

        verify(empresaRepository, times(1)).findByNombreContainingIgnoreCase("Muebles");
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }

    @Test
    void findAllEmpresaAsociadaTest() {
        EmpresaAsociadaEntity e1 = new EmpresaAsociadaEntity();
        e1.setId(2);

        when(empresaRepository.findAll()).thenReturn(List.of(e1));

        List<EmpresaAsociadaEntity> resultado = empresaService.findAllEmpresaAsociada();

        verify(empresaRepository, times(1)).findAll();
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }

    @Test
    void findEmpresaByIdExistsTest() {
        EmpresaAsociadaEntity e = new EmpresaAsociadaEntity();
        e.setId(3);

        when(empresaRepository.findById(3)).thenReturn(Optional.of(e));

        Optional<EmpresaAsociadaEntity> resultado = empresaService.findEmpresaById(3);

        verify(empresaRepository, times(1)).findById(3);
        assertTrue(resultado.isPresent());
    }

    @Test
    void findEmpresaByIdNotFoundTest() {
        when(empresaRepository.findById(99)).thenReturn(Optional.empty());

        Optional<EmpresaAsociadaEntity> resultado = empresaService.findEmpresaById(99);

        verify(empresaRepository, times(1)).findById(99);
        assertFalse(resultado.isPresent());
    }

    @Test
    void saveEmpresaTest() {
        EmpresaAsociadaEntity e = new EmpresaAsociadaEntity();
        e.setNombre("Nueva");

        when(empresaRepository.save(e)).thenReturn(e);

        Optional<EmpresaAsociadaEntity> resultado = empresaService.saveEmpresa(e);

        verify(empresaRepository, times(1)).save(e);
        assertTrue(resultado.isPresent());
        assertEquals("Nueva", resultado.get().getNombre());
    }

    @Test
    void deleteEmpresaSuccessTest() {
        EmpresaAsociadaEntity e = new EmpresaAsociadaEntity();
        e.setId(21);

        when(empresaRepository.findById(21)).thenReturn(Optional.of(e));

        Optional<EmpresaAsociadaEntity> resultado = empresaService.deleteEmpresa(21);

        verify(empresaRepository, times(1)).findById(21);
        verify(empresaRepository, times(1)).deleteById(21);
        assertTrue(resultado.isPresent());
    }

    @Test
    void deleteEmpresaNotFoundTest() {
        when(empresaRepository.findById(22)).thenReturn(Optional.empty());

        Optional<EmpresaAsociadaEntity> resultado = empresaService.deleteEmpresa(22);

        verify(empresaRepository, times(1)).findById(22);
        assertFalse(resultado.isPresent());
    }

    @Test
    void updateEmpresaSuccessTest() {
        EmpresaAsociadaEntity existing = new EmpresaAsociadaEntity();
        existing.setId(40);

        EmpresaAsociadaEntity nuevo = new EmpresaAsociadaEntity();
        nuevo.setId(40);
        nuevo.setNombre("Actualizada");

        when(empresaRepository.findById(40)).thenReturn(Optional.of(existing));
        when(empresaRepository.save(nuevo)).thenReturn(nuevo);

        Optional<EmpresaAsociadaEntity> resultado = empresaService.updateEmpresa(nuevo);

        verify(empresaRepository, times(1)).findById(40);
        verify(empresaRepository, times(1)).save(nuevo);
        assertTrue(resultado.isPresent());
    }

    @Test
    void updateEmpresaNotFoundTest() {
        EmpresaAsociadaEntity nuevo = new EmpresaAsociadaEntity();
        nuevo.setId(99);

        when(empresaRepository.findById(99)).thenReturn(Optional.empty());

        Optional<EmpresaAsociadaEntity> resultado = empresaService.updateEmpresa(nuevo);

        verify(empresaRepository, times(1)).findById(99);
        assertFalse(resultado.isPresent());
    }
}
