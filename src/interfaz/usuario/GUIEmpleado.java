package interfaz.usuario;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import consola.ConsolaGaleria;
import logica.Factura;
import logica.Galeria;
import logica.Verificacion;
import pasarela_pagos.TiposPasarela;
import piezas.Escultura;
import piezas.Fotografia;
import piezas.Impresion;
import piezas.Pieza;
import piezas.Pintura;
import piezas.Video;
import usuarios.Usuario;
import usuarios.UsuarioComun;

public class GUIEmpleado extends JFrame {
    private Galeria galeria;
    private Usuario usuario;

    public GUIEmpleado(Galeria galeria, Usuario usuario) {
        this.galeria = galeria;
        this.usuario = usuario;
        initialize();
    }

    private void initialize() {
        setTitle("Galería y Casa de Subastas");
        setSize(800, 600); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        menuAdministrador();
    }

    private void menuAdministrador() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(11, 1));

        JButton btnSubasta = new JButton("Subastas");
        JButton btnConsultarInventario = new JButton("Consultar inventario de la galeria");
        JButton btnConsultarHistorialPieza = new JButton("Consultar historial de una pieza");
        JButton btnVerHistorialArtistas = new JButton("Ver Historial Artistas");
        JButton btnConsultarClientes = new JButton("Consultar Lista Clientes");
        JButton btnConsultarHistorialCliente = new JButton("Consultar Historia Cliente");
        JButton btnSalir = new JButton("Salir");

        btnSubasta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                subastas();
            }

			private void subastas() {
				// TODO Auto-generated method stub
				
			}
        });
        
        btnConsultarInventario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	consultarInventario();
            }
        });


        btnConsultarHistorialPieza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	consultarHistorialPiezaOpcion();
            }
        });

        btnVerHistorialArtistas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verHistorialArtistas();
            }
        });

        btnConsultarClientes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                consultarListaClientes();
            }
        });

        btnConsultarHistorialCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                consultarHistorialCliente();
            }
        });

        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salir();
            }
        });

        panel.add(btnSubasta); //
        panel.add(btnConsultarInventario); //
        panel.add(btnConsultarHistorialPieza); //
        panel.add(btnVerHistorialArtistas);
        panel.add(btnConsultarClientes); //
        panel.add(btnConsultarHistorialCliente); //
        panel.add(btnSalir);

        add(panel);
    }

    private void consultarHistorialPiezaOpcion() {
        String nombrePiezaConsultar = JOptionPane.showInputDialog(this, "Ingrese el nombre de la pieza que desea consultar:");
        if (nombrePiezaConsultar == null || nombrePiezaConsultar.isEmpty()) {
            return;
        }

        Factura historiaPieza = galeria.getCurrentOwner(nombrePiezaConsultar);
        if (historiaPieza == null) {
            JOptionPane.showMessageDialog(this, "La pieza no se encuentra en el inventario.");
            return;
        }

        imprimirDetallesPieza(historiaPieza.getIdPieza());

        StringBuilder detalles = new StringBuilder();
        detalles.append("Dueño actual de la pieza: ").append(historiaPieza.getComprador().getNombre())
                .append(" ").append(historiaPieza.getComprador().getApellido()).append("\n");
        detalles.append("Vendida por un valor de: ").append(historiaPieza.getValor());

        JOptionPane.showMessageDialog(this, detalles.toString(), "Historial de la Pieza", JOptionPane.INFORMATION_MESSAGE);
    }

    private void verHistorialArtistas() {
        String nombreArtista = JOptionPane.showInputDialog(this, "Ingrese el nombre del artista que quiere consultar:");
        galeria.historiaArtista(nombreArtista);
        JOptionPane.showMessageDialog(this, "Historial de artistas consultado.");
    }

    private void consultarListaClientes() {
        Map<String, Usuario> usuarios = galeria.getUsuarios();
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Usuario> entry : usuarios.entrySet()) {
            String key = entry.getKey();
            Usuario value = entry.getValue();
            sb.append("Nombre de Usuario: ").append(value.getNombre()).append("\n");
            sb.append("(ID) Buscar como: ").append(key).append("\n");
            sb.append("-----------------------------------\n");
        }
        JOptionPane.showMessageDialog(this, sb.toString(), "Lista de Clientes", JOptionPane.INFORMATION_MESSAGE);
    }

    private void consultarHistorialCliente() {
        String idABuscar = JOptionPane.showInputDialog(this, "Ingrese el id del usuario que desea consultar");
        UsuarioComun usuario = (UsuarioComun) galeria.getUsuarioId(idABuscar);
        Map<String, Factura> piezasHistoria = galeria.getHistorialDeCompras();

        StringBuilder sb = new StringBuilder();
        sb.append("+++++++++++++++++++++++++++++++++\n");
        sb.append("Usuario: ").append(usuario.getNombre()).append(" ").append(usuario.getApellido()).append("\n");
        sb.append("+++++++++++++++++++++++++++++++++\n");

        for (Map.Entry<String, Factura> entry : piezasHistoria.entrySet()) {
            Factura value = entry.getValue();
            if (value.getComprador().getId().equals(usuario.getId())) {
                sb.append("Nombre de Pintura: ").append(value.getIdPieza()).append("\n");
                sb.append("Fecha de compra: ").append(value.getFecha()).append("\n");
                sb.append("---\n");
            }
        }

        List<Pieza> piezasUser = usuario.getPiezasActuales();
        sb.append("[ Piezas en el inventario del usuario ]\n");
        double total = 0;

        for (Pieza entry : piezasUser) {
            sb.append("Nombre de Pintura:").append(entry.getTitulo()).append("\n");
            sb.append("Valor: ").append(entry.getValor()).append("\n");
            sb.append("---\n");
            total += entry.getValor();
        }

        sb.append("Valor del inventario:").append(total).append("\n");

        JOptionPane.showMessageDialog(this, sb.toString(), "Historial del Cliente", JOptionPane.INFORMATION_MESSAGE);
    }

    private void salir() {
        int response = JOptionPane.showConfirmDialog(this, "¿Está seguro que desea salir?", "Salir", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            galeria.salvarGaleria();
            this.dispose();
        }
    }

    public Pieza imprimirDetallesPieza(String idPieza) {
        Pieza pieza = galeria.getPieza(idPieza);
        if (pieza == null) {
            return null;
        }

        StringBuilder detalles = new StringBuilder();
        detalles.append("Detalles de la Pieza:\n");
        detalles.append("Título: ").append(pieza.getTitulo()).append("\n");
        detalles.append("Autor: ").append(pieza.getAutor()).append("\n");
        detalles.append("Año de Creación: ").append(pieza.getAnioCreacion()).append("\n");
        detalles.append("Lugar de Creación: ").append(pieza.getLugarCreacion()).append("\n");
        detalles.append("En Exhibición: ").append(pieza.isExhibicion()).append("\n");
        detalles.append("Valor: ").append(pieza.getValor()).append("\n");
        detalles.append("Estado: ").append(pieza.getEstado()).append("\n");

        if (pieza instanceof Pintura) {
            Pintura p = (Pintura) pieza;
            detalles.append("Alto: ").append(p.getAlto()).append("\n");
            detalles.append("Ancho: ").append(p.getAncho()).append("\n");
            detalles.append("Técnica: ").append(p.getTecnica()).append("\n");
            detalles.append("Lienzo: ").append(p.getLienzo()).append("\n");
            detalles.append("Estilo: ").append(p.getEstilo()).append("\n");
        } else if (pieza instanceof Escultura) {
            Escultura e = (Escultura) pieza;
            detalles.append("Alto: ").append(e.getAlto()).append("\n");
            detalles.append("Ancho: ").append(e.getAncho()).append("\n");
            detalles.append("Profundidad: ").append(e.getProfundidad()).append("\n");
            detalles.append("Materiales: ").append(e.getMateriales()).append("\n");
            detalles.append("Peso: ").append(e.getPeso()).append("\n");
            detalles.append("Necesita Electricidad: ").append(e.isNecesitaElectricidad()).append("\n");
            detalles.append("Detalles de Instalación: ").append(e.getDetallesInstalacion()).append("\n");
        } else if (pieza instanceof Fotografia) {
            Fotografia f = (Fotografia) pieza;
            detalles.append("Alto: ").append(f.getAlto()).append("\n");
            detalles.append("Ancho: ").append(f.getAncho()).append("\n");
            detalles.append("Formato: ").append(f.getFormato()).append("\n");
            detalles.append("Técnica: ").append(f.getTecnica()).append("\n");
            detalles.append("Resolución: ").append(f.getResolucion()).append("\n");
        } else if (pieza instanceof Impresion) {
            Impresion imp = (Impresion) pieza;
            detalles.append("Alto: ").append(imp.getAlto()).append("\n");
            detalles.append("Ancho: ").append(imp.getAncho()).append("\n");
            detalles.append("Tipo de Impresión: ").append(imp.getTipoImpresion()).append("\n");
            detalles.append("Tamaño: ").append(imp.getTamano()).append("\n");
            detalles.append("Calidad: ").append(imp.getCalidad()).append("\n");
        } else if (pieza instanceof Video) {
            Video v = (Video) pieza;
            detalles.append("Alto: ").append(v.getAlto()).append("\n");
            detalles.append("Ancho: ").append(v.getAncho()).append("\n");
            detalles.append("Formato: ").append(v.getFormato()).append("\n");
            detalles.append("Duración: ").append(v.getDuracion()).append("\n");
            detalles.append("Calidad: ").append(v.getCalidad()).append("\n");
        }

        JOptionPane.showMessageDialog(this, detalles.toString(), "Detalles de la Pieza", JOptionPane.INFORMATION_MESSAGE);
        return pieza;
    }
    
    private void consultarInventario() {
        JDialog dialog = new JDialog(this, "Piezas en el Inventario", true);
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> list = new JList<>(listModel);

        for (Map.Entry<String, Pieza> entry : galeria.getInventario().entrySet()) {
            String titulo = entry.getValue().getTitulo();
            String tipo = entry.getValue().getTipo();
            listModel.addElement(titulo + " (" + tipo + ")");
        }

        JScrollPane scrollPane = new JScrollPane(list);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        panel.add(btnCerrar, BorderLayout.SOUTH);
        dialog.add(panel);
        dialog.setVisible(true);
    }

}
