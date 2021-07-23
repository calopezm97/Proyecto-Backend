package com.pragma.user.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class UserEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private int id;
	private String nombres;
	private String apellidos;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "iddocumentotipo")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private DocumentoTipoEntity documentotipo;

	private String documentoNumero;
	private int edad;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idciudadnacimiento")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private CiudadEntity ciudadNacimiento;

	private String idfoto;

}
