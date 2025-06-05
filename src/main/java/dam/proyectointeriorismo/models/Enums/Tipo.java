package dam.proyectointeriorismo.models.Enums;

public enum Tipo {
    REFORMA_INTEGRAL("Reforma Integral"),
    REFORMA_OBRA_NUEVA("Reforma Obra Nueva"),
    PLANOS("Planos"),
    AMUEBLAMIENTO("Amueblamiento");

    private final String nombreVisible;

    Tipo(String nombreVisible) {
        this.nombreVisible = nombreVisible;
    }

    public String getNombreVisible() {
        return nombreVisible;
    }
}
