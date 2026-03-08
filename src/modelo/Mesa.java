package modelo;

public class Mesa {

    private int idMesa;
    private int capacidad;
    private String estado;

    public Mesa(int idMesa, int capacidad, String estado) {
        this.idMesa = idMesa;
        this.capacidad = capacidad;
        this.estado = estado;
    }

    public int getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(int idMesa) {
        this.idMesa = idMesa;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String toArchivo() {
        return idMesa + ";" + capacidad + ";" + estado;
    }
}