package com.hotel.reservas.exceptions;

import java.time.LocalDateTime;
import java.util.List;

public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private List<String> mensagens;

    public ErrorResponse(LocalDateTime timestamp, int status, String error, List<String> mensagens) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.mensagens = mensagens;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public List<String> getMensagens() {
        return mensagens;
    }
}
