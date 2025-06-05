package dam.proyectointeriorismo.models.repository.specifications;

import dam.proyectointeriorismo.models.entities.ClienteEntity;
import org.springframework.data.jpa.domain.Specification;

import java.util.Locale;

public class ClienteSpecification {

    public static Specification<ClienteEntity> contieneNombre(String nombre){
        return ((root, query, criteriaBuilder) -> {
            if (nombre == null || nombre.isBlank()){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("nombre")),"%" + nombre.toLowerCase() + "%");
        });

    }

    public static Specification<ClienteEntity> contieneDNI (String dni){
        return ((root, query, criteriaBuilder) -> {
            if (dni == null || dni.isBlank()){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("dni")),"%" + dni.toLowerCase() + "%");
        });
    }

    public static Specification<ClienteEntity> filtro(String nombre, String dni){
        return Specification.where(contieneNombre(nombre))
                .and(contieneDNI(dni));
    }
}
