package com.force.example.fulfillment.pet.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.core.style.ToStringCreator;

@Entity
@Table(name="PETS")
public class Pet {
    @Id
    @GeneratedValue
	private Integer petId;
	
    @Column(unique = true)
	@NotNull
	@Size(min = 15, max = 18)
	private String id;

	public Integer getPetId() {
		return petId;
	}

	void setPetId(Integer petId) {
		this.petId = petId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String toString() {
		return new ToStringCreator(this).append("petId", petId).append("id", id)
				.toString();
	}
}
