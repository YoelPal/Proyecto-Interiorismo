package dam.proyectointeriorismo.services;

import dam.proyectointeriorismo.models.dao.IFacturaEntityDAO;
import dam.proyectointeriorismo.models.entities.FacturaEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FacturaService {
    private final IFacturaEntityDAO facturaEntityDAO;

    public FacturaService(IFacturaEntityDAO facturaEntityDAO) {
        this.facturaEntityDAO = facturaEntityDAO;
    }

    public List<FacturaEntity> findAllFacturas(){
        return (List<FacturaEntity>) facturaEntityDAO.findAll();
    }

    public Optional<FacturaEntity> findFacturaById(int id){
        if (facturaEntityDAO.existsById(id)){
            return facturaEntityDAO.findById(id);
        }
        return Optional.empty();
    }

    public Optional<FacturaEntity> saveFactura(FacturaEntity facturaEntity){
        return Optional.of(facturaEntityDAO.save(facturaEntity));
    }

    public boolean deleteFactura(int id){
        if (facturaEntityDAO.existsById(id)){
            facturaEntityDAO.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<FacturaEntity> updateFactura(FacturaEntity facturaEntity, int id){
        Optional<FacturaEntity> facturaUpdate = facturaEntityDAO.findById(id);
        if (!facturaUpdate.isEmpty()){
            facturaEntity.setId(id);
            return Optional.of(facturaEntityDAO.save(facturaEntity));
        }
        return Optional.empty();
    }
}
