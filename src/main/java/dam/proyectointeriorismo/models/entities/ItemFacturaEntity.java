package dam.proyectointeriorismo.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "items_factura")
public class ItemFacturaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer cantidad;

    @Column(name = "importe", precision = 10, scale = 2)
    private BigDecimal importe;

    // Relación ManyToOne con Factura
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "factura_id")
    private FacturaEntity factura;

    // Relación ManyToOne con Concepto
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concepto_id")
    private ConceptoEntity concepto;

    // Método para calcular el importe
    public BigDecimal getImporte() {
        if (this.concepto != null && this.cantidad != null) {
            return this.concepto.getPrecio().multiply(new BigDecimal(this.cantidad));
        }
        return BigDecimal.ZERO;
    }

}
