package logica;

import java.util.HashMap;
import java.util.Map;

import piezas.Pieza;

public class Artista {
	private String nombre;
	private Map<String, Pieza>piezasHechas;
	/* Se almacenarán las compras que involucren piezas del artista*/
	private Map<String,Factura> piezasVendidas; 
	public Artista(String nombre) {
		super();
		this.nombre = nombre;
		this.piezasHechas = new HashMap<String, Pieza>();
		this.piezasVendidas = new HashMap<String, Factura>();
	}
	public String getNombre() {
		return nombre;
	}
	public Map<String, Pieza> getPiezasHechas() {
		return piezasHechas;
	}
	public Map<String,Factura> getPiezasVendidas() {
		return piezasVendidas;
	}
	
	public void agregarPiezaVendida(Factura factura) {
		piezasVendidas.put(factura.getId(),factura);
	}
	public void agregarPiezaHecha(Pieza pieza) {
		piezasHechas.put(pieza.getId(), pieza);
	}
	
}

