package org.springframework.samples.petclinic.web;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.model.Causes;
import org.springframework.samples.petclinic.service.CauseService;
import org.springframework.samples.petclinic.service.DonationService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CauseController {
		
	private final CauseService causeService;
	private final DonationService donationService;

	@Autowired
	public CauseController(CauseService causeService, DonationService donationService) {
		this.causeService = causeService;
		this.donationService = donationService;
	}
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	@GetMapping(value = { "/causes" })
	public String showCausesList(Map<String, Object> model) {
		Causes causes = new Causes();
		causes.getCauseList().addAll(this.causeService.findCauses());
		model.put("causes", causes);
		return "causes/causesList";
	}
	
	@GetMapping(value="/causes/{causeId}/show")
	public String showCause(Map<String, Object> model, @PathVariable("causeId") int causeId) {
		Cause cause = this.causeService.findById(causeId).get();
		model.put("cause", cause);
		return "causes/causeDetails";
	}
	
	@GetMapping(value="/causes/new")
	public String initCreateCauseForm(Map<String, Object> model) {
		Cause cause = new Cause();
		model.put("cause", cause);
		return "causes/createCause";
	}
	
	@PostMapping(value = "/causes/new")
	public String processNewCauseForm(@Valid Cause cause, BindingResult result) {
		if(result.hasErrors()) {
			return "causes/createCause";
		} else {
			this.causeService.saveCause(cause);
			return "redirect:/causes";
		}
	}
	
}
