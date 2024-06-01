package pasarela_pagos;

public enum TiposPasarela {
	Paypal,
	Payu,
	Sire;
	
	public static TiposPasarela fromString(String platformStr) {
        switch (platformStr.toUpperCase()) {
            case "PAYPAL":
                return Paypal;
            case "PAYU":
                return Payu;
            case "SIRE":
                return Sire;
            default:
                throw new IllegalArgumentException("Unknown Payment Platform: " + platformStr);
        }
	}
}