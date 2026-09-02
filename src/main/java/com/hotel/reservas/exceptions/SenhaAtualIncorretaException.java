package com.hotel.reservas.exceptions;

public class SenhaAtualIncorretaException extends RuntimeException {
    public SenhaAtualIncorretaException() {
        super("A senha atual está incorreta.");
    }
}
