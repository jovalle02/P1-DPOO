package logica;

import usuarios.Usuario;

public class Factura {
	private String tipoDePago;
	private double valor;
	private Usuario comprador;
	private String id;
	private String idPieza;
	public Factura(String tipoDePago, double valor, Usuario comprador, String id, String idPieza) {
		super();
		this.tipoDePago = tipoDePago;
		this.valor = valor;
		this.comprador = comprador;
		this.id = id;
		this.idPieza= idPieza;
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
	public String getIdPieza() {
		return idPieza;
	}
	
}
