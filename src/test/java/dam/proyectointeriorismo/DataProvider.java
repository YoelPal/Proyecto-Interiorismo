package dam.proyectointeriorismo;

import java.util.Arrays;
import java.util.List;

import dam.proyectointeriorismo.models.entities.ClienteEntity;

public class DataProvider {

    public static List<ClienteEntity> getListaClientes() {
        // Asumiendo que ClienteEntity tiene un constructor o setters accesibles
        ClienteEntity cliente1 = new ClienteEntity();
        cliente1.setId(1);
        cliente1.setDni("11111111A");
        cliente1.setNombre("Ana García");
        cliente1.setEmail("ana.g@test.com");
        
        ClienteEntity cliente2 = new ClienteEntity();
        cliente2.setId(2);
        cliente2.setDni("22222222B");
        cliente2.setNombre("Luis Pérez");
        cliente2.setEmail("luis.p@test.com");
        
        return Arrays.asList(cliente1, cliente2);
    }
}
