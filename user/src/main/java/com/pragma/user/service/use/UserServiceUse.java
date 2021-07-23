package com.pragma.user.service.use;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pragma.user.client.ArchivoClient;
import com.pragma.user.dto.ArchivoDTO;
import com.pragma.user.dto.UserDTO;
import com.pragma.user.entity.DocumentoTipoEntity;
import com.pragma.user.entity.UserEntity;
import com.pragma.user.exception.ErrorException;
import com.pragma.user.repository.*;
import com.pragma.user.service.*;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceUse implements UserService {

	private final UserRepository userRepository;

	private final ArchivoClient archivoClient;

	ModelMapper modelMapper = new ModelMapper();

	@Override
	@Transactional(readOnly = true)
	public UserDTO findById(Integer id) {
		// TODO Auto-generated method stub
		Optional<UserEntity> personaEntity = userRepository.findById(id);
		if (personaEntity.isPresent()) {
			UserDTO userDTO = modelMapper.map(personaEntity.get(), UserDTO.class);
			userDTO.setFoto(archivoClient.BuscarArchivo(personaEntity.get().getIdfoto()).getBody());
			return userDTO;
		} else {
			throw new ErrorException(HttpStatus.NOT_FOUND, "No existe el usuario");
		}
	}

	@Override
	@Transactional
	public void saveUser(UserDTO persona) {
		// TODO Auto-generated method stub
		if (userRepository.existsById(persona.getId())) {
			throw new ErrorException(HttpStatus.CONFLICT, "Ya existe el usuario");
		} else {
			UserEntity userEntity = modelMapper.map(persona, UserEntity.class);
			userEntity.setIdfoto(persona.getFoto().getId());

			if (archivoClient.CrearArchivo(persona.getFoto()).getStatusCodeValue() == HttpStatus.CREATED.value()) {
				userRepository.save(userEntity);
				if (!userRepository.existsById(persona.getId())) {
					archivoClient.EliminarArchivo(persona.getFoto().getId());
					throw new ErrorException(HttpStatus.BAD_REQUEST,
							"No se registro nada, fallo en el registro del usuario");
				}
			} else {
				throw new ErrorException(HttpStatus.BAD_REQUEST, "No se registro nada, fallo en el registro de la foto");
			}
		}
	}

	@Override
	@Transactional
	public UserDTO updateUser(UserDTO persona) {
		// TODO Auto-generated method stub
		if (userRepository.existsById(persona.getId())) {
			UserEntity userEntity = modelMapper.map(persona, UserEntity.class);
			userEntity.setIdfoto(persona.getFoto().getId());
			if (archivoClient.ActualizArarchivo(persona.getFoto()).getStatusCodeValue() == HttpStatus.OK.value()) {
				userRepository.save(userEntity);
			} else {
				throw new ErrorException(HttpStatus.BAD_REQUEST,
						"No se actualizo nada, fallo actualizacion de la foto");
			}
			return persona;
		} else {
			throw new ErrorException(HttpStatus.NOT_FOUND, "No existe el usuario");
		}
	}

	@Override
	@Transactional
	public boolean deleteUserById(Integer id) {
		// TODO Auto-generated method stub
		Optional<UserEntity> personaEntity = userRepository.findById(id);
		if (personaEntity.isPresent()) {
			if (archivoClient.EliminarArchivo(personaEntity.get().getIdfoto()).getStatusCodeValue() == HttpStatus.OK
					.value()) {
				userRepository.deleteById(id);
			} else {
				throw new ErrorException(HttpStatus.BAD_REQUEST, "No se elimin� nada, fall� eliminaci�n de la foto");
			}
			return true;
		} else {
			throw new ErrorException(HttpStatus.NOT_FOUND, "No existe la persona");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<UserDTO> findAllUser() {
		// TODO Auto-generated method stub
		List<UserEntity> userEntityList = userRepository.findAll();
		if (userEntityList.isEmpty()) {
			throw new ErrorException(HttpStatus.NO_CONTENT, "No existen personas");
		} else {
			Map<String, UserEntity> personaDTOMap = userEntityList.stream()
					.collect(Collectors.toMap(UserEntity::getIdfoto, Function.identity()));
			List<ArchivoDTO> archivoDTOList = archivoClient
					.ListaDeArchivosIds(new ArrayList<String>(personaDTOMap.keySet())).getBody();
			if (archivoDTOList == null) {
				return modelMapper.map(userEntityList, new TypeToken<List<UserDTO>>() {
				}.getType());
			} else {
				Map<String, ArchivoDTO> archivoDTOMap = archivoDTOList.stream()
						.collect(Collectors.toMap(ArchivoDTO::getId, Function.identity()));
				List<UserDTO> userDTOList = new ArrayList<UserDTO>();
				UserDTO userDTO = null;
				for (Map.Entry<String, UserEntity> personaEntity : personaDTOMap.entrySet()) {
					userDTO = modelMapper.map(personaEntity.getValue(), UserDTO.class);
					userDTO.setFoto(archivoDTOMap.get(personaEntity.getValue().getIdfoto()));
					userDTOList.add(userDTO);
				}
				return userDTOList;
			}
		}
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isUserExist(UserDTO persona) {
		// TODO Auto-generated method stub
		return userRepository.existsById(persona.getId());
	}

	@Override
	@Transactional(readOnly = true)
	public UserDTO findByDocumentotipoAndDocumentonumero(int idDocumentoTipoDTO, String documentoNumero) {
		// TODO Auto-generated method stub
		Optional<UserEntity> personaEntity = userRepository.findByDocumentotipoAndDocumentonumero(
				DocumentoTipoEntity.builder().id(idDocumentoTipoDTO).build(), documentoNumero);
		if (personaEntity.isPresent()) {
			UserDTO userDTO = modelMapper.map(personaEntity.get(), UserDTO.class);
			userDTO.setFoto(archivoClient.BuscarArchivo(personaEntity.get().getIdfoto()).getBody());
			return userDTO;
		} else {
			throw new ErrorException(HttpStatus.NOT_FOUND, "No existe la persona");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<UserDTO> findByEdadGreaterThanEqual(int edad) {
		// TODO Auto-generated method stub
		List<UserEntity> userEntityList = userRepository.findByEdadGreaterThanEqual(edad);
		if (userEntityList.isEmpty()) {
			throw new ErrorException(HttpStatus.NO_CONTENT, "No existen personas");
		} else {
			Map<String, UserEntity> personaDTOMap = userEntityList.stream()
					.collect(Collectors.toMap(UserEntity::getIdfoto, Function.identity()));
			List<ArchivoDTO> archivoDTOList = archivoClient
					.ListaDeArchivosIds(new ArrayList<String>(personaDTOMap.keySet())).getBody();
			if (archivoDTOList == null) {
				return modelMapper.map(userEntityList, new TypeToken<List<UserDTO>>() {
				}.getType());
			} else {
				Map<String, ArchivoDTO> archivoDTOMap = archivoDTOList.stream()
						.collect(Collectors.toMap(ArchivoDTO::getId, Function.identity()));
				List<UserDTO> userDTOList = new ArrayList<UserDTO>();
				UserDTO userDTO = null;
				for (Map.Entry<String, UserEntity> personaEntity : personaDTOMap.entrySet()) {
					userDTO = modelMapper.map(personaEntity.getValue(), UserDTO.class);
					userDTO.setFoto(archivoDTOMap.get(personaEntity.getValue().getIdfoto()));
					userDTOList.add(userDTO);
				}
				return userDTOList;
			}
		}
	}

}
