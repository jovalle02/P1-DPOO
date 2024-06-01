package consola.auth;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

import auth.Rol;
import usuarios.Administrador;
import usuarios.Empleado;
import usuarios.Usuario;
import usuarios.UsuarioComun;

public class UsuariosJson {
	
	private static UsuariosJson instance;
	private static final String USERS_FILE = "datos/usuarios.json";
	private JSONObject users;
	
	private UsuariosJson() {
		try {
            users = cargar(USERS_FILE);
        } catch (Exception e) {
            // Si el archivo no existe, lo crea
            try (FileWriter file = new FileWriter(USERS_FILE)) {
                file.write("{}");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
	}
	
	private JSONObject cargar(String archivo) throws IOException {
        String jsonCompleto = new String(Files.readAllBytes(new File(archivo).toPath()));
        return new JSONObject(jsonCompleto);
    }
	
	public static UsuariosJson getInstance() {
		if (instance == null) {
            instance = new UsuariosJson();
		}
		return instance;
	}
	
	public Usuario login(String username, String password) {
		Usuario usuario = null;
            if (users.has(username)) {
                JSONObject user = users.getJSONObject(username);
                if (user.getString("password").equals(password)) {
                	Rol role = Rol.fromString(user.getString("rol"));
                    if (user.getString("rol").equals(Rol.EMPLEADO.name())) {
                        usuario = new Empleado(user.getString("id"), user.getString("nombre"), user.getString("apellido"), user.getString("email"), password, username,role);
                    } else if (user.getString("rol").equals(Rol.COMUN.name())) {
                        usuario = new UsuarioComun(user.getString("id"), user.getString("nombre"), user.getString("apellido"), user.getString("email"), password, username,role, null, null, null,user.getBoolean("verificado"),user.getFloat("topeDeCompra"));
                    } else if (user.getString("rol").equals(Rol.ADMIN.name())) {
                        usuario = new Administrador(user.getString("id"), user.getString("nombre"), user.getString("apellido"), user.getString("email"), password, username,role);
                    }
                } 
            } 
		return usuario;
	}
	
	public boolean create(String username, String password, String nombre, String apellido, String email, Double topeDeCompra, String fechaNacimiento) {
		boolean result = true;
            if (users.has(username)) {
                result = false;
            } else {
                JSONObject user = new JSONObject();
                user.put("login", username);
                user.put("password", password);
                user.put("nombre", nombre);
                user.put("apellido", apellido);
                user.put("fechaNacimiento", fechaNacimiento);
                user.put("email", email);
                user.put("topeDeCompra", topeDeCompra);
                user.put("verificado", false);
                user.put("rol", Rol.COMUN.name());
                user.put("id", UUID.randomUUID().toString());
                JSONArray historialArray = new JSONArray();
                user.put("historial", historialArray);
                JSONArray piezasArray = new JSONArray();
                user.put("piezas_actuales", piezasArray);
                users.put(username, user);
                try (FileWriter file = new FileWriter(USERS_FILE)) {
                    file.write(users.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        
		return result;
	}
	
}
