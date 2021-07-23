package com.pragma.user.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

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
@ApiModel(description = "Detalles de la persona")
public class UserDTO {
	
	@ApiModelProperty(notes = "El identificador �nico de la persona")
	private int id;

	@NotEmpty(message = "Los nombres no deben ser vac�o")
	@ApiModelProperty(notes = "El nombre de la persona")
	private String nombres;

	@NotEmpty(message = "Los apellidos no deben ser vac�o")
	@ApiModelProperty(notes = "El apellido de la persona")
	private String apellidos;

	@NotNull(message = "El tipo de documento no debe ser vac�o")
	@ApiModelProperty(notes = "El tipo de documento de la persona")
	private DocumentoTipoDTO documentotipo;

	@NotEmpty(message = "El n�mero de documento no debe ser vac�o")
	@ApiModelProperty(notes = "El n�mero de documento de la persona")
	private String documentonumero;

	@Positive(message = "La edad debe ser mayor a cero")
	@ApiModelProperty(notes = "La edad de la persona")
	private int edad;

	@NotNull(message = "La ciudad de nacimiento no debe ser vac�a")
	@ApiModelProperty(notes = "La ciudad de nacimeinto de la persona")
	private CiudadDTO ciudadnacimiento;

	@NotNull(message = "La foto no debe ser vac�a")
	@ApiModelProperty(notes = "La foto de la persona")
	private ArchivoDTO foto;

}
