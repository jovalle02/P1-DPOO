package consola;

import auth.Rol;
import consola.auth.ConsolaAuth;
import logica.Galeria;
import piezas.Pieza;
import usuarios.Usuario;

public class ConsolaGaleria extends ConsolaBasica {
	private Galeria galeria;
	private boolean autenticado = false;
	private Usuario usuario;

	public void correrAplicacion() {

        galeria = new Galeria( );

        if (isAutenticado()) {
			if (usuario.getRol().equals(Rol.ADMIN)) {
				menuAdministrador();
			} else if (usuario.getRol().equals(Rol.EMPLEADO)) {
				menuEmpleado();
			} else if (usuario.getRol().equals(Rol.COMUN)) {
				menuUsuario();
			}

        } else {
        	ConsolaAuth auth = new ConsolaAuth();
        	usuario = auth.iniciar();
			setAutenticado(true);
			correrAplicacion();
        }


	}

	private void menuAdministrador() {
		int opcion = mostrarMenu("Galeria y Casa de Subastas (ADMIN)", new String[]{"Añadir una Pieza", "Eliminar una Pieza", "Vender una pieza", "Consultar inventario de la galeria", "Salir"});

		switch (opcion) {
			case 1:
				System.out.println("Añadir una Pieza");
				galeria.agregarPieza();
				System.out.println("La pieza se ha añadido exitosamente!");
				break;
			case 2:
				System.out.println("Eliminar una Pieza");
				String name = pedirCadenaAlUsuario("Ingrese el nombre de la pieza:");
				Pieza pieza = galeria.getPieza(name);
				galeria.eliminarPieza(pieza);
				System.out.println("La pieza se ha eliminado exitosamente!");
				break;
			case 3:
				System.out.println("Consultar inventario de la galeria");
				galeria.consultarInventario();
				break;
			case 4:
				System.out.println("Vender una pieza");
				galeria.consultarInventario();
				break;
			case 5:
				System.out.println("Gracias por usar la Galería y Casa de Subastas");
				autenticado = false;
				correrAplicacion();
				break;
			default:
				System.out.println("Opción inválida. Por favor, seleccione una opción válida.");
		}
		menuAdministrador();
	}

	private void menuEmpleado() {
		int opcion = mostrarMenu("Galeria y Casa de Subastas (EMPLEADO)", new String[]{"Consultar Pieza", "Realizar Oferta de Compra", "Consultar Historial de Compras", "Salir"});

		switch (opcion) {
			case 1:
				System.out.println("Consultar Pieza");
				menuEmpleado();
				break;
			case 2:
				System.out.println("Realizar Oferta de Compra");
				menuEmpleado();
				break;
			case 3:
				System.out.println("Consultar Historial de Compras");
				menuEmpleado();
				break;
			case 4:
				System.out.println("Gracias por usar la Galería y Casa de Subastas");
				autenticado = false;
				correrAplicacion();
				break;
			default:
				System.out.println("Opción inválida. Por favor, seleccione una opción válida.");
		}
	}

	public static void main(String[] args) {
		ConsolaGaleria cg = new ConsolaGaleria();
		cg.correrAplicacion();
	}

	public boolean isAutenticado() {
		return autenticado;
	}

	public void setAutenticado(boolean autenticado) {
		this.autenticado = autenticado;
	}



	private void menuUsuario() {
		int opcion = mostrarMenu("Galeria y Casa de Subastas", new String[]{"Consultar Pieza", "Realizar Oferta de Compra", "Consultar el Inventario de la galería.", "Salir"});

		switch (opcion) {
			case 1:
				System.out.println("Consultar Pieza");
				menuUsuario();
				break;
			case 2:
				System.out.println("Realizar Oferta de Compra");
				menuUsuario();
				break;
			case 3:
				System.out.println("Consultar Historial de Compras");
				menuUsuario();
				break;
			case 4:
				System.out.println("Gracias por usar la Galería y Casa de Subastas");
				autenticado = false;
				correrAplicacion();
				break;
			default:
				System.out.println("Opción inválida. Por favor, seleccione una opción válida.");
		}

	}

}
