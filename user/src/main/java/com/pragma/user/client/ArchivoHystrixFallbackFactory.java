package com.pragma.user.client;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.pragma.user.client.ArchivoClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.pragma.user.dto.ArchivoDTO;

@Component
public class ArchivoHystrixFallbackFactory implements ArchivoClient {

	@Override
	public ResponseEntity<ArchivoDTO> BuscarArchivo(String id) {
		// TODO Auto-generated method stub
		return new ResponseEntity<ArchivoDTO>(ArchivoDTO.builder().build(), HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<Void> EliminarArchivo(String id) {
		// TODO Auto-generated method stub
		return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<List<ArchivoDTO>> ListaDeArchivosIds(@Valid List<String> ids) {
		// TODO Auto-generated method stub
		return new ResponseEntity<List<ArchivoDTO>>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<Void> CrearArchivo(@Valid ArchivoDTO archivo) {
		// TODO Auto-generated method stub
		return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<Void> ActualizArarchivo(ArchivoDTO archivo) {
		// TODO Auto-generated method stub
		return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
	}

}
