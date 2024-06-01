package pasarela_pagos;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.json.JSONObject;

public class Sire extends PasarelaPagos {
	private final static String file = "datos/sire.json";
	
	//Simula la conexión con el servicio externo, y dice si el pago fue aprobado
	private boolean conectarPasarelaPagos() {
		return true;
	}
	
	@Override
	public boolean realizarPago(String firstName, String lastName, String email, String idTarjeta, double monto) {
		boolean pagoAutorizado = conectarPasarelaPagos();
		if(pagoAutorizado) {
			return registrarPago(firstName,lastName,email,idTarjeta,monto);
		}else {
			System.out.println("El pago fue rechazado por la plataforma de pagos.");
			return false;
		}
	}
	@Override
	protected boolean registrarPago(String firstName, String lastName, String email, String idTarjeta, double monto) {
		
		JSONObject pagos = cargarCompras();
		if(pagos!=null) {
			String id = UUID.randomUUID().toString();
			while(pagos.has(id)) {
				id = UUID.randomUUID().toString();
			}
			JSONObject pago = new JSONObject();
			
			//Obtiene la fecha actual y la guarda en la factura.
			Date date = Calendar.getInstance().getTime();  
			DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
			String fecha = dateFormat.format(date);
			pago.put("fecha", fecha);
			
			pago.put("id", id);
			pago.put("nombre", firstName);
			pago.put("apellido", lastName);
			pago.put("email", email);
			pago.put("id_tarjeta", idTarjeta);
			pago.put("valor", monto);
			
			try (FileWriter writer = new FileWriter(file)) {
	            writer.write(pago.toString());
	        } catch (IOException e) {
	        	System.out.println("Ocurrió un error guardando el pago en el archivo "+file);
	            e.printStackTrace();
	            return false;
	        }
			return true;
		}else {
			return false;
		}
	}
	
	private JSONObject cargarCompras() {
		try {
			String jsonPagos = new String(Files.readAllBytes(new File(file).toPath()));
	        return new JSONObject(jsonPagos);
		}catch (IOException e){
			System.out.println("Ocurrió un error leyendo el archivo "+file);
			e.printStackTrace();
			return null;
		}
	}
}
