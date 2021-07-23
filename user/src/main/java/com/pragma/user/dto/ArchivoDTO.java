package com.pragma.user.dto;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Detalles del archivo")
public class ArchivoDTO {

	@ApiModelProperty(notes = "El identificador �nico del archivo")
	private String id;

	@NotEmpty(message = "La data en base64 no deben ser vac�a")
	@ApiModelProperty(notes = "Data en Base64 del archivo")
	private String data;
	
	@NotEmpty(message = "El tipo no deben ser vac�o")
	@ApiModelProperty(notes = "El tipo del archivo (Eje: .jpg, .png...)")
	private String tipo;
	
}
