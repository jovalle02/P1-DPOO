import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import auth.Rol;
import consola.ConsolaGaleria;
import logica.Factura;
import logica.Galeria;
import piezas.Pieza;
import usuarios.UsuarioComun;

public class adminVerHistorialTest {

	private Galeria galeriaPrueba;	
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final ByteArrayInputStream inContent = new ByteArrayInputStream("101".getBytes());

	@BeforeEach
	void setUp() throws IOException {
        galeriaPrueba = new Galeria();
        galeriaPrueba.agregarEscultura("El David" , "Miguel Ángel", "1501", "Italia", true, 40000.0, false, "Reluciente y apoteósico.", 5.17, 1.0, 1.0, "Mármol", 5572.0, false, "Tener especial cuidado debido a su alto valor.");
        Pieza pieza = galeriaPrueba.getPieza("El David");
        List<Pieza> piezas = new ArrayList<Pieza>();
        piezas.add(pieza);
        List<Factura> facturas = new ArrayList<Factura>();
        UsuarioComun usuario = new UsuarioComun(
        	    "101",                // ID del usuario
        	    "Bruce",               // Nombre
        	    "Wayne",             // Apellido
        	    "bruce@wayne.com",    // Email
        	    "imbatman",       // password
        	    "battyman",        // Nombre de usuario o login
        	    Rol.COMUN,              // Rol del usuario
        	    piezas, // Historial de piezas
        	    piezas, // Piezas actuales en posesión
        	    facturas, // Lista de compras realizadas (facturas)
        	    true,                   // Estado de verificación del usuario
        	    500000.0f                 // Tope de compra
        	);
        facturas.add(new Factura(
        		"tarjeta",
        		40000.0,
        		usuario,
        		"25",
        		"El David",
        		"2023-01-20"
        		));
        galeriaPrueba.añadirUsuario(usuario);
        Map<String, Factura> historialDeCompras = new HashMap<String, Factura>();
        historialDeCompras.put("101", new Factura(
        		"tarjeta",
        		40000.0,
        		usuario,
        		"25",
        		"El David",
        		"2023-01-20"
        		));
        galeriaPrueba.setHistorialDeCompras(historialDeCompras);
        
        UsuarioComun user = (UsuarioComun)galeriaPrueba.getUsuarioId("101");
        System.out.println(user.getCompras());
        
        System.setOut(new PrintStream(outContent));

        System.setIn(new ByteArrayInputStream("101".getBytes()));
        
    }
	
	@Test
	void consultarUsuario() {
		ConsolaGaleria cg = new ConsolaGaleria();
		
		UsuarioComun user = (UsuarioComun) galeriaPrueba.getUsuarioId("101");
		
		cg.consultarUsuario(galeriaPrueba);
		
        String output = outContent.toString();
        assertTrue(output.contains("Usuario: Bruce Wayne"), "No existe el usuario"); // El usuario es Bruce Wayne

        assertTrue(output.contains("Nombre de Pintura:El David"), "No existe la pieza"); // Que el David sea una obra que tenga el usuario
        assertTrue(output.contains("Fecha de compra: 2023-01-20"), "La fecha de compra es erronea"); // Que el valor del inventario sea el valor de la obra
        
        assertTrue(output.contains("Nombre de Pintura:El David"), "No existe la pieza"); // Que el David sea una obra que tenga el usuario
        assertTrue(output.contains("Valor del inventario:40000.0"), "El valor del inventario no es valido"); // Que el valor del inventario sea el valor de la obra
        assertTrue(output.contains("Valor: 40000.0"), "El valor de la pieza no es valido"); // Como es la unica obra, que haya valido 40000.0
        

        System.setOut(originalOut);
        System.setIn(System.in);
       
	}
	
	
}
