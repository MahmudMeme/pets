package mk.ukim.finki.emt.ptes.repository;

import mk.ukim.finki.emt.ptes.model.Pet;
import mk.ukim.finki.emt.ptes.model.enums.PetType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PetRepositoryTest {

    @Autowired
    private PetRepository petRepository;


    @Test
    public void savePet() {

        Pet pet = Pet.builder()
                .name("asda")
                .description("asda")
                .birthDate(LocalDate.now())
                .price(1)
                .petType(PetType.DOG)
                .rating(2)
                .owner(null)
                .build();

        Pet savedPet = petRepository.save(pet);

        Assertions.assertThat(savedPet).isNotNull();
        Assertions.assertThat(savedPet.getId()).isGreaterThan(0);
        Assertions.assertThat(savedPet.getName()).isEqualTo(pet.getName());
    }

    @Test
    public void getAllPets() {
        Pet pet = Pet.builder()
                .name("asda")
                .description("asda")
                .birthDate(LocalDate.now())
                .price(1)
                .petType(PetType.DOG)
                .rating(2)
                .owner(null)
                .build();

        Pet pet2 = Pet.builder()
                .name("a")
                .description("asda")
                .birthDate(LocalDate.now())
                .price(1)
                .petType(PetType.DOG)
                .rating(2)
                .owner(null)
                .build();

        petRepository.save(pet);
        petRepository.save(pet2);

        List<Pet> pets = petRepository.findAll();

        Assertions.assertThat(pets.size()).isEqualTo(2);
        Assertions.assertThat(pets).isNotNull();
    }
}
