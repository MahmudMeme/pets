package mk.ukim.finki.emt.ptes.web.controller;

import mk.ukim.finki.emt.ptes.model.Pet;
import mk.ukim.finki.emt.ptes.model.dto.PetDto;
import mk.ukim.finki.emt.ptes.model.dto.maper.PetMapper;
import mk.ukim.finki.emt.ptes.service.PetService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/pets")
public class PetController {
    private final PetService petService;
    private final PetMapper petMapper;

    public PetController(PetService petService, PetMapper petMapper) {
        this.petService = petService;
        this.petMapper = petMapper;
    }

    @GetMapping("/create")
    public Pet create() {
        return petService.createPetFactory();
    }

    @GetMapping("/all")
    public List<PetDto> getAll() {
        List<Pet> pets = petService.getAllPets();
        return petMapper.toListDto(pets);
    }

    @GetMapping({"/create-pets", "/create-pets/", "/create-pets/{number}"})
    public List<Pet> createPets(@PathVariable(required = false) Integer number) {
        int count = (number == null) ? 20 : number;
        return petService.createPets(count);
    }

}
