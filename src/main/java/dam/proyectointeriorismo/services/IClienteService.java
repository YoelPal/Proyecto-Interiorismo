package dam.proyectointeriorismo.services;

import dam.proyectointeriorismo.models.dao.IClienteEntityDAO;
import dam.proyectointeriorismo.models.entities.ClienteEntity;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface IClienteService {

    public List<ClienteEntity> buscarClientes();
    public Optional<ClienteEntity> saveCliente(ClienteEntity clienteEntity);
    public Optional<ClienteEntity> updateCliente(ClienteEntity cliente);
}
