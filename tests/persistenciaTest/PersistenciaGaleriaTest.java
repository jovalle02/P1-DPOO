package persistenciaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import logica.Galeria;
import logica.Subasta;
import logica.Verificacion;
import persistencia.CentralPersistencia;
import piezas.Pieza;
import usuarios.Usuario;

public class PersistenciaGaleriaTest {
	private static final String INVENTARIO__FILE = "datos_test/salvar/inventario.json";
	private static final String HISTORIAL__FILE = "datos_test/salvar/historial.json";
	private static final String USUARIOS__FILE = "datos_test/salvar/usuarios.json";
	private static final String VERIFICACIONES__FILE = "datos_test/salvar/verificaciones_compra.json";
	private static final String FACTURAS__FILE = "datos_test/salvar/facturas.json";
	private static final String SUBASTAS__FILE = "datos_test/salvar/subastas.json";
	private static final String ARTISTAS__FILE = "datos_test/salvar/artistas.json";
	private Galeria galeriaTest;
	@BeforeEach
	void setUp() {
		galeriaTest = new Galeria();
		//Carga las piezas
		Map<String, Pieza> inventario = galeriaTest.getInventario();
		Map<String, Pieza> historial = galeriaTest.getHistorial();
		
		try { 
		String jsonDataInventario = new String(Files.readAllBytes(Paths.get(INVENTARIO__FILE)));
        JSONArray jPiezasInventario = new JSONArray(jsonDataInventario);
        CentralPersistencia.cargarPiezas(galeriaTest, jPiezasInventario, inventario);
        
        String jsonDataHistorial = new String(Files.readAllBytes(Paths.get(HISTORIAL__FILE)));
        JSONArray jPiezasHistorial = new JSONArray(jsonDataHistorial);
        CentralPersistencia.cargarPiezas(galeriaTest, jPiezasHistorial, historial);
		} catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error leyendo el archivo de piezas.");
        }
		
		//Carga los usuarios 
				Map<String,Usuario> usuarios = galeriaTest.getUsuarios();
				try { 
					String jsonDataUsuarios = new String(Files.readAllBytes(Paths.get(USUARIOS__FILE)));
			        JSONObject jPiezasUsuarios = new JSONObject(jsonDataUsuarios);
			        CentralPersistencia.cargarUsuarios(galeriaTest, jPiezasUsuarios, usuarios);

					} catch (IOException e) {
			            e.printStackTrace();
			            System.out.println("Error leyendo el archivo de usuarios.");
			        }
				
