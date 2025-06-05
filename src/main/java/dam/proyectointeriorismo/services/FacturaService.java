package dam.proyectointeriorismo.services;

import dam.proyectointeriorismo.models.repository.IFacturaEntityRepository;
import dam.proyectointeriorismo.models.entities.FacturaEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FacturaService {
    private final IFacturaEntityRepository facturaRepository;

    public FacturaService(IFacturaEntityRepository facturaEntityDAO) {
        this.facturaRepository = facturaEntityDAO;
    }

    public List<FacturaEntity> findAllFacturas(){
        return (List<FacturaEntity>) facturaRepository.findAll();
    }

    public Optional<FacturaEntity> findFacturaById(int id){
        if (facturaRepository.existsById(id)){
            return facturaRepository.findById(id);
        }
        return Optional.empty();
    }

    public Optional<FacturaEntity> saveFactura(FacturaEntity facturaEntity){
        return Optional.of(facturaRepository.save(facturaEntity));
    }

    public Optional<FacturaEntity> deleteFactura(int id){
        Optional<FacturaEntity> facturaEntityOptional = facturaRepository.findById(id);
        if (facturaEntityOptional.isPresent()){
            facturaRepository.deleteById(id);
            return facturaEntityOptional;
        }
        return Optional.empty();
    }

    public Optional<FacturaEntity> updateFactura(FacturaEntity facturaEntity, int id){
        Optional<FacturaEntity> facturaUpdate = facturaRepository.findById(id);
        if (!facturaUpdate.isEmpty()){
            facturaEntity.setId(id);
            return Optional.of(facturaRepository.save(facturaEntity));
        }
        return Optional.empty();
    }
}
