import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import auth.Rol;
import exceptions.PiezaNoDisponibleException;
import logica.Factura;
import logica.Galeria;
import logica.Verificacion;
import piezas.Pieza;
import usuarios.Usuario;
import usuarios.UsuarioComun;

public class VentasTest {
	private Galeria galeriaPrueba;
	private UsuarioComun usuarioPrueba;
	private Pieza piezaPrueba;
	
	@BeforeEach
	void setUp() throws Exception {
		//Se crea la geleria sobre la cual se van a inicializar las variables
		galeriaPrueba = new Galeria();
		List<Pieza> historial = new ArrayList<Pieza>();
		List <Pieza>piezasActuales = new ArrayList<Pieza>();
		List<Factura>compras = new ArrayList<Factura>();
		usuarioPrueba = new UsuarioComun("Jose", "Jose", "Ovalle", "ja.ovalle", "jose", "jose", Rol.COMUN, historial, piezasActuales, compras, true, 4000);
		galeriaPrueba.agregarPintura("Noche Estrellada", "Da Vincci", "2002", "Italia", true, 200, true, "Perfecto", 10, 10, "None", "None", "None");
		galeriaPrueba.añadirUsuario(usuarioPrueba);
	}
	
	@Test
    void testVentaPieza( ) throws PiezaNoDisponibleException
    {
		Pieza piezaPrueba = galeriaPrueba.getPieza("Noche Estrellada");
    	galeriaPrueba.realizarCompra(usuarioPrueba, piezaPrueba);
    	
    	//Verificar si el estado de disponibilidad de la pieza fue cambiado correctamente.
        assertEquals( false, galeriaPrueba.getPieza("Noche Estrellada").isDisponible(), "El estado de 'vendido' no fue cambiado exitosamente." );
        //Verificar si la verificacion se creo exitosamente.
        boolean existe = false;
        if (galeriaPrueba.getVerificaciones().size() != 0) {
        	existe = true;
        }
        assertEquals( true, existe, "La verificacion no fue añadida exitosamente." );
        //Confirmar la compra de la pieza
        Verificacion verificacion = galeriaPrueba.getVerificaciones().get(0);
        galeriaPrueba.confirmarVenta(verificacion, true, "Tarjeta Ex");
        //Confirmar que la verificacion sea eliminada de las confirmaciones tras su confirmacion
        boolean eliminada = false;
        if (galeriaPrueba.getVerificaciones().size() == 0) {
        	eliminada = true;
        }
        assertEquals( true, eliminada, "La verificacion no fue eliminada exitosamente." );
        //Verificar si la compra fue añadida correctamente al usuario
        List<Pieza> piezasDelUsuario = usuarioPrueba.getPiezasActuales();
        
        boolean hayPieza = false;
        for (Pieza pieza : piezasDelUsuario) {
			if (pieza.getId() == "Noche Estrellada") {
				hayPieza = true;
			}
		}
        assertEquals( true, hayPieza, "La pieza no fue añadida exitosamente a las piezas del usuario despues de la compra." );
        //Verificar si el recibo se adjunto correctamente.
        Map<String, Factura> facturasGaleria = galeriaPrueba.getHistorialDeCompras();
        assertEquals( false, facturasGaleria.isEmpty(), "La factura no fue añadida exitosamente al historial de compras de la galeria." );
        //Verificar si el estado de venta de la pieza fue cambiado correctamente.
        assertEquals( true, galeriaPrueba.getPieza("Noche Estrellada").isVendida(), "El estado de 'vendido' no fue cambiado exitosamente." );
        //Verificar si el usuario fue añadido exitosamente al mapa de compradores
        Map<String, Usuario> compradoresGaleria = galeriaPrueba.getCompradores();
        assertEquals( false, compradoresGaleria.isEmpty(), "La factura no fue añadida exitosamente al historial de compras de la galeria." );
    }
}
