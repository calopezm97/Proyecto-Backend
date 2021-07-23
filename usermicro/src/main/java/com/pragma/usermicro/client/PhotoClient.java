package com.pragma.usermicro.client;

import java.util.List;

import javax.validation.Valid;

import com.pragma.usermicro.dto.PhotoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


public interface PhotoClient {

	public final String pathClient = "/photo";

	@PostMapping(value = pathClient + "/")
	public ResponseEntity<Void> CrearArchivo(@Valid @RequestBody PhotoDTO archivo);

	@PutMapping(value = pathClient + "/")
	public ResponseEntity<Void> ActualizArarchivo(@RequestBody PhotoDTO archivo);

	@GetMapping(value = pathClient + "/{id}")
	public ResponseEntity<PhotoDTO> BuscarArchivo(@PathVariable("id") String id);

	@DeleteMapping(value = pathClient + "/{id}")
	public ResponseEntity<Void> EliminarArchivo(@PathVariable("id") String id);

	@PostMapping(value = pathClient + "/Ids")
	public ResponseEntity<List<PhotoDTO>> ListaDeArchivosIds(@Valid @RequestBody List<String> ids);

}
