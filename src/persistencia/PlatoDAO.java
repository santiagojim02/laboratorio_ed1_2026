package persistencia;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import modelo.Plato;

public class PlatoDAO {

    private final String archivo = "data/Platos.txt";


    public void guardarPlato(Plato plato) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true))) {
            bw.write(plato.toArchivo());
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error al guardar el plato.");
        }
    }

    public List<Plato> cargarPlatos() {

        List<Plato> lista = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {

            String linea;

            while ((linea = br.readLine()) != null) {

                String[] partes = linea.split(";");

                int id = Integer.parseInt(partes[0]);
                String nombre = partes[1];
                String categoria = partes[2];
                double precio = Double.parseDouble(partes[3]);
                String disponible = partes[4];

                Plato p = new Plato(id, nombre, categoria, precio, disponible);

                lista.add(p);
            }

        } catch (Exception e) {
            System.out.println("Error al leer el archivo.");
        }

        return lista;
    }

    public Plato buscarPorId(int idBuscado) {

        List<Plato> lista = cargarPlatos();

        for (Plato p : lista) {
            if (p.getIdPlato() == idBuscado) {
                return p;
            }
        }

        return null;
    }

}