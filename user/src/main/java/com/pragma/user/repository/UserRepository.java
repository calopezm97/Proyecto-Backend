package com.pragma.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pragma.user.entity.DocumentoTipoEntity;
import com.pragma.user.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
	
	public Optional<UserEntity> findByDocumentotipoAndDocumentonumero(DocumentoTipoEntity documentoTipo,
                                                                      String documentoNumero);

	public List<UserEntity> findByEdadGreaterThanEqual(Integer edad);

}
