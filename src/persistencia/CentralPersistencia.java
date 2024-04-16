package persistencia;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import auth.Rol;
import logica.Galeria;
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
		jUsuario.put("topeDeCompra", usuario.getTopeDeCompra());}
	
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
	
	public static void salvarVerificaciones(List<Verificacion> listaVerificaciones, String archivo) {
	    JSONArray jVerificaciones = new JSONArray();
	    for (Verificacion verificacion : listaVerificaciones) {
	        JSONObject jVerificacion = new JSONObject();
	        jVerificacion.put("usuario", verificacion.getUsuario().getLogin());
	        jVerificacion.put("pieza", verificacion.getPieza().getId());
	        jVerificaciones.put(jVerificacion);
	    }

	    try (FileWriter file = new FileWriter(archivo)) {
	        file.write(jVerificaciones.toString(2));
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
		salvarVerificaciones(galeria.getVerificaciones(), VERIFICACIONES__FILE);
	}
	
	public static void cargarVerificaciones (Galeria galeria, JSONArray arrayVerificaciones) {
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
			List<Verificacion> newList = galeria.getVerificaciones();
			Verificacion verificacionAdd = new Verificacion(usuarioAdd, piezaAdd);
			newList.add(verificacionAdd);
			galeria.setVerificaciones(newList);
			}catch (Exception e) {
	            e.printStackTrace();
	            System.out.println("Una verificación de compra solo puede contener un usuario común.");
	        }
			
			
			
		}
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
	                cargarAdministrador(mapaUsuarios, usuario, id, nombre, apellido, email, password, login, rolClass);
	                break;
	            case "EMPLEADO":
	                cargarEmpleado(mapaUsuarios, usuario, id, nombre, apellido, email, password, login, rolClass);
	                break;
	            case "COMUN":
	                cargarUsuarioComun(mapaUsuarios, usuario, id, nombre, apellido, email, password, login, rolClass);
	                break;
	            default:
	                // Handle unknown role
	                break;
	        }
	    }
	}

	private static void cargarAdministrador(Map<String, Usuario> mapaUsuarios, JSONObject usuario, String id, String nombre,
	                                         String apellido, String email, String password, String login, Rol rol) {
	    // Create and add the Administrador object to the map
	    mapaUsuarios.put(id, new Administrador(id, nombre, apellido, email, password, login,  rol));
	}

	private static void cargarEmpleado(Map<String, Usuario> mapaUsuarios, JSONObject usuario, String id, String nombre,
	                                    String apellido, String email, String password, String login, Rol rol) {
	    // Create and add the Empleado object to the map
	    mapaUsuarios.put(id, new Empleado(id, nombre, apellido, email, password, login,rol));
	}

	private static void cargarUsuarioComun(Map<String, Usuario> mapaUsuarios, JSONObject usuario, String id, String nombre,
	                                       String apellido, String email, String password, String login, Rol rol) {
	    // Extract additional attributes specific to UsuarioComun
	    boolean verificado = usuario.getBoolean("verificado");
	    float topeDeCompra = usuario.getFloat("topeDeCompra");
	    // Create and add the UsuarioComun object to the map
	    mapaUsuarios.put(id, new UsuarioComun(id, nombre, apellido, email, password, login, rol, null, null, null, verificado, topeDeCompra));
	}


	public static void cargarGaleria(Galeria galeria) {
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
		
		//Carga las verificaciones 
		try {
		String jsonVerificaciones = new String(Files.readAllBytes(Paths.get(VERIFICACIONES__FILE)));
	    JSONArray jVerificaciones = new JSONArray(jsonVerificaciones);
		cargarVerificaciones(galeria, jVerificaciones);
		} catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error leyendo el archivo de verificaciones.");
        }
		
		
        
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
