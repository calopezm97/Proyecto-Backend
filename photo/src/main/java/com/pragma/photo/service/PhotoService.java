package com.pragma.photo.service;

import java.util.List;

import com.pragma.photo.dto.PhotoDTO;

public interface PhotoService {

	PhotoDTO findById(String id);

	void savePhoto(PhotoDTO nuevo);

	PhotoDTO updatePhoto(PhotoDTO nuevo);

	boolean deletePhotoById(String id);

	List<PhotoDTO> findAllPhoto();

	public boolean isPhotoExist(PhotoDTO nuevo);
	
	List<PhotoDTO> findByIdIn(List<String> ids);
	
}
