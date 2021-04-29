package org.springframework.samples.petclinic.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "causes")
public class Cause extends BaseEntity{
	
	@Column(name = "name")
	@NotEmpty
	private String name;
	
	@Column(name = "description")
	@NotEmpty
	private String description;
	
	@Column(name = "budgetTarget")
	@NotNull
	private Double budgetTarget;
	
	@Column(name = "organization")
	@NotEmpty
	private String organization;
	
	@Column(name = "isClosed")
	private Boolean isClosed;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "cause", fetch = FetchType.EAGER)
	private Set<Donation> donations;
	
	public Double getTotalDonations() {
		return donations.stream().mapToDouble(x -> x.getQuantity()).sum();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getBudgetTarget() {
		return budgetTarget;
	}

	public void setBudgetTarget(Double budgetTarget) {
		this.budgetTarget = budgetTarget;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public Boolean getIsClosed() {
		return isClosed;
	}

	public void setIsClosed(Boolean isClosed) {
		this.isClosed = isClosed;
	}

	public Set<Donation> getDonations() {
		return donations;
	}

	public void setDonations(Set<Donation> donations) {
		this.donations = donations;
	}

	@Override
	public String toString() {
		return "Cause [name=" + name + ", description=" + description + ", budgetTarget=" + budgetTarget
				+ ", organization=" + organization + ", isClosed=" + isClosed 	  + "]";
	}
	
	protected Set<Donation> getDonationsInternal() {
		if (this.donations == null) {
			this.donations = new HashSet<>();
		}
		return this.donations;
	}

	public void addDonation(Donation donation) {
		getDonationsInternal().add(donation);
		donation.setCause(this);
//		Double totalDonaciones = this.getTotalDonations();
//		Double target = this.getBudgetTarget();
//		if(totalDonaciones >= target) {
//			this.setIsClosed(true);
//		}
		
	}

}
