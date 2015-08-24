package com.force.example.fulfillment.pet.controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.force.example.fulfillment.pet.model.Pet;
import com.force.example.fulfillment.pet.service.PetService;

@Controller
@RequestMapping(value="/pet")
public class PetController {
	
	@Autowired
	private PetService petService;
	
	private Validator validator;
	
	@Autowired
	public PetController(Validator validator) {
		this.validator = validator;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public @ResponseBody List<? extends Object> create(@Valid @RequestBody Pet[] pets, HttpServletResponse response) {
		boolean failed = false;
		List<Map<String, String>> failureList = new LinkedList<Map<String, String>>();
		for (Pet pet: pets) {
			Set<ConstraintViolation<Pet>> failures = validator.validate(pet);
			if (failures.isEmpty()) {
				Map<String, String> failureMessageMap = new HashMap<String, String>();
				if (! petService.findPetById(pet.getId()).isEmpty()) {					
					failureMessageMap.put("id", "id already exists in database");					
					failed = true;
				}
				failureList.add(failureMessageMap);
			} else {
				failureList.add(validationMessages(failures));
				failed = true;
			}
		}
		if (failed) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return failureList;
		} else {
			List<Map<String, Object>> responseList = new LinkedList<Map<String, Object>>();
			for (Pet pet: pets) {
				petService.addPet(pet);
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("id", pet.getId());
				map.put("pet_number", pet.getPetId());
				responseList.add(map);
			}
			return responseList;
		}
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public @ResponseBody List<Pet> getPets() {
		return petService.listPets();
	}

	@RequestMapping(value="{petId}", method=RequestMethod.GET)
	public @ResponseBody Pet getPet(@PathVariable Integer petId) {
		Pet pet = petService.findPet(petId);
		if (pet == null) {
			throw new ResourceNotFoundException(petId);
		}
		return pet;
	}
	
	@RequestMapping(value="{petId}", method=RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public void deletePet(@PathVariable Integer petId) {
		petService.removePet(petId);
	}
	
	// internal helper
	private Map<String, String> validationMessages(Set<ConstraintViolation<Pet>> failureSet) {
		Map<String, String> failureMessageMap = new HashMap<String, String>();
		for (ConstraintViolation<Pet> failure : failureSet) {
			failureMessageMap.put(failure.getPropertyPath().toString(), failure.getMessage());
		}
		return failureMessageMap;
	}
}
