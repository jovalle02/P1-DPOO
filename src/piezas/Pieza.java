package piezas;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public abstract class Pieza {
    // Atributos
    private String id;
    private String tipo;
    private String titulo;
    private String autor;
    private String anioCreacion;
    private String lugarCreacion;
    private double valor;
    private boolean exhibicion;
    private boolean disponible;
    private boolean vendida;
    private boolean valorfijo;
    private String estado;
    private double alto;
    private double ancho;

    // Otros atributos según tus necesidades

    // Constructor
    public Pieza(String id, String tipo, String titulo, String autor, String anioCreacion, String lugarCreacion, Boolean exhibicion, double valor, Boolean valorfijo, String estado, double alto, double ancho, boolean disponible, boolean vendida) {
        this.id = id;
        this.tipo = tipo;
        this.titulo = titulo;
        this.autor = autor;
        this.anioCreacion = anioCreacion;
        this.lugarCreacion = lugarCreacion;
        this.exhibicion = exhibicion;
        this.valor = valor;
        this.disponible = disponible;
        this.vendida = vendida;
        this.valorfijo = valorfijo;
        this.estado=estado;
        this.alto= alto;
        this.ancho= ancho;
    }
 
    // Getters y setters
    public String getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getAnioCreacion() {
        return anioCreacion;
    }

    public void setAnioCreacion(String anioCreacion) {
        this.anioCreacion = anioCreacion;
    }

    public String getLugarCreacion() {
        return lugarCreacion;
    }

    public void setLugarCreacion(String lugarCreacion) {
        this.lugarCreacion = lugarCreacion;
    }

	public boolean isExhibicion() {
		return exhibicion;
	}

	public void setExhibicion(boolean exhibicion) {
		this.exhibicion = exhibicion;
	}

	public boolean isDisponible() {
		return disponible;
	}

	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}

	public boolean isVendida() {
		return vendida;
	}

	public void setVendida(boolean vendida) {
		this.vendida = vendida;
	}

	public boolean isValorfijo() {
		return valorfijo;
	}

	public void setValorfijo(boolean valorfijo) {
		this.valorfijo = valorfijo;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public double getAlto() {
		return alto;
	}

	public void setAlto(float alto) {
		this.alto = alto;
	}

	public double getAncho() {
		return ancho;
	}
	
	public boolean getIsFijo() {
		return valorfijo;
	}

	public void setAncho(float ancho) {
		this.ancho = ancho;
	}
    
}
