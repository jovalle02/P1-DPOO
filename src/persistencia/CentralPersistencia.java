package persistencia;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import auth.Rol;
import logica.Factura;
import logica.Galeria;
import logica.Subasta;
import logica.Verificacion;
import piezas.Escultura;
import piezas.Fotografia;
import piezas.Impresion;
import piezas.Pieza;
import piezas.Pintura;
import piezas.Video;
import usuarios.Administrador;
import usuarios.Empleado;
import usuarios.Usuario;
import usuarios.UsuarioComun;

public class CentralPersistencia {
	private static final String INVENTARIO__FILE = "datos/inventario.json";
	private static final String HISTORIAL__FILE = "datos/historial.json";
	private static final String USUARIOS__FILE = "datos/usuarios.json";
	private static final String VERIFICACIONES__FILE = "datos/verificaciones_compra.json";
	private static final String FACTURAS__FILE = "datos/facturas.json";
	private static final String SUBASTAS__FILE = "datos/subastas.json";


	public static void salvarUsuarios(Map<String,Usuario> mapa, String archivo) {
	    JSONObject jUsuarios = new JSONObject();
	    for (Map.Entry<String, Usuario> entry : mapa.entrySet()) {
	    	Usuario usuario = entry.getValue();
	        JSONObject jUsuario = new JSONObject();
	        jUsuario.put("id", usuario.getId());
	        jUsuario.put("login", usuario.getLogin());
	        jUsuario.put("password", usuario.getPassword());
	        jUsuario.put("nombre", usuario.getNombre());
	        jUsuario.put("apellido", usuario.getApellido());
	        jUsuario.put("email", usuario.getEmail());
	        String rol = usuario.getRol().name();
	        jUsuario.put("rol", rol);
	        if(rol.equals("COMUN")) {
	        	salvarComun((UsuarioComun)usuario, jUsuario);
	        }
	        jUsuarios.put(usuario.getLogin(), jUsuario);
	        
	    }
	    try (FileWriter file = new FileWriter(archivo)) {
            file.write(jUsuarios.toString(2));
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	public static void salvarComun(UsuarioComun usuario, JSONObject jUsuario) {
		jUsuario.put("verificado", usuario.isVerificado());
		jUsuario.put("topeDeCompra", usuario.getTopeDeCompra());
	    
		JSONArray jHistorial = new JSONArray();
		for (Pieza pieza: usuario.getHistorial()) {
	    	jHistorial.put(pieza.getId());
	    }
		jUsuario.put("historial", jHistorial);
		
	    JSONArray jPiezasActuales = new JSONArray();
	    for (Pieza pieza: usuario.getPiezasActuales()) {
	    	jPiezasActuales.put(pieza.getId());
	    }
		jUsuario.put("piezas_actuales", jPiezasActuales);
	    
	}
	
	public static void salvarPiezas(Map<String, Pieza> mapaPiezas, String archivo) {
	    JSONArray jPiezas = new JSONArray();
	    for (Map.Entry<String, Pieza> entry : mapaPiezas.entrySet()) {
	        Pieza pieza = entry.getValue();
	        JSONObject jPieza = new JSONObject();
	        jPieza.put("titulo", pieza.getTitulo());
	        jPieza.put("id", pieza.getId());
	        jPieza.put("ano", pieza.getAnioCreacion());
	        jPieza.put("lugarDeCreacion", pieza.getLugarCreacion());
	        jPieza.put("estado", pieza.getEstado());
	        jPieza.put("valorFijo", pieza.isValorfijo());
	        jPieza.put("disponible", pieza.isDisponible());
	        jPieza.put("vendida", pieza.isVendida());
	        jPieza.put("valor", pieza.getValor());
	        jPieza.put("exhibicion", pieza.isExhibicion());
	        jPieza.put("descripcion", pieza.getEstado());
	        jPieza.put("autor", pieza.getAutor());

	        String tipoPieza = pieza.getTipo();
	        if (tipoPieza.equals("Impresion")) {
	            salvarImpresion((Impresion) pieza, jPieza);
	        } else if (tipoPieza.equals("Fotografia")) {
	            salvarFotografia((Fotografia) pieza, jPieza);
	        } else if (tipoPieza.equals("Video")) {
	            salvarVideo((Video) pieza, jPieza);
	        } else if (tipoPieza.equals("Escultura")) {
	            salvarEscultura((Escultura) pieza, jPieza);
	        } else if (tipoPieza.equals("Pintura")) {
	            salvarPintura((Pintura) pieza, jPieza);
	        }

	        jPiezas.put(jPieza);
	    }
	    
        try (FileWriter file = new FileWriter(archivo)) {
            file.write(jPiezas.toString(2));
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	private static void salvarImpresion(Impresion impresion, JSONObject jPieza) {
	    jPieza.put("tipo", "Impresion");
	    jPieza.put("alto", impresion.getAlto());
	    jPieza.put("ancho", impresion.getAncho());
	    jPieza.put("tipoImpresion", impresion.getTipoImpresion());
	    jPieza.put("tamano", impresion.getTamano());
	    jPieza.put("calidad", impresion.getCalidad());

	}

	private static void salvarFotografia(Fotografia fotografia, JSONObject jPieza) {
	    jPieza.put("tipo", "Fotografia");
	    jPieza.put("alto", fotografia.getAlto());
	    jPieza.put("ancho", fotografia.getAncho());
	}

	private static void salvarVideo(Video video, JSONObject jPieza) {
	    jPieza.put("tipo", "Video");
	    jPieza.put("alto", video.getAlto()); //FORMATO, DURACION, CALIDAD
	    jPieza.put("ancho", video.getAncho());
	    jPieza.put("duracion", video.getDuracion());
	    jPieza.put("formato", video.getFormato());
	    jPieza.put("calidad", video.getCalidad());


	}

	private static void salvarEscultura(Escultura escultura, JSONObject jPieza) {
	    jPieza.put("tipo", "Escultura");
	    jPieza.put("alto", escultura.getAlto());
	    jPieza.put("ancho", escultura.getAncho());
	    jPieza.put("profundidad", escultura.getProfundidad());
	    jPieza.put("materiales", escultura.getMateriales());
	    jPieza.put("peso", escultura.getPeso());
	    jPieza.put("electricidad", escultura.isNecesitaElectricidad());
	}

	private static void salvarPintura(Pintura pintura, JSONObject jPieza) {
	    jPieza.put("tipo", "Pintura");
	    jPieza.put("alto", pintura.getAlto());
	    jPieza.put("ancho", pintura.getAncho());
	    jPieza.put("tecnica", pintura.getTecnica());
	    jPieza.put("lienzo", pintura.getLienzo());
	    jPieza.put("estilo", pintura.getEstilo());
	}
	
	public static void salvarVerificaciones(Galeria galeria, String archivo) {
		List<Verificacion> listaVerificaciones = galeria.getVerificaciones();
	    JSONArray jVerificaciones = new JSONArray();
	    for (Verificacion verificacion : listaVerificaciones) {
	    	//System.out.println("Verificacion: "+verificacion.getUsuario().getId());
	        JSONObject jVerificacion = new JSONObject();
	        jVerificacion.put("usuario", verificacion.getUsuario().getId());
	        jVerificacion.put("pieza", verificacion.getPieza().getId());
	        jVerificaciones.put(jVerificacion);
	    }

	    try (FileWriter file = new FileWriter(archivo)) {
	        file.write(jVerificaciones.toString(2));
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public static void salvarFacturas(List<Factura> listaFacturas, String archivo) {
	    JSONArray jFacturas = new JSONArray();
	    for (Factura factura : listaFacturas) {
	        JSONObject jFactura = new JSONObject();
	        jFactura.put("tipoDePago", factura.getTipoDePago());
	        jFactura.put("valor", factura.getValor());
	        jFactura.put("usuario", factura.getComprador().getId());
	        jFactura.put("id", factura.getId());
	        jFactura.put("id_pieza", factura.getIdPieza());
	        jFacturas.put(jFactura);
	    }

	    try (FileWriter file = new FileWriter(archivo)) {
	        file.write(jFacturas.toString(2));
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	public static void salvarSubastas(Galeria galeria, String archivo) {
	    List<Subasta> listaSubastas = galeria.getSubastas();
	    JSONArray jSubastas = new JSONArray();
	    for (Subasta subasta : listaSubastas) {
	        JSONObject jSubasta = new JSONObject();
	        jSubasta.put("id", subasta.getId());
	        jSubasta.put("valorMinimo", subasta.getValorMinimo());
	        jSubasta.put("valorInicial", subasta.getValorInicial());
	        jSubasta.put("mayorOfrecido", subasta.getMayorOfrecido());
	        jSubasta.put("operador", subasta.getOperador().getId());
	        jSubasta.put("piezaSubastada", subasta.getPiezaSubastada().getId());
	        jSubasta.put("clienteMaximoOfrecido", subasta.getClienteMaximoOfrecido().getId());

	        JSONArray jOfertadores = new JSONArray();
		    for (Usuario usuario: subasta.getOfertadores()) {
		    	jOfertadores.put(usuario.getId());
		    }
			jSubasta.put("ofertadores", jOfertadores);
	        jSubastas.put(jSubasta);
	    }

	    try (FileWriter file = new FileWriter(archivo)) {
	        file.write(jSubastas.toString(2));
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	public static void salvarGaleria(Galeria galeria) {
		//Salvar usuarios
		Map<String, Usuario> usuarios = galeria.getUsuarios();
		salvarUsuarios(usuarios, USUARIOS__FILE);
		//Salvar piezas
		Map<String, Pieza> inventario = galeria.getInventario();
		//System.out.println(inventario.keySet());
		Map<String, Pieza> historial = galeria.getHistorial();
		salvarPiezas(inventario, INVENTARIO__FILE);
		salvarPiezas(historial, HISTORIAL__FILE);
		//Salvar verificaciones
		salvarVerificaciones(galeria, VERIFICACIONES__FILE);
		salvarSubastas(galeria, SUBASTAS__FILE);
	}
	
	public static void cargarVerificaciones (Galeria galeria, JSONArray arrayVerificaciones) {
		List<Verificacion> newList = new ArrayList<Verificacion>();
		for (int i =0; i<arrayVerificaciones.length();i++) {
			JSONObject verificacion = arrayVerificaciones.getJSONObject(i);
			String usuario = verificacion.getString("usuario");
			//System.out.println(usuario);
			String pieza = verificacion.getString("pieza");
			//System.out.println(pieza);
			try {
			//System.out.println(galeria.getUsuarios().keySet());
			UsuarioComun usuarioAdd = (UsuarioComun) galeria.getUsuarios().get(usuario);
			Pieza piezaAdd = galeria.getInventario().get(pieza);
			Verificacion verificacionAdd = new Verificacion(usuarioAdd, piezaAdd);
			newList.add(verificacionAdd);
			}catch (Exception e) {
	            e.printStackTrace();
	            System.out.println("Una verificación de compra solo puede contener un usuario común.");
	        }
		}
		galeria.setVerificaciones(newList);
	}
	public static void cargarPiezas(Galeria galeria, JSONArray arrayPiezas,Map<String, Pieza> mapa) {
	for (int i = 0; i < arrayPiezas.length(); i++) {
		JSONObject pieza = arrayPiezas.getJSONObject(i);
		String id = pieza.getString("id");
		String titulo = pieza.getString("titulo");
		String autor = pieza.getString("autor");
		String ano = pieza.getString("ano");
		String lugarDeCreacion = pieza.getString("lugarDeCreacion");
		boolean exhibicion = pieza.getBoolean("exhibicion"); // Updated to boolean
		double valor = pieza.getDouble("valor");
		boolean valorFijo = pieza.getBoolean("valorFijo");
		String estado = pieza.getString("estado");
		double alto = pieza.getDouble("alto");
		double ancho = pieza.getDouble("ancho");
	        switch (pieza.getString("tipo")) {
	            case "Escultura":
	                cargarEscultura(galeria,mapa, pieza, id, titulo, autor, ano, lugarDeCreacion, exhibicion, valor, valorFijo, estado, alto, ancho);
	                break;
	            case "Fotografia":
	                cargarFotografia(galeria, mapa, pieza, id, titulo, autor, ano, lugarDeCreacion, exhibicion, valor, valorFijo, estado, alto, ancho);
	                break;
	            case "Impresion":
	                cargarImpresion(galeria, mapa,pieza, id, titulo, autor, ano, lugarDeCreacion, exhibicion, valor, valorFijo, estado, alto, ancho);
	                break;
	            case "Pintura":
	                cargarPintura(galeria,mapa, pieza, id, titulo, autor, ano, lugarDeCreacion, exhibicion, valor, valorFijo, estado, alto, ancho);
	                break;
	            case "Video":
	                cargarVideo(galeria,mapa, pieza, id, titulo, autor, ano, lugarDeCreacion, exhibicion, valor, valorFijo, estado, alto, ancho);
	                break;
	            default:
	                // Handle unknown type
	                break;
	        }
	    }
	}
	private static void cargarEscultura(Galeria galeria,Map<String, Pieza> mapa,  JSONObject pieza, String id, String titulo, String autor, String ano,
            String lugarDeCreacion, boolean exhibicion, double valor, boolean valorFijo, String estado,
            double alto, double ancho) {
			// Extract additional attributes specific to Escultura
			double profundidad = pieza.getDouble("profundidad"); 
			String materiales = pieza.getString("materiales");
			double peso = pieza.getDouble("peso");
			boolean electricidad = pieza.getBoolean("electricidad");
			String detallesInstalacion = pieza.getString("detallesInstalacion"); 
			// Add the Escultura object to the Galeria
			galeria.agregarPiezaJSON(new Escultura(id, titulo, autor, ano, lugarDeCreacion, exhibicion, valor, valorFijo,
			estado, alto, ancho, profundidad, materiales, peso, electricidad, detallesInstalacion),mapa);
			}

	private static void cargarFotografia(Galeria galeria, Map<String, Pieza> mapa, JSONObject pieza, String id, String titulo, String autor, String ano,
            String lugarDeCreacion, boolean exhibicion, double valor, boolean valorFijo, String estado,
            double alto, double ancho) {
			// Extract additional attributes specific to Fotografia
			String formato = pieza.getString("formato");
			String tecnica = pieza.getString("tecnica");
			double resolucion = pieza.getDouble("resolucion");
			// Add the Fotografia object to the Galeria
			galeria.agregarPiezaJSON(new Fotografia(id, titulo, autor, ano, lugarDeCreacion, exhibicion, valor, valorFijo,
			estado, alto, ancho, formato, tecnica, resolucion), mapa);
			}


	private static void cargarImpresion(Galeria galeria, Map<String, Pieza> mapa, JSONObject pieza, String id, String titulo, String autor, String ano,
            String lugarDeCreacion, boolean exhibicion, double valor, boolean valorFijo, String estado,
            double alto, double ancho) {
			// Extract additional attributes specific to Impresion
			String tipoImpresion = pieza.getString("tipoImpresion");
			String tamano = pieza.getString("tamano");
			String calidad = pieza.getString("calidad");
			// Add the Impresion object to the Galeria
			galeria.agregarPiezaJSON(new Impresion(id, titulo, autor, ano, lugarDeCreacion, exhibicion, valor, valorFijo,
			estado, alto, ancho, tipoImpresion, tamano, calidad), mapa);
			}

	private static void cargarPintura(Galeria galeria, Map<String, Pieza> mapa, JSONObject pieza, String id, String titulo, String autor, String ano,
            String lugarDeCreacion, boolean exhibicion, double valor, boolean valorFijo, String estado,
            double alto, double ancho) {
			// Extract additional attributes specific to Pintura
			String tecnica = pieza.getString("tecnica");
			String lienzo = pieza.getString("lienzo"); 
			String estilo = pieza.getString("estilo");
			// Add the Pintura object to the Galeria
			galeria.agregarPiezaJSON(new Pintura(id, titulo, autor, ano, lugarDeCreacion, exhibicion, valor, valorFijo,
			estado, alto, ancho, tecnica, lienzo, estilo), mapa);
			}


	private static void cargarVideo(Galeria galeria, Map<String, Pieza> mapa, JSONObject pieza, String id, String titulo, String autor, String ano,
            String lugarDeCreacion, boolean exhibicion, double valor, boolean valorFijo, String estado,
            double alto, double ancho) {
			// Extract additional attributes specific to Video
			String formato = pieza.getString("formato");
			String duracion = pieza.getString("duracion"); 
			String calidad = pieza.getString("calidad");
			// Add the Video object to the Galeria
			galeria.agregarPiezaJSON(new Video(id, titulo, autor, ano, lugarDeCreacion, exhibicion, valor, valorFijo,
			estado, alto, ancho, formato, duracion, calidad), mapa);
			}

	public static void cargarUsuarios(Galeria galeria, JSONObject objUsuarios, Map<String, Usuario> mapaUsuarios) {
	    for (String key: objUsuarios.keySet()) {
	        JSONObject usuario = objUsuarios.getJSONObject(key);
	        String id = usuario.getString("id");
	        String nombre = usuario.getString("nombre");
	        String apellido = usuario.getString("apellido");
	        String email = usuario.getString("email");
	        String password = usuario.getString("password");
	        String login = usuario.getString("login");
	        String rol = usuario.getString("rol");
	        Rol rolClass = Rol.fromString(rol);
	        switch (rol) {
	            case "ADMIN":
	                cargarAdministrador(galeria, mapaUsuarios, usuario, id, nombre, apellido, email, password, login, rolClass);
	                break;
	            case "EMPLEADO":
	                cargarEmpleado(galeria, mapaUsuarios, usuario, id, nombre, apellido, email, password, login, rolClass);
	                break;
	            case "COMUN":
	                cargarUsuarioComun(galeria, mapaUsuarios, usuario, id, nombre, apellido, email, password, login, rolClass);
	                break;
	            default:
	                // Handle unknown role
	                break;
	        }
	    }
	}

	private static void cargarAdministrador(Galeria galeria, Map<String, Usuario> mapaUsuarios, JSONObject usuario, String id, String nombre,
	                                         String apellido, String email, String password, String login, Rol rol) {
	    // Create and add the Administrador object to the map
	    mapaUsuarios.put(id, new Administrador(id, nombre, apellido, email, password, login,  rol));
	}

	private static void cargarEmpleado(Galeria galeria, Map<String, Usuario> mapaUsuarios, JSONObject usuario, String id, String nombre,
	                                    String apellido, String email, String password, String login, Rol rol) {
	    // Create and add the Empleado object to the map
	    mapaUsuarios.put(id, new Empleado(id, nombre, apellido, email, password, login,rol));
	}

	private static void cargarUsuarioComun(Galeria galeria, Map<String, Usuario> mapaUsuarios, JSONObject usuario, String id, String nombre,
	                                       String apellido, String email, String password, String login, Rol rol) {
	    // Extract additional attributes specific to UsuarioComun
	    boolean verificado = usuario.getBoolean("verificado");
	    float topeDeCompra = usuario.getFloat("topeDeCompra");
	    
	    JSONArray jHistorial = usuario.getJSONArray("historial");
	    List<Pieza> histLista = new ArrayList<Pieza>();
	    for (int i = 0; i<jHistorial.length();i++) {
	    	String idObra = jHistorial.getString(i);
	    	Pieza obra = galeria.getPieza(idObra);
	    	if(obra!=null) {
	    		histLista.add(galeria.getPieza(idObra));
	    		//System.out.println(galeria.getPieza(idObra).getId());
	    	}
	    }
	    
	    JSONArray jPiezas = usuario.getJSONArray("piezas_actuales");
	    List<Pieza> piezasLista = new ArrayList<Pieza>();
	    for (int i = 0; i<jPiezas.length();i++) {
	    	String idObra = jPiezas.getString(i);
	    	Pieza obra = galeria.getPieza(idObra);
	    	if(obra!=null) {
	    		piezasLista.add(galeria.getPieza(idObra));
	    		//System.out.println(galeria.getPieza(idObra).getId());
	    	}
	    	
	    	
	    }
	    List<Factura>listCompras = new ArrayList<Factura>();
	    // Create and add the UsuarioComun object to the map
	    mapaUsuarios.put(id, new UsuarioComun(id, nombre, apellido, email, password, login, rol, histLista, piezasLista, listCompras, verificado, topeDeCompra));
	}

	public static void cargarFacturas(Galeria galeria, JSONObject factura) {
		double valor = factura.getDouble("valor");
		String tipoDePago = factura.getString("tipoDePago");
		String idUsuario = factura.getString("usuario");
		//System.out.println(idUsuario);
		//System.out.println(galeria.getUsuarioId(idUsuario));
		Usuario usuario = galeria.getUsuarioId(idUsuario);
		//System.out.println("Verificando user "+usuario.getId());
		String idFactura = factura.getString("id");
		String idPieza = factura.getString("id_pieza");
		Factura newFactura = new Factura(tipoDePago, valor, usuario, idFactura, idPieza);
		//System.out.println("Factura creada: "+newFactura.getComprador().getId());
		galeria.agregarFactura(idFactura, newFactura);
	}
	
	public static void agregarFacturasUsuarios(Galeria galeria) {
		for (Factura factura: galeria.getHistorialDeCompras().values()	) {
			//System.out.println("Dentro de agregar factura "+factura.getId());
			UsuarioComun usuario = (UsuarioComun) factura.getComprador();
			usuario.agregarCompra(factura);
		}
	}
	
	public static void cargarSubastas (Galeria galeria, JSONArray arraySubastas) {
		List<Subasta> newList = new ArrayList<Subasta>();
		for (int i =0; i<arraySubastas.length();i++) {
			JSONObject subasta = arraySubastas.getJSONObject(i);
			String id = subasta.getString("id");
			float valorMinimo  = subasta.getFloat("valorMinimo");
			float valorInicial  = subasta.getFloat("valorInicial");
			float mayorOfrecido  = subasta.getFloat("mayorOfrecido");
			String idOperador = subasta.getString("operador");
			Usuario operadorAdd = galeria.getUsuarios().get(idOperador);
			String idClienteMaximo = subasta.getString("clienteMaximoOfrecido");
			Usuario clienteMaximoAdd = galeria.getUsuarios().get(idClienteMaximo);
			String piezaSubastada = subasta.getString("piezaSubastada");
			Pieza piezaAdd = galeria.getInventario().get(piezaSubastada);
			JSONArray ofertadores = subasta.getJSONArray("ofertadores");
			List<Usuario>ofertadoresAdd = new ArrayList<Usuario>();
			for(int j=0; j<ofertadores.length();j++) {
				;
				String ofertadorID = ofertadores.getString(j);
				Usuario ofertador = galeria.getUsuarioId(ofertadorID);
				ofertadoresAdd.add(ofertador);
			}
			Subasta subastaAdd = new Subasta(valorMinimo, valorInicial, operadorAdd, piezaAdd,id);
			subastaAdd.setMayorOfrecido(mayorOfrecido);
			subastaAdd.setOfertadores(ofertadoresAdd);
			subastaAdd.setClienteMaximoOfrecido(clienteMaximoAdd);
			newList.add(subastaAdd);
			
		}
		galeria.setSubastas(newList);
	}
	public static void cargarGaleria(Galeria galeria) {
		
		//Carga las piezas
		Map<String, Pieza> inventario = galeria.getInventario();
		Map<String, Pieza> historial = galeria.getHistorial();
		
		try { 
		String jsonDataInventario = new String(Files.readAllBytes(Paths.get(INVENTARIO__FILE)));
        JSONArray jPiezasInventario = new JSONArray(jsonDataInventario);
        cargarPiezas(galeria, jPiezasInventario, inventario);
        
        String jsonDataHistorial = new String(Files.readAllBytes(Paths.get(HISTORIAL__FILE)));
        JSONArray jPiezasHistorial = new JSONArray(jsonDataHistorial);
        cargarPiezas(galeria, jPiezasHistorial, historial);
		} catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error leyendo el archivo de piezas.");
        }
		
		//Carga los usuarios 
				Map<String,Usuario> usuarios = galeria.getUsuarios();
				try { 
					String jsonDataUsuarios = new String(Files.readAllBytes(Paths.get(USUARIOS__FILE)));
			        JSONObject jPiezasUsuarios = new JSONObject(jsonDataUsuarios);
			        cargarUsuarios(galeria, jPiezasUsuarios, usuarios);

					} catch (IOException e) {
			            e.printStackTrace();
			            System.out.println("Error leyendo el archivo de usuarios.");
			        }
				
		//Carga las verificaciones 
		try {
		String jsonVerificaciones = new String(Files.readAllBytes(Paths.get(VERIFICACIONES__FILE)));
	    JSONArray jVerificaciones = new JSONArray(jsonVerificaciones);
		cargarVerificaciones(galeria, jVerificaciones);
		} catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error leyendo el archivo de verificaciones.");
        }
		
		//Carga las facturas
		try {
			String jsonFacturas = new String(Files.readAllBytes(Paths.get(FACTURAS__FILE)));
		    JSONArray jFacturas = new JSONArray(jsonFacturas);
		    for(int i=0; i<jFacturas.length();i++	) {
		    	cargarFacturas(galeria, jFacturas.getJSONObject(i));
		    }
			agregarFacturasUsuarios(galeria);
			/*
			for (Usuario us: galeria.getUsuarios().values()) {
				if(us instanceof UsuarioComun) {
					for(Factura fact :((UsuarioComun) us).getCompras()) {
						System.out.println(fact.getId());
						System.out.println(fact.getComprador().getId());
					}
				}
			}
			*/
			} catch (IOException e) {
	            e.printStackTrace();
	            System.out.println("Error leyendo el archivo de facturas.");
	        }
		
		//Carga las subastas.
		try {
			String jsonSubastas = new String(Files.readAllBytes(Paths.get(SUBASTAS__FILE)));
		    JSONArray jSubastas = new JSONArray(jsonSubastas);
		    cargarSubastas(galeria, jSubastas);

			} catch (IOException e) {
	            e.printStackTrace();
	            System.out.println("Error leyendo el archivo de subastas.");
	        }
		/*
		for (Subasta sub: galeria.getSubastas()) {
			System.out.println(sub.getPiezaSubastada().getId());
				for(Usuario us :sub.getOfertadores()) {
					System.out.println(us.getId());
					
				}

		}*/
		
        
	}
}



	
	/*
	public void cargarPiezasDesdeJSON(Galeria galeria) {
        try {
            String jsonData = new String(Files.readAllBytes(Paths.get(PIEZAS__FILE)));
            JSONArray jPiezas = new JSONArray(jsonData);
            for (int i = 0; i < jPiezas.length(); i++) {
                JSONObject jPieza = jPiezas.getJSONObject(i);
                agregarPiezaDesdeJSON(jPieza, galeria);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error leyendo el archivo de piezas.");
        }
    }

    private void agregarPiezaDesdeJSON(JSONObject jPieza, Galeria galeria) throws IOException {
        String tipo = jPieza.getString("tipo");
        switch (tipo) {
            case "Pintura":
                galeria.agregarPintura(
                    jPieza.getString("titulo"),
                    jPieza.getString("autor"),
                    jPieza.getString("ano"),
                    jPieza.getString("lugarDeCreacion"),
                    jPieza.getBoolean("exhibicion"),
                    jPieza.getDouble("precio"),
                    jPieza.getBoolean("valorFijo"),
                    jPieza.getString("estado"),
                    jPieza.getDouble("alto"),
                    jPieza.getDouble("ancho"),
                    jPieza.getString("tecnica"),
                    jPieza.getString("lienzo"),
                    jPieza.getString("estilo")
                );
                break;
            case "Escultura":
                galeria.agregarEscultura(
                    jPieza.getString("titulo"),
                    jPieza.getString("autor"),
                    jPieza.getString("ano"),
                    jPieza.getString("lugarDeCreacion"),
                    jPieza.getBoolean("exhibicion"),
                    jPieza.getDouble("precio"),
                    jPieza.getBoolean("valorFijo"),
                    jPieza.getString("estado"),
                    jPieza.getDouble("alto"),
                    jPieza.getDouble("ancho"),
                    jPieza.getDouble("profundidad"),
                    jPieza.getString("materiales"),
                    jPieza.getDouble("peso"),
                    jPieza.getBoolean("necesitaElectricidad"),
                    jPieza.optString("detallesInstalacion", "")
                );
                break;
            case "Fotografia":
                galeria.agregarFotografia(
                    jPieza.getString("titulo"),
                    jPieza.getString("autor"),
                    jPieza.getString("ano"),
                    jPieza.getString("lugarDeCreacion"),
                    jPieza.getBoolean("exhibicion"),
                    jPieza.getDouble("precio"),
                    jPieza.getBoolean("valorFijo"),
                    jPieza.getString("estado"),
                    jPieza.getDouble("alto"),
                    jPieza.getDouble("ancho"),
                    jPieza.getString("formato"),
                    jPieza.getString("tecnica"),
                    jPieza.getDouble("resolucion")
                );
                break;
            default:
                System.out.println("Tipo de pieza no reconocido: " + tipo);
                break;
        }
    }
}
*/
