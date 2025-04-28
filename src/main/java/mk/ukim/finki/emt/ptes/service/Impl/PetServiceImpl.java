package mk.ukim.finki.emt.ptes.service.Impl;

import mk.ukim.finki.emt.ptes.model.Pet;
import mk.ukim.finki.emt.ptes.model.dto.PetDto;
import mk.ukim.finki.emt.ptes.model.dto.maper.PetMapper;
import mk.ukim.finki.emt.ptes.model.enums.PetType;
import mk.ukim.finki.emt.ptes.repository.PetRepository;
import mk.ukim.finki.emt.ptes.service.FactoryGenerator;
import mk.ukim.finki.emt.ptes.service.PetService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class PetServiceImpl implements PetService {
    private final PetRepository petRepository;
    private final FactoryGenerator factoryGenerator;

    public PetServiceImpl(PetRepository petRepository, FactoryGenerator factoryGenerator) {
        this.petRepository = petRepository;
        this.factoryGenerator = factoryGenerator;
    }

    @Override
    public Pet createPetFactory() {

        Pet pet = new Pet();
        Random rand = new Random();
        int type = rand.nextInt(2);
        pet.setName(factoryGenerator.generatePetName(type));
        pet.setDescription(factoryGenerator.generatePetDescription());
        LocalDate birthdate = factoryGenerator.generateBirthDate();
        pet.setBirthDate(birthdate);

        if (type == 0) {
            pet.setPetType(PetType.CAT);
            pet.setRating(1);
            int price = LocalDate.now().getYear() - birthdate.getYear();
            pet.setPrice(price);
        } else {
            pet.setPetType(PetType.DOG);
            int rating = rand.nextInt(11);
            pet.setRating(rating);
            int yearsDiff = LocalDate.now().getYear() - birthdate.getYear();
            int price = rating + yearsDiff;
            pet.setPrice(price);
        }
        pet.setOwner(null);

        petRepository.save(pet);
        return pet;
    }

    @Override
    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    @Override
    public List<Pet> createPets(int number) {
        if (number <= 0 || number > 20) {
            number = 20;
        }
        List<Pet> pets = new ArrayList<Pet>();
        for (int i = 0; i < number; i++) {
            pets.add(createPetFactory());
        }
        return pets;
    }
}
