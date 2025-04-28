package mk.ukim.finki.emt.ptes.service;

import mk.ukim.finki.emt.ptes.model.Pet;

import java.util.List;

public interface PetService {
    Pet createPetFactory();
    List<Pet> getAllPets();
    List<Pet> createPets(int number);
}
