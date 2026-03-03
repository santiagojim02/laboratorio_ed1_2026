package modelo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Plato {

    private int idPlato;
    private String nombre;
    private String categoria;
    private double precio;
    private String disponible; // boolean (y/n)

    public Plato(int idPlato, String nombre, String categoria, double precio, String disponible) {
        this.idPlato = idPlato;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.disponible = disponible;
    }

    public int getIdPlato() {
        return idPlato;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public double getPrecio() {
        return precio;
    }

    public String getDisponible() {
        return disponible;
    }

    public void setDisponible(String disponible) {
        this.disponible = disponible;
    }

    public String toArchivo() {
        return idPlato + ";" + nombre + ";" + categoria + ";" + precio + ";" + disponible;
    }

    @Override
    public String toString() {
        return "ID: " + idPlato +
               "\nNombre: " + nombre +
               "\nCategoría: " + categoria +
               "\nPrecio: " + precio +
               "\nDisponible: " + disponible;
    }
}
