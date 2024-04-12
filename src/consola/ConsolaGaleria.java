package consola;

import logica.Galeria;

public class ConsolaGaleria extends ConsolaBasica {
	private Galeria galeria;
	private boolean autenticado = false;
	
	public void correrAplicacion() {
		
        galeria = new Galeria( );
        
        if (isAutenticado()) {
        	
        } else {
        	
        }
        
		
	}
	
	public static void main(String[] args) {
		ConsolaGaleria cg = new ConsolaGaleria();
		cg.correrAplicacion();
	}

	public boolean isAutenticado() {
		return autenticado;
	}

	public void setAutenticado(boolean autenticado) {
		this.autenticado = autenticado;
	}
	
}
