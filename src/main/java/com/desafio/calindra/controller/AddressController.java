package com.desafio.calindra.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.desafio.calindra.dto.AddressDTO;
import com.desafio.calindra.model.ResultAlgorithm;
import com.desafio.calindra.service.GeocodingService;

@RestController
@RequestMapping("/address")
public class AddressController {

	@Value("${api.key}")
	private String apiKey;
	
	@Value("${url.api}")
	private String urlApi;
	
	@Autowired
	private GeocodingService geocodingService;
	
	@GetMapping(value = "/findAddressData")
	public ResponseEntity<ResultAlgorithm> getAddressData(@Valid @RequestBody List<AddressDTO> listAddress) {
		ResultAlgorithm resultadoAlgoritmo = geocodingService.calculatingDistanceBetweenAddresses(listAddress, apiKey, urlApi);
		return new ResponseEntity<>(resultadoAlgoritmo, HttpStatus.OK);
	}
	
}
