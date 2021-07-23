package com.pragma.usermicro.service;

import java.util.List;

import com.pragma.usermicro.dto.UserDTO;

public interface UserService {

	UserDTO findById(Integer id);

	void savePersona(UserDTO persona);

	UserDTO updatePersona(UserDTO persona);

	boolean deletePersonaById(Integer id);

	List<UserDTO> findAllPersona();

	public boolean isPersonaExist(UserDTO persona);

	UserDTO findByDocumentotipoAndDocumentonumero(int idDocumentoTipoDTO, String documentoNumero);

	List<UserDTO> findByEdadGreaterThanEqual(int edad);

}
