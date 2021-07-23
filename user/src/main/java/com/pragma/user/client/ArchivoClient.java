package com.pragma.user.client;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.pragma.user.dto.ArchivoDTO;

//@FeignClient(name = "microarchivo", fallback = ArchivoHystrixFallbackFactory.class)
public interface ArchivoClient {

	public final String pathClient = "/archivos";

	@PostMapping(value = pathClient + "/")
	public ResponseEntity<Void> CrearArchivo(@Valid @RequestBody ArchivoDTO archivo);

	@PutMapping(value = pathClient + "/")
	public ResponseEntity<Void> ActualizArarchivo(@RequestBody ArchivoDTO archivo);

	@GetMapping(value = pathClient + "/{id}")
	public ResponseEntity<ArchivoDTO> BuscarArchivo(@PathVariable("id") String id);

	@DeleteMapping(value = pathClient + "/{id}")
	public ResponseEntity<Void> EliminarArchivo(@PathVariable("id") String id);

	@PostMapping(value = pathClient + "/Ids")
	public ResponseEntity<List<ArchivoDTO>> ListaDeArchivosIds(@Valid @RequestBody List<String> ids);

}
