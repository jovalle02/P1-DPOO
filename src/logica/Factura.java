package logica;

import usuarios.Usuario;

public class Factura {
	private String tipoDePago;
	private double valor;
	private Usuario comprador;
	public Factura(String tipoDePago, double valor, Usuario comprador) {
		super();
		this.tipoDePago = tipoDePago;
		this.valor = valor;
		this.comprador = comprador;
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
	
}
