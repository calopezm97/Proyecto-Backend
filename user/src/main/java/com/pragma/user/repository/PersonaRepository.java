package com.pragma.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pragma.user.entity.DocumentoTipoEntity;
import com.pragma.user.entity.PersonaEntity;

@Repository
public interface PersonaRepository extends JpaRepository<PersonaEntity, Integer> {
	
	public Optional<PersonaEntity> findByDocumentotipoAndDocumentonumero(DocumentoTipoEntity documentoTipo,
			String documentoNumero);

	public List<PersonaEntity> findByEdadGreaterThanEqual(Integer edad);

}
