package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.model.Donation;
import org.springframework.samples.petclinic.repository.CauseRepository;
import org.springframework.samples.petclinic.repository.DonationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CauseService {
	
	private CauseRepository causeRepository;
	private DonationRepository donationRepository;
	
	@Autowired
	public CauseService(CauseRepository causeRepository) {
		this.causeRepository = causeRepository;
	}
	
	@Transactional(readOnly=true)
	public Collection<Cause> findCauses() throws DataAccessException{
		return causeRepository.findAll();
	}
	
	@Transactional(readOnly=true)
	public Optional<Cause> findById(Integer causeId) throws DataAccessException{
		return causeRepository.findById(causeId);
	}
	
	@Transactional
	public void saveCause(Cause cause) throws DataAccessException{
		this.causeRepository.save(cause);
	}
	
	@Transactional
	public void saveDonation(@Valid Donation donation) throws DataAccessException{
		donationRepository.save(donation);		
	}

}
