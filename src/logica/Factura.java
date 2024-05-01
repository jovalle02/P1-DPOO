package logica;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import usuarios.Usuario;

public class Factura {
	private String tipoDePago;
	private double valor;
	private Usuario comprador;
	private String id;
	private String idPieza;
	private String fecha;
	public Factura(String tipoDePago, double valor, Usuario comprador, String id, String idPieza) {
		super();
		this.tipoDePago = tipoDePago;
		this.valor = valor;
		this.comprador = comprador;
		this.id = id;
		this.idPieza= idPieza;
		
		//Obtiene la fecha actual y la guarda en la factura.
		Date date = Calendar.getInstance().getTime();  
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
		this.fecha = dateFormat.format(date);
	}
	
	//Este constructor solo debería ser usado cuando se vaya a cargar una factura desde CentralPersistencia. 
	//El constructor anterior se encarga de guardar la factura dada la fecha actual.
	public Factura(String tipoDePago, double valor, Usuario comprador, String id, String idPieza, String fecha) {
		super();
		this.tipoDePago = tipoDePago;
		this.valor = valor;
		this.comprador = comprador;
		this.id = id;
		this.idPieza= idPieza;
		this.fecha = fecha;
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
	public String getFecha() {
		return fecha;
	}
	
}
