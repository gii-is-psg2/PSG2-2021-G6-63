/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Adoption;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.repository.AdoptionRepository;
import org.springframework.samples.petclinic.repository.BookingRepository;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.samples.petclinic.repository.VisitRepository;
import org.springframework.samples.petclinic.service.exceptions.ConcurrentBookingsException;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder
 * for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 */
@Service
public class PetService {
	
	Logger logger = LoggerFactory.getLogger(PetService.class);

	private PetRepository petRepository;
	
	private VisitRepository visitRepository;
	
	private BookingRepository bookingRepository;
	
	private AdoptionRepository adoptionRepository;
	
	//private RoomRepository roomRepository;
	
	@Autowired
	public PetService(PetRepository petRepository,
			VisitRepository visitRepository, BookingRepository bookingRepository, AdoptionRepository adoptionRepository) {
		this.petRepository = petRepository;
		this.visitRepository = visitRepository;
		this.bookingRepository = bookingRepository;
		this.adoptionRepository = adoptionRepository;
	}

	@Transactional(readOnly = true)
	public Collection<PetType> findPetTypes() throws DataAccessException {
		return petRepository.findPetTypes();
	}
	
	@Transactional
	public void saveVisit(@Valid Visit visit) throws DataAccessException {
		visitRepository.save(visit);
	}
	

	@Transactional(readOnly = true)
	public Pet findPetById(int id) throws DataAccessException {
		return petRepository.findById(id);
	}

	@Transactional(rollbackFor = DuplicatedPetNameException.class)
	public void savePet(Pet pet) throws DataAccessException, DuplicatedPetNameException {
			Pet otherPet=pet.getOwner().getPetwithIdDifferent(pet.getName(), pet.getId());
            if (StringUtils.hasLength(pet.getName()) &&  (otherPet!= null && otherPet.getId()!=pet.getId())) {            	
            	throw new DuplicatedPetNameException();
            }else
                petRepository.save(pet);                
	}


	public Collection<Visit> findVisitsByPetId(int petId) {
		return visitRepository.findByPetId(petId);
	}
	
	public Collection<Booking> findBookingsByPetId(int petId) {
		return bookingRepository.findByPetId(petId);
	}
	
	@Transactional
	public void deleteVisit(final Visit visit){
		this.visitRepository.delete(visit);
	}
	
	@Transactional
	public Visit findVisitById(final int visitId) {
		return visitRepository.findVisitById(visitId);
	}
	
	@Transactional
	public Booking findBookingById(final int bookingID) {
		return bookingRepository.findBookingById(bookingID);
	}
	
    @Transactional
    public void deletePet(final Pet pet) throws DataAccessException{
        this.petRepository.delete(pet);
    }

    @Transactional(readOnly = true)
	public List<Pet> findPetsInAdoption() {
		return this.petRepository.findPetsInAdoption();
	}
    
    @Transactional
    public void saveAdoption(@Valid Adoption adoption) {
    	this.adoptionRepository.save(adoption);
    }

    @Transactional(readOnly = true)
	public List<Adoption> findAdoptionsByOwnerId(Integer id) {
		return this.adoptionRepository.findAdoptionsByOwnerId(id);
	}

    @Transactional(readOnly = true)
	public Adoption findAdoptionById(int adoptionId) {
		return this.adoptionRepository.findAdoptionsById(adoptionId);
	}

    @Transactional
	public void deleteAdoption(Adoption adoption) throws DataAccessException{
		this.adoptionRepository.delete(adoption);
		
	}

	public void saveBooking(@Valid Booking booking) throws ConcurrentBookingsException { 
		
    	LocalDate checkIn = booking.getCheckIn();
    	LocalDate checkOut = booking.getCheckOut();
    	
    	Pet pet = petRepository.findById(booking.getPet().getId()).get();
    	List<Booking> listaBookings = bookingRepository.findByPetId(pet.getId());
    	if(listaBookings.isEmpty()) {
    		bookingRepository.save(booking);
    	}else {
    		for(Booking b: listaBookings) {
        		LocalDate in = b.getCheckIn();
        		LocalDate out = b.getCheckOut();
        		
        		//in y out están dentro del rango de la reserva
        		if((checkIn.isAfter(in) && checkIn.isBefore(out)) ||		//  in  [CIN    out  
        			checkOut.isAfter(in) && checkOut.isBefore(out) ||		//	in	COUT]	out
        			checkIn.isBefore(in) && checkOut.isAfter(out)||			//	[CIN  in	out	COUT]    			
        			checkIn.isEqual(in) ||
        			checkIn.isEqual(out) ||
        			checkOut.isEqual(in) ||
        			checkOut.isEqual(out) ||
        			checkIn.isAfter(checkOut)) {
        			
        			throw new ConcurrentBookingsException();
        		
        		} else {
        			bookingRepository.save(booking);
        		}
    	}
    	
    	
    	}
	}
}
