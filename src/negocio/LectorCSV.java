package negocio;

import modelo.Propiedad;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LectorCSV {

    public List<Propiedad> cargarPropiedades(String rutaArchivo) {
        List<Propiedad> lista = new ArrayList<>();
        String linea;

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String encabezado = br.readLine();
            if (encabezado == null) return lista;

            String separador = ",";
            if (encabezado.contains(";")) {
                separador = ";";
            }

            int lineasCargadas = 0;

            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;

                String[] datos = linea.split(separador, -1);

                if (datos.length >= 4) {
                    try {
                        String tipoPropiedad = datos[0].trim();
                        String sector = datos[1].trim();

                        double totalConstruido = limpiarNumeroDouble(datos[2]);

                        // ================================================================
                        // CORRECCIÓN: Se lee el decimal y se multiplica por 1000 automáticamente
                        // ================================================================
                        double precio = limpiarNumeroDouble(datos[3]) * 1000;

                        String referencia = (datos.length > 4 && !datos[4].trim().isEmpty()) ? datos[4].trim() : "Sin Especificar";

                        int dormitorios = (datos.length > 5) ? limpiarNumeroInt(datos[5]) : 3;
                        int banos = (datos.length > 6) ? limpiarNumeroInt(datos[6]) : 2;
                        String antiguedad = (datos.length > 7 && !datos[7].trim().isEmpty()) ? datos[7].trim() : "A Estrenar";
                        double superficie = (datos.length > 8) ? limpiarNumeroDouble(datos[8]) : totalConstruido;
                        int ambientes = (datos.length > 9) ? limpiarNumeroInt(datos[9]) : dormitorios;

                        Propiedad propiedad = new Propiedad(
                                tipoPropiedad, sector, totalConstruido, precio,
                                referencia, dormitorios, banos, antiguedad, superficie, ambientes
                        );

                        // Regla de Auditoría adaptada al nuevo rango de precios reales (Módulo Integrante 2)
                        if (precio <= 50000 || totalConstruido <= 5) {
                            propiedad.setEstadoInspeccion("Requiere Inspección (Anomalía)");
                        } else {
                            propiedad.setEstadoInspeccion("Aprobado");
                        }

                        lista.add(propiedad);
                        lineasCargadas++;

                    } catch (Exception e) {
                        // Ignorar filas con errores estructurales
                    }
                }
            }

            System.out.println("-> [ÉXITO]: " + lineasCargadas + " registros inmobiliarios procesados correctamente en memoria.");

        } catch (IOException e) {
            System.err.println("Error crítico al procesar la base de datos física: " + e.getMessage());
        }

        return lista;
    }

    private double limpiarNumeroDouble(String texto) {
        if (texto == null || texto.trim().isEmpty()) return 0;
        try {
            String limpio = texto.trim()
                    .replace("$", "")
                    .replace(" ", "");

            if (limpio.contains(",") && limpio.contains(".")) {
                if (limpio.lastIndexOf(",") > limpio.lastIndexOf(".")) {
                    limpio = limpio.replace(".", "").replace(",", ".");
                } else {
                    limpio = limpio.replace(",", "");
                }
            } else if (limpio.contains(",")) {
                limpio = limpio.replace(",", ".");
            }

            return Double.parseDouble(limpio);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private int limpiarNumeroInt(String texto) {
        if (texto == null || texto.trim().isEmpty()) return 0;
        try {
            String limpio = texto.trim().replaceAll("[^0-9]", "");
            return limpio.isEmpty() ? 0 : Integer.parseInt(limpio);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}