package exceptions;

/**
 * Esta excepción se usa para indicar que se trató de acceder a una accion a la que no se tiene permiso
 */
@SuppressWarnings("serial")
public class AccionNoAutorizadaException extends Exception{

	public AccionNoAutorizadaException() {
		super();
	}
	
	@Override
	public String getMessage()
	{
		return "No tiene permiso para realizar esta accion";
	}

}
