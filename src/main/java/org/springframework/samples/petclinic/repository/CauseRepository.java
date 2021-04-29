package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Cause;

public interface CauseRepository extends CrudRepository<Cause, Integer>{
	
	Collection<Cause> findAll() throws DataAccessException;
	
	Optional<Cause> findById(Integer causeId) throws DataAccessException;

}
