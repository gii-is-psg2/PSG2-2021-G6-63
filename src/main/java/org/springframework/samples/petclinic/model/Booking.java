package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
@Entity
@Table(name = "bookings")
public class Booking extends BaseEntity{
	
	@Column(name = "details")
	private String details;
	
	@Column(name = "checkIn")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate checkIn;
	
	@Column(name = "checkOut")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate checkOut;
	
	@ManyToOne
	@JoinColumn(name = "pet_id")
	private Pet pet;

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public LocalDate getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(LocalDate checkIn) {
		this.checkIn = checkIn;
	}

	public LocalDate getCheckOut() {
		return checkOut;
	}

	public void setCheckOut(LocalDate checkOut) {
		this.checkOut = checkOut;
	}

	public Pet getPet() {
		return pet;
	}

	public void setPet(Pet pet) {
		this.pet = pet;
	}

	@Override
	public String toString() {
		return "Booking [details=" + details + ", checkIn=" + checkIn + ", checkOut=" + checkOut + ", pet=" + pet
				+ ", room=" + null + "]";
	}

}
