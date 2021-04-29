package org.springframework.samples.petclinic.web;

import java.util.Map;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.exceptions.ConcurrentBookingsException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BookingController {
	
	Logger logger = LoggerFactory.getLogger(BookingController.class);
	
	private final PetService petService;
	
	@Autowired
	public BookingController(PetService petService) {
		this.petService = petService;
	}
	
//	@InitBinder
//	public void setAllowedFields(WebDataBinder dataBinder) {
//		dataBinder.setDisallowedFields("id");
//	}
	
	@ModelAttribute("booking")
	public Booking loadPetWithBooking(@PathVariable("petId") int petId) {
		Pet pet = this.petService.findPetById(petId);
		Booking booking = new Booking();
		pet.addBooking(booking);
		return booking;
	}
	@GetMapping(value = "/owners/*/pets/{petId}/booking/new")
	public String initNewBookingForm(@PathVariable("petId") int petId, Map<String, Object> model) {
		return "booking/formNewBooking";
	}
	
    @PostMapping("/owners/{ownerId}/pets/{petId}/booking/new")
    public String processNewBookingForm(@Valid Booking booking, BindingResult result) throws Exception {
    	if(result.hasErrors()) {
    		return "booking/formNewBooking";
    	} else {
    		try {
        		this.petService.saveBooking(booking);    			
    		} catch(ConcurrentBookingsException c){
    			result.rejectValue("checkIn", "concurrent", "Fecha no valida");
    		}
    		return "redirect:/owners/{ownerId}";
    	}
    }
    
	@GetMapping(value = "/owners/*/pets/{petId}/booking")
	public String showBookings(@PathVariable("petId") int petId, Map<String, Object> model) {
		model.put("bookings", this.petService.findPetById(petId).getBookings());
		return "booking/formNewBooking";
	}

}
