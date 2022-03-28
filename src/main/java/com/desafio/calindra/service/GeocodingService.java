package com.desafio.calindra.service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.desafio.calindra.dto.AddressDTO;
import com.desafio.calindra.model.BodyWeather;
import com.desafio.calindra.model.CalculatedAddresses;
import com.desafio.calindra.model.Geometry;
import com.desafio.calindra.model.Location;
import com.desafio.calindra.model.ResultAlgorithm;

@Service
public class GeocodingService {

	private Map<String,Geometry> mapAllDataAddressSecondary = new HashMap<String,Geometry>();
	private BigDecimal resultMultiplyLocalAtual;
	private BigDecimal resultMultiplyLocalDestino;
	private ResultAlgorithm resultAlgorithm;
	
	public ResultAlgorithm calculatingDistanceBetweenAddresses(List<AddressDTO> listAddress, String apiKey, String urlApi) {
		Map<String,Geometry> mapAllDataAddress = new HashMap<String,Geometry>();
		RestTemplate restTemplate = new RestTemplate();
		List<String> listUrl = new ArrayList<>();
		resultAlgorithm = new ResultAlgorithm();
		
		listAddress.forEach(address -> {
			StringBuilder builder = new StringBuilder();
			String url = builder.append(urlApi)
									.append(address.getAddress())
									.append("&key=")
									.append(apiKey)
									.toString();
			
			listUrl.add(url);
		});
		
		listUrl.forEach(url -> {
			ResponseEntity<BodyWeather> entity = restTemplate.getForEntity(url, BodyWeather.class);
			entity.getBody().getResults().forEach(result -> {
				mapAllDataAddress.put(result.getFormatted_address(), result.getGeometry());
			});
		});
		
		mapAllDataAddressSecondary = mapAllDataAddress;
		
		mapAllDataAddress.entrySet().forEach(mainAddres -> {
			mapAllDataAddressSecondary.entrySet().forEach(addressComparison -> {
				if(mainAddres.getValue().getLocation().getLng()
											.compareTo(addressComparison.getValue().getLocation().getLng()) == -1) {
					
					resultAlgorithm.getCombinations().add(calculateEuclideanDistance(mainAddres.getKey(), 
																					addressComparison.getKey(), 
																					mainAddres.getValue().getLocation(), 
																					addressComparison.getValue().getLocation()));
				}
			});
		});
		
		CalculatedAddresses nearestAddress = resultAlgorithm.getCombinations().stream().findFirst().get();
		
		List<CalculatedAddresses> addressFarther = resultAlgorithm.getCombinations().stream()
															.sorted(Comparator.comparing(CalculatedAddresses::getDistance)
																	.reversed())
															.collect(Collectors.toList());
		
		resultAlgorithm.setAddressNearest("Trajeto entre: " + nearestAddress.getDepartureAddress() 
													+ " / " + nearestAddress.getDestinationAddress());
		
		resultAlgorithm.setAddressFarther("Trajeto entre: " + addressFarther.stream().findFirst()
																		.get().getDepartureAddress() 
												+ " / " + addressFarther.stream().findFirst()
																		.get().getDestinationAddress());
		
		return resultAlgorithm;
	}
	
	private CalculatedAddresses calculateEuclideanDistance(String departureAddress, String addressDestination, 
													Location departureLocation, Location destinationLocation) {
		resultMultiplyLocalAtual = BigDecimal.ZERO;
		resultMultiplyLocalDestino = BigDecimal.ZERO;
		CalculatedAddresses calculatedAddresses = new CalculatedAddresses();
		
		BigDecimal resultSubtractLocalAtual = departureLocation.getLat().subtract(departureLocation.getLng());
		resultMultiplyLocalAtual = resultSubtractLocalAtual.multiply(new BigDecimal("2"));
		
		BigDecimal resultSubtractLocalDestino = destinationLocation.getLat().subtract(destinationLocation.getLng());
		resultMultiplyLocalDestino = resultSubtractLocalDestino.multiply(new BigDecimal("2"));

		BigDecimal resultMultiplicationAddresses = resultMultiplyLocalAtual.add(resultMultiplyLocalDestino);
		MathContext mc = new MathContext(10);
		BigDecimal distance = resultMultiplicationAddresses.sqrt(mc);
		
		calculatedAddresses.setDepartureAddress(departureAddress);
		calculatedAddresses.setDestinationAddress(addressDestination);
		calculatedAddresses.setDistance(distance);
		
		return calculatedAddresses;
	}
	
}
