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
	private boolean verificado;
	private float topeDeCompra;
	public UsuarioComun(String id, String nombre, String apellido, String email, String password, String login, Rol rol, List<Pieza> historial, List<Pieza> piezasActuales, List<Factura> compras, boolean verificado, float topeDeCompra ) {
		super(id, nombre, apellido, email, password, login, rol);
		this.historial = historial;
		this.piezasActuales = piezasActuales;
		this.compras = compras;
		this.verificado = verificado;
		this.topeDeCompra  = topeDeCompra;
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
	public boolean isVerificado() {
		return verificado;
	}
	public void setVerificado(boolean verificado) {
		this.verificado = verificado;
	}
	public float getTopeDeCompra() {
		return topeDeCompra;
	}
	public void setTopeDeCompra(float topeDeCompra) {
		this.topeDeCompra = topeDeCompra;
	}
    public void agregarCompra(Factura factura) {
        this.compras.add(factura);
    }

}
