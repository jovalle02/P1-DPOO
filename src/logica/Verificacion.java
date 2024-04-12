package logica;

import piezas.Pieza;
import usuarios.UsuarioComun;

public class Verificacion {
	private UsuarioComun usuario;
	public Verificacion(UsuarioComun usuario, Pieza pieza) {
		super();
		this.usuario = usuario;
		this.pieza = pieza;
	}
	private Pieza pieza;
	
	public UsuarioComun getUsuario() {
		return usuario;
	}
	public Pieza getPieza() {
		return pieza;
	}
}
