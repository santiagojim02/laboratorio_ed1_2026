package persistencia;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
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
        e.printStackTrace();
        }
    }

    public List<Plato> cargarPlatos() {

        List<Plato> lista = new ArrayList<>();

        File file = new File(archivo);
        if (!file.exists()) {
            return lista;
        }

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

    public int generarNuevoId() {

        List<Plato> lista = cargarPlatos();

        if (lista.isEmpty()) {
            return 1;
        }

        int mayor = lista.get(0).getIdPlato();

        for (Plato p : lista) {
            if (p.getIdPlato() > mayor) {
                mayor = p.getIdPlato();
            }
        }

        return mayor + 1;
    }
    public boolean eliminarPlato(int idEliminar) {

    List<Plato> lista = cargarPlatos();
    boolean eliminado = false;

    for (int i = 0; i < lista.size(); i++) {
        if (lista.get(i).getIdPlato() == idEliminar) {
            lista.remove(i);
            eliminado = true;
            break;
        }
    }

    if (eliminado) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            for (Plato p : lista) {
                bw.write(p.toArchivo());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al reescribir el archivo.");
        }
    }

    return eliminado;
}
public boolean actualizarPlato(Plato platoActualizado) {

    List<Plato> lista = cargarPlatos();
    boolean actualizado = false;

    for (int i = 0; i < lista.size(); i++) {
        if (lista.get(i).getIdPlato() == platoActualizado.getIdPlato()) {
            lista.set(i, platoActualizado);
            actualizado = true;
            break;
        }
    }

    if (actualizado) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            for (Plato p : lista) {
                bw.write(p.toArchivo());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al reescribir el archivo.");
        }
    }

    return actualizado;
}
}