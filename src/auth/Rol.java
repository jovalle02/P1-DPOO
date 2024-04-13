package auth;

public enum Rol {
	ADMIN,
	COMUN,
	EMPLEADO;
	
	public static Rol fromString(String rolStr) {
        switch (rolStr.toUpperCase()) {
            case "ADMIN":
                return ADMIN;
            case "COMUN":
                return COMUN;
            case "EMPLEADO":
                return EMPLEADO;
            default:
                throw new IllegalArgumentException("Unknown Rol: " + rolStr);
        }
	}
}
