package mk.ukim.finki.emt.ptes.repository;

import mk.ukim.finki.emt.ptes.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
