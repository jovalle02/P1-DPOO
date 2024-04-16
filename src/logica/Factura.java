package logica;

import usuarios.Usuario;

public class Factura {
	private String tipoDePago;
	private double valor;
	private Usuario comprador;
	private String id;
	public Factura(String tipoDePago, double valor, Usuario comprador, String id) {
		super();
		this.tipoDePago = tipoDePago;
		this.valor = valor;
		this.comprador = comprador;
		this.id = id;
	}
	public String getTipoDePago() {
		return tipoDePago;
	}
	public double getValor() {
		return valor;
	}
	public Usuario getComprador() {
		return comprador;
	}
	public String getId() {
		return id;
	}
	
}
