package com.pragma.photo;

import com.pragma.photo.entity.PhotoEntity;
import com.pragma.photo.repository.PhotoRepository;
import com.pragma.photo.serviceuse.PhotoServiceUse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class PhotoApplicationTests {

	@InjectMocks
	private PhotoServiceUse photoServiceUse;

	@Mock
	private PhotoRepository photoRepository;

	@BeforeEach
	public void DatosBaseParaLosTest() {
		MockitoAnnotations.openMocks(this);
		PhotoEntity photoEntity1 = PhotoEntity.builder().id("60d4c1fdaba981f736e01d7d").data("Mi fotoooo1")
				.tipo(".jpg").build();
		PhotoEntity photoEntity2 = PhotoEntity.builder().id("60d4c1fdaba981f736e01d7e").data("Mi fotoooo2")
				.tipo(".jpg").build();
		List<PhotoEntity> photoEntityList = new ArrayList<>();
		photoEntityList.add(photoEntity1);
		photoEntityList.add(photoEntity2);
		List<String> IdsList = new ArrayList<>();
		IdsList.add("60d4c1fdaba981f736e01d7d");
		IdsList.add("60d4c1fdaba981f736e01d7e");
		Mockito.when(photoRepository.findByIdIn(IdsList)).thenReturn(photoEntityList);
	}

	@Test
	public void findByIdIn() {
		List<String> IdsList = new ArrayList<>();
		IdsList.add("60d4c1fdaba981f736e01d7d");
		IdsList.add("60d4c1fdaba981f736e01d7e");
		Assertions.assertThat(photoServiceUse.findByIdIn(IdsList).size()).isEqualTo(2);
	}

}
