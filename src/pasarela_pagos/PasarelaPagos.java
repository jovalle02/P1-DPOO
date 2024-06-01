package pasarela_pagos;

import java.io.File;
import java.nio.file.Files;

import org.json.JSONObject;

import logica.Galeria;

public abstract class PasarelaPagos {
	
	//Los métodos retornarán true si la transacción se pudo efectuar correctamente, false de lo contrario
	public abstract boolean realizarPago(String firstName, String lastName, String email, String idTarjeta, double monto);
	protected abstract boolean registrarPago(String firstName, String lastName, String email, String idTarjeta, double monto);
	
}
