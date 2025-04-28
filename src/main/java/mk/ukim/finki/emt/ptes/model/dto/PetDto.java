package mk.ukim.finki.emt.ptes.model.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mk.ukim.finki.emt.ptes.model.enums.PetType;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PetDto {
    private Long id;

    private String name;
    private LocalDate birthDate;
    private int price;
    @Enumerated(EnumType.STRING)
    private PetType petType;
    private int rating;

    private SingalUserOwner owner;
}
