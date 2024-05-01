package piezas;

public class Escultura extends Pieza {
    // Atributos específicos para esculturas
    private double alto;
    private double ancho;
    private double profundidad;
    private String materiales;
    private double peso;
    private boolean necesitaElectricidad;
    private String detallesInstalacion;

    // Constructor
    public Escultura(String id, String titulo, String autor, String anioCreacion, String lugarCreacion, Boolean exhibicion, double valor, Boolean valorfijo,String estado,
                     double alto, double ancho, double profundidad, String materiales, double peso,
                     boolean necesitaElectricidad, String detallesInstalacion, boolean disponible, boolean vendida) {
        super(id, "Escultura", titulo, autor, anioCreacion, lugarCreacion, exhibicion, valor, valorfijo, estado, alto, ancho, disponible, vendida);
        this.profundidad = profundidad;
        this.materiales = materiales;
        this.peso = peso;
        this.necesitaElectricidad = necesitaElectricidad;
        this.detallesInstalacion = detallesInstalacion;
    }
    
    public Escultura(String id, String titulo, String autor, String anioCreacion, String lugarCreacion, Boolean exhibicion, double valor, Boolean valorfijo,String estado,
            double alto, double ancho, double profundidad, String materiales, double peso,
            boolean necesitaElectricidad, String detallesInstalacion, boolean disponible, boolean vendida, String fechaCreacion) {
super(id, "Escultura", titulo, autor, anioCreacion, lugarCreacion, exhibicion, valor, valorfijo, estado, alto, ancho, disponible, vendida);
this.profundidad = profundidad;
this.materiales = materiales;
this.peso = peso;
this.necesitaElectricidad = necesitaElectricidad;
this.detallesInstalacion = detallesInstalacion;
}

    // Getters y setters para los atributos específicos de esculturas
    public double getAlto() {
        return alto;
    }

    public void setAlto(double alto) {
        this.alto = alto;
    }

    public double getAncho() {
        return ancho;
    }

    public void setAncho(double ancho) {
        this.ancho = ancho;
    }

    public double getProfundidad() {
        return profundidad;
    }

    public void setProfundidad(double profundidad) {
        this.profundidad = profundidad;
    }

    public String getMateriales() {
        return materiales;
    }

    public void setMateriales(String materiales) {
        this.materiales = materiales;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public boolean isNecesitaElectricidad() {
        return necesitaElectricidad;
    }

    public void setNecesitaElectricidad(boolean necesitaElectricidad) {
        this.necesitaElectricidad = necesitaElectricidad;
    }

    public String getDetallesInstalacion() {
        return detallesInstalacion;
    }

    public void setDetallesInstalacion(String detallesInstalacion) {
        this.detallesInstalacion = detallesInstalacion;
    }   
}