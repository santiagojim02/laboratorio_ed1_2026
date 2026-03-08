package modelo;

public class Pedido {

    private int idPedido;
    private int idMesa;
    private String fechaHora;
    private String estado;

    public Pedido(int idPedido, int idMesa, String fechaHora, String estado) {
        this.idPedido = idPedido;
        this.idMesa = idMesa;
        this.fechaHora = fechaHora;
        this.estado = estado;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public int getIdMesa() {
        return idMesa;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public String getEstado() {
        return estado;
    }

    public String toArchivo() {
        return idPedido + ";" + idMesa + ";" + fechaHora + ";" + estado;
    }
}