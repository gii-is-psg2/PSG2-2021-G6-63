package org.springframework.samples.petclinic.model;


import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "adoptions")
public class Adoption extends BaseEntity{
	
	@NotBlank
	private String description;
	
	private Boolean accepted = null;
	
	@NotNull
	@OneToOne
	private Pet pet;
	
	@NotNull
	@OneToOne
	private Owner adoptant;
	
	@NotNull
	@OneToOne
	private Owner originalOwner;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getAccepted() {
		return accepted;
	}

	public void setAccepted(Boolean accepted) {
		this.accepted = accepted;
	}

	public Pet getPet() {
		return pet;
	}

	public void setPet(Pet pet) {
		this.pet = pet;
	}

	public Owner getAdoptant() {
		return adoptant;
	}

	public void setAdoptant(Owner adoptant) {
		this.adoptant = adoptant;
	}

	public Owner getOriginalOwner() {
		return originalOwner;
	}

	public void setOriginalOwner(Owner originalOwner) {
		this.originalOwner = originalOwner;
	}

	
}
