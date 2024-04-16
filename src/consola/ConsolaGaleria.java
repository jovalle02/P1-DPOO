package consola;

import java.io.IOException;
import java.util.Map;

import auth.Rol;
import consola.auth.ConsolaAuth;
import logica.Galeria;
import persistencia.CentralPersistencia;
import piezas.Escultura;
import piezas.Fotografia;
import piezas.Impresion;
import piezas.Pieza;
import piezas.Pintura;
import piezas.Video;
import usuarios.Usuario;
import usuarios.UsuarioComun;

public class ConsolaGaleria extends ConsolaBasica {
	private Galeria galeria;
	private boolean autenticado = false;
	private Usuario usuario;
	private CentralPersistencia persistencia;

	public void correrAplicacion() {

        galeria = new Galeria( );
        persistencia = new CentralPersistencia();

        persistencia.cargarPiezasDesdeJSON(galeria);
        if (isAutenticado()) {
			if (usuario.getRol().equals(Rol.ADMIN)) {
				menuAdministrador();
			} else if (usuario.getRol().equals(Rol.EMPLEADO)) {
				menuEmpleado();
			} else if (usuario.getRol().equals(Rol.COMUN)) {
				menuUsuario();
			}

        } else {
        	ConsolaAuth auth = new ConsolaAuth();
        	usuario = auth.iniciar();
			setAutenticado(true);
			correrAplicacion();
        }


	}

	private void menuAdministrador() {
		int opcion = mostrarMenu("Galeria y Casa de Subastas (ADMIN)", new String[]{"Añadir una Pieza", "Eliminar una Pieza", "Vender una pieza", "Consultar inventario de la galeria", "Salir"});

		switch (opcion) {
			case 1:
				System.out.println("Añadir una Pieza");
				agregarPieza();
				System.out.println("La pieza se ha añadido exitosamente!");
				break;
			case 2:
				System.out.println("Eliminar una Pieza");
				String name = pedirCadenaAlUsuario("Ingrese el nombre de la pieza:");
				Pieza pieza = galeria.getPieza(name);
				galeria.eliminarPieza(pieza);
				System.out.println("La pieza se ha eliminado exitosamente!");
				break;
			case 3:
				System.out.println("Consultar inventario de la galeria");
				galeria.consultarInventario();
				break;
			case 4:
				System.out.println("Vender una pieza");
				galeria.consultarInventario();
				break;
			case 5:
				System.out.println("Gracias por usar la Galería y Casa de Subastas");
				autenticado = false;
				Map<String, Pieza> inventario = galeria.getInventario();
				persistencia.salvarPiezas(inventario);
				correrAplicacion();
				break;
			default:
				System.out.println("Opción inválida. Por favor, seleccione una opción válida.");
		}
		menuAdministrador();
	}

	private void menuEmpleado() {
		int opcion = mostrarMenu("Galeria y Casa de Subastas (EMPLEADO)", new String[]{"Consultar Pieza", "Realizar Oferta de Compra", "Consultar Historial de Compras", "Salir"});

		switch (opcion) {
			case 1:
				System.out.println("Consultar Pieza");
				menuEmpleado();
				break;
			case 2:
				System.out.println("Realizar Oferta de Compra");
				menuEmpleado();
				break;
			case 3:
				System.out.println("Consultar Historial de Compras");
				menuEmpleado();
				break;
			case 4:
				System.out.println("Gracias por usar la Galería y Casa de Subastas");
				autenticado = false;
				correrAplicacion();
				break;
			default:
				System.out.println("Opción inválida. Por favor, seleccione una opción válida.");
		}
	}

	public static void main(String[] args) {
		ConsolaGaleria cg = new ConsolaGaleria();
		cg.correrAplicacion();
	}

	public boolean isAutenticado() {
		return autenticado;
	}

	public void setAutenticado(boolean autenticado) {
		this.autenticado = autenticado;
	}



