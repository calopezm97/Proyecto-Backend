package com.pragma.user.service;

import java.util.List;

import com.pragma.user.dto.PersonaDTO;

public interface PersonaService {

	PersonaDTO findById(Integer id);

	void savePersona(PersonaDTO persona);

	PersonaDTO updatePersona(PersonaDTO persona);

	boolean deletePersonaById(Integer id);

	List<PersonaDTO> findAllPersona();

	public boolean isPersonaExist(PersonaDTO persona);

	PersonaDTO findByDocumentotipoAndDocumentonumero(int idDocumentoTipoDTO, String documentoNumero);

	List<PersonaDTO> findByEdadGreaterThanEqual(int edad);

}
