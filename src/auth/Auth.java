package auth;

import java.util.List;

import exceptions.AccionNoAutorizadaException;
import usuarios.Usuario;

public class Auth {

	/*
	 * La función acceso revisa si el usuario tiene permisos para acceder a una funcion de la aplicacion
	 * Se le debe pasar una lista de roles.
	 * */
    public static boolean acceso(Usuario usuario, List<Rol> rolesAutorizados) throws AccionNoAutorizadaException {
        final Rol rolUsuario = usuario.getRol();
        
        if (rolesAutorizados.contains(rolUsuario)) {
            return true; // El acceso está autorizado
        } else {
            throw new AccionNoAutorizadaException();
        }
    }
}

