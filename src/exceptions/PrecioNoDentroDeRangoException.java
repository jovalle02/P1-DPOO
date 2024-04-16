package exceptions;

/**
 * Esta excepción se usa para indicar que se trató de comprar una pieza que no se encuentra disponible
 */
@SuppressWarnings("serial")
public class PrecioNoDentroDeRangoException extends Exception{

	private float valorMinimo;

	public PrecioNoDentroDeRangoException(float valorMinimo) {
		super();
		this.valorMinimo = valorMinimo;
	}
	
	@Override
	public String getMessage()
	{
		return "Debes ofrecer más de." + String.valueOf(valorMinimo);
	}

}
