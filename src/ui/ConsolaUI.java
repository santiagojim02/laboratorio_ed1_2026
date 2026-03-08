package ui;

import servicio.RestauranteService;
import servicio.ReporteService;

import java.util.Scanner;

public class ConsolaUI {

    Scanner scanner = new Scanner(System.in);
    RestauranteService servicio = new RestauranteService();
    ReporteService reporte = new ReporteService();

    public void iniciar() {

        int opcion;

        do {

            System.out.println("===== SISTEMA RESTAURANTE =====");

            System.out.println("1. Crear Mesa");
            System.out.println("2. Crear Plato");
            System.out.println("3. Crear Pedido");
            System.out.println("4. Reporte Top 5 Platos");
            System.out.println("5. Tiempo promedio de espera");
            System.out.println("0. Salir");

            opcion = scanner.nextInt();

            switch(opcion) {

                case 1:
                    crearMesa();
                    break;

                case 2:
                    crearPlato();
                    break;

                case 3:
                    crearPedido();
                    break;

                case 4:
                    reporte.top5Platos();
                    break;

                case 5:
                    reporte.tiempoPromedioEspera();
                    break;

            }

        } while(opcion != 0);

    }

    private void crearMesa() {

        System.out.println("Capacidad mesa:");

        int capacidad = scanner.nextInt();

        servicio.agregarMesa(capacidad, "LIBRE");

    }

    private void crearPlato() {

        scanner.nextLine();

        System.out.println("Nombre plato:");

        String nombre = scanner.nextLine();

        System.out.println("Precio:");

        double precio = scanner.nextDouble();

        servicio.agregarPlato(nombre, precio);

    }

    private void crearPedido() {

        System.out.println("ID Mesa:");

        int idMesa = scanner.nextInt();

        scanner.nextLine();

        System.out.println("Fecha:");

        String fecha = scanner.nextLine();

        servicio.crearPedido(idMesa, fecha);

    }

    public static void main(String[] args) {

    ConsolaUI ui = new ConsolaUI();
    ui.iniciar();

}

}

