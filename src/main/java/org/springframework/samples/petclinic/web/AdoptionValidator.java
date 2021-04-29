package org.springframework.samples.petclinic.web;

import org.springframework.samples.petclinic.model.Adoption;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class AdoptionValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Adoption.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		Adoption a = (Adoption) target;

		if (a.getAdoptant() == null || a.getDescription().isEmpty()
				|| a.getDescription() == null || a.getOriginalOwner() == null || a.getPet() == null) {

			if (a.getAdoptant() == null) {
				errors.rejectValue("description", "El adoptante no puede ser nulo!", "El adoptante no puede ser nulo!");
			}

			if (a.getDescription().isEmpty() || a.getDescription() == null) {
				errors.rejectValue("description", "La descripcion no puede ser nula!",
						"La descripcion no puede ser nula!");
			}

			if (a.getOriginalOwner() == null) {
				errors.rejectValue("description", "El dueño original no puede ser nulo!",
						"El dueño original no puede ser nulo!");
			}

			if (a.getPet() == null) {
				errors.rejectValue("description", "La mascota no puede ser nula!", "La mascota no puede ser nula!");
			}

		} else if (a.getAccepted() == null && a.getAdoptant().equals(a.getOriginalOwner())) {
			errors.rejectValue("description", "El adoptante no puede ser el owner original",
					"El adoptante no puede ser el owner original");
		} else if (a.getAccepted() == null && a.getPet().getAdoption() != null) {
			errors.rejectValue("description", "Esta mascota ya tiene una petición de adopción!", "Esta mascota ya tiene una petición de adopción!");
		}else if (a.getAccepted() == null && a.getAdoptant().getAdoption() != null) {
			errors.rejectValue("description", "Ya estas en el proceso de adopcion de otra mascota",
					"Ya estas en el proceso de adopcion de otra mascota");
		}

	}

}