	private void menuUsuario() {
		int opcion = mostrarMenu("Galeria y Casa de Subastas", new String[]{"Comprar una Pieza", "Realizar Oferta de Compra", "Consultar el Inventario de la galería.", "Salir"});

		switch (opcion) {
			case 1:
				System.out.println("Comprar una Pieza");
				galeria.consultarInventarioParaCompra();
				System.out.println("Recuerde, en esta lista solo aparecen aquellas piezas cuyo valor es fijo.");
				System.out.println("Si no encuentra la pieza deseada deberá esperar o consultar para abrir una subasta.");
				String nombrePieza = pedirCadenaAlUsuario("Digite el nombre de la pieza en la cual esta interesado:");
				Pieza pieza = imprimirDetallesPieza(nombrePieza);
				boolean ofertar = pedirConfirmacionAlUsuario("¿Desea comprar esta pieza?");
				
				if (ofertar) {
					if (usuario instanceof UsuarioComun) {
						UsuarioComun u = (UsuarioComun) usuario;
						if (u.getTopeDeCompra() >= pieza.getValor()) {
							System.out.println("Si puede comprarlo");
						} else {
							System.out.println("No puede comprarlo");
						}
					}
				}
				menuUsuario();
				break;
			case 2:
				System.out.println("Realizar Oferta de Compra");
				menuUsuario();
				break;
			case 3:
				System.out.println("Consultar Historial de Compras");
				menuUsuario();
				break;
			case 4:
				System.out.println("Gracias por usar la Galería y Casa de Subastas");
				autenticado = false;
				correrAplicacion();
				break;
			default:
				System.out.println("Opción inválida. Por favor, seleccione una opción válida.");
		}

	}

	
    private void agregarPieza() {

        try {
            String name = pedirCadenaAlUsuario("Ingrese el título de la pieza:");
            String autor = pedirCadenaAlUsuario("Ingrese el autor de la pieza:");
            String anio = pedirCadenaAlUsuario("Ingrese el año de creación de la pieza:");
            String lugar = pedirCadenaAlUsuario("Ingrese el lugar de creación de la pieza:");
            boolean exhibicion = pedirConfirmacionAlUsuario("Ingrese si la pieza se va a encontrar en exhibicion");
            boolean valorfijo = pedirConfirmacionAlUsuario("Ingrese si la pieza tiene un valor fijo");
            double valor = pedirNumeroAlUsuario("Ingrese el valor de la pieza");
            String estado = pedirCadenaAlUsuario("Ingrese el estado de la pieza:");
            double alto = pedirNumeroAlUsuario("Ingrese el alto de la pieza (m)");
            double ancho = pedirNumeroAlUsuario("Ingrese el ancho de la pieza (m)");       
            
            String tipoPieza = pedirCadenaAlUsuario("Ingrese el tipo de pieza (Pintura, Escultura, Fotografia, Impresion, Video)");

            switch (tipoPieza.toLowerCase()) {
                case "pintura":
                    String tecnica = pedirCadenaAlUsuario("Ingrese la técnica de la pintura");
                    String lienzo = pedirCadenaAlUsuario("Ingrese el lienzo de la pintura");
                    String estilo = pedirCadenaAlUsuario("Ingrese el estilo de la pintura:");
                    galeria.agregarPintura(name, autor, anio, lugar, exhibicion, valor, valorfijo, estado, alto, ancho, tecnica, lienzo, estilo);
                    break;
                case "escultura":
                    double profundidad = pedirNumeroAlUsuario("Ingrese la profundidad de la escultura (m)");
                    double peso = pedirNumeroAlUsuario("Ingrese el peso de la estructura (kg)");
                    String materiales = pedirCadenaAlUsuario("Ingrese el material de la escultura");
                    boolean electricidad = pedirConfirmacionAlUsuario("¿Necesita electricidad?");
                    String detallesInstalacion = pedirCadenaAlUsuario("Ingrese los detalles de instalación de la escultura");
                    galeria.agregarEscultura(name, autor, anio, lugar, exhibicion, valor, valorfijo,estado, alto, ancho, profundidad, materiales, peso, electricidad, detallesInstalacion);
                    break;
                case "fotografia":
                    String formato = pedirCadenaAlUsuario("Ingrese el formato de la fotografía");
                    String tecnicafotografia = pedirCadenaAlUsuario("Ingrese la técnica de la fotografía");
                    double resolucionfotografia = pedirNumeroAlUsuario("Ingrese la resolución de la fotografía");
                    galeria.agregarFotografia(name, autor, anio, lugar, exhibicion, valor, valorfijo, estado, alto, ancho, formato, tecnicafotografia, resolucionfotografia);
                    break;
                case "impresion":
                	String calidad = pedirCadenaAlUsuario("Ingrese la calidad de la impresión");
                	String tamanoimpresion = pedirCadenaAlUsuario("Ingrese el tamaño de la impresión:");
                	String tipo = pedirCadenaAlUsuario("Ingrese el tipo de impresión:");
                    galeria.agregarImpresion(name, autor, anio, lugar, exhibicion, valor, valorfijo, estado, alto, ancho, tipo, tamanoimpresion, calidad);
                    break;
                case "video":
                	String formatovideo = pedirCadenaAlUsuario("Ingrese el formato del video");
                	String duracion = pedirCadenaAlUsuario("Ingrese la duracion del video:");
                	String calidadvideo = pedirCadenaAlUsuario("Ingrese la calidad del video:");
                    galeria.agregarVideo(name, autor, anio, lugar, exhibicion, valor, valorfijo, estado, alto, ancho, formatovideo, duracion, calidadvideo);
                    break;
                default:
                    System.out.println("Tipo de pieza no válido.");
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }
    
    public Pieza imprimirDetallesPieza(String idPieza) {
    	Pieza pieza = galeria.getPieza(idPieza);
        if (pieza == null) {
            System.out.println("La pieza con ID " + idPieza + " no existe en el inventario.");
            return null;
        }

        System.out.println("Detalles de la Pieza:");
        System.out.println("Título: " + pieza.getTitulo());
        System.out.println("Autor: " + pieza.getAutor());
        System.out.println("Año de Creación: " + pieza.getAnioCreacion());
        System.out.println("Lugar de Creación: " + pieza.getLugarCreacion());
        System.out.println("En Exhibición: " + pieza.isExhibicion());
        System.out.println("Valor: " + pieza.getValor());
        System.out.println("Estado: " + pieza.getEstado());
        
        if (pieza instanceof Pintura) {
            Pintura p = (Pintura) pieza;
            System.out.println("Alto: " + p.getAlto());
            System.out.println("Ancho: " + p.getAncho());
            System.out.println("Técnica: " + p.getTecnica());
            System.out.println("Lienzo: " + p.getLienzo());
            System.out.println("Estilo: " + p.getEstilo());
        } else if (pieza instanceof Escultura) {
            Escultura e = (Escultura) pieza;
            System.out.println("Alto: " + e.getAlto());
            System.out.println("Ancho: " + e.getAncho());
            System.out.println("Profundidad: " + e.getProfundidad());
            System.out.println("Materiales: " + e.getMateriales());
            System.out.println("Peso: " + e.getPeso());
            System.out.println("Necesita Electricidad: " + e.isNecesitaElectricidad());
            System.out.println("Detalles de Instalación: " + e.getDetallesInstalacion());
        } else if (pieza instanceof Fotografia) {
            Fotografia f = (Fotografia) pieza;
            System.out.println("Alto: " + f.getAlto());
            System.out.println("Ancho: " + f.getAncho());
            System.out.println("Formato: " + f.getFormato());
            System.out.println("Técnica: " + f.getTecnica());
            System.out.println("Resolución: " + f.getResolucion());
        } else if (pieza instanceof Impresion) {
            Impresion imp = (Impresion) pieza;
            System.out.println("Alto: " + imp.getAlto());
            System.out.println("Ancho: " + imp.getAncho());
            System.out.println("Tipo de Impresión: " + imp.getTipoImpresion());
            System.out.println("Tamaño: " + imp.getTamano());
            System.out.println("Calidad: " + imp.getCalidad());
        } else if (pieza instanceof Video) {
            Video v = (Video) pieza;
            System.out.println("Alto: " + v.getAlto());
            System.out.println("Ancho: " + v.getAncho());
            System.out.println("Formato: " + v.getFormato());
            System.out.println("Duración: " + v.getDuracion());
            System.out.println("Calidad: " + v.getCalidad());
        }
        return pieza;
    }
    
    
}