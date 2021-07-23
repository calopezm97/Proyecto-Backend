package com.pragma.photo.serviceuse;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;

import com.pragma.photo.entity.PhotoEntity;
import com.pragma.photo.exception.ErrorException;
import com.pragma.photo.dto.PhotoDTO;
import com.pragma.photo.repository.PhotoRepository;
import com.pragma.photo.service.*;

@Service
public class PhotoServiceUse implements PhotoService {

	@Autowired
	PhotoRepository photoRepository;

	ModelMapper modelMapper = new ModelMapper();

	@Override
	@Transactional(readOnly = true)
	public PhotoDTO findById(String id) {
		Optional<PhotoEntity> archivoEntity = photoRepository.findById(id);
		if (archivoEntity.isPresent()) {
			return modelMapper.map(archivoEntity.get(), PhotoDTO.class);
		} else {
			throw new ErrorException(HttpStatus.NOT_FOUND, "No existe el archivo");
		}
	}

	@Override
	@Transactional
	public void savePhoto(PhotoDTO nuevo) {
		if (photoRepository.existsById(nuevo.getId())) {
			throw new ErrorException(HttpStatus.CONFLICT, "Ya existe el archivo");
		} else {
			photoRepository.save(modelMapper.map(nuevo, PhotoEntity.class));
		}
	}

	@Override
	@Transactional
	public PhotoDTO updatePhoto(PhotoDTO nuevo) {
		if (photoRepository.existsById(nuevo.getId())) {
			photoRepository.save(modelMapper.map(nuevo, PhotoEntity.class));
			return nuevo;
		} else {
			throw new ErrorException(HttpStatus.NOT_FOUND, "No existe el archivo");
		}
	}

	@Override
	@Transactional
	public boolean deletePhotoById(String id) {
		if (photoRepository.existsById(id)) {
			photoRepository.deleteById(id);
			return true;
		} else {
			throw new ErrorException(HttpStatus.NOT_FOUND, "No existe el archivo");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<PhotoDTO> findAllPhoto() {
		List<PhotoDTO> archivosDTOList = null;
		List<PhotoEntity> archivosEntityList = photoRepository.findAll();
		if (archivosEntityList.isEmpty()) {
			throw new ErrorException(HttpStatus.NO_CONTENT, "No existen archivos");
		} else {
			archivosDTOList = modelMapper.map(archivosEntityList, new TypeToken<List<PhotoDTO>>() {
			}.getType());
		}
		return archivosDTOList;
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isPhotoExist(PhotoDTO nuevo) {
		return photoRepository.existsById(nuevo.getId());
	}

	@Override
	@Transactional(readOnly = true)
	public List<PhotoDTO> findByIdIn(List<String> ids) {
		List<PhotoDTO> archivosDTOList = null;
		List<PhotoEntity> archivosEntityList = photoRepository.findByIdIn(ids);
		if (archivosEntityList.isEmpty()) {
			throw new ErrorException(HttpStatus.NO_CONTENT, "No existen archivos");
		} else {
			archivosDTOList = modelMapper.map(archivosEntityList, new TypeToken<List<PhotoDTO>>() {
			}.getType());
		}
		return archivosDTOList;
	}

}
