package dam.proyectointeriorismo.models.Enums;

public enum EstadoFactura {
    PAGADA("Pagada"),
    PENDIENTE_DE_ENVIO("Pendiente de envío"),
    EMITIDA("Emitida"),
    PENDIENTE_DE_PAGO("Pendiente de pago"),
    ANULADA("Anulada"),
    VENCIDA("Vencida");


    private final String nombreVisible;


    EstadoFactura(String nombreVisible) {
        this.nombreVisible = nombreVisible;
    }

    public String getNombreVisible() {
        return nombreVisible;
    }
}
