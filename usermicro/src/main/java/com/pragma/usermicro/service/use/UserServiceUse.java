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

	private final UserRepository personaRepository;

	private final PhotoClient archivoClient;

	ModelMapper modelMapper = new ModelMapper();

	@Override
	@Transactional(readOnly = true)
	public UserDTO findById(Integer id) {
		Optional<UserEntity> personaEntity = personaRepository.findById(id);
		if (personaEntity.isPresent()) {
			UserDTO personaDTO = modelMapper.map(personaEntity.get(), UserDTO.class);
			personaDTO.setFoto(archivoClient.BuscarArchivo(personaEntity.get().getIdfoto()).getBody());
			return personaDTO;
		} else {
			throw new ErrorException(HttpStatus.NOT_FOUND, "No existe la persona");
		}
	}

	@Override
	@Transactional
	public void savePersona(UserDTO persona) {
		// TODO Auto-generated method stub
		if (personaRepository.existsById(persona.getId())) {
			throw new ErrorException(HttpStatus.CONFLICT, "Ya existe la persona");
		} else {
			UserEntity personaEntity = modelMapper.map(persona, UserEntity.class);
			personaEntity.setIdfoto(persona.getFoto().getId());

			if (archivoClient.CrearArchivo(persona.getFoto()).getStatusCodeValue() == HttpStatus.CREATED.value()) {
				personaRepository.save(personaEntity);
				if (!personaRepository.existsById(persona.getId())) {
					archivoClient.EliminarArchivo(persona.getFoto().getId());
					throw new ErrorException(HttpStatus.BAD_REQUEST,
							"No se registro nada, fall� en el registro de la persona");
				}
			} else {
				throw new ErrorException(HttpStatus.BAD_REQUEST, "No se registro nada, fall� en el regisro de la foto");
			}
		}
	}

	@Override
	@Transactional
	public UserDTO updatePersona(UserDTO persona) {
		// TODO Auto-generated method stub
		if (personaRepository.existsById(persona.getId())) {
			UserEntity personaEntity = modelMapper.map(persona, UserEntity.class);
			personaEntity.setIdfoto(persona.getFoto().getId());
			if (archivoClient.ActualizArarchivo(persona.getFoto()).getStatusCodeValue() == HttpStatus.OK.value()) {
				personaRepository.save(personaEntity);
			} else {
				throw new ErrorException(HttpStatus.BAD_REQUEST,
						"No se actualiz� nada, fall� actualizaci�n de la foto");
			}
			return persona;
		} else {
			throw new ErrorException(HttpStatus.NOT_FOUND, "No existe la persona");
		}
	}

	@Override
	@Transactional
	public boolean deletePersonaById(Integer id) {
		// TODO Auto-generated method stub
		Optional<UserEntity> personaEntity = personaRepository.findById(id);
		if (personaEntity.isPresent()) {
			if (archivoClient.EliminarArchivo(personaEntity.get().getIdfoto()).getStatusCodeValue() == HttpStatus.OK
					.value()) {
				personaRepository.deleteById(id);
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
	public List<UserDTO> findAllPersona() {
		// TODO Auto-generated method stub
		List<UserEntity> personaEntityList = personaRepository.findAll();
		if (personaEntityList.isEmpty()) {
			throw new ErrorException(HttpStatus.NO_CONTENT, "No existen personas");
		} else {
			Map<String, UserEntity> personaDTOMap = personaEntityList.stream()
					.collect(Collectors.toMap(UserEntity::getIdfoto, Function.identity()));
			List<PhotoDTO> archivoDTOList = archivoClient
					.ListaDeArchivosIds(new ArrayList<String>(personaDTOMap.keySet())).getBody();
			if (archivoDTOList == null) {
				return modelMapper.map(personaEntityList, new TypeToken<List<UserDTO>>() {
				}.getType());
			} else {
				Map<String, PhotoDTO> archivoDTOMap = archivoDTOList.stream()
						.collect(Collectors.toMap(PhotoDTO::getId, Function.identity()));
				List<UserDTO> personaDTOList = new ArrayList<UserDTO>();
				UserDTO personaDTO = null;
				for (Map.Entry<String, UserEntity> personaEntity : personaDTOMap.entrySet()) {
					personaDTO = modelMapper.map(personaEntity.getValue(), UserDTO.class);
					personaDTO.setFoto(archivoDTOMap.get(personaEntity.getValue().getIdfoto()));
					personaDTOList.add(personaDTO);
				}
				return personaDTOList;
			}
		}
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isPersonaExist(UserDTO persona) {
		return personaRepository.existsById(persona.getId());
	}

	@Override
	@Transactional(readOnly = true)
	public UserDTO findByDocumentotipoAndDocumentonumero(int idDocumentoTipoDTO, String documentoNumero) {
		// TODO Auto-generated method stub
		Optional<UserEntity> personaEntity = personaRepository.findByDocumentotipoAndDocumentonumero(
				documentoNumero, documentoNumero);
		if (personaEntity.isPresent()) {
			UserDTO personaDTO = modelMapper.map(personaEntity.get(), UserDTO.class);
			personaDTO.setFoto(archivoClient.BuscarArchivo(personaEntity.get().getIdfoto()).getBody());
			return personaDTO;
		} else {
			throw new ErrorException(HttpStatus.NOT_FOUND, "No existe la persona");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<UserDTO> findByEdadGreaterThanEqual(int edad) {
		// TODO Auto-generated method stub
		List<UserEntity> personaEntityList = personaRepository.findByEdadGreaterThanEqual(edad);
		if (personaEntityList.isEmpty()) {
			throw new ErrorException(HttpStatus.NO_CONTENT, "No existen personas");
		} else {
			Map<String, UserEntity> personaDTOMap = personaEntityList.stream()
					.collect(Collectors.toMap(UserEntity::getIdfoto, Function.identity()));
			List<PhotoDTO> archivoDTOList = archivoClient
					.ListaDeArchivosIds(new ArrayList<String>(personaDTOMap.keySet())).getBody();
			if (archivoDTOList == null) {
				return modelMapper.map(personaEntityList, new TypeToken<List<UserDTO>>() {
				}.getType());
			} else {
				Map<String, PhotoDTO> archivoDTOMap = archivoDTOList.stream()
						.collect(Collectors.toMap(PhotoDTO::getId, Function.identity()));
				List<UserDTO> personaDTOList = new ArrayList<UserDTO>();
				UserDTO personaDTO = null;
				for (Map.Entry<String, UserEntity> personaEntity : personaDTOMap.entrySet()) {
					personaDTO = modelMapper.map(personaEntity.getValue(), UserDTO.class);
					personaDTO.setFoto(archivoDTOMap.get(personaEntity.getValue().getIdfoto()));
					personaDTOList.add(personaDTO);
				}
				return personaDTOList;
			}
		}
	}

}
