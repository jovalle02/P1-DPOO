package piezas;

public abstract class Pieza {
    // Atributos
    private String id;
    private String tipo;
    private String titulo;
    private String autor;
    private String anioCreacion;
    private String lugarCreacion;
    private double valor;
    public boolean exhibicion;
    public boolean disponible;
    public boolean vendida;
    public boolean valorfijo;
    public String estado;
    // Otros atributos según tus necesidades

    // Constructor
    public Pieza(String id, String tipo, String titulo, String autor, String anioCreacion, String lugarCreacion, Boolean exhibicion, double valor, Boolean valorfijo) {
        this.id = id;
        this.tipo = tipo;
        this.titulo = titulo;
        this.autor = autor;
        this.anioCreacion = anioCreacion;
        this.lugarCreacion = lugarCreacion;
        this.exhibicion = exhibicion;
        this.valor = valor;
        this.disponible = true;
        this.vendida = false;
        this.valorfijo = valorfijo;
        
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
    
}
