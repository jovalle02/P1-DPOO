package interfaz.usuario;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
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

public class GUIAdmin extends JFrame {
	private Galeria galeria;
    private Usuario usuario;

    public GUIAdmin(Galeria galeria, Usuario usuario) {
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
        panel.setLayout(new GridLayout(12, 1));

        JButton btnAddPieza = new JButton("Añadir una Pieza");
        JButton btnRemovePieza = new JButton("Eliminar una Pieza");
        JButton btnSellPieza = new JButton("Vender una pieza");
        JButton btnConsultarInventario = new JButton("Consultar inventario de la galeria");
        JButton btnModificarTope = new JButton("Modificar tope de compra de un usuario");
        JButton btnVerificarCompra = new JButton("Verificar compra de una Pieza");
        JButton btnConsultarHistorialPieza = new JButton("Consultar historial de una pieza");
        JButton btnVerHistorialArtistas = new JButton("Ver Historial Artistas");
        JButton btnConsultarClientes = new JButton("Consultar Lista Clientes");
        JButton btnConsultarHistorialCliente = new JButton("Consultar Historia Cliente");
        JButton btnSalir = new JButton("Salir");
        JButton btnVisualizarVentas = new JButton("Visualizar Ventas");

        btnAddPieza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarPieza();
            }
        });

        btnRemovePieza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarPieza();
            }
        });

        btnSellPieza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                venderPieza();
            }
        });

        btnConsultarInventario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                consultarInventario();
            }
        });

        btnModificarTope.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarTope();
            }
        });

        btnVerificarCompra.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verificarCompra();
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

        btnVisualizarVentas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    visualizarVentas();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        panel.add(btnAddPieza);
        panel.add(btnRemovePieza);
        panel.add(btnSellPieza);
        panel.add(btnConsultarInventario);
        panel.add(btnModificarTope);
        panel.add(btnVerificarCompra);
        panel.add(btnConsultarHistorialPieza);
        panel.add(btnVerHistorialArtistas);
        panel.add(btnConsultarClientes);
        panel.add(btnConsultarHistorialCliente);
        panel.add(btnSalir);
        panel.add(btnVisualizarVentas);

        add(panel);
    }

    private void visualizarVentas() throws Exception {
        Map<String, Integer> ventasPorDia = new HashMap<>();

        for (Factura factura : galeria.getHistorialDeCompras().values()) {
            String fecha = factura.getFecha();
            ventasPorDia.put(fecha, ventasPorDia.getOrDefault(fecha, 0) + 1);
        }

        JFrame frame = new JFrame("Visualización de Ventas");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(850, 300);
        frame.add(new VentasHeatmapPanel(galeria));
        frame.pack();
        frame.setVisible(true);
    }

    private void agregarPieza() {
        JDialog dialog = new JDialog(this, "Añadir una Pieza", true);
        dialog.setSize(400, 600);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(14, 2));

        JTextField txtTitulo = new JTextField();
        JTextField txtAutor = new JTextField();
        JTextField txtAnio = new JTextField();
        JTextField txtLugar = new JTextField();
        JCheckBox chkExhibicion = new JCheckBox();
        JCheckBox chkValorFijo = new JCheckBox();
        JTextField txtValor = new JTextField();
        JTextField txtEstado = new JTextField();
        JTextField txtAlto = new JTextField();
        JTextField txtAncho = new JTextField();
        JComboBox<String> cmbTipoPieza = new JComboBox<>(new String[]{"Pintura", "Escultura", "Fotografia", "Impresion", "Video"});

        panel.add(new JLabel("Título:"));
        panel.add(txtTitulo);
        panel.add(new JLabel("Autor:"));
        panel.add(txtAutor);
        panel.add(new JLabel("Año de creación:"));
        panel.add(txtAnio);
        panel.add(new JLabel("Lugar de creación:"));
        panel.add(txtLugar);
        panel.add(new JLabel("En exhibición:"));
        panel.add(chkExhibicion);
        panel.add(new JLabel("Valor fijo:"));
        panel.add(chkValorFijo);
        panel.add(new JLabel("Valor:"));
        panel.add(txtValor);
        panel.add(new JLabel("Estado:"));
        panel.add(txtEstado);
        panel.add(new JLabel("Alto (m):"));
        panel.add(txtAlto);
        panel.add(new JLabel("Ancho (m):"));
        panel.add(txtAncho);
        panel.add(new JLabel("Tipo de pieza:"));
        panel.add(cmbTipoPieza);

        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");

        panel.add(btnGuardar);
        panel.add(btnCancelar);

        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = txtTitulo.getText();
                    String autor = txtAutor.getText();
                    String anio = txtAnio.getText();
                    String lugar = txtLugar.getText();
                    boolean exhibicion = chkExhibicion.isSelected();
                    boolean valorfijo = chkValorFijo.isSelected();
                    double valor = Double.parseDouble(txtValor.getText());
                    String estado = txtEstado.getText();
                    double alto = Double.parseDouble(txtAlto.getText());
                    double ancho = Double.parseDouble(txtAncho.getText());

                    String tipoPieza = (String) cmbTipoPieza.getSelectedItem();

                    switch (tipoPieza.toLowerCase()) {
                        case "pintura":
                            String tecnica = JOptionPane.showInputDialog(dialog, "Ingrese la técnica de la pintura:");
                            String lienzo = JOptionPane.showInputDialog(dialog, "Ingrese el lienzo de la pintura:");
                            String estilo = JOptionPane.showInputDialog(dialog, "Ingrese el estilo de la pintura:");
                            galeria.agregarPintura(name, autor, anio, lugar, exhibicion, valor, valorfijo, estado, alto, ancho, tecnica, lienzo, estilo);
                            break;
                        case "escultura":
                            double profundidad = Double.parseDouble(JOptionPane.showInputDialog(dialog, "Ingrese la profundidad de la escultura (m):"));
                            double peso = Double.parseDouble(JOptionPane.showInputDialog(dialog, "Ingrese el peso de la estructura (kg):"));
                            String materiales = JOptionPane.showInputDialog(dialog, "Ingrese el material de la escultura:");
                            boolean electricidad = JOptionPane.showConfirmDialog(dialog, "¿Necesita electricidad?", "Electricidad", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
                            String detallesInstalacion = JOptionPane.showInputDialog(dialog, "Ingrese los detalles de instalación de la escultura:");
                            galeria.agregarEscultura(name, autor, anio, lugar, exhibicion, valor, valorfijo, estado, alto, ancho, profundidad, materiales, peso, electricidad, detallesInstalacion);
                            break;
                        case "fotografia":
                            String formato = JOptionPane.showInputDialog(dialog, "Ingrese el formato de la fotografía:");
                            String tecnicafotografia = JOptionPane.showInputDialog(dialog, "Ingrese la técnica de la fotografía:");
                            double resolucionfotografia = Double.parseDouble(JOptionPane.showInputDialog(dialog, "Ingrese la resolución de la fotografía:"));
                            galeria.agregarFotografia(name, autor, anio, lugar, exhibicion, valor, valorfijo, estado, alto, ancho, formato, tecnicafotografia, resolucionfotografia);
                            break;
                        case "impresion":
                            String calidad = JOptionPane.showInputDialog(dialog, "Ingrese la calidad de la impresión:");
                            String tamanoimpresion = JOptionPane.showInputDialog(dialog, "Ingrese el tamaño de la impresión:");
                            String tipo = JOptionPane.showInputDialog(dialog, "Ingrese el tipo de impresión:");
                            galeria.agregarImpresion(name, autor, anio, lugar, exhibicion, valor, valorfijo, estado, alto, ancho, tipo, tamanoimpresion, calidad);
                            break;
                        case "video":
                            String formatovideo = JOptionPane.showInputDialog(dialog, "Ingrese el formato del video:");
                            String duracion = JOptionPane.showInputDialog(dialog, "Ingrese la duracion del video:");
                            String calidadvideo = JOptionPane.showInputDialog(dialog, "Ingrese la calidad del video:");
                            galeria.agregarVideo(name, autor, anio, lugar, exhibicion, valor, valorfijo, estado, alto, ancho, formatovideo, duracion, calidadvideo);
                            break;
                        default:
                            JOptionPane.showMessageDialog(dialog, "Tipo de pieza no válido.");
                    }
                    dialog.dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog, "Por favor, ingrese valores numéricos válidos.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(dialog, "Ocurrió un error al agregar la pieza.", "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        dialog.add(panel);
        dialog.setVisible(true);
    }


    private void eliminarPieza() {
        String name = JOptionPane.showInputDialog(this, "Ingrese el nombre de la pieza:");
        Pieza pieza = galeria.getPieza(name);
        if (pieza != null) {
            galeria.eliminarPieza(pieza);
            JOptionPane.showMessageDialog(this, "La pieza se ha eliminado exitosamente!");
        } else {
            JOptionPane.showMessageDialog(this, "La pieza no se encuentra en el inventario.");
        }
    }

    private void venderPieza() {
        galeria.consultarInventario();
        JOptionPane.showMessageDialog(this, "Inventario consultado para venta.");
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

    private void modificarTope() {
        String id = JOptionPane.showInputDialog(this, "Cual es el id del usuario?");
        String topeStr = JOptionPane.showInputDialog(this, "Ingrese el nuevo tope");
        double nuevoTope = Double.parseDouble(topeStr);
        galeria.manipularTopeDeCompra(id, nuevoTope);
        JOptionPane.showMessageDialog(this, "El tope de compra se ha modificado exitosamente!");
    }

    private void verificarCompra() {
        Verificacion verificacion = imprimirVerificaciones();
        if (verificacion != null) {
            boolean ofertar = JOptionPane.showConfirmDialog(this, "¿Desea confirmar esta verificación?", "Confirmar Verificación", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
            if (ofertar) {
                String metodo = JOptionPane.showInputDialog(this, "¿Cuál es el método de pago? (Tarjeta o efectivo)");
                if (metodo != null && metodo.equalsIgnoreCase("tarjeta")) {
                    JOptionPane.showMessageDialog(this, "Seleccione la pasarela de pago:");
                    TiposPasarela[] pasarelas = TiposPasarela.values();
                    StringBuilder pasarelaOptions = new StringBuilder();
                    int k = 1;
                    for (TiposPasarela pasarela : pasarelas) {
                        pasarelaOptions.append(k).append(". ").append(pasarela).append("\n");
                        k++;
                    }
                    JOptionPane.showMessageDialog(this, pasarelaOptions.toString());
                    
                    String pasaString = JOptionPane.showInputDialog(this, "Escriba el nombre de la pasarela de pago");
                    try {
                        TiposPasarela pasa = TiposPasarela.fromString(pasaString);
                        String idTarjeta = JOptionPane.showInputDialog(this, "Ingrese el número de la tarjeta: ");
                        Usuario usuarioVerificacion = verificacion.getUsuario();
                        boolean approved = galeria.pagoPasarela(pasa, usuarioVerificacion.getNombre(), usuarioVerificacion.getApellido(), usuarioVerificacion.getEmail(), idTarjeta, verificacion.getPieza().getValor());
                        if (!approved) {
                            JOptionPane.showMessageDialog(this, "No se pudo completar la transacción.");
                            return;
                        }
                        galeria.confirmarVenta(verificacion, ofertar, metodo);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                } else {
                    galeria.confirmarVenta(verificacion, ofertar, metodo);
                }
                JOptionPane.showMessageDialog(this, "Verificación confirmada exitosamente.");
            } else {
                galeria.confirmarVenta(verificacion, ofertar, null);
                JOptionPane.showMessageDialog(this, "Verificación rechazada exitosamente.");
            }
        }
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

    private Verificacion imprimirVerificaciones() {
        List<Verificacion> verificaciones = galeria.getVerificaciones();
        if (verificaciones.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay verificaciones pendientes.");
            return null;
        }

        JDialog dialog = new JDialog(this, "Verificaciones Pendientes", true);
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> list = new JList<>(listModel);

        for (int i = 0; i < verificaciones.size(); i++) {
            Verificacion verificacion = verificaciones.get(i);
            UsuarioComun usuario = verificacion.getUsuario();
            Pieza pieza = verificacion.getPieza();
            listModel.addElement("Verificación " + (i + 1) + ": Usuario: " + usuario.getNombre() + " - Pieza: " + pieza.getTitulo() + " - Valor: " + pieza.getValor());
        }

        JScrollPane scrollPane = new JScrollPane(list);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton btnSeleccionar = new JButton("Seleccionar");
        btnSeleccionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int seleccion = list.getSelectedIndex();
                if (seleccion != -1) {
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Selección inválida. Intente de nuevo.");
                }
            }
        });

        panel.add(btnSeleccionar, BorderLayout.SOUTH);
        dialog.add(panel);
        dialog.setVisible(true);

        int seleccion = list.getSelectedIndex();
        if (seleccion < 0 || seleccion >= verificaciones.size()) {
            return null;
        }
        return verificaciones.get(seleccion);
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
    
}
