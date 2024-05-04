import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import logica.Galeria;
import piezas.Pieza;

public class InventarioTest {
	private Galeria galeriaPrueba;	
	
	@BeforeEach
	void setUp() {
			galeriaPrueba= new Galeria();
	}
	
	@Test
	void testAsociarArtista(){
		galeriaPrueba.asociarArtista("Yayoi Kusama");
		assertNotEquals(null, galeriaPrueba.getArtistaNombre("Yayoi Kusama"), "No se están añadiendo correctamente los artistas inexistentes");

	}
	
	@Test
	void testAgregarEscultura() throws IOException {
		galeriaPrueba.agregarEscultura("El David" , "Miguel Ángel", "1501", "Italia", true, 40000.0, false, "Reluciente y apoteósico.", 5.17, 1.0, 1.0, "Mármol", 5572.0, false, "Tener especial cuidado debido a su alto valor.");
		Pieza testPieza = galeriaPrueba.getPieza("El David");
		assertNotEquals(null, testPieza, "La pieza no se ha añadido correctamente al inventario (valor nulo)");
		assertEquals("El David", testPieza.getTitulo(),"La pieza se ha añadido con datos incorrectos al inventario");
		Pieza testHistorial = galeriaPrueba.getHistorial().get("El David");
		assertNotEquals(null, testHistorial, "La pieza no se ha añadido correctamente al historial (valor nulo)");
		assertEquals("El David", testHistorial.getTitulo(),"La pieza se ha añadido con datos incorrectos al historial");
	}
	
	@Test
	void testAgregarFotografia() throws IOException {
	    galeriaPrueba.agregarFotografia("Fotografía1" , "AutorFotografía", "2023", "PaísFotografía", true, 1000.0, false, "Excelente estado.", 10.0, 8.0, "JPEG", "Digital", 300.0);
	    Pieza testPieza = galeriaPrueba.getPieza("Fotografía1");
	    assertNotEquals(null, testPieza, "La fotografía no se ha añadido correctamente al inventario (valor nulo)");
	    assertEquals("Fotografía1", testPieza.getTitulo(),"La fotografía se ha añadido con datos incorrectos al inventario");
	    Pieza testHistorial = galeriaPrueba.getHistorial().get("Fotografía1");
	    assertNotEquals(null, testHistorial, "La fotografía no se ha añadido correctamente al historial (valor nulo)");
	    assertEquals("Fotografía1", testHistorial.getTitulo(),"La fotografía se ha añadido con datos incorrectos al historial");
	}

	@Test
	void testAgregarImpresion() throws IOException {
	    galeriaPrueba.agregarImpresion("Impresion1" , "AutorImpresion", "2022", "PaísImpresion", true, 1500.0, false, "Buen estado.", 20.0, 16.0, "Digital", "A4", "Alta");
	    Pieza testPieza = galeriaPrueba.getPieza("Impresion1");
	    assertNotEquals(null, testPieza, "La impresión no se ha añadido correctamente al inventario (valor nulo)");
	    assertEquals("Impresion1", testPieza.getTitulo(),"La impresión se ha añadido con datos incorrectos al inventario");
	    Pieza testHistorial = galeriaPrueba.getHistorial().get("Impresion1");
	    assertNotEquals(null, testHistorial, "La impresión no se ha añadido correctamente al historial (valor nulo)");
	    assertEquals("Impresion1", testHistorial.getTitulo(),"La impresión se ha añadido con datos incorrectos al historial");
	}

	@Test
	void testAgregarVideo() throws IOException {
	    galeriaPrueba.agregarVideo("Video1" , "AutorVideo", "2021", "PaísVideo", true, 2000.0, false, "Buen estado.", 1920.0, 1080.0, "MP4", "02:30", "Alta");
	    Pieza testPieza = galeriaPrueba.getPieza("Video1");
	    assertNotEquals(null, testPieza, "El video no se ha añadido correctamente al inventario (valor nulo)");
	    assertEquals("Video1", testPieza.getTitulo(),"El video se ha añadido con datos incorrectos al inventario");
	    Pieza testHistorial = galeriaPrueba.getHistorial().get("Video1");
	    assertNotEquals(null, testHistorial, "El video no se ha añadido correctamente al historial (valor nulo)");
	    assertEquals("Video1", testHistorial.getTitulo(),"El video se ha añadido con datos incorrectos al historial");
	}

	@Test
	void testAgregarPintura() throws IOException {
	    galeriaPrueba.agregarPintura("Pintura1" , "AutorPintura", "2020", "PaísPintura", true, 2500.0, false, "Buen estado.", 24.0, 18.0, "Óleo", "Lienzo", "Clásico");
	    Pieza testPieza = galeriaPrueba.getPieza("Pintura1");
	    assertNotEquals(null, testPieza, "La pintura no se ha añadido correctamente al inventario (valor nulo)");
	    assertEquals("Pintura1", testPieza.getTitulo(),"La pintura se ha añadido con datos incorrectos al inventario");
	    Pieza testHistorial = galeriaPrueba.getHistorial().get("Pintura1");
	    assertNotEquals(null, testHistorial, "La pintura no se ha añadido correctamente al historial (valor nulo)");
	    assertEquals("Pintura1", testHistorial.getTitulo(),"La pintura se ha añadido con datos incorrectos al historial");
	}

}
