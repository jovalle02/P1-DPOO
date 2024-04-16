package consola.auth;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

import org.json.JSONObject;

import auth.Rol;
import consola.ConsolaBasica;
import usuarios.Administrador;
import usuarios.Empleado;
import usuarios.Usuario;
import usuarios.UsuarioComun;

public class ConsolaAuth extends ConsolaBasica {

    private static final String USERS_FILE = "datos/usuarios.json";
    private boolean autenticado = false;
    private Usuario usuario;

    public ConsolaAuth() {
        // Constructor
    }

    public Usuario iniciar() {
        while (!autenticado) {
            System.out.println("Bienvenido a la Consola de Autenticación");
            int opcion = mostrarMenu("Opciones", new String[]{"Iniciar sesión", "Crear cuenta"});
            try {
                JSONObject users = cargar(USERS_FILE);
            } catch (Exception e) {
                // Si el archivo no existe, lo crea
                try (FileWriter file = new FileWriter(USERS_FILE)) {
                    file.write("{}");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            switch (opcion) {
                case 1:
                    iniciarSesion();
                    break;
                case 2:
                    crearCuenta();
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, seleccione una opción válida.");
            }
        }
        return usuario;
    }

    private JSONObject cargar(String archivo) throws IOException {
        String jsonCompleto = new String(Files.readAllBytes(new File(archivo).toPath()));
        return new JSONObject(jsonCompleto);
    }

    private void iniciarSesion() {
        String username = pedirCadenaAlUsuario("Ingrese su nombre de usuario:");
        String password = pedirCadenaAlUsuario("Ingrese su contraseña:");

        try {
            JSONObject users = cargar(USERS_FILE);
            if (users.has(username)) {
                JSONObject user = users.getJSONObject(username);
                if (user.getString("password").equals(password)) {
                	Rol role = Rol.fromString(user.getString("rol"));
                    if (user.getString("rol").equals(Rol.EMPLEADO.name())) {
                        usuario = new Empleado(user.getString("id"), user.getString("nombre"), user.getString("apellido"), user.getString("email"), password, username,role);
                        autenticado = true;
                    } else if (user.getString("rol").equals(Rol.COMUN.name())) {
                        usuario = new UsuarioComun(user.getString("id"), user.getString("nombre"), user.getString("apellido"), user.getString("email"), password, username,role, null, null, null,user.getBoolean("verificado"),user.getFloat("topeDeCompra"));
                        autenticado = true;
                    } else if (user.getString("rol").equals(Rol.ADMIN.name())) {
                        usuario = new Administrador(user.getString("id"), user.getString("nombre"), user.getString("apellido"), user.getString("email"), password, username,role);
                        autenticado = true;
                    }
                } else {
                    System.out.println("Contraseña incorrecta.");
                }
            } else {
                System.out.println("Usuario no encontrado.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void crearCuenta() {
        String username = pedirCadenaAlUsuario("Ingrese su nombre de usuario:");
        String password = pedirCadenaAlUsuario("Ingrese su contraseña:");
        String nombre = pedirCadenaAlUsuario("Ingrese su nombre:");
        String apellido = pedirCadenaAlUsuario("Ingrese su apellido:");
        String email = pedirCadenaAlUsuario("Ingrese su email:");
        Double topaDeCompra = 2000000.0;
        String fechaNacimiento = pedirCadenaAlUsuario("Ingrese su fecha de nacimiento (dd/mm/yyyy):");

        try {
            JSONObject users = cargar(USERS_FILE);
            if (users.has(username)) {
                System.out.println("El usuario ya existe.");
            } else {
                JSONObject user = new JSONObject();
                user.put("password", password);
                user.put("nombre", nombre);
                user.put("apellido", apellido);
                user.put("fechaNacimiento", fechaNacimiento);
                user.put("email", email);
                user.put("topeDeCompra", topaDeCompra);
                user.put("verificado", false);
                user.put("rol", Rol.COMUN.name());
                user.put("id", UUID.randomUUID().toString());
                users.put(username, user);
                try (FileWriter file = new FileWriter(USERS_FILE)) {
                    file.write(users.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}

