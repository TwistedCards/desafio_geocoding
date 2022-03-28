package com.desafio.calindra.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Location {
	private BigDecimal lat;
	private BigDecimal lng;
}
