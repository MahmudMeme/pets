package mk.ukim.finki.emt.ptes.model.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private int budget;
    private int numberOfPets;
    private List<PetDto> pets;



}
