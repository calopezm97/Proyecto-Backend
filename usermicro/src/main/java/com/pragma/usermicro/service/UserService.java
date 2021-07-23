package com.pragma.usermicro.service;

import java.util.List;

import com.pragma.usermicro.dto.UserDTO;

public interface UserService {

	UserDTO findById(Integer id);

	void saveUser(UserDTO persona);

	UserDTO updateUser(UserDTO persona);

	boolean deleteUserById(Integer id);

	List<UserDTO> findAllUser();

	public boolean isUserExist(UserDTO persona);

	UserDTO findByDocumentotipoAndDocumentonumero(int idDocumentoTipoDTO, String documentoNumero);

	List<UserDTO> findByEdadGreaterThanEqual(int edad);

}
