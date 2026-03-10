package ui;

import servicio.*;
import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {
    private RestauranteService servicio = new RestauranteService();
    private ReporteService reporte = new ReporteService();

    public VentanaPrincipal() {
        setTitle("Sistema de Gestión - Restaurante Uninorte");
        setSize(550, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Encabezado Rojo Uninorte
        JPanel header = new JPanel();
        header.setBackground(new Color(180, 0, 0));
        JLabel titulo = new JLabel("MENÚ ADMINISTRATIVO");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        header.add(titulo);
        add(header, BorderLayout.NORTH);

        // Panel de Botones con todos los RF
        JPanel panelBotones = new JPanel(new GridLayout(9, 1, 10, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        panelBotones.add(crearBoton("1. Crear Mesa", e -> guiCrearMesa()));
        panelBotones.add(crearBoton("2. Crear Plato", e -> guiCrearPlato()));
        panelBotones.add(crearBoton("3. Crear Pedido (ABIERTO)", e -> guiCrearPedido()));
        panelBotones.add(crearBoton("4. Agregar Plato a Pedido (Detalle)", e -> guiAgregarDetalle()));
        panelBotones.add(crearBoton("5. Cerrar o Cancelar Pedido", e -> guiCambiarEstado()));
        panelBotones.add(crearBoton("5.5 Consultar Detalle de Pedido", e -> guiConsultarPedido()));
        panelBotones.add(crearBoton("6. Reporte Top 5 Platos", e -> guiTop5()));
        panelBotones.add(crearBoton("7. Tiempo Promedio", e -> guiPromedio()));
        panelBotones.add(crearBoton("Salir", e -> System.exit(0)));

        add(panelBotones, BorderLayout.CENTER);
    }

    private JButton crearBoton(String texto, java.awt.event.ActionListener accion) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.PLAIN, 16));
        boton.addActionListener(accion);
        return boton;
    }

    // --- MÉTODOS DE INTERFAZ ---

    private void guiCrearMesa() {
        String cap = JOptionPane.showInputDialog(this, "Capacidad de la mesa:");
        if(cap != null) {
            servicio.agregarMesa(Integer.parseInt(cap), "LIBRE");
            JOptionPane.showMessageDialog(this, "Mesa creada.");
        }
    }

    private void guiCrearPlato() {
        String nom = JOptionPane.showInputDialog(this, "Nombre del plato:");
        String pre = JOptionPane.showInputDialog(this, "Precio:");
        if(nom != null && pre != null) {
            servicio.agregarPlato(nom, Double.parseDouble(pre));
            JOptionPane.showMessageDialog(this, "Plato guardado.");
        }
    }

    private void guiCrearPedido() {
        String mesa = JOptionPane.showInputDialog(this, "ID de la Mesa:");
        if(mesa != null) {
            servicio.crearPedido(Integer.parseInt(mesa), "2026-03-10");
            JOptionPane.showMessageDialog(this, "Pedido abierto correctamente.");
        }
    }

    private void guiAgregarDetalle() {
        String idPed = JOptionPane.showInputDialog(this, "ID del Pedido:");
        String idPlat = JOptionPane.showInputDialog(this, "ID del Plato:");
        String cant = JOptionPane.showInputDialog(this, "Cantidad:");
        if(idPed != null && idPlat != null && cant != null) {
            String res = servicio.agregarPlatoAPedido(Integer.parseInt(idPed), Integer.parseInt(idPlat), Integer.parseInt(cant));
            JOptionPane.showMessageDialog(this, res);
        }
    }

    private void guiCambiarEstado() {
        String idPed = JOptionPane.showInputDialog(this, "ID del Pedido a modificar:");
        if(idPed != null) {
            Object[] opciones = {"CERRAR", "CANCELAR"};
            int seleccion = JOptionPane.showOptionDialog(this, "¿Qué desea hacer con el pedido?", "Estado", 
                            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
            
            String nuevoEstado = (seleccion == 0) ? "CERRADO" : "CANCELADO";
            if(servicio.cambiarEstadoPedido(Integer.parseInt(idPed), nuevoEstado)) {
                JOptionPane.showMessageDialog(this, "Pedido actualizado a: " + nuevoEstado);
            } else {
                JOptionPane.showMessageDialog(this, "Error: No se encontró el pedido o el archivo.");
            }
        }
    }

    private void guiTop5() {
        JOptionPane.showMessageDialog(this, reporte.top5Platos());
    }

    private void guiConsultarPedido() {
    String id = JOptionPane.showInputDialog(this, "Ingrese el ID del Pedido a consultar:");
    if (id != null) {
        String resultado = reporte.consultarDetallePedido(Integer.parseInt(id));
        JOptionPane.showMessageDialog(this, resultado, "Consulta de Pedido", JOptionPane.PLAIN_MESSAGE);
    }
}
    private void guiPromedio() {
        JOptionPane.showMessageDialog(this, reporte.tiempoPromedioEspera());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaPrincipal().setVisible(true));
    }
}