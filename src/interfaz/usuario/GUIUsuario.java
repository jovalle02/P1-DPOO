package interfaz.usuario;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import exceptions.PiezaNoDisponibleException;
import logica.Factura;
import logica.Galeria;
import logica.Subasta;
import piezas.Escultura;
import piezas.Fotografia;
import piezas.Impresion;
import piezas.Pieza;
import piezas.Pintura;
import piezas.Video;
import usuarios.Usuario;
import usuarios.UsuarioComun;

public class GUIUsuario extends JFrame {
    private Galeria galeria;
    private Usuario usuario;

    public GUIUsuario(Galeria galeria, Usuario usuario) {
        this.galeria = galeria;
        this.usuario = usuario;
        initialize();
    }

    private void initialize() {
        setTitle("Galería y Casa de Subastas (USUARIO)");
        setSize(800, 600); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 1));

        JButton btnComprarPieza = new JButton("Comprar una Pieza");
        JButton btnRealizarOferta = new JButton("Realizar Oferta de Compra");
        JButton btnConsultarInventario = new JButton("Consultar el Inventario de la galería");
        JButton btnSubastas = new JButton("Subastas");
        JButton btnVerHistorialArtistas = new JButton("Ver Historial Artistas");
        JButton btnConsultarHistorialPieza = new JButton("Consultar historial de una pieza");
        JButton btnSalir = new JButton("Salir");

        btnComprarPieza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
					comprarPieza();
				} catch (PiezaNoDisponibleException e1) {
					e1.printStackTrace();
				}
            }
        });

        btnRealizarOferta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarOferta();
            }
        });

        btnConsultarInventario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                consultarInventario();
            }
        });

        btnSubastas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                subastas();
            }
        });

        btnVerHistorialArtistas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verHistorialArtistas();
            }
        });

        btnConsultarHistorialPieza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                consultarHistorialPieza();
            }
        });

        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salir();
            }
        });

        panel.add(btnComprarPieza);
        panel.add(btnRealizarOferta);
        panel.add(btnConsultarInventario);
        panel.add(btnSubastas);
        panel.add(btnVerHistorialArtistas);
        panel.add(btnConsultarHistorialPieza);
        panel.add(btnSalir);

        add(panel);
    }

    private void comprarPieza() throws PiezaNoDisponibleException {
        consultarInventarioParaCompra();
        JOptionPane.showMessageDialog(this, "Recuerde, en esta lista solo aparecen aquellas piezas cuyo valor es fijo.\nSi no encuentra la pieza deseada deberá esperar o consultar para abrir una subasta.");

        String nombrePieza = JOptionPane.showInputDialog(this, "Digite el nombre de la pieza en la cual está interesado:");
        if (nombrePieza == null || nombrePieza.isEmpty()) {
            return;
        }

        Pieza pieza = imprimirDetallesPieza(nombrePieza);
        if (pieza == null) {
            JOptionPane.showMessageDialog(this, "La pieza no se encuentra en el inventario.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "¿Desea comprar esta pieza?", "Confirmación de Compra", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (usuario instanceof UsuarioComun) {
                UsuarioComun u = (UsuarioComun) usuario;
                if (u.getTopeDeCompra() >= pieza.getValor()) {
                    JOptionPane.showMessageDialog(this, "Usted cumple con el monto mínimo necesario para comprar la pieza.\nPor favor espere mientras un administrador confirma la compra, por ahora la pieza se encuentra bloqueada.");
                    galeria.realizarCompra(u, pieza);
                } else {
                    JOptionPane.showMessageDialog(this, "Usted no cumple con el monto mínimo necesario para comprar la pieza.\nPor favor consulte con un administrador para aumentar su tope máximo.");
                }
            }
        }
    }
    
    private void consultarInventarioParaCompra() {
        JDialog dialog = new JDialog(this, "Piezas en el Inventario", true);
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> list = new JList<>(listModel);

        for (Map.Entry<String, Pieza> entry : galeria.getInventario().entrySet()) {
            if (entry.getValue().getIsFijo() && entry.getValue().isDisponible()) {
                String titulo = entry.getValue().getTitulo();
                String tipo = entry.getValue().getTipo();
                listModel.addElement(titulo + " (" + tipo + ")");
            }
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

    private void realizarOferta() {
        // Implementation for making an offer
        JOptionPane.showMessageDialog(this, "Realizar Oferta de Compra");
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

    private void subastas() {
        JDialog dialog = new JDialog(this, "Subastas", true);
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));

        JButton btnConsultarSubastas = new JButton("Consultar Subastas");
        JButton btnRealizarOfertaSubasta = new JButton("Realizar Oferta en Subasta");
        JButton btnVolver = new JButton("Volver");

        btnConsultarSubastas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                consultarSubastas();
            }
        });

        btnRealizarOfertaSubasta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarOfertaSubasta();
            }
        });

        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        panel.add(btnConsultarSubastas);
        panel.add(btnRealizarOfertaSubasta);
        panel.add(btnVolver);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void consultarSubastas() {
        List<Subasta> subastas = galeria.getSubastas();
        if (subastas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay subastas disponibles.", "Subastas", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < subastas.size(); i++) {
            Subasta s = subastas.get(i);
            Pieza p = s.getPiezaSubastada();
            sb.append("Número de subasta: ").append(i + 1).append("\n");
            sb.append("Título: ").append(p.getTitulo()).append("\n");
            sb.append("Estado subasta: ").append(s.isActiva() ? "Activa" : "Terminada").append("\n");
            sb.append("Valor mínimo para ofertar: ").append(s.getMayorOfrecido()).append("\n\n");
        }

        JOptionPane.showMessageDialog(this, sb.toString(), "Subastas", JOptionPane.INFORMATION_MESSAGE);
    }

    private void realizarOfertaSubasta() {
        String idSubasta = JOptionPane.showInputDialog(this, "Ingrese el número de la subasta:");
        if (idSubasta == null || idSubasta.isEmpty()) {
            return;
        }

        int subastaIndex;
        try {
            subastaIndex = Integer.parseInt(idSubasta) - 1;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Número de subasta inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<Subasta> subastas = galeria.getSubastas();
        if (subastaIndex < 0 || subastaIndex >= subastas.size()) {
            JOptionPane.showMessageDialog(this, "Número de subasta no válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Subasta s = subastas.get(subastaIndex);
        String valorOfertaStr = JOptionPane.showInputDialog(this, "Ingrese el valor de la oferta:");
        if (valorOfertaStr == null || valorOfertaStr.isEmpty()) {
            return;
        }

        float valorOferta;
        try {
            valorOferta = Float.parseFloat(valorOfertaStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Valor de oferta inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            s.subastar((UsuarioComun) this.usuario, valorOferta);
            JOptionPane.showMessageDialog(this, "Oferta realizada exitosamente.", "Subasta", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al realizar la oferta.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void verHistorialArtistas() {
        // Implementation for viewing artist history
        JOptionPane.showMessageDialog(this, "Ver Historial Artistas");
    }

    private void consultarHistorialPieza() {
        // Implementation for checking piece history
        JOptionPane.showMessageDialog(this, "Consultar historial de una pieza");
        consultarHistorialPiezaOpcion();
    }

    private void salir() {
        int response = JOptionPane.showConfirmDialog(this, "¿Está seguro que desea salir?", "Salir", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            try {
                galeria.salvarGaleria();
                this.dispose();
            } catch (Exception e) {
                e.printStackTrace();
            }
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
}
