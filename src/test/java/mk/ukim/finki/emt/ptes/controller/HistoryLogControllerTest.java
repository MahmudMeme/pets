package mk.ukim.finki.emt.ptes.controller;

import mk.ukim.finki.emt.ptes.model.HistoryLog;
import mk.ukim.finki.emt.ptes.service.HistoryLogService;
import mk.ukim.finki.emt.ptes.web.controller.HistoryLogController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = HistoryLogController.class)
public class HistoryLogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private HistoryLogService historyLogService;

    @Test
    public void findAll() throws Exception {
        HistoryLog historyLog = new HistoryLog();
        HistoryLog historyLog2 = new HistoryLog();

        when(historyLogService.getAllHistoryLogs()).thenReturn(List.of(historyLog, historyLog2));

        mockMvc.perform(get("/api/history"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

        verify(historyLogService).getAllHistoryLogs();
    }
}
