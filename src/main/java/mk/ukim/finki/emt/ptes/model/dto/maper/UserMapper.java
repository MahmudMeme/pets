package mk.ukim.finki.emt.ptes.model.dto.maper;

import mk.ukim.finki.emt.ptes.model.Pet;
import mk.ukim.finki.emt.ptes.model.User;
import mk.ukim.finki.emt.ptes.model.dto.PetDto;
import mk.ukim.finki.emt.ptes.model.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    public UserDto toUserDto(User user) {
        UserDto dto = new UserDto(
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getBudget(),
                0,
                new ArrayList<>()
        );
        if (user.getPets() != null) {
            List<PetDto> petDtos = new ArrayList<>();
            for (Pet pet : user.getPets()) {
                PetMapper petMapper = new PetMapper();
                petDtos.add(petMapper.toDto(pet));
            }
            dto.setPets(petDtos);
            dto.setNumberOfPets(petDtos.size());
        }
        return dto;
    }

    public List<UserDto> toListUserDto(List<User> users) {
        return users.stream()
                .map(this::toUserDto)
                .collect(Collectors.toList());
    }
}
