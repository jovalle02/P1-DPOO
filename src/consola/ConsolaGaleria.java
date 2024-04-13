package consola;

import auth.Rol;
import consola.auth.ConsolaAuth;
import logica.Galeria;
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
		int opcion = mostrarMenu("Galeria y Casa de Subastas (ADMIN)", new String[]{"Consultar Pieza", "Realizar Oferta de Compra", "Consultar Historial de Compras", "Salir"});

		switch (opcion) {
			case 1:
				System.out.println("Consultar Pieza");
				menuAdministrador();
				break;
			case 2:
				System.out.println("Realizar Oferta de Compra");
				menuAdministrador();
				break;
			case 3:
				System.out.println("Consultar Historial de Compras");
				menuAdministrador();
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
		int opcion = mostrarMenu("Galeria y Casa de Subastas", new String[]{"Consultar Pieza", "Realizar Oferta de Compra", "Consultar Historial de Compras", "Salir"});

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
