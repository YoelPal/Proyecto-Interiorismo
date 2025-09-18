package dam.proyectointeriorismo.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "clientes")
public class ClienteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "dni", length = 30)
    private String dni;

    @Column (name ="nombre", length = 30)
    private String nombre;

    @Column(name = "edad")
    private Byte edad;

    @Column(name = "domicilio", length = 150)
    private String domicilio;

    @Column(name = "telefono", length = 30)
    private String telefono;

    @Column(name = "email", length = 30)
    private String email;

    @Column(name = "observaciones", length = 100)
    private String observaciones;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    @JoinColumn(name = "id_proyecto", referencedColumnName = "id")
    private ProyectoEntity proyecto;


}