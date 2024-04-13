package piezas;

public class Fotografia extends Pieza {
    // Atributos específicos para fotografías
    private String formato;
    private String tecnica;
    private double resolucion;

    // Constructor
    public Fotografia(String id, String titulo, String autor, String anioCreacion, String lugarCreacion, Boolean exhibicion, double valor, Boolean valorfijo,String estado, double alto, double ancho,
                      String formato, String tecnica, double resolucion) {
        super(id, "Fotografia", titulo, autor, anioCreacion, lugarCreacion, exhibicion, valor, valorfijo, estado, alto, ancho);
        this.formato = formato;
        this.tecnica = tecnica;
        this.resolucion = resolucion;
    }

    // Getters y setters para los atributos específicos de fotografías
    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public String getTecnica() {
        return tecnica;
    }

    public void setTecnica(String tecnica) {
        this.tecnica = tecnica;
    }

    public double getResolucion() {
        return resolucion;
    }

    public void setResolucion(double resolucion) {
        this.resolucion = resolucion;
    }
    
}