package persistencia;

import modelo.Pedido;
import java.io.*;
import java.util.ArrayList;

public class PedidoDAO {

    private String archivo = "data/Pedidos.txt";

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

    public void guardar(Pedido pedido) {

        try {

            BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true));
            bw.write(pedido.toArchivo());
            bw.newLine();
            bw.close();

        } catch (Exception e) {
            System.out.println("Error guardando pedido");
        }
    }

    public ArrayList<Pedido> cargar() {

        ArrayList<Pedido> lista = new ArrayList<>();

        try {

            BufferedReader br = new BufferedReader(new FileReader(archivo));
            String linea;

            while ((linea = br.readLine()) != null) {

                String[] datos = linea.split(";");

                int idPedido = Integer.parseInt(datos[0]);
                int idMesa = Integer.parseInt(datos[1]);
                String fechaHora = datos[2];
                String estado = datos[3];

                lista.add(new Pedido(idPedido, idMesa, fechaHora, estado));
            }

            br.close();

        } catch (Exception e) {
        }

        return lista;
    }
}