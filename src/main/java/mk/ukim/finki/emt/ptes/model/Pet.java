package mk.ukim.finki.emt.ptes.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import mk.ukim.finki.emt.ptes.model.enums.PetType;

import java.time.LocalDate;

@Entity(name = "pet")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private LocalDate birthDate;
    private int price;
    @Enumerated(EnumType.STRING)
    private PetType petType;
    private int rating;

    @ManyToOne()
    @JsonManagedReference
    private User owner;


}
