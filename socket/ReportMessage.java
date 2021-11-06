package com.imdev.webchat.socket;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportMessage {
    private String reportId;
    private String writer;
    private String message;
    private MessageType type;
}
