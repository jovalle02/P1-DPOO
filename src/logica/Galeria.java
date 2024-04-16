package logica;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import exceptions.PiezaNoDisponibleException;
import persistencia.CentralPersistencia;
import piezas.Escultura;
import piezas.Fotografia;
import piezas.Impresion;
import piezas.Pieza;
import piezas.Pintura;
import piezas.Video;
import usuarios.Usuario;
import usuarios.UsuarioComun;

public class Galeria {
    // Atributos
    private Map<String, Pieza>  inventario;
    private Map<String, Pieza>  historial;
    private Map<String, Usuario> usuarios;
    private Map<String, Usuario> propietarios;
    private Map<String, Usuario> compradores;
    private Map<String, Factura> historialDeCompras;
    private List<Verificacion> verificaciones;//Hace referencia a las solicitudes pendientes de compra.
    // Constructor
    public Galeria() {
    	inventario = new HashMap<String, Pieza>();
    	historial = new HashMap<String, Pieza>();
        usuarios = new HashMap<String, Usuario>();
        propietarios = new HashMap<>();
        compradores = new HashMap<>();
        historialDeCompras = new HashMap<String, Factura>();
        verificaciones = new ArrayList<Verificacion>();
    }
    
    public Map<String, Pieza> getHistorial() {
		return historial;
	}

	public void cargarGaleria() {
    	CentralPersistencia.cargarGaleria(this);
    }
	public void salvarGaleria () {
		CentralPersistencia.salvarGaleria(this);
	}
    public void agregarPiezaJSON(Pieza pieza, Map<String, Pieza> mapa) {
    	mapa.put(pieza.getId(), pieza);
    }
    public Map<String, Usuario> getUsuarios() {
		return usuarios;
	}

	public List<Verificacion> getVerificaciones() {
		return verificaciones;
	}

    public void setVerificaciones(List<Verificacion> verificaciones) {
		this.verificaciones = verificaciones;
	}

	// Métodos para agregar cada tipo de pieza
    public void agregarPintura(String titulo, String autor, String anioCreacion, String lugarCreacion, boolean exhibicion, double valor, boolean valorfijo, String estado, double alto, double ancho, String tecnica, String lienzo, String estilo) throws IOException {
        Pintura pintura = new Pintura(titulo, titulo, autor, anioCreacion, lugarCreacion, exhibicion, valor, valorfijo,estado, alto, ancho, tecnica, lienzo, estilo);
        inventario.put(pintura.getId(), pintura);
        historial.put(pintura.getId(), pintura);
    }

    public void agregarEscultura(String titulo, String autor, String anioCreacion, String lugarCreacion, boolean exhibicion, double valor, boolean valorfijo,String estado, double alto, double ancho, double profundidad, String materiales, Double peso, boolean necesitaElectricidad, String detallesInstalacion) throws IOException {
        // Aquí puedes crear el objeto Escultura y agregarlo a la galería
        Escultura escultura = new Escultura(titulo, titulo, autor, anioCreacion, lugarCreacion, exhibicion, valor, valorfijo, estado, alto, ancho, profundidad, materiales, peso, necesitaElectricidad, detallesInstalacion);
        // Agregar la escultura a la galería
        inventario.put(escultura.getId(), escultura);
        historial.put(escultura.getId(), escultura);
    }

    public void agregarFotografia(String titulo, String autor, String anioCreacion, String lugarCreacion, boolean exhibicion, double valor, boolean valorfijo, String estado, double alto, double ancho, String formato, String tecnica, double resolucion) throws IOException {
        Fotografia fotografia = new Fotografia(titulo, titulo, autor, anioCreacion, lugarCreacion, exhibicion, valor, valorfijo, estado, alto, ancho,formato, tecnica, resolucion);
        inventario.put(fotografia.getId(), fotografia);
        historial.put(fotografia.getId(), fotografia);
    }

    public void agregarImpresion(String titulo, String autor, String anioCreacion, String lugarCreacion, boolean exhibicion, double valor, boolean valorfijo,String estado, double alto, double ancho, String tipoImpresion, String tamano, String calidad) throws IOException {
        // Aquí puedes crear el objeto Impresion y agregarlo a la galería
        Impresion impresion = new Impresion(titulo, titulo, autor, anioCreacion, lugarCreacion, exhibicion, valor, valorfijo, estado, alto, ancho,tipoImpresion, tamano, calidad);
     // Agregar la impresion a la galería
        inventario.put(impresion.getId(), impresion);
        historial.put(impresion.getId(), impresion);
    }

