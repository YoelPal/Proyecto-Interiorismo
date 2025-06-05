package dam.proyectointeriorismo.models.Enums;

public enum Estado {
    ESPERANDO_CONFIRMACION("Esperando Confirmación"), // Cambiado a mayúsculas por convención y añadido nombre visible
    CONFIRMADO("Confirmado"),
    TERMINADO("Terminado"),
    CANCELADO("Cancelado");

    private final String nombreVisible;

    Estado(String nombreVisible) {
        this.nombreVisible = nombreVisible;
    }

    public String getNombreVisible() {
        return nombreVisible;
    }
}
