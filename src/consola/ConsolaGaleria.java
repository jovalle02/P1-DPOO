package consola;

import java.awt.EventQueue;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import auth.Rol;
import consola.auth.ConsolaAuth;
import consola.auth.UsuariosJson;
import consola.auth.VentanaAuth;
import interfaz.usuario.GUIAdmin;
import interfaz.usuario.GUIEmpleado;
import interfaz.usuario.GUIUsuario;
import logica.Factura;
import logica.Galeria;
import logica.Subasta;
import logica.Verificacion;
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

	public void correrAplicacion() throws Exception {

        galeria = new Galeria( );
        persistencia = new CentralPersistencia();

        galeria.cargarGaleria();
        if (isAutenticado()) {
			if (usuario.getRol().equals(Rol.ADMIN)) {
				menuAdministrador();
			} else if (usuario.getRol().equals(Rol.EMPLEADO)) {
				menuEmpleado();
			} else if (usuario.getRol().equals(Rol.COMUN)) {
				menuUsuario();
			}

        } else {

   		 VentanaAuth ventAuth = new VentanaAuth();
         ventAuth.addWindowListener(new java.awt.event.WindowAdapter() {
         @Override
         public void windowClosed(java.awt.event.WindowEvent windowEvent) {
        	 UsuariosJson user = UsuariosJson.getInstance();
             usuario = user.getUsuario();
             setAutenticado(true);
             try {
				correrAplicacion();
			} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
					}
            	}
            });
            ventAuth.setVisible(true);
       }
	}
	
	private void menuAdministrador() throws Exception {
		
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                	GUIAdmin frame = new GUIAdmin(galeria, usuario);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
		

	}

	public void consultarUsuario(Galeria galeriaPrueba) {
		
		
		Galeria galeria = galeriaPrueba;
		
		System.out.println("Consultar usuario");
		String idABuscar = pedirCadenaAlUsuario("Ingrese el id del usuario que desea consultar");
		
		UsuarioComun usuario = (UsuarioComun) galeria.getUsuarioId(idABuscar);
		Map<String, Factura> piezasHistoria = galeria.getHistorialDeCompras();
		
		System.out.println("+++++++++++++++++++++++++++++++++");
        
		System.out.println("Usuario: " + usuario.getNombre() + " " + usuario.getApellido() );
		System.out.println("+++++++++++++++++++++++++++++++++");		            	
        
    	
		
		for (Entry<String, Factura> entry : piezasHistoria.entrySet()) {
            String key = entry.getKey();
            Factura value = entry.getValue(); 
            if (value.getComprador().getId() == usuario.getId()) {
            	System.out.println("Nombre de Pintura: " + value.getIdPieza() ); 
            	System.out.println("Fecha de compra: " + value.getFecha());
            	System.out.println("---");		            	
            }
            
            
        }
		
		List<Pieza> piezasUser = usuario.getPiezasActuales();
		
		System.out.println("[ Piezas en el inventario del usuario ]"); 
		
		double total = 0;
		
		for (Pieza entry : piezasUser) {
			
        	System.out.println("Nombre de Pintura:" + entry.getTitulo()  ); 
        	System.out.println("Valor: " + entry.getValor() );
        	System.out.println("---");		            	
        	total += entry.getValor();
		}
		
		System.out.println("Valor del inventario:" + total  ); 
	}
	
	private void menuEmpleado() throws Exception {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                	GUIEmpleado frame = new GUIEmpleado(galeria, usuario);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
	}

	public static void main(String[] args) throws Exception {
		ConsolaGaleria cg = new ConsolaGaleria();
		cg.correrAplicacion();
	}

	public boolean isAutenticado() {
		return autenticado;
	}

	public void setAutenticado(boolean autenticado) {
		this.autenticado = autenticado;
	}



	private void menuUsuario() throws Exception {
		GUIUsuario interfaz = new GUIUsuario(galeria, usuario);
		
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    GUIUsuario frame = new GUIUsuario(galeria, usuario);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
	}
	
	private void menuSubastasEmpleado() throws Exception {
		int opcion = mostrarMenu("Galeria y Casa de Subastas", new String[]{"Consultar subastas", "Consultar subasta", "Finalizar subasta","Crear Subasta","Menu Principal"});
		List<Subasta> subastas = this.galeria.getSubastas();
		
		
		switch(opcion) {
		
		case 1:
			for (int i = 0; i < subastas.size(); i++) {
			    Subasta s = subastas.get(i);
			    Pieza p = s.getPiezaSubastada();
			    System.out.println("Numero de subasta: " + String.valueOf(i+1));
			    this.imprimirDetallesPieza(p.getId());
			    if (s.isActiva()) {
			    	System.out.println("Estado subasta: Activa");
			    } else {
			    	System.out.println("Estado subasta: Terminada");
			    }
			    System.out.println("Valor minimo para ofertar: " + String.valueOf(s.getMayorOfrecido()) );
			}
			
			if (subastas.size() == 0) {
				System.out.println("No hay subastas disponibles");
			}
			menuSubastasEmpleado();
			break;
		case 2:
			int idSubasta = pedirEnteroAlUsuario("Ingrese el numero de la subasta");
			Subasta s = subastas.get(idSubasta - 1);
			Pieza p = s.getPiezaSubastada();
		    System.out.println("Numero de subasta: " + String.valueOf(idSubasta+1));
		    System.out.println("---------------------------");
		    this.imprimirDetallesPieza(p.getId());
		    if (s.isActiva()) {
		    	System.out.println("Estado subasta: Activa");
		    } else {
		    	System.out.println("Estado subasta: Terminada");
		    }
		    System.out.println("Valor minimo para ofertar: " + String.valueOf(s.getMayorOfrecido()) );
		    System.out.println("Valor minimo para poder finalizar la subasta:" + String.valueOf(s.getValorMinimo()) );
		    System.out.println("Historial de Usuarios que han ofertado:");
		    
		    List<Usuario> ofertadores = s.getOfertadores();
		    
		    for (Usuario ofertador: ofertadores) {
		    	System.out.println(ofertador.getNombre() + " " + ofertador.getApellido());
		    }
		    menuSubastasEmpleado();
		    break;
		case 3:
			int id = pedirEnteroAlUsuario("Ingrese el numero de la subasta");
			Subasta subasta = subastas.get(id - 1);
			Pieza piezaSubastada = subasta.getPiezaSubastada();
			galeria.realizarCompra((UsuarioComun) subasta.getClienteMaximoOfrecido(), piezaSubastada);
			List<Verificacion> verificaciones = galeria.getVerificaciones(); 
			galeria.confirmarVenta(verificaciones.get(verificaciones.size()-1), autenticado, "tarjeta");
			menuSubastasEmpleado();
			break;
		case 4:
			float valorMinimo = (float) pedirNumeroAlUsuario("Cuanto debe ser el valor minimo de la subasta?");
			float valorInicial = (float) pedirNumeroAlUsuario("Cuanto debe ser el valor inicial de la subasta?");
			String idPieza = pedirCadenaAlUsuario("Ingrese el nombre de la pieza la cual desea subastar");
			Pieza pieza = galeria.getPieza(idPieza);
			
			Subasta nuevaSubasta = new Subasta(valorMinimo, valorInicial, usuario, pieza, UUID.randomUUID().toString());
			nuevaSubasta.setClienteMaximoOfrecido(usuario);
			nuevaSubasta.setActiva(true);
			subastas.add(nuevaSubasta);
			
			this.galeria.setSubastas(subastas);
			menuSubastasEmpleado();
			break;
		case 5:
			menuEmpleado();
			break;
		default:
			System.out.println("Opción inválida. Por favor, seleccione una opción válida.");
		}
	}
	
	private void menuSubastasUsuario() throws Exception {
		int opcion = mostrarMenu("Galeria y Casa de Subastas", new String[]{"Consultar subastas", "Realizar oferta", "Menu Principal"});
		List<Subasta> subastas = this.galeria.getSubastas();
		switch (opcion) {
		case 1:
			for (int i = 0; i < subastas.size(); i++) {
			    Subasta s = subastas.get(i);
			    Pieza p = s.getPiezaSubastada();
			    System.out.println("Numero de subasta: " + String.valueOf(i+1));
			    System.out.println("---------------------------");
			    this.imprimirDetallesPieza(p.getId());
			    if (s.isActiva()) {
			    	System.out.println("Estado subasta: Activa");
			    } else {
			    	System.out.println("Estado subasta: Terminada");
			    }
			    System.out.println("Valor minimo para ofertar: " + String.valueOf(s.getMayorOfrecido()) );
			}
			
			if (subastas.size() == 0) {
				System.out.println("No hay subastas disponibles");
			}
			
			menuSubastasUsuario();
			break;
			
		case 2: 
			int idSubasta = pedirEnteroAlUsuario("Ingrese el numero de la subasta");
			Subasta s = subastas.get(idSubasta - 1);
			float valorOferta = (float) pedirNumeroAlUsuario("Cuanto desea ofrecer?");
			try {
				s.subastar((UsuarioComun) this.usuario, valorOferta);				
			} catch (Exception e){
				e.printStackTrace();
			}
			menuSubastasUsuario();
			break;
		case 3:
			menuUsuario();
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
    public Verificacion imprimirVerificaciones() {
    	List<Verificacion> verificaciones = galeria.getVerificaciones();
        if (verificaciones.isEmpty()) {
            System.out.println("No hay verificaciones pendientes.");
            return null;
        }

        System.out.println("Verificaciones Pendientes:");
        int index = 1;
        for (Verificacion verificacion : verificaciones) {
            UsuarioComun usuario = verificacion.getUsuario();
            Pieza pieza = verificacion.getPieza();
            System.out.println("-----------------------------------");
            System.out.println("Verificación" + " - "  + index);
            System.out.println("Usuario: " + usuario.getId() + " - " + usuario.getNombre());
            System.out.println("Pieza: " + pieza.getId() + " - " + pieza.getAutor());
            System.out.println("Tipo de Pieza: " + pieza.getClass().getSimpleName());
            System.out.println("Valor: " + pieza.getValor());
            System.out.println("Estado de la Pieza: " + pieza.getEstado());
            System.out.println("-----------------------------------");
            index++;
        }
        int seleccion = pedirEnteroAlUsuario("Ingrese el numero de la verificacion que desea confirmar:");
        if (seleccion < 1 || seleccion > verificaciones.size()) {
            System.out.println("Selección inválida. Intente de nuevo.");
            return null; 
        }
        return verificaciones.get(seleccion - 1);
    }
    
}