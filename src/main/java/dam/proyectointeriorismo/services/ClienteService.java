package dam.proyectointeriorismo.services;

import dam.proyectointeriorismo.models.dao.IClienteEntityDAO;
import dam.proyectointeriorismo.models.dao.IProyectoEntityDAO;
import dam.proyectointeriorismo.models.entities.ClienteEntity;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    private final IClienteEntityDAO clienteEntityDAO;
    private final IProyectoEntityDAO proyectoEntityDAO;

    public ClienteService(IClienteEntityDAO clienteEntityDAO, IProyectoEntityDAO proyectoEntityDAO) {
        this.clienteEntityDAO = clienteEntityDAO;
        this.proyectoEntityDAO = proyectoEntityDAO;
    }

    public List<ClienteEntity> buscarClientes(){
        return (List<ClienteEntity>) clienteEntityDAO.findAll();
    }

    public Optional<ClienteEntity> findClienteById(int id){
        return clienteEntityDAO.findById(id);
    }

    public ClienteEntity saveCliente (ClienteEntity clienteEntity){
        if (clienteEntity.getProyecto()!=null && !proyectoEntityDAO.existsById(clienteEntity.getProyecto().getId())){
            throw new IllegalArgumentException("El proyecto introducido no se puede encontrar");
        }
        return clienteEntityDAO.save(clienteEntity);
    }

    public boolean deleteCliente(int id){
        if (clienteEntityDAO.existsById(id)){
            clienteEntityDAO.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<ClienteEntity> updateCliente(ClienteEntity cliente, int id) {
        Optional<ClienteEntity> clienteOpt = clienteEntityDAO.findById(id);
        if (clienteOpt.isPresent()) {
            cliente.setId(id);
            clienteEntityDAO.save(cliente);
            return Optional.of(cliente);
        }
        return Optional.empty();
    }


}
