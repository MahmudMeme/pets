package mk.ukim.finki.emt.ptes.repository;

import mk.ukim.finki.emt.ptes.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
}