    public void agregarVideo(String titulo, String autor, String anioCreacion, String lugarCreacion, boolean exhibicion, double valor, boolean valorfijo,String estado, double alto, double ancho, String formato, String duracion, String calidad) throws IOException {
        // Aquí puedes crear el objeto Video y agregarlo a la galería
        Video video = new Video(titulo, titulo, autor, anioCreacion, lugarCreacion, exhibicion, valor, valorfijo,estado, alto, ancho, formato, duracion, calidad);
        // Agregar el video a la galería
        inventario.put(video.getId(), video);
        historial.put(video.getId(), video);
    }
    
    public Pieza getPieza(String name) {
		if (inventario.containsKey(name)) {
		    Pieza pieza = inventario.get(name);
		    return pieza;
		} else {
		    System.out.println("La pieza " + name + "no existe dentro del inventario.");
		    return null;
		}
    }
    
    public void eliminarPieza(Pieza pieza) {
    	inventario.remove(pieza.getId());
    }
    
    public void consultarInventarioParaCompra() {
        System.out.println("Piezas en el inventario:");
        for (Map.Entry<String, Pieza> entry : inventario.entrySet()) {
        	if (entry.getValue().getIsFijo() == true) {
                String titulo = entry.getValue().getTitulo();
                String tipo = entry.getValue().getTipo();
                System.out.println("- " + titulo + " (" + tipo + ")");
        	}
        }
    }
    
    public void consultarInventario() {
        System.out.println("Piezas en el inventario:");
        for (Map.Entry<String, Pieza> entry : inventario.entrySet()) {
            String titulo = entry.getValue().getTitulo();
            String tipo = entry.getValue().getTipo();
            System.out.println("- " + titulo + " (" + tipo + ")");
        }
    }


    
    // Métodos para la compra y subasta de piezas
    public void realizarCompra(UsuarioComun comprador, Pieza pieza) throws PiezaNoDisponibleException {
    	if (pieza.isDisponible()) {
        pieza.setDisponible(false); //Pone la pieza como no disponible
        crearVerificacionCompra(comprador, pieza);
    	}else {
    		throw new PiezaNoDisponibleException(pieza.getId());
    	}
    }
    
    //Crea un nuevo caso de verificación para que un administrador pueda aprobar o rechazar la compra.
    private void crearVerificacionCompra(UsuarioComun comprador, Pieza pieza) {
    	Verificacion verificacion = new Verificacion(comprador, pieza);
    	verificaciones.add(verificacion);
    }
    
    //Revisa todos los
    
    
    //Dado un caso pendiente de verificacion y la respuesta dada, se añadirá la pieza al comprador o, por el contrario, se pondrá de nuevo en venta.
    public void confirmarVenta(Verificacion verificacion, boolean aprobar, String medioPago) {
    	UsuarioComun comprador = verificacion.getUsuario();
    	Pieza pieza = verificacion.getPieza();
    	if(aprobar) {
    		pieza.setVendida(true);
    		comprador.getHistorial().add(pieza);
    		comprador.getPiezasActuales().add(pieza);
    		String facturaId = UUID.randomUUID().toString();
    		Factura factura = new Factura(medioPago,pieza.getValor(), comprador, facturaId);
    		comprador.getCompras().add(factura);
    		historialDeCompras.put(factura.getComprador().getId(), factura);
    		propietarios.put(comprador.getId(), comprador);
    		compradores.put(comprador.getId(), comprador);
    	}else {
    		pieza.setDisponible(true);
    	}
    }

    
    public void iniciarSubasta(Pieza pieza, double valorInicial, double valorMinimo) {
        // Lógica para iniciar una subasta
    }

    // Métodos para propietarios, compradores y pagos
    public void agregarPropietario(Usuario propietario) {
        propietarios.put(propietario.getId(), propietario);
    }

    public void agregarComprador(Usuario comprador) {
        compradores.put(comprador.getId(), comprador);
    }

    public void realizarPago(Factura factura) {
    	historialDeCompras.put(factura.getComprador().getId(), factura);
    }

	public Map<String, Pieza> getInventario() {
		return inventario;
	}
}

