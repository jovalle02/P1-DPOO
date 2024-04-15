package consola;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Iterator;

/**
 * Esta es una clase abstracta que implementa métodos útiles para todas las consolas de la aplicación.
 */
public abstract class ConsolaBasica
{
    /**
     * Le pide al usuario que ingrese una cadena de caracteres
     * @param mensaje El mensaje con el que se solicita la información
     * @return La cadena introducida por el usuario
     */
    protected String pedirCadenaAlUsuario( String mensaje )
    {
        try
        {
            System.out.print( mensaje + ": " );
            BufferedReader reader = new BufferedReader( new InputStreamReader( System.in ) );
            String input = reader.readLine( );
            return input;
        }
        catch( IOException e )
        {
            System.out.println( "Error leyendo de la consola" );
        }
        return "error";
    }

    /**
     * Le pide confirmación al usuario, indicándole que debe responder 'si' o 'no'.
     * @param mensaje El mensaje con el que se solicita la información
     * @return Retorna true únicamente si el usuario responde 'sí', 'si' o 'si', independientemente de las minúsculas y las mayúsculas. Retorna false en cualquier otro caso.
     */
    protected boolean pedirConfirmacionAlUsuario( String mensaje )
    {
        try
        {
            System.out.print( mensaje + " (Responda 'si' o 'no' ) " );
            BufferedReader reader = new BufferedReader( new InputStreamReader( System.in ) );
            String input = reader.readLine( ).toLowerCase( );
            boolean respuesta = false;
            if( input.equals( "si" ) || input.equals( "sí" ) || input.equals( "s" ) )
                respuesta = true;

            return respuesta;
        }
        catch( IOException e )
        {
            System.out.println( "Error leyendo de la consola" );
        }
        return false;
    }

    /**
     * Le pide al usuario que ingrese un número que no puede tener cifras decimales
     * @param mensaje El mensaje con el que se solicita la información
     * @return El valor introducido por el usuario
     */
    protected int pedirEnteroAlUsuario( String mensaje )
    {
        int valorResultado = Integer.MIN_VALUE;
        while( valorResultado == Integer.MIN_VALUE )
        {
            try
            {
                System.out.print( mensaje + ": " );
                BufferedReader reader = new BufferedReader( new InputStreamReader( System.in ) );
                String input = reader.readLine( );
                int numero = Integer.parseInt( input );
                valorResultado = numero;
            }
            catch( NumberFormatException nfe )
            {
                System.out.println( "El valor digitado no es un entero" );
            }
            catch( IOException e )
            {
                System.out.println( "Error leyendo de la consola" );
            }
        }
        return valorResultado;
    }

    /**
     * Le pide al usuario que ingrese un número que puede tener cifras decimales
     * @param mensaje El mensaje con el que se solicita la información
     * @return El valor introducido por el usuario
     */
    protected double pedirNumeroAlUsuario( String mensaje )
    {
        double valorResultado = Integer.MIN_VALUE;
        while( valorResultado == Integer.MIN_VALUE )
        {
            try
            {
                System.out.print( mensaje + ": " );
                BufferedReader reader = new BufferedReader( new InputStreamReader( System.in ) );
                String input = reader.readLine( );
                double numero = Double.parseDouble( input );
                valorResultado = numero;
            }
            catch( NumberFormatException nfe )
            {
                System.out.println( "El valor digitado no es un entero" );
            }
            catch( IOException e )
            {
                System.out.println( "Error leyendo de la consola" );
            }
        }
        return valorResultado;
    }

    /**
     * Le pide al usuario que seleccione una de las opciones que se le presenten
     * @param coleccionOpciones
     * @return Retorna la opción seleccionada (el valor, no su posición).
     */
    protected String pedirOpcionAlUsuario( Collection<? extends Object> coleccionOpciones )
    {
        String[] opciones = new String[coleccionOpciones.size( )];
        int pos = 0;
        for( Iterator<? extends Object> iterator = coleccionOpciones.iterator( ); iterator.hasNext( ); pos++ )
        {
            opciones[ pos ] = iterator.next( ).toString( );
        }

        System.out.println( "Seleccione una de las siguientes opciones:" );
        for( int i = 1; i <= opciones.length; i++ )
        {
            System.out.println( " " + i + ". " + opciones[ i - 1 ] );
        }

        String opcion = pedirCadenaAlUsuario( "\nEscriba el número que corresponde a la opción deseada" );
        try
        {
            int opcionSeleccionada = Integer.parseInt( opcion );
            if( opcionSeleccionada > 0 && opcionSeleccionada <= opciones.length )
                return opciones[ opcionSeleccionada - 1 ];
            else
            {
                System.out.println( "Esa no es una opción válida. Digite solamente números entre 1 y " + opciones.length );
                return pedirOpcionAlUsuario( coleccionOpciones );
            }
        }
        catch( NumberFormatException nfe )
        {
            System.out.println( "Esa no es una opción válida. Digite solamente números." );
            return pedirOpcionAlUsuario( coleccionOpciones );
        }
    }

    /**
     * Muestra un menú y le pide al usuario que seleccione una opción
     * @param nombreMenu El nombre del menu
     * @param opciones Las opciones que se le presentan al usuario
     * @return El número de la opción seleccionada por el usuario, contando desde 1
     */
    protected int mostrarMenu(String nombreMenu, String[] opciones) {
        int opcionSeleccionada = 0;
        boolean opcionValida = false;

        while (!opcionValida) {
            System.out.println("\n---------------------");
            System.out.println(nombreMenu);
            System.out.println("---------------------");

            for (int i = 1; i <= opciones.length; i++) {
                System.out.println(" " + i + ". " + opciones[i - 1]);
            }

            String opcion = pedirCadenaAlUsuario("Escoja la opción deseada");

            try {
                opcionSeleccionada = Integer.parseInt(opcion);
                if (opcionSeleccionada > 0 && opcionSeleccionada <= opciones.length) {
                    opcionValida = true;
                } else {
                    System.out.println("Esa no es una opción válida. Digite solamente números entre 1 y " + opciones.length);
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Esa no es una opción válida. Digite solamente números.");
            }
        }

        return opcionSeleccionada;
    }

}