package ui;

import servicio.RestauranteService;
import servicio.ReporteService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class VentanaPrincipal extends JFrame {
    private RestauranteService servicio = new RestauranteService();
    private ReporteService reporte = new ReporteService();

    public VentanaPrincipal() {
        // Configuración básica de la ventana
        setTitle("Sistema de Gestión - Restaurante Uninorte");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel de Encabezado
        JPanel header = new JPanel();
        header.setBackground(new Color(180, 0, 0)); // Rojo Uninorte
        JLabel titulo = new JLabel("MENÚ ADMINISTRATIVO");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        header.add(titulo);
        add(header, BorderLayout.NORTH);

        // Panel de Botones (Cuerpo)
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(6, 1, 10, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        // Creamos los botones reutilizando tu lógica
        panelBotones.add(crearBoton("1. Crear Mesa", e -> guiCrearMesa()));
        panelBotones.add(crearBoton("2. Crear Plato", e -> guiCrearPlato()));
        panelBotones.add(crearBoton("3. Crear Pedido", e -> guiCrearPedido()));
        panelBotones.add(crearBoton("4. Reporte Top 5 Platos", e -> guiTop5()));
        panelBotones.add(crearBoton("5. Tiempo Promedio", e -> guiPromedio()));
        panelBotones.add(crearBoton("Salir", e -> System.exit(0)));

        add(panelBotones, BorderLayout.CENTER);
    }

    private JButton crearBoton(String texto, ActionListener accion) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.PLAIN, 16));
        boton.setFocusable(false);
        boton.addActionListener(accion);
        return boton;
    }

    // --- MÉTODOS DE INTERFAZ CONECTADOS A TU SERVICIO ---

    private void guiCrearMesa() {
        String capStr = JOptionPane.showInputDialog(this, "Capacidad de la mesa:");
        if (capStr != null) {
            int capacidad = Integer.parseInt(capStr);
            servicio.agregarMesa(capacidad, "LIBRE");
            JOptionPane.showMessageDialog(this, "Mesa agregada con éxito.");
        }
    }

    private void guiCrearPlato() {
        JTextField nombreF = new JTextField();
        JTextField precioF = new JTextField();
        Object[] fields = {"Nombre:", nombreF, "Precio:", precioF};
        
        int result = JOptionPane.showConfirmDialog(this, fields, "Nuevo Plato", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            servicio.agregarPlato(nombreF.getText(), Double.parseDouble(precioF.getText()));
            JOptionPane.showMessageDialog(this, "Plato guardado.");
        }
    }

    private void guiCrearPedido() {
        JTextField mesaF = new JTextField();
        JTextField fechaF = new JTextField("2026-03-08"); // Fecha actual
        Object[] fields = {"ID Mesa:", mesaF, "Fecha:", fechaF};

        int result = JOptionPane.showConfirmDialog(this, fields, "Crear Pedido", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            servicio.crearPedido(Integer.parseInt(mesaF.getText()), fechaF.getText());
            JOptionPane.showMessageDialog(this, "Pedido creado en archivos.");
        }
    }

    private void guiTop5() {
        // Aquí podrías mejorar ReporteService para que devuelva un String en lugar de hacer print
        reporte.top5Platos(); 
        JOptionPane.showMessageDialog(this, "El reporte se generó en la consola (puedes revisarlo allí).");
    }

    private void guiPromedio() {
        reporte.tiempoPromedioEspera();
        JOptionPane.showMessageDialog(this, "Cálculo realizado. Revisa la consola.");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VentanaPrincipal().setVisible(true);
        });
    }
}