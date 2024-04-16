package logica;

import java.util.ArrayList;
import java.util.List;

import piezas.Pieza;
import usuarios.Usuario;
import usuarios.UsuarioComun;

public class Subasta {
	private float valorMinimo;
	private float valorInicial;
	private float mayorOfrecido;
	private Usuario operador;
	private List<Usuario> ofertadores;
	private Usuario clienteMaximoOfrecido;
	private Pieza piezaSubastada;
	private boolean activa;
	
	public Subasta(float valorMinimo, float valorMaximo, Usuario operador, Pieza piezaSubastada) {
		super();
		this.valorMinimo = valorMinimo;
		this.valorInicial = valorMaximo;
		this.mayorOfrecido = valorMinimo;
		this.operador = operador;
		this.ofertadores = new ArrayList<Usuario>();
		this.piezaSubastada = piezaSubastada;
	}
	
	public void subastar(UsuarioComun user, float valor) throws Exception {
		
		if (this.isActiva()) {
			if (user.isVerificado()) {
				
				if (valor > this.getMayorOfrecido()) {
					this.nuevoSubastador(user, valor);
				} else {
					throw new Exception("Error");
				}
				
			} else {
				throw new Exception("El usuario no esta verificado por lo que no puede ofertar");
			}
		} else {
			throw new Exception("La subasta ya no esta recibiendo más ofertas");
		}
	}
	
	private void nuevoSubastador(UsuarioComun user, float valor) {
		this.setMayorOfrecido(valor);
		this.setClienteMaximoOfrecido(user);
		this.getOfertadores().add(user);
	}
	
	public void finalizarSubasta() throws Exception {
		if (this.isActiva()) {
			if (this.getMayorOfrecido() >= this.getValorMinimo()) {
				this.setActiva(false);
			} else {
				throw new Exception("La subasta aun no ha llegado a su valor minimo");
			}			
		} else {
			throw new Exception("La subasta ya ha finalizado");
		}
		
	}
	

	public float getValorMinimo() {
		return valorMinimo;
	}

	public float getValorInicial() {
		return valorInicial;
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

	public Usuario getClienteMaximoOfrecido() {
		return clienteMaximoOfrecido;
	}

	public void setClienteMaximoOfrecido(Usuario clienteMaximoOfrecido) {
		this.clienteMaximoOfrecido = clienteMaximoOfrecido;
	}

	public Pieza getPiezaSubastada() {
		return piezaSubastada;
	}

	public boolean isActiva() {
		return activa;
	}

	private void setActiva(boolean activa) {
		this.activa = activa;
	}
	
}
