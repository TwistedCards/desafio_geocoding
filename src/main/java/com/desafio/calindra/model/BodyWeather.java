package com.desafio.calindra.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class BodyWeather {
	private List<Results> results = new ArrayList<>();
	private String status;
}
