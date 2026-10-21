package modelo;

/**
 * Clase que representa la entidad de una propiedad inmobiliaria.
 * Aplica sobrecarga de constructores para maxima compatibilidad en el sistema.
 */
public class Propiedad {
    // Atributos privados
    private String tipoPropiedad;
    private String sector;
    private double totalConstruido;
    private double precio;
    private String referencia;
    private int dormitorios;
    private int banos;
    private int antiguedad;
    private String estadoInspeccion;
    private double variableAdicional; // Atributo de soporte para el constructor antiguo de 10 parametros

    /**
     * Constructor 1: Vacio (Buenas practicas POO)
     */
    public Propiedad() {
    }

    /**
     * Constructor 2: Adaptado exactamente a las 9 variables del lector CSV actual
     */
    public Propiedad(String tipoPropiedad, String sector, double totalConstruido, double precio,
                     String referencia, int dormitorios, int banos, int antiguedad, String estadoInspeccion) {
        this.tipoPropiedad = tipoPropiedad;
        this.sector = sector;
        this.totalConstruido = totalConstruido;
        this.precio = precio;
        this.referencia = referencia;
        this.dormitorios = dormitorios;
        this.banos = banos;
        this.antiguedad = antiguedad;
        this.estadoInspeccion = estadoInspeccion;
    }

    /**
     * Constructor 3: Constructor antiguo de 10 parametros (Sobrecarga)
     * Requerido por compatibilidad con otras secciones previas del proyecto.
     */
    public Propiedad(String tipoPropiedad, String sector, double totalConstruido, double precio,
                     String referencia, int dormitorios, int banos, String estadoInspeccion,
                     double variableAdicional, int antiguedad) {
        this.tipoPropiedad = tipoPropiedad;
        this.sector = sector;
        this.totalConstruido = totalConstruido;
        this.precio = precio;
        this.referencia = referencia;
        this.dormitorios = dormitorios;
        this.banos = banos;
        this.estadoInspeccion = estadoInspeccion;
        this.variableAdicional = variableAdicional;
        this.antiguedad = antiguedad;
    }

    // --- METODOS GETTERS Y SETTERS ---

    public String getTipoPropiedad() {
        return tipoPropiedad;
    }

    public void setTipoPropiedad(String tipoPropiedad) {
        this.tipoPropiedad = tipoPropiedad;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public double getTotalConstruido() {
        return totalConstruido;
    }

    public void setTotalConstruido(double totalConstruido) {
        this.totalConstruido = totalConstruido;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public int getDormitorios() {
        return dormitorios;
    }

    public void setDormitorios(int dormitorios) {
        this.dormitorios = dormitorios;
    }

    public int getBanos() {
        return banos;
    }

    public void setBanos(int banos) {
        this.banos = banos;
    }

    public int getAntiguedad() {
        return antiguedad;
    }

    public void setAntiguedad(int antiguedad) {
        this.antiguedad = antiguedad;
    }

    public String getEstadoInspeccion() {
        return estadoInspeccion;
    }

    public void setEstadoInspeccion(String estadoInspeccion) {
        this.estadoInspeccion = estadoInspeccion;
    }

    public double getVariableAdicional() {
        return variableAdicional;
    }

    public void setVariableAdicional(double variableAdicional) {
        this.variableAdicional = variableAdicional;
    }
}