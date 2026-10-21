package negocio;

import modelo.Propiedad;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase controladora del modulo de negocio (Fachada).
 * Centraliza la administracion de los datos inmobiliarios y los algoritmos de IA.
 */
public class InmobiliariaNegocio {
    private List<Propiedad> listaPropiedades;
    private RegresionLineal modeloPrediccion;

    /**
     * Constructor. Inicializa la coleccion de datos y el motor de prediccion (POO).
     */
    public InmobiliariaNegocio() {
        this.listaPropiedades = new ArrayList<>();
        this.modeloPrediccion = new RegresionLineal();
    }

    /**
     * Inicializa el sistema cargando los datos desde el archivo plano CSV
     * y entrena de manera simulada o automatica los coeficientes del modelo.
     * * @param rutaArchivo Nombre o ubicacion del archivo datos.csv
     */
    public void inicializarSistema(String rutaArchivo) {
        cargarDatosDesdeCSV(rutaArchivo);

        // --- SECCION DE ENTRENAMIENTO / REEMPLAZO DE COEFICIENTES REALES ---
        // Aqui puedes colocar tu algoritmo matematico real para calcular la regresion.
        // Mientras tanto, se configuran coeficientes comerciales base aproximados para el mercado:
        // Formula: b0 (Base) = 25000, b1 (m2) = 850, b2 (Habitaciones) = 4500, b3 (Banios) = 3000
        this.modeloPrediccion.setCoeficientesMultiples(25000.0, 850.0, 4500.0, 3000.0);
    }

    /**
     * Metodo privado encargado del parseo de datos (Lectura de Archivos Planos).
     */
    private void cargarDatosDesdeCSV(String rutaArchivo) {
        this.listaPropiedades.clear();
        String linea;

        // Expresión regular avanzada: separa por comas, pero ignora las comas dentro de comillas
        String regexSeparador = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            // Leer y omitir la cabecera
            String cabecera = br.readLine();

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(regexSeparador);

                if (datos.length >= 9) {
                    try {
                        String tipo = datos[0].trim().replace("\"", "");
                        String sector = datos[1].trim().replace("\"", "");
                        double m2 = Double.parseDouble(datos[2].trim().replace("\"", ""));

                        // EXTRAEMOS EL PRECIO DEL CSV Y LO MULTIPLICAMOS POR 1000 PARA TENER EL VALOR REAL
                        double precioOriginal = Double.parseDouble(datos[3].trim().replace("\"", ""));
                        double precio = precioOriginal * 1000.0; // <--- AQUÍ SE HACE LA CONVERSIÓN A VALOR COMPLETO

                        String referencia = datos[4].trim().replace("\"", "");
                        int dormitorios = Integer.parseInt(datos[5].trim().replace("\"", ""));
                        int banos = Integer.parseInt(datos[6].trim().replace("\"", ""));

                        int antiguedad;
                        String estado;

                        if (datos.length == 9) {
                            antiguedad = Integer.parseInt(datos[7].trim().replace("\"", ""));
                            estado = datos[8].trim().replace("\"", "");
                        } else {
                            antiguedad = Integer.parseInt(datos[9].trim().replace("\"", ""));
                            estado = datos[7].trim().replace("\"", "");
                        }

                        // Se guarda el objeto con el precio ya convertido
                        Propiedad propiedad = new Propiedad(tipo, sector, m2, precio, referencia, dormitorios, banos, antiguedad, estado);
                        this.listaPropiedades.add(propiedad);

                    } catch (NumberFormatException e) {
                        System.err.println("Fila descartada por inconsistencia numerica: " + linea);
                    }
                }
            }
            System.out.println("Sincronizacion exitosa: " + this.listaPropiedades.size() + " inmuebles cargados.");

        } catch (IOException e) {
            System.err.println("Error critico al leer el archivo CSV: " + e.getMessage());
            generarDatosRespaldo();
        }
    }

    /**
     * Genera datos de prueba controlados para evitar fallos si el archivo datos.csv no se encuentra en la raiz.
     */
    private void generarDatosRespaldo() {
        System.out.println("Activando base de datos de respaldo...");
        this.listaPropiedades.add(new Propiedad("Casa", "Cumbaya", 160.0, 185000.0, "Ref-01", 3, 3, 2, "Normal"));
        this.listaPropiedades.add(new Propiedad("Departamento", "La Carolina", 95.0, 125000.0, "Ref-02", 2, 2, 5, "Normal"));
        this.listaPropiedades.add(new Propiedad("Casa", "Tumbaco", 220.0, 240000.0, "Ref-03", 4, 3, 0, "Normal"));
        this.listaPropiedades.add(new Propiedad("Departamento", "Cumbaya", 110.0, 145000.0, "Ref-04", 2, 2, 1, "Anomalia de Costo"));
        this.listaPropiedades.add(new Propiedad("Quinta", "Los Chillos", 450.0, 390000.0, "Ref-05", 5, 4, 10, "Normal"));
    }

    // --- ENCAPSULAMIENTO (GETTERS Y SETTERS) ---

    /**
     * Devuelve la lista completa de propiedades cargadas en memoria.
     */
    public List<Propiedad> getListaPropiedades() {
        return this.listaPropiedades;
    }

    /**
     * Provee acceso directo al objeto de regresion para calcular o modificar formulas.
     */
    public RegresionLineal getModeloPrediccion() {
        return this.modeloPrediccion;
    }
}