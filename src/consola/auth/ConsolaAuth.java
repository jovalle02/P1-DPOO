package consola.auth;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;

import org.json.JSONObject;

import consola.ConsolaBasica;

public class ConsolaAuth extends ConsolaBasica {

    private static final String USERS_FILE = "users.json";

    public ConsolaAuth() {
        // Constructor
    }

    public void iniciar() {
        boolean autenticado = false;
        while (!autenticado) {
            System.out.println("Bienvenido a la Consola de Autenticación");
            System.out.println("Seleccione una opción:");
            System.out.println("1. Iniciar sesión");
            System.out.println("2. Crear cuenta");
            int opcion = mostrarMenu("Opciones", new String[]{"Iniciar sesión", "Crear cuenta"});
            switch (opcion) {
                case 1:
                    autenticarUsuario();
                    autenticado = true;
                    break;
                case 2:
                    registrarUsuario();
                    autenticado = true;
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, seleccione una opción válida.");
            }
        }
    }

    private void autenticarUsuario() {
        System.out.println("Iniciar sesión:");
        String username = pedirCadenaAlUsuario("Nombre de usuario");
        String password = pedirCadenaAlUsuario("Contraseña");
        if (validarCredenciales(username, password)) {
            System.out.println("¡Inicio de sesión exitoso!");
        } else {
            System.out.println("Nombre de usuario o contraseña incorrectos. Inténtelo de nuevo.");
            autenticarUsuario();
        }
    }

    private void registrarUsuario() {
        System.out.println("Crear cuenta:");
        String username = pedirCadenaAlUsuario("Nombre de usuario");
        String password = pedirCadenaAlUsuario("Contraseña");
        if (usuarioExiste(username)) {
            System.out.println("El nombre de usuario ya existe. Por favor, elija otro.");
            registrarUsuario();
        } else {
            guardarUsuario(username, password);
            System.out.println("Usuario creado exitosamente.");
        }
    }

    private boolean validarCredenciales(String username, String password) {
        JSONObject usuarios = leerUsuariosDesdeArchivo();
        if (usuarios.containsKey(username)) {
            String storedPassword = (String) usuarios.get(username);
            return storedPassword.equals(password);
        }
        return false;
    }

    private boolean usuarioExiste(String username) {
        JSONObject usuarios = leerUsuariosDesdeArchivo();
        return usuarios.containsKey(username);
    }

    @SuppressWarnings("unchecked")
    private void guardarUsuario(String username, String password) {
        JSONObject usuarios = leerUsuariosDesdeArchivo();
        usuarios.put(username, password);
        escribirUsuariosEnArchivo(usuarios);
    }

    private JSONObject leerUsuariosDesdeArchivo() {
        JSONParser parser = new JSONParser();
        JSONObject usuarios = new JSONObject();
        try {
            FileReader reader = new FileReader(USERS_FILE);
            usuarios = (JSONObject) parser.parse(reader);
            reader.close();
        } catch (FileNotFoundException e) {
            // El archivo no existe, se creará uno nuevo
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    private void escribirUsuariosEnArchivo(JSONObject usuarios) {
        try {
            FileWriter writer = new FileWriter(USERS_FILE);
            usuarios.writeJSONString(writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

