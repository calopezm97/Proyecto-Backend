package com.pragma.usermicro.service.use;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.pragma.usermicro.client.PhotoClient;
import com.pragma.usermicro.dto.PhotoDTO;
import com.pragma.usermicro.dto.UserDTO;
import com.pragma.usermicro.entity.UserEntity;
import com.pragma.usermicro.exception.ErrorException;
import com.pragma.usermicro.repository.UserRepository;
import com.pragma.usermicro.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceUse implements UserService {

	private final UserRepository userRepository;

	private final PhotoClient photoClient;

	ModelMapper modelMapper = new ModelMapper();

	@Override
	@Transactional(readOnly = true)
	public UserDTO findById(Integer id) {
		Optional<UserEntity> userEntity = userRepository.findById(id);
		if (userEntity.isPresent()) {
			UserDTO userDTO = modelMapper.map(userEntity.get(), UserDTO.class);
			userDTO.setFoto(photoClient.BuscarArchivo(userEntity.get().getIdfoto()).getBody());
			return userDTO;
		} else {
			throw new ErrorException(HttpStatus.NOT_FOUND, "No existe el user");
		}
	}

	@Override
	@Transactional
	public void saveUser(UserDTO user) {
		if (userRepository.existsById(user.getId())) {
			throw new ErrorException(HttpStatus.CONFLICT, "Ya existe el user");
		} else {
			UserEntity userEntity = modelMapper.map(user, UserEntity.class);
			userEntity.setIdfoto(user.getFoto().getId());

			if (photoClient.CrearArchivo(user.getFoto()).getStatusCodeValue() == HttpStatus.CREATED.value()) {
				userRepository.save(userEntity);
				if (!userRepository.existsById(user.getId())) {
					photoClient.EliminarArchivo(user.getFoto().getId());
					throw new ErrorException(HttpStatus.BAD_REQUEST,
							"No se registro nada, fallo en el registro del user");
				}
			} else {
				throw new ErrorException(HttpStatus.BAD_REQUEST, "No se registro nada, fallo en el regisro de la foto");
			}
		}
	}

	@Override
	@Transactional
	public UserDTO updateUser(UserDTO user) {
		if (userRepository.existsById(user.getId())) {
			UserEntity userEntity = modelMapper.map(user, UserEntity.class);
			userEntity.setIdfoto(user.getFoto().getId());
			if (photoClient.ActualizArarchivo(user.getFoto()).getStatusCodeValue() == HttpStatus.OK.value()) {
				userRepository.save(userEntity);
			} else {
				throw new ErrorException(HttpStatus.BAD_REQUEST,
						"No se actualizo nada, fallo actualizacion de la foto");
			}
			return user;
		} else {
			throw new ErrorException(HttpStatus.NOT_FOUND, "No existe el user");
		}
	}

	@Override
	@Transactional
	public boolean deleteUserById(Integer id) {
		Optional<UserEntity> userEntity = userRepository.findById(id);
		if (userEntity.isPresent()) {
			if (photoClient.EliminarArchivo(userEntity.get().getIdfoto()).getStatusCodeValue() == HttpStatus.OK
					.value()) {
				userRepository.deleteById(id);
			} else {
				throw new ErrorException(HttpStatus.BAD_REQUEST, "No se elimino nada, fallo eliminacion de la foto");
			}
			return true;
		} else {
			throw new ErrorException(HttpStatus.NOT_FOUND, "No existe el user");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<UserDTO> findAllUser() {
		List<UserEntity> userEntityList = userRepository.findAll();
		if (userEntityList.isEmpty()) {
			throw new ErrorException(HttpStatus.NO_CONTENT, "No existen users");
		} else {
			Map<String, UserEntity> userDTOMap = userEntityList.stream()
					.collect(Collectors.toMap(UserEntity::getIdfoto, Function.identity()));
			List<PhotoDTO> archivoDTOList = photoClient
					.ListaDeArchivosIds(new ArrayList<>(userDTOMap.keySet())).getBody();
			if (archivoDTOList == null) {
				return modelMapper.map(userEntityList, new TypeToken<List<UserDTO>>() {
				}.getType());
			} else {
				Map<String, PhotoDTO> archivoDTOMap = archivoDTOList.stream()
						.collect(Collectors.toMap(PhotoDTO::getId, Function.identity()));
				List<UserDTO> userDTOList = new ArrayList<>();
				UserDTO userDTO;
				for (Map.Entry<String, UserEntity> userEntity : userDTOMap.entrySet()) {
					userDTO = modelMapper.map(userEntity.getValue(), UserDTO.class);
					userDTO.setFoto(archivoDTOMap.get(userEntity.getValue().getIdfoto()));
					userDTOList.add(userDTO);
				}
				return userDTOList;
			}
		}
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isUserExist(UserDTO user) {
		return userRepository.existsById(user.getId());
	}

	@Override
	@Transactional(readOnly = true)
	public UserDTO findByDocumentotipoAndDocumentonumero(String DocumentoTipoDTO, String documentoNumero) {
		Optional<UserEntity> userEntity = userRepository.findByDocumentotipoAndDocumentonumero(
				documentoNumero, documentoNumero);
		if (userEntity.isPresent()) {
			UserDTO userDTO = modelMapper.map(userEntity.get(), UserDTO.class);
			userDTO.setFoto(photoClient.BuscarArchivo(userEntity.get().getIdfoto()).getBody());
			return userDTO;
		} else {
			throw new ErrorException(HttpStatus.NOT_FOUND, "No existe el user");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<UserDTO> findByAgeGreaterThanEqual(int edad) {
		List<UserEntity> userEntityList = userRepository.findByEdadGreaterThanEqual(edad);
		if (userEntityList.isEmpty()) {
			throw new ErrorException(HttpStatus.NO_CONTENT, "No existen users");
		} else {
			Map<String, UserEntity> userDTOMap = userEntityList.stream()
					.collect(Collectors.toMap(UserEntity::getIdfoto, Function.identity()));
			List<PhotoDTO> archivoDTOList = photoClient
					.ListaDeArchivosIds(new ArrayList<>(userDTOMap.keySet())).getBody();
			if (archivoDTOList == null) {
				return modelMapper.map(userEntityList, new TypeToken<List<UserDTO>>() {
				}.getType());
			} else {
				Map<String, PhotoDTO> archivoDTOMap = archivoDTOList.stream()
						.collect(Collectors.toMap(PhotoDTO::getId, Function.identity()));
				List<UserDTO> userDTOList = new ArrayList<>();
				UserDTO userDTO;
				for (Map.Entry<String, UserEntity> userEntity : userDTOMap.entrySet()) {
					userDTO = modelMapper.map(userEntity.getValue(), UserDTO.class);
					userDTO.setFoto(archivoDTOMap.get(userEntity.getValue().getIdfoto()));
					userDTOList.add(userDTO);
				}
				return userDTOList;
			}
		}
	}

}
