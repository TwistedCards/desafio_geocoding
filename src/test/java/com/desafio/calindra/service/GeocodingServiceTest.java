package com.desafio.calindra.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.desafio.calindra.dto.AddressDTO;
import com.desafio.calindra.model.ResultAlgorithm;

class GeocodingServiceTest {

	private String apiKey;
	private String urlApi;
	private List<AddressDTO> listAddress = new ArrayList<>();

	@BeforeEach
	public void inicializar() {
		apiKey = "AIzaSyAqV0HwIwp3tAzox3ARt6c0Gcwx6nTPmlU";
		urlApi = "https://maps.googleapis.com/maps/api/geocode/json?address=";
		AddressDTO addressDTOLoc1 = new AddressDTO();
		AddressDTO addressDTOLoc2 = new AddressDTO();
		
		addressDTOLoc1.setAddress("Rua 19 de Fevereiro, 34 Botafogo, Rio de Janeiro RJ, 22280030");
		addressDTOLoc2.setAddress("Av. Rio Branco, 1 Centro, Rio de Janeiro RJ, 20090003");
		
		listAddress.add(addressDTOLoc1);
		listAddress.add(addressDTOLoc2);
	}

	@Test
	void calculatingDistanceBetweenAddressesTest() {
		GeocodingService geocodingService = new GeocodingService();
		ResultAlgorithm resultAlgorithm = geocodingService.calculatingDistanceBetweenAddresses(listAddress, apiKey, urlApi);
		assertNotNull(resultAlgorithm);
	}

}
