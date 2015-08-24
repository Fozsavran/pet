package com.force.example.fulfillment.pet.service;

import com.force.example.fulfillment.pet.model.Pet;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PetServiceImpl implements PetService {

    @PersistenceContext
    EntityManager em;
        
	@Override
    @Transactional
	public void addPet(Pet pet) {
		em.persist(pet);
	}

	@Override
    @Transactional
	public List<Pet> listPets() {
		CriteriaQuery<Pet> c = em.getCriteriaBuilder().createQuery(Pet.class);
		c.from(Pet.class);
        return em.createQuery(c).getResultList();
	}

	@Override
    @Transactional
	public Pet findPet(Integer petId) {
		return em.find(Pet.class, petId);
	}
	
	@Override
    @Transactional
	public void removePet(Integer petId) {
		Pet pet = em.find(Pet.class, petId);
		if (null != pet) {
			em.remove(pet);
		}
	}

	@Override
	public List<Pet> findPetById(String id) {
		return em.createQuery("SELECT o FROM Pet o WHERE o.id = :id", Pet.class)
		    .setParameter("id", id)
		    .getResultList();
	}
}
