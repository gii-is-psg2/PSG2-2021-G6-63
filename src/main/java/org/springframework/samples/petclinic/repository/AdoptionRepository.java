package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Adoption;

public interface AdoptionRepository extends CrudRepository<Adoption, Integer>{

	@Query("select a from Adoption a where a.originalOwner.id = ?1")
	List<Adoption> findAdoptionsByOwnerId(Integer id);

	@Query("select a from Adoption a where a.id = ?1")
	Adoption findAdoptionsById(int adoptionId);
	
	
	
}
