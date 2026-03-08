package persistencia;

import modelo.Mesa;
import java.io.*;
import java.util.ArrayList;

public class MesaDAO {

    private String archivo = "data/Mesas.txt";

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

    public void guardar(Mesa mesa) {

        try {

            BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true));
            bw.write(mesa.toArchivo());
            bw.newLine();
            bw.close();

        } catch (Exception e) {
            System.out.println("Error guardando mesa");
        }
    }

    public ArrayList<Mesa> cargar() {

        ArrayList<Mesa> lista = new ArrayList<>();

        try {

            BufferedReader br = new BufferedReader(new FileReader(archivo));
            String linea;

            while ((linea = br.readLine()) != null) {

                String[] datos = linea.split(";");

                int id = Integer.parseInt(datos[0]);
                int capacidad = Integer.parseInt(datos[1]);
                String estado = datos[2];

                Mesa mesa = new Mesa(id, capacidad, estado);
                lista.add(mesa);
            }

            br.close();

        } catch (Exception e) {
        }

        return lista;
    }

    public Mesa buscar(int idMesa) {

        ArrayList<Mesa> mesas = cargar();

        for (Mesa m : mesas) {

            if (m.getIdMesa() == idMesa) {
                return m;
            }
        }

        return null;
    }

    public void eliminar(int idMesa) {

        ArrayList<Mesa> mesas = cargar();

        try {

            BufferedWriter bw = new BufferedWriter(new FileWriter(archivo));

            for (Mesa m : mesas) {

                if (m.getIdMesa() != idMesa) {

                    bw.write(m.toArchivo());
                    bw.newLine();
                }
            }

            bw.close();

        } catch (Exception e) {
            System.out.println("Error eliminando mesa");
        }
    }
}