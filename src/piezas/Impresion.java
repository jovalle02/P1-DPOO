package piezas;

public class Impresion extends Pieza {
    // Atributos específicos para impresiones
    private String tipoImpresion;
    private String tamano;
    private String calidad;

    // Constructor
    public Impresion(String id, String titulo, String autor, String anioCreacion, String lugarCreacion, Boolean exhibicion, double valor, Boolean valorfijo,String estado, double alto, double ancho,
                      String tipoImpresion, String tamano, String calidad, boolean disponible, boolean vendida) {
        super(id, "Impresion", titulo, autor, anioCreacion, lugarCreacion, exhibicion, valor, valorfijo,estado,alto,ancho, disponible, vendida);
        this.tipoImpresion = tipoImpresion;
        this.tamano = tamano;
        this.calidad = calidad;
    }

    // Getters y setters para los atributos específicos de impresiones
    public String getTipoImpresion() {
        return tipoImpresion;
    }

    public void setTipoImpresion(String tipoImpresion) {
        this.tipoImpresion = tipoImpresion;
    }

    public String getTamano() {
        return tamano;
    }

    public void setTamano(String tamano) {
        this.tamano = tamano;
    }

    public String getCalidad() {
        return calidad;
    }

    public void setCalidad(String calidad) {
        this.calidad = calidad;
    }
}
