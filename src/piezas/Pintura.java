package piezas;

public class Pintura extends Pieza {
    // Atributos específicos para pinturas
    private String tecnica;
    private String lienzo;
    private String estilo;

    // Constructor
    public Pintura(String id, String titulo, String autor, String anioCreacion, String lugarCreacion, Boolean exhibicion, double valor, Boolean valorfijo, String estado, double alto, double ancho,
                      String tecnica, String lienzo, String estilo) {
        super(id, "Pintura", titulo, autor, anioCreacion, lugarCreacion, exhibicion, valor, valorfijo, estado, alto, ancho);
        this.tecnica = tecnica;
        this.lienzo = lienzo;
        this.estilo = estilo;
    }

    // Getters y setters para los atributos específicos de pinturas
    public String getTecnica() {
        return tecnica;
    }

    public void setTecnica(String tecnica) {
        this.tecnica = tecnica;
    }

    public String getLienzo() {
        return lienzo;
    }

    public void setLienzo(String lienzo) {
        this.lienzo = lienzo;
    }

    public String getEstilo() {
        return estilo;
    }

    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }
}
