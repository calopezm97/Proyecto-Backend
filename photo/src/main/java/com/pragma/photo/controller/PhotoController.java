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
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
@RequestMapping(value = "/photo")
public class PhotoController {

	@Autowired
	private PhotoServiceUse archivoService;

	@GetMapping(value = "/")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Todo bien"),
			@ApiResponse(code = 401, message = "No autorizado el acceso"),
			@ApiResponse(code = 403, message = "Prohibido el acceso"),
			@ApiResponse(code = 404, message = "No encontrado") })
	@ApiOperation(value = "Lista de todos los archivos", notes = "Proporciona una lista de archivos de la base de datos")
	public ResponseEntity<List<PhotoDTO>> ListaDeArchivos() {
		return new ResponseEntity<List<PhotoDTO>>(archivoService.findAllPhoto(), HttpStatus.OK);
	}

	@GetMapping(value = "/{id}")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Todo bien"),
			@ApiResponse(code = 401, message = "No autorizado el acceso"),
			@ApiResponse(code = 403, message = "Prohibido el acceso"),
			@ApiResponse(code = 404, message = "No encontrado") })
	@ApiOperation(value = "Busca un archivo por identificador(id)", notes = "Porpocionar un identificador(id) para encontrar el archivo a la que corresponde", response = PhotoDTO.class)
	public ResponseEntity<PhotoDTO> BuscarArchivo(
			@ApiParam(value = "Identificador del archivo", required = true) @PathVariable("id") String id) {
		return new ResponseEntity<PhotoDTO>(archivoService.findById(id), HttpStatus.OK);
	}

	@PostMapping(value = "/")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Todo bien"),
			@ApiResponse(code = 201, message = "Creado"), @ApiResponse(code = 401, message = "No autorizado el acceso"),
			@ApiResponse(code = 403, message = "Prohibido el acceso"),
			@ApiResponse(code = 404, message = "No encontrado") })
	@ApiOperation(value = "Crear un archivo", notes = "Porpocionar los datos necesarios para crear un archivo")
	public ResponseEntity<Void> CrearArchivo(
			@ApiParam(value = "Un JSON de archivo", required = true) @Valid @RequestBody PhotoDTO archivo)
			throws IOException {
		archivoService.savePhoto(archivo);
		return new ResponseEntity<Void>(HttpStatus.CREATED);

	}

	@PutMapping(value = "/")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Todo bien"),
			@ApiResponse(code = 201, message = "Creado"), @ApiResponse(code = 401, message = "No autorizado el acceso"),
			@ApiResponse(code = 403, message = "Prohibido el acceso"),
			@ApiResponse(code = 404, message = "No encontrado") })
	@ApiOperation(value = "Actualizar un archivo", notes = "Porpocionar el identificador(id) y dem�s datos necesarios para actualizar un archivo")
	public ResponseEntity<Void> ActualizArarchivo(
			@ApiParam(value = "Un JSON de archivo", required = true) @RequestBody PhotoDTO archivo) {
		archivoService.updatePhoto(archivo);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Todo bien"),
			@ApiResponse(code = 204, message = "Sin contenido"),
			@ApiResponse(code = 401, message = "No autorizado el acceso"),
			@ApiResponse(code = 403, message = "Prohibido el acceso") })
	@ApiOperation(value = "Elimina un archivo por identificador(id)", notes = "Porpocionar el identificador(id) para eliminar un archivo")
	public ResponseEntity<Void> EliminarArchivo(
			@ApiParam(value = "Identificador del archivo", required = true) @PathVariable("id") String id) {
		archivoService.deletePhotoById(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@PostMapping(value = "/Ids")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Todo bien"),
			@ApiResponse(code = 401, message = "No autorizado el acceso"),
			@ApiResponse(code = 403, message = "Prohibido el acceso"),
			@ApiResponse(code = 404, message = "No encontrado") })
	@ApiOperation(value = "Lista de archivos por ids", notes = "Porpocionar los ids para consultar la lista de archivos")
	public ResponseEntity<List<PhotoDTO>> ListaDeArchivosIds(
			@ApiParam(value = "Un JSON de archivo", required = true) @Valid @RequestBody List<String> ids)
			throws IOException {
		return new ResponseEntity<List<PhotoDTO>>(archivoService.findByIdIn(ids), HttpStatus.OK);

	}

}
