package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.configuration.SecurityUtils;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.model.Donation;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.service.CauseService;
import org.springframework.samples.petclinic.service.DonationService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DonationController {

	private final CauseService causeService;
	private final DonationService donationService;
	private final OwnerService ownerService;

	@Autowired
	public DonationController(CauseService causeService, DonationService donationService, OwnerService ownerService) {
		this.causeService = causeService;
		this.donationService = donationService;
		this.ownerService = ownerService;
	}
	
	@ModelAttribute("donation")
	public Donation addDonationToCause(@PathVariable("causeId") int causeId) {
		Owner owner = ownerService.findOwnerByUsername(SecurityUtils.getUserName());
		Cause cause = new Cause();
		cause = causeService.findById(causeId).get();
		
		Donation donation = new Donation();
		donation.setOwner(owner);
		donation.setDate(LocalDate.now());
		
		cause.addDonation(donation);
		return donation;
	}
	
	@GetMapping(value= {"/causes/{causeId}/donation/new"})
	public String initCreateDonationForm(@PathVariable("causeId") int causeId, Map<String, Object> model){
        Donation donation = new Donation();
        Cause cause = new Cause();
        cause = causeService.findById(causeId).get();
        cause.addDonation(donation);
        model.put("donation", donation);
		return "causes/createDonation";
	}
	
	@PostMapping(value= {"/causes/{causeId}/donation/new"})
	public String processNewDonationForm(@Valid Donation donation, @PathVariable("causeId") int causeId, BindingResult result) {
    	if(result.hasErrors()) {
    		return "causes/createDonation";
    	} else {
            Cause cause = new Cause();
            cause = causeService.findById(causeId).get();
            cause.addDonation(donation);
            this.donationService.save(donation);
    		return "redirect:/causes/{causeId}/show";
    	}
	}
	
}
