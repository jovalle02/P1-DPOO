package persistencia;

import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Files;

import org.json.JSONArray;
import org.json.JSONObject;

import logica.Verificacion;

public class VerificacionCompra {

	public VerificacionCompra() {
		
	}
	/*
	private void añadirVerificacion( Galeria galeria, Verificacion verificacion) {
		String jsonCompleto = new String( Files.readAllBytes( new File( "./datos/verificaciones.json" ).toPath( ) ) );
        JSONObject raiz = new JSONObject( jsonCompleto );
    	JSONArray jVerificaciones = new JSONArray( );
        for( Verificacion verificacion : galeria.getAviones( ) ) {
        	JSONObject jAvion = new JSONObject( );
            jAvion.put( NOMBRE_AVION, avion.getNombre() );
            jAvion.put( CAPACIDAD_AVION, avion.getCapacidad() );
            jAviones.put( jAvion );
        }
        jobject.put( "aviones", jAviones );
        PrintWriter pw = new PrintWriter( "./datos/verificaciones.json" );
	    jobject.write( pw, 2, 0 );
	    pw.close( );
    }
    */
}
