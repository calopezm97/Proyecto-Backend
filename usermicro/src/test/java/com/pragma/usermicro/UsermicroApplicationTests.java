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
	public static class userServiceMockTest {

		@InjectMocks
		UserServiceUse userServiceUse;

		@Mock
		UserRepository userRepository;

		@Mock
		PhotoClient photoClient;

		@BeforeAll
		public void DatosBaseParaLosTest() {
			MockitoAnnotations.openMocks(this);


			UserEntity userEntity1 = UserEntity.builder().id(1).nombres("Carlos").apellidos("Lopez Mazo")
					.documentotipo("CC").documentonumero("1097405899").edad(24).ciudadnacimiento("Genova")
					.idfoto("60d4c1fdaba981f736e01d7d").build();
			UserEntity userEntity2 = UserEntity.builder().id(22).nombres("Carlos Alberto").apellidos("Lopez")
					.documentotipo("CC").documentonumero("1097405900").edad(20).ciudadnacimiento("Genova")
					.idfoto("60d4c1fdaba981f736e01d7e").build();

			List<UserEntity> userEntityList = new ArrayList<>();
			userEntityList.add(userEntity1);
			userEntityList.add(userEntity2);

			List<String> idsStringList = new ArrayList<>();
			idsStringList.add("60d4c1fdaba981f736e01d7d");
			idsStringList.add("60d4c1fdaba981f736e01d7e");

			PhotoDTO photoDTO1 = PhotoDTO.builder().id("60d4c1fdaba981f736e01d7d").data("Mi foto1").tipo(".jpg")
					.build();
			PhotoDTO photoDTO2 = PhotoDTO.builder().id("60d4c1fdaba981f736e01d7e").data("Mi foto2").tipo(".jpg")
					.build();

			List<PhotoDTO> archivoDTOList = new ArrayList<>();
			archivoDTOList.add(photoDTO1);
			archivoDTOList.add(photoDTO2);

			Mockito.when(userRepository.findByEdadGreaterThanEqual(5)).thenReturn(userEntityList);
			Mockito.when(userRepository.findByDocumentotipoAndDocumentonumero("CC", "1097405899"))
					.thenReturn(Optional.of(userEntity1));

			Mockito.when(photoClient.BuscarArchivo("60d4c1fdaba981f736e01d7d"))
					.thenReturn((ResponseEntity<PhotoDTO>) ResponseEntity.ok(photoDTO1));
			Mockito.when(photoClient.ListaDeArchivosIds(idsStringList))
					.thenReturn((ResponseEntity<List<PhotoDTO>>) ResponseEntity.ok(archivoDTOList));
		}

		@Test
		public void findByDocumentotipoAndDocumentonumero() {
			UserDTO userDTO = userServiceUse.findByDocumentotipoAndDocumentonumero("CC", "1097405899");
			Assertions.assertThat(userDTO.getNombres()).isEqualTo("Carlos");
		}

		@Test
		public void findByAgeGreaterThanEqual() {
			List<UserDTO> userDTOList = userServiceUse.findByAgeGreaterThanEqual(5);
			Assertions.assertThat(userDTOList.size()).isEqualTo(2);
		}
	}
}
