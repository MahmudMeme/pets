package mk.ukim.finki.emt.ptes.repository;

import mk.ukim.finki.emt.ptes.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void saveUserTest() {
        User user = User.builder()
                .name("Test")
                .surname("Surname")
                .email("test@test.com")
                .budget(10)
                .pets(new ArrayList<>())
                .build();

        User savedUser = userRepository.save(user);

        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isGreaterThan(0);
        Assertions.assertThat(savedUser.getName()).isEqualTo(user.getName());
    }

    @Test
    void getAllUsersTest() {
        User user = User.builder()
                .name("Test")
                .surname("Surname")
                .email("test@test.com")
                .budget(10)
                .pets(new ArrayList<>())
                .build();
        User user2 = User.builder()
                .name("Test2")
                .surname("Surname")
                .email("test@test.com")
                .budget(10)
                .pets(new ArrayList<>())
                .build();
        userRepository.save(user);
        userRepository.save(user2);

        List<User> users = userRepository.findAll();

        Assertions.assertThat(users.size()).isEqualTo(2);
        Assertions.assertThat(users).isNotNull();
    }
}
