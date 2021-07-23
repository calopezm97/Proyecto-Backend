package com.pragma.photo.dto;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Detalles de la foto")
public class PhotoDTO {

	@NotEmpty(message = "El identificador no debe ser vacio")
	private String id;

	@NotEmpty(message = "La data en base64 no deben ser vacia")
	private String data;
	
	@NotEmpty(message = "El tipo no deben ser vacio")
	private String tipo;
	
}
