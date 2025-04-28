package mk.ukim.finki.emt.ptes.service.Impl;

import mk.ukim.finki.emt.ptes.model.HistoryLog;
import mk.ukim.finki.emt.ptes.repository.HistoryLogRepository;
import mk.ukim.finki.emt.ptes.service.HistoryLogService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryLogServiceImpl implements HistoryLogService {

    private final HistoryLogRepository historyLogRepository;

    public HistoryLogServiceImpl(HistoryLogRepository historyLogRepository) {
        this.historyLogRepository = historyLogRepository;
    }


    @Override
    public List<HistoryLog> getAllHistoryLogs() {
        return historyLogRepository.findAll();
    }
}
