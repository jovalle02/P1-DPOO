package persistencia;

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
	        String tipoUsuario = usuario.getRol();
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
	        jPieza.put("titulo", pieza.getTitulo());
	        jPieza.put("ano", pieza.getAnioCreacion());
	        jPieza.put("lugarDeCreacion", pieza.getLugarCreacion());
	        jPieza.put("estado", pieza.getEstado());
	        jPieza.put("valorFijo", pieza.isValorfijo());
	        jPieza.put("disponible", pieza.isDisponible());
	        jPieza.put("vendida", pieza.isVendida());
	        jPieza.put("precio", pieza.getValor());
	        jPieza.put("exhibicion", pieza.isExhibicion());
	        jPieza.put("descripcion", pieza.getEstado());

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
	}

	private void salvarPintura(Pintura pintura, JSONObject jPieza) {
	    jPieza.put("tipo", "Pintura");
	    jPieza.put("alto", pintura.getAlto());
	    jPieza.put("ancho", pintura.getAncho());
	    jPieza.put("tecnica", pintura.getTecnica());
	    jPieza.put("lienzo", pintura.getLienzo());
	    jPieza.put("estilo", pintura.getEstilo());
	}

}
