package com.desafio.calindra.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CalculatedAddresses {
	private String departureAddress;
	private String destinationAddress;
	private BigDecimal distance;
}
