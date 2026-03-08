package servicio;

import modelo.*;
import persistencia.*;

import java.util.ArrayList;

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

    public void agregarDetalle(int idPedido, int idPlato, int cantidad, double subtotal) {

        int id = detalleDAO.generarNuevoId();

        DetallePedido detalle = new DetallePedido(id, idPedido, idPlato, cantidad, subtotal);

        detalleDAO.guardar(detalle);
    }

    public void generarFactura(int idPedido, String fechaHora, double subtotal, double propina, double total) {

        int id = facturaDAO.generarNuevoId();

        Factura factura = new Factura(id, idPedido, fechaHora, subtotal, propina, total);

        facturaDAO.guardar(factura);
    }

    ReporteService reporte = new ReporteService();
}