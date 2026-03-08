package servicio;

import java.io.*;
import java.util.*;

public class ReporteService {

    public void top5Platos() {

        Map<Integer, Integer> conteo = new HashMap<>();

        try {

            BufferedReader br = new BufferedReader(new FileReader("data/DetallePedido.txt"));

            String linea;

            while ((linea = br.readLine()) != null) {

                String[] partes = linea.split(";");

                int idPlato = Integer.parseInt(partes[2]);
                int cantidad = Integer.parseInt(partes[3]);

                conteo.put(idPlato, conteo.getOrDefault(idPlato, 0) + cantidad);

            }

            br.close();

        } catch (Exception e) {

            System.out.println("Error leyendo archivo");

        }

        List<Map.Entry<Integer, Integer>> lista = new ArrayList<>(conteo.entrySet());

        lista.sort((a, b) -> b.getValue() - a.getValue());

        System.out.println("===== TOP 5 PLATOS =====");

        for (int i = 0; i < Math.min(5, lista.size()); i++) {

            System.out.println("Plato ID: " + lista.get(i).getKey() +
                    " Cantidad: " + lista.get(i).getValue());

        }

    }

    public void tiempoPromedioEspera() {

        int total = 0;
        int cantidad = 0;

        try {

            BufferedReader br = new BufferedReader(new FileReader("data/Pedidos.txt"));

            String linea;

            while ((linea = br.readLine()) != null) {

                String[] partes = linea.split(";");

                int espera = Integer.parseInt(partes[3]);

                total += espera;

                cantidad++;

            }

            br.close();

        } catch (Exception e) {

            System.out.println("Error leyendo pedidos");

        }

        if (cantidad > 0) {

            double promedio = (double) total / cantidad;

            System.out.println("Tiempo promedio de espera: " + promedio + " minutos");

        }

    }

}