package mk.ukim.finki.emt.ptes.service;

import mk.ukim.finki.emt.ptes.model.HistoryLog;
import mk.ukim.finki.emt.ptes.repository.HistoryLogRepository;
import mk.ukim.finki.emt.ptes.service.Impl.HistoryLogServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HistoryLogServiceTest {

    @Mock
    private HistoryLogRepository historyLogRepository;

    @InjectMocks
    private HistoryLogServiceImpl historyLogService;

    @Test
    public void findAll() {
        HistoryLog historyLog = HistoryLog.builder()
                .date(Date.from(Instant.now()))
                .numberOfUsersSuccess(1)
                .numberOfUsersFailed(1)
                .build();

        when(historyLogRepository.findAll()).thenReturn(List.of(historyLog));

        List<HistoryLog> logs = historyLogService.getAllHistoryLogs();

        Assertions.assertThat(logs).hasSize(1);
        Assertions.assertThat(logs).isNotNull();

    }
}
