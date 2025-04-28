package mk.ukim.finki.emt.ptes.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users_table")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;
    private String email;

    private int budget;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "owner")
    @JsonBackReference
    private List<Pet> pets;

    public User( String name, String surname, String email, int budget) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.budget = budget;
        this.pets = new ArrayList<>();
    }
}
