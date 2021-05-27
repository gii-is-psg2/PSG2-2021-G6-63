package org.springframework.samples.petclinic.web;

import java.security.Principal;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.exceptions.ConcurrentBookingsException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BookingController {
	
	Logger logger = LoggerFactory.getLogger(BookingController.class);
	
	private static final String CREATE_BOOKING_FORM = "booking/formNewBooking";
	
	private final PetService petService;
	
	private final OwnerService ownerService;
	
	private static final String LIST_OWNERS = "owners/ownersList";
	
	@Autowired
	public BookingController(PetService petService, OwnerService ownerService) {
		this.petService = petService;
		this.ownerService = ownerService;
	}
	
//	@ModelAttribute("booking")
//	public Booking loadPetWithBooking(@PathVariable("petId") int petId) {
//		Pet pet = this.petService.findPetById(petId);
//		Booking booking = new Booking();
//		pet.addBooking(booking);
//		return booking;
//	}
	
	public Owner ownerLogeado(Principal principal) {
        String username = principal.getName();
		return ownerService.findOwnerByUsername(username);
    }
	
	@GetMapping(value = "/owners/{ownerId}/pets/{petId}/booking/new")
	public String initNewBookingForm(@PathVariable("petId") int petId, @PathVariable("ownerId") int ownerId, ModelMap model, Principal principal) {
		Owner logeado = ownerLogeado(principal);
		Booking booking = new Booking();
		Pet pet = petService.findPetById(petId);
        if(logeado.getId() != ownerId) {
        	model.addAttribute("message", "No tienes permisos para añadir una reserva a esta mascota!");
            return LIST_OWNERS;
        } else {
        	pet.addBooking(booking);
        	model.addAttribute("petId", petId);
        	model.addAttribute("booking", booking);
        	return CREATE_BOOKING_FORM;	
        }
	}
	
    @PostMapping("/owners/{ownerId}/pets/{petId}/booking/new")
    public String processNewBookingForm(@Valid Booking booking, BindingResult result) throws Exception {
    	if(result.hasErrors()) {
    		return CREATE_BOOKING_FORM;
    	} else {
    		try {
        		this.petService.saveBooking(booking);    			
    		} catch(ConcurrentBookingsException c){
    			result.rejectValue("checkOut", "duplicate", "Fecha no valida");
    		}
    		return "redirect:/owners/{ownerId}/pets/{petId}/booking/new";
    	}
    }
    
	@GetMapping(value = "/owners/*/pets/{petId}/booking")
	public String showBookings(@PathVariable("petId") int petId, ModelMap model) {
		model.put("bookings", this.petService.findPetById(petId).getBookings());
		return CREATE_BOOKING_FORM;
	}

}
