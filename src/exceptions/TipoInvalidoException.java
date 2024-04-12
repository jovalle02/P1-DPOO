package exceptions;

/**
 * Esta excepción sirve para indicar que se intentó cargar o salvar información en un formato de archivo desconocido
 */
@SuppressWarnings("serial")
public class TipoInvalidoException extends Exception
{
    public TipoInvalidoException( String tipoArchivo )
    {
        super( "La cadena '" + tipoArchivo + "' no corresponde a un tipo válido de archivo para la persistencia" );
    }

}
