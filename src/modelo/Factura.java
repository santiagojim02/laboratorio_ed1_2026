package modelo;

public class Factura {

    private int idFactura;
    private int idPedido;
    private String fechaHora;
    private double subtotal;
    private double propina;
    private double total;

    public Factura(int idFactura, int idPedido, String fechaHora, double subtotal, double propina, double total) {
        this.idFactura = idFactura;
        this.idPedido = idPedido;
        this.fechaHora = fechaHora;
        this.subtotal = subtotal;
        this.propina = propina;
        this.total = total;
    }

    public int getIdFactura() {
        return idFactura;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double getPropina() {
        return propina;
    }

    public double getTotal() {
        return total;
    }

    public String toArchivo() {
        return idFactura + ";" + idPedido + ";" + fechaHora + ";" + subtotal + ";" + propina + ";" + total;
    }
}