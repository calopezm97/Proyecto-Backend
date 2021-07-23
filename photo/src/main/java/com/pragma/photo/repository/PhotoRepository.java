package com.pragma.photo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.pragma.photo.entity.PhotoEntity;

@Repository
public interface PhotoRepository extends MongoRepository<PhotoEntity, String> {

	List<PhotoEntity> findByIdIn(List<String> ids);

}
