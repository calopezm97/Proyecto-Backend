package com.pragma.usermicro.service;

import java.util.List;

import com.pragma.usermicro.dto.UserDTO;

public interface UserService {

	UserDTO findById(Integer id);

	void saveUser(UserDTO User);

	UserDTO updateUser(UserDTO User);

	boolean deleteUserById(Integer id);

	List<UserDTO> findAllUser();

	boolean isUserExist(UserDTO User);

	UserDTO findByDocumentotipoAndDocumentonumero(String DocumentoTipoDTO, String documentoNumero);

	List<UserDTO> findByAgeGreaterThanEqual(int edad);

}
