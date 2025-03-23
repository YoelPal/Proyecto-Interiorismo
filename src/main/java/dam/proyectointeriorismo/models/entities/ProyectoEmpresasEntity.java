package dam.proyectointeriorismo.models.entities;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "proyectos_empresas")
public class ProyectoEmpresasEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    @JoinColumn(name = "id_proyecto", nullable = false)
    private ProyectoEntity idProyecto;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    @JoinColumn(name = "id_empresa_asociada", nullable = false)
    private EmpresaAsociadaEntity idEmpresaAsociada;

    @Column(name = "tipo_empresa", length = 50)
    private String tipoEmpresa;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ProyectoEntity getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(ProyectoEntity idProyecto) {
        this.idProyecto = idProyecto;
    }

    public EmpresaAsociadaEntity getIdEmpresaAsociada() {
        return idEmpresaAsociada;
    }

    public void setIdEmpresaAsociada(EmpresaAsociadaEntity idEmpresaAsociada) {
        this.idEmpresaAsociada = idEmpresaAsociada;
    }

    public String getTipoEmpresa() {
        return tipoEmpresa;
    }

    public void setTipoEmpresa(String tipoEmpresa) {
        this.tipoEmpresa = tipoEmpresa;
    }

}