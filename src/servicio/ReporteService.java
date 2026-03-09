package servicio;

import java.io.*;
import java.util.*;

public class ReporteService {

    /**
     * Calcula los 5 platos más pedidos leyendo DetallePedido.txt
     * @return String con el reporte formateado para una ventana
     */
    public String top5Platos() {
        Map<Integer, Integer> conteo = new HashMap<>();
        StringBuilder sb = new StringBuilder("===== TOP 5 PLATOS =====\n\n");
        File archivo = new File("data/DetallePedido.txt");

        if (!archivo.exists()) {
            return "Error: No se encontró el archivo de detalles (DetallePedido.txt).";
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                String[] partes = linea.split(";");
                if (partes.length >= 4) {
                    int idPlato = Integer.parseInt(partes[2]);
                    int cantidad = Integer.parseInt(partes[3]);
                    conteo.put(idPlato, conteo.getOrDefault(idPlato, 0) + cantidad);
                }
            }
        } catch (Exception e) {
            return "Error al procesar el Top 5: " + e.getMessage();
        }

        List<Map.Entry<Integer, Integer>> lista = new ArrayList<>(conteo.entrySet());
        lista.sort((a, b) -> b.getValue() - a.getValue());

        if (lista.isEmpty()) return "No hay datos de ventas para mostrar.";

        for (int i = 0; i < Math.min(5, lista.size()); i++) {
            sb.append("Posición ").append(i + 1)
              .append(" - Plato ID: ").append(lista.get(i).getKey())
              .append(" | Pedidos: ").append(lista.get(i).getValue()).append("\n");
        }
        return sb.toString();
    }

    /**
     * Calcula el promedio de la columna de tiempo en Pedidos.txt
     * @return String con el resultado del promedio
     */
    public String tiempoPromedioEspera() {
        int total = 0;
        int cantidad = 0;
        File archivo = new File("data/Pedidos.txt");

        if (!archivo.exists()) {
            return "Error: El archivo data/Pedidos.txt no existe.";
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                String[] partes = linea.split(";");
                
                // Verificamos que la línea tenga al menos 4 columnas (ID;Mesa;Fecha;Tiempo;Estado)
                if (partes.length >= 4) {
                    try {
                        int espera = Integer.parseInt(partes[3].trim());
                        total += espera;
                        cantidad++;
                    } catch (NumberFormatException nfe) {
                        // Si la columna 4 no es un número, saltamos la línea
                        continue;
                    }
                }
            }
        } catch (Exception e) {
            return "Error al procesar promedios: " + e.getMessage();
        }

        if (cantidad > 0) {
            double promedio = (double) total / cantidad;
            return "ESTADÍSTICAS DE SERVICIO\n\n" +
                   "Total de pedidos analizados: " + cantidad + "\n" +
                   "Tiempo promedio de espera: " + String.format("%.2f", promedio) + " minutos.";
        }
        return "No hay pedidos con datos de tiempo suficientes para calcular.";
    }
}