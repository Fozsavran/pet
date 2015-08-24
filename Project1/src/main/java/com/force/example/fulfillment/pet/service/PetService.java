package com.force.example.fulfillment.pet.service;

import com.force.example.fulfillment.pet.model.Pet;

import java.util.List;

public interface PetService {
    public void addPet(Pet pet);
    public List<Pet> listPets();
    public Pet findPet(Integer petId);
    public void removePet(Integer petId);
	List<Pet> findPetById(String id);
}