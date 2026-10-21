package negocio;

import modelo.PropiedadesFiltro;

/**
 * Clase encargada de gestionar los modelos estadisticos de prediccion
 * de precios de mercado aplicando principios de regresion.
 */
public class RegresionLineal {

    // Coeficientes libres para la prediccion multivariable (IA)
    // Formula base: Precio = b0 + (b1 * m2) + (b2 * habitaciones) + (b3 * banios)
    private double beta0;
    private double betaM2;
    private double betaHabitaciones;
    private double betaBanios;

    /**
     * Constructor por defecto. Inicializa los coeficientes en cero
     * a la espera del entrenamiento o asignacion dinamica.
     */
    public RegresionLineal() {
        this.beta0 = 0.0;
        this.betaM2 = 0.0;
        this.betaHabitaciones = 0.0;
        this.betaBanios = 0.0;
    }

    /**
     * Permite asignar o reemplazar dinamicamente los coeficientes reales
     * una vez que el modelo ha sido calculado matematicamente a partir del CSV.
     */
    public void setCoeficientesMultiples(double b0, double bM2, double bHab, double bBan) {
        this.beta0 = b0;
        this.betaM2 = bM2;
        this.betaHabitaciones = bHab;
        this.betaBanios = bBan;
    }

    /**
     * Predice el precio final aplicando la ecuacion de regresion multiple
     * y aplicando factores de ajuste orientados a objetos (POO) segun el tipo de inmueble.
     * * @param necesidades Objeto DTO con las variables ingresadas por el cliente
     * @return precio Estimado final de la propiedad
     */
    public double predecirPrecioMultiple(PropiedadesFiltro necesidades) {
        // Evaluacion de la ecuacion de regresion lineal multiple entrenada
        double precioBase = this.beta0
                + (this.betaM2 * necesidades.getMetrosCuadrados())
                + (this.betaHabitaciones * necesidades.getHabitaciones())
                + (this.betaBanios * necesidades.getBanios());

        // Regla de negocio / Comportamiento POO: Multiplicador comercial por tipo de vivienda
        double factorTipo = 1.0;

        if (necesidades.getTipoCasa() != null) {
            if (necesidades.getTipoCasa().equalsIgnoreCase("Departamento")) {
                factorTipo = 0.90; // Factor de reduccion por densidad habitacional mas alta
            } else if (necesidades.getTipoCasa().equalsIgnoreCase("Quinta")) {
                factorTipo = 1.25; // Factor de incremento por area verde o plusvalia campestre
            }
        }

        return precioBase * factorTipo;
    }

    /**
     * Metodo heredado/compatible para predicciones simples (unicamente m2)
     */
    public double predecirPrecio(double metrosCuadrados) {
        return this.beta0 + (this.betaM2 * metrosCuadrados);
    }

    // --- GETTERS Y SETTERS PARA LA INTEGRACION COMERCIAL DE LA INTERFAZ ---

    public double getInterseccion() {
        return this.beta0;
    }

    public void setInterseccion(double beta0) {
        this.beta0 = beta0;
    }

    public double getPendiente() {
        return this.betaM2;
    }

    public void setPendiente(double betaM2) {
        this.betaM2 = betaM2;
    }

    public double getBetaHabitaciones() {
        return this.betaHabitaciones;
    }

    public void setBetaHabitaciones(double betaHabitaciones) {
        this.betaHabitaciones = betaHabitaciones;
    }

    public double getBetaBanios() {
        return this.betaBanios;
    }

    public void setBetaBanios(double betaBanios) {
        this.betaBanios = betaBanios;
    }
}