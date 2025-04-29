package mk.ukim.finki.emt.ptes.repository;


import mk.ukim.finki.emt.ptes.model.HistoryLog;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class HistoryLogRepositoryTest {

    @Autowired
    private HistoryLogRepository historyLogRepository;

    @Test
    public void saveHistoryLogTest() {
        HistoryLog historyLog = HistoryLog.builder()
                .date(Date.from(Instant.now()))
                .numberOfUsersSuccess(1)
                .numberOfUsersFailed(1)
                .build();

        HistoryLog savedHistoryLog = historyLogRepository.save(historyLog);


        Assertions.assertThat(savedHistoryLog).isNotNull();
        Assertions.assertThat(savedHistoryLog.getId()).isGreaterThan(0);
        Assertions.assertThat(savedHistoryLog.getNumberOfUsersFailed()).isEqualTo(historyLog.getNumberOfUsersFailed());
    }

    @Test
    void getAllHistoryLogsTest() {
        HistoryLog historyLog = HistoryLog.builder()
                .date(Date.from(Instant.now()))
                .numberOfUsersSuccess(1)
                .numberOfUsersFailed(1)
                .build();
        HistoryLog historyLog2 = HistoryLog.builder()
                .date(Date.from(Instant.now()))
                .numberOfUsersSuccess(11)
                .numberOfUsersFailed(1)
                .build();

        historyLogRepository.save(historyLog);
        historyLogRepository.save(historyLog2);

        List<HistoryLog> historyLogs = historyLogRepository.findAll();

        Assertions.assertThat(historyLogs.size()).isEqualTo(2);
        Assertions.assertThat(historyLogs).isNotNull();
    }
}
