package usuarios;

import java.util.ArrayList;
import java.util.List;

import auth.Rol;
import logica.Factura;
import piezas.Pieza;

public class UsuarioComun extends Usuario {
	private List<Pieza> historial = new ArrayList<Pieza>();
	private List <Pieza>piezasActuales = new ArrayList<Pieza>();
	private List<Factura>compras = new ArrayList<Factura>();
	public UsuarioComun(String id, String nombre, String apellido, String email, String password, String login, Rol rol, List<Pieza> historial, List<Pieza> piezasActuales, List<Factura> compras) {
		super(id, nombre, apellido, email, password, login, rol);
		this.historial = historial;
		this.piezasActuales = piezasActuales;
		this.compras = compras;
	}
	public List<Pieza> getHistorial() {
		return historial;
	}
	public void setHistorial(List<Pieza> historial) {
		this.historial = historial;
	}
	public List<Pieza> getPiezasActuales() {
		return piezasActuales;
	}
	public void setPiezasActuales(List<Pieza> piezasActuales) {
		this.piezasActuales = piezasActuales;
	}
	public List<Factura> getCompras() {
		return compras;
	}

}
