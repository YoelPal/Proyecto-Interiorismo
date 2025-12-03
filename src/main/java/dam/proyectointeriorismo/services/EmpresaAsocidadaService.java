package dam.proyectointeriorismo.services;

import dam.proyectointeriorismo.models.repository.IEmpresaAsociadaEntityRepository;
import dam.proyectointeriorismo.models.entities.EmpresaAsociadaEntity;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpresaAsocidadaService {
    private final IEmpresaAsociadaEntityRepository empresaAsociadaEntityRepository;

    public EmpresaAsocidadaService(IEmpresaAsociadaEntityRepository empresaAsociadaEntityDAO) {
        this.empresaAsociadaEntityRepository = empresaAsociadaEntityDAO;
    }


    public List<EmpresaAsociadaEntity> findByNombreContainingIgnoreCase(String nombre) {
        return empresaAsociadaEntityRepository.findByNombreContainingIgnoreCase(nombre);
    }
    public List<EmpresaAsociadaEntity> findAllEmpresaAsociada(){
        return (List<EmpresaAsociadaEntity>) empresaAsociadaEntityRepository.findAll();
    }

    public Optional<EmpresaAsociadaEntity> findEmpresaById(int id){
        return empresaAsociadaEntityRepository.findById(id);
    }

    public Optional<EmpresaAsociadaEntity> saveEmpresa(@NonNull EmpresaAsociadaEntity empresaAsociada){
        return Optional.of(empresaAsociadaEntityRepository.save(empresaAsociada));
    }

    public Optional<EmpresaAsociadaEntity> deleteEmpresa(int id){
        Optional<EmpresaAsociadaEntity> empresaOpt = empresaAsociadaEntityRepository.findById(id);
        if (empresaOpt.isPresent()){
            empresaAsociadaEntityRepository.deleteById(id);
            return empresaOpt;
        }
        return Optional.empty();

    }

    public Optional<EmpresaAsociadaEntity> updateEmpresa(@NonNull EmpresaAsociadaEntity empresaAsociada){
        Optional<EmpresaAsociadaEntity> empresaOpt = empresaAsociadaEntityRepository.findById(empresaAsociada.getId());
        if (empresaOpt.isPresent()){
            empresaAsociada.setId(empresaAsociada.getId());
            return Optional.of(empresaAsociadaEntityRepository.save(empresaAsociada));
        }
        return Optional.empty();
    }
}
