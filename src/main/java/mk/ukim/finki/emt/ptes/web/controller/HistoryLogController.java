package mk.ukim.finki.emt.ptes.web.controller;

import mk.ukim.finki.emt.ptes.model.HistoryLog;
import mk.ukim.finki.emt.ptes.service.HistoryLogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/history")
public class HistoryLogController {

    private final HistoryLogService historyLogService;

    public HistoryLogController(HistoryLogService historyLogService) {
        this.historyLogService = historyLogService;
    }

    @GetMapping
    public List<HistoryLog> getAllHistoryLogs() {
        return historyLogService.getAllHistoryLogs();
    }
}
