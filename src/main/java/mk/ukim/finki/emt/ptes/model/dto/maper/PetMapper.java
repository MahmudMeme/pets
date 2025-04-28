package mk.ukim.finki.emt.ptes.model.dto.maper;

import mk.ukim.finki.emt.ptes.model.Pet;
import mk.ukim.finki.emt.ptes.model.User;
import mk.ukim.finki.emt.ptes.model.dto.PetDto;
import mk.ukim.finki.emt.ptes.model.dto.SingalUserOwner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PetMapper {
    public PetDto toDto(Pet pet) {
        return new PetDto(
                pet.getId(),
                pet.getName(),
                pet.getBirthDate(),
                pet.getPrice(),
                pet.getPetType(),
                pet.getRating(),
                toSingalUserOwner(pet.getOwner())
        );
    }

    public SingalUserOwner toSingalUserOwner(User user) {
        if (user == null) {
            return null;
        }
        return new SingalUserOwner(
                user.getId(),
                user.getName(),
                user.getSurname()
        );
    }

    public List<PetDto> toListDto(List<Pet> pets) {
        return pets.stream().map(this::toDto).collect(Collectors.toList());
    }
}
