package servicio;

import java.io.*;
import java.util.*;

public class ReporteService {

    public String top5Platos() {
        Map<Integer, Integer> conteo = new HashMap<>();
        StringBuilder sb = new StringBuilder("===== TOP 5 PLATOS =====\n\n");
        File archivo = new File("data/DetallePedido.txt");
        if (!archivo.exists()) return "No hay datos de detalles.";

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
        } catch (Exception e) { return "Error: " + e.getMessage(); }

        List<Map.Entry<Integer, Integer>> lista = new ArrayList<>(conteo.entrySet());
        lista.sort((a, b) -> b.getValue() - a.getValue());
        for (int i = 0; i < Math.min(5, lista.size()); i++) {
            sb.append(i + 1).append(". Plato ID: ").append(lista.get(i).getKey())
              .append(" | Cantidad: ").append(lista.get(i).getValue()).append("\n");
        }
        return sb.toString();
    }

    public String tiempoPromedioEspera() {
        int total = 0, cantidad = 0;
        File archivo = new File("data/Pedidos.txt");
        if (!archivo.exists()) return "No hay datos de pedidos.";

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                String[] partes = linea.split(";");
                if (partes.length >= 4) {
                    total += Integer.parseInt(partes[3].trim());
                    cantidad++;
                }
            }
        } catch (Exception e) { return "Error: " + e.getMessage(); }

        return cantidad > 0 ? "Promedio de espera: " + String.format("%.2f", (double)total/cantidad) + " min." : "Sin datos.";
    }

 
    public String consultarDetallePedido(int idPedido) {
        StringBuilder sb = new StringBuilder("=== DETALLE PEDIDO #" + idPedido + " ===\n\n");
        double total = 0;
        boolean encontrado = false;
        File archivo = new File("data/DetallePedido.txt");

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                String[] partes = linea.split(";");
                if (Integer.parseInt(partes[1]) == idPedido) {
                    encontrado = true;
                    double subtotal = Double.parseDouble(partes[4]);
                    sb.append("- Plato ID: ").append(partes[2])
                      .append(" | Cant: ").append(partes[3])
                      .append(" | Subtotal: $").append(subtotal).append("\n");
                    total += subtotal;
                }
            }
        } catch (Exception e) { return "Error al consultar."; }

        if (!encontrado) return "No se encontraron platos para el pedido #" + idPedido;
        sb.append("\nTOTAL A PAGAR: $").append(total);
        return sb.toString();
    }
}