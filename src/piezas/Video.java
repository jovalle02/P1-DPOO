package piezas;

public class Video extends Pieza {
    // Atributos específicos para videos
    private String formato;
    private String duracion;
    private String calidad;

    // Constructor
    public Video(String id, String titulo, String autor, String anioCreacion, String lugarCreacion, Boolean exhibicion, double valor, Boolean valorfijo,
                 String formato, String duracion, String calidad) {
        super(id, "Video", titulo, autor, anioCreacion, lugarCreacion, exhibicion, valor, valorfijo);
        this.formato = formato;
        this.duracion = duracion;
        this.calidad = calidad;
    }

    // Getters y setters para los atributos específicos de videos
    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getCalidad() {
        return calidad;
    }

    public void setCalidad(String calidad) {
        this.calidad = calidad;
    }
}
