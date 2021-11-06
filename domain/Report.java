package com.imdev.webchat.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imdev.webchat.socket.ReportMessage;
import com.imdev.webchat.socket.MessageType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.*;

@Getter
@Setter
public class Report {
    private String reportId;
    private String memberId;
    private Set<WebSocketSession> sessions = new HashSet<>();

    public static Report create(String memberId){
        Report report = new Report();
        report.memberId = memberId;
        report.reportId = UUID.randomUUID().toString();
        return report;
    }

    public void handleMessage(WebSocketSession session, ReportMessage chatMessage,
                              ObjectMapper objectMapper) throws IOException {
        if(chatMessage.getType() == MessageType.ENTER){
            sessions.add(session);
        }
        else if(chatMessage.getType() == MessageType.LEAVE){
            sessions.remove(session);
        }
        else{
            chatMessage.setMessage(chatMessage.getWriter() + " : " + chatMessage.getMessage());
        }
        send(chatMessage,objectMapper);
    }

    private void send(ReportMessage chatMessage, ObjectMapper objectMapper) throws IOException {
        TextMessage textMessage = new TextMessage(objectMapper.
                writeValueAsString(chatMessage.getMessage()));
        for(WebSocketSession sess : sessions){
            sess.sendMessage(textMessage);
        }
    }
}
