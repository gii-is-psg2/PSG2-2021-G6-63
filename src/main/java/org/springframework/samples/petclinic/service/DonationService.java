package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.model.Donation;
import org.springframework.samples.petclinic.repository.CauseRepository;
import org.springframework.samples.petclinic.repository.DonationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DonationService {
	
	private DonationRepository donationRepository;
	private CauseRepository causeRepository;
	
	@Autowired
	public DonationService(DonationRepository donationRepository, CauseRepository causeRepository) {
		this.donationRepository = donationRepository;
		this.causeRepository = causeRepository;
	}
	
	@Transactional
	public void save(Donation donation) throws DataAccessException{
		donation.setDate(LocalDate.now());
		Optional<Cause> causeOpt = causeRepository.findById(donation.getCause().getId());
		if(causeOpt.isPresent()) {
			Cause cause = causeOpt.get();
			Double totalDonaciones = cause.getTotalDonations();
			Double target = cause.getBudgetTarget();
			donationRepository.save(donation);
			if(totalDonaciones >= target) {
				cause.setIsClosed(true);
				causeRepository.save(cause);
			}
			
		}
	}
	
	
}
