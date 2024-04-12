package exceptions;

/**
 * Esta excepción se usa para indicar que se trató de comprar una pieza que no se encuentra disponible
 */
@SuppressWarnings("serial")
public class PiezaNoDisponibleException extends Exception{

	private String id;

	public PiezaNoDisponibleException(String id) {
		super();
		this.id = id;
	}
	
	@Override
	public String getMessage()
	{
		return "La pieza con el código " + id + " no se encuentra disponible para la venta.";
	}

}
