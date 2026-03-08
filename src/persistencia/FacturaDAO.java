package persistencia;

import modelo.Factura;
import java.io.*;
import java.util.ArrayList;

public class FacturaDAO {

    private String archivo = "data/Facturas.txt";

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

    public void guardar(Factura factura) {

        try {

            BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true));
            bw.write(factura.toArchivo());
            bw.newLine();
            bw.close();

        } catch (Exception e) {
            System.out.println("Error guardando factura");
        }
    }

    public ArrayList<Factura> cargar() {

        ArrayList<Factura> lista = new ArrayList<>();

        try {

            BufferedReader br = new BufferedReader(new FileReader(archivo));
            String linea;

            while ((linea = br.readLine()) != null) {

                String[] datos = linea.split(";");

                int idFactura = Integer.parseInt(datos[0]);
                int idPedido = Integer.parseInt(datos[1]);
                String fechaHora = datos[2];
                double subtotal = Double.parseDouble(datos[3]);
                double propina = Double.parseDouble(datos[4]);
                double total = Double.parseDouble(datos[5]);

                lista.add(new Factura(idFactura, idPedido, fechaHora, subtotal, propina, total));
            }

            br.close();

        } catch (Exception e) {
        }

        return lista;
    }
}