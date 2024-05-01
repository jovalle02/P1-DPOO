package logica;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import piezas.Pieza;

public class Artista {
	private String nombre;
	private Map<String, Pieza>piezasHechas;
	/* Se almacenarán las compras que involucren piezas del artista*/
	private List<Factura> piezasVendidas; 
	public Artista(String nombre) {
		super();
		this.nombre = nombre;
		this.piezasHechas = new HashMap<String, Pieza>();
		this.piezasVendidas = new ArrayList<Factura>();
	}
	public String getNombre() {
		return nombre;
	}
	public Map<String, Pieza> getPiezasHechas() {
		return piezasHechas;
	}
	public List<Factura> getPiezasVendidas() {
		return piezasVendidas;
	}
	
	public void agregarPiezaVendida(Factura factura) {
		piezasVendidas.add(factura);
	}
	public void agregarPiezaHecha(Pieza pieza) {
		piezasHechas.put(pieza.getId(), pieza);
	}
	
}

