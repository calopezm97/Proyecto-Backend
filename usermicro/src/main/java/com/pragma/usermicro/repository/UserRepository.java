package com.pragma.usermicro.repository;

import java.util.List;
import java.util.Optional;

import com.pragma.usermicro.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
	
	public Optional<UserEntity> findByDocumentotipoAndDocumentonumero(int documentoTipo, String documentoNumero);

	public List<UserEntity> findByEdadGreaterThanEqual(Integer edad);

}
