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
package org.springframework.samples.petclinic.web;

import java.security.Principal;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Controller
public class VisitController {

	Logger logger = LoggerFactory.getLogger(VisitController.class);
	
	private static final String LIST_OWNERS = "owners/ownersList";

	private final OwnerService ownerService;
	private final PetService petService;

	public Owner ownerLogeado(Principal principal) {
		String username = principal.getName();
		return ownerService.findOwnerByUsername(username);
	}
	
	@Autowired
	public VisitController(PetService petService, OwnerService ownerService) {
		this.ownerService = ownerService;
		this.petService = petService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	/**
	 * Called before each and every @GetMapping or @PostMapping annotated method. 2
	 * goals: - Make sure we always have fresh data - Since we do not use the
	 * session scope, make sure that Pet object always has an id (Even though id is
	 * not part of the form fields)
	 * 
	 * @param petId
	 * @return Pet
	 */
//	@ModelAttribute("visit")
//	public Visit loadPetWithVisit(@PathVariable("petId") int petId) {
//		Pet pet = this.petService.findPetById(petId);
//		Visit visit = new Visit();
//		pet.addVisit(visit);
//		return visit;
//	}

	// Spring MVC calls method loadPetWithVisit(...) before initNewVisitForm is
	// called
	@GetMapping(value = "/owners/{ownerId}/pets/{petId}/visits/new")
	public String initNewVisitForm(@PathVariable("petId") int petId, @PathVariable("ownerId") int ownerId,
			ModelMap model, Principal principal) {
		Visit visit = new Visit();
		visit.setDescription(" ");
		if(!principal.getName().equals("admin1")) {
			Owner logeado = ownerLogeado(principal);
			if(logeado.getId() != ownerId) {
				model.addAttribute("message", "No tienes permisos para añadir una visita a esta mascota!");
				return LIST_OWNERS;
			}
		}		
		Pet pet = this.petService.findPetById(petId);
		pet.addVisit(visit);
		model.addAttribute("visit", visit);
		return "pets/createOrUpdateVisitForm";
	}

	// Spring MVC calls method loadPetWithVisit(...) before processNewVisitForm is
	// called
	@PostMapping(value = "/owners/{ownerId}/pets/{petId}/visits/new")
	public String processNewVisitForm(@Valid Visit visit, @PathVariable("petId") int petId, BindingResult result) {
		if (result.hasErrors()) {
			return "pets/createOrUpdateVisitForm";
		} else {
			Pet pet = this.petService.findPetById(petId);
			pet.addVisit(visit);
			this.petService.saveVisit(visit);
			return "redirect:/owners/{ownerId}";
		}
	}

	@GetMapping(value = "/owners/*/pets/{petId}/visits")
	public String showVisits(@PathVariable int petId, Map<String, Object> model) {
		model.put("visits", this.petService.findPetById(petId).getVisits());
		return "visitList";
	}

	@GetMapping(value = "/owners/{ownerId}/pets/{petId}/visits/{visitId}/delete")
	public String deleteVisit(@PathVariable int ownerId, @PathVariable int petId, @PathVariable int visitId,
			ModelMap model, Principal principal) {
		if(!principal.getName().equals("admin1")) {
			Owner logeado = ownerLogeado(principal);
			if(logeado.getId() != ownerId) {
				model.addAttribute("message", "No tienes permisos para eliminar una visita de esta mascota!");
				return LIST_OWNERS;
			}
		}		
		Pet pet = this.petService.findPetById(petId);
		Visit visit = this.petService.findVisitById(visitId);
		pet.deleteVisit(visit);
		petService.deleteVisit(visit);
		return "redirect:/owners/{ownerId}";
	}
}
