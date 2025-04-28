package mk.ukim.finki.emt.ptes.model.dataholder;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.emt.ptes.service.PetService;
import mk.ukim.finki.emt.ptes.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class DataHolder {

    private final UserService userService;
    private final PetService petService;

    public DataHolder(UserService userService, PetService petService) {
        this.userService = userService;
        this.petService = petService;
    }


    @PostConstruct
    public void init() {
        if (userService.findAll().isEmpty()) {
            userService.createFactory();
            userService.createFactory();
            userService.createFactory();
        }

        if (petService.getAllPets().isEmpty()) {
            petService.createPetFactory();
            petService.createPetFactory();
            petService.createPetFactory();
        }
    }
}
