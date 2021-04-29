package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

@Entity
@Table(name = "donations")
public class Donation extends BaseEntity{
	
	@NotNull
	@Range(min=1)
	private Double quantity;
	
	@NotNull
	private LocalDate date;
	
	@ManyToOne
	@JoinColumn(name = "cause_id")
	private Cause cause;
	
	@ManyToOne
	@JoinColumn(name="owner_id")
	private Owner owner;

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Cause getCause() {
		return cause;
	}

	public void setCause(Cause cause) {
		this.cause = cause;
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	@Override
	public String toString() {
		return "Donation [quantity=" + quantity + ", date=" + date + ", cause=" + cause + ", owner=" + owner + "]";
	}
	
	

}
