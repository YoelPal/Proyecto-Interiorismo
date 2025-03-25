package dam.proyectointeriorismo.services;

import dam.proyectointeriorismo.models.dao.IEmpresaAsociadaEntityDAO;
import dam.proyectointeriorismo.models.entities.EmpresaAsociadaEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpresaAsocidadaService {
    private final IEmpresaAsociadaEntityDAO empresaAsociadaEntityDAO;

    public EmpresaAsocidadaService(IEmpresaAsociadaEntityDAO empresaAsociadaEntityDAO) {
        this.empresaAsociadaEntityDAO = empresaAsociadaEntityDAO;
    }

    public List<EmpresaAsociadaEntity> findAllEmpresaAsociada(){
        return (List<EmpresaAsociadaEntity>) empresaAsociadaEntityDAO.findAll();
    }

    public Optional<EmpresaAsociadaEntity> findEmpresaById(int id){
        return empresaAsociadaEntityDAO.findById(id);
    }

    public Optional<EmpresaAsociadaEntity> saveEmpresa(EmpresaAsociadaEntity empresaAsociada){
        return Optional.of(empresaAsociadaEntityDAO.save(empresaAsociada));
    }

    public Optional<EmpresaAsociadaEntity> deleteCliente(int id){
        Optional<EmpresaAsociadaEntity> empresaOpt = empresaAsociadaEntityDAO.findById(id);
        if (empresaAsociadaEntityDAO.existsById(id)){
            empresaAsociadaEntityDAO.deleteById(id);
            return Optional.of(empresaOpt.get());
        }
        return Optional.empty();

    }

    public Optional<EmpresaAsociadaEntity> updateCliente(EmpresaAsociadaEntity empresaAsociada,int id){
        Optional<EmpresaAsociadaEntity> empresaOpt = empresaAsociadaEntityDAO.findById(id);
        if (empresaOpt.isPresent()){
            empresaAsociada.setId(id);
            return Optional.of(empresaAsociadaEntityDAO.save(empresaAsociada));
        }
        return Optional.empty();
    }
}
