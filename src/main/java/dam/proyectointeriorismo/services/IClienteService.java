package dam.proyectointeriorismo.services;

import dam.proyectointeriorismo.models.Enums.Estado;
import dam.proyectointeriorismo.models.entities.ClienteEntity;
import dam.proyectointeriorismo.models.entities.ProyectoEntity;

import java.util.List;
import java.util.Optional;

public interface IClienteService {

    public List<ClienteEntity> buscarClientes();
    public Optional<ClienteEntity> saveCliente(ClienteEntity clienteEntity);
    public Optional<ClienteEntity> updateCliente(ClienteEntity cliente);
    public List<ClienteEntity> findClienteByEstado(Estado estado);


}
