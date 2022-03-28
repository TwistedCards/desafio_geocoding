package com.desafio.calindra.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ResultAlgorithm {
	private List<CalculatedAddresses> combinations = new ArrayList<>();
	private String addressNearest;
	private String addressFarther;
}
