package com.imdev.webchat.socket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imdev.webchat.domain.Report;
import com.imdev.webchat.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {
    private final ReportRepository reportRepository;
    private final ObjectMapper objectMapper;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("메세지 전송 = {} : {}",session,message.getPayload());
        String msg = message.getPayload();
        ReportMessage reportMessage = objectMapper.readValue(msg, ReportMessage.class);
        Report report = reportRepository.findReportById(reportMessage.getReportId());
        report.handleMessage(session,reportMessage,objectMapper);
    }

}
