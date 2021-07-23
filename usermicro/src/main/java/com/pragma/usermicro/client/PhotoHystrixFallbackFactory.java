package com.pragma.usermicro.client;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.pragma.usermicro.dto.PhotoDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


@Component
public class PhotoHystrixFallbackFactory implements PhotoClient {

	@Override
	public ResponseEntity<PhotoDTO> BuscarArchivo(String id) {
		return new ResponseEntity<PhotoDTO>(PhotoDTO.builder().build(), HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<Void> EliminarArchivo(String id) {
		return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<List<PhotoDTO>> ListaDeArchivosIds(@Valid List<String> ids) {
		return new ResponseEntity<List<PhotoDTO>>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<Void> CrearArchivo(@Valid PhotoDTO archivo) {
		return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<Void> ActualizArarchivo(PhotoDTO archivo) {
		return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
	}

}
