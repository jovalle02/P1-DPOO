package persistencia;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import logica.Galeria;
import piezas.Escultura;
import piezas.Fotografia;
import piezas.Impresion;
import piezas.Pieza;
import piezas.Pintura;
import piezas.Video;
import usuarios.Usuario;
import usuarios.UsuarioComun;

public class CentralPersistencia {
	private void salvarUsuarios(Galeria galeria, JSONObject jobject) {
	    JSONArray jUsuarios = new JSONArray();
	    for (Map.Entry<String, Usuario> entry : galeria.getUsuarios().entrySet()) {
	    	Usuario usuario = entry.getValue();
	        JSONObject jUsuario = new JSONObject();
	        jUsuario.put("login", usuario.getLogin());
	        jUsuario.put("password", usuario.getPassword());
	        jUsuario.put("nombre", usuario.getNombre());
	        jUsuario.put("apellido", usuario.getApellido());
	        jUsuario.put("email", usuario.getEmail());
	        String tipoUsuario = usuario.getRol().name();
	        jUsuario.put("tipoUsuario", tipoUsuario);
	        if(tipoUsuario.equals("comun")) {
	        	salvarComun((UsuarioComun)usuario, jUsuario);
	        }
	        jUsuarios.put(jUsuario);
	    }
	    jobject.put("usuarios", jUsuarios);
	}
	private void salvarComun(UsuarioComun usuario, JSONObject jUsuario) {
		jUsuario.put("verificado", usuario.isVerificado());
		jUsuario.put("topeDeCompra", usuario.getTopeDeCompra());}
	
	private void salvarPiezas(Map<String, Pieza> mapaPiezas, JSONObject jobject) {
	    JSONArray jPiezas = new JSONArray();
	    for (Map.Entry<String, Pieza> entry : mapaPiezas.entrySet()) {
	        Pieza pieza = entry.getValue();
	        JSONObject jPieza = new JSONObject();
	        jPieza.put("id", pieza.getId());
	        jPieza.put("tipo", pieza.getTipo());
	        jPieza.put("titulo", pieza.getTitulo());
	        jPieza.put("autor", pieza.getAutor());
	        jPieza.put("ano", pieza.getAnioCreacion());
	        jPieza.put("lugarDeCreacion", pieza.getLugarCreacion());
	        jPieza.put("estado", pieza.getEstado());
	        jPieza.put("valorFijo", pieza.isValorfijo());
	        jPieza.put("disponible", pieza.isDisponible());
	        jPieza.put("vendida", pieza.isVendida());
	        jPieza.put("valor", pieza.getValor());
	        jPieza.put("exhibicion", pieza.isExhibicion());

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
	    jobject.put("piezas", jPiezas);
	}


	private void salvarImpresion(Impresion impresion, JSONObject jPieza) {
	    jPieza.put("tipo", "Impresion");
	    jPieza.put("alto", impresion.getAlto());
	    jPieza.put("ancho", impresion.getAncho());
	}

	private void salvarFotografia(Fotografia fotografia, JSONObject jPieza) {
	    jPieza.put("tipo", "Fotografia");
	    jPieza.put("alto", fotografia.getAlto());
	    jPieza.put("ancho", fotografia.getAncho());
	    jPieza.put("formato", fotografia.getFormato());
	    jPieza.put("tecnica", fotografia.getTecnica());
	    jPieza.put("resolucion", fotografia.getResolucion());



	}

	private void salvarVideo(Video video, JSONObject jPieza) {
	    jPieza.put("tipo", "Video");
	    jPieza.put("alto", video.getAlto());
	    jPieza.put("ancho", video.getAncho());
	    jPieza.put("duracion", video.getDuracion());
	}

	private void salvarEscultura(Escultura escultura, JSONObject jPieza) {
	    jPieza.put("tipo", "Escultura");
	    jPieza.put("alto", escultura.getAlto());
	    jPieza.put("ancho", escultura.getAncho());
	    jPieza.put("profundidad", escultura.getProfundidad());
	    jPieza.put("materiales", escultura.getMateriales());
	    jPieza.put("peso", escultura.getPeso());
	    jPieza.put("electricidad", escultura.isNecesitaElectricidad());
	    jPieza.put("detallesInstalacion", escultura.getDetallesInstalacion());

	}

	private void salvarPintura(Pintura pintura, JSONObject jPieza) {
	    jPieza.put("tipo", "Pintura");
	    jPieza.put("alto", pintura.getAlto());
	    jPieza.put("ancho", pintura.getAncho());
	    jPieza.put("tecnica", pintura.getTecnica());
	    jPieza.put("lienzo", pintura.getLienzo());
	    jPieza.put("estilo", pintura.getEstilo());
	}
	public void cargarPiezas(Galeria galeria, JSONArray arrayPiezas,HashMap<String, Pieza> mapa) {
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
	private void cargarEscultura(Galeria galeria,HashMap<String, Pieza> mapa,  JSONObject pieza, String id, String titulo, String autor, String ano,
            String lugarDeCreacion, boolean exhibicion, double valor, boolean valorFijo, String estado,
            double alto, double ancho) {
			// Extract additional attributes specific to Escultura
			double profundidad = pieza.getDouble("profundidad");
			String materiales = pieza.getString("materiales");
			double peso = pieza.getDouble("peso");
			boolean electricidad = pieza.getBoolean("electricidad");
			String detallesInstalacion = pieza.getString("detallesInstalacion"); // Added
			// Add the Escultura object to the Galeria
			galeria.agregarPiezaJSON(new Escultura(id, titulo, autor, ano, lugarDeCreacion, exhibicion, valor, valorFijo,
			estado, alto, ancho, profundidad, materiales, peso, electricidad, detallesInstalacion),mapa);
			}

	private void cargarFotografia(Galeria galeria, HashMap<String, Pieza> mapa, JSONObject pieza, String id, String titulo, String autor, String ano,
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


	private void cargarImpresion(Galeria galeria, HashMap<String, Pieza> mapa, JSONObject pieza, String id, String titulo, String autor, String ano,
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

	private void cargarPintura(Galeria galeria, HashMap<String, Pieza> mapa, JSONObject pieza, String id, String titulo, String autor, String ano,
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


	private void cargarVideo(Galeria galeria, HashMap<String, Pieza> mapa, JSONObject pieza, String id, String titulo, String autor, String ano,
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



	
	

}
