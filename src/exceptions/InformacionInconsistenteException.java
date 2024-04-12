package exceptions;

/**
 * Esta clase se usa para indicar que, al cargar un archivo, se encontraron inconsistencias bien sea dentro del archivo, o entre el archivo y el estado de la aerolínea.
 */
@SuppressWarnings("serial")
public class InformacionInconsistenteException extends Exception
{

    public InformacionInconsistenteException( String mensaje )
    {
        super( mensaje );
    }

}
