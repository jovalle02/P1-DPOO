import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import auth.Rol;
import logica.Factura;
import logica.Galeria;
import logica.Subasta;
import piezas.Pieza;
import usuarios.UsuarioComun;

public class SubastaTest {
    private Galeria galeriaPrueba;
    private Subasta subasta;

    @BeforeEach
    void setUp() throws IOException {
        galeriaPrueba = new Galeria();
        UsuarioComun usuario = new UsuarioComun(
        	    "101",                // ID del usuario
        	    "Bruce",               // Nombre
        	    "Wayne",             // Apellido
        	    "bruce@wayne.com",    // Email
        	    "imbatman",       // Contraseña
        	    "battyman",        // Nombre de usuario o login
        	    Rol.COMUN,              // Rol del usuario (asumiendo que Rol es una enumeración con valores como COMUN, ADMIN, etc.)
        	    new ArrayList<Pieza>(), // Historial de piezas
        	    new ArrayList<Pieza>(), // Piezas actuales en posesión
        	    new ArrayList<Factura>(), // Lista de compras realizadas (facturas)
        	    true,                   // Estado de verificación del usuario
        	    500000.0f                 // Tope de compra
        	);
        galeriaPrueba.añadirUsuario(usuario);
        
        galeriaPrueba.agregarEscultura("El David" , "Miguel Ángel", "1501", "Italia", true, 40000.0, false, "Reluciente y apoteósico.", 5.17, 1.0, 1.0, "Mármol", 5572.0, false, "Tener especial cuidado debido a su alto valor.");
        Pieza pieza = galeriaPrueba.getPieza("El David");
        subasta = new Subasta(2000.0f, 1500.0f, usuario, pieza, "sub002");
        galeriaPrueba.getSubastas().add(subasta);
    }

    @Test
    void testRegistrarSubasta() {
        List<Subasta> subastas = galeriaPrueba.getSubastas();
        Subasta testSubasta = subastas.get(subastas.size() - 1); // Último item en la lista de subastas
        assertNotNull(testSubasta, "La subasta no se ha registrado correctamente en la galería (valor nulo)");
        assertEquals("sub002", testSubasta.getId(), "La subasta se ha registrado con datos incorrectos");
    }

    @Test
    void testRealizarOferta() {
        List<Subasta> subastas = galeriaPrueba.getSubastas();
        Subasta testSubasta = subastas.get(subastas.size() - 1);
        assertDoesNotThrow(() -> testSubasta.subastar( (UsuarioComun) galeriaPrueba.getUsuarioId("101"), 2100.0f));
        assertEquals(2100.0f, testSubasta.getMayorOfrecido(), "La oferta no se ha registrado correctamente");
    }

    @Test
    void testOfertaInsuficiente() {
        List<Subasta> subastas = galeriaPrueba.getSubastas();
        Subasta testSubasta = subastas.get(subastas.size() - 1);
        Exception exception = assertThrows(Exception.class, () -> testSubasta.subastar((UsuarioComun)galeriaPrueba.getUsuarioId("101"), 1400.0f));
        assertEquals("Error", exception.getMessage());
    }

    @Test
    void testFinalizarSubasta() throws Exception {
        List<Subasta> subastas = galeriaPrueba.getSubastas();
        Subasta testSubasta = subastas.get(subastas.size() - 1);
        testSubasta.subastar((UsuarioComun) galeriaPrueba.getUsuarioId("101"), 2100.0f);
        testSubasta.finalizarSubasta();
        assertFalse(testSubasta.isActiva(), "La subasta debería estar inactiva después de finalizar.");
    }
}
