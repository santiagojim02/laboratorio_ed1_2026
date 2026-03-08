package persistencia;

import modelo.DetallePedido;
import java.io.*;
import java.util.ArrayList;

public class DetallePedidoDAO {

    private String archivo = "data/DetallePedido.txt";

    public int generarNuevoId() {

        int mayor = 0;

        try {

            BufferedReader br = new BufferedReader(new FileReader(archivo));
            String linea;

            while ((linea = br.readLine()) != null) {

                String[] datos = linea.split(";");
                int id = Integer.parseInt(datos[0]);

                if (id > mayor) {
                    mayor = id;
                }
            }

            br.close();

        } catch (Exception e) {
        }

        return mayor + 1;
    }

    public void guardar(DetallePedido detalle) {

        try {

            BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true));
            bw.write(detalle.toArchivo());
            bw.newLine();
            bw.close();

        } catch (Exception e) {
            System.out.println("Error guardando detalle");
        }
    }

    public ArrayList<DetallePedido> cargar() {

        ArrayList<DetallePedido> lista = new ArrayList<>();

        try {

            BufferedReader br = new BufferedReader(new FileReader(archivo));
            String linea;

            while ((linea = br.readLine()) != null) {

                String[] datos = linea.split(";");

                int idDetalle = Integer.parseInt(datos[0]);
                int idPedido = Integer.parseInt(datos[1]);
                int idPlato = Integer.parseInt(datos[2]);
                int cantidad = Integer.parseInt(datos[3]);
                double subtotal = Double.parseDouble(datos[4]);

                lista.add(new DetallePedido(idDetalle, idPedido, idPlato, cantidad, subtotal));
            }

            br.close();

        } catch (Exception e) {
        }

        return lista;
    }
}