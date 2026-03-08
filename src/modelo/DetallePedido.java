package modelo;

public class DetallePedido {

    private int idDetalle;
    private int idPedido;
    private int idPlato;
    private int cantidad;
    private double subtotal;

    public DetallePedido(int idDetalle, int idPedido, int idPlato, int cantidad, double subtotal) {
        this.idDetalle = idDetalle;
        this.idPedido = idPedido;
        this.idPlato = idPlato;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }

    public int getIdDetalle() {
        return idDetalle;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public int getIdPlato() {
        return idPlato;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public String toArchivo() {
        return idDetalle + ";" + idPedido + ";" + idPlato + ";" + cantidad + ";" + subtotal;
    }
}