package logica;

import java.util.ArrayList;
import java.util.List;

import piezas.Pieza;
import usuarios.Usuario;

public class Subasta {
	public Subasta(float valorMinimo, float valorMaximo, Usuario operador, Pieza piezaSubastada) {
		super();
		this.valorMinimo = valorMinimo;
		this.valorMaximo = valorMaximo;
		this.mayorOfrecido = 0f;
		this.operador = operador;
		this.ofertadores = new ArrayList<Usuario>();
		this.piezaSubastada = piezaSubastada;
	}
	
	private float valorMinimo;
	private float valorMaximo;
	private float mayorOfrecido;
	private Usuario operador;
	private List<Usuario> ofertadores;
	private Usuario clienteMaximoOfrecido;
	private Pieza piezaSubastada;
	public float getValorMinimo() {
		return valorMinimo;
	}
	public void setValorMinimo(float valorMinimo) {
		this.valorMinimo = valorMinimo;
	}
	public float getValorMaximo() {
		return valorMaximo;
	}
	public void setValorMaximo(float valorMaximo) {
		this.valorMaximo = valorMaximo;
	}
	public float getMayorOfrecido() {
		return mayorOfrecido;
	}
	public void setMayorOfrecido(float mayorOfrecido) {
		this.mayorOfrecido = mayorOfrecido;
	}
	public Usuario getOperador() {
		return operador;
	}
	public void setOperador(Usuario operador) {
		this.operador = operador;
	}
	public List<Usuario> getOfertadores() {
		return ofertadores;
	}
	public void setOfertadores(List<Usuario> ofertadores) {
		this.ofertadores = ofertadores;
	}
	public Usuario getClienteMaximoOfrecido() {
		return clienteMaximoOfrecido;
	}
	public void setClienteMaximoOfrecido(Usuario clienteMaximoOfrecido) {
		this.clienteMaximoOfrecido = clienteMaximoOfrecido;
	}
	public Pieza getPiezaSubastada() {
		return piezaSubastada;
	}
	public void setPiezaSubastada(Pieza piezaSubastada) {
		this.piezaSubastada = piezaSubastada;
	}
}
