package servicio;

import modelo.*;
import persistencia.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RestauranteService {
    PlatoDAO platoDAO = new PlatoDAO();
    MesaDAO mesaDAO = new MesaDAO();
    PedidoDAO pedidoDAO = new PedidoDAO();
    DetallePedidoDAO detalleDAO = new DetallePedidoDAO();
    FacturaDAO facturaDAO = new FacturaDAO();

    public void agregarPlato(String nombre, double precio) {
        int id = platoDAO.generarNuevoId();
        Plato plato = new Plato(id, nombre, "general", precio, "activo");
        platoDAO.guardarPlato(plato);
    }

    public void agregarMesa(int capacidad, String estado) {
        int id = mesaDAO.generarNuevoId();
        Mesa mesa = new Mesa(id, capacidad, estado);
        mesaDAO.guardar(mesa);
    }

    public void crearPedido(int idMesa, String fechaHora) {
        int id = pedidoDAO.generarNuevoId();
        Pedido pedido = new Pedido(id, idMesa, fechaHora, "ABIERTO");
        pedidoDAO.guardar(pedido);
    }

    public String agregarPlatoAPedido(int idPedido, int idPlato, int cantidad) {
        double precioReal = 0;
        boolean platoEncontrado = false;

        try (BufferedReader br = new BufferedReader(new FileReader("data/Platos.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                String[] partes = linea.split(";");
                if (Integer.parseInt(partes[0]) == idPlato) {
                    precioReal = Double.parseDouble(partes[3]);
                    platoEncontrado = true;
                    break;
                }
            }
        } catch (Exception e) {
            return "Error al acceder a los platos: " + e.getMessage();
        }

        if (!platoEncontrado) return "Error: Plato no existe.";

        double subtotal = precioReal * cantidad;
        int idDetalle = detalleDAO.generarNuevoId();
        
        // CORRECCIÓN: Forzamos el salto de línea al final
        try (PrintWriter pw = new PrintWriter(new FileWriter("data/DetallePedido.txt", true))) {
            pw.println(idDetalle + ";" + idPedido + ";" + idPlato + ";" + cantidad + ";" + subtotal);
        } catch (IOException e) {
            return "Error al guardar detalle.";
        }
        
        return "¡Éxito! Subtotal: $" + subtotal;
    }

    public boolean cambiarEstadoPedido(int idPedido, String nuevoEstado) {
        File archivo = new File("data/Pedidos.txt");
        List<String> lineas = new ArrayList<>();
        boolean encontrado = false;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                String[] partes = linea.split(";");
                if (Integer.parseInt(partes[0]) == idPedido) {
                    String tiempo = (partes.length > 3) ? partes[3] : "0";
                    lineas.add(partes[0] + ";" + partes[1] + ";" + partes[2] + ";" + tiempo + ";" + nuevoEstado);
                    encontrado = true;
                } else {
                    lineas.add(linea);
                }
            }
        } catch (Exception e) { return false; }

        if (encontrado) {
            try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
                for (String l : lineas) {
                    pw.println(l); // CORRECCIÓN: Escribe cada línea y salta a la siguiente
                }
            } catch (Exception e) { return false; }

            if (nuevoEstado.equalsIgnoreCase("CERRADO")) {
                generarFactura(idPedido);
            }
        }
        return encontrado;
    }

    private void generarFactura(int idPedido) {
        double totalPedido = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("data/DetallePedido.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                String[] partes = linea.split(";");
                if (Integer.parseInt(partes[1]) == idPedido) {
                    totalPedido += Double.parseDouble(partes[4]);
                }
            }
            
            int idFactura = facturaDAO.generarNuevoId();
            try (PrintWriter pw = new PrintWriter(new FileWriter("data/Facturas.txt", true))) {
                pw.println(idFactura + ";" + idPedido + ";" + totalPedido + ";2026-03-10"); // CORRECCIÓN: println
            }
        } catch (Exception e) {
            System.err.println("Error factura: " + e.getMessage());
        }
    }
}