		//Carga las verificaciones 
		try {
		String jsonVerificaciones = new String(Files.readAllBytes(Paths.get(VERIFICACIONES__FILE)));
	    JSONArray jVerificaciones = new JSONArray(jsonVerificaciones);
		CentralPersistencia.cargarVerificaciones(galeriaTest, jVerificaciones);
		} catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error leyendo el archivo de verificaciones.");
        }
		
		//Carga las facturas
		try {
			String jsonFacturas = new String(Files.readAllBytes(Paths.get(FACTURAS__FILE)));
		    JSONArray jFacturas = new JSONArray(jsonFacturas);
		    for(int i=0; i<jFacturas.length();i++	) {
		    	CentralPersistencia.cargarFacturas(galeriaTest, jFacturas.getJSONObject(i));
		    }
			CentralPersistencia.agregarFacturasUsuarios(galeriaTest);
			} catch (IOException e) {
	            e.printStackTrace();
	            System.out.println("Error leyendo el archivo de facturas.");
	        }
		
		//Carga las subastas.
		try {
			String jsonSubastas = new String(Files.readAllBytes(Paths.get(SUBASTAS__FILE)));
		    JSONArray jSubastas = new JSONArray(jsonSubastas);
		    CentralPersistencia.cargarSubastas(galeriaTest, jSubastas);

			} catch (IOException e) {
	            e.printStackTrace();
	            System.out.println("Error leyendo el archivo de subastas.");
	        }
		
		//Carga los artistas
		try {
			String jsonArtistas = new String(Files.readAllBytes(Paths.get(ARTISTAS__FILE)));
		    JSONArray jArtistas = new JSONArray(jsonArtistas);
		    CentralPersistencia.cargarArtistas(galeriaTest, jArtistas);
		}catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error leyendo el archivo de artistas.");
        }
	}
	
	@Test
	void testSalvarUsuarios() throws IOException {
		CentralPersistencia.salvarUsuarios(galeriaTest.getUsuarios(), USUARIOS__FILE);
		Galeria galeriaTest2 = new Galeria();
		String jsonDataUsuarios = new String(Files.readAllBytes(Paths.get(USUARIOS__FILE)));
        JSONObject jPiezasUsuarios = new JSONObject(jsonDataUsuarios);
		CentralPersistencia.cargarUsuarios(galeriaTest2, jPiezasUsuarios,galeriaTest2.getUsuarios());
		assertEquals(galeriaTest2.getUsuarios().keySet(),galeriaTest.getUsuarios().keySet(), "Los usuarios no se han guardado correctamente.");
	}
	
	@Test
	void testSalvarPiezas() throws Exception {
		Map<String, Pieza> map1 = galeriaTest.getInventario();
		CentralPersistencia.salvarPiezas(map1, INVENTARIO__FILE);
		Galeria galeria2 = new Galeria();
		Map<String, Pieza> map2 = galeria2.getInventario();
		String jsonDataInventario = new String(Files.readAllBytes(Paths.get(INVENTARIO__FILE)));
        JSONArray jPiezasInventario = new JSONArray(jsonDataInventario);
		CentralPersistencia.cargarPiezas(galeria2, jPiezasInventario, map2);
		assertEquals(map1.keySet(),map2.keySet(), "Las piezas no se guardaron correctamente");
	}
	
	@Test
	void testSalvarArtistas() throws Exception{
		Set<String> set1 = galeriaTest.getSetArtistas();
		CentralPersistencia.salvarArtistas(galeriaTest, ARTISTAS__FILE);
		Galeria galeria2 = new Galeria();
		String jsonArtistas = new String(Files.readAllBytes(Paths.get(ARTISTAS__FILE)));
	    JSONArray jArtistas = new JSONArray(jsonArtistas);
		CentralPersistencia.cargarArtistas(galeria2, jArtistas);
		assertEquals(set1,galeria2.getSetArtistas());
	}
	
	@Test
	void testSalvarSubastas()throws Exception{
		List<Subasta> subastas1 = galeriaTest.getSubastas();
		CentralPersistencia.salvarSubastas(galeriaTest, SUBASTAS__FILE);
		List<String> list1 = new ArrayList<String>();
		for(Subasta subasta: subastas1) {
			list1.add(subasta.getId());
		}
		
		Galeria galeria2 = new Galeria();
		String jsonSubastas = new String(Files.readAllBytes(Paths.get(SUBASTAS__FILE)));
        JSONArray jSubastas= new JSONArray(jsonSubastas);
		CentralPersistencia.cargarSubastas(galeria2, jSubastas);
		
		List<String>list2= new ArrayList<String>();
		for(Subasta subasta: galeria2.getSubastas()) {
			list2.add(subasta.getId());
		}
		assertEquals(list1,list2, "Las subastas no se guardaron correctamente");
	}
	
	@Test
	void testSalvarVerificaciones()throws Exception{
		List<Verificacion> verificaciones1 = galeriaTest.getVerificaciones();
		CentralPersistencia.salvarVerificaciones(galeriaTest, verificaciones1,VERIFICACIONES__FILE);
		List<String> list1 = new ArrayList<String>();
		for(Verificacion verificacion: verificaciones1) {
			String stringV = verificacion.getUsuario().getId()+ verificacion.getPieza().getId();
			list1.add(stringV);
		}
		
		Galeria galeria2 = new Galeria();
		String jsonVerificaciones = new String(Files.readAllBytes(Paths.get(VERIFICACIONES__FILE)));
        JSONArray jVerificaciones= new JSONArray(jsonVerificaciones);
		CentralPersistencia.cargarVerificaciones(galeria2, jVerificaciones);
		
		List<String>list2= new ArrayList<String>();
		for(Verificacion verificacion: galeria2.getVerificaciones()) {
			String stringV = verificacion.getUsuario().getId()+ verificacion.getPieza().getId();
			list2.add(stringV);
		}
		assertEquals(list1,list2, "Las verificaciones no se guardaron correctamente");
	}
}
