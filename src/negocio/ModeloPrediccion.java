package negocio;

import modelo.PropiedadesFiltro;

public class ModeloPrediccion {
    // Coeficientes libres para la regresion multiple:
    // Precio = b0 + (b1 * m2) + (b2 * hab) + (b3 * ban) + (b4 * factorTipo)
    private double beta0 = 0.0;
    private double betaM2 = 0.0;
    private double betaHabitaciones = 0.0;
    private double betaBanios = 0.0;


    public void setCoeficientes(double b0, double bM2, double bHab, double bBan) {
        this.beta0 = b0;
        this.betaM2 = bM2;
        this.betaHabitaciones = bHab;
        this.betaBanios = bBan;
    }

    public double predecirPrecioMultiple(PropiedadesFiltro necesidades) {
        // Logica matematica de prediccion automatizada
        double precioBase = beta0
                + (betaM2 * necesidades.getMetrosCuadrados())
                + (betaHabitaciones * necesidades.getHabitaciones())
                + (betaBanios * necesidades.getBanios());

        // Ejemplo de factor segun el tipo de casa (POO / Logica de Negocio)
        double factorTipo = 1.0;
        if (necesidades.getTipoCasa().equalsIgnoreCase("Departamento")) factorTipo = 0.90;
        if (necesidades.getTipoCasa().equalsIgnoreCase("Quinta")) factorTipo = 1.25;

        return precioBase * factorTipo;
    }

    // Mantener compatibilidad metodos anteriores
    public double getPendiente() { return betaM2; }
    public double getInterseccion() { return beta0; }
}