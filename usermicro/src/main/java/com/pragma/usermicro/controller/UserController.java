package com.pragma.usermicro.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import com.pragma.usermicro.dto.UserDTO;
import com.pragma.usermicro.service.UserService;
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

import io.swagger.annotations.ApiParam;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
@RequestMapping(value = "/usermicro")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping(value = "/")
	public ResponseEntity<List<UserDTO>> ListaDeUsers() {
		return new ResponseEntity<List<UserDTO>>(userService.findAllUser(), HttpStatus.OK);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<UserDTO> BuscarUser(
			@ApiParam(value = "Identificador de la User", required = true) @PathVariable("id") int id) {
		return new ResponseEntity<UserDTO>(userService.findById(id), HttpStatus.OK);
	}

	@GetMapping(value = "/{idTipoDeDocumento}/{numeroDeDocumento}")
	public ResponseEntity<UserDTO> BuscarUser(
			@ApiParam(value = "Id del Tipo de documento", required = true) @PathVariable("idTipoDeDocumento") String tipoDeDocumento,
			@ApiParam(value = "Numero de documento", required = true) @PathVariable("numeroDeDocumento") String numeroDeDocumento) {
		return new ResponseEntity<UserDTO>(
				userService.findByDocumentotipoAndDocumentonumero(tipoDeDocumento, numeroDeDocumento),
				HttpStatus.OK);
	}

	@GetMapping(value = "/Edad/{edad}")
	public ResponseEntity<List<UserDTO>> BuscarUsera(
			@ApiParam(value = "Edad para la condici???n", required = true) @PathVariable("edad") int edad) {
		return new ResponseEntity<List<UserDTO>>(userService.findByAgeGreaterThanEqual(edad), HttpStatus.OK);
	}

	@PostMapping(value = "/")
	public ResponseEntity<Void> CrearUser(
			@ApiParam(value = "Un JSON de User", required = true) @Valid @RequestBody UserDTO User)
			throws IOException {
		userService.saveUser(User);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	@PutMapping(value = "/")
	public ResponseEntity<Void> ActualizarUser(
			@ApiParam(value = "Un JSON de User", required = true) @RequestBody UserDTO User) {
		userService.updateUser(User);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> EliminarUser(
			@ApiParam(value = "Identificador de la User", required = true) @PathVariable("id") int id) {
		userService.deleteUserById(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}
