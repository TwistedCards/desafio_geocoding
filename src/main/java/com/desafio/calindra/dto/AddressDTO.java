package com.desafio.calindra.dto;


import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class AddressDTO {
	@NotEmpty
	private String address;
}
