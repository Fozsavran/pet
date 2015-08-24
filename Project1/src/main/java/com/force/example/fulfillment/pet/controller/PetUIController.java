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
@RequestMapping(value="/petui")
public class PetUIController {
	
	@Autowired
	private PetService petService;
	
	private Validator validator;
	
	@Autowired
	public PetUIController(Validator validator) {
		this.validator = validator;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String getPetsPage(Model model) {
		model.addAttribute("pet", new Pet());
		model.addAttribute("pets", petService.listPets());
		
		return "pets";
	}

	@RequestMapping(value="{petId}", method=RequestMethod.GET)
	public String getPetPage(@PathVariable Integer petId, Model model) {
		Pet pet = petService.findPet(petId);
		if (pet == null) {
			throw new ResourceNotFoundException(petId);
		}
		model.addAttribute("pet", pet);
		
		return "pet";
	}	
}
