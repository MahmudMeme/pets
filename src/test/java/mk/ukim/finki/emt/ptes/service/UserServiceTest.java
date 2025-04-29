package mk.ukim.finki.emt.ptes.service;

import mk.ukim.finki.emt.ptes.model.HistoryLog;
import mk.ukim.finki.emt.ptes.model.Pet;
import mk.ukim.finki.emt.ptes.model.User;
import mk.ukim.finki.emt.ptes.model.enums.PetType;
import mk.ukim.finki.emt.ptes.model.exceptions.PetsNotFound;
import mk.ukim.finki.emt.ptes.model.exceptions.UsersNotFound;
import mk.ukim.finki.emt.ptes.repository.HistoryLogRepository;
import mk.ukim.finki.emt.ptes.repository.PetRepository;
import mk.ukim.finki.emt.ptes.repository.UserRepository;
import mk.ukim.finki.emt.ptes.service.Impl.FactoryGeneratorImpl;
import mk.ukim.finki.emt.ptes.service.Impl.UserServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PetRepository petRepository;

    @Mock
    private HistoryLogRepository historyLogRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private FactoryGeneratorImpl factoryGenerator;


    @Test
    public void createUserFactoryTest() {
        User user = User.builder()
                .name("Test")
                .surname("Surname")
                .email("test@test.com")
                .budget(10)
                .pets(new ArrayList<>())
                .build();

        when(factoryGenerator.generateFirstName()).thenReturn("Test");
        when(factoryGenerator.generateLastName()).thenReturn("Surname");
        when(factoryGenerator.generateEmail()).thenReturn("test@test.com");
        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        User savedUser = userService.createFactory();

        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getName()).isEqualTo("Test");
    }

    @Test
    public void getAllUsersTest() {
        User user = User.builder()
                .name("Test")
                .surname("Surname")
                .email("test@test.com")
                .budget(10)
                .pets(new ArrayList<>())
                .build();

        when(userRepository.findAll()).thenReturn(List.of(user));

        List<User> users = userService.findAll();

        Assertions.assertThat(users).isNotNull();
        Assertions.assertThat(users.size()).isEqualTo(1);
    }

    private void setupUserFactoryMock() {
        when(factoryGenerator.generateFirstName()).thenReturn("Test");
        when(factoryGenerator.generateLastName()).thenReturn("Surname");
        when(factoryGenerator.generateEmail()).thenReturn("test@test.com");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    public void createUserFactory_withUsersInputMoreThenExpected() {
        setupUserFactoryMock();
        int excessiveRequest = 25;

        List<User> result = userService.createUsers(excessiveRequest);

        assertThat(result).hasSize(20);
        verify(userRepository, times(20)).save(any(User.class));
    }

    @Test
    public void createUsers_withUsersInputZeroOrNegativeAs20Pets() {
        setupUserFactoryMock();
        assertThat(userService.createUsers(-5)).hasSize(20);

        verify(userRepository, times(20)).save(any(User.class));
    }

    @Test
    public void createHistoryLogTest() {
        int allUsers = 100;
        int uniqueUsers = 75;
        ArgumentCaptor<HistoryLog> logCaptor = ArgumentCaptor.forClass(HistoryLog.class);

        // Act
        userService.addHistoryLog(allUsers, uniqueUsers);

        // Assert
        verify(historyLogRepository).save(logCaptor.capture());

        HistoryLog savedLog = logCaptor.getValue();
        Assertions.assertThat(savedLog.getNumberOfUsersSuccess()).isEqualTo(uniqueUsers);


    }

    @Test
    void buy_ThrowWhenNoUsersExist() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> userService.buy())
                .isInstanceOf(UsersNotFound.class)
                .hasMessageContaining("There are 0 users in the database");
    }

    @Test
    void buy_shouldThrowWhenNoPetsExist() {
        when(userRepository.findAll()).thenReturn(List.of(new User()));
        when(petRepository.findAll()).thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> userService.buy())
                .isInstanceOf(PetsNotFound.class)
                .hasMessageContaining("There are no pets in your database");
    }

    @Test
    void buy_shouldNotPurchaseWhenBudgetInsufficient() {
        User user = User.builder().budget(10).build();
        Pet expensivePet = Pet.builder().price(20).owner(null).build();

        when(userRepository.findAll()).thenReturn(List.of(user));
        when(petRepository.findAll()).thenReturn(List.of(expensivePet));

        List<User> result = userService.buy();

        assertThat(result).hasSize(0);
        verify(userRepository, never()).save(any());
        verify(petRepository, never()).save(any());
    }

    @Test
    void buy_shouldNotPurchaseWhenPetAlreadyOwned() {
        User user = User.builder().budget(100).build();
        User otherUser = User.builder().build();
        Pet ownedPet = Pet.builder().price(50).owner(otherUser).build();

        when(userRepository.findAll()).thenReturn(List.of(user));
        when(petRepository.findAll()).thenReturn(List.of(ownedPet));

        List<User> result = userService.buy();

        assertThat(result).hasSize(0);
        verify(userRepository, never()).save(any());
    }

    @Test
    void buy_shouldCompleteSuccessfulPurchase() {
        User user = User.builder()
                .id(1L)
                .name("Alice")
                .budget(100)
                .pets(new ArrayList<>())
                .build();

        Pet cat = Pet.builder()
                .id(1L)
                .name("Whiskers")
                .price(30)
                .petType(PetType.CAT)
                .owner(null)
                .build();

        Pet dog = Pet.builder()
                .id(2L)
                .name("Rex")
                .price(40)
                .petType(PetType.DOG)
                .owner(null)
                .build();

        when(userRepository.findAll()).thenReturn(List.of(user));
        when(petRepository.findAll()).thenReturn(List.of(cat, dog));

        List<User> result = userService.buy();
        int finalBudget = 100 - 40 - 30;
        assertThat(result.get(0).getBudget()).isEqualTo(finalBudget);
        assertThat(result.get(0).getPets()).containsExactlyInAnyOrder(cat, dog);
        assertThat(cat.getOwner()).isEqualTo(user);
        assertThat(dog.getOwner()).isEqualTo(user);

        verify(userRepository, times(2)).save(user);//kupil
        verify(petRepository).save(cat);
        verify(petRepository).save(dog);
    }

}
