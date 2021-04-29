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
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Adoption;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Controller
public class OwnerController {

	private static final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "owners/createOrUpdateOwnerForm";
	private static final String ADOPTION_LIST = "pets/adoptionList";
	private static final String PET_DETAILS = "pets/petDetails";
	private static final String ADOPT_FORM = "pets/adoptForm";
	private static final String MY_ADOPTION_LIST = "pets/myAdoptions";
	private static final String ADOPTION_DETAILS = "pets/adoptionDetails";

	private final OwnerService ownerService;
	private final PetService petService;
	private final UserService userService;

	@Autowired
	public OwnerController(OwnerService ownerService, PetService petService, UserService userService,
			AuthoritiesService authoritiesService) {
		this.ownerService = ownerService;
		this.petService = petService;
		this.userService = userService;
	}

	public Owner ownerLogeado(Principal principal) {
		String username = principal.getName();
		Owner owner = ownerService.findOwnerByUsername(username);
		return owner;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	@InitBinder("adoption")
	public void initAdoptionBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new AdoptionValidator());
	}

	@GetMapping(value = "/owners/new")
	public String initCreationForm(Map<String, Object> model) {
		Owner owner = new Owner();
		model.put("owner", owner);
		return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/owners/new")
	public String processCreationForm(@Valid Owner owner, BindingResult result) {
		if (result.hasErrors()) {
			return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
		} else {
			// creating owner, user and authorities
			this.ownerService.saveOwner(owner);

			return "redirect:/owners/" + owner.getId();
		}
	}

	@GetMapping(value = "/owners/find")
	public String initFindForm(Map<String, Object> model) {
		model.put("owner", new Owner());
		return "owners/findOwners";
	}
	
	

	@GetMapping(value = "/owners")
	public String processFindForm(Owner owner, BindingResult result, Map<String, Object> model) {

		// allow parameterless GET request for /owners to return all records
		if (owner.getLastName() == null) {
			owner.setLastName(""); // empty string signifies broadest possible search
		}

		// find owners by last name
		Collection<Owner> results = this.ownerService.findOwnerByLastName(owner.getLastName());
		if (results.isEmpty()) {
			// no owners found
			result.rejectValue("lastName", "notFound", "not found");
			return "owners/findOwners";
		} else if (results.size() == 1) {
			// 1 owner found
			owner = results.iterator().next();
			return "redirect:/owners/" + owner.getId();
		} else {
			// multiple owners found
			model.put("selections", results);
			return "owners/ownersList";
		}
	}

	@GetMapping(value = "/owners/{ownerId}/edit")
	public String initUpdateOwnerForm(@PathVariable("ownerId") int ownerId, Model model) {
		Owner owner = this.ownerService.findOwnerById(ownerId);
		model.addAttribute(owner);
		return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/owners/{ownerId}/edit")
	public String processUpdateOwnerForm(@Valid Owner owner, BindingResult result,
			@PathVariable("ownerId") int ownerId) {
		if (result.hasErrors()) {
			return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
		} else {
			owner.setId(ownerId);
			this.ownerService.saveOwner(owner);
			return "redirect:/owners/{ownerId}";
		}
	}

	/**
	 * Custom handler for displaying an owner.
	 * 
	 * @param ownerId the ID of the owner to display
	 * @return a ModelMap with the model attributes for the view
	 */
	@GetMapping("/owners/{ownerId}")
	public ModelAndView showOwner(@PathVariable("ownerId") int ownerId) {
		ModelAndView mav = new ModelAndView("owners/ownerDetails");
		mav.addObject(this.ownerService.findOwnerById(ownerId));
		return mav;
	}

	@GetMapping("/owners/{ownerId}/delete")
	public String deleteOwner(@PathVariable("ownerId") final int ownerId, final ModelMap model) {
		Owner owner = this.ownerService.findOwnerById(ownerId);
		this.ownerService.deleteOwner(owner);
		return "redirect:/owners";
	}

	@GetMapping("/pets/adoptionList")
	public String adoptionList(final ModelMap model) {
		model.put("pets", petService.findPetsInAdoption());
		return ADOPTION_LIST;
	}

	@GetMapping("/pets/{petId}")
	public String petDetails(@PathVariable("petId") final int petId, final ModelMap model) {
		model.put("pet", petService.findPetById(petId));
		model.put("owner", petService.findPetById(petId).getOwner());
		return PET_DETAILS;
	}

	@GetMapping("pets/{petId}/adopt")
	public String initAdoptForm(@PathVariable("petId") final int petId, final ModelMap model, Principal principal) {
		Owner adoptant = ownerService.findOwnerByUsername(principal.getName());
		Pet pet = petService.findPetById(petId);
		Owner originalOwner = petService.findPetById(petId).getOwner();
		model.put("pet", pet);
		model.put("originalOwner", originalOwner);
		model.put("adoptant", adoptant);
		model.put("adoption", new Adoption());
		return ADOPT_FORM;
	}

	@PostMapping("pets/{petId}/adopt")
	public String processAdoptForm(@Valid Adoption adoption, BindingResult binding, final ModelMap model) {
		if (binding.hasErrors()) {
			return ADOPT_FORM;
		}
		Owner originalOwner = adoption.getOriginalOwner();
		Owner adoptant = adoption.getAdoptant();
		if (adoptant.equals(originalOwner)) {
			model.addAttribute("message", "El adoptante no puede ser el owner original!");
			return ADOPTION_LIST;
		} else if (adoption.getPet().getAdoption() != null) {
			model.addAttribute("message", "Esta mascota ya tiene una petición de adopción!");
			return ADOPTION_LIST;
		} else if (adoptant.getAdoption() != null) {
			model.addAttribute("message", "Ya estas en el proceso de adopcion de otra mascota!");
			return ADOPTION_LIST;
		} else {

			adoption.getPet().setAdoption(adoption);
			adoption.getAdoptant().setAdoption(adoption);
			adoption.getOriginalOwner().setAdoption(adoption);

			petService.saveAdoption(adoption);
			model.addAttribute("message", "El proceso de adopcion de la mascota ha comenzado!");
			return ADOPTION_LIST;
		}
	}

	@GetMapping("pets/myAdoptions")
	public String showAdoptions(Principal principal, final ModelMap model) {
		Owner logedOwner = ownerService.findOwnerByUsername(principal.getName());
		List<Adoption> adoptions = petService.findAdoptionsByOwnerId(logedOwner.getId());
		model.put("owner", logedOwner);
		model.put("adoptions", adoptions);
		return MY_ADOPTION_LIST;
	}

	@GetMapping("pets/adoptions/{adoptionId}")
	public String adoptionDetailsGet(@PathVariable("adoptionId") final int adoptionId, Principal principal,
			final ModelMap model) {
		Adoption adoption = petService.findAdoptionById(adoptionId);
		model.put("adoption", adoption);
		return ADOPTION_DETAILS;
	}

	@PostMapping("pets/adoptions/{adoptionId}")
	public String acceptAdoption(@Valid Adoption adoption, Principal principal,
			final ModelMap model) {
		if (adoption.getAccepted()) {
			adoption.getPet().setOwner(adoption.getAdoptant());
			adoption.getPet().setIsInAdoption(false);
			adoption.getPet().setAdoption(null);
			adoption.getAdoptant().setAdoption(null);
			adoption.getOriginalOwner().setAdoption(null);
			petService.deleteAdoption(adoption);

			model.addAttribute("message", "La adopcion se ha llevado a cabo correctamente!");
			return ADOPTION_LIST;
		} else {
			adoption.getPet().setIsInAdoption(false);
			adoption.getPet().setAdoption(null);
			adoption.getAdoptant().setAdoption(null);
			adoption.getOriginalOwner().setAdoption(null);
			petService.deleteAdoption(adoption);

			model.addAttribute("message", "La adopcion se ha denegado correctamente!");
			return ADOPTION_LIST;
		}
	}

}
