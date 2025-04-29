package mk.ukim.finki.emt.ptes.service;

import mk.ukim.finki.emt.ptes.model.Pet;
import mk.ukim.finki.emt.ptes.model.enums.PetType;
import mk.ukim.finki.emt.ptes.repository.PetRepository;
import mk.ukim.finki.emt.ptes.service.Impl.FactoryGeneratorImpl;
import mk.ukim.finki.emt.ptes.service.Impl.PetServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PetServiceTest {

    @Mock
    private PetRepository petRepository;

    @InjectMocks
    private PetServiceImpl petService;

    @Mock
    private FactoryGeneratorImpl factoryGenerator;


    @Test
    public void createPet_returnsPet() {
        LocalDate testDate = LocalDate.of(2020, 1, 1);

        when(factoryGenerator.generatePetName(anyInt())).thenReturn("TestPet");
        when(factoryGenerator.generatePetDescription()).thenReturn("TestDescription");
        when(factoryGenerator.generateBirthDate()).thenReturn(testDate);

        Pet pet = Pet.builder()
                .name("asda")
                .description("asda")
                .birthDate(LocalDate.now())
                .price(1)
                .petType(PetType.DOG)
                .rating(2)
                .owner(null)
                .build();

        when(petRepository.save(any(Pet.class))).thenReturn(pet);

        Pet savedPet = petService.createPetFactory();

        Assertions.assertThat(savedPet).isNotNull();
    }

    @Test
    public void getAllPets_returnsListOfPets() {
        Pet pet = Pet.builder()
                .name("asda")
                .description("asda")
                .birthDate(LocalDate.now())
                .price(1)
                .petType(PetType.DOG)
                .rating(2)
                .owner(null)
                .build();

        when(petRepository.findAll()).thenReturn(List.of(pet));

        List<Pet> savedPets = petService.getAllPets();

        Assertions.assertThat(savedPets).isNotNull();
        Assertions.assertThat(savedPets.size()).isEqualTo(1);
    }

    private void setupPetFactoryMock() {
        LocalDate testDate = LocalDate.of(2020, 1, 1);
        when(factoryGenerator.generatePetName(anyInt())).thenReturn("TestPet");
        when(factoryGenerator.generatePetDescription()).thenReturn("TestDescription");
        when(factoryGenerator.generateBirthDate()).thenReturn(testDate);
        when(petRepository.save(any(Pet.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    public void createPetsFactory_withUsersInputMoreThenExpected() {
        setupPetFactoryMock();
        int excessiveRequest = 25;

        List<Pet> result = petService.createPets(excessiveRequest);

        assertThat(result).hasSize(20);
        verify(petRepository, times(20)).save(any(Pet.class));
    }

    @Test
    public void createPets_withUsersInputZeroOrNegativeAs20Pets() {
        setupPetFactoryMock();
        assertThat(petService.createPets(-5)).hasSize(20);

        verify(petRepository, times(20)).save(any(Pet.class));
    }
}
