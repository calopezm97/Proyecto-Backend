package com.pragma.photo.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pragma.photo.dto.PhotoDTO;
import com.pragma.photo.serviceuse.PhotoServiceUse;
import io.swagger.annotations.ApiParam;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
@RequestMapping(value = "/photo")
public class PhotoController {

	@Autowired
	private PhotoServiceUse photoService;

	@GetMapping(value = "/")
	public ResponseEntity<List<PhotoDTO>> ListaDeArchivos() {
		return new ResponseEntity<List<PhotoDTO>>(photoService.findAllPhoto(), HttpStatus.OK);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<PhotoDTO> BuscarArchivo(
			@ApiParam(value = "Identificador del archivo", required = true) @PathVariable("id") String id) {
		return new ResponseEntity<PhotoDTO>(photoService.findById(id), HttpStatus.OK);
	}

	@PostMapping(value = "/")
	public ResponseEntity<Void> CrearArchivo(
			@ApiParam(value = "Un JSON de archivo", required = true) @Valid @RequestBody PhotoDTO archivo)
			throws IOException {
		photoService.savePhoto(archivo);
		return new ResponseEntity<Void>(HttpStatus.CREATED);

	}

	@PutMapping(value = "/")
	public ResponseEntity<Void> ActualizArarchivo(
			@ApiParam(value = "Un JSON de archivo", required = true) @RequestBody PhotoDTO archivo) {
		photoService.updatePhoto(archivo);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> EliminarArchivo(
			@ApiParam(value = "Identificador del archivo", required = true) @PathVariable("id") String id) {
		photoService.deletePhotoById(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@PostMapping(value = "/Ids")
	public ResponseEntity<List<PhotoDTO>> ListaDeArchivosIds(
			@ApiParam(value = "Un JSON de archivo", required = true) @Valid @RequestBody List<String> ids)
			throws IOException {
		return new ResponseEntity<List<PhotoDTO>>(photoService.findByIdIn(ids), HttpStatus.OK);

	}

}
