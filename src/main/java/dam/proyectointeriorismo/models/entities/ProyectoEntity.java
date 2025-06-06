package dam.proyectointeriorismo.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dam.proyectointeriorismo.models.Enums.Estado;
import dam.proyectointeriorismo.models.Enums.Tipo;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "proyectos")
public class ProyectoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "presupuesto")
    private Integer presupuesto;

    @Column(name = "fecha_inicio")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fechaInicio;

    @Column(name = "metros_cuadrados")
    private Double metrosCuadrados;

    @Column(name = "tipo")
    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    @Column(name ="estado")
    @Enumerated(EnumType.STRING) // Esto es importante para que JPA lo guarde como String en la BBDD
    private Estado estado;

    @Column(name = "nombre")
    private String nombre;

    @OneToMany(mappedBy = "proyecto")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JsonIgnore
    private List<ClienteEntity> clientes;


    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(Integer presupuesto) {
        this.presupuesto = presupuesto;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public List<ClienteEntity> getClientes() {
        return clientes;
    }

    public void setClientes(List<ClienteEntity> clientes) {
        this.clientes = clientes;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Double getMetrosCuadrados() {
        return metrosCuadrados;
    }

    public void setMetrosCuadrados(Double metrosCuadrados) {
        this.metrosCuadrados = metrosCuadrados;
    }
}
