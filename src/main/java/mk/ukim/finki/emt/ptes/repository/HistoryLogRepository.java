package mk.ukim.finki.emt.ptes.repository;

import mk.ukim.finki.emt.ptes.model.HistoryLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryLogRepository extends JpaRepository<HistoryLog, Long> {
}
