package modelo;

public class PropiedadesFiltro {
    private double metrosCuadrados;
    private int habitaciones;
    private int banios;
    private String tipoCasa;

    public PropiedadesFiltro(double metrosCuadrados, int habitaciones, int banios, String tipoCasa) {
        this.metrosCuadrados = metrosCuadrados;
        this.habitaciones = habitaciones;
        this.banios = banios;
        this.tipoCasa = tipoCasa;
    }

    // Getters
    public double getMetrosCuadrados() { return metrosCuadrados; }
    public int getHabitaciones() { return habitaciones; }
    public int getBanios() { return banios; }
    public String getTipoCasa() { return tipoCasa; }
}