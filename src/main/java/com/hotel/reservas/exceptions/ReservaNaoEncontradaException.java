package com.hotel.reservas.exceptions;

import java.util.UUID;

public class ReservaNaoEncontradaException extends RuntimeException {
    public ReservaNaoEncontradaException(UUID id) {
        super("Reserva não encontrada para o ID: " + id);
    }
}
