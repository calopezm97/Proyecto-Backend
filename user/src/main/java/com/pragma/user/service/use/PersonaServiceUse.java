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
import com.pragma.user.dto.PersonaDTO;
import com.pragma.user.entity.DocumentoTipoEntity;
import com.pragma.user.entity.PersonaEntity;
import com.pragma.user.exception.ErrorException;
import com.pragma.user.repository.*;
import com.pragma.user.service.*;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PersonaServiceUse implements PersonaService {

	private final PersonaRepository personaRepository;

	private final ArchivoClient archivoClient;

	ModelMapper modelMapper = new ModelMapper();

	@Override
	@Transactional(readOnly = true)
	public PersonaDTO findById(Integer id) {
		// TODO Auto-generated method stub
		Optional<PersonaEntity> personaEntity = personaRepository.findById(id);
		if (personaEntity.isPresent()) {
			PersonaDTO personaDTO = modelMapper.map(personaEntity.get(), PersonaDTO.class);
			personaDTO.setFoto(archivoClient.BuscarArchivo(personaEntity.get().getIdfoto()).getBody());
			return personaDTO;
		} else {
			throw new ErrorException(HttpStatus.NOT_FOUND, "No existe la persona");
		}
	}

	@Override
	@Transactional
	public void savePersona(PersonaDTO persona) {
		// TODO Auto-generated method stub
		if (personaRepository.existsById(persona.getId())) {
			throw new ErrorException(HttpStatus.CONFLICT, "Ya existe la persona");
		} else {
			PersonaEntity personaEntity = modelMapper.map(persona, PersonaEntity.class);
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
	public PersonaDTO updatePersona(PersonaDTO persona) {
		// TODO Auto-generated method stub
		if (personaRepository.existsById(persona.getId())) {
			PersonaEntity personaEntity = modelMapper.map(persona, PersonaEntity.class);
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
		Optional<PersonaEntity> personaEntity = personaRepository.findById(id);
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
	public List<PersonaDTO> findAllPersona() {
		// TODO Auto-generated method stub
		List<PersonaEntity> personaEntityList = personaRepository.findAll();
		if (personaEntityList.isEmpty()) {
			throw new ErrorException(HttpStatus.NO_CONTENT, "No existen personas");
		} else {
			Map<String, PersonaEntity> personaDTOMap = personaEntityList.stream()
					.collect(Collectors.toMap(PersonaEntity::getIdfoto, Function.identity()));
			List<ArchivoDTO> archivoDTOList = archivoClient
					.ListaDeArchivosIds(new ArrayList<String>(personaDTOMap.keySet())).getBody();
			if (archivoDTOList == null) {
				return modelMapper.map(personaEntityList, new TypeToken<List<PersonaDTO>>() {
				}.getType());
			} else {
				Map<String, ArchivoDTO> archivoDTOMap = archivoDTOList.stream()
						.collect(Collectors.toMap(ArchivoDTO::getId, Function.identity()));
				List<PersonaDTO> personaDTOList = new ArrayList<PersonaDTO>();
				PersonaDTO personaDTO = null;
				for (Map.Entry<String, PersonaEntity> personaEntity : personaDTOMap.entrySet()) {
					personaDTO = modelMapper.map(personaEntity.getValue(), PersonaDTO.class);
					personaDTO.setFoto(archivoDTOMap.get(personaEntity.getValue().getIdfoto()));
					personaDTOList.add(personaDTO);
				}
				return personaDTOList;
			}
		}
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isPersonaExist(PersonaDTO persona) {
		// TODO Auto-generated method stub
		return personaRepository.existsById(persona.getId());
	}

	@Override
	@Transactional(readOnly = true)
	public PersonaDTO findByDocumentotipoAndDocumentonumero(int idDocumentoTipoDTO, String documentoNumero) {
		// TODO Auto-generated method stub
		Optional<PersonaEntity> personaEntity = personaRepository.findByDocumentotipoAndDocumentonumero(
				DocumentoTipoEntity.builder().id(idDocumentoTipoDTO).build(), documentoNumero);
		if (personaEntity.isPresent()) {
			PersonaDTO personaDTO = modelMapper.map(personaEntity.get(), PersonaDTO.class);
			personaDTO.setFoto(archivoClient.BuscarArchivo(personaEntity.get().getIdfoto()).getBody());
			return personaDTO;
		} else {
			throw new ErrorException(HttpStatus.NOT_FOUND, "No existe la persona");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<PersonaDTO> findByEdadGreaterThanEqual(int edad) {
		// TODO Auto-generated method stub
		List<PersonaEntity> personaEntityList = personaRepository.findByEdadGreaterThanEqual(edad);
		if (personaEntityList.isEmpty()) {
			throw new ErrorException(HttpStatus.NO_CONTENT, "No existen personas");
		} else {
			Map<String, PersonaEntity> personaDTOMap = personaEntityList.stream()
					.collect(Collectors.toMap(PersonaEntity::getIdfoto, Function.identity()));
			List<ArchivoDTO> archivoDTOList = archivoClient
					.ListaDeArchivosIds(new ArrayList<String>(personaDTOMap.keySet())).getBody();
			if (archivoDTOList == null) {
				return modelMapper.map(personaEntityList, new TypeToken<List<PersonaDTO>>() {
				}.getType());
			} else {
				Map<String, ArchivoDTO> archivoDTOMap = archivoDTOList.stream()
						.collect(Collectors.toMap(ArchivoDTO::getId, Function.identity()));
				List<PersonaDTO> personaDTOList = new ArrayList<PersonaDTO>();
				PersonaDTO personaDTO = null;
				for (Map.Entry<String, PersonaEntity> personaEntity : personaDTOMap.entrySet()) {
					personaDTO = modelMapper.map(personaEntity.getValue(), PersonaDTO.class);
					personaDTO.setFoto(archivoDTOMap.get(personaEntity.getValue().getIdfoto()));
					personaDTOList.add(personaDTO);
				}
				return personaDTOList;
			}
		}
	}

}
