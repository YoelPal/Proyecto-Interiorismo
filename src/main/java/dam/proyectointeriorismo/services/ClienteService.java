package dam.proyectointeriorismo.services;

import dam.proyectointeriorismo.models.Enums.Estado;
import dam.proyectointeriorismo.models.repository.IClienteEntityRepository;
import dam.proyectointeriorismo.models.repository.IProyectoEntityRepository;
import dam.proyectointeriorismo.models.entities.ClienteEntity;

import dam.proyectointeriorismo.models.repository.specifications.ClienteSpecification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService implements IService {
    private final IClienteEntityRepository clienteEntityRepository;
    private final IProyectoEntityRepository proyectoEntityDAO;

    public ClienteService(IClienteEntityRepository clienteEntityRepository, IProyectoEntityRepository proyectoEntityDAO) {
        this.clienteEntityRepository = clienteEntityRepository;
        this.proyectoEntityDAO = proyectoEntityDAO;
    }

    @Override
    public List<ClienteEntity> buscarClientes(){
        return  clienteEntityRepository.findAll();
    }

    public Optional<ClienteEntity> findClienteById(int id){
        return clienteEntityRepository.findById(id);
    }

    @Override
    public Optional<ClienteEntity> saveCliente (ClienteEntity clienteEntity){
        if (clienteEntity.getProyecto()!=null && !proyectoEntityDAO.existsById(clienteEntity.getProyecto().getId())){
            throw new IllegalArgumentException("El proyecto introducido no se puede encontrar");
        }
        return Optional.of(clienteEntityRepository.save(clienteEntity)) ;
    }

    public Optional<ClienteEntity> deleteCliente(int id){
        Optional<ClienteEntity> optionalClienteEntity = clienteEntityRepository.findById(id);
        if (optionalClienteEntity.isPresent()){
            clienteEntityRepository.deleteById(id);
            return optionalClienteEntity;
        }
        return Optional.empty();
    }

    public List<ClienteEntity> findByNombreContainingIgnoreCase(String nombre) {
        return clienteEntityRepository.findByNombreContainingIgnoreCase(nombre);
    }


    @Override
    public Optional<ClienteEntity> updateCliente(ClienteEntity cliente) {
        Optional<ClienteEntity> clienteOpt = clienteEntityRepository.findById(cliente.getId());
        if (clienteOpt.isPresent()) {
            clienteEntityRepository.save(cliente);
            return Optional.of(cliente);
        }
        return Optional.empty();
    }

    @Override
    public List<ClienteEntity> findClienteByEstado(Estado estado) {
        return clienteEntityRepository.findClientesByEstado(estado);
    }

    public Optional<ClienteEntity> findByDni(String dni){
        Optional<ClienteEntity> optionalCliente = clienteEntityRepository.findByDni(dni);
        if (optionalCliente.isPresent()){
            return optionalCliente;
        }
        return Optional.empty();
    }


}
