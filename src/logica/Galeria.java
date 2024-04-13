package logica;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import exceptions.PiezaNoDisponibleException;
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
    private Map<String, Double> pagos;
    private List<Verificacion> verificaciones;//Hace referencia a las solicitudes pendientes de compra.
    // Constructor
    public Galeria() {
    	inventario = new HashMap<>();
        usuarios = new HashMap<String, Usuario>();
        propietarios = new HashMap<>();
        compradores = new HashMap<>();
        pagos = new HashMap<>();
        verificaciones = new ArrayList<Verificacion>();
    }

    public Map<String, Usuario> getUsuarios() {
		return usuarios;
	}

	public List<Verificacion> getVerificaciones() {
		return verificaciones;
	}

	// Métodos para el inventario de piezas
    public void agregarPieza() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        try {
            // Solicitar datos comunes a todas las piezas
            System.out.println("Ingrese el título de la pieza:");
            String titulo = br.readLine();

            System.out.println("Ingrese el autor de la pieza:");
            String autor = br.readLine();

            System.out.println("Ingrese el año de creación de la pieza:");
            String anioCreacion = br.readLine();

            System.out.println("Ingrese el lugar de creación de la pieza:");
            String lugarCreacion = br.readLine();
            
            System.out.println("Ingrese si la pieza se va a encontrar en exhibicion (True/False):");
            boolean exhibicion = Boolean.parseBoolean(br.readLine());

            System.out.println("Ingrese si la pieza tiene un valor fijo (True/False):");
            boolean valorfijo = Boolean.parseBoolean(br.readLine());
            
            System.out.println("Ingrese el valor de la pieza:");
            double valor = Double.parseDouble(br.readLine());
            
            System.out.println("Ingrese el estado de la pieza:");
            String estado = br.readLine();
            
            System.out.println("Ingrese el alto de la piaza (m):");
            double alto = Double.parseDouble(br.readLine());
            
            System.out.println("Ingrese el ancho de la piaza (m):");
            double ancho = Double.parseDouble(br.readLine());
            
            
            // Solicitar datos específicos según el tipo de pieza
            System.out.println("Ingrese el tipo de pieza (Pintura, Escultura, Fotografia, Impresion, Video):");
            String tipoPieza = br.readLine();

            switch (tipoPieza.toLowerCase()) {
                case "pintura":
                    agregarPintura(br, titulo, autor, anioCreacion, lugarCreacion, exhibicion, valor, valorfijo, estado, alto, ancho);
                    break;
                case "escultura":
                    agregarEscultura(br, titulo, autor, anioCreacion, lugarCreacion, exhibicion, valor, valorfijo,estado, alto, ancho);
                    break;
                case "fotografia":
                    agregarFotografia(br, titulo, autor, anioCreacion, lugarCreacion, exhibicion, valor, valorfijo, estado, alto, ancho);
                    break;
                case "impresion":
                    agregarImpresion(br, titulo, autor, anioCreacion, lugarCreacion, exhibicion, valor, valorfijo, estado, alto, ancho);
                    break;
                case "video":
                    agregarVideo(br, titulo, autor, anioCreacion, lugarCreacion, exhibicion, valor, valorfijo, estado, alto, ancho);
                    break;
                default:
                    System.out.println("Tipo de pieza no válido.");
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    // Métodos para agregar cada tipo de pieza
    private void agregarPintura(BufferedReader br, String titulo, String autor, String anioCreacion, String lugarCreacion, boolean exhibicion, double valor, boolean valorfijo, String estado, double alto, double ancho) throws IOException {
        // Solicitar datos específicos para pinturas
        System.out.println("Ingrese la técnica de la pintura:");
        String tecnica = br.readLine();

        System.out.println("Ingrese el lienzo de la pintura:");
        String lienzo = br.readLine();

        System.out.println("Ingrese el estilo de la pintura:");
        String estilo = br.readLine();

        // Aquí puedes crear el objeto Pintura y agregarlo a la galería
        Pintura pintura = new Pintura(titulo, titulo, autor, anioCreacion, lugarCreacion, exhibicion, valor, valorfijo,estado, alto, ancho, tecnica, lienzo, estilo);
        // Agregar la pintura a la galería
        inventario.put(pintura.getId(), pintura);
        historial.put(pintura.getId(), pintura);
    }

    private void agregarEscultura(BufferedReader br, String titulo, String autor, String anioCreacion, String lugarCreacion, boolean exhibicion, double valor, boolean valorfijo,String estado, double alto, double ancho) throws IOException {
        // Solicitar datos específicos para esculturas

        System.out.println("Ingrese la profundidad de la escultura (m):");
        double profundidad = Double.parseDouble(br.readLine());

        System.out.println("Ingrese el material de la escultura:");
        String materiales = br.readLine();

        System.out.println("Ingrese el peso de la escultura:");
        double peso = Double.parseDouble(br.readLine());

        System.out.println("¿Necesita electricidad? (true/false):");
        boolean necesitaElectricidad = Boolean.parseBoolean(br.readLine());

        System.out.println("Ingrese los detalles de instalación de la escultura:");
        String detallesInstalacion = br.readLine();

        // Aquí puedes crear el objeto Escultura y agregarlo a la galería
        Escultura escultura = new Escultura(titulo, titulo, autor, anioCreacion, lugarCreacion, exhibicion, valor, valorfijo, estado, alto, ancho, profundidad, materiales, peso, necesitaElectricidad, detallesInstalacion);
        // Agregar la escultura a la galería
        inventario.put(escultura.getId(), escultura);
        historial.put(escultura.getId(), escultura);
    }

    private void agregarFotografia(BufferedReader br, String titulo, String autor, String anioCreacion, String lugarCreacion, boolean exhibicion, double valor, boolean valorfijo, String estado, double alto, double ancho) throws IOException {
        // Solicitar datos específicos para fotografías
        System.out.println("Ingrese el formato de la fotografía:");
        String formato = br.readLine();

        System.out.println("Ingrese la técnica de la fotografía:");
        String tecnica = br.readLine();

        System.out.println("Ingrese la resolución de la fotografía:");
        double resolucion = Double.parseDouble(br.readLine());

        // Aquí puedes crear el objeto Fotografia y agregarlo a la galería
        Fotografia fotografia = new Fotografia(titulo, titulo, autor, anioCreacion, lugarCreacion, exhibicion, valor, valorfijo, estado, alto, ancho,formato, tecnica, resolucion);
        // Agregar la fotografía a la galería
        inventario.put(fotografia.getId(), fotografia);
        historial.put(fotografia.getId(), fotografia);
    }

    private void agregarImpresion(BufferedReader br, String titulo, String autor, String anioCreacion, String lugarCreacion, boolean exhibicion, double valor, boolean valorfijo,String estado, double alto, double ancho) throws IOException {
        // Solicitar datos específicos para impresiones
        System.out.println("Ingrese el tipo de impresión:");
        String tipoImpresion = br.readLine();

        System.out.println("Ingrese el tamaño de la impresión:");
        String tamano = br.readLine();

        System.out.println("Ingrese la calidad de la impresión:");
        String calidad = br.readLine();

        // Aquí puedes crear el objeto Impresion y agregarlo a la galería
        Impresion impresion = new Impresion(titulo, titulo, autor, anioCreacion, lugarCreacion, exhibicion, valor, valorfijo, estado, alto, ancho,tipoImpresion, tamano, calidad);
     // Agregar la impresion a la galería
        inventario.put(impresion.getId(), impresion);
        historial.put(impresion.getId(), impresion);
    }

    private void agregarVideo(BufferedReader br, String titulo, String autor, String anioCreacion, String lugarCreacion, boolean exhibicion, double valor, boolean valorfijo,String estado, double alto, double ancho) throws IOException {
        // Solicitar datos específicos para videos
        System.out.println("Ingrese el formato del video:");
        String formato = br.readLine();

        System.out.println("Ingrese la duración del video:");
        String duracion = br.readLine();

        System.out.println("Ingrese la calidad del video:");
        String calidad = br.readLine();

        // Aquí puedes crear el objeto Video y agregarlo a la galería
        Video video = new Video(titulo, titulo, autor, anioCreacion, lugarCreacion, exhibicion, valor, valorfijo,estado, alto, ancho, formato, duracion, calidad);
        // Agregar el video a la galería
        inventario.put(video.getId(), video);
        historial.put(video.getId(), video);
    }
    
    public void eliminarPieza(Pieza pieza) {
    	inventario.remove(pieza.getId());
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
    		Factura factura = new Factura(medioPago,pieza.getValor(), comprador);
    		comprador.getCompras().add(factura);
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

    public void realizarPago(Usuario comprador, double monto) {
        // Lógica para registrar un pago
    }
}

