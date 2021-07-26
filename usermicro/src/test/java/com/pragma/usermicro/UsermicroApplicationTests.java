package com.pragma.usermicro;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.pragma.usermicro.client.PhotoClient;
import com.pragma.usermicro.dto.PhotoDTO;
import com.pragma.usermicro.dto.UserDTO;
import com.pragma.usermicro.entity.UserEntity;
import com.pragma.usermicro.repository.UserRepository;
import com.pragma.usermicro.service.use.UserServiceUse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

//@SpringBootTest
class UsermicroApplicationTests {

	@ExtendWith(MockitoExtension.class)
	@TestInstance(Lifecycle.PER_CLASS)
	public class PersonaServiceMockTest {

		@InjectMocks
		UserServiceUse personaServiceUse;

		@Mock
		UserRepository personaRepository;

		@Mock
		PhotoClient archivoClient;

		@BeforeAll
		public void DatosBaseParaLosTest() {
			MockitoAnnotations.openMocks(this);


			UserEntity userEntity1 = UserEntity.builder().id(11).nombres("Jorge").apellidos("Anaya")
					.documentotipo("Cedula").documentonumero("12312").edad(10).ciudadnacimiento("Armenia")
					.idfoto("60d4c1fdaba981f736e01d7d").build();
			UserEntity userEntity2 = UserEntity.builder().id(22).nombres("Jorge2").apellidos("Anaya2")
					.documentotipo("Cedula").documentonumero("12412").edad(20).ciudadnacimiento("Genova")
					.idfoto("60d4c1fdaba981f736e01d7e").build();

			List<UserEntity> personaEntityList = new ArrayList<UserEntity>();
			personaEntityList.add(userEntity1);
			personaEntityList.add(userEntity2);

			List<String> idsStringList = new ArrayList<>();
			idsStringList.add("60d4c1fdaba981f736e01d7d");
			idsStringList.add("60d4c1fdaba981f736e01d7e");

			PhotoDTO archivoDTO1 = PhotoDTO.builder().id("60d4c1fdaba981f736e01d7d").data("Mi foto3").tipo(".jpg")
					.build();
			PhotoDTO archivoDTO2 = PhotoDTO.builder().id("60d4c1fdaba981f736e01d7e").data("Mi foto4").tipo(".jpg")
					.build();

			List<PhotoDTO> archivoDTOList = new ArrayList<PhotoDTO>();
			archivoDTOList.add(archivoDTO1);
			archivoDTOList.add(archivoDTO2);

			Mockito.when(personaRepository.findByEdadGreaterThanEqual(5)).thenReturn(personaEntityList);
			Mockito.when(personaRepository.findByDocumentotipoAndDocumentonumero("CC", "12312"))
					.thenReturn(Optional.of(userEntity1));

			Mockito.when(archivoClient.BuscarArchivo("60d4c1fdaba981f736e01d7d"))
					.thenReturn((ResponseEntity<PhotoDTO>) ResponseEntity.ok(archivoDTO1));
			Mockito.when(archivoClient.ListaDeArchivosIds(idsStringList))
					.thenReturn((ResponseEntity<List<PhotoDTO>>) ResponseEntity.ok(archivoDTOList));
		}

		@Test
		public void findByDocumentotipoAndDocumentonumero() {
//		Como buenas practicas deber�a estar en cada @ Test lo siguente: dado esto -- cuando esto -- entonces esto
			UserDTO personaDTO = personaServiceUse.findByDocumentotipoAndDocumentonumero(1, "12312");
			Assertions.assertThat(personaDTO.getNombres()).isEqualTo("Jorge");
		}

		@Test
		public void findByEdadGreaterThanEqual() {
//		Como buenas practicas deber�a estar en cada @ Test lo siguente: dado esto -- cuando esto -- entonces esto
			List<UserDTO> personaDTOList = personaServiceUse.findByEdadGreaterThanEqual(5);
			Assertions.assertThat(personaDTOList.size()).isEqualTo(2);
		}
	}
}
